package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class UpArrowTile extends Tile{

	
	public UpArrowTile(Game gameIn, int x, int y) {
		super(gameIn, Resources.Uparrow, 10);
		rect = new Rectangle(x,y,width,height);
	}
	
	
}
