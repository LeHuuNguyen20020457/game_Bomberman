package uet.oop.bomberman.Entities.StaticEntity;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Entities.MobileEntity.Bomber;
import uet.oop.bomberman.Graphic.Sprite;


public class Portal extends StaticEntity {
    public Board board;

    public Portal(int x, int y, Sprite sprite, Board board) {
        super(x, y, sprite);
        this.board = board;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            if (!board.noEnemy()) {
                return false;
            }
            if (e.getXTile() == getX() && e.getYTile() == getY()) {
                if (board.noEnemy()) {
                    board.nextLevel();
                }
            }
        }
        return true;
    }
}
