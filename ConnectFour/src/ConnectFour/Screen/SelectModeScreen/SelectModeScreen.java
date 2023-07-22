/*******************************************************************
*** File Name            : SelectModeScreen.java
*** Designer             : 新井田　俊輔
*** Date                 : 2023.07.18
*** Purpose              : ゲームをプレイする人数を選択する。
***
*******************************************************************/

package ConnectFour.Screen.SelectModeScreen;

import ConnectFour.Screen.OriginScreen;
import ConnectFour.Screen.SelectBoardScreen.SelectBoardScreen;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SelectModeScreen extends OriginScreen {
	
	/****************************************************************************
	*** Method Name         : SelectModeScreen()
	*** Designer            : 新井田　俊輔
	*** Date                : 2023.07.18
	*** Function            : テキストに基づいた２つのボタンを表示させる。
	                          またそのボタンを選択することが可能にする。
	*** Return              : 選択したプレイ人数
	****************************************************************************/
	
	public SelectModeScreen() {
		//複数のNodeを盾に結合できるVBoxを生成
		VBox vb = new VBox();
		//vb内で中央位置に合わせる。
		vb.setAlignment(Pos.CENTER);
		//(ButtonやTextなどの)Nodeの感覚を20pxに設定する。
		vb.setSpacing(20);
		
		//2つのテキストを作りClickButtonイベントを設定する。
		for (int i = 1; i <= 2; i++) {
			//Textに表示する文字列の作成
			String str = String.valueOf(i) + "人で対戦";
			//Buttonの中に文字を入れる。
			Button bt = new Button(str);
			//Button自体の大きさを設定する。
			bt.setPrefSize(200, 100);
			//文字の大きさを設定する。
			bt.setFont(new Font(25));
			//VBoxにbtというButtonを加える。
			vb.getChildren().add(bt);
			
			//作成したインスタンスにクリックしたときのイベントを設定
			if(i == 1)
				bt.setOnMouseClicked(new ClickButton(false));

			else
				bt.setOnMouseClicked(new ClickButton(true));
		}
		/*作成したVBoxをもとにSceneインスタンスを生成
		代入先はこのクラスの継承元OriginScreenのメンバ変数scene
		vb自体の大きさは(400,300)に設定する。 */
		 scene =new Scene(vb, 400, 300);
	}

	//Textインスタンスがクリックされたときに発生するイベントの定義
	class ClickButton implements EventHandler<MouseEvent> {
		//何番目のボタンかの情報を保持する変数
		private boolean online;

		//コンストラクタ
		public ClickButton(boolean online) {
			this.online = online;
		}

		//イベント発生時の処理
		@Override
		public void handle(MouseEvent e) {
			changeNextScreen(new SelectBoardScreen(online));

		}
	}
}
