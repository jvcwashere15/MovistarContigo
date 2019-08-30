package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab2;

import java.io.Serializable;

class RecommendationRecommendation implements Serializable {
    private long order;
    private String row1;
    private String row2;
    private String row3;

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getRow1() {
        return row1;
    }

    public void setRow1(String row1) {
        this.row1 = row1;
    }

    public String getRow2() {
        return row2;
    }

    public void setRow2(String row2) {
        this.row2 = row2;
    }

    public String getRow3() {
        return row3;
    }

    public void setRow3(String row3) {
        this.row3 = row3;
    }
}
