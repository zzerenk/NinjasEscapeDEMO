package Levels;

import Gamestate.Gamestate;
import Main.Game;
import Utilz.LoadAndSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;

    public LevelManager(Game game) {

        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();

    }

    public void loadNextLevel() {
        levelIndex++;
        if(levelIndex >= levels.size()) {
            levelIndex = 0;
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().addEnemies(newLevel);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels =  LoadAndSave.getAllLevels();
        for(BufferedImage img : allLevels)
            levels.add(new Level(img));
    }

    private void importOutsideSprites() {

        BufferedImage spriteSheet = LoadAndSave.getSpriteAtlas(LoadAndSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];

        for(int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = spriteSheet.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics g, int lvlOffset) {

        for(int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < levels.get(levelIndex).getLevelData()[0].length; i++) {

                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);

            }
        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

}