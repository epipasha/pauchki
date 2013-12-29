package project;

import java.awt.Color;

public class PlayerColor {

	private int currentPlayer = 0;
	private static final Color[] COLOR_PLAYER = {Color.red, Color.blue};
	
	public Color getColorOfNextPlayer(){
		nextPlayer();
		return getCurrentColor();
	}
	
	public Color getCurrentColor(){
		return COLOR_PLAYER[currentPlayer];
	}
	
	public void nextPlayer(){
		currentPlayer++;
		if (currentPlayer > 1) {
			currentPlayer = 0;
		}
	}
	
}
