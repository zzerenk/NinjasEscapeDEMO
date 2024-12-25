package Utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadAndSave {

    // Karakterler
    public static final String PLAYER_SPRITE_SHEET = "/Resources/Assets/PlayerCharacters/wind_SpriteSheet_288x128.png";
    public static final String CRABBY_SPRITE_SHEET = "/Resources/Assets/EnemyCharacters/crabby_sprite.png";

    // Playing durumunda level yapısı ve arkaplan görselleri
    public static final String LEVEL_ATLAS = "/Resources/Assets/OutsideSprites/outside_sprites.png";
    public static final String PLAYING_BG_IMG = "/Resources/Assets/OutsideSprites/playing_bg_img.png";
    public static final String BIG_CLOUDS = "/Resources/Assets/OutsideSprites/big_clouds.png";
    public static final String SMALL_CLOUDS = "/Resources/Assets/OutsideSprites/small_clouds.png";

    // Ana menü görselleri
    public static final String MAIN_MENU_BUTTONS_ATLAS = "/Resources/Assets/OutsideSprites/button_atlas.png";
    public static final String MAIN_MENU_BUTTONS_BACKGROUND = "/Resources/Assets/OutsideSprites/menu_background.png";
    public static final String MAIN_MENU_BACKGROUND = "/Resources/Assets/OutsideSprites/background_of_menu.png";

    // Duraklatma menüsü görselleri
    public static final String PAUSE_MENU_BACKGROUND = "/Resources/Assets/OutsideSprites/pause_menu.png";
    public static final String SOUND_BUTTONS_ATLAS = "/Resources/Assets/OutsideSprites/sound_button.png";
    public static final String URM_BUTTONS_ATLAS = "/Resources/Assets/OutsideSprites/urm_buttons.png";
    public static final String VOLUME_BUTTONS_ATLAS = "/Resources/Assets/OutsideSprites/volume_buttons.png";

    // Oyuncu can ve enerji göstergesi
    public static final String STATUS_BAR = "/Resources/Assets/OutsideSprites/health_power_bar.png";

    // Level tamamlandı görseli
    public static final String COMPLETED_IMG = "/Resources/Assets/OutsideSprites/completed_sprite.png";

    // Ölüm ekranı
    public static final String DEATH_SCREEN = "/Resources/Assets/OutsideSprites/death_screen.png";

    public static BufferedImage getSpriteAtlas(String path) {

        BufferedImage spriteSheet = null;
        InputStream is = LoadAndSave.class.getResourceAsStream(path);

        try {
            spriteSheet = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return spriteSheet;
    }

    public static BufferedImage[] getAllLevels() {

        URL url = LoadAndSave.class.getResource("/Assets/levels");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for(int i = 0; i < filesSorted.length; i++)
            for(int j = 0; j < files.length; j++) {
                if(files[j].getName().equals((i + 1) + ".png"))
                    filesSorted[i] = files[j];
            }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for(int i = 0; i < imgs.length; i++) {
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgs;
    }
}
