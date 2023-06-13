package ConnectFour.Screen.PlayGameScreen;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class PlayMain extends Application {
	private Button bt;
	private HBox hb;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// 盤面,チャット欄,スキルボタンを表示する処理を書く.
	// イベントハンドラを登録する.
	@Override
	public void start(Stage stage) throws Exception {
		int column = 7, row = 6;
		hb = new HBox();
		hb.setSpacing(5); // マスとマスの間隔を設定
		VBox vb1;
		
		// 盤面の生成(7×6)
		for (int x = 0; x < column; x++) {
			hb.getChildren().add(vb1 = new VBox());
			vb1.setSpacing(5);
			for (int y = 0; y < row; y++) {
				Circle space = new Circle(40, 50, 30); // space:マス radius:30
				space.setFill(Color.GREY);
				VBox vb2 = (VBox) hb.getChildren().get(x);
				vb2.getChildren().add(space);
			}
			hb.getChildren().get(x).setOnMousePressed(new ClickBoardEventHandler(x));
		}
		
		// チャット欄とスキルボタンの生成　　ただし，「一人で対戦」の場合チャット欄は空白とする．
		VBox sideBar = new VBox();
		bt = new Button("Skill");
		bt.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		Rectangle r = new Rectangle(40, 50, 120, 360);
		r.setFill(Color.GREY);
		sideBar.getChildren().addAll(r, bt);
		hb.getChildren().addAll(sideBar);

		// 盤面を画面に表示する
		Scene sc = new Scene(hb);
		stage.setScene(sc);
		stage.setTitle("Connect Four");
		stage.show();
	}
	
	// マウスでマスをクリックしたら赤or黄色に染まる処理を追加する.
	class ClickBoardEventHandler implements EventHandler<MouseEvent> {
		private int x;
		
		public ClickBoardEventHandler(int x) {
			this.x = x;
		}
		
		@Override
		public void handle(MouseEvent e) {
			VBox vb = (VBox) hb.getChildren().get(x);
			for (Node node : vb.getChildren()) {
				Circle space = (Circle) node;
				if (space.getFill() == Color.GREY) {
					if (e.isPrimaryButtonDown()) {
						space.setFill(PlayerAffiliation.PLAYER1.getColor()); // 別のクラスファイルで色を指定?
						//gm.putOnspace();
					} else if (e.isSecondaryButtonDown()) {
						space.setFill(PlayerAffiliation.PLAYER2.getColor());
						//gm.putOnspace();
					}
					break;
				}
			}
		}
	}
	/*
	class ClickBottunEventHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			// スキルの効果を反映させる処理を書く.
		}
	} */
}
