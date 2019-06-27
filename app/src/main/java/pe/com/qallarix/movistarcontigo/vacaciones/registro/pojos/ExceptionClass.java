package pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos;

public class ExceptionClass {
    private String exceptionCategory;
    private String exceptionCode;
    private String exceptionMessage;
    private Object exceptionDetail;
    private String exceptionSeverity;

    public String getExceptionCategory() {
        return exceptionCategory;
    }

    public void setExceptionCategory(String exceptionCategory) {
        this.exceptionCategory = exceptionCategory;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Object getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(Object exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    public String getExceptionSeverity() {
        return exceptionSeverity;
    }

    public void setExceptionSeverity(String exceptionSeverity) {
        this.exceptionSeverity = exceptionSeverity;
    }
}
