package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class CreditsState extends State{

	
	Game game;
	private Point point = new Point(0,0);
	private int width = Game.width / 7, height = Game.height / 8, space = 20;
	private int backX = Game.width / 2 - width / 2, backY = Game.height / 2 + space / 2 + height + space;
	
	private Rectangle backRect = new Rectangle(backX, backY, width, height);
	
	private boolean back;
	
	public CreditsState(Game gameIn) {
		super(gameIn);
		game = gameIn;
	}

	@Override
	public void update() {
		mouseMovement();
		
		if(back) {
			if(backRect.contains(Game.getMousePressedPoint())){
				if(backRect.contains(Game.getMouseRelesedPoint())) {
					Resources.stop(Resources.bbs);
					Resources.play(Resources.bbs);
					Game.setMousePressedPoint(point);
					State.setcurrentState(Game.menuState);
				}
			}
		}
		
	}

	@Override
	public void render(Graphics graphics) {
		Resources.drawString(graphics, "Bilge H ONAYCI", game.getWidth() / 2, game.getHeight() / 2 - 150, true, Color.LIGHT_GRAY, Resources.font56);
		Resources.drawString(graphics, "Berke AKSOY", game.getWidth() / 2, game.getHeight() / 2 - 50, true, Color.LIGHT_GRAY, Resources.font56);
		Resources.drawString(graphics, "Special Thanks To MertS For The Soundtrack", game.getWidth() / 2, game.getHeight() / 2 + 75, true, Color.LIGHT_GRAY, Resources.font40);
		Resources.drawString(graphics, "              " + " MertS" + "                  ", game.getWidth() / 2, game.getHeight() / 2 + 75, true, Color.RED, Resources.font40);
		
		if(!back) {
			Resources.drawString(graphics, "BACK", backX + width / 2 , backY + height / 2, true, Color.WHITE, Resources.font56);
		}
		else if(back) {
			if(game.isLeftButtonPressed()) {
				Resources.drawString(graphics, "BACK", backX + width / 2 , backY + height / 2, true, Color.red, Resources.font56);
			}
			else {
				Resources.drawString(graphics, "BACK", backX + width / 2 , backY + height / 2, true, Color.CYAN, Resources.font56);
			}
		}
	}
	
	
	
	public void mouseMovement() {
		if(backRect.contains(Game.getMouseMovedPoint())) {
			back = true;
		}else {
			back = false;
		}
	}
	
	
}
