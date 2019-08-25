package pe.com.qallarix.movistarcontigo.health.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class DataPlan {
    private Message message;
    private HealthPlan healthPlan;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public HealthPlan getHealthPlan() { return healthPlan; }
    public void setHealthPlan(HealthPlan value) { this.healthPlan = value; }
}
