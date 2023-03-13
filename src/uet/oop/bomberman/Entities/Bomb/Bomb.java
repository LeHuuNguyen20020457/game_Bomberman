package uet.oop.bomberman.Entities.Bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;

import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Entities.AnimatedEntity;
import uet.oop.bomberman.Entities.MobileEntity.Bomber;
import uet.oop.bomberman.Graphic.Sprite;
import uet.oop.bomberman.Output.BombSFX;
import uet.oop.bomberman.Util.Utils;

//standonbombexplode
public class Bomb extends AnimatedEntity {

    protected double bombChargeTime = 120;
    public int afterExplodeTime = 20;

    BombSFX sfx = new BombSFX();
    protected Board board;
    protected Flame[] flames;
    protected boolean exploded = false;
    protected boolean passable = true;

    public Bomb(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        currentSprite = Sprite.bomb;
    }

    @Override
    public void update() {
        if (bombChargeTime > 0) {
            bombChargeTime--;
            animate();
        } else {
            if (!exploded) {
                sfx.run();
                explode();
                animateFrame = 0;
            } else {
                updateFlames();
            }

            if (afterExplodeTime > 0) {
                afterExplodeTime--;
            } else {
                remove();
            }
        }

    }

    @Override
    public void render(Board board) {
        if (exploded) {
            currentSprite = Sprite.movingSprite(Sprite.bomb_exploded2, Sprite.bomb_exploded1, Sprite.bomb_exploded, animateFrame, 120);
            animate();
            renderFlames(board);
        } else {
            currentSprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animateFrame, 60);
        }
        int xRender = Utils.coordinateToPixel(x);
        int yRender = Utils.coordinateToPixel(y);
        board.renderEntity(xRender, yRender, currentSprite);
    }

    public void renderFlames(Board board) {
        for (Flame flame : flames) {
            flame.render(board);
        }
    }

    public void updateFlames() {
        for (Flame flame : flames) {
            flame.update();
        }
    }


    protected void explode() {
        exploded = true;

        int bomberX = this.board.getBomber().getXTile();
        int bomberY = this.board.getBomber().getYTile();

        if (bomberX == this.getXTile() && this.getYTile() == bomberY) {
            this.board.getBomber().kill();
        }

        flames = new Flame[4];
        for (int index = 0; index < flames.length; index++) {
            flames[index] = new Flame((int) x, (int) y, index, Game.getBombLevel(), board);
        }
    }


    public FlameSection flameAt(int x, int y) {
        if (!exploded) return null;

        for (Flame flame : flames) {
            if (flame == null){
                return null;
            }
            FlameSection flameSection = flame.flameSegmentAt(x, y);
            if (flameSection != null){
                return flameSection;
            }
        }
        return null;
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            double diffX = entity.getX() - Utils.coordinateToPixel(getX());
            double diffY = entity.getY() - Utils.coordinateToPixel(getY());

            if (diffY >= 1 && diffY <= 30 && diffX >= -10 && diffX < 16) {
                passable = true;
                return true;
            } else {
                if (exploded) {
                    ((Bomber) entity).kill();
                    return true;
                }
                passable = false;
                return false;
            }
        }

        if (entity instanceof Flame || entity instanceof FlameSection) {
            this.bombChargeTime = 0;
            return true;
        }

        if (entity instanceof Bomb) {
            if (((Bomb) entity).isExploded()) {
                this.bombChargeTime = 0;
                return true;
            }
        }
        return false;
    }

    public boolean isPassable() {
        return passable;
    }

    public boolean isExploded() {
        return exploded;
    }

}
