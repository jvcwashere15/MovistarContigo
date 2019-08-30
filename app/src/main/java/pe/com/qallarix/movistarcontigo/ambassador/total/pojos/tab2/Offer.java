package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab2;
import java.util.List;

public class Offer {
    private String id;
    private long order;
    private boolean recommend;
    private List<GroupFeature> groupFeatures;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public List<GroupFeature> getGroupFeatures() {
        return groupFeatures;
    }

    public void setGroupFeatures(List<GroupFeature> groupFeatures) {
        this.groupFeatures = groupFeatures;
    }
}
