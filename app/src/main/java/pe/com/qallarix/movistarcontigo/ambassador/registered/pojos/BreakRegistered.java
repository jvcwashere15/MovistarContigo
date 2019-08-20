package pe.com.qallarix.movistarcontigo.ambassador.registered.pojos;

public class BreakRegistered {
    private String dni;
    private String date;
    private String status;
    private String mobile;
    private String employee;
    private String breakType;
    private String breakProduct;

    public String getDni() { return dni; }
    public void setDni(String value) { this.dni = value; }

    public String getDate() { return date; }
    public void setDate(String value) { this.date = value; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getBreakType() {
        return breakType;
    }

    public void setBreakType(String breakType) {
        this.breakType = breakType;
    }

    public String getBreakProduct() {
        return breakProduct;
    }

    public void setBreakProduct(String breakProduct) {
        this.breakProduct = breakProduct;
    }
}
