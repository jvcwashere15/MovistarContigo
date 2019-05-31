package pe.com.qallarix.movistarcontigo.openlearning.pojos;

public class Benefit {
    private long id;
    private String title;
    private String description;
    private String institution;
    private String institutionImage;
    private String category;
    private String discount;
    private String discountDetail;
    private String discountImage;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getInstitution() { return institution; }
    public void setInstitution(String value) { this.institution = value; }

    public String getInstitutionImage() { return institutionImage; }
    public void setInstitutionImage(String value) { this.institutionImage = value; }

    public String getCategory() { return category; }
    public void setCategory(String value) { this.category = value; }

    public String getDiscount() { return discount; }
    public void setDiscount(String value) { this.discount = value; }

    public String getDiscountDetail() { return discountDetail; }
    public void setDiscountDetail(String value) { this.discountDetail = value; }

    public String getDiscountImage() { return discountImage; }
    public void setDiscountImage(String value) { this.discountImage = value; }
}
