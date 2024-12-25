package UserInterface;

import Utilz.LoadAndSave;
import static Utilz.Constants.UI.VolumeButtons.*;

import java.awt.*;
import java.awt.image.BufferedImage;


public class VolumeButton extends PauseButton {

    private BufferedImage[] volumeButton;
    private BufferedImage slider;
    private int index = 0;
    private boolean mouseOver, mousePressed;
    private int buttonX, minX, maxX;

    public VolumeButton(int x, int y, int width, int height) {
        //Sadece kaydıracağımız butonun bir hitbox'a sahip olması gerek.
        super(x + width / 2, y, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH / 2;
        buttonX = x + width / 2;
        this.x = x;
        this.width = width;

        // Kayan butonun sadece kaydırgacı üzerinde hareket etmesi için sınırlar koyuyoruz.
        minX = x + VOLUME_WIDTH / 2;
        maxX = x + width - VOLUME_WIDTH / 2;
        loadVolumeButtonImage();
    }

    private void loadVolumeButtonImage() {
        BufferedImage temp = LoadAndSave.getSpriteAtlas(LoadAndSave.VOLUME_BUTTONS_ATLAS);

        volumeButton = new BufferedImage[3];
        for(int i = 0; i < volumeButton.length; i++) {
            volumeButton[i] = temp.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
        }

        slider = temp.getSubimage(3 * VOLUME_WIDTH_DEFAULT, 0, SLIDER_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
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
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(volumeButton[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);
    }

    public void changeVolumeButtonXPosition(int x) {

        /* Eğer kullanıcı ses düzeyini minimum değerden daha aşağı kaydırmaya çalışırsa butonun konumu minimum
        değerde sabitlenir. */
        if(x < minX) {
            buttonX = minX;
        }
        /* Eğer kullanıcı ses düzeyini maksimum değerden daha yükseğe kaydırmaya çalışırsa butonun konumu
        maksimum değerde sabitlenir. */
        else if(x > maxX) {
            buttonX = maxX;
        }
        /* Eğer kullanıcı ses düzeyini değer aralığında değiştirmek istiyorsa, fonksiyonun parametre olarak
        aldığı x değeri ses düzeyi butonunun x değerine atanır. */
        else {
            buttonX = x;
        }

        bounds.x = buttonX - VOLUME_WIDTH / 2;
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