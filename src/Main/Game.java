package Main;

import entities.Player;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameLoop;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    private LevelManager levelManager;

    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float scale = 2f;
    public final static int TILES_IN_WIDTH = 26; // how many tiles there are going horizontally
    public final static int TILES_IN_HEIGHT = 14; // how many tiles there are going vertically
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * scale);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        initializeClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initializeClasses()
    {
        levelManager = new LevelManager(this);
        player = new Player(200, 200, 70, 90);
        player.loadLvlData(levelManager.getLevel().getLvlData());
    }

    public void startGameLoop()
    {
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void update()
    {
        player.update();
        levelManager.update();
    }

    public void render(Graphics g)
    {
        levelManager.draw(g);
        player.render(g);
    }
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();
        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        while (true)
        {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1)
            {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1)
            {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000)
            {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " UPDATES: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost()
    {
        player.resetDirectionBooleans();
    }
    public Player getPlayer()
    {
        return player;
    }


}
