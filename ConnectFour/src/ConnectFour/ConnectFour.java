/*******************************************************************
*** File Name            : ConnectFour.java
*** Designer             : 玉木 将成
*** Date                 : 2023.07.10
*** Purpose              : UI処理
***						   起動処理
***
*******************************************************************/

package ConnectFour;

import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;
import ConnectFour.Screen.ResultScreen.ResultScreen;
import ConnectFour.Screen.SelectBoardScreen.SelectBoardScreen;
import ConnectFour.Screen.SelectModeScreen.SelectModeScreen;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConnectFour extends Application {
	private static Stage stage;

	/****************************************************************************
	*** Method Name         : main(String[] args)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : JavaFX起動処理
	*** Return              : void
	****************************************************************************/
	public static void main(String[] args) {
		launch(args);
	}
	
	/****************************************************************************
	*** Method Name         : main(String[] args)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : ウィンドウ生成
							  SelectModeScreen切替
	*** Return              : void
	****************************************************************************/
	@Override
	public void start(Stage stage) throws Exception {
		setStage(stage);
		/*
		VBox vb = new VBox();
		vb.setSpacing(20);
		for (int i = 1; i <= 5; i++) {
			//Textに表示する文字列の作成
			String str = String.valueOf(i) + "番目のボタン";
			//Textインスタンス生成
			Text text = new Text(str);
			//作成したインスタンスにクリックしたときのイベントを設定
			text.setOnMouseClicked(new DebugClickButton(i));
			//VBoxに作成したNodeを追加(今回はTextインスタンス)
			vb.getChildren().add(text);
		}
		*/
		setScene(new SelectModeScreen().getScene());
		stage.setTitle("ConnectFour");
		stage.show();
	}
	/****************************************************************************
	*** Method Name         : setScene(Scene scene)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : Scene切替
	*** Return              : void
	****************************************************************************/
	public static void setScene(Scene scene) {
		getStage().setScene(scene);
	}
	/****************************************************************************
	*** Method Name         : setScene(Scene scene)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : stage切替
	*** Return              : void
	****************************************************************************/
	public static void setStage(Stage stage) {
		ConnectFour.stage = stage;
	}
	/****************************************************************************
	*** Method Name         : getStage() 
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : stage取得
	*** Return              : Stage
	****************************************************************************/
	public static Stage getStage() {
		return ConnectFour.stage;
	}

	class DebugClickButton implements EventHandler<MouseEvent> {
		//何番目のボタンかの情報を保持する変数
		private int num;

		//コンストラクタ
		public DebugClickButton(int num) {
			this.num = num;
		}

		//イベント発生時の処理
		@Override
		public void handle(MouseEvent e) {
			switch (num) {
			/*
			case 1:
				DemoScreen ds = new DemoScreen();
				setScene(ds.getScene());
				break;
			*/
			case 2:
				PlayGameScreen pgs = new PlayGameScreen(false, 7, 6);
				setScene(pgs.getScene());
				break;
			case 3:
				ResultScreen rs = new ResultScreen("win",false,7,6);
				setScene(rs.getScene());
				break;
			case 4:
				SelectBoardScreen sbs = new SelectBoardScreen(false);
				setScene(sbs.getScene());
				break;
			case 5:
				SelectModeScreen sms = new SelectModeScreen();
				ConnectFour.getStage().setScene(sms.getScene());
				break;
			}
		}
	}
}
