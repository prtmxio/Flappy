package UI;

import java.awt.image.BufferedImage;

public class Pipe {
    BufferedImage img;
    int[] pos = {Game_panel.width, 0};
    int px = pos[0];
    int py = pos[1];
    int pw = 64;
    int ph = 512;
    float Vx = -2;
    boolean pipe_passed = false;


    public Pipe(BufferedImage pipe){
        this.img = pipe;
    }


}
