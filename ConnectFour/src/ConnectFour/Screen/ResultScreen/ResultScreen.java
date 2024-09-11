/*******************************************************************
*** File Name            : ResultScreen.java
*** Designer             : 新井田 俊輔, 千葉 飛馬
*** Date                 : 2023.07.18
*** Purpose              : 対戦結果画面を表示する。
***
*******************************************************************/

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
	private Button selectModeButton;
	private Button resultShowButton;

	public ResultScreen(String playerResult, boolean online, int column, int row) {
		Text result;
		
		//勝敗で表示テキスト、対戦記録の情報を決める
		if (playerResult == "win") {
			result = new Text("Win!!");
			String resultText = "Win";
			ResultRecordManageMain.resultRecord(resultText);
		} else if (playerResult == "lose"){
			result = new Text("Lose...");
			String resultText = "Lose";
			ResultRecordManageMain.resultRecord(resultText);
		} else {
			result = new Text("Draw");
			String resultText = "Draw";
			ResultRecordManageMain.resultRecord(resultText);
		}

		BorderPane.setAlignment(result, Pos.CENTER);
		result.setFont(new Font(25));
		
		//ボタンの作成
		continueButton = new Button("続ける");
		selectModeButton = new Button("モード選択");
		resultShowButton = new Button("対戦記録表示");
		
		//それぞれのボタンの処理を設定
		continueButton.setOnMousePressed(event -> {
			changeNextScreen(new PlayGameScreen(online, column, row));
		});
		selectModeButton.setOnMousePressed(event -> {
			changeNextScreen(new SelectModeScreen());
		});
		resultShowButton.setOnMousePressed(event -> {
			ResultRecordManageMain.resultRecordShow();
		});
		
		//Buttonの文字の大きさを設定する
		continueButton.setFont(new Font(25));
		selectModeButton.setFont(new Font(25));
		resultShowButton.setFont(new Font(15));

		BorderPane bp = new BorderPane();
		bp.setCenter(result);
		bp.setLeft(continueButton);
		bp.setRight(selectModeButton);
		bp.setBottom(resultShowButton);
		
		//継承元のOriginScreenにあるscene変数に格納 */
		this.scene = new Scene(bp, 300, 200);
	}
}
