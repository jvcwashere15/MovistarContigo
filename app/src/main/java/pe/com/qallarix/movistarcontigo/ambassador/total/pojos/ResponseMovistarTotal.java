package pe.com.qallarix.movistarcontigo.ambassador.total.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class ResponseMovistarTotal {
    private Message message;
    private Benefit benefit;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public Benefit getBenefit() { return benefit; }
    public void setBenefit(Benefit value) { this.benefit = value; }
}
