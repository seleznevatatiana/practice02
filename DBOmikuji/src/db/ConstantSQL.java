package db;

/**
 * SQL文の管理用クラス
 */

public class ConstantSQL {

    public static String SQL_SELECT_FROM_RESULT = "SELECT omikuji_id FROM result WHERE birthday = ? AND uranai_date =?";
    public static String SQL_SELECT_COUNT_FROM_OMIKUJI = "SELECT COUNT (*) AS CNT FROM omikuji";
    public static String SQL_INSERT_OMIKUJI = "INSERT INTO omikuji  (omikuji_id, unsei_id, negaigoto, akinai, gakumon, updater, updated_date, creator, created_date) VALUES (?, ?, ?, ?, ?, ?, current_timestamp, ?, current_timestamp) ";
    public static String SQL_SELECT_OMIKUJI = "SELECT u.unsei_name, o.negaigoto, o.akinai, o.gakumon, o.updater, o.updated_date, o.creator, o.created_date  FROM omikuji o INNER JOIN unseimaster u ON o.unsei_id = u.unsei_id WHERE o.omikuji_id = ?";
    public static String SQL_INSERT_RESULT = "INSERT INTO result VALUES (?, ?, ?, ?, current_timestamp, ?, current_timestamp)";



}
