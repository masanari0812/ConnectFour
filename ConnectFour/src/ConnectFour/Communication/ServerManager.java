package ConnectFour.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import ConnectFour.Screen.PlayGameScreen.PlayGameScreen;

public class ServerManager extends Thread {

	private Socket socket;
	private PlayGameScreen pgs;

	public ServerManager(PlayGameScreen pgs) {
		this.pgs = pgs;
	}

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(8782);
			InetAddress localhost = InetAddress.getLocalHost();
			DatagramSocket handShakeSocket = new DatagramSocket();
			InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
			byte[] sendData = localhost.getAddress();
			DatagramPacket packet = new DatagramPacket(sendData, sendData.length, broadcastAddress, 1182);
			handShakeSocket.send(packet);
			handShakeSocket.close();
			serverSocket.setSoTimeout(3000);
			this.socket = serverSocket.accept();
			serverSocket.close();
			if (socket.isConnected()) {
				pgs.setHost(true);
				pgs.setInputStream(socket.getInputStream());
				pgs.setOutputStream(socket.getOutputStream());
			}
			while (pgs.getOnline())
				;
			socket.close();
			this.interrupt();
		} catch (SocketTimeoutException e) {
			ClientManager cm=new ClientManager(pgs);
			cm.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
