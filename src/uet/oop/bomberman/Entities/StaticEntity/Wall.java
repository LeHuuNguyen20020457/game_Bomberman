package uet.oop.bomberman.Entities.StaticEntity;


import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.Graphic.Sprite;

public class Wall extends StaticEntity {

    public Wall(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

}
