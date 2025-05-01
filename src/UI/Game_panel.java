package UI;

import javax.swing.*;
import java.awt.*;

public class Game_panel extends JPanel{
    static final int width = 360;
    static final int height = 640;
    private Game game;

    public Game_panel(Game game) {
        this.game = game;
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);
        this.setBackground(new Color(255, 255, 255));
        this.setFocusTraversalKeysEnabled(false);
        this.setDoubleBuffered(true);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }



}
