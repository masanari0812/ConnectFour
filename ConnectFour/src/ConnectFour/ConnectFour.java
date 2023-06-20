package ConnectFour;

import java.util.HashMap;

import ConnectFour.Screen.OriginScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class ConnectFour extends Application {
	private static Stage stage;
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		setStage(stage);
		
		
		HashMap<String,OriginScreen> sc;
		
		
		stage.setTitle("ConnectFourDemo");
		stage.show();
	}

	public static void setStage(Stage stage) {
		ConnectFour.stage = stage;
	}

	public static Stage getStage() {
		return ConnectFour.stage;
	}

}
