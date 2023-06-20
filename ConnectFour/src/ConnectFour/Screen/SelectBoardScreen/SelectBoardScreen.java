package ConnectFour.Screen.SelectBoardScreen;

import ConnectFour.ConnectFour;
import ConnectFour.Communication.ClientManager;
import ConnectFour.Communication.ServerManager;
import ConnectFour.Screen.OriginScreen;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;




public class SelectBoardScreen extends OriginScreen {
	
	private static TextField tf;

	public SelectBoardScreen() {
		//複数のNodeを横に結合できるVBoxを生成
		HBox vb = new HBox();
		//(ButtonやTextなどの)Nodeの感覚を20pxに設定する。
		vb.setSpacing(20);
		//1から5までのテキストを作りClickButtonイベントを設定する。
		for (int i = 0; i <= 2; i++) {
			//Textに表示する文字列の作成
			String str = String.valueOf(7+(2*i)) + "×" + String.valueOf(6+(2*i));
			//Textインスタンス生成
			Text text = new Text(str);
			//作成したインスタンスにクリックしたときのイベントを設定
			text.setOnMouseClicked(new ClickButton(i));
			//VBoxに作成したNodeを追加(今回はTextインスタンス)
			vb.getChildren().add(text);
		}
		//メンバ変数tfにTextFieldインスタンスを生成し代入

		//通信用デバッグ処理
		Thread cm = new ClientManager();
		cm.start();
		Button send = new Button("send LocalIP");
		send.setOnMouseClicked(new sendLocalIP());
		vb.getChildren().add(send);
		//通信用デバッグ処理終了

		SelectBoardScreen.tf = new TextField();

		//VBoxに作成したNodeを追加(今回はTextFieldインスタンス)
		vb.getChildren().add(tf);

		//作成したVBoxをもとにSceneインスタンスを生成
		//代入先はこのクラスの継承元OriginScreenのメンバ変数scene
		scene = new Scene(vb);
		//画面切り替え処理
		changeNextScreen();

	}

	//スクリーン転換時の処理
	//(今回はデモなのでスクリーン転換ではなくスクリーン設定のみになっている)
	@Override
	public void changeNextScreen() {

		//ConnctFour.getStage()で起動中のStageを持ってこれる
		//持ってきたStageに作成したSceneを設定(この時点で画面が切り替わる)
		ConnectFour.getStage().setScene(scene);
	}

	//Textインスタンスがクリックされたときに発生するイベントの定義
	class ClickButton implements EventHandler<MouseEvent> {
		//何番目のボタンかの情報を保持する変数
		private int num;

		//コンストラクタ
		public ClickButton(int num) {
			this.num = num;
		}

		//イベント発生時の処理
		@Override
		public void handle(MouseEvent e) {
			//TextFieldに表示する文字列の作成
			String str = String.valueOf(7+(2*num)) + "×" + String.valueOf(6+(2*num));
			//TextFieldの文字列の変更処理
			tf.setText(str);
		}
	}

	//通信用デバッグ
	public static void changeString(String str) {
		tf.setText(str);
	}

	class sendLocalIP implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent arg0) {
			Thread sm = new ServerManager();
			sm.start();
		}
	}
	//通信用デバッグ処理終了

}
