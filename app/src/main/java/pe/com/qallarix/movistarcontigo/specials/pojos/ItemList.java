package pe.com.qallarix.movistarcontigo.specials.pojos;

import java.io.Serializable;
import java.util.List;

public class ItemList implements Serializable {
    private String title;
    private String description;
    private List<String> benefits;

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public List<String> getBenefits() { return benefits; }
    public void setBenefits(List<String> value) { this.benefits = value; }
}
