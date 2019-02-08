package gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	
	private BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheetIn) {
		sheet = sheetIn;
	}
	
	public BufferedImage crop(int x, int y, int width, int height) {
		return sheet.getSubimage(x, y, width, height);
	}
}
