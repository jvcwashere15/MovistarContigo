package pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseDetalleSolicitud {
    private Message message;
    private DetalleVacaciones request;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public DetalleVacaciones getRequest() {
        return request;
    }

    public void setRequest(DetalleVacaciones request) {
        this.request = request;
    }
}
