/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uet.oop.bomberman.Entities.MobileEntity.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.MobileEntity.enemy.ai.AIRandom;
import uet.oop.bomberman.Graphic.Sprite;

public class Doll extends Enemy {
    public Doll(int x, int y, Board board) {
        super(x, y, board, 1.0, 100);

        currentSprite = Sprite.doll_right1;

        ai = new AIRandom();
        direction = 1;
    }

    @Override
    public void calculateMove() {
        int xMove = 0, yMove = 0;

        if (steps <= 0) {
            if (ai.calculateDirection() <= 1) {
                direction = 1;
            } else {
                direction = 3;
            }

            steps = maxSteps;
        }

        if (direction == 1){
            xMove++;
        }
        if (direction == 3){
            xMove--;
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
                currentSprite = Sprite.doll_dead;
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
            case 1:
                if (moving) {
                    currentSprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animateFrame, 20);
                } else {
                    currentSprite = Sprite.doll_left1;
                }
                break;
            case 3:
                if (moving) {
                    currentSprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animateFrame, 20);
                } else {
                    currentSprite = Sprite.doll_left1;
                }
                break;
        }
    }

}
