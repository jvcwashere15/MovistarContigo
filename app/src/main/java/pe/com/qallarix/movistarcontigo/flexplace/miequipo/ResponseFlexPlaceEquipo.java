package pe.com.qallarix.movistarcontigo.flexplace.miequipo;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseFlexPlaceEquipo {
    private Message message;
    private Team team;
    private long total;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public Team getTeam() { return team; }
    public void setTeam(Team value) { this.team = value; }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
