package ConnectFour.Screen.ResultScreen;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultRecordManageMain {
	String rs;
	
	ResultRecordManageMain(String rs) {
		this.rs = rs;
		ResultRecord(rs);
	}
	
	ResultRecordManageMain() {
		ResultRecordShow();
	}

   public static void ResultRecord (String rs) {
      try {
         //現在時刻を取得し、yyyy/MM/dd HH:mm:ssのフォーマットに設定
         LocalDateTime Now = LocalDateTime.now();
         DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
         String NowTime = dtformat.format(Now);
         
         //result.txtを開く
         FileWriter fw = new FileWriter("result.txt", true);

         //一番下の行に、結果を追加
         fw.write(NowTime + " " + rs + "\n");

         //result.txtを閉じる
         fw.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
	
	public static void ResultRecordShow () {
      try {
            File file = new File("result.txt");
            if(!Desktop.isDesktopSupported()){
               System.out.println("not supported");
               return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists())
               desktop.open(file);
      } catch(Exception e) {
         e.printStackTrace();
      }
	}

}
