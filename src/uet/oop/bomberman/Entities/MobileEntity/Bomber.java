package uet.oop.bomberman.Entities.MobileEntity;


import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Entities.Bomb.Bomb;

import uet.oop.bomberman.Graphic.Sprite;
import uet.oop.bomberman.Input.Keyboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uet.oop.bomberman.Entities.Bomb.Flame;
import uet.oop.bomberman.Entities.Bomb.FlameSection;
import uet.oop.bomberman.Entities.MobileEntity.enemy.Enemy;
import uet.oop.bomberman.Output.KillSFX;
import uet.oop.bomberman.Output.PlaceBombSFX;
import uet.oop.bomberman.Output.PowerUpSFX;
import uet.oop.bomberman.Util.Utils;
import uet.oop.bomberman.Entities.Item.*;


public class Bomber extends Character {
    private Keyboard keyboardInput;
    private List<Bomb> bombs;
    public static List<Item> powerUps = new ArrayList<>();
    private int bombCoolDown = 0;

    PlaceBombSFX placeBombSFX;
    PowerUpSFX powerUpSFX;
    KillSFX killSFX;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        bombs = this.board.getBombs();
        keyboardInput = this.board.getKeyboard();
        placeBombSFX = new PlaceBombSFX();
        powerUpSFX = new PowerUpSFX();
        killSFX = new KillSFX();
        currentSprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBomb();

        if (!alive) {
            afterKill();
            return;
        }

        if (bombCoolDown < -7500) {
            bombCoolDown = 0;
        } else {
            bombCoolDown--;
        }

        animate();

        if (keyboardInput.BOMB) {
            placeBomb();
        }

        calculateMove();
    }

    @Override
    public void render(Board board) {
        int xRender = (int) x;
        int yRender = (int) y  - 16;

        if (alive) {
            chooseCurrentSprite();
        } else {
            currentSprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animateFrame, 60);
        }

        board.renderEntity(xRender, yRender, currentSprite);
    }

    private void placeBomb() {
        if (bombCoolDown < 0 && Game.getBombNum() > 0) {

            int xBomb = Utils.pixelToCoordinate(x + 8);
            int yBomb = Utils.pixelToCoordinate(y + 8 - Utils.TILES_SIZE);
            placeBombSFX.run();
            Bomb bomb = new Bomb(xBomb, yBomb, board);
            board.addBomb(bomb);
            Game.addBombNum(-1);
            bombCoolDown = 10;
        }
    }

    private void clearBomb() {
        Iterator<Bomb> bs = bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombNum(1);
            }
        }
    }

    @Override
    public void kill() {
        killSFX.run();
        if (!alive) {
            return;
        }
        animateFrame = 0;
        alive = false;
        Game.addLive(-1);
    }

    @Override
    protected void afterKill() {

        if (timeAfterKill > 0) {
            timeAfterKill--;
        } else {
            if (bombs.size() == 0) {
                if (board.getLives() == 0)
                    board.endGame();
                else {
                    board.restartLevel();
                    powerUps.clear();
                    Game.resetPowerUp();
                }
            }
        }
    }

    public void addPowerUp(Item item) {
        if (item.isRemoved()) {
            return;
        }
        powerUps.add(item);
        powerUpSFX.run();
        item.init();
    }

    @Override
    protected void calculateMove() {
        int xMove = 0, yMove = 0;
        double bomberSpeed = Game.getBomberSpeed();
        if (keyboardInput.LEFT) {
            xMove--;
        }
        if (keyboardInput.RIGHT){
            xMove++;
        }
        if (keyboardInput.UP){
            yMove--;
        }
        if (keyboardInput.DOWN){
            yMove++;
        }

        if (xMove != 0 || yMove != 0) {
            move(xMove *bomberSpeed, yMove * bomberSpeed);
            moving = true;
        } else {
            moving = false;
        }
    }

    @Override
    public boolean canMove(double xCanMove, double yCanMove) {
        for (int di = 0; di < 4; di++) {

            double xt = ((this.x + xCanMove) + di % 2 * 11) / Utils.TILES_SIZE;
            double yt = ((this.y + yCanMove) + di / 2 * 12 - 15) / Utils.TILES_SIZE;

            Entity entity = board.getEntity(xt, yt, this);

            if (!entity.collide(this))
                return false;
        }
        return true;
    }

    @Override
    public void move(double xMove, double yMove) {
        if (yMove < 0) {
            direction = 0; //down
        }
        if (yMove > 0){
            direction = 2; // up
        }
        if (xMove > 0){
            direction = 1; //left
        }
        if (xMove < 0){
            direction = 3; //right
        }

        if (canMove(0, yMove)) {
            y += yMove;
        }
        if (canMove(xMove, 0)) {
            x += xMove;
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Enemy) {
            kill();
            return true;
        }
        if (e instanceof Flame || e instanceof FlameSection) {
            kill();
            return true;
        }
        if (e instanceof Bomb) {
            if (((Bomb) e).isExploded() && ((Bomb) e).isPassable()) {
                kill();
                return true;
            }
        }

        return true;
    }

    @Override
    protected void chooseCurrentSprite() {
        switch (direction) {
            case 0:
                if (moving) {
                    currentSprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animateFrame, 20);
                } else {
                    currentSprite = Sprite.player_up;
                }
                break;
            case 1:
                if (moving) {
                    currentSprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animateFrame, 20);
                } else {
                    currentSprite = Sprite.player_right;
                }
                break;
            case 2:
                if (moving) {
                    currentSprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animateFrame, 20);
                } else {
                    currentSprite = Sprite.player_down;
                }
                break;
            case 3:
                if (moving) {
                    currentSprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animateFrame, 20);
                } else {
                    currentSprite = Sprite.player_left;
                }
                break;
            default:
                currentSprite = Sprite.player_right;
                break;
        }
    }
}
