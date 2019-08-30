package pe.com.qallarix.movistarcontigo.ambassador.total.pojos.tab3;
import java.util.List;

class BillingElement {
    private long order;
    private String title;
    private String subtitleOne;
    private String subtitleTwo;
    private String descriptionOne;
    private String descriptionTwo;
    private List<Date> dates;

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitleOne() {
        return subtitleOne;
    }

    public void setSubtitleOne(String subtitleOne) {
        this.subtitleOne = subtitleOne;
    }

    public String getSubtitleTwo() {
        return subtitleTwo;
    }

    public void setSubtitleTwo(String subtitleTwo) {
        this.subtitleTwo = subtitleTwo;
    }

    public String getDescriptionOne() {
        return descriptionOne;
    }

    public void setDescriptionOne(String descriptionOne) {
        this.descriptionOne = descriptionOne;
    }

    public String getDescriptionTwo() {
        return descriptionTwo;
    }

    public void setDescriptionTwo(String descriptionTwo) {
        this.descriptionTwo = descriptionTwo;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }
}
