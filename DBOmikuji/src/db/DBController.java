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
///**
// * データベース操作用クラス
// */
//public class DBController {
//
//    public static void insert() throws ClassNotFoundException, SQLException {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            // DBに接続
//            connection = DBManager.getConnection();
//
//            // ステートメントを作成
//            preparedStatement = connection.prepareStatement(SQL_INSERT = "INSERT INTO `omikuji` (`negaigoto`, `akinai`, `gakumon`) VALUES ('" + data[1] + "','" + data[2] +  "','" + data[3] + "')";);
//
//            // SQL文を実行
//            resultSet = preparedStatement.executeQuery();
//
//
//        } finally {
//            // ResultSetをクローズ
//            DBManager.close(resultSet);
//            // Statementをクローズ
//            DBManager.close(preparedStatement);
//            // DBとの接続を切断
//            DBManager.close(connection);
//        }
//    }
//}
