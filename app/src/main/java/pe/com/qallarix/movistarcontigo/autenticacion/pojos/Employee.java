package pe.com.qallarix.movistarcontigo.autenticacion.pojos;

public class Employee {
    private String documentNumber;
    private String fullName;
    private String firstName;
    private String shortName;
    private String initials;
    private String email;
    private String sex;
    private String clase;
    private String category;
    private String profile;
    private String cip;
    private String vicePresidency;
    private String direction;
    private String management;
    private boolean isFlexPlace;


    public boolean isFlexPlace() {
        return isFlexPlace;
    }

    public void setFlexPlace(boolean flexPlace) {
        isFlexPlace = flexPlace;
    }

    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String value) { this.documentNumber = value; }

    public String getFullName() { return fullName; }
    public void setFullName(String value) { this.fullName = value; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String value) { this.firstName = value; }

    public String getShortName() { return shortName; }
    public void setShortName(String value) { this.shortName = value; }

    public String getInitials() { return initials; }
    public void setInitials(String value) { this.initials = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getSex() { return sex; }
    public void setSex(String value) { this.sex = value; }

    public String getClase() { return clase; }
    public void setClase(String value) { this.clase = value; }

    public String getCategory() { return category; }
    public void setCategory(String value) { this.category = value; }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getVicePresidency() {
        return vicePresidency;
    }

    public void setVicePresidency(String vicePresidency) {
        this.vicePresidency = vicePresidency;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getManagement() {
        return management;
    }

    public void setManagement(String management) {
        this.management = management;
    }
}
