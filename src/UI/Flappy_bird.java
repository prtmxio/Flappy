package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Flappy_bird {
    private int[] pos = {Game_panel.width / 8, Game_panel.height / 2};
    int x = pos[0];
    int y = pos[1];
    private int width = 34;
    private int height = 24;
    BufferedImage img;
    static float Vy = 0;
    final int MOVE_DELAY_MS = 30;
    long lastMoveTime = System.currentTimeMillis();
    float gravity = 0.45f;


    public Flappy_bird() {
        load_animation();
    }

    private void load_animation() {
        InputStream is = getClass().getResourceAsStream("/flappybird.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void move(){
        Vy += gravity;
        y += Vy;
        if(y < 0){
            y = 0;
        } else if (y > Game_panel.height){
            y = Game_panel.height;
        }

    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= MOVE_DELAY_MS) {
            move();
            lastMoveTime = currentTime;
        }
    }

    public void render(Graphics g) {
        g.drawImage(img, (int)x, (int)y, width, height, null);
    }

}
