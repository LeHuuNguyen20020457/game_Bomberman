package uet.oop.bomberman.Graphic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SpriteSheet {

	private final String path = "/textures/classic.png";
	public final int SIZE;
	public int[] pixels;
	public BufferedImage image;

	public static SpriteSheet tiles = new SpriteSheet( 256);
	
	public SpriteSheet(int size) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}


	private void load() {
		try {
			URL a = SpriteSheet.class.getResource(path);
			image = ImageIO.read(a);
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
