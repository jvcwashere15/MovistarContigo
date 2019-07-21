package pe.com.qallarix.movistarcontigo.flexplace.miequipo;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseFlexPlaceEquipo {
    private Message message;
    private Team team;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public Team getTeam() { return team; }
    public void setTeam(Team value) { this.team = value; }
}
