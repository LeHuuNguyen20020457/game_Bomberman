package uet.oop.bomberman;

import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Entities.MobileEntity.enemy.*;
import uet.oop.bomberman.Entities.StaticEntity.Brick;
import uet.oop.bomberman.Util.Message;
import uet.oop.bomberman.Entities.Bomb.Bomb;
import uet.oop.bomberman.Entities.Bomb.FlameSection;
import uet.oop.bomberman.Entities.MobileEntity.Bomber;
import uet.oop.bomberman.Entities.MobileEntity.Character;

import uet.oop.bomberman.Graphic.Sprite;
import uet.oop.bomberman.Input.Keyboard;
import uet.oop.bomberman.Input.Map;
import uet.oop.bomberman.Util.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class Board {
    private Entity[] entities;
    private List<Character> characters = new ArrayList<>();
    private List<Bomb> bombs = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    public int highScore = (Integer.parseInt(Map.Read()));
    public int currentTime = 0;
    public int currentPoint = 0;
    private int level = 1;

    private Game game;
    private Map map;
    private Keyboard keyboardInput;

    public int[] pixels = new int[Utils.WIDTH * Utils.HEIGHT];
    private Font font;
    private FontMetrics fontMetrics;
    private int screen = 1;

    public Board(Game game, Keyboard keyboardInput) {
        this.game = game;
        this.keyboardInput = keyboardInput;

        this.loadLevel();
    }

    public void endGame() {
        if (currentPoint > highScore) {
            Map.write(currentPoint);
        }

        screen = 1;
        game.resetDelay();
        game.pauseGame();
    }

    public void nextLevel() {
        level++;
        loadLevel();
    }

    public void newGame() {
        level = 1;
        reset();
        loadLevel();
    }

    public void restartLevel() {
        loadLevel();
    }

    public void loadLevel() {
        currentTime += 200;
        screen = 2;
        game.resetDelay();
        game.pauseGame();
        characters.clear();
        bombs.clear();
        messages.clear();

        try {
            map = new Map(this);
            entities = new Entity[Map.getHeight() * Map.getWidth()];
            map.createEntities();
        } catch (IOException e) {
            endGame();
        }
    }

    public void render() {
        if (game.isGamePaused()) return;

        renderEntities();
        renderBombs();
        renderCharacters();
    }

    private void renderEntities() {
        for (Entity entity : entities) entity.render(this);
    }

    private void renderCharacters() {
        for (Character character : characters) character.render(this);
    }

    private void renderBombs() {
        for (Bomb bomb : bombs) bomb.render(this);
    }

    public void renderMessages(Graphics graphics) {
        for (Message message : messages) {
            graphics.setFont(new Font("Arial", Font.PLAIN, message.size));
            graphics.setColor(message.color);
            graphics.drawString(message.message, message.x, message.y);
        }
    }

    public void renderEntity(int xp, int yp, Sprite sprite) {
        int yPixel;
        int xPixel;
        int pixel;
        for (int y = 0; y < Utils.TILES_SIZE; y++) {
            yPixel = y + yp;
            for (int x = 0; x < Utils.TILES_SIZE; x++) {
                xPixel = x + xp;
                pixel = sprite.getPixel(x + y * Utils.TILES_SIZE);
                if (pixel != Utils.transparentColor) {
                    pixels[xPixel + yPixel * Utils.WIDTH] = pixel;
                }
            }
        }
    }

    public void renderBothEntity(int xp, int yp, Brick brick) {
        int yPixel;
        int xPixel;
        int pixel;
        for (int y = 0; y < Utils.TILES_SIZE; y++) {
            yPixel = y + yp;
            for (int x = 0; x < Utils.TILES_SIZE; x++) {
                xPixel = x + xp;
                pixel = brick.getSprite().getPixel(x + y * Utils.TILES_SIZE);
                if (pixel != Utils.transparentColor) {
                    pixels[xPixel + yPixel * Utils.WIDTH] = pixel;
                } else {
                    pixels[xPixel + yPixel * Utils.WIDTH] = brick.spriteBelow.getPixel(x + y * Utils.TILES_SIZE);
                }
            }
        }
    }

    public void update() {
        if (currentTime <= 0 || keyboardInput.end) {
            endGame();
        }
        if (keyboardInput.resume) {
            gameResume();
        }

        if (keyboardInput.restart) {
            newGame();
        }
        if (keyboardInput.pause) {
            gamePause();
            return;
        }
        if (!game.isGamePaused()) {
            updateEntities();
            updateBombs();
            updateCharacters();
            updateMessages();
        }

        for (int index = 0; index < characters.size(); index++) {
            Character character = characters.get(index);
            if (character.isRemoved()) {
                int x = Utils.coordinateToPixel(character.getXTile());
                int y = Utils.coordinateToPixel(character.getYTile());
                characters.remove(index);

                if (character instanceof Kondoria) {
                    characters.add (new Oneal(x,y,this));
                }
            }
        }
    }

    private void updateEntities() {
        for (Entity entity : entities) {
            entity.update();
        }
    }

    private void updateCharacters() {
        Iterator<Character> characterIterator = characters.iterator();

        while (characterIterator.hasNext() && !game.isGamePaused()) {
            characterIterator.next().update();
        }
    }

    private void updateBombs() {
        for (Bomb bomb : bombs) {
            bomb.update();
        }
    }

    private void updateMessages() {
        Message message;
        for (int index = 0; index < messages.size(); index++) {
            message = messages.get(index);

            if (message.duration > 0) {
                message.duration--;
            }
            else {
                messages.remove(index);
            }
        }
    }

    public int updateCurrentTime() {
        if (game.isGamePaused()) {
            return this.currentTime;
        } else {
            return this.currentTime--;
        }
    }

    public void gamePause() {
        game.music.stop();
        game.resetDelay();
        if (screen <= 0) {
            screen = 3;
        }
        game.pauseGame();
    }

    public void gameResume() {
        game.music.run();
        game.resetDelay();
        screen = -1;
        game.run();
    }

    public void drawScreen(Graphics g) {
        if (screen == 1) {
            endGameScreen(g);
        } else if (screen == 2) {
            changeLevelScreen(g);
        } else if (screen == 3) {
            pauseScreen(g);
        }
    }

    public void endGameScreen(Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, Utils.REALWITDH, Utils.REALHEIGHT);

        font = new Font("Arial", Font.PLAIN, 20 * Utils.SCALE);
        graphics.setFont(font);
        graphics.setColor(Color.white);
        if (level == 4) {
            drawString(" You Win ", Utils.REALWITDH, Utils.REALHEIGHT, graphics);
        } else {
            drawString(" Game Over ", Utils.REALWITDH, Utils.REALHEIGHT, graphics);
        }
        font = new Font("Arial", Font.PLAIN, 15 * Utils.SCALE);
        graphics.setFont(font);
        drawString("POINTS : " + currentPoint, Utils.REALWITDH, Utils.REALHEIGHT + (Utils.TILES_SIZE * 2) * Utils.SCALE, graphics);
        font = new Font("Arial", Font.PLAIN, 10 * Utils.SCALE);
        graphics.setFont(font);
        drawString("Press R to restart", Utils.REALWITDH, Utils.REALHEIGHT + (Utils.TILES_SIZE * 3) * Utils.SCALE, graphics);
    }

    public void changeLevelScreen(Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, Utils.REALWITDH, Utils.REALHEIGHT);

        font = new Font("Arial", Font.PLAIN, 20 * Utils.SCALE);
        graphics.setFont(font);
        graphics.setColor(Color.white);
        drawString("LEVEL " + level, Utils.REALWITDH, Utils.REALHEIGHT - (Utils.TILES_SIZE * 2) * Utils.SCALE, graphics);
        font = new Font("Arial", Font.PLAIN, 15 * Utils.SCALE);
        graphics.setFont(font);
        drawString("POINTS : " + currentPoint, Utils.REALWITDH, Utils.REALHEIGHT + (Utils.TILES_SIZE * 2) * Utils.SCALE, graphics);

    }

    public void pauseScreen(Graphics graphics) {
        font = new Font("Arial", Font.PLAIN, 20 * Utils.SCALE);
        graphics.setFont(font);
        graphics.setColor(Color.white);
        drawString("PAUSED", Utils.REALWITDH, Utils.REALHEIGHT - (Utils.TILES_SIZE * 2) * Utils.SCALE, graphics);
        font = new Font("Arial", Font.PLAIN, 10 * Utils.SCALE);
        graphics.setFont(font);
        drawString("Press O to continue", Utils.REALWITDH, Utils.REALHEIGHT + (Utils.TILES_SIZE * 2) * Utils.SCALE, graphics);
    }

    public void drawString(String string, int width, int height, Graphics graphics) {
        fontMetrics = graphics.getFontMetrics();
        int xString = (width - fontMetrics.stringWidth(string)) / 2;
        int yString = (fontMetrics.getAscent() + (height - (fontMetrics.getAscent() + fontMetrics.getDescent())) / 2);
        graphics.drawString(string, xString, yString);
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }

    private void reset() {
        currentTime = 0;
        currentPoint = 0;
        Bomber.powerUps.clear();
        Game.resetPowerUp();
    }

    public Entity getEntity(double xEntity, double yEntity, Character character) {
        int xEntityInt = (int) xEntity;
        int yEntityInt = (int) yEntity;

        if (getFlameSectionAt(xEntityInt, yEntityInt) != null) {
            return getFlameSectionAt(xEntityInt, yEntityInt);
        }

        if (getBombAt(xEntity, yEntity) != null) {
            return getBombAt(xEntity, yEntity);
        }

        if (getCharacterAt(xEntityInt, yEntityInt, character) != null) {
            return getCharacterAt(xEntityInt, yEntityInt, character);
        }

        return getEntityAt(xEntityInt, yEntityInt);
    }


    public Bomb getBombAt(double xBomb, double yBomb) {
        Iterator<Bomb> bombIterator = bombs.iterator();
        Bomb bomb;
        while (bombIterator.hasNext()) {
            bomb = bombIterator.next();
            if (bomb.getX() == (int) xBomb && bomb.getY() == (int) yBomb)
                return bomb;
        }
        return null;
    }

    public Bomber getBomber() {
        for (Character character : characters) {
            if (character instanceof Bomber) {
                return (Bomber) character;
            }
        }
        return null;
    }

    public Character getCharacterAt(int xCharacter, int yCharacter, Character otherCharacter) {
        for (Character character : characters) {
            if (character == otherCharacter) {
                continue;
            }
            if (character.getXTile() == xCharacter && character.getYTile() == yCharacter) {
                return character;
            }
        }
        return null;
    }

    public FlameSection getFlameSectionAt(int x, int y) {
        Iterator<Bomb> bombIterator = bombs.iterator();
        Bomb bomb;
        while (bombIterator.hasNext()) {
            bomb = bombIterator.next();
            FlameSection flameSection = bomb.flameAt(x, y);
            if (flameSection != null) {
                return flameSection;
            }
        }
        return null;
    }

    public Entity getEntityAt(double x, double y) {
        return entities[(int) x + (int) y * Map.getWidth()];
    }


    public boolean noEnemy() {
        for (Character character : characters) {
            if (character instanceof Enemy) {
                return false;
            }
        }
        return true;
    }


    public void addEntity(int position, Entity e) {
        entities[position] = e;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public int getLives() {
        return game.getLive();
    }

    public Keyboard getKeyboard() {
        return keyboardInput;
    }

    public int getLevel() {
        return level;
    }

    public void addPoints(int points) {
        this.currentPoint += points;
    }

    public int getScreen() {
        return screen;
    }

    public void setScreen(int screen) {
        this.screen = screen;
    }
}
