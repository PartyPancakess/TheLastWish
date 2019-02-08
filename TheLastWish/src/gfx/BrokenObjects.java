package gfx;

import java.awt.image.BufferedImage;

public class BrokenObjects {
	
	public BufferedImage brokenImage;
	public int x,y,width,height;
	
	public BrokenObjects(BufferedImage bf, int xIn, int yIn, int widthIn, int heightIn) {
		brokenImage = bf;
		x = xIn;
		y = yIn;
		width = widthIn;
		height = heightIn;
	}

}
