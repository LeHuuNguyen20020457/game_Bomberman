package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.FileLevelLoader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    private static final int WIDTH = 31;
    private static final int HEIGHT = 13;
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    //private List<Entity> stillObjects = new ArrayList<>();
    public char[][] map = new char[WIDTH][HEIGHT];

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Tao Canvas
        canvas = new Canvas( Sprite.SCALED_SIZE*WIDTH,  Sprite.SCALED_SIZE*HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        FileLevelLoader.createMap();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

//    public void LoadFileLevel() throws IOException {
//
//        List<String> list = new ArrayList<>();
//        String url = "res\\levels\\Level" + 1 + ".txt";
//        File file = new File(url);
//        InputStream inputStream = new FileInputStream(file);
//        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//        BufferedReader reader = new BufferedReader(inputStreamReader);
//        String line = "";
//        while((line = reader.readLine()) != null){
//            list.add(line);
//        }
//        String[]  s = list.get(0).trim().split(" ");
//        int level_map = Integer.parseInt(s[0]);
//        int height_map = Integer.parseInt(s[1]);
//        int width_map =Integer.parseInt(s[2]);
//        for(int i=0; i < height_map; i++)
//        {
//            for(int j=0; j < width_map; j++)
//            {
//                map[j][i] = list.get(i+1).charAt(j);
//            }
//        }
//    }
//
//    public void createMap() throws IOException {
//        LoadFileLevel();
//        for (int i = 0; i < WIDTH; i++) {
//            for (int j = 0; j < HEIGHT; j++) {
//                Entity object1 = new Grass(i, j , Sprite.grass.getFxImage());
//                stillObjects.add(object1);
//                switch (map[i][j]) {
//                    case ' ':
//                        Entity object = new Grass(i, j, Sprite.grass.getFxImage());
//                        stillObjects.add(object);
//                        break;
//                    case '#':
//                        Entity object2 = new Wall(i, j, Sprite.wall.getFxImage());
//                        stillObjects.add(object2);
//                        break;
//                    case '*':
//                        Entity object3 = new Wall(i, j, Sprite.brick.getFxImage());
//                        stillObjects.add(object3);
//
//                        break;
//                }
//
//            }
//        }
//    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        FileLevelLoader.getStillObjects().forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

}
