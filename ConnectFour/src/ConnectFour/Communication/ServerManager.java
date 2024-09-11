/*******************************************************************
*** File Name            : ServerManager.java
*** Designer             : 玉木 将成
*** Date                 : 2023.07.10
*** Purpose              : サーバー接続処理
***
*******************************************************************/

package ConnectFour.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;
import javafx.application.Platform;

public class ServerManager extends Thread {
	private ServerSocket serverSocket;
	private Socket socket;
	private PlayGameScreen pgs;
	private int num = 0;

	/****************************************************************************
	*** Method Name         : ServerManager(PlayGameScreen pgs)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : PlayGameScreen連携
	****************************************************************************/
	public ServerManager(PlayGameScreen pgs) {
		this.pgs = pgs;
		pgs.setOnlineMgr(this);
	}

	/****************************************************************************
	*** Method Name         : run()
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : LAN内にLocalIPの情報を含めたパケット送信
							  クライアント側からの接続待機処理
							  クライアントへの接続処理
							  通信終了処理
							  ClientManagerインスタンスの生成
	*** Return              : void
	****************************************************************************/
	@Override
	public void run() {
		try {
			System.out.println(num++);
			this.serverSocket = new ServerSocket(8782);
			InetAddress localhost = InetAddress.getLocalHost();
			DatagramSocket handShakeSocket = new DatagramSocket();
			InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
			byte[] sendData = localhost.getAddress();
			DatagramPacket packet = new DatagramPacket(sendData, sendData.length, broadcastAddress, 1182);
			handShakeSocket.send(packet);
			handShakeSocket.close();
			serverSocket.setSoTimeout(2500);
			System.out.println(num++);
			this.socket = serverSocket.accept();
			System.out.println(num++);
			serverSocket.close();
			if (socket.isConnected()) {
				pgs.setHost(true);
				pgs.setObjectOutputStream(socket.getOutputStream());
				Thread.sleep(100);
				pgs.setObjectInputStream(socket.getInputStream());
				System.out.println("!!!");
				Platform.runLater(() -> {
					pgs.makeBoard();
				});
			} else
				System.out.println("x");
			while (pgs.getOnline())
				;
			System.out.println(num++);
			socket.close();
			this.interrupt();
		} catch (SocketTimeoutException e) {
			System.out.println(num + "!");
			if(pgs.getOnline()!=false)
				this.interrupt();
			ClientManager cm = new ClientManager(pgs);
			pgs.setOnlineMgr(cm);
			cm.start();
			this.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}finally  {
			System.out.println("Dis");
			try {
			if(serverSocket!=null)
				serverSocket.close();
			if(socket!=null)
				socket.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
