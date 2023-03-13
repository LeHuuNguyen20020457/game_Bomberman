package uet.oop.bomberman.Entities.Item;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;

import uet.oop.bomberman.Graphic.Sprite;


public class SpeedItem extends Item {
    public SpeedItem(int x, int y, Sprite sprite, Board board) {
        super(x, y, sprite, board);
    }


    @Override
    public void init() {
        active = true;
        Game.addBomberSpeed(0.2);
    }
}
