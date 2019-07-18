package pe.com.qallarix.movistarcontigo.flexplace.solicitudes.pojos;

public class SolicitudFlex {
    private long id;
    private String employee;
    private String statusID;
    private String status;
    private String dateMessage;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getEmployee() { return employee; }
    public void setEmployee(String value) { this.employee = value; }

    public String getStatusID() { return statusID; }
    public void setStatusID(String value) { this.statusID = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public String getDateMessage() { return dateMessage; }
    public void setDateMessage(String value) { this.dateMessage = value; }
}
