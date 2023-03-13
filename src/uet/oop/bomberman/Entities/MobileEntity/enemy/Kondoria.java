package uet.oop.bomberman.Entities.MobileEntity.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.MobileEntity.enemy.ai.AIBfs;

import uet.oop.bomberman.Graphic.Sprite;


public class Kondoria extends Enemy {

    public Kondoria(int x, int y, Board board) {
        super(x, y, board, 0.7, 300);

        currentSprite = Sprite.kondoria_right1;

        ai = new AIBfs(this.board.getBomber(), this, board);
        direction = ai.calculateDirection();
    }


    @Override
    public void render(Board board) {

        if (alive)
            chooseCurrentSprite();
        else {
            if (timeAfterKill > 0) {
                currentSprite = Sprite.kondoria_dead;
                animateFrame = 0;
            } else {
                currentSprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animateFrame, 60);
            }

        }
        int xRender = (int) x;
        int yRender = (int) y - 16;
        board.renderEntity(xRender, yRender , currentSprite);
    }

    @Override
    protected void chooseCurrentSprite() {
        switch (direction) {
            case 0:
            case 1:
                if (moving) {
                    currentSprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animateFrame, 20);
                } else {
                    currentSprite = Sprite.kondoria_left1;
                }
                break;
            case 2:
            case 3:
                if (moving) {
                    currentSprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animateFrame, 20);
                } else {
                    currentSprite = Sprite.kondoria_left1;
                }
                break;
        }
    }
}