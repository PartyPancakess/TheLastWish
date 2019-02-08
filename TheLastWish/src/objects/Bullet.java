package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import gfx.Resources;
import main.Game;
import main.Level;
import states.GameState;
import tiles.Tile;

public class Bullet extends Object{
	
	private static int bulletW = 18, bulletH = 18;
	private static int bulletSpeed = 10;
	private Creature creature;
	private boolean isPlayer = false;
	
	private double x;
	private double y;
	
	double dx;
	double dy;
	double angle;
	double xSpeed;
	double ySpeed;
	
	
	public Bullet(Level levelIn, Game gameIn, Creature creatureIn, double targetxIn, double targetyIn) {
		super(levelIn, gameIn, creatureIn.getX(), creatureIn.getY(), bulletW, bulletH);
		x = creatureIn.getX() + creatureIn.getWidth()/2 - 4;
		y = creatureIn.getY() + creatureIn.getHeight()/2 - bulletH/2;
		rect.setRect((x + width / 8), (y + height / 8), (width * 3 / 4), (height * 3 / 4));
		creature = creatureIn;
		
//		dx = Game.mousePressedX - (x - game.getgamecam().getXoffset());
//		dy = Game.mousePressedY - (y - game.getgamecam().getYoffset());
		dx = targetxIn - (x - game.getgamecam().getXoffset());
		dy = targetyIn - (y - game.getgamecam().getYoffset());
		
		angle = Math.atan2(dy, dx);
		
		xSpeed = bulletSpeed * Math.cos(angle);
	    ySpeed = bulletSpeed * Math.sin(angle);
	    
	}

	@Override
	public void update() {
		rect.setRect(rect.getX() + xSpeed, rect.getY(), rect.getWidth(), rect.getHeight());
		rect.setRect(rect.getX(), rect.getY() + ySpeed, rect.getWidth(), rect.getHeight());
		x += xSpeed;
		y += ySpeed;
		
		
		//conditions
		for(int i=0;i<Tile.stonetileList.size();i++) {
			if(this.rect.intersects(Tile.stonetileList.get(i).getRect())) {
				removeBullet();
				removeEnemyBullet();
			}
		}
		
		if(x > Level.getWidth() * Tile.getWidth() + 50)
			removeBullet();
		else if(x < -50)
			removeBullet();
		if(y < -50)
			removeBullet();
		else if(y > Level.getHeight() * Tile.getHeight() + 50)
			removeBullet();
			
	}

	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(Resources.Bulletimg, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), bulletW, bulletH, null);
		
		//graphics.setColor(Color.red);
		//graphics.fillRect((int)(rect.getX() - game.getgamecam().getXoffset()), (int)(rect.getY() - game.getgamecam().getYoffset()), (int)rect.getWidth(), (int)rect.getHeight());
	}
	
	
	
	public void removeBullet(){
        GameState.getBullets().remove(this);
    }
	
	public void removeEnemyBullet() {
		GameState.getEnemyBullets().remove(this);
	}

	public Creature getCreature() {
		return creature;
	}

	public void setCreature(Creature creature) {
		this.creature = creature;
	}

}
