package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class MarketUp extends Tile{

	
	public MarketUp(Game gameIn, int x, int y) {
		super(gameIn, Resources.MarketUp, 60);
		rect = new Rectangle(x,y,width,height);
	}
	
	
}
