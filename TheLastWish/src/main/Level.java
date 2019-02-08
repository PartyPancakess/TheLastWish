package main;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.io.Reader;
import java.io.StringReader;
import javax.xml.stream.*;

import gfx.BrokenObjects;
import objects.Bandit;
import objects.Enemy;
import objects.GreenTank;
import objects.Money;
import objects.Pistol;
import objects.Player;
import objects.ShotGunner;
import objects.Shotgun;
import objects.StaticObject;
import objects.Thomson;
import objects.Tree;
import objects.Weapon;
import states.GameState;
import tiles.*;

public class Level {

	private static int width, height;
	private Point2D.Double startpoint = new Point2D.Double(0, 0);
	private int[][] Maptiles;
	
	Tile grasstile, dirttile, dirttile3, dirttile5, dirttile7, stonetile, uptile, downtile, lefttile, righttile, marketUp, marketDown, asphaltUp, asphaltDown;
	private Weapon thomson, pistol, shotgun;

	private BlackMarket markets[] = new BlackMarket[3];
	private Bandit bandit;
	private ShotGunner shotGunner;
	private GreenTank greenTank;
	private Money money;
	private static boolean isdeleted = false;
	
	private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private static ArrayList<BrokenObjects> deadBodies = new ArrayList<BrokenObjects>();
	
	private Weapon[] addToMarket = new Weapon[3];
	
	private int[] weaponToAdd = new int[3];//0=thomson, 1=pistol, 2=shotgun, 3=... etc (in world.txt)
	
	private boolean levelPassed = false;
	
	StaticObject tree;
	
	Game game;
	
	
	public Level(Game gameIn, String path) {
		String file = LoadFileAsString(path);
		String[] digits = file.split("\\s+");
		
		game = gameIn;
		
		
		width = Integer.parseInt(digits[0]);
		height = Integer.parseInt(digits[1]);
		Maptiles = new int[width][height];
		startpoint.setLocation(Integer.parseInt(digits[2]), Integer.parseInt(digits[3]));
		
		weaponToAdd[0] =  Integer.parseInt(digits[4]);
		weaponToAdd[1] =  Integer.parseInt(digits[5]);
		weaponToAdd[2] =  Integer.parseInt(digits[6]);
		
		
		for(int i = 0;i<width;i++) {
			for(int j = 0;j<height;j++) {
				Maptiles[i][j] = Integer.parseInt(digits[(i + j * width) + 7]);
				
				if(Maptiles[i][j] == 0) {
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 1) {
					dirttile = new DirtTile(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 3) {
					dirttile3 = new DirtTile3(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 5) {
					dirttile5 = new DirtTile5(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 7) {
					dirttile7 = new DirtTile7(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 2) {
					stonetile = new StoneTile(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 4) {//Money
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					money = new Money(this, game, bandit, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 6) {//Money
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					money = new Money(this, game, greenTank, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 8) {//Money
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					money = new Money(this, game, shotGunner, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 10) { //arrows
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					uptile = new UpArrowTile(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 11) {
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					downtile = new DownArrowTile(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 12) {
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					lefttile = new LeftArrowTile(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 13) { //arrows end
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					righttile = new RightArrowTile(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 20) {//tree
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					tree = new Tree(this, game, i * Tile.getWidth(), j * Tile.getHeight(), Tile.getWidth(), Tile.getHeight(), 8);
				}
				else if(Maptiles[i][j] == 30) {//Bandit
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					bandit = new Bandit(this, game, i * Tile.getWidth(), j * Tile.getHeight(), Tile.getWidth(), Tile.getHeight());
				}
				else if(Maptiles[i][j] == 31) {//GreenTank
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					greenTank = new GreenTank(this, game, i * Tile.getWidth(), j * Tile.getHeight(), Tile.getWidth(), Tile.getHeight());
				}
				else if(Maptiles[i][j] == 32) {//ShotGunner
					grasstile = new GrassTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					shotGunner = new ShotGunner(this, game, i * Tile.getWidth(), j * Tile.getHeight(), Tile.getWidth(), Tile.getHeight());
				}
				else if(Maptiles[i][j] == 60) {//Market
					stonetile = new StoneTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					marketUp = new MarketUp(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 61) {//Market
					stonetile = new StoneTile(game, i * Tile.getWidth(), j * Tile.getHeight());
					marketDown = new MarketDown(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 62) {//Market
					asphaltUp = new AsphaltUp(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
				else if(Maptiles[i][j] == 63) {//Market
					asphaltDown = new AsphaltDown(game, i * Tile.getWidth(), j * Tile.getHeight());
				}
			}
		}
		
		
		
		//Move and resize weapons on market screen with game screen sizes
		for(int i=0; i<addToMarket.length;i++) {
			if(weaponToAdd[i]==0) {//thomson
				if(Game.width>=1366) {
					if(Game.height>=768) {
						addToMarket[i] = thomson = new Thomson(this, gameIn, (int)BlackMarket.slot1X, (int)BlackMarket.slot1Y, 120*(Game.width/1366.0), 120*(Game.height/768.0));
					}else {
						addToMarket[i] = thomson = new Thomson(this, gameIn, (int)BlackMarket.slot1X, (int)BlackMarket.slot1Y, 120*(Game.width/1366.0), 120/(768/Game.height));
					}
				}else {
					if(Game.height>=768) {
						addToMarket[i] = thomson = new Thomson(this, gameIn, (int)BlackMarket.slot1X, (int)BlackMarket.slot1Y, 120/(1366.0/Game.width), 120*(Game.height/768.0));
					}else {
						addToMarket[i] = thomson = new Thomson(this, gameIn, (int)BlackMarket.slot1X, (int)BlackMarket.slot1Y, 120/(1366.0/Game.width), 120/(768.0/Game.height));
					}
				}
			}else if(weaponToAdd[i]==1) {//pistol
				if(Game.width>=1366) {
					if(Game.height>=768) {
						addToMarket[i] = pistol = new Pistol(this, gameIn, (int)BlackMarket.slot2X, (int)BlackMarket.slot2Y, 100*(Game.width/1366.0), 100*(Game.height/768.0));
					}else {
						addToMarket[i] = pistol = new Pistol(this, gameIn, (int)BlackMarket.slot2X, (int)BlackMarket.slot2Y, 100*(Game.width/1366.0), 100/(768/Game.height));
					}
				}else {
					if(Game.height>=768) {
						addToMarket[i] = pistol = new Pistol(this, gameIn, (int)BlackMarket.slot2X, (int)BlackMarket.slot2Y, 100/(1366.0/Game.width), 100*(Game.height/768.0));
					}else {
						addToMarket[i] = pistol = new Pistol(this, gameIn, (int)BlackMarket.slot2X, (int)BlackMarket.slot2Y, 100/(1366.0/Game.width), 100/(768.0/Game.height));
					}
				}
			}else if(weaponToAdd[i]==2) {//shotgun
				if(Game.width>=1366) {
					if(Game.height>=768) {
						addToMarket[i] = shotgun = new Shotgun(this, gameIn, (int)BlackMarket.slot3X, (int)BlackMarket.slot3Y, 100*(Game.width/1366.0), 100*(Game.height/768.0));
					}else {
						addToMarket[i] = shotgun = new Shotgun(this, gameIn, (int)BlackMarket.slot3X, (int)BlackMarket.slot3Y, 100*(Game.width/1366.0), 100/(768/Game.height));
					}
				}else {
					if(Game.height>=768) {
						addToMarket[i] = shotgun = new Shotgun(this, gameIn, (int)BlackMarket.slot3X, (int)BlackMarket.slot3Y, 100/(1366.0/Game.width), 100*(Game.height/768.0));
					}else {
						addToMarket[i] = shotgun = new Shotgun(this, gameIn, (int)BlackMarket.slot3X, (int)BlackMarket.slot3Y, 100/(1366.0/Game.width), 100/(768.0/Game.height));
					}
				}
			}
			
		}
		//weapon sizing and creation over
		
		
		markets[GameState.currentLevel] = new BlackMarket(this, gameIn, addToMarket[0], addToMarket[1], addToMarket[2]);
		
		GameState.getPlayer().setRect(new Rectangle2D.Double(startpoint.getX() + GameState.getPlayer().getWidth() / 8 + 5, startpoint.getY() + GameState.getPlayer().getHeight() / 8 + 5, 
									  GameState.getPlayer().getRect().getWidth(), GameState.getPlayer().getRect().getHeight()));
		
		GameState.getPlayer().setX(startpoint.getX());
		GameState.getPlayer().setY(startpoint.getY());
		
		
		
	}
	
	public void update() {
		for(int k=0;k<GameState.getStaticObjectList().size();k++) {
			GameState.getStaticObjectList().get(k).update();
		}
		
		for(int i=0;i< enemyList.size();i++) {
			enemyList.get(i).update();
		}
		
		
	}
	
	public void render(Graphics graphics) {
		int istart = (int) Math.max(0, game.getgamecam().getXoffset() / Tile.getWidth());
		int iend = (int) Math.min(width, (game.getgamecam().getXoffset() + game.getWidth()) / Tile.getWidth() + 1);
		int jstart = (int) Math.max(0, game.getgamecam().getYoffset() / Tile.getHeight());
		int jend = (int) Math.min(height, (game.getgamecam().getYoffset() + game.getHeight()) / Tile.getHeight() + 1);
		
		for(int i = istart;i<iend;i++) {
			for(int j = jstart;j<jend;j++) {
				if(Maptiles[i][j] == 0) {
					grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 1) {
					dirttile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 3) {
					dirttile3.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 5) {
					dirttile5.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 7) {
					dirttile7.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 2) {
					stonetile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 4) {//Money
					grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));				
					}
				else if(Maptiles[i][j] == 6) {//Money
					grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));				
					}
				else if(Maptiles[i][j] == 8) {//Money
					grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));				
					}
				else if(Maptiles[i][j] == 10) {
					if(!levelPassed) {
						grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
					}else
					uptile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 11) {
					if(!levelPassed) {
						grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
					}else
					downtile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 12) {
					if(!levelPassed) {
						grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
					}else
					lefttile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 13) {
					if(!levelPassed) {
						grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
					}else
					righttile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 20) {//tree
					grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 30) {//Bandit
					grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 31) {//GreenTank
					grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 32) {//ShotGunner
					grasstile.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 60) {//Market
					marketUp.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 61) {//Market
					marketDown.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 62) {//Market
					asphaltUp.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
				else if(Maptiles[i][j] == 63) {//Market
					asphaltDown.render(graphics, (int)(i * Tile.getWidth() - game.getgamecam().getXoffset()), (int)(j * Tile.getHeight() - game.getgamecam().getYoffset()));
				}
			}
		}
		
		for(int k=0;k<GameState.getBrokenObjectList().size();k++) {
			graphics.drawImage(GameState.getBrokenObjectList().get(k).brokenImage, 
					(int)(GameState.getBrokenObjectList().get(k).x - game.getgamecam().getXoffset()),
					(int)(GameState.getBrokenObjectList().get(k).y - game.getgamecam().getYoffset()),
					GameState.getBrokenObjectList().get(k).width,GameState.getBrokenObjectList().get(k).height,null);
		}
		
		for(int k=0;k<GameState.getStaticObjectList().size();k++) {
			GameState.getStaticObjectList().get(k).render(graphics);
		}
		
		
		for(int i=0;i< deadBodies.size();i++) {
			graphics.drawImage(deadBodies.get(i).brokenImage, (int)(deadBodies.get(i).x - game.getgamecam().getXoffset()), (int)(deadBodies.get(i).y - game.getgamecam().getYoffset()), deadBodies.get(i).width, deadBodies.get(i).height, null);
		}
		
		for (Money droppedMoney : GameState.getDroppedMoneyList()){
			droppedMoney.render(graphics);
		}
		
		for(int i=0;i< enemyList.size();i++) {
			enemyList.get(i).render(graphics);
		}
	}
	
	public String LoadFileAsString(String path) {
		StringBuilder builder = new StringBuilder();
		String a = "";
		String everything = "";
		int i = 0;
	
		
		try {
			//BufferedReader br = new BufferedReader(new FileReader(path));
			InputStream fin = (Level.class.getResourceAsStream(path));
			String line;
//			while((line = br.readLine()) != null) {
//				builder.append(line +"\n");
//			}
			while((i = fin.read()) != -1) {
				builder.append((char)i);
			}
			//System.out.println(builder.toString());
			//br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
			  
			     
		
		return builder.toString();
		//return everything;
	}
	
	
	
	
	//getters

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public Weapon getThomson() {
		return thomson;
	}

	public void setThomson(Weapon thomson) {
		this.thomson = thomson;
	}

	public Weapon getPistol() {
		return pistol;
	}

	public void setPistol(Weapon pistol) {
		this.pistol = pistol;
	}

	public Bandit getBandit() {
		return bandit;
	}

	public void setBandit(Bandit bandit) {
		this.bandit = bandit;
	}

	public static ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

	public static void setEnemyList(ArrayList<Enemy> enemyList) {
		Level.enemyList = enemyList;
	}

	public Weapon getShotgun() {
		return shotgun;
	}

	public void setShotgun(Weapon shotgun) {
		this.shotgun = shotgun;
	}

	public static ArrayList<BrokenObjects> getDeadBodies() {
		return deadBodies;
	}

	public static void setDeadBodies(ArrayList<BrokenObjects> deadBodies) {
		Level.deadBodies = deadBodies;
	}

	public static boolean isIsdeleted() {
		return isdeleted;
	}

	public static void setIsdeleted(boolean isdeleted) {
		Level.isdeleted = isdeleted;
	}

	public BlackMarket[] getMarkets() {
		return markets;
	}

	public void setMarkets(BlackMarket[] markets) {
		this.markets = markets;
	}

	public boolean isLevelPassed() {
		return levelPassed;
	}

	public void setLevelPassed(boolean levelPassed) {
		this.levelPassed = levelPassed;
	}

	public Weapon[] getAddToMarket() {
		return addToMarket;
	}

	public void setAddToMarket(Weapon[] addToMarket) {
		this.addToMarket = addToMarket;
	}

	public ShotGunner getShotGunner() {
		return shotGunner;
	}

	public void setShotGunner(ShotGunner shotGunner) {
		this.shotGunner = shotGunner;
	}

	public GreenTank getGreenTank() {
		return greenTank;
	}

	public void setGreenTank(GreenTank greenTank) {
		this.greenTank = greenTank;
	}
	
	
	
	
	
	
	
	
	
	
}
