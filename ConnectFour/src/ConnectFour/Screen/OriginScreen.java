package ConnectFour.Screen;

import javafx.scene.Scene;

public abstract class  OriginScreen  {
	//各スクリーンを格納する変数
	protected Scene scene;
	
	//スクリーン転換時の処理
	//(例:SelectModeScreenからPlayGameScreenへの画面切り替え処理)
	public abstract void changeNextScreen();
	
	public Scene getScene() {
		return scene;
	}
}
