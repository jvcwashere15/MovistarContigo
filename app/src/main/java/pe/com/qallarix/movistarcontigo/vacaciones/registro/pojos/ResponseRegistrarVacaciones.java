package pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseRegistrarVacaciones {
    private Message message;
    private String result;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public String getResult() { return result; }
    public void setResult(String value) { this.result = value; }
}
