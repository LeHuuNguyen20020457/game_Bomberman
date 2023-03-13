package uet.oop.bomberman.Entities.MobileEntity.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Output.MonsterKillSFX;
import uet.oop.bomberman.Util.Message;
import uet.oop.bomberman.Entities.Bomb.Flame;
import uet.oop.bomberman.Entities.MobileEntity.Bomber;
import uet.oop.bomberman.Entities.MobileEntity.Character;
import uet.oop.bomberman.Entities.MobileEntity.enemy.ai.AI;
import uet.oop.bomberman.Entities.Bomb.FlameSection;
import uet.oop.bomberman.Util.Utils;

import java.awt.*;

public abstract class Enemy extends Character {

    protected int point;

    protected double speed;
    protected AI ai;

    protected final double maxSteps;
    protected final double restStep;
    protected double steps;

    protected int deadAnimation = 30;

    MonsterKillSFX monsterKillSFX = new MonsterKillSFX();

    public Enemy(int x, int y, Board board, double speed, int points) {
        super(x, y, board);

        point = points;
        this.speed = speed;

        maxSteps = Utils.TILES_SIZE / this.speed;
        restStep = (maxSteps - (int) maxSteps) / maxSteps;
        steps = maxSteps;

        timeAfterKill = 20;
    }

    @Override
    public void update() {
        animate();

        if (!alive) {
            afterKill();
            return;
        }

        calculateMove();
    }

    @Override
    public void render(Board board) {}

    @Override
    public void calculateMove() {
        int xMove = 0, yMove = 0;

        if (steps <= 0) {
            direction = ai.calculateDirection();
            steps = maxSteps;
        }

        if (direction == 0){
            yMove--;
        }
        if (direction == 2){
            yMove++;
        }
        if (direction == 3){
            xMove--;
        }
        if (direction == 1){
            xMove++;
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
    public void move(double xMove, double yMove) {
        if (!alive) return;
        y += yMove;
        x += xMove;
    }

    @Override
    public boolean canMove(double xCanMove, double yCanMove) {
        double xMove = this.x;
        double yMove = this.y - 16;

        int distance = Utils.TILES_SIZE;

        if (direction == 0) {
            yMove += distance - 1;
            xMove += distance / 2;
        }
        if (direction == 1) {
            yMove += distance / 2;
            xMove += 1;
        }
        if (direction == 2) {
            xMove += distance / 2;
            yMove += 1;
        }
        if (direction == 3) {
            xMove += distance - 1;
            yMove += distance / 2;
        }

        int xCheck = Utils.pixelToCoordinate(xMove) + (int) xCanMove;
        int yCheck = Utils.pixelToCoordinate(yMove) + (int) yCanMove;

        return  board.getEntity(xCheck, yCheck, this).collide(this);
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

    @Override
    public void kill() {
        if (!alive) {
            return;
        }
        alive = false;

        board.addPoints(point);

        monsterKillSFX.run();
        int xMessage = Utils.getXMessage((int) getX());
        int yMessage = Utils.getYMessage((int) getY());
        Message msg = new Message("+ " + point, xMessage, yMessage, 2, Color.white, 14);
        board.addMessage(msg);
    }


    @Override
    protected void afterKill() {
        if (timeAfterKill > 0){
            --timeAfterKill;
        } else {
            if (deadAnimation > 0) {
                deadAnimation--;
            }
            else {
                remove();
            }
        }
    }
}
