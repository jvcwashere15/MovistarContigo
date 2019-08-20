package pe.com.qallarix.movistarcontigo.especiales.pojos;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class BenefitDetail {
    private Message message;
    private SpecialBenefits specialBenefits;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public SpecialBenefits getSpecialBenefits() { return specialBenefits; }
    public void setSpecialBenefits(SpecialBenefits value) { this.specialBenefits = value; }
}
