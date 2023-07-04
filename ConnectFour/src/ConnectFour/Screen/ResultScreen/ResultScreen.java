package ConnectFour.Screen.ResultScreen;

import ConnectFour.Screen.OriginScreen;
import ConnectFour.Screen.SelectBoardScreen.SelectBoardScreen;
import ConnectFour.Screen.SelectModeScreen.SelectModeScreen;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class ResultScreen extends OriginScreen {
	
	private Button button;
	private Button button1;
	
	private static TextField tf;

		public ResultScreen(){
			button = new Button("続ける");
			button1 = new Button("モード選択");
			
			//buttonの文字の大きさを25にする
			button.setFont(new Font(25));
			
			//button1の文字の大きさを25にする
			button1.setFont(new Font(25));
			
		    BorderPane bp = new BorderPane();
		    
		    //左右に２つのボタンを設置する
		    bp.setLeft(button);
		    bp.setRight(button1);
		    
		    /*//button.setOnAction(new MousePressedHandler());
		    //継承元のOriginScreenにあるscene変数に格納 */
		    button.setOnMouseClicked(new ClickButton(true));
		    button1.setOnMouseClicked(new ClickButton1());
		    
		    this.scene = new Scene(bp,300,200);
		}
		
		//button(続ける)がクリックされたときに発生するイベントの定義
		class ClickButton implements EventHandler<MouseEvent>{
			//何番目のボタンかの情報を保持する変数
			private boolean online;
			
			//コンストラクタ
			public ClickButton(boolean multi) {
				this.online = multi;
			}
			
			//イベント発生時の処理
			@Override
			public void handle(MouseEvent e) {
				changeNextScreen(new SelectBoardScreen(online));
			}
		}
		
		
		//button1(モード選択)がクリックされたときに発生するイベントの定義
		class ClickButton1 implements EventHandler<MouseEvent>{
			
			//イベント発生時の処理
			@Override
			public void handle(MouseEvent e) {
				changeNextScreen(new SelectModeScreen());
			}
		}
	
		
}		
		