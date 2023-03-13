package uet.oop.bomberman.Entities.MobileEntity.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.MobileEntity.enemy.ai.AIRandom;
import uet.oop.bomberman.Graphic.Sprite;

public class Balloon extends Enemy {


    public Balloon(int x, int y, Board board) {
        super(x, y, board, 1.0, 100);
        currentSprite = Sprite.balloom_left1;
        ai = new AIRandom();
        direction = 0;
    }

    @Override
    public void calculateMove() {
        int xMove = 0, yMove = 0;

        if (steps <= 0) {
            if (ai.calculateDirection() <= 1) {
                direction = 0;
            } else {
                direction = 2;
            }
            steps = maxSteps;
        }

        if (direction == 0){
            yMove--;
        }
        if (direction == 2){
            yMove++;
        }

        if (canMove(xMove, yMove)) {
            steps -= 1 + restStep;
            move(xMove * speed, yMove * speed);
            moving = true;
        } else {
            steps = 0;
            moving = false;
        }
    }

    @Override
    public void render(Board board) {

        if (alive)
            chooseCurrentSprite();
        else {
            if (timeAfterKill > 0) {
                currentSprite = Sprite.balloom_dead;
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
                currentSprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animateFrame, 20);
                break;
            case 2:
                currentSprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animateFrame, 20);
                break;
        }
    }
}
