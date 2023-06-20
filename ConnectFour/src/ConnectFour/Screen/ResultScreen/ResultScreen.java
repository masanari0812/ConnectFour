package ConnectFour.Screen.ResultScreen;

import ConnectFour.ConnectFour;
import ConnectFour.Screen.OriginScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public class ResultScreen extends OriginScreen {
	
	private Button button;
	private Button button1;
	
	private static TextField tf;

		public ResultScreen(){
			button = new Button("続ける");
			button1 = new Button("モード選択");
		    BorderPane bp = new BorderPane();
		    bp.setLeft(button);
		    bp.setRight(button1);
		    //button.setOnAction(new MousePressedHandler());
		    
		    //継承元のOriginScreenにあるscene変数に格納
		    this.scene = new Scene(bp,300,200);
		}
		
		public class MousePressedHandler implements EventHandler<ActionEvent>{
			private int num;

			
			public MousePressedHandler(int num) {
				this.num = num;
			}
			
			@Override
			public void handle(ActionEvent arg0) {	
				String str = String.valueOf(num);
				//TextFieldの文字列の変更処理
				tf.setText(str);
			}
			
		}

		@Override
		public void changeNextScreen() {
			ConnectFour.getStage().setScene(scene);
			
		}
	


		
	
}

