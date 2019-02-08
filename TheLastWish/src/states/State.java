package states;
import java.awt.Graphics;

import main.Game;

public abstract class State {

	private static State currentState = null;
	
	Game game;
	
	public State(Game gameIn) {
		game = gameIn;
	}

	public abstract void update();
	public abstract void render(Graphics graphics);

	//getters
	
	public static State getcurrentState() {
		return currentState;
	}
	
	//setters
	
	public static void setcurrentState(State state) {
		currentState = state;
	}
	
	
}
