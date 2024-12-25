package UserInterface;

import Gamestate.Gamestate;
import Gamestate.Playing;
import Main.Game;
import Utilz.LoadAndSave;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Utilz.Constants.UI.PauseButtons.*;
import static Utilz.Constants.UI.URMButtons.*;
import static Utilz.Constants.UI.VolumeButtons.*;

public class PauseOverlay {

    private Playing playing;
    private BufferedImage pauseBackground;
    private int backgroundX, backgroundY, backgroundWidth, backgroundHeight;
    private SoundButton musicButton, sfxButton;
    private URMButton menuButton, replayButton, unpauseButton;
    private VolumeButton volumeButton;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createURMButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 * Game.SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createURMButtons() {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuButton = new URMButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayButton = new URMButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpauseButton = new URMButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        pauseBackground = LoadAndSave.getSpriteAtlas(LoadAndSave.PAUSE_MENU_BACKGROUND);
        backgroundWidth = (int) (pauseBackground.getWidth() * Game.SCALE);
        backgroundHeight = (int) (pauseBackground.getHeight() * Game.SCALE);
        backgroundX = Game.GAME_WIDTH / 2 - backgroundWidth / 2;
        backgroundY = (int) (25 * Game.SCALE);
    }

    public void update() {

        // Durdurma menüsü müzik/ses efektleri seçeneklerini güncelle.
        musicButton.update();
        sfxButton.update();

        // Durdurma menüsü seçeneklerini güncelle.
        menuButton.update();
        replayButton.update();
        unpauseButton.update();

        //Durdurma menüsü ses düzeyi seçeneklerini güncelle.
        volumeButton.update();
    }

    public void draw(Graphics g) {
        // Arkaplan
        g.drawImage(pauseBackground, backgroundX, backgroundY, backgroundWidth, backgroundHeight, null);

        //Durdurma menüsü müzik/ses efektleri seçenekleri görsellerini çiz.
        musicButton.draw(g);
        sfxButton.draw(g);

        //Durdurma menüsü URM seçenekleri görsellerini çiz.
        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);

        //Durdurma menüsü ses düzeyi seçenekleri görsellerini çiz.
        volumeButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {

        /* Kullanıcı butona tıkladığında sürükleme işlemini slider'ın dışına çıkarsa bile yapmalı. */
        if(volumeButton.isMousePressed()) {
            volumeButton.changeVolumeButtonXPosition(e.getX());
        }

    }

    public void mousePressed(MouseEvent e) {
        if(isCursorIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        }
        else if(isCursorIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        }
        else if (isCursorIn(e, menuButton)) {
            menuButton.setMousePressed(true);
        }
        else if (isCursorIn(e, replayButton)) {
            replayButton.setMousePressed(true);
        }
        else if (isCursorIn(e, unpauseButton)) {
            unpauseButton.setMousePressed(true);
        }
        else if (isCursorIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {

        if(isCursorIn(e, musicButton)) {
            if(musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if(isCursorIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        } else if(isCursorIn(e, menuButton)) {
            if (menuButton.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
            }
        } else if(isCursorIn(e, replayButton)) {
            if (replayButton.isMousePressed()) {
                playing.resetAll();
                playing.unpauseGame();
            }
        } else if(isCursorIn(e, unpauseButton)) {
            if (unpauseButton.isMousePressed()) {
                playing.unpauseGame();
            }
        }

        musicButton.resetBooleans();
        sfxButton.resetBooleans();
        menuButton.resetBooleans();
        replayButton.resetBooleans();
        unpauseButton.resetBooleans();
        volumeButton.resetBooleans();
    }

    public void mouseMoved(MouseEvent e) {

        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if(isCursorIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        }
        else if(isCursorIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        }
        else if(isCursorIn(e, menuButton)) {
            menuButton.setMouseOver(true);
        }
        else if(isCursorIn(e, replayButton)) {
            replayButton.setMouseOver(true);
        }
        else if(isCursorIn(e, unpauseButton)) {
            unpauseButton.setMouseOver(true);
        }
        else if(isCursorIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }

    private boolean isCursorIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}