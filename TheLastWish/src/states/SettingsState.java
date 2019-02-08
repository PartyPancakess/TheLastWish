package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gfx.Resources;
import main.Game;

public class SettingsState extends State{

	Game game;
	private Point point = new Point(0,0);
	private int width = Game.width / 7, height = Game.height / 8, space = 20;
	private int volumeWidth = 300, volumeHeight = 30;
	private int backX = Game.width / 2 - width / 2, backY = Game.height / 2 + space / 2 + height + space,
			sfxX = Game.width / 2 - volumeWidth / 2, sfxY = Game.height / 2 + space * 4, 
			MusicX = Game.width / 2 - volumeWidth / 2, MusicY = Game.height / 2;
	private int controlVolumeWidth = 30, controlVolumeHeight = 30;
	
	private int sfxControlVolumeRectX = sfxX+volumeWidth-controlVolumeWidth,
			MusicControlVolumeRectX = MusicX+volumeWidth-controlVolumeWidth;
	
	private Rectangle backRect = new Rectangle(backX, backY, width, height);
	private Rectangle sfxVolumeRect = new Rectangle(sfxX, sfxY, volumeWidth, volumeHeight);
	private Rectangle MusicVolumeRect = new Rectangle(MusicX, MusicY, volumeWidth, volumeHeight);
	
	private Rectangle sfxControlVolumeRect = new Rectangle(sfxControlVolumeRectX, sfxY, controlVolumeWidth, controlVolumeHeight);
	private Rectangle MusicControlVolumeRect = new Rectangle(MusicControlVolumeRectX, MusicY, controlVolumeWidth, controlVolumeHeight);
	
	private boolean back, sfxControl, MusicControl, fullScreenOn,
						  sfxMoving=false, MusicMoving=false;
	private int sfxVolume;
	private int MusicVolume;
	

	private Point toSfx = new Point(sfxControlVolumeRectX, sfxY),
				  toMusic = new Point(MusicControlVolumeRectX, MusicY);
	
	
	public SettingsState(Game gameIn) {
		super(gameIn);
		game = gameIn;
	}

	@Override
	public void update() {
		mouseMovement();
		
		if(sfxControl) {
			if(game.isLeftButtonPressed()) {
				sfxMoving = true;
				MusicMoving = false;
			}
		}else if(MusicControl){
			if(game.isLeftButtonPressed()) {
				MusicMoving = true;
				sfxMoving = false;
			}
		}
		
		
		if(game.isLeftButtonDragged()) {
			if(sfxMoving) {
				if(Game.getMouseDraggedPoint().x>sfxX && Game.getMouseDraggedPoint().x<sfxX + volumeWidth - sfxControlVolumeRect.width) {
					toSfx = Game.getMouseDraggedPoint();
					sfxControlVolumeRect.x = toSfx.x;	
					MusicMoving = false;
				}
			}else if(MusicMoving) {
				if(Game.getMouseDraggedPoint().x>MusicX && Game.getMouseDraggedPoint().x<MusicX + volumeWidth - MusicControlVolumeRect.width) {
					toMusic = Game.getMouseDraggedPoint();
					MusicControlVolumeRect.x = toMusic.x;
					sfxMoving = false;
				}
			}
			
			
		}
		if(game.isLeftButtonReleased()) {
			if(sfxMoving) {
				if(Game.getMouseRelesedPoint().x>sfxX && Game.getMouseRelesedPoint().x<sfxX + volumeWidth - sfxControlVolumeRect.width) {
					toSfx = Game.getMouseRelesedPoint();
					sfxControlVolumeRect.x = toSfx.x;
					sfxMoving = false;
					Resources.stop(Resources.bbs);
					Resources.play(Resources.bbs);
				}
			}else if(MusicMoving) {
				if(Game.getMouseRelesedPoint().x>MusicX && Game.getMouseRelesedPoint().x<MusicX + volumeWidth - MusicControlVolumeRect.width) {
					toMusic = Game.getMouseRelesedPoint();
					MusicControlVolumeRect.x = toMusic.x;
					MusicMoving = false;
				}
			}
			
			
		}
		
		
		sfxVolume = (sfxControlVolumeRect.x - sfxVolumeRect.x)/(volumeWidth/100)+20;
		MusicVolume = (MusicControlVolumeRect.x - MusicVolumeRect.x)/(volumeWidth/100)+20;
		
		if(sfxVolume>=20 && sfxVolume <=100) {
			setSFXVolume(-(100-sfxVolume));
		}
		if(MusicVolume>=20 && MusicVolume <=100) {
			setMusicVolume(-(100-MusicVolume));
		}
		
		if(back) {
			sfxMoving = false;
			MusicMoving = false;
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
		Resources.drawString(graphics, "SFX Volume", Game.width / 2, sfxVolumeRect.y - 20, true, Color.red, Resources.font18);
		Resources.drawString(graphics, "Music Volume", Game.width / 2, MusicVolumeRect.y - 20, true, Color.red, Resources.font18);
		
		graphics.setColor(Color.white);
		graphics.fillRect(sfxVolumeRect.x, sfxVolumeRect.y, sfxVolumeRect.width, sfxVolumeRect.height);
		graphics.fillRect(MusicVolumeRect.x, MusicVolumeRect.y, MusicVolumeRect.width, MusicVolumeRect.height);
		
		graphics.setColor(Color.black);
//		graphics.fillRect(sfxControlVolumeRectX, sfxVolumeRect.y, controlVolumeWidth, controlVolumeHeight);
//		graphics.fillRect(MusicControlVolumeRectX, MusicVolumeRect.y, controlVolumeWidth, controlVolumeHeight);
		graphics.fillRect(toSfx.x, sfxVolumeRect.y, controlVolumeWidth, controlVolumeHeight);
		graphics.fillRect(toMusic.x, MusicVolumeRect.y, controlVolumeWidth, controlVolumeHeight);
		
		graphics.setColor(Color.pink);
		graphics.fillRect(sfxVolumeRect.x, sfxVolumeRect.y, toSfx.x - sfxVolumeRect.x, sfxControlVolumeRect.height);
		graphics.fillRect(MusicVolumeRect.x, MusicVolumeRect.y, MusicControlVolumeRect.x - MusicVolumeRect.x, MusicControlVolumeRect.height);
		
		
		
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
			sfxControl = false;
			MusicControl = false;
		}else if(sfxControlVolumeRect.contains(Game.getMouseMovedPoint())) {
			back = false;
			sfxControl = true;
			MusicControl = false;
		}else if(MusicControlVolumeRect.contains(Game.getMouseMovedPoint())) {
			back = false;
			sfxControl = false;
			MusicControl = true;
		}else {
			back = false;
			sfxControl = false;
			MusicControl = false;
		}
	}
	
	
	
	public static void setSFXVolume(float volume) {
		Resources.volume(Resources.bbs, +volume);
		Resources.volume(Resources.buy, +volume);
		Resources.volume(Resources.scroll, +volume);
		Resources.volume(Resources.thomsonsound, +volume);
		Resources.volume(Resources.select, +volume);
		Resources.volume(Resources.teleport, +volume);
		Resources.volume(Resources.coinsound, +volume);
		Resources.volume(Resources.errorsound, +volume);
		Resources.volume(Resources.shotgunsound, +volume);
		Resources.volume(Resources.pistolsound, +volume);
		Resources.volume(Resources.emptyClipSound, +volume);
		Resources.volume(Resources.DirtWalking, +volume);
		Resources.volume(Resources.GrassWalking, +volume);
		Resources.volume(Resources.Running, +volume);
		Resources.volume(Resources.SlimeAttack, +volume);
		
	}
	public static void setMusicVolume(float volume) {
		Resources.volume(Resources.bst, +volume);
	}

}
