package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab2;
import java.io.Serializable;
import java.util.List;

class Recommendation implements Serializable {
    private String title;
    private List<RecommendationRecommendation> recommendations;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RecommendationRecommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationRecommendation> recommendations) {
        this.recommendations = recommendations;
    }
}
