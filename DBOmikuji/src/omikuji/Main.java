package omikuji;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.DBManager;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {

        System.out.print("誕生日を入力してください：");
        //入力準備
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //入力値を読み込む
        String birthday = reader.readLine();
        //      // 変換対象の日付文字列
        //      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //      // Date型変換
        //      Date birthday = sdf.parse(birthday1);
        boolean check;
        CheckBirthday checkBirthday = new CheckBirthday();
        check = checkBirthday.checkBirthday(birthday);
        if (!check) {
            System.out.println("正しい形式で誕生日を入力してください。");
            return;
        }

        Date date = new Date(); // 今日の日付
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String uranaiDate = dateFormat.format(date);

     // Timestampオブジェクト生成
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());


        //omikuji_idで情報取得
        //サブクエリ
        // birthdayとuranai_dateでsql条件を書く
        //SELECT u.unsei_name, o.negaigoto, o.akinai, o.gakumon  FROM omikuji o INNER JOIN unseimaster u ON o.unsei_id = u.unsei_id WHERE o.omikuji_id = (SELECT r.omikuji_id FROM result r INNER JOIN omikuji o ON r.omikuji_id = o.omikuji_id);

        //ファイル読み込みで使用する３つのクラス
        FileInputStream fi = null;
        InputStreamReader is = null;
        BufferedReader br = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            Omikuji omikuji = null;

            // DBに接続
            connection = DBManager.getConnection();
            //SQL文を準備
            String sql = "SELECT COUNT (*) AS CNT FROM omikuji";
            // ステートメントを作成
            preparedStatement = connection.prepareStatement(sql);
            // SQL文を実行
            resultSet = preparedStatement.executeQuery();
            System.out.println("resultsetの中身：" + resultSet);
            resultSet.next();
            System.out.println(resultSet.getInt("cnt"));

            if (resultSet.getInt("cnt") == 0) {

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

                    omikuji = getInstance(data[0]);

                    // 要素の追加
                    omikuji.setUnsei();
                    omikuji.setUnseiId(data[1]);
                    omikuji.setOmikujiId(data[2]);
                    omikuji.setNegaigoto(data[3]);
                    omikuji.setAkinai(data[4]);
                    omikuji.setGakumon(data[5]);

                    // DBに接続
                    connection = DBManager.getConnection();
                    //SQL文を準備
                    String sql2 = "INSERT INTO omikuji VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    // ステートメントを作成
                    preparedStatement = connection.prepareStatement(sql);
                    //入力値をバインド
                    preparedStatement.setString(1, data[2]);
                    preparedStatement.setString(2, data[1]);
                    preparedStatement.setString(3, data[3]);
                    preparedStatement.setString(4, data[4]);
                    preparedStatement.setString(5, data[5]);
                    preparedStatement.setString(6, "タチアナ");
                    preparedStatement.setTimestamp(7, timestamp);
                    preparedStatement.setString(8, "タチアナ");
                    preparedStatement.setTimestamp(9, timestamp);

                    // SQL文を実行
                    int cnt2 = preparedStatement.executeUpdate();
                }

//               } else {
//                 //ランダム表示
//                   int num = new Random().nextInt(resultSet.getInt("cnt"));
//                   Integer i = Integer.valueOf(num);
//                   String str = i.toString();
//
//                 // DBに接続
//                    connection = DBManager.getConnection();
//                    //SQL文を準備
//                    String sql3 = "SELECT u.unsei_name, o.negaigoto, o.akinai, o.gakumon  FROM omikuji o INNER JOIN unseimaster u ON o.unsei_id = u.unsei_id WHERE o.omikuji_id = ?";
//                    // ステートメントを作成
//                    preparedStatement = connection.prepareStatement(sql3);
//                    //入力値をバインド
//                    preparedStatement.setString(1,  str);
//                 // SQL文を実行
//                    ResultSet resultSet2 = null;
//                   resultSet2 = preparedStatement.executeQuery();
//
//                   //resultsetから値の取り出し方
//                   if(resultSet2.next()){
//                       omikuji = getInstance(resultSet2.getString("unsei_name"));
//                       omikuji.setUnsei();
//                       omikuji.omikujiId = resultSet2.getString("omikuji_id");
//                       omikuji.negaigoto = resultSet2.getString("negaigoto");
//                       omikuji.akinai = resultSet2.getString("akinai");
//                       omikuji.gakumon = resultSet2.getString("gakumon");
//                       omikuji.updater = resultSet2.getString("updater");
//                       omikuji.updatedDate = resultSet2.getTimestamp("updated_date");
//                       omikuji.creator = resultSet2.getString("creator");
//                       omikuji.createdDate = resultSet2.getTimestamp("created_date");
//                       }
//
//
//                // DBに接続
//                connection = DBManager.getConnection();
//
//                //SQL文を準備
//                String sql4 = "INSERT INTO result VALUES (?, ?, ?, ?, ?)";
//                // ステートメントを作成
//                preparedStatement = connection.prepareStatement(sql4);
//                                   //入力値をバインド
//                                   preparedStatement.setString(1, uranaiDate);
//                                   preparedStatement.setString(2, birthday);
//                                   preparedStatement.setString(3, omikuji.omikujiId);
//                                   preparedStatement.setString(4, omikuji.updater);
//                                   preparedStatement.setTimestamp(5, omikuji.updatedDate);
//                                   preparedStatement.setString(6, omikuji.creator);
//                                   preparedStatement.setTimestamp(7, omikuji.createdDate);
//                                   //タイムスタンプ→更新日と作成日(型を変える）
//                                   //更新者と作成者は私の名前
//                // SQL文を実行
//                int cnt4 = preparedStatement.executeUpdate();

                //結果を出力
                System.out.println(omikuji.disp());
                //            return;
                }
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
