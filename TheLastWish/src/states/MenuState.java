package states;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;

import gfx.Resources;
import main.*;

public class MenuState extends State{

	
	Game game;
	//private int width = Game.width / ((Game.width/1366)*7), height = Game.height / ((Game.height/768) * 8), space = 20;
	private int width = Game.width / 7, height = Game.height / 8, space = 20;
	private int startX = Game.width / 2 - width / 2, startY = Game.height / 2 - space / 2 - height - space - height,
			settingsX = Game.width / 2 - width / 2, settingsY = Game.height / 2 - space / 2 - height,
			creditsX = Game.width / 2 - width / 2, creditsY = Game.height / 2 + space / 2,
			exitX = Game.width / 2 - width / 2, exitY = Game.height / 2 + space / 2 + height + space;
	
	private Rectangle startRect = new Rectangle(startX, startY, width, height),
					  settingsRect = new Rectangle(settingsX, settingsY, width, height),
					  creditsRect = new Rectangle(creditsX, creditsY, width, height),
					  exitRect = new Rectangle(exitX, exitY, width, height);
	
	private boolean start, settings, credits, exit;
	
	long timer = 0;
	long lastTime = 0;
	
	public MenuState(Game gameIn) {
		super(gameIn);
		game = gameIn;
	}
	
	public void selectSound() {
		Resources.stop(Resources.select);
		Resources.play(Resources.select);
	}

	@Override
	public void update() {
		mouseMovement();
		
		if(start) {
			if(startRect.contains(Game.getMousePressedPoint())){
				if(startRect.contains(Game.getMouseRelesedPoint())) {
					selectSound();
					State.setcurrentState(Game.getGameState());
				}
			}
		}
		
		
		if(settings) {
			if(settingsRect.contains(Game.getMousePressedPoint())){
				if(settingsRect.contains(Game.getMouseRelesedPoint())) {
					selectSound();
					State.setcurrentState(Game.getSettingsState());
				}
			}
		}
		
		
		if(credits) {
			if(creditsRect.contains(Game.getMousePressedPoint())){
				if(creditsRect.contains(Game.getMouseRelesedPoint())) {
					selectSound();
					State.setcurrentState(Game.getCreditsState());
				}
			}
		}
		
		
		
		
		if(exit) {
			if(exitRect.contains(Game.getMousePressedPoint())){
				if(exitRect.contains(Game.getMouseRelesedPoint())) {
					
					lastTime = System.currentTimeMillis();
					selectSound();
					while(System.currentTimeMillis()<lastTime+800) {
						
					}
					System.exit(0);
				
				}
			}
		}
		
		
	}

	@Override
	public void render(Graphics graphics) {
		if(!start) {
			//graphics.drawImage(Resources.start0, startX, startY, width, height, null);
			Resources.drawString(graphics, "START", startX + width / 2 , startY + height / 2, true, Color.WHITE, Resources.font56);
		}
		else if(start) {
			if(game.isLeftButtonPressed()) {
				//graphics.drawImage(Resources.start2, startX, startY, width, height, null);
				Resources.drawString(graphics, "START", startX + width / 2 , startY + height / 2, true, Color.red, Resources.font56);
			}
			else {
				//graphics.drawImage(Resources.start1, startX, startY, width, height, null);
				Resources.drawString(graphics, "START", startX + width / 2 , startY + height / 2, true, Color.CYAN, Resources.font56);
			}
		}
		
		if(!settings) {
			//graphics.drawImage(Resources.settings0, settingsX, settingsY, width, height, null);
			Resources.drawString(graphics, "Settings", settingsX + width / 2 , settingsY + height / 2, true, Color.WHITE, Resources.font56);
		}
		else if(settings) {
			if(game.isLeftButtonPressed()) {
				//graphics.drawImage(Resources.settings2, settingsX, settingsY, width, height, null);
				Resources.drawString(graphics, "Settings", settingsX + width / 2 , settingsY + height / 2, true, Color.red, Resources.font56);
			}
			else {
				//graphics.drawImage(Resources.settings1, settingsX, settingsY, width, height, null);
				Resources.drawString(graphics, "Settings", settingsX + width / 2 , settingsY + height / 2, true, Color.CYAN, Resources.font56);
			}
		}
		
		
		if(!credits) {
			//graphics.drawImage(Resources.credits0, creditsX, creditsY, width, height, null);
			Resources.drawString(graphics, "Credits", creditsX + width / 2 , creditsY + height / 2, true, Color.WHITE, Resources.font56);
		}
		else if(credits) {
			if(game.isLeftButtonPressed()) {
				//graphics.drawImage(Resources.credits2, creditsX, creditsY, width, height, null);
				Resources.drawString(graphics, "Credits", creditsX + width / 2 , creditsY + height / 2, true, Color.red, Resources.font56);
			}
			else {
				//graphics.drawImage(Resources.credits1, creditsX, creditsY, width, height, null);
				Resources.drawString(graphics, "Credits", creditsX + width / 2 , creditsY + height / 2, true, Color.CYAN, Resources.font56);
			}
		}

		
		if(!exit) {
			//graphics.drawImage(Resources.exit0, exitX, exitY, width, height, null);
			Resources.drawString(graphics, "EXIT", exitX + width / 2 , exitY + height / 2, true, Color.WHITE, Resources.font56);
		}
		else if(exit) {
			if(game.isLeftButtonPressed()) {
				//graphics.drawImage(Resources.exit2, exitX, exitY, width, height, null);
				Resources.drawString(graphics, "EXIT", exitX + width / 2 , exitY + height / 2, true, Color.red, Resources.font56);
			}
			else {
				//graphics.drawImage(Resources.exit1, exitX, exitY, width, height, null);
				Resources.drawString(graphics, "EXIT", exitX + width / 2 , exitY + height / 2, true, Color.CYAN, Resources.font56);
			}
		}
		
		
		
		
	}
	
	public void mouseMovement() {
		if(startRect.contains(Game.getMouseMovedPoint())) {
			start = true;
			credits = false;
			exit = false;
			settings = false;
		}
		else if(settingsRect.contains(Game.getMouseMovedPoint())) {
			settings = true;
			start = false;
			credits = false;
			exit = false;
		}
		else if(creditsRect.contains(Game.getMouseMovedPoint())) {
			start = false;
			credits = true;
			exit = false;
			settings = false;
		}
		else if(exitRect.contains(Game.getMouseMovedPoint())) {
			start = false;
			credits = false;
			exit = true;
			settings = false;
		}
		else {
			start = false;
			credits = false;
			exit = false;
			settings = false;
		}
		
		
		
		
		
		
	}

}
