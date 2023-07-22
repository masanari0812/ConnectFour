/*******************************************************************
*** File Name             : PlayerAffiliation.java
*** Designer              : 塚田 大貴
*** Date                  : 2023.07.22
*** Purpose               : 埋まっていないマス，プレイヤー，スキルブロックを識別する処理を記述する
***
*******************************************************************/

package ConnectFour.Screen.PlayGameScreen;

import javafx.scene.paint.Color;

public enum PlayerAffiliation {
	NONE(0), PLAYER1(1), PLAYER2(2), BLOCK(3);
	
	final private Color color;

	/****************************************************************************
	*** Method Name         : PlayerAffiliation(int i)
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : 埋まっていないマスをColor.GREY，PLAYER1をColor.RED，PLAYER2をColor.YELLOW，
	                          スキルブロックをColor.BLUE，例外をColor.MEDIUMPURPLEとしてcolorに格納
	*** Return              : なし
	                          
	****************************************************************************/	

	PlayerAffiliation(int i) {
		switch(i) {
		
		case 0 :
			color = Color.GREY;
			break;
		case 1 :
			color = Color.RED;
			break;
		case 2 :
			color = Color.YELLOW;
			break;
		case 3 :
			color = Color.BLUE;
			break;
		default :
			color = Color.MEDIUMPURPLE;
			break;
		}
	}
	
	/****************************************************************************
	*** Method Name         : getColor()
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : colorを返り値として返す
	*** Return              : color 色情報
	****************************************************************************/
	
	Color getColor() {
		return color;
	}
}
