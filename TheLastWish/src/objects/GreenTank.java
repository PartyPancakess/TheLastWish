package objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.Animation;
import gfx.BrokenObjects;
import gfx.Resources;
import main.Game;
import main.Level;
import states.GameState;

public class GreenTank extends Enemy{
	
	private BufferedImage rotated = Resources.Greentank;
	private BufferedImage still = Resources.Greentank;
	
	
	public GreenTank(Level levelIn, Game gameIn, double xIn, double yIn, int widthIn, int heightIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn);
		rect.setRect((xIn + width / 8 + 5), (yIn + height / 8 + 5), (width * 3 / 4 - 5), (height * 3 / 4 - 5));
		Level.getEnemyList().add(this);
		HP = 10;
		Speed = 1.5f;
		name = "greentank";
		dead = false;
		
		chanceToDrop = (int)(Math.random() * (1));//1 out of every 1 times
		range = 3;
		coinDrop = calculateCoinDrop();
		
		greentankanimation = new Animation(250, Resources.greentankanimation);
	}
	
	@Override
	public void update() {
		if(HP > 0) {
			greentankanimation.update();
			createLineSight();
			checkVision();
			findDirection();
			if(playerSeen) {
					enemyMove();
			}
		}else {
			dead = true;
		}
		
		if(dead) {
			deadBody = new BrokenObjects(Resources.DeadGreenTank, (int)x, (int)y, (int)width, (int)height);
			createAndCheckDeadBody();
			
			if(chanceToDrop == 0) {
				dropMoney = new Money(level, game, this, (int)(x + width/2 - Money.width/2), (int)(y + height/2 - Money.height/2));
			}
		}
		
		isShoot();
	}
	
		

	@Override
	public void render(Graphics graphics) {
		if(HP > 0) {
			if(playerSeen) {
				rotated = Resources.rotateImage2(greentankanimation.getCurrentImage(), angle + 1.60);
				graphics.drawImage(rotated, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), (int)width, (int)height, null);
				still = rotated;
			}else {
				graphics.drawImage(still,(int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), (int)width, (int)height, null);
			}
			//graphics.drawLine((int)(sight.getX1() - game.getgamecam().getXoffset()), (int)(sight.getY1()- game.getgamecam().getYoffset()), (int)(sight.getX2()- game.getgamecam().getXoffset()), (int)(sight.getY2()- game.getgamecam().getYoffset()));
		}
	}

}
