package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab1;

import java.io.Serializable;
import java.util.List;

public class Block implements Serializable {
    private String title;
    private String description;
    private String image;
    private List<ItemList> list;

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getImage() { return image; }
    public void setImage(String value) { this.image = value; }

    public List<ItemList> getList() {
        return list;
    }

    public void setList(List<ItemList> list) {
        this.list = list;
    }
}
