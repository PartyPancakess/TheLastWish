package objects;

import java.awt.Rectangle;

public class WeaponSlot {
		
	
	private Rectangle slotRect;
	private double x, y;
	private int width = 90, height = 90;
	private Weapon weapon;
	private String cost;
	
	public WeaponSlot(int costIn, Weapon weaponIn, double xIn, double yIn) {
		weapon =  weaponIn;
		x = xIn;
		y = yIn;
		slotRect = new Rectangle((int)x, (int)y, width, height);
		cost = "Cost: " + costIn;
	}


	
	
	
	
	public Rectangle getSlotRect() {
		return slotRect;
	}
	
	public void setSlotRect(Rectangle slotRect) {
		this.slotRect = slotRect;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public String getCost() {
		return cost;
	}
	
	public void setCost(String cost) {
		this.cost = cost;
	}
	
	
	
	
	
	
	
	
}
