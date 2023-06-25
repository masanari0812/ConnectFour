package ConnectFour.Screen.PlayGameScreen;

import java.util.ArrayList;
import java.util.List;

import ConnectFour.ConnectFour;
import ConnectFour.Screen.OriginScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class PlayGameScreen extends OriginScreen {
	private List<List<PlayerAffiliation>> boardState;
	private int column, row;
	private int used_skill = 0;		// スキルを使用した回数を判断する変数
	//private HBox hb;
	
	
	
	// columnとrowをコンストラクタで取得
	public PlayGameScreen(int column, int row) {
		boardState = new ArrayList<>();
		for (int x = 0; x < column; x++) {		// 列の数だけArrayListを追加
			boardState.add(new ArrayList<>());
		}
		this.column = column;
		this.row = row;
		reloadBoard();
	}

	// x 列の一番下のマスを染める
	public PlayerAffiliation putOnSpace(PlayerAffiliation player, int x) {	// x: 列の場所 0 to 6
		if (boardState.get(x).size() <= row) {
			boardState.get(x).add(player);
		}
		return PlayerAffiliation.NONE;
	}

	
	// 特定のマスを返り値として返す
	public PlayerAffiliation getSpace(int x, int y) {
		if (x < 0 || x >= column || y < 0 || y >= boardState.get(x).size()) {
			return PlayerAffiliation.NONE;
		}
		return boardState.get(x).get(y);
	}

	
	// x 列 y 行目のマスをplayerの色で染め，盤面を更新
	public void setSpace(PlayerAffiliation player, int x, int y) {
		if (player == PlayerAffiliation.NONE)
			return;
		boardState.get(x).remove(y);
		boardState.get(x).add(y, player);	// y: 追加する場所  player: 追加する値
		reloadBoard();
	}

	
	// x 列の一番下のマスを染め，盤面を更新
	public void setSpace(PlayerAffiliation player, int x) {
		if (player == PlayerAffiliation.NONE)
			return;
		boardState.get(x).add(player);
		System.out.println(String.valueOf(x) + " " + String.valueOf(boardState.get(x).size()));
		reloadBoard();
	}

	
	// スキルの処理(青色のマスで層を作る)
	public void activateSkill(List<List<PlayerAffiliation>> boardState) {
		used_skill += 1;
		int count;
		
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (count == 0 && boardState.get(x).get(row-1-y) == PlayerAffiliation.NONE) {
					setSpace(PlayerAffiliation.BLOCK, x, row-1-y);
					count += 1;
				}
			}
		}		
		reloadBoard();
	}
	
	
	// 盤面の生成・更新，イベントハンドラの登録(マウス，ボタン)
	// used_skill が 2 の場合，ボタンは生成しない
	public void reloadBoard() {
		HBox hb = new HBox();
		hb.setSpacing(5);
		for (int x = 0; x < column; x++) {
			VBox vb;
			hb.getChildren().add(vb = new VBox());
			vb.setSpacing(5);
			for (int y = row - 1; y >= 0; y--) {
				Circle space = new Circle(40, 50, 50); 			// space: マス
				space.setFill(getSpace(x, y).getColor());
				vb.getChildren().add(space);
			}
			vb.setOnMousePressed(new ClickBoardEventHandler(x));
		}

		VBox sideBar = new VBox();
		if (used_skill != 2) {
			Button bt = new Button("Skill");
			bt.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			Rectangle r = new Rectangle(40, 50, 120, 360);
			r.setFill(Color.GREY);
			sideBar.getChildren().addAll(r, bt);
			bt.setOnAction(new ClickButtonEventHandler(boardState));
		}
		else {
			Rectangle r = new Rectangle(40, 50, 120, 360);
			r.setFill(Color.GREY);
			sideBar.getChildren().add(r);
		}
		hb.getChildren().add(sideBar);
		Scene sc=new Scene(hb);
		ConnectFour.setScene(sc);
	}

	
	// x 列の要素数を返す
	public int getFirstNoneSpace(int x) {
		return boardState.get(x).size();
	}


	// 盤面の把握 列の確認
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
	
	
	// 行の確認
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

	
	// 斜めの確認1
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

	
	// 斜めの確認2
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

	
	
	// マウスでマスをクリックしたら赤or黄色に染まる処理
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

	
	// Skillボタンをクリックしたらスキルの効果を反映させる処理
	class ClickButtonEventHandler implements EventHandler<ActionEvent> {
		private List<List<PlayerAffiliation>> boardState;
		
		
		public ClickButtonEventHandler(List<List<PlayerAffiliation>> boardState) {		// 盤面の情報を受け取る
			this.boardState = boardState;
		}
		
		@Override
		public void handle(ActionEvent e) {
			activateSkill(boardState);
			System.out.println("activate skill");
		}
	}
	
	
	
	@Override
	public void changeNextScreen() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
