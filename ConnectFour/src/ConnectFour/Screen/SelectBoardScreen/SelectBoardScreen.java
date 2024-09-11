/*******************************************************************
*** File Name            : SelectBoardScreen.java
*** Designer             : 新井田　俊輔
*** Date                 : 2023.07.18
*** Purpose              : プレイする盤面の大きさを選択する。
***
*******************************************************************/

package ConnectFour.Screen.SelectBoardScreen;

import ConnectFour.Screen.OriginScreen;
import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SelectBoardScreen extends OriginScreen {
	private boolean online;
	private TextField columnTF;
	private TextField rowTF;
	
	/****************************************************************************
	*** Method Name         : SelectBoardScreen(boolean online)
	*** Designer            : 新井田　俊輔
	*** Date                : 2023.07.18
	*** Function            : テキストに基づいた3つのボタンを表示させる。
	                          またそのボタンを選択することが可能にする。
	*** Return              : 選択した盤面の大きさ
	****************************************************************************/
	
	public SelectBoardScreen(boolean online) {
		this.online = online;
		
		//複数のNodeを横に結合できるVBoxを生成
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.setSpacing(20);
		
		//Textに表示する文字列(盤面の大きさ）の作成
		int column, row;
		for (int i = 1; i <= 3; i++) {
			switch (i) {
			case 1:
				column = 7;
				row = 6;
				break;
			case 2:
				column = 9;
				row = 7;
				break;
			case 3:
				column = 12;
				row = 6;
				break;
			default:
				column = -1;
				row = -1;
			}
			
			//Textインスタンスを生成し、インスタンスをクリックした時のイベントを設定
			Button bt = new Button(String.valueOf(column) + "×" + String.valueOf(row));
			bt.setOnMouseClicked(new ClickButton(column, row));
			bt.setPrefSize(300, 80);
			bt.setFont(new Font(25));
			vb.getChildren().add(bt);
			
		}

		//選んだテキストを表示
		HBox size = new HBox();
		size.setAlignment(Pos.CENTER);
		this.columnTF = new TextField();
		this.rowTF = new TextField();
		columnTF.setEditable(false);
		rowTF.setEditable(false);
		Text midText = new Text("×");
		size.getChildren().addAll(columnTF, midText, rowTF);
		
		//STARTボタンの表示
		Button start = new Button("START");
		start.setPrefSize(300, 80);
		start.setOnMousePressed(new ClickStart());
		vb.getChildren().addAll(size, start);
		scene = new Scene(vb, 400, 300);
	}

	//Textインスタンスがクリックされたときに発生するイベントの定義
	class ClickButton implements EventHandler<MouseEvent> {
		//何番目のボタンかの情報を保持する変数
		private int column;
		private int row;

		//コンストラクタ
		public ClickButton(int column, int row) {
			this.column = column;
			this.row = row;
		}

		//イベント発生時の処理
		@Override
		public void handle(MouseEvent e) {
			columnTF.setText(String.valueOf(column));
			rowTF.setText(String.valueOf(row));
		}
	}

	class ClickStart implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			try {
				int column = Integer.parseInt(columnTF.getText());
				int row = Integer.parseInt(rowTF.getText());
				changeNextScreen(new PlayGameScreen(online, column, row));
			} catch (NumberFormatException ex) {
			}
		}

	}

}
