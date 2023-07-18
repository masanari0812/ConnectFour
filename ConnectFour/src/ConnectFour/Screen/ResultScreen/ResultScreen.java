package ConnectFour.Screen.ResultScreen;

import ConnectFour.Screen.OriginScreen;
import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;
import ConnectFour.Screen.SelectModeScreen.SelectModeScreen;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ResultScreen extends OriginScreen {

	private Button continueButton;
	private Button selectModeBbutton;
	private Button resultShowButton;

	public ResultScreen(String playerResult, boolean online, int column, int row) {
		Text result;

		if (playerResult == "win") {
			result = new Text("Win!!");
			String rs = "Win";
			ResultRecordManageMain.ResultRecord(rs);
		} else if (playerResult == "lose"){
			result = new Text("Lose...");
			String rs = "Lose";
			ResultRecordManageMain.ResultRecord(rs);
		} else {
			result = new Text("Draw");
			String rs = "Draw";
			ResultRecordManageMain.ResultRecord(rs);
		}

		BorderPane.setAlignment(result, Pos.CENTER);
		result.setFont(new Font(25));

		continueButton = new Button("続ける");
		selectModeBbutton = new Button("モード選択");
		resultShowButton = new Button("対戦記録表示");

		continueButton.setOnMousePressed(event -> {
			changeNextScreen(new PlayGameScreen(online, column, row));
		});
		selectModeBbutton.setOnMousePressed(event -> {
			changeNextScreen(new SelectModeScreen());
		});
		resultShowButton.setOnMousePressed(event -> {
			ResultRecordManageMain.ResultRecordShow();
		});

		continueButton.setFont(new Font(25));
		//buttonの文字の大きさを25にする
		selectModeBbutton.setFont(new Font(25));
		//button1の文字の大きさを25にする
		resultShowButton.setFont(new Font(15));
		//resultButtonの文字の大きさを25にする

		BorderPane bp = new BorderPane();
		bp.setCenter(result);
		bp.setLeft(continueButton);
		bp.setRight(selectModeBbutton);
		bp.setBottom(resultShowButton);
		/*//button.setOnAction(new MousePressedHandler());
		
		//継承元のOriginScreenにあるscene変数に格納 */
		this.scene = new Scene(bp, 300, 200);
	}
}
