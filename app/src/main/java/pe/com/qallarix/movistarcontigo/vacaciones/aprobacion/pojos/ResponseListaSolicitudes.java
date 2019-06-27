package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseListaSolicitudes {
    private SolicitudAprobacion[] list;
    private Message message;

    public SolicitudAprobacion[] getList() {
        return list;
    }

    public void setList(SolicitudAprobacion[] list) {
        this.list = list;
    }

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }
}
