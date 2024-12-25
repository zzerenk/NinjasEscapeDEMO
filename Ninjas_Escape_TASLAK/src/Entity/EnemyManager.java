package Entity;

import Gamestate.Playing;
import Levels.Level;
import Utilz.LoadAndSave;
import static Utilz.Constants.EnemyConstants.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] crabbySpriteSheet;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
    }

    public void addEnemies(Level level) {
        crabbies = level.getCrabs();
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for(Crabby c : crabbies)
            if(c.isActive()) {
                c.update(lvlData, player);
                isAnyActive = true;
            }
        if(!isAnyActive)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for(Crabby c : crabbies)
            if(c.isActive()) {
                int groundAlignmentOffset = (int) (CRABBY_HEIGHT - c.getHitbox().height) - 17;
                g.drawImage(crabbySpriteSheet[c.getEnemyState()][c.getAnimationIndex()],
                        (int) (c.getHitbox().x - (CRABBY_WIDTH / 2) + c.getHitbox().width / 2) - xLvlOffset + c.flipX(),
                        (int) (c.getHitbox().y - (CRABBY_HEIGHT - c.getHitbox().height)) + groundAlignmentOffset,
                        CRABBY_WIDTH * c.flipW(), CRABBY_HEIGHT,
                        null);
//              c.drawHitbox(g, xLvlOffset);
//              c.drawAttackBox(g, xLvlOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for(Crabby c : crabbies)
            if(c.isActive())
                if(attackBox.intersects(c.getHitbox())) {
                    c.hurt(10);
                    return;
                }
    }

    private void loadEnemyImages() {
        crabbySpriteSheet = new BufferedImage[5][9];
        BufferedImage temp = LoadAndSave.getSpriteAtlas(LoadAndSave.CRABBY_SPRITE_SHEET);
        for(int j = 0; j < crabbySpriteSheet.length; j ++)
            for(int i = 0; i < crabbySpriteSheet[j].length; i++)
                crabbySpriteSheet[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies() {
        for(Crabby c : crabbies)
            c.resetEnemy();
    }
}
