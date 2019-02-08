package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class DirtTile3 extends Tile{

	public DirtTile3(Game gameIn, int x, int y) {
		super(gameIn, Resources.dirt3, 3);
		rect = new Rectangle(x, y + 8, width - 8, height - 8);
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	
	
	
	
	
}
