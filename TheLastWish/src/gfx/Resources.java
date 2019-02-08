package gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;

public class Resources {

	
	private static final int width = 32, height = 32;// w and h of tiles
	
	public static BufferedImage player, dirt1 ,dirt3, dirt5, dirt7, grass,
								stone, Bulletimg, Gun, thomson, tree, pistol,
								BlackMarket, Coin, Log, DeadBandit, Bandit, Greentank, 
								shotgun, customCrossHair, Uparrow, Downarrow, Leftarrow, Rightarrow,
								DeadPlayer, Shotgunner, MarketUp, MarketDown, AsphaltUp, AsphaltDown,
								HelloWorldPaper, DeadGreenTank, DeadShotGunner, Coins, LotsOfCoins;
	
	public static BufferedImage[] playeranimation;
	public static BufferedImage[] shotgunneranimation;
	public static BufferedImage[] banditanimation;
	public static BufferedImage[] greentankanimation;
	public static BufferedImage[] playerpunchanimation;
	
	public static SpriteSheet weaponSheet, sheet;
	
	public static Clip bst, bbs, buy, scroll, select, teleport, thomsonsound, 
					   coinsound, errorsound, shotgunsound, pistolsound, emptyClipSound, 
					   DirtWalking, GrassWalking, Running, SlimeAttack, PlayerHit;

	public static Font font56, font18, fontB18, font40;

	public static void sheetCrop() {
		bst = SoundLoader.loadSound("/sfx/BossSoundTrack3.wav");
		bbs = SoundLoader.loadSound("/sfx/back2.wav");
		buy = SoundLoader.loadSound("/sfx/Buy.wav");
		scroll = SoundLoader.loadSound("/sfx/Scroll.wav");
		select = SoundLoader.loadSound("/sfx/Selected2.wav");
		teleport = SoundLoader.loadSound("/sfx/Teleport.wav");
		thomsonsound = SoundLoader.loadSound("/sfx/ThompsonSound.wav");
		coinsound = SoundLoader.loadSound("/sfx/CoinSound.wav");
		errorsound = SoundLoader.loadSound("/sfx/errorSound.wav");
		shotgunsound = SoundLoader.loadSound("/sfx/shotgunSound.wav");
		pistolsound = SoundLoader.loadSound("/sfx/pistolSound.wav");
		emptyClipSound = SoundLoader.loadSound("/sfx/emptyClip.wav");
		DirtWalking = SoundLoader.loadSound("/sfx/dirtWalking.wav");
		GrassWalking = SoundLoader.loadSound("/sfx/grassWalkingSound2.wav");
		Running = SoundLoader.loadSound("/sfx/runningInGrass2.wav");
		SlimeAttack = SoundLoader.loadSound("/sfx/SlimeAttack.wav");
		PlayerHit = SoundLoader.loadSound("/sfx/playerPunch.wav");
		
		
		
		BlackMarket = ImageLoader.loadImage("/textures/BlackMarket.png");
		HelloWorldPaper = ImageLoader.loadImage("/textures/HelloWorldPaper.png");
		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/spritesheet.png"));
		weaponSheet = new SpriteSheet(ImageLoader.loadImage("/textures/weaponsprite.png"));
		
		font56 = FontLoader.loadFont("/fonts/PixelHour.ttf", 56);
		font18 = FontLoader.loadFont("/fonts/PixelHour.ttf", 18);
		fontB18 = FontLoader.loadFont("/fonts/Blockbuster.ttf", 18);
		font40 = FontLoader.loadFont("/fonts/PixelHour.ttf", 40);
		
		
		playeranimation = new BufferedImage[8];
		
		playeranimation[0] = sheet.crop(0, height, width, height);
		playeranimation[1] = sheet.crop(width, height, width, height);
		playeranimation[2] = sheet.crop(width * 2, height, width, height);
		playeranimation[3] = sheet.crop(width * 3, height, width, height);
		playeranimation[4] = sheet.crop(width * 4, height, width, height);
		playeranimation[5] = sheet.crop(width * 5, height, width, height);
		playeranimation[6] = sheet.crop(width * 6, height, width, height);
		playeranimation[7] = sheet.crop(width * 7, height, width, height);
		
		greentankanimation = new BufferedImage[4];
		
		greentankanimation[0] = sheet.crop(width * 3, height * 3, width, height);
		greentankanimation[1] = sheet.crop(width * 4, height * 3, width, height);
		greentankanimation[2] = sheet.crop(width * 5, height * 3, width, height);
		greentankanimation[3] = sheet.crop(width * 6, height * 3, width, height);
		
		shotgunneranimation = new BufferedImage[4];
		
		shotgunneranimation[0] = sheet.crop(width * 2, height * 4, width, height);
		shotgunneranimation[1] = sheet.crop(width * 3, height * 4, width, height);
		shotgunneranimation[2] = sheet.crop(width * 4, height * 4, width, height);
		shotgunneranimation[3] = sheet.crop(width * 5, height * 4, width, height);
		
		banditanimation = new BufferedImage[8];
		
		banditanimation[0] = sheet.crop(width * 3, height * 5, width, height);
		banditanimation[1] = sheet.crop(width * 4, height * 5, width, height);
		banditanimation[2] = sheet.crop(width * 5, height * 5, width, height);
		banditanimation[3] = sheet.crop(width * 6, height * 5, width, height);
		banditanimation[4] = sheet.crop(width * 7, height * 5, width, height);
		banditanimation[5] = sheet.crop(width * 5, height * 6, width, height);
		banditanimation[6] = sheet.crop(width * 6, height * 6, width, height);
		banditanimation[7] = sheet.crop(width * 7, height * 6, width, height);
		
		playerpunchanimation = new BufferedImage[4];
		
		playerpunchanimation[0] = sheet.crop(width * 4, height * 7, width, height);
		playerpunchanimation[1] = sheet.crop(width * 5, height * 7, width, height);
		playerpunchanimation[2] = sheet.crop(width * 6, height * 7, width, height);
		playerpunchanimation[3] = sheet.crop(width * 7, height * 7, width, height);
		
		Bulletimg = sheet.crop(width, height * 2, width, height);
		Gun = sheet.crop(width * 7, 0, width, height);
		tree = sheet.crop(width * 7, height * 2, width, height);
		stone = sheet.crop(width * 6, 0, width, height);
		grass = sheet.crop(width, 0, width, height);
		dirt1 = sheet.crop(width * 2, 0, width, height);
		dirt3 = sheet.crop(width * 3, 0, width, height);
		dirt5 = sheet.crop(width * 4, 0, width, height);
		dirt7 = sheet.crop(width * 5, 0, width, height);
		player = sheet.crop(0, 0, width, height);
		Coin = sheet.crop(0, height * 3, width, height);
		Log = sheet.crop(width, height * 3, width, height);
		DeadBandit = sheet.crop(0, height * 5, width, height);
		Bandit = sheet.crop(0, height * 4, width, height);
		Greentank = sheet.crop(width * 2, height * 3, width, height);
		customCrossHair = sheet.crop(0, height * 6, width, height);
		Uparrow = sheet.crop(width, height * 6, width, height);
		Downarrow = sheet.crop(width * 3, height * 6, width, height);
		Leftarrow = sheet.crop(width * 4, height * 6, width, height);
		Rightarrow = sheet.crop(width * 2, height * 6, width, height);
		DeadPlayer = sheet.crop(width, height * 5, width, height);
		Shotgunner = sheet.crop(width, height * 4, width, height);
		MarketUp = sheet.crop(0, height * 7, width, height);
		MarketDown = sheet.crop(width, height * 7, width, height);
		AsphaltUp = sheet.crop(width * 2, height * 7, width, height);
		AsphaltDown = sheet.crop(width * 3, height * 7, width, height);
		DeadGreenTank = sheet.crop(width * 7, height * 3, width, height);
		DeadShotGunner = sheet.crop(width * 2, height * 5, width, height);
		Coins = sheet.crop(width * 6, height * 4, width, height);
		LotsOfCoins = sheet.crop(width * 7, height * 4, width, height);
		
		thomson = weaponSheet.crop(0, 0, width * 4, height * 4);
		pistol = weaponSheet.crop(width * 4, 0, width * 4, height * 4);
		shotgun = weaponSheet.crop(width * 8, 0, width * 4, height * 4);
	}
	
	
	public static BufferedImage rotateImage(BufferedImage imageIn, double degrees) {//redrawing to an empty image
		BufferedImage imgToRotate = imageIn;
		
		ImageIcon icon = new ImageIcon(imgToRotate);
		BufferedImage emptyImg = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)emptyImg.getGraphics();
		g2.rotate(degrees, icon.getIconWidth()/2, icon.getIconHeight()/2);
		g2.drawImage(imgToRotate, 0, 0, null);
		imgToRotate = emptyImg;
		
		return imgToRotate;
	}
	
	public static BufferedImage rotateImage2(BufferedImage imageIn, double degrees) {//using transform
		BufferedImage before = imageIn;
		int w = before.getWidth();
		int h = before.getHeight();
		BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.rotate(degrees,after.getWidth()/2, after.getHeight()/2);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(before, after);
		return after;
	}
	
	
	
	public static void drawString(Graphics graphics, String text, int xIn, int yIn, boolean center, Color color, Font font) {
		graphics.setColor(color);
		graphics.setFont(font);
		int x = xIn;
		int y = yIn;
		if(center) {
			FontMetrics fm = graphics.getFontMetrics(font);
			x = xIn - fm.stringWidth(text) / 2;
			y = yIn - fm.getHeight() / 2 + fm.getAscent();
		}
		graphics.drawString(text, x, y);
	}
	
	 public static void play(Clip ClipIn){
		 ClipIn.setFramePosition(0);  // Must always rewind!
		 ClipIn.loop(0);
		 ClipIn.start();
     }
     public static void loop(Clip ClipIn){
    	 ClipIn.loop(Clip.LOOP_CONTINUOUSLY);
    	 
     }
     public static void stop(Clip ClipIn){
    	 ClipIn.stop();
     }
     
     public static void volume(Clip ClipIn, float f) {
    	 FloatControl gainControl = 
    			    (FloatControl) ClipIn.getControl(FloatControl.Type.MASTER_GAIN);
    			gainControl.setValue(f);
     }
}
