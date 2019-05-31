package pe.com.qallarix.movistarcontigo.openlearning.pojos;

import java.util.List;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class BeneficioEstudios {
    private Message message;
    private List<Benefit> benefits;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<Benefit> getBenefits() { return benefits; }
    public void setBenefits(List<Benefit> value) { this.benefits = value; }
}
