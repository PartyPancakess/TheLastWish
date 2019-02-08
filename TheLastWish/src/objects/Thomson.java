package objects;

import java.awt.Graphics;

import gfx.Resources;
import main.Game;
import main.Level;

public class Thomson extends Weapon{

	public Thomson(Level levelIn, Game gameIn, int xIn, int yIn, double widthIn, double heightIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn);
		ammoCapacity = 128;
		ammo = ammoCapacity;
		cost = 10;
		damage = 1;
		name = "thomson";
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(Resources.thomson, (int)x, (int)y, (int)width, (int)height, null);
	}

}
