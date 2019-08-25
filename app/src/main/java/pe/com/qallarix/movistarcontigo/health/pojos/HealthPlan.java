package pe.com.qallarix.movistarcontigo.health.pojos;

import java.io.Serializable;

public class HealthPlan implements Serializable {
    private long id;
    private String title;
    private String additionalInformation;
    private Detail detail;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getAdditionalInformation() { return additionalInformation; }
    public void setAdditionalInformation(String value) { this.additionalInformation = value; }

    public Detail getDetail() { return detail; }
    public void setDetail(Detail value) { this.detail = value; }
}
