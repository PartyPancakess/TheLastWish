package gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;
import states.GameState;

public class CutScene {
	
	private int screenWidth = Game.width, screenHeight = Game.height;
	private String slidingText;
	private int textX, textY;
	private int textWidth, textHeight;
	private int slidingSpeed;
	private boolean cutSceneOver = false;
	
	private long lastTime = System.currentTimeMillis();
	
	private Rectangle nextLevelButton, collectedButton;
	private int buttonWidth = 400, buttonHeight = 70, 
				buttonX = screenWidth / 2 - buttonWidth / 2, buttonY = screenHeight-buttonHeight-20;
	
	private boolean nextButtonActive = false, collectedButtonActive = false, movingRight = true;
	private boolean seePagesActive = false;
	
	private boolean isLastLevel = false;
	
	private int no = 0;
	
	
	public CutScene(int lastLevel, int no, String text) {
		slidingText = text;
		textHeight = 56;
		textWidth = 300;//smh;
		textX = 0;//-textWidth;
		textY = screenHeight / 2 - textHeight / 2;
		slidingSpeed = 5;
		this.no = no;
		
		
		nextLevelButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
		collectedButton = new Rectangle(buttonX, buttonY - buttonHeight - 20, buttonWidth, buttonHeight);
		
		if(lastLevel == no) {
			isLastLevel = true;
		}
	}
	
	public void update() {
		if(!GameState.seePagesList[GameState.currentLevel].isSeePagesActive()) {
			//System.out.println(textX);
			if(!isLastLevel) {
				if(nextLevelButton.contains(Game.getMouseMovedPoint())) {
					nextButtonActive = true;
				}else {
					nextButtonActive = false;
				}
				if(nextButtonActive) {
					if(nextLevelButton.contains(Game.getMousePressedPoint())) {
						cutSceneOver = true;
					}
				}
			}else {
				if(nextLevelButton.contains(Game.getMouseMovedPoint())) {
					nextButtonActive = true;
				}else {
					nextButtonActive = false;
				}
				if(nextButtonActive) {
					if(nextLevelButton.contains(Game.getMousePressedPoint())) {
						System.exit(0);
					}
				}
			}
			
			if(collectedButton.contains(Game.getMouseMovedPoint())) {
				collectedButtonActive = true;
			}else {
				collectedButtonActive = false;
			}
			if(collectedButtonActive) {
				if(collectedButton.contains(Game.getMousePressedPoint())) {
					//GameState.seePagesList[GameState.currentLevel].setSeePagesActive(true);
					GameState.seePagesList[no].setSeePagesActive(true);
					seePagesActive = true;
				}
			}
			
			if(lastTime + 10 < System.currentTimeMillis()) {
				lastTime = System.currentTimeMillis();
				if(movingRight)
					textX += slidingSpeed;
				else
					textX -= slidingSpeed;
				
				if(textX+textWidth >= screenWidth) {
					movingRight = false;
				}else if(textX <= 0) {
					movingRight = true;
				}
			}
		}else {//see pages is true
			//GameState.getSeePagesList()[GameState.currentLevel].update();
			GameState.getSeePagesList()[no].update();
		}
		
		
		
	}
	
	public void render(Graphics graphics) {
		if(!seePagesActive) {
			graphics.setColor(Color.black);
			graphics.fillRect(0, 0, screenWidth, screenHeight);
			
			if(!isLastLevel) {
				Resources.drawString(graphics, slidingText, textX, textY, false, Color.WHITE, Resources.font56);
			
				//graphics.setColor(Color.GREEN);
				//graphics.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
				if(!nextButtonActive) {
					Resources.drawString(graphics, "Next Level", buttonX+buttonWidth/2 , buttonY+buttonHeight/2 - 10, true, Color.WHITE, Resources.font56);
				}else {
					Resources.drawString(graphics, "Next Level", buttonX+buttonWidth/2 , buttonY+buttonHeight/2 - 10, true, Color.RED, Resources.font56);
				}
			}else {
				Resources.drawString(graphics, "Well Done", textX, textY, false, Color.WHITE, Resources.font56);
				Resources.drawString(graphics, "You have found a note", 30, 30, false, Color.WHITE, Resources.font18);
				
				//graphics.setColor(Color.GREEN);
				//graphics.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
				if(!nextButtonActive) {
					Resources.drawString(graphics, "End Game", buttonX+buttonWidth/2 , buttonY+buttonHeight/2 - 10, true, Color.WHITE, Resources.font56);
				}else {
					Resources.drawString(graphics, "End Game", buttonX+buttonWidth/2 , buttonY+buttonHeight/2 - 10, true, Color.RED, Resources.font56);
				}
			}
			
			
			//graphics.fillRect(collectedButton.x, collectedButton.y, collectedButton.width, collectedButton.height);
			if(!collectedButtonActive) {
				Resources.drawString(graphics, "See Pages", collectedButton.x+buttonWidth/2 , collectedButton.y+buttonHeight/2 - 10, true, Color.WHITE, Resources.font56);
			}else {
				Resources.drawString(graphics, "See Pages", collectedButton.x+buttonWidth/2 , collectedButton.y+buttonHeight/2 - 10, true, Color.GREEN, Resources.font56);
			}
		}else {
			GameState.getSeePagesList()[GameState.currentLevel].render(graphics);
		}
	
	
	}

	public boolean isCutSceneOver() {
		return cutSceneOver;
	}

	public void setCutSceneOver(boolean cutSceneOver) {
		this.cutSceneOver = cutSceneOver;
	}
	
	
	

}
