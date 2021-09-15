//package db;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import omikuji.Omikuji;
//
///**
// * データベース操作用クラス
// */
//public class DBController {
//    /**
//     * 全件表示
//     *
//     * @throws ClassNotFoundException
//     *             ドライバクラスが存在しない場合に送出
//     * @throws SQLException
//     *             データベース操作時にエラーが発生した場合に送出
//     */
//
//    public static void findAll() throws ClassNotFoundException, SQLException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        // DBに接続
//        connection = DBManager.getConnection();
//        //SQL文を準備
//        String sql = "SELECT * FROM omikuji";
//        // ステートメントを作成
//        preparedStatement = connection.prepareStatement(sql);
//        // SQL文を実行
//        resultSet = preparedStatement.executeQuery();
//
//        // DBに接続
//        connection = DBManager.getConnection();
//        //SQL文を準備
//        String sql2 = "INSERT INTO omikuji VALUES (?, ?, ?, ?, ?)";
//        // ステートメントを作成
//        preparedStatement = connection.prepareStatement(sql);
//        //入力値をバインド
//        preparedStatement.setString(1, data[2]);
//        preparedStatement.setString(2, data[1]);
//        preparedStatement.setString(3, data[3]);
//        preparedStatement.setString(4, data[4]);
//        preparedStatement.setString(5, data[5]);
//        // SQL文を実行
//        int cnt2 = preparedStatement.executeUpdate();
//    }
//
//    }else{
//    // DBに接続
//    connection=DBManager.getConnection();
//
//    //SQL文を準備
//    String sql3 = "SELECT *  FROM omikuji WHERE omikuji_id LIKE ? ORDER BY random() LIMIT 1";
//    // ステートメントを作成
//    preparedStatement=connection.prepareStatement(sql3);
//    //入力値をバインド
//    preparedStatement.setString(1,"%"+omikuji.omikujiId+"%");
//    // SQL文を実行
//    resultSet=preparedStatement.executeQuery();
//
//    // DBに接続
//    connection=DBManager.getConnection();
//
//    //SQL文を準備
//    String sql2 = "INSERT INTO result VALUES (?, ?, ?)";
//    // ステートメントを作成
//    preparedStatement=connection.prepareStatement(sql2);
//    //                   //入力値をバインド
//    //                   preparedStatement.setString(1, uranaiDate);
//    //                   preparedStatement.setString(2, birthday);
//    //                   preparedStatement.setString(3, data2[2]);
//    // SQL文を実行
//    int cnt2 = preparedStatement.executeUpdate();
//
//    //                //ランダム表示
//    //                int num = new Random().nextInt(omikujiList.size());
//    //                omikuji = omikujiList.get(num);
//
//    //結果を出力
//    System.out.println(omikuji.disp());
//    //            return;
//}}
//
//try
//
//{
//// DBに接続
//connection=DBManager.getConnection();
//
//// ステートメントを作成
//preparedStatement=connection.prepareStatement(SQL_INSERT="INSERT INTO `omikuji` (`negaigoto`, `akinai`, `gakumon`) VALUES ('"+data[1]+"','"+data[2]+"','"+data[3]+"')";);
//
//// SQL文を実行
//resultSet=preparedStatement.executeQuery();
//
//}finally{
//// ResultSetをクローズ
//DBManager.close(resultSet);
//// Statementをクローズ
//DBManager.close(preparedStatement);
//// DBとの接続を切断
//DBManager.close(connection);}}}
