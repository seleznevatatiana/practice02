package omikuji;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import db.ConstantSQL;
import db.DBManager;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

        System.out.print("誕生日を入力してください：");
        //入力準備
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //入力値を読み込む
        String birthday = reader.readLine();
        boolean check;
        CheckBirthday checkBirthday = new CheckBirthday();
        check = checkBirthday.checkBirthday(birthday);
        if (!check) {
            System.out.println("正しい形式で誕生日を入力してください。");//繰り返しの処理while
            return;
        }

        Date date = new Date(); // 今日の日付
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String uranaiDate = dateFormat.format(date);

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
            System.out.println("rsがnullです");
        }
        String omikujiId = "";

        while (rs.next()) {
        omikujiId = rs.getString("omikuji_id");
        }

        Omikuji omikuji = null;

        if (omikujiId != null) {

            // ステートメントを作成
            preparedStatement = connection.prepareStatement(ConstantSQL.SQL_SELECT_FROM_OMIKUJI);
            //入力値をバインド
            preparedStatement.setString(1, omikujiId);
            // SQL文を実行
            ResultSet rs2 = null;
            rs2 = preparedStatement.executeQuery();

            //resultsetから値の取り出し方
            while (rs2.next()) {
                omikuji = getInstance(rs2.getString("unsei_name"));
                omikuji.setUnsei();
                omikuji.setOmikujiId(omikujiId);
                omikuji.setNegaigoto(rs2.getString("negaigoto"));
                omikuji.setAkinai(rs2.getString("akinai"));
                omikuji.setGakumon(rs2.getString("gakumon"));
            }
        }

        //ファイル読み込みで使用する３つのクラス
        FileInputStream fi = null;
        InputStreamReader is = null;
        BufferedReader br = null;

        try {

            // DBに接続
            connection = DBManager.getConnection();
            // ステートメントを作成
            preparedStatement = connection.prepareStatement(ConstantSQL.SQL_SELECT_COUNT_FROM_OMIKUJI);
            // SQL文を実行
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("cnt");

            if (count == 0) {

                //読み込みファイルのインスタンス生成
                //ファイル名を指定する
                fi = new FileInputStream("src/omikuji/fortune.csv");
                is = new InputStreamReader(fi);
                br = new BufferedReader(is);

                // readLineで一行ずつ読み込む
                String line; // 読み　込み行
                String[] data; // 分割後のデータを保持する配列

                while ((line = br.readLine()) != null) {
                    // lineをカンマで分割し、配列dataに設定
                    data = line.split(",");

//                    omikuji = getInstance(data[0]);
//
//                    // 要素の追加
//                    omikuji.setUnsei();
//                    omikuji.setUnseiId(data[1]);
//                    omikuji.setOmikujiId(data[2]);
//                    omikuji.setNegaigoto(data[3]);
//                    omikuji.setAkinai(data[4]);
//                    omikuji.setGakumon(data[5]);
//
//                    // Timestampオブジェクト生成
//                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                    // DBに接続
                    connection = DBManager.getConnection();
                    // ステートメントを作成
                    preparedStatement = connection.prepareStatement(ConstantSQL.SQL_INSERT_OMIKUJI);
                    //入力値をバインド
                    preparedStatement.setString(1, data[2]);
                    preparedStatement.setString(2, data[1]);
                    preparedStatement.setString(3, data[3]);
                    preparedStatement.setString(4, data[4]);
                    preparedStatement.setString(5, data[5]);
                    preparedStatement.setString(6, "タチアナ");
                    preparedStatement.setString(7, "タチアナ");

                    // SQL文を実行
                    preparedStatement.executeUpdate();
                }
                // ステートメントを作成
                preparedStatement = connection.prepareStatement(ConstantSQL.SQL_SELECT_COUNT_FROM_OMIKUJI);
                // SQL文を実行
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                count = resultSet.getInt("cnt");
            }

            //データがなかった場合
            if (omikujiId.isEmpty()) {

                //ランダム表示
                int num = new Random().nextInt(count + 1);
                String str = Integer.toString(num);

                // DBに接続
                connection = DBManager.getConnection();
                // ステートメントを作成
                preparedStatement = connection.prepareStatement(ConstantSQL.SQL_SELECT_OMIKUJI);
                //入力値をバインド
                preparedStatement.setString(1, str);
                // SQL文を実行
                ResultSet resultSet2 = null;
                resultSet2 = preparedStatement.executeQuery();

                //resultsetから値の取り出し方
                while (resultSet2.next()) {
                    omikuji = getInstance(resultSet2.getString("unsei_name"));
                    omikuji.setUnsei();
                    omikuji.setOmikujiId(str);
                    omikuji.setNegaigoto(resultSet2.getString("negaigoto"));//setterを利用して書き換える
                    omikuji.akinai = resultSet2.getString("akinai");
                    omikuji.gakumon = resultSet2.getString("gakumon");
                }

                // DBに接続
                connection = DBManager.getConnection();
                // ステートメントを作成
                preparedStatement = connection.prepareStatement(ConstantSQL.SQL_INSERT_RESULT);
                //入力値をバインド
                //getを使う
                preparedStatement.setString(1, uranaiDate);
                preparedStatement.setString(2, birthday);
                preparedStatement.setString(3, omikuji.omikujiId);
                preparedStatement.setString(4, "タチアナ");
                preparedStatement.setString(5, "タチアナ");

                // SQL文を実行
                int cnt4 = preparedStatement.executeUpdate();
        }

                //結果を出力
                System.out.println(omikuji.disp());
                //            return;

        }

        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // ResultSetをクローズ
            DBManager.close(resultSet);
            // Statementをクローズ
            DBManager.close(preparedStatement);
            // DBとの接続を切断
            DBManager.close(connection);
        }
    }

    //Omikujiクラスをnewするためのメソッド
    public static Omikuji getInstance(String unseimei) {
        Omikuji omikuji = null;
        switch (unseimei) {
        //大吉の場合
        case "大吉":
            omikuji = new Daikichi();
            break;

        //中吉の場合
        case "中吉":
            omikuji = new Chukichi();
            break;

        //小吉の場合
        case "小吉":
            omikuji = new Shokichi();
            break;

        //吉の場合
        case "吉":
            omikuji = new Kichi();
            break;

        //末吉の場合
        case "末吉":
            omikuji = new Suekichi();
            break;

        //凶の場合
        case "凶":
            omikuji = new Kyo();
            break;
        }
        return omikuji;

    }
}
