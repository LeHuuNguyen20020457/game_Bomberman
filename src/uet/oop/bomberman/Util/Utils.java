package uet.oop.bomberman.Util;

public class Utils {
	public static final int TILES_SIZE = 16;
	public static final int HEIGHT = TILES_SIZE * 13;
	public static final int WIDTH = TILES_SIZE * 31;
	public static final int SCALE = 3;
	public static final int REALWITDH = WIDTH * SCALE;
	public static final int REALHEIGHT = HEIGHT * SCALE;
	public static final int transparentColor = 0xffff00ff;

	public static int pixelToCoordinate(double i) {
		return (int)(i / TILES_SIZE);
	}

	public static int coordinateToPixel(int i) {
		return i << 4;
	}
	
	public static int coordinateToPixel(double i) {return (int)(i * TILES_SIZE);}

	public static int getXMessage(int x) {
		return  (x + 8) * Utils.SCALE;
	}

	public static int getYMessage(int y) {
		return  (y - 8) * Utils.SCALE;
	}
}
