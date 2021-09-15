package omikuji;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

        LocalDate uranaiDate = LocalDate.now();

        //ファイル読み込みで使用する３つのクラス
        FileInputStream fi = null;
        InputStreamReader is = null;
        BufferedReader br = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            Omikuji omikuji = null;

            //            // readLineで一行ずつ読み込む
            //            String line2; // 読み　込み行
            //            String[] data2; // 分割後のデータを保持する配列
            //            Omikuji omikuji = null;
            //            while ((line2 = br2.readLine()) != null) {
            //                // lineをカンマで分割し、配列dataに設定
            //                data2 = line2.split(",");
            //
            //                if (!data2[6].equals(birthday) && !data2[7].equals(uranaiDate))
            //                    continue;
            //                omikuji = getInstance(data2[0]);
            //                //分割した文字を画面出力する
            //                for (int i = 0; i < data2.length; i++) {
            //                    omikuji.setUnsei();
            //                    omikuji.setUnseiId(data2[1]);
            //                    omikuji.setOmikujiId(data2[2]);
            //                    omikuji.setNegaigoto(data2[3]);
            //                    omikuji.setAkinai(data2[4]);
            //                    omikuji.setGakumon(data2[5]);
            //                }
            //            }

            // DBに接続
            connection = DBManager.getConnection();
            //SQL文を準備
            String sql = "SELECT * FROM omikuji";
            // ステートメントを作成
            preparedStatement = connection.prepareStatement(sql);
            // SQL文を実行
            resultSet = preparedStatement.executeQuery();

            if (resultSet == null) {

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
                    String sql2 = "INSERT INTO omikuji VALUES (?, ?, ?, ?, ?)";
                    // ステートメントを作成
                    preparedStatement = connection.prepareStatement(sql);
                    //入力値をバインド
                    preparedStatement.setString(1, data[2]);
                    preparedStatement.setString(2, data[1]);
                    preparedStatement.setString(3, data[3]);
                    preparedStatement.setString(4, data[4]);
                    preparedStatement.setString(5, data[5]);
                    // SQL文を実行
                    int cnt2 = preparedStatement.executeUpdate();
                }

               } else {
                   String omikujiId;
                 // DBに接続
                    connection = DBManager.getConnection();
                    //SQL文を準備
                    String sql3 = "SELECT *  FROM omikuji WHERE omikuji_id LIKE ? ORDER BY random() LIMIT 1";
                    // ステートメントを作成
                    preparedStatement = connection.prepareStatement(sql3);
                    //入力値をバインド
                    preparedStatement.setString(1, "%" + omikuji.omikujiId + "%" );
                 // SQL文を実行
                   resultSet = preparedStatement.executeQuery();

                // DBに接続
                connection = DBManager.getConnection();

                //SQL文を準備
                String sql2 = "INSERT INTO result VALUES (?, ?, ?)";
                // ステートメントを作成
                preparedStatement = connection.prepareStatement(sql2);
                //                   //入力値をバインド
                //                   preparedStatement.setString(1, uranaiDate);
                //                   preparedStatement.setString(2, birthday);
                //                   preparedStatement.setString(3, data2[2]);
                // SQL文を実行
                int cnt2 = preparedStatement.executeUpdate();

                //                //ランダム表示
                //                int num = new Random().nextInt(omikujiList.size());
                //                omikuji = omikujiList.get(num);

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
