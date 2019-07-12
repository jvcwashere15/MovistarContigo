package pe.com.qallarix.movistarcontigo.flexplace.historial.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseDetalleFlexPlace {
    private Message message;
    private Request request;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public Request getRequest() { return request; }
    public void setRequest(Request value) { this.request = value; }
}
