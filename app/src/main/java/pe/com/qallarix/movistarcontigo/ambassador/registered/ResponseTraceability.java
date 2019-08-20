package pe.com.qallarix.movistarcontigo.ambassador.registered;

import java.util.List;

import pe.com.qallarix.movistarcontigo.ambassador.registered.pojos.BreakRegistered;
import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseTraceability {
    private Message message;
    private List<BreakRegistered> list;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<BreakRegistered> getList() { return list; }
    public void setList(List<BreakRegistered> value) { this.list = value; }
}
