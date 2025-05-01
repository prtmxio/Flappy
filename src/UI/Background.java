package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Background {
    BufferedImage bg_img;

    public Background(){
        load_Img();
    }

    public void update(){

    }

    public void render(Graphics g){
        g.drawImage(bg_img, 0, 0, Game_panel.width, Game_panel.height, null);
    }

    public void load_Img(){
        InputStream is_bg = getClass().getResourceAsStream("/flappybirdbg.png");
        try {
            bg_img = ImageIO.read(is_bg);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                is_bg.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
