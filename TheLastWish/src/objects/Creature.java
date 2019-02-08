package objects;

import java.awt.geom.Line2D;
import java.util.Random;

import main.*;
import states.GameState;
import tiles.Tile;

public abstract class Creature extends Object{
	
	
	private static final int BaseHP = 10;
	private static final float BaseSpeed = 3;
	protected static final int BaseWidth = 64;
	protected static final int BaseHeight = 64;

	protected int HP, maxHP;
	protected float Speed;
	protected long lastTime, timer, lastTime2;
	protected boolean dirt = false;
	protected float mudSpeed = 1.0f, lowSpeed = 2.0f, normalSpeed = 3.0f, highSpeed = 5.0f;
	
	protected float xMove, yMove;
	
	protected boolean up, down, right, left;
	
	private boolean upC, downC, rightC, leftC;//collisions
	
	Line2D upl = new Line2D.Float((int)x, (int)y, (int)(x+width), (int)y);
	Line2D rightl = new Line2D.Float((int)x+ (int)width, (int)y, (int)(x+width), (int)(y+height));
	Line2D downl = new Line2D.Float((int)x, (int)(y+height), (int)(x+width), (int)(y+height));
	Line2D leftl = new Line2D.Float((int)x, (int)y, (int)x, (int)(y+height));
	
	Random randomwalk = new Random();
	
	
	
	public Creature(Level levelIn, Game gameIn, double xIn, double yIn, int widthIn, int heightIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn);
		maxHP = BaseHP;
		HP = maxHP;
		Speed = BaseSpeed;
		xMove = 0;
		yMove = 0;
	}
	
	private float getARandom() {
		float random = randomwalk.nextFloat() * 3 + 1;
		
		return random;
	}
	
	
	public void move() {
		xMove = 0;
		yMove = 0;
		
		//Collision will do from here
		boolean collisionActive = false;
		
		for(int i=0;i<Tile.stonetileList.size();i++) {
			
			if(upl.intersects(Tile.stonetileList.get(i).getRect())) {
				//System.out.println("upCollision");
				upC=true;
				collisionActive = true;
			}
			if(downl.intersects(Tile.stonetileList.get(i).getRect())) {
				//System.out.println("downCollision");
				downC=true;
				collisionActive=true;
			}
			if(rightl.intersects(Tile.stonetileList.get(i).getRect())) {
				//System.out.println("rightCollision");
				rightC=true;
				collisionActive= true;
			}
			if(leftl.intersects(Tile.stonetileList.get(i).getRect())) {
				//System.out.println("leftCollision");
				leftC=true;
				collisionActive=true;
			}
			
		}
		
		for(int i=0;i<GameState.getStaticObjectList().size();i++) {
			
			if(upl.intersects(GameState.getStaticObjectList().get(i).getRect())) {
				//System.out.println("upCollision");
				upC=true;
				collisionActive = true;
			}
			if(downl.intersects(GameState.getStaticObjectList().get(i).getRect())) {
				//System.out.println("downCollision");
				downC=true;
				collisionActive=true;
			}
			if(rightl.intersects(GameState.getStaticObjectList().get(i).getRect())) {
				//System.out.println("rightCollision");
				rightC=true;
				collisionActive= true;
			}
			if(leftl.intersects(GameState.getStaticObjectList().get(i).getRect())) {
				//System.out.println("leftCollision");
				leftC=true;
				collisionActive=true;
			}
			
		}
		
		if(!collisionActive)
			walkingInputs();
		
		if(upC && !downC && !leftC && !rightC) {
			noUp();
		}
		if(!upC && downC && !leftC && !rightC) {
			noDown();
		}
		if(!upC && !downC && leftC && !rightC) {
			noLeft();
		}
		if(!upC && !downC && !leftC && rightC) {
			noRight();
		}
		if(upC && !downC && leftC && !rightC) {
			noUpLeft();
		}
		if(upC && !downC && !leftC && rightC) {
			noUpRight();
		}
		if(!upC && downC && leftC && !rightC) {
			noDownLeft();
		}
		if(!upC && downC && !leftC && rightC) {
			noDownRight();
		}
		
		
		
		collisionActive = false; upC = false; downC = false; rightC = false; leftC=false;
		
		
		
		
		if((x < game.getgamecam().getXoffset())) {
			x+=Speed;
			rect.setRect(rect.getX() + Speed, rect.getY(), rect.getWidth(), rect.getHeight());
			moveLines();
		}
		if((x > Level.getWidth() * Tile.getWidth() - width)) {
			x-=Speed;
			rect.setRect(rect.getX() - Speed, rect.getY(), rect.getWidth(), rect.getHeight());
			moveLines();
		}
		if((y > Level.getHeight() * Tile.getHeight() - height)) {
			y-=Speed;
			rect.setRect(rect.getX(), rect.getY() - Speed, rect.getWidth(), rect.getHeight());
			moveLines();
		}
		if((y < game.getgamecam().getYoffset())) {
			y+=Speed;
			rect.setRect(rect.getX(), rect.getY() + Speed, rect.getWidth(), rect.getHeight());
			moveLines();
		}
		
		
		rect.setRect(rect.getX() + xMove, rect.getY(), rect.getWidth(), rect.getHeight());
		rect.setRect(rect.getX(), rect.getY() + yMove, rect.getWidth(), rect.getHeight());
		x += xMove;
		y += yMove;
		moveLines();
		}
	
	
	
	public void enemyMove() {
		xMove = 0;
		yMove = 0;
		
		//Collision will do from here
		boolean collisionActive = false;
		
		for(int i=0;i<Tile.stonetileList.size();i++) {
			
			if(upl.intersects(Tile.stonetileList.get(i).getRect())) {
				//System.out.println("upCollision");
				upC=true;
				collisionActive = true;
			}
			if(downl.intersects(Tile.stonetileList.get(i).getRect())) {
				//System.out.println("downCollision");
				downC=true;
				collisionActive=true;
			}
			if(rightl.intersects(Tile.stonetileList.get(i).getRect())) {
				//System.out.println("rightCollision");
				rightC=true;
				collisionActive= true;
			}
			if(leftl.intersects(Tile.stonetileList.get(i).getRect())) {
				//System.out.println("leftCollision");
				leftC=true;
				collisionActive=true;
			}
			
		}
		
		for(int i=0;i<GameState.getStaticObjectList().size();i++) {
			
			if(upl.intersects(GameState.getStaticObjectList().get(i).getRect())) {
				//System.out.println("upCollision");
				upC=true;
				collisionActive = true;
			}
			if(downl.intersects(GameState.getStaticObjectList().get(i).getRect())) {
				//System.out.println("downCollision");
				downC=true;
				collisionActive=true;
			}
			if(rightl.intersects(GameState.getStaticObjectList().get(i).getRect())) {
				//System.out.println("rightCollision");
				rightC=true;
				collisionActive= true;
			}
			if(leftl.intersects(GameState.getStaticObjectList().get(i).getRect())) {
				//System.out.println("leftCollision");
				leftC=true;
				collisionActive=true;
			}
			
		}
		
		if(!collisionActive)
			walkingInputs();
		
		if(upC && !downC && !leftC && !rightC) {
			noUp();
		}
		if(!upC && downC && !leftC && !rightC) {
			noDown();
		}
		if(!upC && !downC && leftC && !rightC) {
			noLeft();
		}
		if(!upC && !downC && !leftC && rightC) {
			noRight();
		}
		if(upC && !downC && leftC && !rightC) {
			noUpLeft();
		}
		if(upC && !downC && !leftC && rightC) {
			noUpRight();
		}
		if(!upC && downC && leftC && !rightC) {
			noDownLeft();
		}
		if(!upC && downC && !leftC && rightC) {
			noDownRight();
		}
		
		
		
		collisionActive = false; upC = false; downC = false; rightC = false; leftC=false;
		
		rect.setRect(rect.getX() + xMove, rect.getY(), rect.getWidth(), rect.getHeight());
		rect.setRect(rect.getX(), rect.getY() + yMove, rect.getWidth(), rect.getHeight());
		moveLines();
		x += xMove;
		y += yMove;
		moveLines();
		}
	
	
	
	
	
	//Move methods
	public void noUp() {
		xMove = 0;
		yMove = 0;
		if(down) {
			walkingInputs();
		}
		if(up) {
			xMove = 0;
			yMove = 0;
		}
		if(left && !(x < game.getgamecam().getXoffset())) {
			xMove = -Speed;
		}
		if(right && !(x > Level.getWidth() * Tile.getWidth() - width)) {
			xMove = Speed;
		}
		if(left && up && !(x < game.getgamecam().getXoffset())) {
			xMove = -Speed;
		}
		if(right && up && !(x > Level.getWidth() * Tile.getWidth() - width)) {
			xMove = Speed;
		}
	}
	
	public void noDown() {
		xMove = 0;
		yMove = 0;
		if(up) {
			walkingInputs();
		}
		if(down) {
			xMove = 0;
			yMove = 0;
		}
		if(left && !(x < game.getgamecam().getXoffset())) {
			xMove = -Speed;
		}
		if(right && !(x > Level.getWidth() * Tile.getWidth() - width)) {
			xMove = Speed;
		}
		if(left && down && !(x < game.getgamecam().getXoffset())) {
			xMove = -Speed;
		}
		if(right && down && !(x > Level.getWidth() * Tile.getWidth() - width)) {
			xMove = Speed;
		}
	}
	
	public void noRight() {
		xMove = 0;
		yMove = 0;
		if(left) {
			walkingInputs();
		}
		if(right) {
			xMove = 0;
			yMove = 0;
		}
		if(up) {
			yMove = -Speed;
		}
		if(down) {
			yMove = Speed;
		}
		if(up && right) {
			yMove = -Speed;
		}
		if(down && right) {
			yMove = Speed;
		}
	}
	
	public void noLeft() {
		xMove = 0;
		yMove = 0;
		if(right) {
			walkingInputs();
		}
		if(left) {
			xMove = 0;
			yMove = 0;
		}
		if(up) {
			yMove = -Speed;
		}
		if(down) {
			yMove = Speed;
		}
		if(up && left) {
			yMove = -Speed;
		}
		if(down && left) {
			yMove = Speed;
		}
	}
	
	
	public void noUpLeft() {
		xMove = 0;
		yMove = 0;
		if(up) {
			xMove = 0;
			yMove = 0;
		}
		if(left) {
			xMove = 0;
			yMove = 0;
		}
		if(down && !left) {
			walkingInputs();
		}
		else if(down && left) {
			yMove = Speed;
		}
		
		if(right && !up) {
			walkingInputs();
		}
		else if(right && up) {
			xMove = Speed;
		}
	}
	
	public void noUpRight() {
		xMove = 0;
		yMove = 0;
		if(up) {
			xMove = 0;
			yMove = 0;
		}
		if(right) {
			xMove = 0;
			yMove = 0;
		}
		if(down && !right) {
			walkingInputs();
		}
		else if(down && right) {
			yMove = Speed;
		}
		
		if(left && !up) {
			walkingInputs();
		}
		else if(left && up) {
			xMove = -Speed;
		}
	}
	
	public void noDownRight() {
		xMove = 0;
		yMove = 0;
		if(down) {
			xMove = 0;
			yMove = 0;
		}
		if(right) {
			xMove = 0;
			yMove = 0;
		}
		if(up && !right) {
			walkingInputs();
		}
		else if(up && right) {
			yMove = -Speed;
		}
		
		if(left && !down) {
			walkingInputs();
		}
		else if(left && down) {
			xMove = -Speed;
		}
	}

	public void noDownLeft() {
		xMove = 0;
		yMove = 0;
		if(down) {
			xMove = 0;
			yMove = 0;
		}
		if(left) {
			xMove = 0;
			yMove = 0;
		}
		if(up && !left) {
			walkingInputs();
		}
		else if(up && left) {
			yMove = -Speed;
		}
		
		if(right && !down) {
			walkingInputs();
		}
		else if(right && down) {
			xMove = Speed;
		}
	}
	
	
	
	
	public void walkingInputs() {
		xMove = 0;
		yMove = 0;
		
		if(up) {
			yMove = -Speed;
		}
		if(down) {
			yMove = Speed;
		}
		if(left) {
			xMove = -Speed;
		}
		if(right) {
			xMove = Speed;
		}
	}
	
	
	private void moveLines() {
		upl = new Line2D.Double(rect.getX() + 5, rect.getY(), rect.getX() + rect.getWidth() - 5, rect.getY());
		rightl = new Line2D.Double(rect.getX() + rect.getWidth(), rect.getY() + 5, rect.getX() + rect.getWidth(),rect.getY() + rect.getHeight()-5);
		downl = new Line2D.Double(rect.getX() + 5, rect.getY() + rect.getHeight(), rect.getX() + rect.getWidth()-5, rect.getY() + rect.getHeight());
		leftl = new Line2D.Double(rect.getX(), rect.getY() + 5, rect.getX(), rect.getY() + rect.getHeight() - 5);
	}
	
	
	
	
	//getters

	public int getHP() {
		return HP;
	}
	
	public float getSpeed() {
		return Speed;
	}
	
	public int getMaxHP() {
		return maxHP;
	}
	
	//setters
	
	public void setHP(int HPIn) {
		HP = HPIn;
	}
	
	public void setSpeed(float speed) {
		Speed = speed;
	}
	
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public Line2D getUpl() {
		return upl;
	}

	public void setUpl(Line2D upl) {
		this.upl = upl;
	}

	public Line2D getRightl() {
		return rightl;
	}

	public void setRightl(Line2D rightl) {
		this.rightl = rightl;
	}

	public Line2D getDownl() {
		return downl;
	}

	public void setDownl(Line2D downl) {
		this.downl = downl;
	}

	public Line2D getLeftl() {
		return leftl;
	}

	public void setLeftl(Line2D leftl) {
		this.leftl = leftl;
	}
	
	
	
	
	
	
	
	

}
