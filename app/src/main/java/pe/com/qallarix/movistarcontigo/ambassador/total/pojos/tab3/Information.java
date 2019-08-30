package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab3;
import java.util.List;

class Information {
    private long order;
    private String title;
    private List<Detail> details;

    public Information(long order, String title, List<Detail> details) {
        this.order = order;
        this.title = title;
        this.details = details;
    }
}
