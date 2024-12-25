package UserInterface;

import Gamestate.Gamestate;
import Utilz.LoadAndSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Utilz.Constants.UI.MainMenuButtons.*;

public class MenuButton {

    /* Butonların oyun penceresinde belirtilen yerlere yerleştirilmesi için xPosition ve yPosition değişkenlerini; buton_atlas.png
    görselinden belirli butonlardan frame oluşturmak için rowIndex değişkenini tanımlıyoruz. */
    private int xPosition, yPosition, rowIndex, index;

    private int xOffsetCenter = BUTTON_WIDTH / 2;

    /* Bu butonlar aracılığıyla oyunun içinde bulunduğu durumu değiştireceğiz. Örneğin menu butonuna tıkladığımızda oyun MENU durumuna,
    play butonuna tıkladığımızda oyun PLAYING durumuna geçecek. Bu yüzden Gamestate enum sınıfı türünden bir state değişkenine ihtiyaç
    duyuyoruz. */
    private Gamestate state;

    private BufferedImage[] buttonImage;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPosition - xOffsetCenter, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private void loadImages() {
        buttonImage = new BufferedImage[3];
        BufferedImage temp = LoadAndSave.getSpriteAtlas(LoadAndSave.MAIN_MENU_BUTTONS_ATLAS);
        for(int i = 0; i < buttonImage.length; i++) {
            buttonImage[i] = temp.getSubimage(i * BUTTON_WIDTH_DEFAULT, rowIndex * BUTTON_HEIGHT_DEFAULT, BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(buttonImage[index], xPosition - xOffsetCenter, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT, null);
    }

    public void update() {
        index = 0;

        if(mouseOver) {
            index = 1;
        }
        if(mousePressed) {
            index = 2;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void applyGameState() {
        Gamestate.state = state;
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

}