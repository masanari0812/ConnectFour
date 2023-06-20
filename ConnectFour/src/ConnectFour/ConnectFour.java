package ConnectFour;

import ConnectFour.Screen.PlayGameScreen.GameManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConnectFour extends Application {
	private static Stage stage;
	private static GameManager gm;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		setStage(stage);
		
		this.gm=new GameManager(7,6);
		stage.setTitle("ConnectFour");
		stage.show();
	}

	public static void setScene(Scene scene) {
		getStage().setScene(scene);
	}
	public static void setStage(Stage stage) {
		ConnectFour.stage = stage;
	}

	public static Stage getStage() {
		return ConnectFour.stage;
	}

}
