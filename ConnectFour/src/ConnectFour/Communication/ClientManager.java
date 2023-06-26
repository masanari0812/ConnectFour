package ConnectFour.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import ConnectFour.Screen.DemoScreen.DemoScreen;

public class ClientManager extends Thread {

	private Socket socket;

	public ClientManager() {
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
				System.out.println("OK!!");
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
