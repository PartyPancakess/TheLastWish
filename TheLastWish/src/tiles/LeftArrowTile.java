package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class LeftArrowTile extends Tile{

	
	public LeftArrowTile(Game gameIn, int x, int y) {
		super(gameIn, Resources.Leftarrow, 12);
		rect = new Rectangle(x,y,width,height);
	}
	
	
}
