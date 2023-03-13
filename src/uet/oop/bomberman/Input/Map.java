package uet.oop.bomberman.Input;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.MobileEntity.Bomber;
import uet.oop.bomberman.Entities.MobileEntity.enemy.Balloon;
import uet.oop.bomberman.Entities.MobileEntity.enemy.Doll;
import uet.oop.bomberman.Entities.MobileEntity.enemy.Kondoria;
import uet.oop.bomberman.Entities.MobileEntity.enemy.Oneal;
import uet.oop.bomberman.Entities.StaticEntity.Grass;
import uet.oop.bomberman.Entities.StaticEntity.Wall;
import uet.oop.bomberman.Entities.StaticEntity.Brick;
import uet.oop.bomberman.Graphic.Sprite;
import uet.oop.bomberman.Util.Utils;


public class Map {
    public static char[][] map;
    public static int width;
    public static int height;
    private int level;
    private Board board;

    public Map(Board board) throws IOException {
        this.board = board;
        loadLevel(this.board.getLevel());
    }

    public void loadLevel(int level) throws IOException {
        try {
            URL mapPath = Map.class.getResource("/levels/Level" + level + ".txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mapPath.openStream()));

            String mapData = bufferedReader.readLine();
            this.level = Integer.parseInt(mapData.substring(0, 1));
            height = Integer.parseInt(mapData.substring(2, 4));
            width = Integer.parseInt(mapData.substring(5, 7));

            map = new char[height][width];
            for (int i = 0; i < height; i++) {
                mapData = bufferedReader.readLine();
                for (int j = 0; j < width; j++) {
                    map[i][j] = mapData.charAt(j);
                }
            }
            bufferedReader.close();

        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void createEntities() {
        int position;
        char Symbol;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                position = x + y * width;
                Symbol = map[y][x];

                switch (Symbol) {
                    case '#':
                        board.addEntity(position, new Wall(x, y, Sprite.wall));
                        break;
                    case '*':
                        board.addEntity(position, new Brick(x, y, Sprite.brick, board));
                        break;
                    case 'f':
                        board.addEntity(position, new Brick(x, y, Sprite.brick, Sprite.powerup_flames, board));
                        break;
                    case 'l':
                        board.addEntity(position, new Brick(x, y, Sprite.brick, Sprite.powerup_detonator, board));
                        break;
                    case 'b':
                        board.addEntity(position, new Brick(x, y, Sprite.brick, Sprite.powerup_bombs, board));
                        break;
                    case 's':
                        board.addEntity(position, new Brick(x, y, Sprite.brick, Sprite.powerup_speed, board));
                        break;
                    case 'x':
                        board.addEntity(position, new Brick(x, y, Sprite.brick, Sprite.portal, board));
                        break;
                    case 'p':
                        board.addEntity(position, new Grass(x, y, Sprite.grass));
                        board.addCharacter(new Bomber(Utils.coordinateToPixel(x), Utils.coordinateToPixel(y + 1), board));
                        break;
                    case '1':
                        board.addCharacter(new Balloon(Utils.coordinateToPixel(x), Utils.coordinateToPixel(y) + Utils.TILES_SIZE, board));
                        board.addEntity(position, new Grass(x, y, Sprite.grass));
                        break;
                    case '2':
                        board.addCharacter(new Oneal(Utils.coordinateToPixel(x), Utils.coordinateToPixel(y) + Utils.TILES_SIZE, board));
                        board.addEntity(position, new Grass(x, y, Sprite.grass));
                        break;
                    case '3':
                        board.addCharacter(new Doll(Utils.coordinateToPixel(x), Utils.coordinateToPixel(y) + Utils.TILES_SIZE, board));
                        board.addEntity(position, new Grass(x, y, Sprite.grass));
                        break;
                    case '4':
                        board.addCharacter(new Kondoria(Utils.coordinateToPixel(x), Utils.coordinateToPixel(y) + Utils.TILES_SIZE, board));
                        board.addEntity(position, new Grass(x, y, Sprite.grass));
                        break;
                    default:
                        board.addEntity(position, new Grass(x, y, Sprite.grass));
                        break;
                }
            }
        }
    }

    public static String Read() {

        String highscore = null;
        File highscoreFile = new File("res/levels/highscore.txt");

        try {
            Scanner scanner = new Scanner(highscoreFile);
            while (scanner.hasNext()) {
                highscore = scanner.nextLine();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return highscore;
    }

    public static void write(int point) {
        File highscoreFile = new File("res/levels/highscore.txt");

        try (FileWriter fileWriter = new FileWriter(highscoreFile, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            printWriter.println(point);
        } catch (Exception ignored) {
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}
