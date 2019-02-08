package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class MarketDown extends Tile{

	
	public MarketDown(Game gameIn, int x, int y) {
		super(gameIn, Resources.MarketDown, 61);
		rect = new Rectangle(x,y,width,height);
	}
	
	
}
