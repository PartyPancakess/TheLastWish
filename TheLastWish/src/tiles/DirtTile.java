package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class DirtTile extends Tile{

	public DirtTile(Game gameIn, int x, int y) {
		super(gameIn, Resources.dirt1, 1);
		rect = new Rectangle(x + 8, y + 8, width - 8, height - 8);
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	
	
	
	
	
}
