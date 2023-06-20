package ConnectFour.Screen.PlayGameScreen;

import java.util.ArrayList;
import java.util.List;
//import javafx.event.EventHandler;

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
	
	
	// 盤面の把握
	public int countColumnSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (getSpace(x, y).equals(team)) {
					count++;
				}
				else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}
	
	public int countRowSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int y = 0; y < row; y++) {
			count = 0;
			for (int x = 0; x < column; x++) {
				if (getSpace(x, y).equals(team)) {
					count++;
				}
				else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}
	
	public int countRightSlashSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (getSpace(x+y, y).equals(team)) {
					count++;
				}
				else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}
	
	public int countLeftSlashSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (getSpace(column-x-y-1, y).equals(team)) {
					count++;
				}
				else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}
	
	public boolean breakSpace(int x, int y) {
		if (getSpace(x, y).equals(PlayerAffiliation.NONE)) {
			return false;
		}
		else {
			return true;
		}
	}
		
}
