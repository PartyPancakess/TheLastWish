package tiles;

import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class StoneTile extends Tile{
	
	private Rectangle rect;
	
	public StoneTile(Game gameIn, int x, int y) {
		super(gameIn, Resources.stone, 2);
		rect = new Rectangle(x,y,width,height);
	}
	
	public boolean isSolid() {
		return true;
	}
	
	

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
	
		

}
