package Gamestate;

import Main.Game;
import UserInterface.MenuButton;
import Utilz.LoadAndSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage buttonBackground, menuBackground;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadButtonBackground();
        menuBackground = LoadAndSave.getSpriteAtlas(LoadAndSave.MAIN_MENU_BACKGROUND);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, Gamestate.QUIT);
    }

    private void loadButtonBackground() {
        buttonBackground = LoadAndSave.getSpriteAtlas(LoadAndSave.MAIN_MENU_BUTTONS_BACKGROUND);
        menuWidth = (int) (buttonBackground.getWidth() * Game.SCALE);
        menuHeight = (int) (buttonBackground.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * Game.SCALE);
    }

    @Override
    public void update() {
        for(MenuButton menuButtons : buttons)
            menuButtons.update();
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(menuBackground, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(buttonBackground, menuX, menuY, menuWidth, menuHeight, null);
        for(MenuButton menuButtons : buttons) {
            menuButtons.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        for(MenuButton menuButtons : buttons) {

            if(isIn(e, menuButtons)) {

                menuButtons.setMousePressed(true);
                break;

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        for(MenuButton menuButtons : buttons) {
            if(isIn(e, menuButtons)) {

                if(menuButtons.isMousePressed()) {

                    menuButtons.applyGameState();
                    break;

                }
            }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        for(MenuButton menuButtons : buttons) {
            menuButtons.setMouseOver(false);
        }

        for(MenuButton menuButtons : buttons) {

            if (isIn(e, menuButtons)) {
                menuButtons.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void resetButtons() {

        for(MenuButton menuButtons : buttons) {
            menuButtons.resetBooleans();
        }
    }

}