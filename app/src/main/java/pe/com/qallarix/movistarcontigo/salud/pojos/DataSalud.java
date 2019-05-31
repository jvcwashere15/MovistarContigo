package pe.com.qallarix.movistarcontigo.salud.pojos;

import java.util.List;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class DataSalud {
    private Message message;
    private List<Plan> plans;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<Plan> getPlans() { return plans; }
    public void setPlans(List<Plan> value) { this.plans = value; }
}
