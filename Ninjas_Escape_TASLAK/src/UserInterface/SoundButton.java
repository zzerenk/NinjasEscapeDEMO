package UserInterface;

import Utilz.LoadAndSave;
import static Utilz.Constants.UI.PauseButtons.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SoundButton extends PauseButton {

    private BufferedImage[][] soundButtonImage;
    private boolean mouseOver, mousePressed;

    private boolean muted;
    private int rowIndex, colIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImages();
    }

    private void loadSoundImages() {
        BufferedImage temp = LoadAndSave.getSpriteAtlas(LoadAndSave.SOUND_BUTTONS_ATLAS);
        soundButtonImage = new BufferedImage[2][3];
        for(int j = 0; j < soundButtonImage.length; j++) {
            for (int i = 0; i < soundButtonImage[j].length; i++) {
                soundButtonImage[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
    }

    public void update() {
        //Sound button görselinin 1. satırında sek false, 2. satırında ise true
        if(muted)
            rowIndex = 1;
        else
            rowIndex = 0;

        colIndex = 0;
        if(mouseOver)
            colIndex = 1;
        if(mousePressed)
            colIndex = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(soundButtonImage[rowIndex][colIndex], x, y, width, height, null);
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

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}