package Main;

import Gamestate.Gamestate;
import Gamestate.Playing;
import Gamestate.Menu;

import java.awt.Graphics;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Game implements Runnable {

    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private LocalDateTime levelStartTime;  // Seviyeye başlama zamanı
    private int currentLevel = 1;  // Başlangıç seviyesi
    private boolean isPlayerDead = false;  // Oyuncunun ölme durumu

    private Playing playing;
    private Menu menu;

    /* Oyun penceresi boyutunu sabit tam sayılarla belirlemek yerine her bir karonun boyutunu
    belirleyerek ve pencerede kaç karo istediğimizi belirterek buluyoruz. */
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 2.0f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                /* Program oyuncuyu sadece oyun PLAYING durumundayken güncellemeli. */
                playing.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                /* Program oyuncuyu sadece oyun PLAYING durumundayken işlemeli.*/
                break;
            default:
                break;
        }
    }

    public void run() {
        // FPS ve UPS için gerekli süreyi hesaplıyoruz.
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        // Başlangıç zamanı (LocalDateTime) kullanıyoruz.
        LocalDateTime previousTime = LocalDateTime.now();
        int frames = 0;
        int updates = 0;
        LocalDateTime lastCheck = LocalDateTime.now();  // FPS ve UPS sayacını kontrol etmek için

        double deltaUpdates = 0;
        double deltaFrames = 0;

        while (true) {

            // Şu anki zamanı alıyoruz
            LocalDateTime currentTime = LocalDateTime.now();

            // Delta hesaplama için Duration kullanıyoruz
            Duration elapsed = Duration.between(previousTime, currentTime);
            deltaUpdates += elapsed.toNanos() / timePerUpdate;
            deltaFrames += elapsed.toNanos() / timePerFrame;

            previousTime = currentTime;  // Sonraki hesaplama için

            // Güncelleme (update) işlemi
            if (deltaUpdates >= 1) {
                update();
                updates++;
                deltaUpdates--;
            }

            // Frame render işlemi
            if (deltaFrames >= 1) {
                gamePanel.repaint();
                frames++;
                deltaFrames--;
            }

            // Her saniye için FPS ve UPS bilgileri konsola yazdırılır.
            if (Duration.between(lastCheck, LocalDateTime.now()).toMillis() >= 1000) {
                lastCheck = LocalDateTime.now();
                System.out.println("FPS: " + frames + " UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }



    public void windowFocusLost() {
        if(Gamestate.state == Gamestate.PLAYING) {
            playing.getPlayer().resetDirectionBooleans();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

}