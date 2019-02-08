package objects;

import java.awt.Graphics;

import gfx.Resources;
import main.Game;
import main.Level;

public class Pistol extends Weapon{

	public Pistol(Level levelIn, Game gameIn, int xIn, int yIn, double widthIn, double heightIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn);
		ammoCapacity = 60;
		ammo = ammoCapacity;
		cost = 1;
		damage = 1;
		name = "pistol";
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(Resources.pistol, (int)x, (int)y, (int)width, (int)height, null);
	}

}
