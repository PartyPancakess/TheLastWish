package states;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import gfx.BrokenObjects;
import gfx.CutScene;
import gfx.Resources;
import gfx.SeePages;
import main.BlackMarket;
import main.DeathScreen;
import main.Game;
import main.Level;
import objects.Bullet;
import objects.Enemy;
import objects.Money;
import objects.Player;
import objects.StaticObject;
import objects.Thomson;
import tiles.Tile;

public class GameState extends State{

	
	private static int playerSpawnX;
	private static int playerSpawnY;
	
	private static Player player;
	private Level level, level2;
	private Level[] levels = new Level[3];
	private Game game;
	
	private static Point defaultPoint = new Point(0,0);

	
	private static List<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();
	private static List<Bullet> enemyBullets = new CopyOnWriteArrayList<Bullet>();
	private static ArrayList <StaticObject> staticObjectList = new ArrayList<StaticObject>();
	private static ArrayList <BrokenObjects> brokenObjectList = new ArrayList<BrokenObjects>();
	private static ArrayList <Money> droppedMoneyList = new ArrayList<Money>();
	
	private static Rectangle HPOut, HPIn, StaminaOut, StaminaIn;
	
	private boolean marketSpaceActive = false;
	private boolean fire = true, oneShot = false;;
	private static boolean marketOpen = false, GameOver = false, levelPassed = false;
	private static boolean canShoot = true;
	private long lastTime10 = System.currentTimeMillis();
	private long lastTime11 = System.currentTimeMillis();
	private long thomsonShootTime = 65;
	private long timeToShoot = 100;
	
	public static int currentLevel = 0;
	
	private DeathScreen deathScreen;
	
	private CutScene[] cutScenes = new CutScene[levels.length];
	
	public static SeePages[] seePagesList = new SeePages[3];
	
	public int lastLevel = levels.length-1;
	
	
	
	public GameState(Game gameIn) {
		super(gameIn);
		player = new Player(level, gameIn, (int)playerSpawnX, (int)playerSpawnY);
		levels[currentLevel] = level = new Level(gameIn, "/levels/world1.txt");
		Player.setCurrentWeapon(levels[currentLevel].getPistol());
		game = gameIn;
		deathScreen = new DeathScreen(level, game);
		
		HPOut = new Rectangle((int)(game.getWidth() / 136.6), (int)(game.getHeight() - (6 * game.getHeight() / 76.8)), 
							  (int)(game.getWidth() / 6.83), (int)(game.getHeight() / 38.4));
		HPIn = new Rectangle((int)(game.getWidth() / 136.6), (int)(game.getHeight() - (6 * game.getHeight() / 76.8)), 
							 (int)(game.getWidth() / 6.83), (int)(game.getHeight() / 38.4));
		StaminaOut = new Rectangle((int)(game.getWidth() / 136.6), (int)(game.getHeight() - (3 * game.getHeight() / 76.8)), 
								   (int)(game.getWidth() / 6.83), (int)(game.getHeight() / 38.4));
		StaminaIn = new Rectangle((int)(game.getWidth() / 136.6), (int)(game.getHeight() - (3 * game.getHeight() / 76.8)), 
								  (int)(game.getWidth() / 6.83), (int)(game.getHeight() / 38.4));
		
		for(int i=0; i<cutScenes.length;i++) {
			cutScenes[i] = new CutScene(lastLevel, i, "Level: " + (i+2));
		}

	}

	@Override
	public void update() {
		if(!GameOver) {
			if(game.isShiftButtonActive()) {
				marketOpen = false;
			}
			if(!marketOpen && canShoot) {
				levels[currentLevel].update();
					player.update();
					game.getgamecam().center(player);
					
				if(Player.getCurrentWeapon().getName().equals("thomson")) {
					oneShot = false;
					if(lastTime10 + thomsonShootTime <= System.currentTimeMillis() && fire) {
						fire = false;
						lastTime10 = System.currentTimeMillis();
						}
					if(lastTime11 + timeToShoot <= System.currentTimeMillis()) {
						fire = true;
						lastTime11 = System.currentTimeMillis();
						}
				}else{
					fire = true;
					oneShot = true;
				}
				if(game.isLeftButtonPressed() && fire) {
					if(Player.getCurrentWeapon().getAmmo() > 0) {
						createBullet(Game.mousePressedX, Game.mousePressedY);
						Player.getCurrentWeapon().setAmmo(Player.getCurrentWeapon().getAmmo()-1);
							if(oneShot) {
								game.setLeftButtonPressed(false);
							}
					}else {
						Resources.stop(Resources.emptyClipSound);
						Resources.play(Resources.emptyClipSound);
					}
				}
				
				
				for (Bullet bullet : bullets){
		            bullet.update();
		        }
				
				for(int i=0;i<enemyBullets.size();i++) {
					enemyBullets.get(i).update();
				}
				
				for (Money droppedMoney : droppedMoneyList){
					droppedMoney.update();
				}
				
				for(int j=0;j<Tile.marketTiles.size();j++) {
					if(player.getRect().intersects((Tile.marketTiles.get(j).getRect()))){
						marketSpaceActive = true;
					}else {
						marketSpaceActive = false;
					}
				}
				
				if(game.isRightButtonPressed() && marketSpaceActive) {
					marketOpen = true;
				}
			}
			else {
				player.soundsOfWalking();
				levels[currentLevel].getMarkets()[currentLevel].update();
			}
		}
		else {
			player.soundsOfWalking();
			deathScreen.update();
			if(game.isRButtonActive()) {
				ResetGame();
			}
		}
		if(game.isEscapeButtonActive()) {
			State.setcurrentState(Game.menuState);
			Game.setMousePressedPoint(defaultPoint);
		}
		if(Level.getEnemyList().isEmpty()) {
			levels[currentLevel].setLevelPassed(true);
			for(int i=0;i<Tile.arrowTiles.size();i++) {
				if(Tile.arrowTiles.get(i).getRect().intersects(player.getRect())) {
					//if(currentLevel<levels.length-1) {
					levelPassed = true;
					seePagesList[currentLevel] = new SeePages(lastLevel);
					if(!cutScenes[currentLevel].isCutSceneOver()) {
						canShoot = false;
						cutScenes[currentLevel].update();
					}else {
						Resources.stop(Resources.teleport);
						Resources.play(Resources.teleport);
						LoadLevel();
						levelPassed = false;
						canShoot = true;
					}
//					}else {
//					System.out.println("last level");
//					//do the ending screen stuff here for update
//					}
				}
			}
		}
		
		Bars();
		
	}
	
	private void Bars() {
		if(Game.width>=1366) {
				HPIn.width = (player.getHP()*10)/(game.getWidth()/1366);
				StaminaIn.width = ((int)player.getStamina()*20/3)/(game.getWidth()/1366);
		}else {
				HPIn.width = (player.getHP()*10) * (1366 / game.getWidth());
				StaminaIn.width = ((int)player.getStamina()*20/3) * (1366 / game.getWidth());
		}
	}

	@Override
	public void render(Graphics graphics) {
		levels[currentLevel].render(graphics);
		
		for (Bullet bullet : bullets){
			bullet.render(graphics);
		}
		
		for(int i=0;i<enemyBullets.size();i++) {
			enemyBullets.get(i).render(graphics);
		}
		
		player.render(graphics);
		
		if(marketOpen) {
			levels[currentLevel].getMarkets()[currentLevel].render(graphics);
		}
		if(GameOver) {
			deathScreen.render(graphics);
		}
		
		if(marketSpaceActive && !marketOpen) {
			Resources.drawString(graphics, "Press Right Mouse", game.getWidth() / 2, game.getHeight() / 2, true, Color.BLACK, Resources.font18);
			Resources.drawString(graphics, "Button To Open Market", game.getWidth() / 2, game.getHeight() / 2 + 18, true, Color.BLACK, Resources.font18);
		}
		graphics.setColor(Color.RED);
		graphics.drawRect(HPOut.x, HPOut.y, HPOut.width, HPOut.height);
		graphics.fillRect(HPIn.x, HPIn.y, HPIn.width, HPIn.height);
		
		graphics.setColor(Color.GREEN);
		graphics.drawRect(StaminaOut.x, StaminaOut.y, StaminaOut.width, StaminaOut.height);
		graphics.fillRect(StaminaIn.x, StaminaIn.y, StaminaIn.width, StaminaIn.height);
		
		Resources.drawString(graphics, "Ammo: " + Player.getCurrentWeapon().getAmmo(), HPOut.x, HPOut.y - 20, false, Color.BLACK, Resources.font18);
		
		if(levelPassed) {
			if(!cutScenes[currentLevel].isCutSceneOver()) {
				cutScenes[currentLevel].render(graphics);
			}
		}
	}

	
	// To Move The Player (Inputs)
	public static Player getPlayer() {
		return player;
	}
	
	public static void setPlayerSpawn(int x, int y) {
		playerSpawnX = x;
		playerSpawnY = y;
	}
	
	public void createBullet(double targetx, double targety) {
		Bullet bullet = new Bullet(level, game, player, targetx, targety);
		if(Player.getCurrentWeapon().getName().equals("thomson")) {
			Resources.stop(Resources.thomsonsound);
			Resources.play(Resources.thomsonsound);
		}
		else if(Player.getCurrentWeapon().getName().equals("pistol")) {
			Resources.stop(Resources.pistolsound);
			Resources.play(Resources.pistolsound);
		}
		else if(Player.getCurrentWeapon().getName().equals("shotgun")) {
			Resources.stop(Resources.shotgunsound);
			Resources.play(Resources.shotgunsound);
		}
		bullets.add(bullet);
	}
	
	public void LoadLevel() {
		String path;
		bullets.clear();
		enemyBullets.clear();
		staticObjectList.clear();
		brokenObjectList.clear();
		droppedMoneyList.clear();
		Level.getEnemyList().clear();
		Level.getDeadBodies().clear();
		Tile.grasstileList.clear();
		Tile.stonetileList.clear();
		Tile.dirttileList.clear();
		Tile.arrowTiles.clear();
		Tile.marketTiles.clear();
		
		currentLevel++;
		
		path = "/levels/world" + (int)(currentLevel+1) + ".txt";
		levels[currentLevel] = new Level(game, path);
	}
	
	public void ResetGame() {
		String path;
		bullets.clear();
		enemyBullets.clear();
		staticObjectList.clear();
		brokenObjectList.clear();
		droppedMoneyList.clear();
		Level.getEnemyList().clear();
		Level.getDeadBodies().clear();
		Tile.grasstileList.clear();
		Tile.stonetileList.clear();
		Tile.dirttileList.clear();
		Tile.arrowTiles.clear();
		Tile.marketTiles.clear();
		
		currentLevel = 0;
		
		player.setHP(player.getMaxHP());
		player.setStamina(Player.getStaminaFull());
		Player.setCurrentWeapon(levels[currentLevel].getPistol());
		Player.getCurrentWeapon().setAmmo(Player.getCurrentWeapon().getAmmo());
		Player.setBudget(Player.getBaseBudget());
		
		GameOver = false;
		path = "/levels/world" + (int)(currentLevel + 1) + ".txt";
		levels[currentLevel] = new Level(game, path);
		
//		for(int i=0; i<cutScenes.length;i++) {
//			cutScenes[i] = new CutScene(lastLevel, i, "Level: " + (i+2));
//		}
		
		for(int i=0; i<cutScenes.length;i++) {
			cutScenes[i].setCutSceneOver(false);
		}
	}
	
	
	
	
	
	
	
	//getter setter
	
	public static List<Bullet> getBullets(){
        return bullets;
    }

	public Level getLevel() {
		return levels[currentLevel];
	}

	public void setLevel(Level level) {
		this.levels[currentLevel] = level;
	}

	public static ArrayList<StaticObject> getStaticObjectList() {
		return staticObjectList;
	}

	public static void setStaticObjectList(ArrayList<StaticObject> staticObjectList) {
		GameState.staticObjectList = staticObjectList;
	}

//	public boolean isMarketOpen() {
//		return marketOpen;
//	}
//
//	public void setMarketOpen(boolean marketOpen) {
//		this.marketOpen = marketOpen;
//	}
	
	public static boolean isMarketOpen() {
		return marketOpen;
	}

	public static void setMarketOpen(boolean marketOpen) {
		GameState.marketOpen = marketOpen;
	}
	
	public static ArrayList<BrokenObjects> getBrokenObjectList() {
		return brokenObjectList;
	}

	public static void setBrokenObjectList(ArrayList<BrokenObjects> brokenObjectList) {
		GameState.brokenObjectList = brokenObjectList;
	}

	public static ArrayList<Money> getDroppedMoneyList() {
		return droppedMoneyList;
	}

	public static void setDroppedMoneyList(ArrayList<Money> droppedMoneyList) {
		GameState.droppedMoneyList = droppedMoneyList;
	}

	public static List<Bullet> getEnemyBullets() {
		return enemyBullets;
	}

	public static void setEnemyBullets(List<Bullet> enemyBullets) {
		GameState.enemyBullets = enemyBullets;
	}

	public static boolean isGameOver() {
		return GameOver;
	}

	public static void setGameOver(boolean gameOver) {
		GameOver = gameOver;
	}

	public static SeePages[] getSeePagesList() {
		return seePagesList;
	}

	public static void setSeePagesList(SeePages[] seePagesList) {
		GameState.seePagesList = seePagesList;
	}

	public static boolean isCanShoot() {
		return canShoot;
	}

	public static void setCanShoot(boolean canShoot) {
		GameState.canShoot = canShoot;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
