package uet.oop.bomberman.Entities.MobileEntity;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.AnimatedEntity;
import uet.oop.bomberman.Util.Utils;


public abstract class Character extends AnimatedEntity {
    public Board board;
    protected int direction;
    protected int timeAfterKill = 40;
    protected boolean alive = true;
    protected boolean moving = false;

    public Character(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(Board board);

    protected abstract void chooseCurrentSprite();

    public abstract void kill();

    protected abstract void afterKill();

    protected abstract void calculateMove();

    protected abstract boolean canMove(double xCanMove, double yCanMove);

    protected abstract void move(double xMove, double yMove);
}
