package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBController {
    /**
     * 全件表示
     *
     * @throws ClassNotFoundException
     *             ドライバクラスが存在しない場合に送出
     * @throws SQLException
     *             データベース操作時にエラーが発生した場合に送出
     */
    public static void selectFromResult(String birthday, String uranaiDate, String omikujiId)
            throws ClassNotFoundException, SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //Resultテーブルから誕生日と占い日が一致する場合
        // DBに接続
        connection = DBManager.getConnection();
        // ステートメントを作成
        preparedStatement = connection.prepareStatement(ConstantSQL.SQL_SELECT_FROM_RESULT);
        //入力値をバインド
        preparedStatement.setString(1, birthday);
        preparedStatement.setString(2, uranaiDate);
        // SQL文を実行
        ResultSet rs = null;
        rs = preparedStatement.executeQuery();
        if (rs == null) {
        }

        while (rs.next()) {
            omikujiId = rs.getString("omikuji_id");
        }
    }
}



