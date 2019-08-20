package pe.com.qallarix.movistarcontigo.flexplace.approve.pojos;

public class SolicitudFlex {
    private long id;
    private String employee;
    private String statusId;
    private String status;
    private String dateMessage;

    public long getId() { return id; }
    public void setId(long value) { this.id = value; }

    public String getEmployee() { return employee; }
    public void setEmployee(String value) { this.employee = value; }

    public String getStatusId() { return statusId; }
    public void setStatusId(String value) { this.statusId = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public String getDateMessage() { return dateMessage; }
    public void setDateMessage(String value) { this.dateMessage = value; }
}
