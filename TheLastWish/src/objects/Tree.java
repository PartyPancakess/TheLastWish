package objects;

import java.awt.Graphics;

import gfx.Resources;
import main.Game;
import main.Level;
import states.GameState;

public class Tree extends StaticObject{
	
	protected static boolean alreadyDamaged = false;
	protected long lastTimeTree = System.currentTimeMillis();
	protected long PlayerAttackTime = 600;

	public Tree(Level levelIn, Game gameIn, int xIn, int yIn, int widthIn, int heightIn, int HealthIn) {
		super(levelIn, gameIn, xIn, yIn, widthIn, heightIn, HealthIn);
		GameState.getStaticObjectList().add(this);
	}
	
	@Override
	public void update() {
		if(HP > 0) {
				if((GameState.getPlayer().getRect().intersects(this.rect) || GameState.getPlayer().getUpl().intersects(this.rect) 
						|| GameState.getPlayer().getDownl().intersects(this.rect) || GameState.getPlayer().getLeftl().intersects(this.rect) 
						|| GameState.getPlayer().getRightl().intersects(this.rect))&& !alreadyDamaged && game.isQbuttonActive()) {
					Resources.stop(Resources.PlayerHit);
					Resources.play(Resources.PlayerHit);
					HP --;
					alreadyDamaged = true;
				}
			
			if(lastTimeTree + PlayerAttackTime <= System.currentTimeMillis()) {
				alreadyDamaged = false;
				lastTimeTree = System.currentTimeMillis();
			}
			
			for(int i=0;i<GameState.getBullets().size();i++) {
				if(GameState.getBullets().get(i).getRect().intersects(this.rect) && HP != 0 ) {
					HP -= 2;
					GameState.getBullets().remove(i);
				}
			}
			
			for(int j=0;j<GameState.getEnemyBullets().size();j++) {
				if(GameState.getEnemyBullets().get(j).getRect().intersects(this.rect) && HP != 0 ) {
					HP -= 2;
					GameState.getEnemyBullets().remove(j);
				}
			}
		}
		else {
			initBrokenObject(Resources.Log);
			GameState.getBrokenObjectList().add(this.brokenObject);
			
			GameState.getStaticObjectList().remove(this);
		}
	}

	@Override
	public void render(Graphics graphics) {
		graphics.drawImage(Resources.tree, (int)(x - game.getgamecam().getXoffset()), (int)(y - game.getgamecam().getYoffset()), (int)width, (int)height, null);
	}

}
