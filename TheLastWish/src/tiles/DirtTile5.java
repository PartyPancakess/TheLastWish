package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class DirtTile5 extends Tile{

	public DirtTile5(Game gameIn, int x, int y) {
		super(gameIn, Resources.dirt5, 5);
		rect = new Rectangle(x + 8, y, width - 8, height - 16);
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	
	
	
	
	
}