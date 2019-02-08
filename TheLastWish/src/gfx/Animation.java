package gfx;

import java.awt.image.BufferedImage;

public class Animation {

	private long lastTime, timer;
	private int speed, index;
	private BufferedImage[] frames;
	
	public Animation(int speedIn, BufferedImage[] framesIn) {
		speed = speedIn;
		frames = framesIn;
		index = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void update() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed) {
			index++;
			timer = 0;
			if(index >= frames.length) {
				index = 0;
			}
		}
	}
	
	public BufferedImage getCurrentImage() {
		return frames[index];
	}
	
	
	
	
}

