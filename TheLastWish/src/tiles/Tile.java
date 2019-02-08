package tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import objects.Money;

public class Tile {

	protected BufferedImage texture;
	protected int id;
	protected Game game;
	protected Rectangle rect;
	
	protected static final int width = 64, height = 64;
	
	public static ArrayList<GrassTile> grasstileList = new ArrayList<GrassTile>();
	public static ArrayList<Tile> dirttileList = new ArrayList<Tile>();
	public static ArrayList<StoneTile> stonetileList = new ArrayList<StoneTile>();
	public static ArrayList <Tile> arrowTiles = new ArrayList<Tile>();
	public static ArrayList <Tile> marketTiles = new ArrayList<Tile>();
	
	
	public Tile(Game gameIn, BufferedImage textureIn, int idIn) {
		texture = textureIn;
		id = idIn;
		game = gameIn;
		
		if(id == 0) {
			grasstileList.add((GrassTile) this);
		}
		else if(id == 1 || id == 3 || id == 5 || id == 7) {
			dirttileList.add(this);
		}
		else if(id == 2) {
			stonetileList.add((StoneTile) this);
		}
		else if(id == 10 || id == 11 || id == 12 || id == 13) {
			arrowTiles.add(this);
		}
		else if(id == 60 || id == 61 || id == 62 || id == 63) {
			marketTiles.add(this);
		}
			
	}
	
	public void update() {
		
	}
	
	public void render(Graphics graphics, int xIn, int yIn) {
		graphics.drawImage(texture, xIn, yIn, width, height, null);
		
//		graphics.setColor(Color.red);
//		for(int i = 0;i<stonetileList.size();i++) {
//		graphics.drawRect((int)(stonetileList.get(i).getRect().x - game.getgamecam().getXoffset()) , (int)(stonetileList.get(i).getRect().y - game.getgamecam().getYoffset()), width, height);
//		}
		
		
		
//		graphics.setColor(Color.red);
//		for(int i = 0;i<dirttileList.size();i++) {
//		graphics.drawRect((int)(dirttileList.get(i).getRect().x - game.getgamecam().getXoffset()) , (int)(dirttileList.get(i).getRect().y - game.getgamecam().getYoffset()), dirttileList.get(i).getRect().width, dirttileList.get(i).getRect().height);
//		}
		
//		graphics.setColor(Color.red);
//		for(int i = 0;i<marketTiles.size();i++) {
//		graphics.drawRect((int)(marketTiles.get(i).getRect().x - game.getgamecam().getXoffset()) , (int)(marketTiles.get(i).getRect().y - game.getgamecam().getYoffset()), marketTiles.get(i).getRect().width, marketTiles.get(i).getRect().height);
//		}
		
		
	}
	
	public boolean isSolid() {
		return false;
	}
	
	
	
	//getters

	public BufferedImage getTexture() {
		return texture;
	}

	public int getId() {
		return id;
	}
	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	
	//setters

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
	
	
	
}
