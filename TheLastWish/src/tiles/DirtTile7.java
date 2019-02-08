package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class DirtTile7 extends Tile{

	public DirtTile7(Game gameIn, int x, int y) {
		super(gameIn, Resources.dirt7, 7);
		rect = new Rectangle(x, y, width - 12, height - 4);
	}
	
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	
	
	
	
	
}
