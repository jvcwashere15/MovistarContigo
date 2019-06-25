package pe.com.qallarix.movistarcontigo.vacaciones.registro.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseValidarFechas {
    private Message message;
    private Validation validation;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public Validation getValidation() { return validation; }
    public void setValidation(Validation value) { this.validation = value; }
}
