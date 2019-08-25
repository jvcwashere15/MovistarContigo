package pe.com.qallarix.movistarcontigo.discounts.pojos;

public class Category {
    private long code;
    private String description;
    private String imageUrl;

    public long getCode() { return code; }
    public void setCode(long value) { this.code = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
