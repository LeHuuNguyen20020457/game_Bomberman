package uet.oop.bomberman.Entities.Item;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Graphic.Sprite;


public class BombItem extends Item {
	public BombItem(int x, int y, Sprite sprite, Board board) {
		super(x, y, sprite, board);
	}


    @Override
    public void init() {
       active = true;
       Game.addBombNum(1);
    }
}
