package uet.oop.bomberman.Entities.Bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.AnimatedEntity;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Entities.MobileEntity.Character;
import uet.oop.bomberman.Entities.StaticEntity.Brick;
import uet.oop.bomberman.Graphic.Sprite;
import uet.oop.bomberman.Util.Utils;


public class FlameSection extends AnimatedEntity {

    private boolean lastSection;
    private int direction;

    public FlameSection(int x, int y, int direction, boolean lastSection) {
        this.x = x;
        this.y = y;
        this.lastSection = lastSection;
        this.direction = direction;

    }

    @Override
    public void render(Board board) {
        currentSprite = chooseCurrentSprite();
        int xRender = Utils.coordinateToPixel(x);
        int yRender = Utils.coordinateToPixel(y);
        board.renderEntity(xRender, yRender, currentSprite);
        this.animate();
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof Character) {
		     ((Character)e).kill();
        }

        if(e instanceof Brick) {
            ((Brick) e).destroy();
        }

        if ( e instanceof Bomb) {
            ( (Bomb) e).explode();
            return true;
        }
        return e instanceof FlameSection || e instanceof Flame;
    }

    private Sprite chooseCurrentSprite() {
        switch (direction) {
            case 0:
                if(!lastSection) {
                    return Sprite.movingSprite(Sprite.explosion_vertical2,
                            Sprite.explosion_vertical1,Sprite.explosion_vertical, animateFrame,120);
                } else {
                    return Sprite.movingSprite(Sprite.explosion_vertical_top_last2,
                            Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last, animateFrame,120);
                }
            case 1:
                if(!lastSection) {
                    return Sprite.movingSprite(Sprite.explosion_horizontal2,
                            Sprite.explosion_horizontal1,Sprite.explosion_horizontal, animateFrame,120);
                } else {
                    return Sprite.movingSprite(Sprite.explosion_horizontal_right_last2,
                            Sprite.explosion_horizontal_right_last1,Sprite.explosion_horizontal_right_last, animateFrame,120);
                }
            case 2:
                if(!lastSection) {
                    return Sprite.movingSprite(Sprite.explosion_vertical2,
                            Sprite.explosion_vertical1,Sprite.explosion_vertical, animateFrame,120);
                } else {
                    return Sprite.movingSprite(Sprite.explosion_vertical_down_last2,
                            Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last, animateFrame,120);
                }
            case 3:
                if(!lastSection) {
                    return Sprite.movingSprite(Sprite.explosion_horizontal2,
                            Sprite.explosion_horizontal1,Sprite.explosion_horizontal, animateFrame,120);
                } else {
                    return Sprite.movingSprite(Sprite.explosion_horizontal_left_last2,
                            Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last, animateFrame,120);
                }
            default:
                return Sprite.explosion_vertical2;
        }
    }
}