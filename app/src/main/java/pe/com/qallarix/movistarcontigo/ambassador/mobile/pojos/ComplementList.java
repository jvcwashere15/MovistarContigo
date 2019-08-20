package pe.com.qallarix.movistarcontigo.ambassador.mobile.pojos;

public class ComplementList {
    private String complementName;
    private String complementImageUrl;
    private String complementPriceValue;
    private String complementPriceDescription;
    private String complementAdditionalDetail;

    public String getComplementName() { return complementName; }
    public void setComplementName(String value) { this.complementName = value; }

    public String getComplementImageUrl() {
        return complementImageUrl;
    }

    public void setComplementImageUrl(String complementImageUrl) {
        this.complementImageUrl = complementImageUrl;
    }

    public String getComplementPriceValue() { return complementPriceValue; }
    public void setComplementPriceValue(String value) { this.complementPriceValue = value; }

    public String getComplementPriceDescription() { return complementPriceDescription; }
    public void setComplementPriceDescription(String value) { this.complementPriceDescription = value; }

    public String getComplementAdditionalDetail() { return complementAdditionalDetail; }
    public void setComplementAdditionalDetail(String value) { this.complementAdditionalDetail = value; }
}
