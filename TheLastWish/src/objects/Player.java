package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import gfx.*;
import main.*;
import states.GameState;
import tiles.Tile;

public class Player extends Creature{
	
	private Animation playeranimation, playeranimationrunning, playerpunchanimation;
	private Game game;
	//protected boolean up, down, right, left;  Send them to creature class
	private static int width = BaseWidth, height = BaseHeight;
	private BufferedImage rotated = Resources.player;
	private BufferedImage normalrotated = Resources.player;
	private BufferedImage normalRotatedPunch  = Resources.player;
	private BufferedImage gun = Resources.Gun;
	
	private static Weapon currentWeapon;
	
	//rotate stuff
	private Point mouseMovedPoint = Game.getMouseMovedPoint();
	double dx;
	double dy;
	public static double angle;
	private float stamina = 30;
	private static float staminaFull = 30;
	private static int budget = 0, BaseBudget = 100;
	private boolean done = false, alreadyDamaged = false;
	private long lastTime5 = System.currentTimeMillis();
	private long greenTankAttackTime = 1000;
	
	
	
	
	
	public Player(Level levelIn, Game gameIn, double xIn, double yIn) {
		super(levelIn, gameIn, xIn, yIn, width, height);
		game = gameIn;
		maxHP = 20;
		HP = 20;
		budget = BaseBudget;
		
		//resizer(200, 200);
		rect.setRect((xIn + width / 8 + 5), (yIn + height / 8 + 5), (width * 3 / 4 - 5), (height * 3 / 4 - 5));
		
		playeranimation = new Animation(250, Resources.playeranimation);
		playeranimationrunning = new Animation(100, Resources.playeranimation);
		playerpunchanimation = new Animation(150, Resources.playerpunchanimation);
	}
	
	protected void isShoot() {
		if(HP > 0) {
			for(int i=0;i<GameState.getEnemyBullets().size();i++) {
				if(((Enemy) GameState.getEnemyBullets().get(i).getCreature()).getName().equals("bandit")) {
					if(GameState.getEnemyBullets().get(i).getRect().intersects(this.rect)) {
						HP--;
						GameState.getEnemyBullets().remove(i);
					}
				}else if(((Enemy) GameState.getEnemyBullets().get(i).getCreature()).getName().equals("shotgunner")){
					if(GameState.getEnemyBullets().get(i).getRect().intersects(this.rect)) {
						HP -= 3;
						GameState.getEnemyBullets().remove(i);
					}
				}
			}
			for(int j=0;j<Level.getEnemyList().size();j++) {
				if(Level.getEnemyList().get(j).getName().equals("greentank")) {
					if(Level.getEnemyList().get(j).getRect().intersects(this.rect) && !alreadyDamaged) {
						Resources.stop(Resources.SlimeAttack);
						Resources.play(Resources.SlimeAttack);
						HP -= 5;
						alreadyDamaged = true;
					}
				}
			}
		}else {
			GameState.setGameOver(true);
		}
		
		if(lastTime5 + greenTankAttackTime <= System.currentTimeMillis()) {
			alreadyDamaged = false;
			lastTime5 = System.currentTimeMillis();
		}
	}
	
	private void resizer(int widthIn, int heightIn) {
		width = widthIn;
		height = heightIn;
	}
	
	private void speedControl() {
			for(int j=0;j<Tile.dirttileList.size();j++) {
				if(this.rect.intersects(Tile.dirttileList.get(j).getRect())){
					this.setSpeed(mudSpeed);
					timer = 0;
					dirt = true;
					done = false;
				}
			}
			for(int i=0;i<Tile.grasstileList.size();i++) {
				if(this.rect.intersects(Tile.grasstileList.get(i).getRect()) && !done) {
					dirt = false;
					this.setSpeed(lowSpeed);
					
					timer += System.currentTimeMillis() - lastTime;
					lastTime = System.currentTimeMillis();
					
					if(timer > 500 && !done) {
						this.setSpeed(normalSpeed);
						done = true;
					}
				}
			}
			
			if(game.isShiftButtonActive() && done) {
				if(up||down||left||right) {
					setShiftSpeed();
					if(lastTime2 + 100 < System.currentTimeMillis()) {
						lastTime2 = System.currentTimeMillis();
						if(stamina>0) {
							stamina--;
						}
					}
				}
			}else if(!game.isShiftButtonActive() && done){
				this.Speed = normalSpeed;
				if(lastTime2 + 250 < System.currentTimeMillis()) {
					if(stamina<staminaFull) {
						stamina++;
						lastTime2 = System.currentTimeMillis();
					}
				}
			}
		}
	
	private void moneyCollector() {
		for(int q=0;q<GameState.getDroppedMoneyList().size();q++) {
			if(this.rect.intersects(GameState.getDroppedMoneyList().get(q).getRect())){
				budget += GameState.getDroppedMoneyList().get(q).getValue();
				GameState.getDroppedMoneyList().remove(GameState.getDroppedMoneyList().get(q));
				Resources.stop(Resources.coinsound);
				Resources.play(Resources.coinsound);
			}
		}
	}
	
	public void soundsOfWalking() {
		if(!GameState.isGameOver() && GameState.isCanShoot() && !GameState.isMarketOpen()) {
			if(game.isShiftButtonActive() && stamina > 0 && this.Speed == highSpeed) {
				Resources.loop(Resources.Running);
				Resources.stop(Resources.GrassWalking);
				Resources.stop(Resources.DirtWalking);
			}
			else if((!game.isShiftButtonActive() || stamina <= 0) && this.Speed == normalSpeed && (up || down || left || right)){
				Resources.loop(Resources.GrassWalking);
				Resources.stop(Resources.Running);
				Resources.stop(Resources.DirtWalking);
			}
			else if((this.Speed == mudSpeed || this.Speed == lowSpeed) && (up || down || left || right)) {
				Resources.loop(Resources.DirtWalking);
				Resources.stop(Resources.Running);
				Resources.stop(Resources.GrassWalking);
			}
			else if(!up && !down && !left && !right) {
				Resources.stop(Resources.Running);
				Resources.stop(Resources.GrassWalking);
				Resources.stop(Resources.DirtWalking);
			}
		}
		else {
			Resources.stop(Resources.Running);
			Resources.stop(Resources.GrassWalking);
			Resources.stop(Resources.DirtWalking);
		}
	}
	
	public void update() {
		soundsOfWalking();
		playeranimation.update();
		playeranimationrunning.update();
		playerpunchanimation.update();
		walkingInputs();
		speedControl();
		moneyCollector();
		isShoot();
		
		
		if(x < game.getgamecam().getXoffset()) {
			noLeft();
		}
		
		if(x > Level.getWidth() * Tile.getWidth() - width) {
			noRight();
		}
		
		if(y < game.getgamecam().getYoffset()) {
			noUp();
		}
		
		if(y > Level.getHeight() * Tile.getHeight() - height) {
			noDown();
		}
		
		move();
		

		//rotate stuff
		dx = Game.mousePressedX - (x - game.getgamecam().getXoffset() + width/2);
		dy = Game.mousePressedY - (y - game.getgamecam().getYoffset() + height/2);
		angle = Math.atan2(dy, dx);
	}
	
	


	private void setShiftSpeed() {
		if(stamina>0) {
			this.setSpeed(highSpeed);
		}else {
			this.setSpeed(normalSpeed);
		}
	}

	public void render(Graphics graphics) {
		//graphics.drawImage(playeranimation.getCurrentImage(), (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
//		graphics.setColor(Color.red);
//		graphics.fillRect((int)(rect.getX() - game.getgamecam().getXoffset()), (int)(rect.getY() - game.getgamecam().getYoffset()), (int)rect.getWidth(), (int)rect.getHeight());

		
		//rotation
		if(!GameState.isGameOver() && game.isQbuttonActive()) {
			normalRotatedPunch = Resources.rotateImage2(playerpunchanimation.getCurrentImage(), angle + 1.60);
			graphics.drawImage(normalRotatedPunch, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
		}
		else if(!up && !down && !left && !right && !GameState.isGameOver()) {
			normalrotated = Resources.rotateImage2(Resources.player, angle + 1.60);
			gun = Resources.rotateImage2(Resources.Gun, angle + 1.60);
			graphics.drawImage(normalrotated,(int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
			graphics.drawImage(gun,(int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
		}
		else if((up || down || left || right) && (!GameState.isGameOver() && game.isShiftButtonActive() && stamina > 0 && this.Speed == highSpeed)){
			rotated = Resources.rotateImage2(playeranimationrunning.getCurrentImage(), angle + 1.60);
			gun = Resources.rotateImage2(Resources.Gun, angle + 1.60);
			graphics.drawImage(rotated, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
			graphics.drawImage(gun, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
		}
		else if((up || down || left || right) && !GameState.isGameOver()){
			rotated = Resources.rotateImage2(playeranimation.getCurrentImage(), angle + 1.60);
			gun = Resources.rotateImage2(Resources.Gun, angle + 1.60);
			graphics.drawImage(rotated, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
			graphics.drawImage(gun, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
		}
		else if(GameState.isGameOver()){
			graphics.drawImage(Resources.DeadPlayer, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), width, height, null);
		}
	}
	
	
	
	//getters
	
	public boolean isUp() {
		return up;
	}
	
	public boolean isDown() {
		return down;
	}

	public boolean isRight() {
		return right;
	}

	public boolean isLeft() {
		return left;
	}
	
	//setters
	
	public void setUp(boolean up) {
		this.up = up;
	}
	
	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setRight(boolean right) {
		this.right = right;
	}
	
	public void setLeft(boolean left) {
		this.left = left;
	}

	public static int getBudget() {
		return budget;
	}

	public static void setBudget(int budget) {
		Player.budget = budget;
	}

	public static Weapon getCurrentWeapon() {
		return currentWeapon;
	}

	public static void setCurrentWeapon(Weapon currentWeapon) {
		Player.currentWeapon = currentWeapon;
	}

	public float getStamina() {
		return stamina;
	}

	public void setStamina(float stamina) {
		this.stamina = stamina;
	}

	public static int getBaseBudget() {
		return BaseBudget;
	}

	public static void setBaseBudget(int baseBudget) {
		BaseBudget = baseBudget;
	}

	public static float getStaminaFull() {
		return staminaFull;
	}

	public static void setStaminaFull(float staminaFull) {
		Player.staminaFull = staminaFull;
	}
	
	

}
