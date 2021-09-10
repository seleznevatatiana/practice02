package db;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * データベース操作用クラス
 */
public class DBController {
    
    //csvファイル読み込み
    try {
        FileInputStream fi = null;
        InputStreamReader is = null;
        BufferedReader br = null;
        
        File f = new File("C:\\kadai\\kadai7\\テストデータ.csv");
        BufferedReader br = new BufferedReader(new FileReader(f));

        String line;
        // 1行ずつCSVファイルを読み込む
        while ((line = br.readLine()) != null) {
          String[] data = line.split(",", 0); // 行をカンマ区切りで配列に変換
          //AGENT_IXN_LOGテーブルにデータをINSERTする
          stmt.executeUpdate("INSERT INTO AGENT_IXN_LOG(USER_NAME,AGENT_FIRST_NAME,AGENT_LAST_NAME,EMPLOYEE_ID,START_DATE,END_DATE) VALUES ('" + data[0] + "','" + data[1] +  "','" + data[2] + "','" + data[3] + "','" + data[4] + "','" + data[5] +"')");
        }
        br.close();

      } catch (IOException e) {
        System.out.println(e);
      }
        // ステートメントをクローズ
        stmt.close();
        // 接続をクローズ
        conn.close();
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}

//    public static void main(String[] args) throws SQLException {
//        Connection connection = null;
//        try {
//            //DBに接続
//            connection = DBManager.getConnection();
////            System.out.println("接続");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            //DBとの接続を切断
//            DBManager.close(connection);
//        }
//
//    }
}
}