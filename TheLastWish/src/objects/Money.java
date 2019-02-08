package objects;

import java.awt.Graphics;

import gfx.Resources;
import main.Game;
import main.Level;
import states.GameState;

public class Money extends Item{
	
	private int value;//how many coins will it give
	public static int width = 32, height = 32;

	public Money(Level levelIn, Game gameIn, Enemy enemyIn, int xIn, int yIn) {
		super(levelIn, gameIn, xIn, yIn, width, height);
		
		value = enemyIn.coinDrop;
		GameState.getDroppedMoneyList().add(this);
	}
	
	
	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics graphics) {
		if(value == 1) {
			graphics.drawImage(Resources.Coin, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
		}
		else if(value == 2) {
			graphics.drawImage(Resources.Coins, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
		}
		else if(value >= 3) {
			graphics.drawImage(Resources.LotsOfCoins, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
		}
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}
	
	

}
