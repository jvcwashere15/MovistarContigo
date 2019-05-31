package pe.com.qallarix.movistarcontigo.notificacion;

import java.util.List;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class Notificaciones {
    private Message message;
    private List<Notification> notifications;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
