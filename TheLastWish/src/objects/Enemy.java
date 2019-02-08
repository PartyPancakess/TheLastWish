package objects;

import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import gfx.Animation;
import gfx.BrokenObjects;
import gfx.Resources;
import main.Game;
import main.Level;
import states.GameState;
import tiles.Tile;

public abstract class Enemy extends Creature{

	protected Line2D.Double sight;
	protected boolean canSeePlayer = false, playerSeen = false, blocked = false, shotted = false;
	protected long lastTime3 = System.currentTimeMillis();
	protected long lastTime4 = System.currentTimeMillis();
	protected long timeToForget = 6000, timeToShoot = 1000;
	protected double sightLenght = 0, distanceToForget = 600, dx, dy, angle;
	protected static boolean alreadyDamaged = false;
	protected long lastTime6 = System.currentTimeMillis();
	protected long PlayerAttackTime = 800;
	
	protected Animation banditanimation;
	protected Animation shotgunneranimation;
	protected Animation greentankanimation;
	protected Weapon weapon;
	protected Game game;
	protected Level level;
	protected BrokenObjects deadBody;
	protected boolean dead = false;
	protected String name;
	protected Money dropMoney;
	
	protected int range, coinDrop, chanceToDrop;
	
	
	
	public Enemy(Level levelIn, Game gameIn, double xIn, double yIn, int widthIn, int heightIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn);
		Speed = 2.0f;
		level = levelIn;
		game = gameIn;
		createLineSight();
		
		chanceToDrop = (int)(Math.random() * (3));//1 out of every 3 times
		range = 4;
		coinDrop = calculateCoinDrop();
	}
	
	protected int calculateCoinDrop() {
		return (int)(Math.random() * range) + 1;
	}
	
	protected void isShoot() {
		if(HP > 0) {
			if(GameState.getPlayer().getRect().intersects(this.rect) && !alreadyDamaged && game.isQbuttonActive()) {
				Resources.stop(Resources.PlayerHit);
				Resources.play(Resources.PlayerHit);
				HP --;
				alreadyDamaged = true;
			}
			for(int i=0;i<GameState.getBullets().size();i++) {
				if(GameState.getBullets().get(i).getRect().intersects(this.rect)) {
					HP -= Player.getCurrentWeapon().getDamage();
					GameState.getBullets().remove(i);
					playerSeen = true;
					shotted = true;
				}
			}
			
			if(lastTime6 + PlayerAttackTime <= System.currentTimeMillis()) {
				alreadyDamaged = false;
				lastTime6 = System.currentTimeMillis();
			}
			
		}else {
			Level.getEnemyList().remove(this);
		}
	}
	
	protected void shooting() {
		if(lastTime4 + timeToShoot <= System.currentTimeMillis()) {
			Bullet bullet = new Bullet(level, game, this, GameState.getPlayer().getX() + GameState.getPlayer().getWidth() / 2 - game.getgamecam().getXoffset(), 
														  GameState.getPlayer().getY() + GameState.getPlayer().getHeight() / 2 - game.getgamecam().getYoffset());
			Resources.stop(Resources.thomsonsound);
			Resources.play(Resources.thomsonsound);
			GameState.getEnemyBullets().add(bullet);
			lastTime4 = System.currentTimeMillis();
		}
	}
	
	protected void shootingShotgun() {
		if(lastTime4 + timeToShoot <= System.currentTimeMillis()) {
			Bullet bullet = new Bullet(level, game, this, GameState.getPlayer().getX() + GameState.getPlayer().getWidth() / 2 - game.getgamecam().getXoffset(), 
														  GameState.getPlayer().getY() + GameState.getPlayer().getHeight() / 2 - game.getgamecam().getYoffset());
			Resources.stop(Resources.shotgunsound);
			Resources.play(Resources.shotgunsound);
			GameState.getEnemyBullets().add(bullet);
			lastTime4 = System.currentTimeMillis();
		}
	}
	
	protected void createLineSight() {
		sight = new Line2D.Double((x + (width / 2)), (y + height / 2), 
				(GameState.getPlayer().getX() +  (GameState.getPlayer().getWidth() / 2)), 
				(GameState.getPlayer().getY() + (GameState.getPlayer().getHeight() / 2)));
		dx = sight.getX2() - sight.getX1();
		dy = sight.getY2() - sight.getY1();
		angle = Math.atan2(dy, dx);
		sightLenght = Math.sqrt(Math.pow(dx, 2) + (Math.pow(dy, 2)));
	}
	
	protected void checkVision() {
		blocked = false;
			for(int i=0;i<GameState.getStaticObjectList().size();i++) {
				if(sight.intersects(GameState.getStaticObjectList().get(i).getRect()) && !blocked) {
					blocked = true;
				}
			}
			for(int j=0;j<Tile.stonetileList.size();j++) {
				if(sight.intersects(Tile.stonetileList.get(j).getRect())) {
					blocked = true;
				}
			}
			if(!blocked && sightLenght < distanceToForget) {
				canSeePlayer = true;
				playerSeen = true;
				lastTime3 = System.currentTimeMillis();
			}
			if(playerSeen && shotted && sightLenght > distanceToForget) {
				playerSeen = true;
				lastTime3 = System.currentTimeMillis();
			}
			if (blocked || sightLenght > distanceToForget){
				canSeePlayer = false;
			}
			if(!canSeePlayer) {
				if(lastTime3 + timeToForget <= System.currentTimeMillis()) {
					playerSeen = false;
				}
			}
	}
	
	protected void findDirection() {
			if(x == GameState.getPlayer().getX() || x+1 == GameState.getPlayer().getX() || x-1 == GameState.getPlayer().getX()) {
				right = false;
				left = false;
			}
			else if(x > GameState.getPlayer().getX()) {
				left = true;
				right = false;
			}
			else if(x < GameState.getPlayer().getX()) {
				right = true;
				left = false;
			}
			if(y == GameState.getPlayer().getY() || y+1 == GameState.getPlayer().getY() || y-1 == GameState.getPlayer().getY()) {
				down = false;
				up = false;
			}
			else if(y > GameState.getPlayer().getY()) {
				up = true;
				down = false;
			}
			else if(y < GameState.getPlayer().getY()) {
				down = true;
				up = false;
			}
			//System.out.println(GameState.getPlayer().getY() + " " + y);
	}
	
	protected void createAndCheckDeadBody() {
		Level.getDeadBodies().add(deadBody);
		if(Level.getDeadBodies().size() > 6) {
			Level.getDeadBodies().remove(0);
		}
	}
	
	
	

	@Override
	public void update() {
		isShoot();
		if(HP > 0) {
			speedControl();
			banditanimation.update();
			createLineSight();
			checkVision();
			findDirection();
			if(playerSeen) {
				enemyMove();
			}
		}
		
	}
	
	protected void speedControl() {
			for(int j=0;j<Tile.dirttileList.size();j++) {
				if(this.rect.intersects(Tile.dirttileList.get(j).getRect())){
					this.setSpeed(mudSpeed);
					dirt = true;
				}
			}
			for(int i=0;i<Tile.grasstileList.size();i++) {
				if(this.rect.intersects(Tile.grasstileList.get(i).getRect())) {
					this.setSpeed(lowSpeed);
					dirt = false;
				}
			}
		}

	
	
	// getters
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static boolean isAlreadyDamaged() {
		return alreadyDamaged;
	}

	public static void setAlreadyDamaged(boolean alreadyDamaged) {
		Enemy.alreadyDamaged = alreadyDamaged;
	}

	
	
}
