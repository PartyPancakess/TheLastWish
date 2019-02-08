package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class AsphaltDown extends Tile{

	
	public AsphaltDown(Game gameIn, int x, int y) {
		super(gameIn, Resources.AsphaltDown, 63);
		rect = new Rectangle(x,y,width,height);
	}
	
	
}
