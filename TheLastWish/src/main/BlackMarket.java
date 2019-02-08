package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import gfx.Resources;
import objects.Item;
import objects.Player;
import objects.Weapon;
import objects.WeaponSlot;
import states.GameState;
import states.State;

public class BlackMarket {

	
	private Level level;
	private Game game;
	private Weapon[] weapons;
	
	private static int width = Game.width / 2, height = Game.height / 2;
	private static int x = Game.width / 2 - width / 2, y = Game.height / 2 - height / 2;
	public static double healthX, healthY, ammoX, ammoY;
	
	public static double slot1X, slot1Y, slot2X, slot2Y,slot3X, slot3Y;
	
	private static Rectangle slotAmmo, slotHealth;
	
	private static boolean ammoSlot = false, healthSlot = false, weaponSlot1 = false, weaponSlot2 = false, weaponSlot3 = false;
	private int ammoCost = 1, healthCost = 2;
	//private WeaponSlot wSlot1, wSlot2, wSlot3;
	private WeaponSlot[] wslot = new WeaponSlot[3];
	private double slotX[] = new double[3];
	private double slotY[] = new double[3];
	
	//private double slotWidth = width/7.59, sideWidth = width/11.38, middleWidth = width/4.88;
	private double slotWidth = 90.0*Game.width/1366.0, sideWidth = width/11.38, middleWidth = width/4.88;
	
	public BlackMarket(Level levelIn, Game gameIn,Weapon...weaponIn) {
		level = levelIn;
		game = gameIn;
		weapons = weaponIn;
		
		if(Game.width>=1366) {
			slot1X = x + (sideWidth)*(Game.width/1366);
			slot2X = (slot1X + slotWidth + middleWidth);
			slot3X = (slot2X + slotWidth + middleWidth);
			
			healthX = x + (sideWidth)*(Game.width/1366);
			ammoX = (slot2X + slotWidth + middleWidth);
		}else {
			slot1X = x + (sideWidth)/(1366/Game.width) + 10;
			slot2X = (slot1X + slotWidth + middleWidth) + 5;
			slot3X = (slot2X + slotWidth + middleWidth) + 5;
			
			healthX = x + (sideWidth)/(1366/Game.width)+10;
			ammoX = (slot2X + slotWidth + middleWidth);
		}
		if(Game.height>=768) {
			slot1Y = y + (height/1.61)*(Game.height/768);
			slot2Y = y + ((height/1.61))*(Game.height/768);
			slot3Y = y + ((height/1.61))*(Game.height/768);
			
			healthY = y + (height/4)*(Game.height/768);
			ammoY = y + (height/4)*(Game.height/768);
		}else {
			slot1Y = y + (height/1.61)/(768/Game.height);
			slot2Y = y + ((height/1.61))/(768/Game.height);
			slot3Y = y + ((height/1.61))/(768/Game.height);
			
			healthY = y + (height/4)/(768/Game.height);
			ammoY = y + (height/4)/(768/Game.height);
		}
		
		slotX[0] = (slot1X); slotX[1] = (slot2X); slotX[2] = (slot3X);
		slotY[0] = (slot1Y); slotY[1] = (slot2Y); slotY[2] = (slot3Y);
		
		
		slotAmmo = new Rectangle((int)ammoX, (int)ammoY, 90, 90);
		slotHealth = new Rectangle((int)healthX, (int)healthY, 90, 90);
		
		
		for(int i=0;i<weapons.length;i++) {
			wslot[i] = new WeaponSlot(weapons[i].getCost(), weapons[i], slotX[i], slotY[i]);
		}
	}
	
	public void update() {
		mouseMovement();
		int ammoDifference = Player.getCurrentWeapon().getAmmoCapacity() - Player.getCurrentWeapon().getAmmo();
		if(ammoSlot) {
			if(slotAmmo.contains(Game.getMousePressedPoint())){
				if(slotAmmo.contains(Game.getMouseRelesedPoint()) && game.isLeftButtonPressed()) {
					if(Player.getBudget() >= ammoCost) {
						if(Player.getCurrentWeapon().getName().equals("thomson")) {
							if(ammoDifference >= 20) {
								Player.getCurrentWeapon().setAmmo(Player.getCurrentWeapon().getAmmo() + 20);
								Player.setBudget(Player.getBudget() - ammoCost);
								Resources.stop(Resources.buy);
								Resources.play(Resources.buy);
								game.setLeftButtonPressed(false);
							}
							else if(ammoDifference > 0) {
								Player.getCurrentWeapon().setAmmo(Player.getCurrentWeapon().getAmmo() + ammoDifference);
								Player.setBudget(Player.getBudget() - ammoCost);
								Resources.stop(Resources.buy);
								Resources.play(Resources.buy);
								game.setLeftButtonPressed(false);
						}
						}else {
							if(ammoDifference >= 10) {
								Player.getCurrentWeapon().setAmmo(Player.getCurrentWeapon().getAmmo() + 10);
								Player.setBudget(Player.getBudget() - ammoCost);
								Resources.stop(Resources.buy);
								Resources.play(Resources.buy);
								game.setLeftButtonPressed(false);
							}
							else if(ammoDifference > 0) {
								Player.getCurrentWeapon().setAmmo(Player.getCurrentWeapon().getAmmo() + ammoDifference);
								Player.setBudget(Player.getBudget() - ammoCost);
								Resources.stop(Resources.buy);
								Resources.play(Resources.buy);
								game.setLeftButtonPressed(false);
							}
						}
					}
				}
			}
		}
		
		if(healthSlot) {
			int healthDifference = GameState.getPlayer().getMaxHP() - GameState.getPlayer().getHP();
			
			if(slotHealth.contains(Game.getMousePressedPoint())){
				if(slotHealth.contains(Game.getMouseRelesedPoint()) && game.isLeftButtonPressed()) {
					if(Player.getBudget() >= healthCost) {
						if(healthDifference >= 5) {
							GameState.getPlayer().setHP(GameState.getPlayer().getHP() + 5);
							Player.setBudget(Player.getBudget() - healthCost);
							Resources.stop(Resources.buy);
							Resources.play(Resources.buy);
							game.setLeftButtonPressed(false);
						}
						else if(healthDifference > 0) {
							GameState.getPlayer().setHP(GameState.getPlayer().getHP() + healthDifference);
							Player.setBudget(Player.getBudget() - healthCost);
							Resources.stop(Resources.buy);
							Resources.play(Resources.buy);
							game.setLeftButtonPressed(false);
						}
					}
				}
			}
		}
		
		if(weaponSlot1 && Player.getCurrentWeapon() != wslot[0].getWeapon()) {
			if(wslot[0].getSlotRect().contains(Game.getMousePressedPoint())){
				if(wslot[0].getSlotRect().contains(Game.getMouseRelesedPoint()) && game.isLeftButtonPressed()) {
					if(Player.getBudget() >= wslot[0].getWeapon().getCost()) {
						Player.setCurrentWeapon(wslot[0].getWeapon());
						Player.setBudget(Player.getBudget() - wslot[0].getWeapon().getCost());
						Resources.stop(Resources.buy);
						Resources.play(Resources.buy);
						game.setLeftButtonPressed(false);
					}
				}
			}
		}
		
		if(weaponSlot2 && Player.getCurrentWeapon() != wslot[1].getWeapon()) {
			if(wslot[1].getSlotRect().contains(Game.getMousePressedPoint())){
				if(wslot[1].getSlotRect().contains(Game.getMouseRelesedPoint()) && game.isLeftButtonPressed()) {
					if(Player.getBudget() >= wslot[1].getWeapon().getCost()) {
						Player.setCurrentWeapon(wslot[1].getWeapon());
						Player.setBudget(Player.getBudget() - wslot[1].getWeapon().getCost());
						Resources.stop(Resources.buy);
						Resources.play(Resources.buy);
						game.setLeftButtonPressed(false);
					}
				}
			}
		}
		
		if(weaponSlot3 && Player.getCurrentWeapon() != wslot[2].getWeapon()) {
			if(wslot[2].getSlotRect().contains(Game.getMousePressedPoint())){
				if(wslot[2].getSlotRect().contains(Game.getMouseRelesedPoint()) && game.isLeftButtonPressed()) {
					if(Player.getBudget() >= wslot[2].getWeapon().getCost()) {
						Player.setCurrentWeapon(wslot[2].getWeapon());
						Player.setBudget(Player.getBudget() - wslot[2].getWeapon().getCost());
						Resources.stop(Resources.buy);
						Resources.play(Resources.buy);
						game.setLeftButtonPressed(false);
					}
				}
			}
		}
		
		
		
		
		
		
		
		for(int k=0; k<wslot.length;k++) {
			if(wslot[k] != null) {
				wslot[k].getWeapon().setX(wslot[k].getX());
				wslot[k].getWeapon().setY(wslot[k].getY());
				
				wslot[k].getSlotRect().width = (int)slotWidth;
				wslot[k].getSlotRect().height = (int)slotWidth;
				slotAmmo.width = (int)slotWidth;
				slotAmmo.height = (int)slotWidth;
				slotHealth.width = (int)slotWidth;
				slotHealth.height = (int)slotWidth;
			}
		}
		
	}
	
	public void render(Graphics graphics) {
		graphics.drawImage(Resources.BlackMarket, x, y, width, height, null);
		Resources.drawString(graphics, "Coins: " + Player.getBudget(), (int)(x + width / 2), (int)(y + slotWidth / 2), true, Color.CYAN, Resources.font18);
		Resources.drawString(graphics, "Cost: " + ammoCost, (int)(slotAmmo.getX() + slotAmmo.getWidth() / 2), (int)(slotAmmo.getY() - slotAmmo.getHeight() / 2), true, Color.GREEN, Resources.font18);
		Resources.drawString(graphics, "Cost: " + healthCost, (int)(slotHealth.getX() + slotHealth.getWidth() / 2), (int)(slotHealth.getY() - slotHealth.getHeight() / 2), true, Color.RED, Resources.font18);
		Resources.drawString(graphics, "Press Shift Button To Close", x + width / 2, y + height + 10, true, Color.BLACK, Resources.font18);
		
		for(int i=0;i<wslot.length;i++) {
			if(wslot[i] != null) {
				
				wslot[i].getWeapon().render(graphics);
				
				Resources.drawString(graphics, wslot[i].getCost(), (int)wslot[i].getX(),  (int)wslot[i].getY() - wslot[i].getHeight() / 4, false, Color.RED, Resources.font18);
			}
		}
		
//		graphics.fillRect(slotHealth.x,slotHealth.y,slotHealth.width,slotHealth.height);
//		graphics.fillRect(slotAmmo.x,slotAmmo.y,slotAmmo.width,slotAmmo.height);
//		
//		graphics.fillRect((int)wslot[0].getSlotRect().getX(), (int)wslot[0].getSlotRect().getY(), (int)wslot[0].getSlotRect().getWidth(),(int) wslot[0].getSlotRect().getHeight());
//		graphics.fillRect((int)wslot[1].getSlotRect().getX(), (int)wslot[1].getSlotRect().getY(), (int)wslot[1].getSlotRect().getWidth(), (int)wslot[1].getSlotRect().getHeight());
//		graphics.fillRect((int)wslot[2].getSlotRect().getX(), (int)wslot[2].getSlotRect().getY(), (int)wslot[2].getSlotRect().getWidth(), (int)wslot[2].getSlotRect().getHeight());
	}
	
	public void mouseMovement() {
		if(slotAmmo.contains(Game.getMouseMovedPoint())) {
			ammoSlot = true;
		}
		if(slotHealth.contains(Game.getMouseMovedPoint())) {
			healthSlot = true;
		}
		if(wslot[0].getSlotRect().contains(Game.getMouseMovedPoint())) {
			weaponSlot1 = true;
		}
		if(wslot[1].getSlotRect().contains(Game.getMouseMovedPoint())) {
			weaponSlot2 = true;
		}
		if(wslot[2].getSlotRect().contains(Game.getMouseMovedPoint())) {
			weaponSlot3 = true;
		}
	
	}
}