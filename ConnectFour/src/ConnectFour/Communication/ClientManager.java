package ConnectFour.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;
import javafx.application.Platform;

public class ClientManager extends Thread {
	private DatagramSocket handShakeSocket;
	private Socket socket;
	private PlayGameScreen pgs;
	private int num = 0;

	public ClientManager(PlayGameScreen pgs) {
		this.pgs = pgs;
		pgs.setOnlineMgr(this);
	}

	@Override
	public void run() {
		try {
			System.out.println(num++);
			this.handShakeSocket = new DatagramSocket(1182);
			byte[] receiveData = new byte[InetAddress.getLocalHost().getAddress().length];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			handShakeSocket.receive(receivePacket);
			System.out.println(num++);
			byte[] receivedData = receivePacket.getData();
			InetAddress localhost = InetAddress.getByAddress(receivedData);
			handShakeSocket.close();
			System.out.println(num++);
			System.out.println(localhost.getHostAddress());
			this.socket = new Socket(localhost, 8782);
			if (socket.isConnected()) {
				System.out.println(num++);
				pgs.setHost(false);
				pgs.setObjectOutputStream(socket.getOutputStream());
				Thread.sleep(100);
				pgs.setObjectInputStream(socket.getInputStream());
				System.out.println("!!!");
				Platform.runLater(() -> {
					pgs.makeBoard();

				});
			} else
				System.out.println("x");
			System.out.println(num++);
			while (pgs.getOnline())
				;
			socket.close();
			this.interrupt();
		} catch (IOException | InterruptedException e) {
			System.out.println(num + "!");
			e.printStackTrace();
		} finally {
			System.out.println("Dis");
			try {
				if(handShakeSocket!=null)
					handShakeSocket.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
