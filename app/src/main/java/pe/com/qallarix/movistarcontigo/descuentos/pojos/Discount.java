package pe.com.qallarix.movistarcontigo.descuentos.pojos;

public class Discount {
    private String id;
    private String origin;
    private String title;
    private String description;
    private String category;
    private String brand;
    private String brandImage;
    private String zonal;
    private long validityTime;
    private String discount;
    private String discountDetail;
    private String discountImage;

    private String deepLink;
    private Object[] local;

    private Contact contact;
    private long behaivorDiscount;
    private String howToUse;
    private String urlFile;
    private String urlWeb;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin() { return origin; }
    public void setOrigin(String value) { this.origin = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public String getCategory() { return category; }
    public void setCategory(String value) { this.category = value; }

    public String getBrand() { return brand; }
    public void setBrand(String value) { this.brand = value; }

    public String getBrandImage() { return brandImage; }
    public void setBrandImage(String value) { this.brandImage = value; }

    public String getZonal() { return zonal; }
    public void setZonal(String value) { this.zonal = value; }

    public long getValidityTime() { return validityTime; }
    public void setValidityTime(long value) { this.validityTime = value; }

    public String getDiscount() { return discount; }
    public void setDiscount(String value) { this.discount = value; }

    public String getDiscountDetail() { return discountDetail; }
    public void setDiscountDetail(String value) { this.discountDetail = value; }

    public String getDiscountImage() { return discountImage; }
    public void setDiscountImage(String value) { this.discountImage = value; }



    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    public Object[] getLocal() {
        return local;
    }

    public void setLocal(Object[] local) {
        this.local = local;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public long getBehaivorDiscount() {
        return behaivorDiscount;
    }

    public void setBehaivorDiscount(long behaivorDiscount) {
        this.behaivorDiscount = behaivorDiscount;
    }

    public String getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public void setUrlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
    }
}
