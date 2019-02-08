package objects;

import java.awt.Graphics;

import main.Game;
import main.Level;

public abstract class Item extends Object{
	
	public Item(Level levelIn, Game gameIn, double xIn, double yIn, double widthIn, double heightIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics graphics) {
		
	}

}
