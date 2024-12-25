package Entity;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static Utilz.Constants.Directions.*;
import static Utilz.Constants.EnemyConstants.*;

public class Crabby extends Enemy {

    // Saldırı hitboxu
    private Rectangle2D.Float attackHitBox;
    private int attackHitBoxOffsetX;

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackHitBox = new Rectangle2D.Float(x, y, (int) (82 * Game.SCALE), (int) (19 * Game.SCALE));
        attackHitBoxOffsetX = (int) (Game.SCALE * 30);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationPulse();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackHitBox.x = hitbox.x - attackHitBoxOffsetX;
        attackHitBox.y = hitbox.y;
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);
        if (inAir) {
            updateInAir(lvlData);
        }else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if(canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerInAttackRange(player))
                            newState(ATTACK);
                    }

                    move(lvlData);
                    break;
                case ATTACK:
                    if(animationIndex == 0)
                        attackChecked = false;

                    if(animationIndex == 3 && !attackChecked)
                        checkEnemyHit(attackHitBox, player);
                    break;
                case HIT:

                    break;
            }
        }
    }

    public void drawCrabbyAttackHitBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackHitBox.x - xLvlOffset), (int) attackHitBox.y, (int) attackHitBox.width, (int) attackHitBox.height);
    }

    public int flipX() {
        if (runningDirection == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (runningDirection == RIGHT)
            return -1;
        else
            return 1;
    }

}