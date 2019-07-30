package pe.com.qallarix.movistarcontigo.notificacion;

import java.util.List;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseListNotifications {
    private Message message;
    private List<Notification> list;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<Notification> getList() {
        return list;
    }

    public void setList(List<Notification> list) {
        this.list = list;
    }
}
