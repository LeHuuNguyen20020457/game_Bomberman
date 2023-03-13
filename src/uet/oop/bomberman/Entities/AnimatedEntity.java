package uet.oop.bomberman.Entities;

public abstract class AnimatedEntity extends Entity {

    protected int animateFrame = 0;
    protected final int maxAnimateFrame = 7500;

    protected void animate() {
        if (animateFrame < maxAnimateFrame){
            animateFrame++;
        } else {
            animateFrame = 0;
        }
    }
}
