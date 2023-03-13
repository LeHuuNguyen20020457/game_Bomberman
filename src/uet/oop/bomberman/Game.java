package uet.oop.bomberman;

import uet.oop.bomberman.Entities.MobileEntity.Bomber;
import uet.oop.bomberman.Input.Keyboard;
import uet.oop.bomberman.Gui.Frame;
import uet.oop.bomberman.Output.Music;
import uet.oop.bomberman.Util.Utils;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas {
    private Keyboard keyboardInput;
    private static Board board;
    private Frame gameFrame;

    private static int BombNum = 1;
    private static int BombLevel = 1;
    private static double BomberSpeed = 1.0;
    private static int lives = 3;

    private int delayScreen = 3;
    private boolean gameRun = false;
    private boolean gamePause = true;

    private BufferedImage bufferedImage = new BufferedImage(Utils.WIDTH, Utils.HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
    private BufferStrategy bufferStrategy = getBufferStrategy();
    private Graphics graphics;
    public Music music;

    public Game(Frame gameFrame) {
        this.gameFrame = gameFrame;
        keyboardInput = new Keyboard();
        board = new Board(this, keyboardInput);
        music = new Music();
        addKeyListener(keyboardInput);
    }

    public static void main(String[] args) {
        new Frame();
    }

    private void renderGame() {
        bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }
        board.clear();
        board.render();
        System.arraycopy(board.pixels, 0, pixels, 0, pixels.length);
        graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);
        board.renderMessages(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }

    private void renderScreen() {
        bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }
        board.clear();
        graphics = bufferStrategy.getDrawGraphics();
        board.drawScreen(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }

    private void update() {
        keyboardInput.update();
        board.update();
    }

    public void start() {
        gameRun = true;

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        long now;
        requestFocus();
        music.run();
        while (gameRun) {
            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta--;
            }

            if (gamePause) {
                if (delayScreen <= 0) {
                    board.setScreen(-1);
                    gamePause = false;
                }
                renderScreen();
            } else {
                renderGame();
            }

            if (System.currentTimeMillis() - timer > 1000) {
                gameFrame.setTime(board.updateCurrentTime());
                gameFrame.setPoint(board.currentPoint);
                gameFrame.setLive(getLive());
                timer += 1000;
                if (board.getScreen() == 2) {
                    delayScreen--;
                }
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public static double getBomberSpeed() {
        return BomberSpeed;
    }

    public static int getBombNum() {
        return BombNum;
    }

    public static int getBombLevel() {
        return BombLevel;
    }

    public static void addBomberSpeed(double bomberSpeed) {
        BomberSpeed += bomberSpeed;
    }

    public static void addBombLevel(int bombLevel) {
        BombLevel += bombLevel;
    }

    public static void addBombNum(int bombNum) {
        BombNum += bombNum;
    }

    public int getLive() {return lives;}

    public void resetDelay() {
        delayScreen = 3;
    }

    public static void addLive(int live) {
        lives += live;
    }

    public boolean isGamePaused() {
        return gamePause;
    }

    public void pauseGame() {
        gamePause = true;
    }

    public void run() {
        gameRun = true;
        gamePause = false;
    }

    public static void resetPowerUp() {
        Bomber.powerUps.clear();
        BomberSpeed = 1.0;
        BombLevel = 1;
        BombNum = 1;
    }
}
