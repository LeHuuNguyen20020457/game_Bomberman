package uet.oop.bomberman.Entities.Bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.AnimatedEntity;
import uet.oop.bomberman.Entities.Entity;

import uet.oop.bomberman.Entities.MobileEntity.Character;
import uet.oop.bomberman.Entities.StaticEntity.Brick;

public class Flame extends AnimatedEntity {

    protected Board board;
    protected int direction;
    private int radius;
    protected FlameSection[] flameSections;


    public Flame(int x, int y, int direction, int radius, Board board) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.radius = radius;
        this.board = board;
        createFlameSegments();
    }


    private void createFlameSegments() {
        flameSections = new FlameSection[distance()];
        boolean lastSection;

        int x = (int) this.x;
        int y = (int) this.y;
        for (int i = 0; i < flameSections.length; i++) {
            lastSection = i == flameSections.length - 1;

            switch (direction) {
                case 0:
                    y--;
                    break;
                case 1:
                    x++;
                    break;
                case 2:
                    y++;
                    break;
                case 3:
                    x--;
                    break;
            }
            flameSections[i] = new FlameSection(x, y, direction, lastSection);
        }
    }


    private int distance() {
        int bombRadius = 0;
        int xFlame = (int) this.x;
        int yFlame = (int) this.y;

        while (bombRadius < this.radius) {
            if (direction == 0){
                yFlame--;
            }
            if (direction == 1){
                xFlame++;
            }
            if (direction == 2){
                yFlame++;
            }
            if (direction == 3){
                xFlame--;
            }

            Entity a = board.getEntity(xFlame, yFlame, null);
            if (!a.collide(this)) {
                break;
            }
            bombRadius++;
        }
        return bombRadius;
    }

    public FlameSection flameSegmentAt(int x, int y) {
        for (FlameSection flameSection : this.flameSections) {
            if (flameSection.getX() == x && flameSection.getY() == y)
                return flameSection;
        }
        return null;
    }

    @Override
    public void update() {
        for (FlameSection flameSection : this.flameSections) {
            flameSection.update();
        }
    }

    @Override
    public void render(Board board) {
        for (FlameSection flameSection : this.flameSections) {
            flameSection.render(board);
        }
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Brick) {
            ((Brick) e).destroy();
        }
        if (e instanceof Character) {
            ((Character) e).kill();
            return false;
        }
        return true;
    }
}
