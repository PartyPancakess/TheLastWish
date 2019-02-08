package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class DownArrowTile extends Tile{

	
	public DownArrowTile(Game gameIn, int x, int y) {
		super(gameIn, Resources.Downarrow, 11);
		rect = new Rectangle(x,y,width,height);
	}
	
	
}
