package ConnectFour.Screen.SelectModeScreen;

import ConnectFour.Screen.OriginScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SelectModeScreen extends OriginScreen{
	
	private TextField txtField;
	private Button button;
	

	public void start(Stage stage) throws Exception {
		txtField = new TextField("テキスト");
		button = new Button("確定");
	    BorderPane bp = new BorderPane();
	    bp.setTop(txtField);
	    bp.setCenter(button);
	    button.setOnAction(new MousePressedHandler());
	    this.scene = new Scene(bp,300,200);
	}
	
	public class MousePressedHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {

			
		}
		
	}

	@Override
	public void changeNextScreen() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}


