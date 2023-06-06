package ConnectFour;

import ConnectFour.Screen.DemoScreen.DemoScreen;
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
		DemoScreen ds = new DemoScreen();
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
