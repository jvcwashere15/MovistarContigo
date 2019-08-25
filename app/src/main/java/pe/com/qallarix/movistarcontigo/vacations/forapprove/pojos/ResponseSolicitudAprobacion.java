package pe.com.qallarix.movistarcontigo.vacations.forapprove.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseSolicitudAprobacion {
    private Message message;
    private DetalleSolicitud request;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public DetalleSolicitud getRequest() {
        return request;
    }

    public void setRequest(DetalleSolicitud request) {
        this.request = request;
    }
}
