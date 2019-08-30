package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab2;
import java.io.Serializable;
import java.util.List;


public class Tab2 implements Serializable {
    private String title;
    private String legal;
    private String description;
    private Billing billing;
    private List<Recommendation> recommendation;
    private List<Offer> offers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public List<Recommendation> getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(List<Recommendation> recommendation) {
        this.recommendation = recommendation;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
