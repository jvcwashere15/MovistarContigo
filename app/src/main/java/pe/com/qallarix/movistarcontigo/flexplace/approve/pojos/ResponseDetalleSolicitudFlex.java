package pe.com.qallarix.movistarcontigo.flexplace.approve.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseDetalleSolicitudFlex {
    private Message message;
    private DetalleSolicitudFlex request;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public DetalleSolicitudFlex getRequest() {
        return request;
    }

    public void setRequest(DetalleSolicitudFlex request) {
        this.request = request;
    }
}
