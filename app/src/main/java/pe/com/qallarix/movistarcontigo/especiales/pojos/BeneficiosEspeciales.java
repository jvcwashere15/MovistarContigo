package pe.com.qallarix.movistarcontigo.especiales.pojos;

import java.util.List;

import pe.com.qallarix.movistarcontigo.pojos.Message;

public class BeneficiosEspeciales {
    private Message message;
    private List<SpecialBenefit> specialBenefits;

    public Message getMessage() { return message; }
    public void setMessage(Message value) { this.message = value; }

    public List<SpecialBenefit> getSpecialBenefits() { return specialBenefits; }
    public void setSpecialBenefits(List<SpecialBenefit> value) { this.specialBenefits = value; }
}
