package uet.oop.bomberman.Entities.StaticEntity;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Graphic.Sprite;
import uet.oop.bomberman.Util.Utils;

public abstract class StaticEntity extends Entity {

    public StaticEntity(int x, int y, Sprite currentSprite) {
        this.x = x;
        this.y = y;
        this.currentSprite = currentSprite;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    @Override
    public void render(Board board) {
        int xRender = Utils.coordinateToPixel(x);
        int yRender =  Utils.coordinateToPixel(y);
        board.renderEntity(xRender,yRender, currentSprite);
    }

    @Override
    public void update() {
    }
}
