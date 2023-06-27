package ConnectFour.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import ConnectFour.Screen.DemoScreen.DemoScreen;
import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;

public class ClientManager extends Thread {

	private Socket socket;
	private PlayGameScreen pgs;

	public ClientManager(PlayGameScreen pgs) {
		this.pgs = pgs;
	}

	@Override
	public void run() {
		try {
			DatagramSocket handShakeSocket = new DatagramSocket(1182);
			byte[] receiveData = new byte[16];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			handShakeSocket.receive(receivePacket);
			byte[] receivedData = receivePacket.getData();
			InetAddress localhost = InetAddress.getByAddress(receivedData);
			handShakeSocket.close();
			DemoScreen.changeString(localhost.getHostAddress());
			this.socket = new Socket(localhost, 8782);
			if (socket.isConnected()) {
				pgs.setHost(false);
				pgs.setObjectInputStream(socket.getInputStream());
				pgs.setObjectOutputStream(socket.getOutputStream());
				pgs.makeBoard();
			}
			while (pgs.getOnline())
				;
			socket.close();
			this.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
