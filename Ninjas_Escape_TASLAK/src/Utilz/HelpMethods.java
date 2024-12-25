package Utilz;

import Entity.Crabby;
import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Utilz.Constants.EnemyConstants.CRABBY;

public class HelpMethods {

    /* We will pass the parameters from hitbox values, not directly from the sprite images. */
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if (IsSolid(x, y, levelData)) {
            return false;
        }

        if (IsSolid(x + width, y + height, levelData)) {
            return false;
        }

        if (IsSolid(x + width, y, levelData)) {
            return false;
        }

        return !IsSolid(x, y + height, levelData);

    }

    private static boolean IsSolid(float x, float y, int[][] levelData) {

        int maxWidth = levelData[0].length * Game.TILES_SIZE;

        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        float xTileIndex = x / Game.TILES_SIZE;
        float yTileIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int) xTileIndex, (int) yTileIndex, levelData);

    }

    public static boolean IsTileSolid(int xTileIndex, int yTileIndex, int[][] levelData) {

        int value = levelData[yTileIndex][xTileIndex];
        return value >= 48 || value < 0 || value != 11;
    }

    public static float GetEntityXPositionNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        /* Karakterimizin boyutları dolayısıyla hitbox olarak nitelendirdiğimiz dikdörtgenin hem uzun kenarı hem de kısa
        kenarı bir karonın kenar uzunluğundan kısa olacaktır. Bu durumda karakter bulunduğu konumda x ekseninde sadece
        sağdaki veya soldaki karo ile çarpışabilir. */

        // Karakterin bulunduğu mevcut karonun x boyutundaki indeksini buluyoruz.
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);

        if(xSpeed > 0) {
            // Bu durumda karakterin sadece bir sonraki karonun kenarı ile çarpışma ihtimali vardır.
            // Karakterin bulunduğu mevcut karonun x eksenindeki pixel uzunluğunu buluyoruz.
            int tileXPos = currentTile * Game.TILES_SIZE;
            /* Karakterin bulunduğu karonun içindeki x koordinatını buluyoruz. (Yani karakterin içinde bulunduğu karonun
            başlangıç x koordinatından ne kadar uzak olduğunu hesaplıyoruz. */
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            /* Karonun kenarının x koordinatının bir eksiğini geri döndürerek karakteri bir sonraki karonun kenarına
            taşıyoruz. */
            return tileXPos +  xOffset -1;
        } else {
            /* Bu durumda ise karakterin sadece bir önceki karonun kenarı ile çarpışma ihtimali var. Burada sadece bir
            önceki karonun x eksenindeki konumunu hesaplayıp döndürmemiz yeterli olacaktır.*/
            return currentTile * Game.TILES_SIZE;
        }

    }

    public static float GetEntityYPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {

        // Karakterin bulunduğu mevcut karonun y boyutundaki indeksini buluyoruz.
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);

        if(airSpeed > 0) {
            /* Bu durumda karakterin sadece y boyutundaki bir sonraki karonun kenarı ile - yani yer ile - çarpışma ihtimali
            vardır. Yani karakter düşüyordur ve havadaki hızı pozitif yönde sıfırdan farklı bir değerdir.*/

            // Karakterin bulunduğu mevcut karonun y eksenindeki pixel uzunluğunu buluyoruz.
            int tileYPos = currentTile * Game.TILES_SIZE;

            /* Karakterin bulunduğu karonun başlangıç y koordinat değerini referans alarak karakterin x koordinat değerinin
            bu değere olan uzaklığını ölçüyoruz. */
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);

            /* Çarpışılacak karonun y ekseninde üst kenarının koordinat değerinin bir eksiğini geri döndürerek karakteri bir alttaki karonun
            kenarına taşıyoruz. */
            return tileYPos + yOffset -1;
        } else  {
            /* Bu durumda karakterin sadece y boyutundaki bir önceki karonun kenarı ile - yani tavan ile - çarpışma ihtimali
            vardır. Yani karakter zıplıyordur ve havadaki hızı negatif yönde sıfırdan farklı bir değerdir.*/
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData) {
        /* Karakterin hitbox alanını gösteren diktörtgenin alt kısa kenarının iki ucunun bir piksel altındaki alan
        kontrol edilir. */

        if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData))
            return IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData);

        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData) {
        if(xSpeed > 0)
            return IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData);
        else
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData) {
        for(int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + 1, y, levelData)) {
                return false;
            }
            if (!IsTileSolid(xStart + 1, y + 1, levelData)) {
                return false;
            }
        }

        return true;
    }

    public static boolean IsSightClear(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (firstHitbox.x / Game.TILES_SIZE);

        if(firstXTile > secondXTile) {
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, levelData);
        } else {
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, levelData);
        }
    }

    public static int[][] GetLevelData(BufferedImage spriteSheet) {

        int[][] lvlData = new int[spriteSheet.getHeight()][spriteSheet.getWidth()];

        for(int j = 0; j < spriteSheet.getHeight(); j++) {
            for (int i = 0; i < spriteSheet.getWidth(); i++) {
                Color color = new Color(spriteSheet.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = color.getRed();
            }
        }

        return lvlData;

    }

    public static ArrayList<Crabby> GetCrabs(BufferedImage spriteSheet) {

        ArrayList<Crabby> list = new ArrayList<>();

        for(int j = 0; j < spriteSheet.getHeight(); j++) {
            for (int i = 0; i < spriteSheet.getWidth(); i++) {
                Color color = new Color(spriteSheet.getRGB(i, j));
                int value = color.getGreen();
                if (value == CRABBY)
                    list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        }

        return list;

    }

    public static Point GetPlayerSpawn(BufferedImage spriteSheet) {

        for(int j = 0; j < spriteSheet.getHeight(); j++) {
            for (int i = 0; i < spriteSheet.getWidth(); i++) {
                Color color = new Color(spriteSheet.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
            }
        }

        return new Point(Game.TILES_SIZE, Game.TILES_SIZE);

    }

}