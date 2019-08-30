package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab2;
import java.util.List;

public class GroupFeature {
    private long id;
    private long order;
    private String name;
    private String title;
    private String image;
    private List<FeatureList> featureList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<FeatureList> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<FeatureList> featureList) {
        this.featureList = featureList;
    }
}
