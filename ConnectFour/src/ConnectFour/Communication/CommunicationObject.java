/*******************************************************************
*** File Name            : CommunicationObject.java
*** Designer             : 玉木 将成
*** Date                 : 2023.07.10
*** Purpose              : 通信IO用オブジェクトクラス
***
*******************************************************************/

package ConnectFour.Communication;

import java.io.Serializable;

public class CommunicationObject implements Serializable {
	private TypeObject to;
	private String text;
	private int x;
	private int y;
	
	/****************************************************************************
	*** Method Name         : CommunicationObject(String name, int column, int row)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : 通信オブジェクト生成
	****************************************************************************/
	public CommunicationObject(String name, int column, int row) {
		this.to = TypeObject.FirstInfo;
		this.text = name;
		this.x = column;
		this.y = row;
	}
	
	/****************************************************************************
	*** Method Name         : CommunicationObject(int x)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : 通信オブジェクト生成
	****************************************************************************/
	public CommunicationObject(int x) {
		this.to = TypeObject.SetSpace;
		this.x = x;

	}
	
	/****************************************************************************
	*** Method Name         : CommunicationObject(int x, int y)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : 通信オブジェクト生成
	****************************************************************************/
	public CommunicationObject(int x, int y) {
		this.to = TypeObject.UseSkill;
		this.x = x;
		this.y = y;
	}
	
	/****************************************************************************
	*** Method Name         : CommunicationObject(String chat)
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : 通信オブジェクト生成
	****************************************************************************/
	public CommunicationObject(String chat) {
		this.to = TypeObject.ChatText;
		this.text = chat;
	}

	/****************************************************************************
	*** Method Name         : getTypeObject()
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : 通信オブジェクト取得
	*** Return              : TypeObject
	****************************************************************************/
	public TypeObject getTypeObject() {
		return to;
	}
	/****************************************************************************
	*** Method Name         : getText()
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : 通信オブジェクト文字列取得
	*** Return              : String
	****************************************************************************/
	public String getText() {
		return text;
	}

	/****************************************************************************
	*** Method Name         : getX()
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : 通信オブジェクトX値取得
	*** Return              : int
	****************************************************************************/
	public int getX() {
		return x;
	}
	
	/****************************************************************************
	*** Method Name         : getY()
	*** Designer            : 玉木 将成
	*** Date                : 2023.07.10
	*** Function            : 通信オブジェクトY値取得
	*** Return              : int
	****************************************************************************/
	public int getY() {
		return y;
	}
	
	public enum TypeObject {
		FirstInfo, SetSpace, UseSkill, ChatText,
	}
}
