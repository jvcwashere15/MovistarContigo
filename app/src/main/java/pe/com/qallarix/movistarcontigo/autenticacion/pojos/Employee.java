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
}
