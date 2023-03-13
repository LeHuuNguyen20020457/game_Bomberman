package uet.oop.bomberman.Entities.MobileEntity.enemy.ai;

import uet.oop.bomberman.Entities.MobileEntity.Bomber;
import uet.oop.bomberman.Entities.MobileEntity.enemy.Enemy;

public class AILow extends AI {
    private Bomber bomber;
    private Enemy mob;

    public AILow(Bomber bomber, Enemy mob) {
        this.bomber = bomber;
        this.mob = mob;
    }

    @Override
    public int calculateDirection() {
        if (bomber == null) return random.nextInt(4);

        int random = this.random.nextInt(2);
        if (random == 1) {
            int direction = rowDirection();
            if (direction == -1) {
                return colDirection();
            } else {
                return direction;
            }
        } else {
            int direction = colDirection();
            if (direction == -1) {
                return rowDirection();
            } else {
                return direction;
            }
        }
    }

    protected int colDirection() {
        if (bomber.getXTile() < mob.getXTile()) {
            return 3;
        }
        if (bomber.getXTile() > mob.getXTile()) {
            return 1;
        }
        return -1;
    }

    protected int rowDirection() {
        if (bomber.getYTile() < mob.getYTile()) {
            return 0;
        }
        if (bomber.getYTile() > mob.getYTile()) {
            return 2;
        }
        return -1;
    }
}
