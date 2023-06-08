package ConnectFour.Communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerManager extends Thread {
	
	private ServerSocket socket;
	
	public ServerManager() {
	}

	@Override
	public void run() {
		try {
			this.socket=new ServerSocket(8782);
			InetAddress localhost = InetAddress.getLocalHost();
			DatagramSocket handShakeSocket = new DatagramSocket();
			InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
			byte[] sendData = localhost.getAddress();
			DatagramPacket packet = new DatagramPacket(sendData, sendData.length, broadcastAddress, 1182);
			handShakeSocket.send(packet);
			handShakeSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
