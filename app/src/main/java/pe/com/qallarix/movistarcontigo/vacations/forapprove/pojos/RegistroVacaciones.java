package pe.com.qallarix.movistarcontigo.vacations.forapprove.pojos;

public class RegistroVacaciones {
    private String bossName;
    private String bossCode;
    private String employeeCode;
    private boolean isApprover;
    private String requestCode;
    private String requestDateEnd;
    private String requestDateStart;

    public RegistroVacaciones(String bossName, String bossCode, String employeeCode, boolean isApprover, String requestCode, String requestDateEnd, String requestDateStart) {
        this.bossName = bossName;
        this.bossCode = bossCode;
        this.employeeCode = employeeCode;
        this.isApprover = isApprover;
        this.requestCode = requestCode;
        this.requestDateEnd = requestDateEnd;
        this.requestDateStart = requestDateStart;
    }

    public RegistroVacaciones() {
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

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

    public boolean isApprover() {
        return isApprover;
    }

    public void setApprover(boolean approver) {
        isApprover = approver;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestDateEnd() {
        return requestDateEnd;
    }

    public void setRequestDateEnd(String requestDateEnd) {
        this.requestDateEnd = requestDateEnd;
    }

    public String getRequestDateStart() {
        return requestDateStart;
    }

    public void setRequestDateStart(String requestDateStart) {
        this.requestDateStart = requestDateStart;
    }
}