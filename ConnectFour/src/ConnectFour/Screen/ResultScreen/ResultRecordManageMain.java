/*******************************************************************
*** File Name             : ResultRecordManageMain.java
*** Designer              : 千葉 飛馬
*** Date                  : 2023.07.18
*** Purpose               : 対戦結果の記録や表示をする。
***
*******************************************************************/

package ConnectFour.Screen.ResultScreen;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultRecordManageMain {
	String result;
	ResultRecordManageMain(String result) {
		this.result = result;
		resultRecord(result);
	}
	
	ResultRecordManageMain() {
		resultRecordShow();
	}
	
	/****************************************************************************
	*** Method Name         : resultRecord(String result)
	*** Designer            : 千葉 飛馬
	*** Date                : 2023.07.11
	*** Function            : 対戦結果をresult.txtに記入する。
	*** Return              : result.txtに結果記録
	****************************************************************************/
   public static void resultRecord (String result) {
      try {
         //現在時刻を取得し、yyyy/MM/dd HH:mm:ssのフォーマットに設定
         LocalDateTime now = LocalDateTime.now();
         DateTimeFormatter nowTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
         String nowTime = nowTimeFormat.format(now);
         
         //result.txtを開く
         FileWriter resultWriter = new FileWriter("result.txt", true);

         //一番下の行に、結果を追加
         resultWriter.write(nowTime + " " + result + "\n");

         //result.txtを閉じる
         resultWriter.close();
      } catch (IOException error) { //例外処理
         error.printStackTrace();
      }
   }
   
   /****************************************************************************
   *** Method Name         : resultRecordShow()
   *** Designer            : 千葉 飛馬
   *** Date                : 2023.07.18
   *** Function            : 対戦結果が記録されたresult.txtを開く。
   *** Return              : result.txtの表示
   ****************************************************************************/
	public static void resultRecordShow () {
      try {
            File resultText = new File("result.txt");
            
            //サポートされていない場合の処理
            if(!Desktop.isDesktopSupported()){
               System.out.println("not supported");
               return;
            }
            
            Desktop desktop = Desktop.getDesktop();
            
            if(resultText.exists()) {
               desktop.open(resultText);
            }
      } catch(Exception error) { //例外処理
         error.printStackTrace();
      }
	}

}
