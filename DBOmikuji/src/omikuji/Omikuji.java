package omikuji;

public abstract class Omikuji implements Fortune{

    protected String negaigoto;
    protected String akinai;
    protected String gakumon;
    protected String id;
    public abstract void setUnsei();

    protected String unsei;
    /**
     * @param unsei セットする unsei
     */
    public void setUnsei(String unsei) {
        this.unsei = unsei;
    }
    /**
     * @param negaigoto セットする negaigoto
     */
    public void setNegaigoto(String negaigoto) {
        this.negaigoto = negaigoto;
    }
    /**
     * @param akinai セットする akinai
     */
    public void setAkinai(String akinai) {
        this.akinai = akinai;
    }
    /**
     * @param gakumon セットする gakumon
     */
    public void setGakumon(String gakumon) {
        this.gakumon = gakumon;
    }
    /**
     * @param omikiji_id セットする negaigoto
     */
    public void setId (String id) {
        this.id = id;
    }

    public String disp() {
        String disp;
        disp = String.format(DISP_STR, this.unsei);


//        //properties読み込み
//        Properties properties = new Properties();
//        String file1 = "src/omikuji02/fortune.properties";
//        String str = "";
//
//        try {
//            FileInputStream fis = new FileInputStream(file1);
//            try {
//                properties.load(fis);
//                String a = properties.getProperty("disp_str");
//                str = String.format(a, this.unsei);

//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        //StringBuilderでコンソール表示する文字列を作る
          StringBuilder sb = new StringBuilder();
          sb.append(disp);
          sb.append(System.getProperty("line.separator"));
          sb.append("願い事:"  + negaigoto);
          sb.append(System.getProperty("line.separator"));
          sb.append("商い:"  + akinai);
          sb.append(System.getProperty("line.separator"));
          sb.append("学問:"  + gakumon);

        return sb.toString();
        }

    }

