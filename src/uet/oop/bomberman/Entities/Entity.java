package uet.oop.bomberman.Entities;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Graphic.Sprite;
import uet.oop.bomberman.Util.Utils;

public abstract class Entity {

    protected double x;
    protected double y;
    protected boolean removed = false;
    protected Sprite currentSprite;

    public abstract void update();

    public abstract void render(Board board);

    public abstract boolean collide(Entity e);

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public Sprite getSprite() {
        return currentSprite;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getXTile() {
        return Utils.pixelToCoordinate(x + 8);
    }

    public int getYTile() {
        return Utils.pixelToCoordinate(y - 8);
    }
}
