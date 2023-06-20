package ConnectFour.Screen.PlayGameScreen;

import java.util.ArrayList;
import java.util.List;

import ConnectFour.ConnectFour;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameManager {
	private List<List<PlayerAffiliation>> boardState;
	private int column, row;
	private HBox hb;

	public GameManager(int column, int row) {
		boardState = new ArrayList<>();
		for (int x = 0; x < column; x++) {
			boardState.add(new ArrayList<>());
		}
		this.column = column;
		this.row = row;
		reloadBoard();
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

	public void setSpace(PlayerAffiliation player, int x, int y) {
		if (player == PlayerAffiliation.NONE)
			return;
		boardState.get(x).remove(y);
		boardState.get(x).add(y, player);
		reloadBoard();
	}

	public void setSpace(PlayerAffiliation player, int x) {
		if (player == PlayerAffiliation.NONE)
			return;
		boardState.get(x).add(player);
		reloadBoard();
	}

	public void reloadBoard() {
		HBox hb = new HBox();
		hb.setSpacing(5);
		for (int x = 0; x < column; x++) {
			VBox vb;
			hb.getChildren().add(vb = new VBox());
			vb.setSpacing(5);
			for (int y = row - 1; y >= 0; y--) {
				//for (int y = 0; y < row; y++) {
				Circle space = new Circle(40, 50, 50); // space:マス radius:30
				space.setFill(getSpace(x, y).getColor());
				space.setOnMousePressed(new ClickBoardEventHandler(x, y));
				vb.getChildren().add(space);
			}
		}

		VBox sideBar = new VBox();
		Button bt = new Button("Skill");
		bt.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		Rectangle r = new Rectangle(40, 50, 120, 360);
		r.setFill(Color.GREY);
		sideBar.getChildren().addAll(r, bt);
		hb.getChildren().add(sideBar);
		Scene sc = new Scene(hb);
		ConnectFour.getStage().setScene(sc);
	}

	public int getFirstNoneSpace(int x) {
		return boardState.get(x).size();
	}

	// 盤面の把握
	public int countColumnSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (getSpace(x, y).equals(team)) {
					count++;
				} else {
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
				} else {
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
				if (getSpace(x + y, y).equals(team)) {
					count++;
				} else {
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
				if (getSpace(column - x - y - 1, y).equals(team)) {
					count++;
				} else {
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
		} else {
			return true;
		}
	}

	// マウスでマスをクリックしたら赤or黄色に染まる処理を追加する.
	class ClickBoardEventHandler implements EventHandler<MouseEvent> {
		private int x;
		private int y;

		public ClickBoardEventHandler(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public ClickBoardEventHandler(int x) {
			this.x = x;
		}

		@Override
		public void handle(MouseEvent e) {
			if (e.isPrimaryButtonDown())
				setSpace(PlayerAffiliation.PLAYER1, x);
			else if (e.isSecondaryButtonDown())
				setSpace(PlayerAffiliation.PLAYER2, x);
			System.out.println(String.valueOf(x) + " " + String.valueOf(y));

		}
	}
}
