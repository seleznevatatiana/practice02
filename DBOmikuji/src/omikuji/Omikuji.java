package omikuji;

public abstract class Omikuji implements Fortune{

    protected String negaigoto;
    protected String akinai;
    protected String gakumon;
    protected String unseiId;
    protected String omikujiId;
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
     * @param unsei_id セットする negaigoto
     */
    public void setUnseiId (String unseiId) {
        this.unseiId = unseiId;
    }
    /**
     * @param omikuji_id セットする negaigoto
     */
    public void setOmikujiId (String omikujiId) {
        this.omikujiId = omikujiId;
    }

    public String disp() {
        String disp;
        disp = String.format(DISP_STR, this.unsei);

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

