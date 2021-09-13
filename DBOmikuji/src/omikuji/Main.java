package omikuji;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import db.DBManager;

public class Main {

    public static void main(String[] args) throws IOException {

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

        //ファイル読み込みで使用する３つのクラス
        FileInputStream fi2 = null;
        InputStreamReader is2 = null;
        BufferedReader br2 = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            //読み込みファイルのインスタンス生成
            //ファイル名を指定する
            fi2 = new FileInputStream("src/omikuji/fortuneWithBirthday.csv");
            is2 = new InputStreamReader(fi2);
            br2 = new BufferedReader(is2);

            // readLineで一行ずつ読み込む
            String line2; // 読み　込み行
            String[] data2; // 分割後のデータを保持する配列
            Omikuji omikuji = null;
            while ((line2 = br2.readLine()) != null) {
                // lineをカンマで分割し、配列dataに設定
                data2 = line2.split(",");

                // DBに接続
                connection = DBManager.getConnection();

                // ステートメントを作成
                preparedStatement = connection.prepareStatement(SQL_INSERT = "INSERT INTO `omikuji` (`negaigoto`, `akinai`, `gakumon`) VALUES ('" + data[1] + "','" + data[2] +  "','" + data[3] + "')";);

                // SQL文を実行
                resultSet = preparedStatement.executeQuery();

                if (!data2[5].equals(birthday) && !data2[6].equals(uranaiDate))
                    continue;
                omikuji = getInstance(data2[1]);
                //分割した文字を画面出力する
                for (int i = 0; i < data2.length; i++) {
                    omikuji.setUnsei();
                    omikuji.setNegaigoto(data2[2]);
                    omikuji.setAkinai(data2[3]);
                    omikuji.setGakumon(data2[4]);
                }
            }
            //誕生日か当日が同じの既存データがない場合
            if (omikuji == null) {

                //読み込みファイルのインスタンス生成
                //ファイル名を指定する
                fi = new FileInputStream("src/omikuji/fortune.csv");
                is = new InputStreamReader(fi);
                br = new BufferedReader(is);

                // readLineで一行ずつ読み込む
                String line; // 読み　込み行
                String[] data; // 分割後のデータを保持する配列

                //リスト作成
                List<Omikuji> omikujiList = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    // lineをカンマで分割し、配列dataに設定
                    data = line.split(",");
//                    String insertSql = "INSERT INTO `omikuji` (`negaigoto`, `akinai`, `gakumon`) VALUES ('" + data[1] + "','" + data[2] +  "','" + data[3] + "')";

                    omikuji = getInstance(data[1]);

                    // 要素の追加
                    omikuji.setUnsei();
                    omikuji.setId(data[0]);
                    omikuji.setNegaigoto(data[2]);
                    omikuji.setAkinai(data[3]);
                    omikuji.setGakumon(data[4]);

                    omikujiList.add(omikuji);
                }

                //ランダム表示
                int num = new Random().nextInt(omikujiList.size());
                omikuji = omikujiList.get(num);

                FileWriter fw = null;

                File file = new File("src/omikuji/fortuneWithBirthday.csv");
                fw = new FileWriter(file, true);

                StringBuilder sb = new StringBuilder();
                sb.append(omikuji.id);
                sb.append(',');
                sb.append(omikuji.unsei);
                sb.append(',');
                sb.append(omikuji.negaigoto);
                sb.append(',');
                sb.append(omikuji.akinai);
                sb.append(',');
                sb.append(omikuji.gakumon);
                sb.append(',');
                sb.append(birthday);
                sb.append(',');
                sb.append(uranaiDate);
                sb.append('\n');

                fw.write(sb.toString());
                fw.flush();

                if (fw != null) {
                    fw.close();
                }

            }
            //結果を出力
            System.out.println(omikuji.disp());
            //            return;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
                // ResultSetをクローズ
                DBManager.close(resultSet);
                // Statementをクローズ
                DBManager.close(preparedStatement);
                // DBとの接続を切断
                DBManager.close(connection);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
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