package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import gfx.Camera;
import gfx.Display;
import gfx.Resources;
import objects.Player;
import states.CreditsState;
import states.GameState;
import states.MenuState;
import states.ResumeState;
import states.SettingsState;
import states.State;

public class Game implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	
	//Fps Settings
		private int fps = 60;
		private double timePerUpdate = 1000000000 / fps;
		private double delta = 0;
		private long now;
		private long lastTime = System.nanoTime();
		private long timer = 0;
		private int updates = 0;
	//Fps Settings Ends
	
	private Display display;
	
	public static int width; // turned this to static to use in bullet class, may  change later...
	public static int height;

	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics graphics;
	
	//States
	public static State gameState;
	public static State menuState;
	public static State settingsState;
	public static State creditsState;
	public static State resumeState;
	
	
	//Camera
	private Camera camera;
	
	//Mouse
	private static Point mousePoint = new Point(0,0);
	private static Point mouseMovedPoint = new Point(0,0);
	private static Point mousePressedPoint = new Point(0,0);
	private static Point mouseRelesedPoint = new Point(0,0);
	private static Point mouseDraggedPoint = new Point(0,0);
	private boolean LeftButtonPressed = false, 
					RightButtonPressed = false, 
					LeftButtonReleased = false, 
					LeftButtonDragged = false,
					ShiftButtonActive = false,
					EscapeButtonActive = false,
					RButtonActive = false,
					QButtonActive = false;
	BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	
	public static double mousePressedX, mousePressedY;

	// Create a new blank cursor.
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
	    cursorImg, new Point(0, 0), "blank cursor");
	Cursor customCrossHair;
	
	public Game(String titleIn, int widthIn, int heightIn) {
		width = widthIn;
		height = heightIn;
		title = titleIn;
	}
	
	private void init() {
		display = new Display(title, width, height);
		Resources.sheetCrop();
		
		customCrossHair = Toolkit.getDefaultToolkit().createCustomCursor(
			    Resources.customCrossHair, new Point(0, 0), "CrossHair");
		
		camera = new Camera(this, 0, 0);
		
		gameState = new GameState(this);
		menuState = new MenuState(this);
		settingsState = new SettingsState(this);
		creditsState = new CreditsState(this);
		resumeState = new ResumeState(this);
		
		//KeyListener, MouseListener and MouseMotionListener added.
		display.getFrame().addKeyListener(this);
		display.getFrame().addMouseListener(this);
		display.getCanvas().addMouseListener(this);
		display.getFrame().addMouseMotionListener(this);
		display.getCanvas().addMouseMotionListener(this);
		
		State.setcurrentState(menuState);
		
	}
	
	private void update() {
		if(State.getcurrentState() != null) {
			State.getcurrentState().update();
		}
		if(State.getcurrentState() == menuState ||
				//State.getcurrentState() == settingsState ||
				State.getcurrentState() == creditsState) {
			Resources.loop(Resources.bst);
			display.getCanvas().setCursor(Cursor.getDefaultCursor());
			
		}else {
			Resources.stop(Resources.bst);
		}
		if(State.getcurrentState() == gameState && !GameState.isMarketOpen()) {
			// Set the blank cursor to the JFrame.
			display.getCanvas().setCursor(customCrossHair);
//			display.getCanvas().setCursor(blankCursor);
		}
		if(GameState.isMarketOpen()) {
			display.getCanvas().setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(2);
			return;
		}
		graphics = bs.getDrawGraphics();
		graphics.clearRect(0, 0, width, height);
		//BackGround
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRect(0, 0, width, height);
		// Drawing Starts
		if(State.getcurrentState() != null) {
			State.getcurrentState().render(graphics);
		}
		// Drawing Ends
		
		bs.show();
		graphics.dispose();
	}
	
	
	
	
	
	
	
	@Override
	public void run() {
		
		init();
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime)/timePerUpdate;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1) {
			update();
			render();
			updates++;
			delta--;
			}
			
			if(timer >= 1000000000) {
				updates = 0;
				timer = 0;
			}
		}
		
		stop();
	}
	
	
	
	public synchronized void start() {
		if(!running) {
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public synchronized void stop() {
		if(running) {
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	//Keyboard

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_W){
			GameState.getPlayer().setUp(true);
		}
		if(event.getKeyCode() == KeyEvent.VK_S){
			GameState.getPlayer().setDown(true);
		}
		if(event.getKeyCode() == KeyEvent.VK_A){
			GameState.getPlayer().setLeft(true);
		}
		if(event.getKeyCode() == KeyEvent.VK_D){
			GameState.getPlayer().setRight(true);
		}
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			EscapeButtonActive = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_SHIFT) {
			ShiftButtonActive = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_R) {
			RButtonActive = true;
		}
		if(event.getKeyCode() == KeyEvent.VK_Q) {
			QButtonActive = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if(event.getKeyCode() == KeyEvent.VK_W){
			GameState.getPlayer().setUp(false);
		}
		if(event.getKeyCode() == KeyEvent.VK_S){
			GameState.getPlayer().setDown(false);
		}
		if(event.getKeyCode() == KeyEvent.VK_A){
			GameState.getPlayer().setLeft(false);
		}
		if(event.getKeyCode() == KeyEvent.VK_D){
			GameState.getPlayer().setRight(false);
		}
		if(event.getKeyCode() == KeyEvent.VK_SHIFT) {
			ShiftButtonActive = false;
		}
		if(event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			EscapeButtonActive = false;
		}
		if(event.getKeyCode() == KeyEvent.VK_R) {
			RButtonActive = false;
		}
		if(event.getKeyCode() == KeyEvent.VK_Q) {
			QButtonActive = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		
	}

	
	
	
	//Mouse
	
	@Override
	public void mouseDragged(MouseEvent event) {
			mousePressedX = event.getX();
			mousePressedY = event.getY();
			mouseDraggedPoint = event.getPoint();
			LeftButtonDragged = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMovedPoint.x = e.getX();
		mouseMovedPoint.y = e.getY();
		
		mousePressedX = e.getX();
		mousePressedY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {

		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			mousePressedPoint = e.getPoint();
			LeftButtonPressed = true;
			LeftButtonReleased = false;
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			RightButtonPressed = true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if(event.getButton() == MouseEvent.BUTTON1) {
			LeftButtonPressed = false;
			LeftButtonReleased = true;
			LeftButtonDragged = false;
			mouseRelesedPoint = event.getPoint();
			mousePoint = event.getPoint();
		}
		if(event.getButton() == MouseEvent.BUTTON3) {
			RightButtonPressed = false;
		}
		
	}
	
		
	
	
	//getters setters
	
	public Camera getgamecam() {
		return camera;
	}
	
	public static Point getMousePoint() {
		return mousePoint;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public static Point getMouseMovedPoint() {
		return mouseMovedPoint;
	}

	public boolean isLeftButtonPressed() {
		return LeftButtonPressed;
	}

	public void setLeftButtonPressed(boolean leftButtonPressed) {
		LeftButtonPressed = leftButtonPressed;
	}

	public boolean isRightButtonPressed() {
		return RightButtonPressed;
	}

	public void setRightButtonPressed(boolean rightButtonPressed) {
		RightButtonPressed = rightButtonPressed;
	}

	public static State getGameState() {
		return gameState;
	}

	public static void setGameState(State gameStateIn) {
		gameState = gameStateIn;
	}

	public Display getDisplay() {
		return display;
	}

	public static Point getMousePressedPoint() {
		return mousePressedPoint;
	}

	public static Point getMouseRelesedPoint() {
		return mouseRelesedPoint;
	}

	public static State getMenuState() {
		return menuState;
	}

	public static void setMenuState(State menuState) {
		Game.menuState = menuState;
	}

	public static State getSettingsState() {
		return settingsState;
	}

	public static void setSettingsState(State settingsStateIn) {
		settingsState = settingsStateIn;
	}

	public static void setMousePressedPoint(Point mousePressedPoint) {
		Game.mousePressedPoint = mousePressedPoint;
	}

	public static State getCreditsState() {
		return creditsState;
	}

	public static void setCreditsState(State creditsState) {
		Game.creditsState = creditsState;
	}

	public static Point getMouseDraggedPoint() {
		return mouseDraggedPoint;
	}

	public static void setMouseDraggedPoint(Point mouseDraggedPoint) {
		Game.mouseDraggedPoint = mouseDraggedPoint;
	}
	
	public boolean isLeftButtonReleased() {
		return LeftButtonReleased;
	}

	public void setLeftButtonReleased(boolean leftButtonReleased) {
		LeftButtonReleased = leftButtonReleased;
	}

	public boolean isLeftButtonDragged() {
		return LeftButtonDragged;
	}

	public void setLeftButtonDragged(boolean leftButtonDragged) {
		LeftButtonDragged = leftButtonDragged;
	}

	public boolean isShiftButtonActive() {
		return ShiftButtonActive;
	}

	public void setShiftButtonActive(boolean shiftButtonActive) {
		ShiftButtonActive = shiftButtonActive;
	}

	public boolean isEscapeButtonActive() {
		return EscapeButtonActive;
	}

	public void setEscapeButtonActive(boolean escapeButtonActive) {
		EscapeButtonActive = escapeButtonActive;
	}

	public boolean isRButtonActive() {
		return RButtonActive;
	}

	public void setRButtonActive(boolean rButtonActive) {
		RButtonActive = rButtonActive;
	}

	public boolean isQbuttonActive() {
		return QButtonActive;
	}

	public void setQbuttonActive(boolean qbuttonActive) {
		QButtonActive = qbuttonActive;
	}

	
	
}
