package pe.com.qallarix.movistarcontigo.vacations.register.pojos;

public class Validation {
    private long id;
    private long type;
    private String dateRequest;
    private String dateModified;
    private String userRegister;
    private String userModified;
    private String employeeCode;
    private String requestDateMax;
    private String employeeProfileCode;
    private String requestDateStart;
    private String requestDateEnd;
    private String requestTypeCode;
    private String observation;
    private String observationError;
    private String observationMessage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequestDateMax() {
        return requestDateMax;
    }

    public void setRequestDateMax(String requestDateMax) {
        this.requestDateMax = requestDateMax;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(String dateRequest) {
        this.dateRequest = dateRequest;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(String userRegister) {
        this.userRegister = userRegister;
    }

    public String getUserModified() {
        return userModified;
    }

    public void setUserModified(String userModified) {
        this.userModified = userModified;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeProfileCode() {
        return employeeProfileCode;
    }

    public void setEmployeeProfileCode(String employeeProfileCode) {
        this.employeeProfileCode = employeeProfileCode;
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

    public String getRequestTypeCode() {
        return requestTypeCode;
    }

    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getObservationError() {
        return observationError;
    }

    public void setObservationError(String observationError) {
        this.observationError = observationError;
    }

    public String getObservationMessage() {
        return observationMessage;
    }

    public void setObservationMessage(String observationMessage) {
        this.observationMessage = observationMessage;
    }
}
