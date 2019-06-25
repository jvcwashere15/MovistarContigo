package pe.com.qallarix.movistarcontigo.vacaciones.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class VacacionesDashboard {
    private Message message;
    private FutureJoy futureJoy;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public FutureJoy getFutureJoy() { return futureJoy; }
    public void setFutureJoy(FutureJoy value) { this.futureJoy = value; }
}
