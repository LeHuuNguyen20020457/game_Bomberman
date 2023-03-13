package uet.oop.bomberman.Graphic;

import java.util.Arrays;

public class Sprite {

    public final int SIZE = 16;
    private int x;
    private int y;
    public int[] pixels = new int[256];
    private int realWidth;
    private int realHeight;
    private final SpriteSheet spriteSheet = SpriteSheet.tiles;

    /*
    |--------------------------------------------------------------------------
    | Board sprites
    |--------------------------------------------------------------------------
     */
    public static Sprite grass = new Sprite(6, 0, 16, 16);
    public static Sprite brick = new Sprite(7, 0, 16, 16);
    public static Sprite wall = new Sprite(5, 0, 16, 16);
    public static Sprite portal = new Sprite(4, 0, 14, 14);

    /*
    |--------------------------------------------------------------------------
    | Bomber Sprites
    |--------------------------------------------------------------------------
     */
    public static Sprite player_up = new Sprite(0, 0, 12, 16);
    public static Sprite player_down = new Sprite(2, 0, 12, 15);
    public static Sprite player_left = new Sprite(3, 0, 10, 15);
    public static Sprite player_right = new Sprite(1, 0, 10, 16);

    public static Sprite player_up_1 = new Sprite(0, 1, 12, 16);
    public static Sprite player_up_2 = new Sprite(0, 2, 12, 15);

    public static Sprite player_down_1 = new Sprite(2, 1, 12, 15);
    public static Sprite player_down_2 = new Sprite(2, 2, 12, 16);

    public static Sprite player_left_1 = new Sprite(3, 1, 11, 16);
    public static Sprite player_left_2 = new Sprite(3, 2, 12, 16);

    public static Sprite player_right_1 = new Sprite(1, 1, 11, 16);
    public static Sprite player_right_2 = new Sprite(1, 2, 12, 16);

    public static Sprite player_dead1 = new Sprite(4, 2, 14, 16);
    public static Sprite player_dead2 = new Sprite(5, 2, 13, 15);
    public static Sprite player_dead3 = new Sprite(6, 2, 16, 16);

    /*
    |--------------------------------------------------------------------------
    | Character
    |--------------------------------------------------------------------------
     */
    //BALLOM
    public static Sprite balloom_left1 = new Sprite(9, 0, 16, 16);
    public static Sprite balloom_left2 = new Sprite(9, 1, 16, 16);
    public static Sprite balloom_left3 = new Sprite(9, 2, 16, 16);

    public static Sprite balloom_right1 = new Sprite(10, 0, 16, 16);
    public static Sprite balloom_right2 = new Sprite(10, 1, 16, 16);
    public static Sprite balloom_right3 = new Sprite(10, 2, 16, 16);

    public static Sprite balloom_dead = new Sprite(9, 3, 16, 16);

    //ONEAL
    public static Sprite oneal_left1 = new Sprite(11, 0, 16, 16);
    public static Sprite oneal_left2 = new Sprite(11, 1, 16, 16);
    public static Sprite oneal_left3 = new Sprite(11, 2, 16, 16);

    public static Sprite oneal_right1 = new Sprite(12, 0, 16, 16);
    public static Sprite oneal_right2 = new Sprite(12, 1, 16, 16);
    public static Sprite oneal_right3 = new Sprite(12, 2, 16, 16);

    public static Sprite oneal_dead = new Sprite(11, 3, 16, 16);

    //Doll
    public static Sprite doll_left1 = new Sprite(13, 0, 16, 16);
    public static Sprite doll_left2 = new Sprite(13, 1, 16, 16);
    public static Sprite doll_left3 = new Sprite(13, 2, 16, 16);

    public static Sprite doll_right1 = new Sprite(14, 0, 16, 16);
    public static Sprite doll_right2 = new Sprite(14, 1, 16, 16);
    public static Sprite doll_right3 = new Sprite(14, 2, 16, 16);

    public static Sprite doll_dead = new Sprite(13, 3, 16, 16);

    //Kondoria
    public static Sprite kondoria_left1 = new Sprite(10, 5, 16, 16);
    public static Sprite kondoria_left2 = new Sprite(10, 6, 16, 16);
    public static Sprite kondoria_left3 = new Sprite(10, 7, 16, 16);

    public static Sprite kondoria_right1 = new Sprite(11, 5, 16, 16);
    public static Sprite kondoria_right2 = new Sprite(11, 6, 16, 16);
    public static Sprite kondoria_right3 = new Sprite(11, 7, 16, 16);

    public static Sprite kondoria_dead = new Sprite(10, 8, 16, 16);

    //ALL
    public static Sprite mob_dead1 = new Sprite(15, 0, 16, 16);
    public static Sprite mob_dead2 = new Sprite(15, 1, 16, 16);
    public static Sprite mob_dead3 = new Sprite(15, 2, 16, 16);

	/*
	|--------------------------------------------------------------------------
	| Bomb Sprites
	|--------------------------------------------------------------------------
	 */

    public static Sprite bomb = new Sprite(0, 3, 15, 15);
    public static Sprite bomb_1 = new Sprite(1, 3, 13, 15);
    public static Sprite bomb_2 = new Sprite(2, 3, 12, 14);

	/*
	|--------------------------------------------------------------------------
	| FlameSegment Sprites
	|--------------------------------------------------------------------------
	 */

    public static Sprite bomb_exploded = new Sprite(0, 4, 16, 16);
    public static Sprite bomb_exploded1 = new Sprite(0, 5, 16, 16);
    public static Sprite bomb_exploded2 = new Sprite(0, 6, 16, 16);

    public static Sprite explosion_vertical = new Sprite(1, 5, 16, 16);
    public static Sprite explosion_vertical1 = new Sprite(2, 5, 16, 16);
    public static Sprite explosion_vertical2 = new Sprite(3, 5, 16, 16);

    public static Sprite explosion_horizontal = new Sprite(1, 7, 16, 16);
    public static Sprite explosion_horizontal1 = new Sprite(1, 8, 16, 16);
    public static Sprite explosion_horizontal2 = new Sprite(1, 9, 16, 16);

    public static Sprite explosion_horizontal_left_last = new Sprite(0, 7, 16, 16);
    public static Sprite explosion_horizontal_left_last1 = new Sprite(0, 8, 16, 16);
    public static Sprite explosion_horizontal_left_last2 = new Sprite(0, 9, 16, 16);

    public static Sprite explosion_horizontal_right_last = new Sprite(2, 7, 16, 16);
    public static Sprite explosion_horizontal_right_last1 = new Sprite(2, 8, 16, 16);
    public static Sprite explosion_horizontal_right_last2 = new Sprite(2, 9, 16, 16);

    public static Sprite explosion_vertical_top_last = new Sprite(1, 4, 16, 16);
    public static Sprite explosion_vertical_top_last1 = new Sprite(2, 4, 16, 16);
    public static Sprite explosion_vertical_top_last2 = new Sprite(3, 4, 16, 16);

    public static Sprite explosion_vertical_down_last = new Sprite(1, 6, 16, 16);
    public static Sprite explosion_vertical_down_last1 = new Sprite(2, 6, 16, 16);
    public static Sprite explosion_vertical_down_last2 = new Sprite(3, 6, 16, 16);

	/*
	|--------------------------------------------------------------------------
	| Brick FlameSegment
	|--------------------------------------------------------------------------
	 */

    public static Sprite brick_exploded = new Sprite(7, 1, 16, 16);
    public static Sprite brick_exploded1 = new Sprite(7, 2, 16, 16);
    public static Sprite brick_exploded2 = new Sprite(7, 3, 16, 16);

	/*
	|--------------------------------------------------------------------------
	| Powerups
	|--------------------------------------------------------------------------
	 */

    public static Sprite powerup_bombs = new Sprite(0, 10, 16, 16);
    public static Sprite powerup_flames = new Sprite(1, 10, 16, 16);
    public static Sprite powerup_speed = new Sprite(2, 10, 16, 16);
    public static Sprite powerup_detonator = new Sprite(4, 10, 16, 16);


    public Sprite(int x, int y, int rw, int rh) {
        this.x = x * SIZE;
        this.y = y * SIZE;
        realWidth = rw;
        realHeight = rh;
        load();
    }

    public Sprite(int color) {
        setColor(color);
    }

    private void setColor(int color) {
        Arrays.fill(pixels, color);
    }

    private void load() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                pixels[x + y * SIZE] = spriteSheet.pixels[(x + this.x) + (y + this.y) * spriteSheet.SIZE];
            }
        }
    }

    public static Sprite movingSprite(Sprite first, Sprite second, Sprite third, int animate, int time) {
        int calc = animate % time;
        int diff = time / 3;

        if (calc < diff) {
            return first;
        }

        if (calc < diff * 2) {
            return second;
        }

        return third;
    }

    public static Sprite movingSprite(Sprite first, Sprite second, int animate, int time) {
        int diff = time / 2;
        return (animate % time > diff) ? first : second;
    }

    public int getPixel(int i) {
        return pixels[i];
    }

}
