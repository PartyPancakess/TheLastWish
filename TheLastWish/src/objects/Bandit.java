package objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.Animation;
import gfx.BrokenObjects;
import gfx.Resources;
import main.Game;
import main.Level;
import states.GameState;

public class Bandit extends Enemy{
	
	private BufferedImage rotated = Resources.Bandit;
	private BufferedImage still = Resources.Bandit;
	
	
	public Bandit(Level levelIn, Game gameIn, double xIn, double yIn, int widthIn, int heightIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn);
		rect.setRect((xIn + width / 8 + 5), (yIn + height / 8 + 5), (width * 3 / 4 - 5), (height * 3 / 4 - 5));
		Level.getEnemyList().add(this);
		weapon = level.getThomson();
		HP = 3;
		name = "bandit";
		dead = false;
		
		chanceToDrop = (int)(Math.random() * (8));//1 out of every 8 times
		range = 2;
		coinDrop = calculateCoinDrop();
		
		banditanimation = new Animation(250, Resources.banditanimation);
	}
	
	@Override
	public void update() {
		if(HP > 0) {
			speedControl();
			banditanimation.update();
			createLineSight();
			checkVision();
			findDirection();
			if(playerSeen) {
				shooting();
				if(sightLenght > 200) {
					enemyMove();
				}
			}
		}else {
			dead = true;
		}
		
		if(dead) {
			deadBody = new BrokenObjects(Resources.DeadBandit, (int)x, (int)y, (int)width, (int)height);
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
				rotated = Resources.rotateImage2(banditanimation.getCurrentImage(), angle + 1.60);
				graphics.drawImage(rotated, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), (int)width, (int)height, null);
				still = rotated;
			}else {
				graphics.drawImage(still,(int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), (int)width, (int)height, null);
			}
			//graphics.drawLine((int)(sight.getX1() - game.getgamecam().getXoffset()), (int)(sight.getY1()- game.getgamecam().getYoffset()), (int)(sight.getX2()- game.getgamecam().getXoffset()), (int)(sight.getY2()- game.getgamecam().getYoffset()));
		}
	}

}
