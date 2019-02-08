package gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;
import states.GameState;

public class SeePages {
	
	private int screenWidth = Game.width, screenHeight = Game.height;
	private long lastTime = System.currentTimeMillis();
	
	private Rectangle backButton, endButton;
	private int buttonWidth = 400, buttonHeight = 70, 
				buttonX = screenWidth / 2 - buttonWidth / 2, buttonY = screenHeight-buttonHeight-20;
	
	private boolean backActive = false, endActive = false;
	
	private boolean seePagesActive = false;
	private boolean isLastLevel = false;
	
	
	public SeePages(int lastLevel) {
		seePagesActive = false;
		backButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
		endButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
		if(lastLevel == GameState.currentLevel) {
			isLastLevel = true;
		}
	}
	
	public void update() {
		if(!isLastLevel) {
			//System.out.println(textX);
			if(backButton.contains(Game.getMouseMovedPoint())) {
				backActive = true;
			}else {
				backActive = false;
			}
			if(backActive) {
				if(backButton.contains(Game.getMousePressedPoint())) {
					seePagesActive = false;
				}
			}
		}else {
			if(endButton.contains(Game.getMouseMovedPoint())) {
				endActive = true;
			}else {
				endActive = false;
			}
			if(endActive) {
				if(endButton.contains(Game.getMousePressedPoint())) {
					System.exit(0);
				}
			}
		}
		
		
		//pages stuff here
		
	}
	
	public void render(Graphics graphics) {
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, screenWidth, screenHeight);
		
		graphics.setColor(Color.GREEN);
		
		if(!isLastLevel) {
			//graphics.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
			if(!backActive) {
				Resources.drawString(graphics, "Next Level", buttonX+buttonWidth/2 , buttonY+buttonHeight/2 - 10, true, Color.WHITE, Resources.font56);
			}else {
				Resources.drawString(graphics, "Next Level", buttonX+buttonWidth/2 , buttonY+buttonHeight/2 - 10, true, Color.RED, Resources.font56);
			}
			Resources.drawString(graphics, "No notes have been found yet", screenWidth/2 , screenHeight/2, true, Color.WHITE, Resources.font18);
		}else {
			//graphics.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
			if(!endActive) {
				Resources.drawString(graphics, "End Game", buttonX+buttonWidth/2 , buttonY+buttonHeight/2 - 10, true, Color.WHITE, Resources.font56);
			}else {
				Resources.drawString(graphics, "End Game", buttonX+buttonWidth/2 , buttonY+buttonHeight/2 - 10, true, Color.RED, Resources.font56);
			}
			
			graphics.drawImage(Resources.HelloWorldPaper, screenWidth / 2 - (844 / 2), screenHeight / 2 - (600 / 2), 844, 600, null);
			
		}
	}

	public boolean isSeePagesActive() {
		return seePagesActive;
	}

	public void setSeePagesActive(boolean seePagesActive) {
		this.seePagesActive = seePagesActive;
	}

	
	

	
	
	
	
	

}
