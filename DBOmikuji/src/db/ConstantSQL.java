package db;

public class ConstantSQL {

    /** SQL文(登録) */
    public static String SQL_INSERT = "INSERT INTO employee VALUES(seq_emp.NEXTVAL, ?, ?, ?, ?)";

}
