package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab3;
import java.util.List;


class Invoicing {
    private String title;
    private List<BillingElement> billings;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BillingElement> getBillings() {
        return billings;
    }

    public void setBillings(List<BillingElement> billings) {
        this.billings = billings;
    }
}
