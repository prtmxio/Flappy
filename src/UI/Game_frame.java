package UI;

import javax.swing.*;

public class Game_frame extends JFrame {
    public Game_frame(Game_panel gp){
        this.setTitle("Flappy");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gp);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
