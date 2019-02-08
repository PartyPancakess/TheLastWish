package main;

import java.awt.Color;
import java.awt.Graphics;

import gfx.Resources;

public class DeathScreen {

	
	private Level level;
	private Game game;
	
	private static int width = Game.width / 2, height = Game.height / 2;
	private static int x = Game.width / 2 , y = Game.height / 2 ;
	
	public DeathScreen(Level levelIn, Game gameIn) {
		level = levelIn;
		game = gameIn;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics graphics) {
		Resources.drawString(graphics, "YOU DIED", x, y, true, Color.DARK_GRAY, Resources.font56);
		Resources.drawString(graphics, "Press R to restart", x, y + 56, true, Color.DARK_GRAY, Resources.font18);
	}
	
	
}
