package uet.oop.bomberman.Entities.MobileEntity.enemy;


import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.Bomb.Flame;
import uet.oop.bomberman.Entities.Bomb.FlameSection;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Entities.MobileEntity.Bomber;
import uet.oop.bomberman.Entities.MobileEntity.enemy.ai.AILow;
import uet.oop.bomberman.Graphic.Sprite;

public class Oneal extends Enemy {


    public Oneal(int x, int y, Board board) {
        super(x, y, board,  0.8 , 200);

        currentSprite = Sprite.oneal_left1;
        ai = new AILow(this.board.getBomber(), this);
        direction = ai.calculateDirection();

    }

    @Override
    protected void chooseCurrentSprite() {
        switch (direction) {
            case 0:
            case 1:
                if (moving) {
                    currentSprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animateFrame, 30);
                } else {
                    currentSprite = Sprite.oneal_left1;
                }
                break;
            case 2:
            case 3:
                if (moving) {
                    currentSprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animateFrame, 30);
                } else {
                    currentSprite = Sprite.oneal_left1;
                }
                break;
        }
    }

    @Override
    public void render(Board board) {

        if (alive) {
            chooseCurrentSprite();
        } else {
            if (timeAfterKill > 0) {
                currentSprite = Sprite.oneal_dead;
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
    public boolean collide(Entity e) {
        if (e instanceof Flame || e instanceof FlameSection) {
            kill();
        }
        if (e instanceof Bomber) {
            ((Bomber) e).kill();
            return false;
        }
        return true;
    }
}
