package pe.com.qallarix.movistarcontigo.discounts.pojos;

public class Contact {
    private String name;
    private String phone;
    private String phoneAnexo;
    private String phoneWhatsapp;
    private String email;

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getPhone() { return phone; }
    public void setPhone(String value) { this.phone = value; }

    public String getPhoneWhatsapp() { return phoneWhatsapp; }
    public void setPhoneWhatsapp(String value) { this.phoneWhatsapp = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getPhoneAnexo() {
        return phoneAnexo;
    }

    public void setPhoneAnexo(String phoneAnexo) {
        this.phoneAnexo = phoneAnexo;
    }
}
