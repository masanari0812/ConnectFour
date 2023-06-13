package ConnectFour.Screen.PlayGameScreen;

import java.util.ArrayList;
import java.util.List;
// improt javafx.event.EventHandler;

public class GameManager {
	private List<List<PlayerAffiliation>> boardState;
	private int column, row;
	
	public GameManager(int column, int row) {
		boardState = new ArrayList<>();
		for (int x = 0; x < column; x++) {
			boardState.add(new ArrayList<>());
		}
		this.column = column;
		this.row = row;
	}
	
	public PlayerAffiliation putOnSpace(PlayerAffiliation player, int x) {
		if (boardState.get(x).size() <= row) {
			boardState.get(x).add(player);
		}
		return PlayerAffiliation.NONE;
	}
	
	public PlayerAffiliation getSpace(int x, int y) {
		if (x < 0 || x >= column || y < 0 || y >= boardState.get(x).size()) {
			return PlayerAffiliation.NONE;
		}
		return boardState.get(x).get(y);
	}
	
	public int getFirstNoneSpace(int x) {
		return boardState.get(x).size() - 1;
	}
	
	/*
	public class activateSkill implements EventHandler<BottunEvent> {
		
	} */
}
