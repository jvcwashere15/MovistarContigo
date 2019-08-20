package pe.com.qallarix.movistarcontigo.flexplace.approve.pojos;

import java.util.List;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseSolicitudesFlexPlace {
    private Message message;
    private List<SolicitudFlex> list;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<SolicitudFlex> getList() { return list; }
    public void setList(List<SolicitudFlex> value) { this.list = value; }
}
