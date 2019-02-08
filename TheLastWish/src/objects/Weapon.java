package objects;

import java.awt.Graphics;
import main.Game;
import main.Level;
import states.GameState;

public abstract class Weapon extends Item{

	protected final int BaseCapacity = 20;
	protected final int BaseAmmo = 20;
	protected int ammoCapacity, ammo, damage, cost;
	protected Level level;
	protected Game game;
	protected String name;
	
	
	public Weapon(Level levelIn, Game gameIn, int xIn, int yIn, double widthIn, double heightIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn);
		level = levelIn;
		game = gameIn;
		ammoCapacity = BaseAmmo;
		ammo = BaseAmmo;
		damage = 1;
	}

	@Override
	abstract public void update();

	@Override
	abstract public void render(Graphics graphics);

	public int getAmmoCapacity() {
		return ammoCapacity;
	}

	public void setAmmoCapacity(int ammoCapacity) {
		this.ammoCapacity = ammoCapacity;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	
	
	
	
}
