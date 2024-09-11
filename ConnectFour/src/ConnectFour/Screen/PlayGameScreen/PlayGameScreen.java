/*******************************************************************
*** File Name             : PlayGameScreen.java
*** Designer              : 塚田 大貴，玉木 将成
*** Date                  : 2023.07.22
*** Purpose               : ゲームの進行処理を記述する
***
*******************************************************************/

package ConnectFour.Screen.PlayGameScreen;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ConnectFour.ConnectFour;
import ConnectFour.Communication.CommunicationObject;
import ConnectFour.Communication.ServerManager;
import ConnectFour.Screen.OriginScreen;
import ConnectFour.Screen.ResultScreen.ResultScreen;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayGameScreen extends OriginScreen {
	private List<List<PlayerAffiliation>> boardState;
	private int column, row;
	private boolean skill; // スキルを使用した回数を判断する変数
	private boolean online;
	private boolean host;
	private boolean end;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Thread onlineMgr;
	private PlayerAffiliation turn;
	private TextArea chatHistory;
	private TextField chatBox;
	private CommunicationThread ct;

	/****************************************************************************
	*** Method Name         : PlayGameScreen()
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : 盤面の行数と列数をコンストラクタで取得する
	*** Return              : void
	****************************************************************************/

	// columnとrowをコンストラクタで取得
	public PlayGameScreen(boolean online, int column, int row) {
		this.column = column;
		this.row = row;
		this.online = online;
		this.host = true;
		this.end = false;
		this.skill = true;
		this.turn = PlayerAffiliation.PLAYER1;

		if (online) {
			Text text = new Text("Matching now");
			text.setFont(new Font(30));
			BorderPane bp = new BorderPane();
			bp.setCenter(text);
			scene = new Scene(bp, 400, 300);
			onlineMgr = new ServerManager(this);
			onlineMgr.start();
		} else {
			makeBoard();
		}
	}

	/****************************************************************************
	*** Method Name         : makeBoard()
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : 盤面生成処理
	*** Return              : void
	****************************************************************************/

	public void makeBoard() {
		System.out.println("d");
		if (online) {
			try {
				if (host) {
					sendCommunicationObject(new CommunicationObject(null, column, row));
				} else {
					CommunicationObject size = (CommunicationObject) ois.readObject();
					column = size.getX();
					row = size.getY();
				}
				this.ct = new CommunicationThread();
				ct.start();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			this.chatHistory = new TextArea();
			chatHistory.setFont(new Font(25));
			chatHistory.setPrefSize(300, row * 20);
			chatHistory.setEditable(false);
			this.chatBox = new TextField();
			chatBox.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.ENTER) {
					// エンターキーが押されたときの処理を記述する
					chatHistory.appendText("You: " + chatBox.getText() + "\n");
					sendCommunicationObject(new CommunicationObject(chatBox.getText()));
					chatBox.clear();
					System.out.println("エンターキーが押されました。入力値: " + chatBox.getText());
				}
			});
		}
		System.out.println("d");
		boardState = new ArrayList<>();
		for (int x = 0; x < column; x++) { // 列の数だけArrayListを追加
			boardState.add(new ArrayList<>());
		}

		reloadBoard();

	}

	/****************************************************************************
	*** Method Name         : putOnSpace(PlayerAffiliation player, int x)
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : x列の一番下のマスを染める
	*** Return              : PlayerAffiliation.NONE
	****************************************************************************/

	// x 列の一番下のマスを染める
	public PlayerAffiliation putOnSpace(PlayerAffiliation player, int x) { // x: 列の場所 0 to 6
		if (boardState.get(x).size() <= row) {
			boardState.get(x).add(player);
		}
		return PlayerAffiliation.NONE;
	}

	/****************************************************************************
	*** Method Name         : getSpace(int x, int y)
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : 特定のマスを返り値として返す
	*** Return              : boardState.get(x).get(y) x列y行のマス
	****************************************************************************/

	// 特定のマスを返り値として返す
	public PlayerAffiliation getSpace(int x, int y) {
		if (x < 0 || x >= column || y < 0 || y >= boardState.get(x).size()) {
			return PlayerAffiliation.NONE;
		}
		return boardState.get(x).get(y);
	}

	/****************************************************************************
	*** Method Name         : setSpace(PlayerAffiliation player, int x, int y)
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : x列，y行目のマスをplayerの色で染め，盤面を更新
	*** Return              : void
	****************************************************************************/

	// x 列 y 行目のマスをplayerの色で染め，盤面を更新
	public void setSpace(PlayerAffiliation player, int x, int y) {
		if (player == PlayerAffiliation.NONE)
			return;
		boardState.get(x).remove(y);
		boardState.get(x).add(y, player); // y: 追加する場所  player: 追加する値
		reloadBoard();
	}

	/****************************************************************************
	*** Method Name         : setSpace(PlayerAffiliation player, int x)
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : x列の一番下のマスを染め，盤面を更新
	*** Return              : void
	****************************************************************************/

	// x 列の一番下のマスを染め，盤面を更新
	public void setSpace(PlayerAffiliation player, int x) {
		if (player != turn || end)
			return;
		boardState.get(x).add(player);
		System.out.println(player.toString());

		if (judgeWin()) {
			reloadBoard();
			this.end = true;
			Stage simpleResult = new Stage();
			Text result = new Text(turn.toString() + " Win!");
			result.setFont(new Font(25));
			Button nextScreen = new Button("OK");
			nextScreen.setFont(new Font(25));
			nextScreen.setOnMousePressed(event -> {
				simpleResult.hide();
				if (online) {
					if ((turn == PlayerAffiliation.PLAYER1) != host)
						changeNextScreen(new ResultScreen("win", online, column, row));
					else
						changeNextScreen(new ResultScreen("lose", online, column, row));

				} else {
					if (player == PlayerAffiliation.PLAYER1)
						changeNextScreen(new ResultScreen("win", online, column, row));
					else
						changeNextScreen(new ResultScreen("lose", online, column, row));
				}
			});
			VBox sr = new VBox();
			sr.setSpacing(50);
			sr.setAlignment(Pos.CENTER);
			sr.getChildren().addAll(result, nextScreen);
			simpleResult.setScene(new Scene(sr, 300, 200));
			simpleResult.show();
		} else {
			int count = 0;
			for (List<PlayerAffiliation> s : boardState)
				if (s.size() >= row)
					count++;
			if (count >= column) {
				this.end = true;
				Stage simpleResult = new Stage();
				Text result = new Text("Draw");
				result.setFont(new Font(25));
				Button nextScreen = new Button("OK");
				nextScreen.setOnMousePressed(event -> {
					simpleResult.hide();
					changeNextScreen(new ResultScreen("draw", online, column, row));
				});
				VBox sr = new VBox();
				sr.getChildren().addAll(result, nextScreen);
				simpleResult.setScene(new Scene(sr));
				simpleResult.show();
			} else {
				changeTurn();
				reloadBoard();
			}
		}
	}

	/****************************************************************************
	*** Method Name         : activateSkill()
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : スキルの処理(青色のマスで層を作る)
	*** Return              : void
	****************************************************************************/

	// スキルの処理(青色のマスで層を作る)
	public void activateSkill() {
		if (online) {
			if ((turn == PlayerAffiliation.PLAYER1) == host) {
				if (skill) {
					sendCommunicationObject(new CommunicationObject(0, 0));
					skill = false;
				} else
					return;
			}
		} else {
			if (turn != PlayerAffiliation.PLAYER1 || !skill)
				return;
			this.skill = false;
		}
		for (int x = 0; x < column; x++)
			if (getFirstNoneSpace(x) != row)
				boardState.get(x).add(PlayerAffiliation.BLOCK);
		changeTurn();
		reloadBoard();
	}

	/****************************************************************************
	*** Method Name         : reloadBoard()
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : 盤面の生成・更新，イベントハンドラの登録を行う
	*** Return              : void
	****************************************************************************/

	// 盤面の生成・更新，イベントハンドラの登録(マウス，ボタン)
	// used_skill が 2 の場合，ボタンは生成しない
	public void reloadBoard() {
		HBox hb = new HBox();
		hb.setSpacing(5);
		for (int x = 0; x < column; x++) {
			VBox vb;
			hb.getChildren().add(vb = new VBox());
			vb.setSpacing(5);
			for (int y = row - 1; y >= 0; y--) {
				Circle space = new Circle(40, 50, 50); // space: マス
				space.setFill(getSpace(x, y).getColor());
				vb.getChildren().add(space);
			}
			vb.setOnMousePressed(new ClickBoardEventHandler(x));
		}

		VBox sideBar = new VBox();
		if (online) {
			sideBar.getChildren().addAll(chatHistory, chatBox);
		} else {
			Rectangle r = new Rectangle(40, 50, 120, 360);
			r.setFill(Color.GREY);
			sideBar.getChildren().add(r);
		}
		if (skill && (turn == PlayerAffiliation.PLAYER1) == host) {
			Button bt = new Button("Skill");
			bt.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			bt.setOnMousePressed(event -> {
				if (!judgeWin())
					activateSkill();
			});
			sideBar.getChildren().add(bt);
		}
		hb.getChildren().add(sideBar);
		this.scene = new Scene(hb);
		ConnectFour.getStage().setScene(scene);
	}

	/****************************************************************************
	*** Method Name         : getFirstNoneSpace(int x)
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : x列の要素数を返す
	*** Return              : boardState.get(x).size() x列の要素数
	****************************************************************************/

	// x 列の要素数を返す
	public int getFirstNoneSpace(int x) {
		return boardState.get(x).size();
	}

	/****************************************************************************
	*** Method Name         : countColumnSpace(PlayerAffiliation team)
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : 各列で4マス続けて揃っているかを確認する
	*** Return              : point
	****************************************************************************/

	// 盤面の把握 列の確認
	public int countColumnSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (getSpace(x, y).equals(team)) {
					count++;
				} else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}

	/****************************************************************************
	*** Method Name         : countRowSpace(PlayerAffiliation team)
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : 各行で4マス続けて揃っているかを確認する
	*** Return              : point
	****************************************************************************/

	// 行の確認
	public int countRowSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int y = 0; y < row; y++) {
			count = 0;
			for (int x = 0; x < column; x++) {
				if (getSpace(x, y).equals(team)) {
					count++;
				} else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}

	/****************************************************************************
	*** Method Name         : countRightSlashSpace(PlayerAffiliation team)
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : 右斜めのマスで4マス続けて揃っているかを確認する
	*** Return              : point
	****************************************************************************/

	// 斜めの確認1
	public int countRightSlashSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (getSpace(x + y, y).equals(team)) {
					count++;
				} else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}

	/****************************************************************************
	*** Method Name         : countLeftSlashSpace(PlayerAffiliation team)
	*** Designer            : 塚田 大貴
	*** Date                : 2023.07.22
	*** Function            : 左斜めのマスで4マス続けて揃っているかを確認する
	*** Return              : point
	****************************************************************************/

	// 斜めの確認2
	public int countLeftSlashSpace(PlayerAffiliation team) {
		int count, point = 0;
		for (int x = 0; x < column; x++) {
			count = 0;
			for (int y = 0; y < row; y++) {
				if (getSpace(column - x - y - 1, y).equals(team)) {
					count++;
				} else {
					count = 0;
				}
				if (count >= 4) {
					count = 0;
					point++;
				}
			}
		}
		return point;
	}

	/****************************************************************************
	*** Method Name         : setHost(boolean host)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.22
	*** Function            : ホスト情報を代入
	*** Return              : void
	****************************************************************************/

	public void setHost(boolean host) {
		this.host = host;
	}

	/****************************************************************************
	*** Method Name         : getOnline()
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.22
	*** Function            : 接続状況の取得
	*** Return              : true:  online
							  false: offline
	****************************************************************************/

	public boolean getOnline() {
		return online;
	}

	/****************************************************************************
	*** Method Name         : setObjectInputStream(InputStream is)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.22
	*** Function            : 通信入力用のストリームを代入
	*** Return              : void
	****************************************************************************/

	public void setObjectInputStream(InputStream is) throws IOException {
		this.ois = new ObjectInputStream(is);

	}

	/****************************************************************************
	*** Method Name         : setObjectOutputStream(OutputStream os)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.22
	*** Function            : 通信出力用のストリームを代入
	*** Return              : void
	****************************************************************************/

	public void setObjectOutputStream(OutputStream os) throws IOException {
		this.oos = new ObjectOutputStream(os);
	}

	/****************************************************************************
	*** Method Name         : setOnlineMgr(Thread t)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.22
	*** Function            : 通信用のスレッドを代入
	*** Return              : void
	****************************************************************************/

	public void setOnlineMgr(Thread t) {
		this.onlineMgr = t;
	}

	/****************************************************************************
	*** Method Name         : changeTurn()
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.22
	*** Function            : プレイヤーもしくはコンピュータのターンを変える処理
	*** Return              : void
	****************************************************************************/

	public void changeTurn() {
		switch (turn) {
		case PLAYER1:
			this.turn = PlayerAffiliation.PLAYER2;
			if (!online)
				setComTrout();
			break;
		case PLAYER2:
			this.turn = PlayerAffiliation.PLAYER1;
			break;
		default:
			break;
		}
	}

	/****************************************************************************
	*** Method Name         : judgeWin()
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.22
	*** Function            : 試合が終了したから確認する処理
	*** Return              : true:  finish
							  false: continue
	****************************************************************************/

	public boolean judgeWin() {
		if (countRowSpace(turn) > 0 || countColumnSpace(turn) > 0 || countRightSlashSpace(turn) > 0
				|| countLeftSlashSpace(turn) > 0)
			return true;
		return false;
	}

	/****************************************************************************
	*** Method Name         : setComTrout()
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.22
	*** Function            : コンピュータがマスをセットする処理
	*** Return              : void
	****************************************************************************/

	public void setComTrout() {
		Random rand = new Random();
		int x = rand.nextInt(column);
		while (getFirstNoneSpace(x) == row)
			x = rand.nextInt(column);
		;
		setSpace(PlayerAffiliation.PLAYER2, x);
	}

	// マウスでマスをクリックしたら赤or黄色に染まる処理
	class ClickBoardEventHandler implements EventHandler<MouseEvent> {
		private int x;

		public ClickBoardEventHandler(int x) {
			this.x = x;
		}

		/****************************************************************************
		*** Method Name         : ClickBoardEventHandler()
		*** Designer            : 塚田 大貴
		*** Date                : 2023.07.22
		*** Function            : マウスでマスをクリックしたら赤or黄色に染まる処理
		*** Return              : void
		****************************************************************************/

		@Override
		public void handle(MouseEvent e) {
			if (online) {
				if (e.isPrimaryButtonDown() && getFirstNoneSpace(x) != row
						&& (turn == PlayerAffiliation.PLAYER1) == host) {
					sendCommunicationObject(new CommunicationObject(x));
					if (host) {
						setSpace(PlayerAffiliation.PLAYER1, x);
					} else {

						setSpace(PlayerAffiliation.PLAYER2, x);
					}
				}
			} else if (e.isPrimaryButtonDown() && getFirstNoneSpace(x) != row) {
				setSpace(PlayerAffiliation.PLAYER1, x);
			}
		}
	}

	/****************************************************************************
	*** Method Name         : sendCommunicationObject(CommunicationObject co)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.22
	*** Function            : 通信送信処理
	*** Return              : void
	****************************************************************************/

	public void sendCommunicationObject(CommunicationObject co) {
		try {
			oos.writeObject(co);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class CommunicationThread extends Thread {
		@Override
		/****************************************************************************
		*** Method Name         : run()
		*** Designer            : 玉木 将成
		*** Date                : 2023.07.22
		*** Function            : 通信受信処理
		*** Return              : void
		****************************************************************************/
		public void run() {
			while (online) {
				try {
					CommunicationObject co = (CommunicationObject) ois.readObject();
					switch (co.getTypeObject()) {
					case FirstInfo:
						break;
					case SetSpace:
						Platform.runLater(() -> {
							if (host)
								setSpace(PlayerAffiliation.PLAYER2, co.getX());
							else
								setSpace(PlayerAffiliation.PLAYER1, co.getX());
						});
						break;
					case UseSkill:
						Platform.runLater(() -> {
							activateSkill();
						});
						break;
					case ChatText:
						chatHistory.appendText(co.getText() + "\n");
						break;
					}
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
