package pe.com.qallarix.movistarcontigo.pojos;

public class MensajeToken {
    private String code;
    private String description;
    private String token;

    public MensajeToken() {
    }

    public MensajeToken(String code, String description, String token) {
        this.code = code;
        this.description = description;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
