package UserInterface;

import Gamestate.Gamestate;
import Gamestate.Playing;
import Main.Game;
import Utilz.LoadAndSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Utilz.Constants.UI.URMButtons.URM_SIZE;

public class GameOverOverlay {

    private Playing playing;
    private BufferedImage img;
    private int imageX, imageY, imageWidth, imageHeight;
    private URMButton menu, play;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        createImg();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (335 * Game.SCALE);
        int playX = (int) (440 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        play = new URMButton(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new URMButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void createImg() {

        img = LoadAndSave.getSpriteAtlas(LoadAndSave.DEATH_SCREEN);
        imageWidth = (int) (img.getWidth() * Game.SCALE);
        imageHeight = (int) (img.getHeight() * Game.SCALE);
        imageX = Game.GAME_WIDTH / 2 - imageWidth / 2;
        imageY = (int) (100 * Game.SCALE);

    }

    public void draw(Graphics g) {

        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(img, imageX, imageY, imageWidth, imageHeight, null);
        menu.draw(g);
        play.draw(g);

    }

    public void update() {

        menu.update();
        play.update();

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }

    private boolean isIn(URMButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {

        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        }
        else if (isIn(play, e)) {
            play.setMouseOver(true);
        }
    }

    public void mouseReleased(MouseEvent e) {

        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
            }
        } else if (isIn(play, e))
            if (play.isMousePressed())
                playing.resetAll();

        menu.resetBooleans();
        play.resetBooleans();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        }
        else if (isIn(play, e)) {
            play.setMousePressed(true);
        }
    }

}