package pe.com.qallarix.movistarcontigo.flexplace.historial.pojos;

public class Request {
    private long id;
    private String employee;
    private String leadership;
    private String statusId;
    private String status;
    private String dayWeek;
    private String dateStart;
    private String dateEnd;
    private String dateRequest;
    private String dateApprover;
    private String dateRefuse;
    private String dateCancelled;
    private String reason;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getLeadership() {
        return leadership;
    }

    public void setLeadership(String leadership) {
        this.leadership = leadership;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(String dateRequest) {
        this.dateRequest = dateRequest;
    }

    public String getDateApprover() {
        return dateApprover;
    }

    public void setDateApprover(String dateApprover) {
        this.dateApprover = dateApprover;
    }

    public String getDateRefuse() {
        return dateRefuse;
    }

    public void setDateRefuse(String dateRefuse) {
        this.dateRefuse = dateRefuse;
    }

    public String getDateCancelled() {
        return dateCancelled;
    }

    public void setDateCancelled(String dateCancelled) {
        this.dateCancelled = dateCancelled;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
