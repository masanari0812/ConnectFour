package ConnectFour.Communication;

import java.io.Serializable;

public class CommunicationObject implements Serializable {
	private TypeObject to;
	private String text;
	private int x;
	private int y;
	private String packet;

	public CommunicationObject(String name, int column, int row) {
		this.to = TypeObject.FirstInfo;
		this.text = name;
		this.x = column;
		this.y = row;
		this.packet = to.toString() + ":" + String.valueOf(x) + ":" + String.valueOf(y);
	}

	public CommunicationObject(int x) {
		this.to = TypeObject.SetSpace;
		this.x = x;
		this.packet = to.toString() + ":" + String.valueOf(x);

	}

	public CommunicationObject(int x, int y) {
		this.to = TypeObject.UseSkill;
		this.x = x;
		this.y = y;
		this.packet = to.toString() + ":" + String.valueOf(x) + ":" + String.valueOf(y);
	}

	public CommunicationObject(String chat, boolean p) {
		this.to = TypeObject.ChatText;
		this.text = chat;
		this.packet = to.toString() + ":" + text;
	}

	public CommunicationObject(String packet) {
		System.out.println(packet);
		String[] str = packet.split(":");
		switch (str[0]) {
		case "FirstInfo":
			to = TypeObject.FirstInfo;
			x = Integer.parseInt(str[1]);
			y = Integer.parseInt(str[2]);
			break;
		case "SetSpace":
			to = TypeObject.SetSpace;
			x = Integer.parseInt(str[1]);
			break;
		case "UseSkill":
			to = TypeObject.UseSkill;
			x = Integer.parseInt(str[1]);
			y = Integer.parseInt(str[2]);
			break;
		case "ChatText":
			to = TypeObject.ChatText;
			text=str[1];
			break;
		default:
			System.out.println("Error communication obj");
		}
	}

	public TypeObject getTypeObject() {
		return to;
	}

	public String getText() {
		return text;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public String getPacket() {
		System.out.println(packet);
		return packet;
	}
	
	public enum TypeObject {
		FirstInfo, SetSpace, UseSkill, ChatText,
	}
}
