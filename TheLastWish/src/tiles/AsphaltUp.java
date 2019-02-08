package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class AsphaltUp extends Tile{

	
	public AsphaltUp(Game gameIn, int x, int y) {
		super(gameIn, Resources.AsphaltUp, 62);
		rect = new Rectangle(x,y,width,height);
	}
	
	
}
