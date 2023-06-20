package ConnectFour.Screen.SelectModeScreen;

import ConnectFour.Screen.OriginScreen;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SelectModeScreen extends OriginScreen{
	
	private TextField txtField;
	private Button button;
	
	public static void main(String[] args) {
	        launch(args);
	}

	public void start(Stage stage) throws Exception {
		txtField = new TextField("テキスト");
		button = new Button("確定");
		

	    BorderPane bp = new BorderPane();
	    bp.setTop(txtField);
	    bp.setCenter(button);
	    button.setOnAction(new MousePressedHandler());
	    Scene sc = new Scene(bp,300,200);
	    stage.setScene(sc);
	    stage.setTitle("モード選択");
	    stage.show();
	    
	}
	
	class MousePressedHandler implements EvendHandler<ActonEvent>{
		
	}
}


