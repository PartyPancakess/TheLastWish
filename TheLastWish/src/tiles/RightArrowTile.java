package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class RightArrowTile extends Tile{

	
	public RightArrowTile(Game gameIn, int x, int y) {
		super(gameIn, Resources.Rightarrow, 13);
		rect = new Rectangle(x,y,width,height);
	}
	
	
}
