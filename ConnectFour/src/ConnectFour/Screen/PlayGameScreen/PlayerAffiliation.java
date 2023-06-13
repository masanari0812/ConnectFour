package ConnectFour.Screen.PlayGameScreen;

import javafx.scene.paint.Color;

public enum PlayerAffiliation {
	NONE(0), PLAYER1(1), PLAYER2(2);
	
	final private Color color;
	
	PlayerAffiliation(int i) {
		switch(i) {
		
		case 0 :
			color = Color.GREY;
			break;
		case 1 :
			color = Color.RED;		// PLAYER1は赤いマス
			break;
		case 2 :
			color = Color.YELLOW;	// PLAYER2は黄色いマス
			break;
		default :
			color = Color.MEDIUMPURPLE;
			break;
		}
	}
	
	Color getColor() {
		return color;
	}
}
