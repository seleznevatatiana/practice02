package db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * データベース操作用クラス
 */
public class DBController {

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            //DBに接続
            connection = DBManager.getConnection();
            System.out.println("接続");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //DBとの接続を切断
            DBManager.close(connection);
        }

    }

}