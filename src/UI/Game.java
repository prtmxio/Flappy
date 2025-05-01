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
    boolean game_over = false;
    double score = 0;

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
        bg = new Background();
        obs = new Obstacle_pipe();
        flappy = new Flappy_bird();
        gp = new Game_panel(this);
        gf = new Game_frame(gp);
        keyH = new KeyHandler();
        gp.addKeyListener(keyH);
    }

    public void update() {
//        obs.update();
//        flappy.update();
        if (!game_over) {
            obs.update();
            flappy.update();
            game_over();  // ✅ Check for game over condition
        } else {
            if(keyH.restart){
                restart_game();
            }
        }

    }


    public void render(Graphics g){
        bg.render(g);
        obs.render(g);
        flappy.render(g);
        score_card(g);
//        if (game_over) {
//            g.setColor(Color.RED);
//            g.setFont(new Font("Arial", Font.BOLD, 48));
//            g.drawString("Game Over", Game_panel.width / 2 - 120, Game_panel.height / 2);
//        }
    }

    public void game_over(){
        if (flappy.y > Game_panel.height || flappy.y + flappy.height < 0 || check_collision()) {
            game_over = true;
        }

    }

    public void restart_game(){
        init_classes();
        score = 0;
        game_over = false;
    }

    public void score_card(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        if(game_over){
            g.drawString("Game Over : " + String.valueOf((int)(score)), 10, 35);
        } else {
            g.drawString(String.valueOf((int)(score)), 10, 35);
        }
    }

    public boolean check_collision() {
        Rectangle birdBounds = flappy.getBounds();

        for (int i = 0; i < obs.pipes_top.size(); i++) {
            Pipe top = obs.pipes_top.get(i);
            Pipe bottom = obs.pipes_bottom.get(i);

            Rectangle topBounds = new Rectangle(top.px, top.py, top.pw, top.ph);
            Rectangle bottomBounds = new Rectangle(bottom.px, bottom.py, bottom.pw, bottom.ph);

            // 💥 Check collision
            if (birdBounds.intersects(topBounds) || birdBounds.intersects(bottomBounds)) {
                return true;
            }

            // ✅ Check if passed and update score
            if (!top.pipe_passed && flappy.x > top.px + top.pw) {
                score += 1;
                top.pipe_passed = true;
                bottom.pipe_passed = true; // mark both as passed
            }
        }

        return false;
    }


//    public boolean check_collision(){
//        Rectangle birdBounds = flappy.getBounds();
//
//        // Collision with top pipes
//        for (Pipe top : obs.pipes_top) {
//            Rectangle topBounds = new Rectangle(top.px, top.py, top.pw, top.ph);
//            if (birdBounds.intersects(topBounds)) {
//                game_over = true;
//                obs.pipe_passed = false;
//                return true;
//            } else {
//                score += 0.5;
//            }
//        }
//
//        // Collision with bottom pipes
//        for (Pipe bottom : obs.pipes_bottom) {
//            Rectangle bottomBounds = new Rectangle(bottom.px, bottom.py, bottom.pw, bottom.ph);
//            if (birdBounds.intersects(bottomBounds)) {
//                game_over = true;
//                obs.pipe_passed = false;
//                return true;
//            } else {
//                score += 0.5;
//            }
//        }
//        return false;
//    }



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
