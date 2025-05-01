package UI;

import java.awt.*;

public class Game implements Runnable{
    Game_frame gf;
    Game_panel gp;
    KeyHandler keyH;
    private Thread game_thread;
    private final static int FPS = 90;
    private final static int UPS = 90;
    Flappy_bird flappy;
    Background bg;
    Obstacle_pipe obs;
    public Game() {
        init_classes();
        gp.requestFocusInWindow();
        start_game_loop();
    }


    private void start_game_loop(){
        game_thread = new Thread(this);
        game_thread.start();
    }

    private void init_classes() {
        gp = new Game_panel(this);
        gf = new Game_frame(gp);
        gp.addKeyListener(new KeyHandler());
        bg = new Background();
        obs = new Obstacle_pipe();
        flappy = new Flappy_bird();
    }

    public void update() {
        obs.update();
        flappy.update();
    }


    public void render(Graphics g){
        bg.render(g);
        obs.render(g);
        flappy.render(g);
    }


    @Override
    public void run(){

        double time_per_frame = 1000000000.0 / FPS;
        double time_per_update = 1000000000.0 / UPS;
        long prev_time = System.nanoTime();
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        while (true){
            long current_time = System.nanoTime();
            deltaU += (current_time - prev_time) / time_per_update;
            deltaF += (current_time - prev_time) / time_per_frame;
            prev_time = current_time;
            if(deltaU >= 1){
                // update
                update();
                updates++;
                deltaU--;
            }

            if(deltaF >= 1){
                gp.repaint();
                Toolkit.getDefaultToolkit().sync();
                frames++;
                deltaF--;
            }


            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | " + "UPS : "+ updates);
                frames = 0;
                updates = 0;
            }

            Thread.yield(); // Sleep for 1 ms
        }
    }

}
