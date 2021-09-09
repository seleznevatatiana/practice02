package omikuji;


import java.util.ResourceBundle;

public interface Fortune {

//正解の書き方
//Omikujiクラスの読み込み処理が必要ではない
    ResourceBundle rb = ResourceBundle.getBundle("fortune");
    String DISP_STR = rb.getString("disp_str");
    String disp();

}
