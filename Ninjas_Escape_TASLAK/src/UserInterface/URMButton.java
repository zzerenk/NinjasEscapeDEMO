package UserInterface;

import Utilz.LoadAndSave;
import static Utilz.Constants.UI.URMButtons.*;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class URMButton extends PauseButton{

    private BufferedImage[] urmImage;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    public URMButton(int x, int y, int width, int height, int rowIndex) {

        /* PauseButton süper sınıfına doğru değerleri ilettiğimiz müddettçe alt sınıfın kurucu metodunun
        ebeveyn sınıfın kurucu metoundan farklı olması sorun oluşturmaz.*/
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadURMImgs();
    }

    private void loadURMImgs() {
        BufferedImage temp = LoadAndSave.getSpriteAtlas(LoadAndSave.URM_BUTTONS_ATLAS);
        urmImage = new BufferedImage[3];
        for(int i = 0; i < urmImage.length; i++) {
            urmImage[i] = temp.getSubimage(i * URM_SIZE_DEFAULT, rowIndex * URM_SIZE_DEFAULT, URM_SIZE_DEFAULT, URM_SIZE_DEFAULT);
        }
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

    public void draw(Graphics g) {
        g.drawImage(urmImage[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    public void resetBooleans() {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}