package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class GrassTile extends Tile{
	
	public GrassTile(Game gameIn, int x, int y) {
		super(gameIn, Resources.grass, 0);
		rect = new Rectangle(x,y,width,height);
	}

	
	
	
	
	
}
