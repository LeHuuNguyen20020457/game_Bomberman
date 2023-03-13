package uet.oop.bomberman.Entities.MobileEntity.enemy.ai;

public class AIRandom extends AI{

    @Override
    public int calculateDirection()
    {
        return random.nextInt(4);
    }

}
