package pe.com.qallarix.movistarcontigo.vacaciones.pendientes.pojos;

public class EstadoVacaciones {
    private String bossCode;
    private String employeeCode;
    private String requestCode;
    private String requestStateCode;
    private String requestStateName;
    private String requestTypeName;
    private String requestDateStart;
    private String requestDateEnd;
    private String requestAdvancementVacation;
    private long requestDifferenceDays;
    private long total;

    public String getBossCode() {
        return bossCode;
    }

    public void setBossCode(String bossCode) {
        this.bossCode = bossCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestStateCode() {
        return requestStateCode;
    }

    public void setRequestStateCode(String requestStateCode) {
        this.requestStateCode = requestStateCode;
    }

    public String getRequestStateName() {
        return requestStateName;
    }

    public void setRequestStateName(String requestStateName) {
        this.requestStateName = requestStateName;
    }

    public String getRequestTypeName() {
        return requestTypeName;
    }

    public void setRequestTypeName(String requestTypeName) {
        this.requestTypeName = requestTypeName;
    }

    public String getRequestDateStart() {
        return requestDateStart;
    }

    public void setRequestDateStart(String requestDateStart) {
        this.requestDateStart = requestDateStart;
    }

    public String getRequestDateEnd() {
        return requestDateEnd;
    }

    public void setRequestDateEnd(String requestDateEnd) {
        this.requestDateEnd = requestDateEnd;
    }

    public String getRequestAdvancementVacation() {
        return requestAdvancementVacation;
    }

    public void setRequestAdvancementVacation(String requestAdvancementVacation) {
        this.requestAdvancementVacation = requestAdvancementVacation;
    }

    public long getRequestDifferenceDays() {
        return requestDifferenceDays;
    }

    public void setRequestDifferenceDays(long requestDifferenceDays) {
        this.requestDifferenceDays = requestDifferenceDays;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
