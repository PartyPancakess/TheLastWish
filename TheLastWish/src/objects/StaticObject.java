package objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.BrokenObjects;
import gfx.Resources;
import main.Game;
import main.Level;
import states.GameState;

public abstract class StaticObject extends Object{
	
	protected int HP;
	protected BrokenObjects brokenObject;
	
	public StaticObject(Level levelIn, Game gameIn, int xIn, int yIn, int widthIn, int heightIn, int HealthIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn);
		HP = HealthIn;
	}
	
	
	@Override
	public void update() {
		
	}
	
	
	public void initBrokenObject(BufferedImage bf) {
		brokenObject = new BrokenObjects(bf, (int)x, (int)y, (int)width, (int)height);
	}


	public int getHP() {
		return HP;
	}
	

}
