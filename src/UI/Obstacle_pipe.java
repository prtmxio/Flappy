package UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Obstacle_pipe {
    BufferedImage top_pipe, bottom_pipe;
    ArrayList<Pipe> pipes_top;
    ArrayList<Pipe> pipes_bottom;
    Random rand;
    final int MOVE_DELAY_MS = 1200;
    long last_pipe_placed = System.currentTimeMillis();

    public Obstacle_pipe(){
        pipes_top = new ArrayList<>();
        pipes_bottom = new ArrayList<>();
        rand = new Random();
        load_Img();
    }

    public void update(){
        move();
        long currentTime = System.currentTimeMillis();
        if (currentTime - last_pipe_placed >= MOVE_DELAY_MS) {
            place_pipes();
            last_pipe_placed = currentTime;
        }
    }

    public void render(Graphics g){
        for (int i = 0; i < pipes_top.size(); i++) {
            Pipe top = pipes_top.get(i);
            Pipe bottom = pipes_bottom.get(i);
            g.drawImage(top.img, top.px, top.py, top.pw, top.ph, null);
            g.drawImage(bottom.img, bottom.px, bottom.py, bottom.pw, bottom.ph, null);
        }
    }

    public void move(){
        for (int i = 0; i < pipes_top.size(); i++) {
            Pipe top = pipes_top.get(i);
            Pipe bottom = pipes_bottom.get(i);
            top.px += top.Vx;
            bottom.px += bottom.Vx;
        }
    }

    public void place_pipes(){
        int gap = 150;
        int topPipeHeight = rand.nextInt(200) + 100;

        // Top pipe
        Pipe top = new Pipe(top_pipe);
        top.px = Game_panel.width;
        top.py = topPipeHeight - top.ph;
        pipes_top.add(top);

        // Bottom pipe
        Pipe bottom = new Pipe(bottom_pipe);
        bottom.px = Game_panel.width;
        bottom.py = topPipeHeight + gap;
        pipes_bottom.add(bottom);
    }

    public void load_Img(){
        InputStream is_t = getClass().getResourceAsStream("/toppipe.png");
        InputStream is_b = getClass().getResourceAsStream("/bottompipe.png");
        try {
            top_pipe = ImageIO.read(is_t);
            bottom_pipe = ImageIO.read(is_b);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                is_t.close();
                is_b.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
