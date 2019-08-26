package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab3;
import java.util.List;

public class Tab3 {
    private String title;
    private Invoicing invoicing;
    private List<Information> informations;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Invoicing getInvoicing() {
        return invoicing;
    }

    public void setInvoicing(Invoicing invoicing) {
        this.invoicing = invoicing;
    }

    public List<Information> getInformations() {
        return informations;
    }

    public void setInformations(List<Information> informations) {
        this.informations = informations;
    }
}
