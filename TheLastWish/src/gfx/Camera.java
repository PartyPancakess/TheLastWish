package gfx;

import main.Game;
import main.Level;
import objects.Object;
import tiles.Tile;

public class Camera {

	private double xoffset, yoffset;
	private Game game;
	
	public Camera(Game gameIn, float xoffsetIn, float yoffsetIn) {
		game = gameIn;
		xoffset = xoffsetIn;
		yoffset = yoffsetIn;
	}
	
	public void move(float xinc, float yinc) {
		xoffset += xinc;
		yoffset += yinc;
	}
	
	public void center(Object e) {
		xoffset = e.getX() - game.getWidth() / 2 + e.getWidth() / 2;
		yoffset = e.getY() - game.getHeight() / 2 + e.getHeight() / 2;
		checkblankspace();
	}
	
	public void checkblankspace() {
		if(xoffset < 0) {
			xoffset = 0;
		}
		
		if(yoffset < 0) {
			yoffset = 0;
		}
		
		if(xoffset > Level.getWidth() * Tile.getWidth() - game.getWidth()) {
			xoffset = Level.getWidth() * Tile.getWidth() - game.getWidth();
		}
		
		if(yoffset > Level.getHeight() * Tile.getHeight() - game.getHeight()) {
			yoffset = Level.getHeight() * Tile.getHeight() - game.getHeight();
		}
	}
	
	
	
	//getters

	public double getXoffset() {
		return xoffset;
	}

	public double getYoffset() {
		return yoffset;
	}
	
	//setters
	
	public void setXoffset(float xoffset) {
		this.xoffset = xoffset;
	}

	public void setYoffset(float yoffset) {
		this.yoffset = yoffset;
	}
	
}
