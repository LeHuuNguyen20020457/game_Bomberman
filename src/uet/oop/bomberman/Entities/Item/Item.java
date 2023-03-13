package uet.oop.bomberman.Entities.Item;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Util.Message;
import uet.oop.bomberman.Entities.MobileEntity.Bomber;
import uet.oop.bomberman.Entities.StaticEntity.Grass;
import uet.oop.bomberman.Graphic.Sprite;
import uet.oop.bomberman.Util.Utils;

import java.awt.*;

public abstract class Item extends Entity {
    protected boolean active = false;
    protected Sprite spriteBelow = Sprite.grass;
    protected Board board;

    public Item(int x, int y, Sprite sprite, Board board) {
        this.x = x;
        this.y = y;
        this.currentSprite = sprite;
        this.board = board;
    }

    public abstract void init();

    @Override
    public void render(Board board) {
        int xRender = Utils.coordinateToPixel(x);
        int yRender = Utils.coordinateToPixel(y);
        board.renderEntity(xRender, yRender, currentSprite);
    }

    @Override
    public void update() {}

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            ((Bomber) e).addPowerUp(this);
            Message msg = new Message("PowerUp", Utils.getXMessage((int) e.getX()),Utils.getXMessage((int) e.getY()), 1, Color.WHITE, 18);
            board.addMessage(msg);
            remove();
            board.addEntity((int) x + (int) y * uet.oop.bomberman.Input.Map.getWidth(), new Grass((int) x, (int) y, spriteBelow));
            return true;
        } else {
            return false;
        }
    }
}
