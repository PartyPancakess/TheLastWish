package objects;

import java.awt.Graphics;

import gfx.Resources;
import main.Game;
import main.Level;

public class Shotgun extends Weapon{

	public Shotgun(Level levelIn, Game gameIn, int xIn, int yIn, double widthIn, double heightIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn);
		ammoCapacity = 32;
		ammo = ammoCapacity;
		cost = 4;
		damage = 3;
		name = "shotgun";
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(Resources.shotgun, (int)x, (int)y, (int)width, (int)height, null);
	}

}
