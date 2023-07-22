/*******************************************************************
*** File Name            : OriginScreen.java
*** Designer             : 玉木 将成
*** Date                 : 2023.07.10
*** Purpose              : スクリーン用親クラス
***
*******************************************************************/

package ConnectFour.Screen;

import ConnectFour.ConnectFour;
import javafx.scene.Scene;

public abstract class  OriginScreen  {
	//各スクリーンを格納する変数
	protected Scene scene;

	/****************************************************************************
	*** Method Name         : changeNextScreen(OriginScreen sc)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : 次のスクリーンに切り替える処理
	*** Return              : void
	****************************************************************************/
	public void changeNextScreen(OriginScreen sc) {
		/*
		//ConnctFour.getStage()で起動中のStageを持ってこれる
		//持ってきたStageに作成したSceneを設定(この時点で画面が切り替わる)
		ConnectFour.getStage().setScene(scene);
		*/
		ConnectFour.getStage().setScene(sc.getScene());
	}
	/****************************************************************************
	*** Method Name         : getScene()
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : シーン取得
	*** Return              : Scene
	****************************************************************************/
	public Scene getScene() {
		return scene;
	}
}
