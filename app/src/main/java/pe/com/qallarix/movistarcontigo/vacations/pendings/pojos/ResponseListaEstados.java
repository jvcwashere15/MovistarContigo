package pe.com.qallarix.movistarcontigo.vacations.pendings.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseListaEstados {
    private Message message;
    private EstadoVacaciones[] list;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public EstadoVacaciones[] getVacaciones() {
        return list;
    }

    public void setVacaciones(EstadoVacaciones[] list) {
        this.list = list;
    }
}
