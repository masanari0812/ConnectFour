package ConnectFour.Screen.DemoScreen;

import ConnectFour.ConnectFour;
import ConnectFour.Screen.OriginScreen;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DemoScreen extends OriginScreen {

	private TextField tf;
	public DemoScreen() {
		VBox vb=new VBox();
		vb.setSpacing(20);
		for(int i=1;i<=5;i++) {
			String str=String.valueOf(i)+"番目のボタン";
			Text text=new Text(str);
			text.setOnMouseClicked(new ClickButton(i));
			vb.getChildren().add(text);
		}
		this.tf=new TextField();
		vb.getChildren().add(tf);
		scene = new Scene(vb);
		ConnectFour.getStage().setScene(scene);
	}

	@Override
	public void changeNextScreen() {

	}
	
	class ClickButton implements EventHandler<MouseEvent> {
		private int num;
		
		public ClickButton(int num) {
			this.num = num;
		}

		@Override
		public void handle(MouseEvent e) {
			String str=String.valueOf(num)+"番目のボタンが押された。";
			tf.setText(str);
		}
	}

}
