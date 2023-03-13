package uet.oop.bomberman.Entities.StaticEntity;


import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.AnimatedEntity;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Entities.Bomb.Flame;
import uet.oop.bomberman.Entities.Item.BombItem;
import uet.oop.bomberman.Entities.Item.FlameItem;
import uet.oop.bomberman.Entities.Item.LiveItem;
import uet.oop.bomberman.Entities.Item.SpeedItem;
import uet.oop.bomberman.Entities.MobileEntity.enemy.Oneal;
import uet.oop.bomberman.Graphic.Sprite;
import uet.oop.bomberman.Util.Utils;

import java.util.ArrayList;

public class Brick extends AnimatedEntity {
    private boolean destroyed = false;
    private int disappearTime = 30;
    public Sprite spriteBelow = Sprite.grass;
    private Board board;
    public static ArrayList<Integer> destroyedBrickX = new ArrayList();
    public static ArrayList<Integer> destroyedBrickY = new ArrayList();


    public Brick(int x, int y, Sprite sprite, Sprite spriteBelow, Board board) {
        this.x = x;
        this.y = y;
        this.currentSprite = sprite;
        this.board = board;
        this.spriteBelow = spriteBelow;
    }

    public Brick(int x, int y, Sprite sprite, Board board) {
        this.x = x;
        this.y = y;
        this.currentSprite = sprite;
        this.board = board;
    }

    private Entity spawnBelowEntity() {
        if (Sprite.powerup_flames.equals(spriteBelow)) {
            return new FlameItem((int) x, (int) y, spriteBelow, board);
        } else if (Sprite.powerup_bombs.equals(spriteBelow)) {
            return new BombItem((int) x, (int) y, spriteBelow, board);
        } else if (Sprite.powerup_detonator.equals(spriteBelow)) {
            return new LiveItem((int) x, (int) y, spriteBelow, board);
        } else if (Sprite.powerup_speed.equals(spriteBelow)) {
            return new SpeedItem((int) x, (int) y, spriteBelow, board);
        } else if (Sprite.portal.equals(spriteBelow)) {
            return new Portal((int) x, (int) y, spriteBelow, board);
        }
        return new Grass((int) x, (int) y, spriteBelow);

    }

    @Override
    public void update() {
        if (destroyed) {
            animate();
            spriteBelow = spawnBelowEntity().getSprite();
            if (disappearTime > 0) {
                disappearTime--;
            } else {
                remove();
                board.addEntity((int) x + (int) y * uet.oop.bomberman.Input.Map.getWidth(), spawnBelowEntity());
            }
        }
    }

    @Override
    public void render(Board board) {
        int xRender = Utils.coordinateToPixel(this.x);
        int yRender = Utils.coordinateToPixel(this.y);

        if (destroyed) {
            currentSprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animateFrame, 40);
            board.renderBothEntity(xRender, yRender, this);
        } else {
            board.renderEntity(xRender, yRender, currentSprite);
        }
    }


    public void destroy() {
        destroyed = true;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Oneal) {
            return true;
        }
        if (e instanceof Flame) {
            destroyedBrickX.add(getXTile());
            destroyedBrickY.add(getYTile());
            destroy();
        }
        return false;
    }
}
