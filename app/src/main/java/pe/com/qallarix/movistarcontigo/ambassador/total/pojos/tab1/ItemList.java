package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab1;

import java.io.Serializable;

public class ItemList implements Serializable {
    private long order;
    private String description;
    private String image;

    public long getOrder() { return order; }
    public void setOrder(long value) { this.order = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getImage() { return image; }
    public void setImage(String value) { this.image = value; }
}
