package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.*;

public abstract class Object {

	
	protected Game game;
	protected Level world;
	protected double x;
	protected double y;
	
	protected double width, height;
	
	protected Rectangle2D rect;
	
	public Object(Level levelIn, Game gameIn, double xIn, double yIn, double widthIn, double heightIn) {
		world = levelIn;
		game = gameIn;
		x = xIn;
		y = yIn;
		width = widthIn;
		height = heightIn;
		rect = new Rectangle2D.Double(xIn, yIn, widthIn, heightIn);
	}
	
	public abstract void update();
	public abstract void render(Graphics graphics);
	
	
	
	
	// getters setters
	
	public double getX() {
		return x;
	}

	public void setX(double d) {
		this.x = d;
	}

	public double getY() {
		return y;
	}

	public void setY(double d) {
		this.y = d;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle2D getRect() {
		return rect;
	}

	public void setRect(Rectangle2D rect) {
		this.rect = rect;
	}
	
	
	
	
	

	
}
