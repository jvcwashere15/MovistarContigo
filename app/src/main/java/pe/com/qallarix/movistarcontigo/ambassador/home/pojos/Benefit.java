package pe.com.qallarix.movistarcontigo.ambassador.home.pojos;

import java.util.List;

public class Benefit {
    private String benefitName;
    private String benefitDescription;
    private String imageURL;
    private List<OfferList> offerList;
    private List<Object> instructionList;
    private List<ComplementList> complementList;
    private List<SelfhelpList> selfhelpList;
    private List<SelfhelpList> selfhelpList2;

    public String getBenefitName() { return benefitName; }
    public void setBenefitName(String value) { this.benefitName = value; }

    public String getBenefitDescription() { return benefitDescription; }
    public void setBenefitDescription(String value) { this.benefitDescription = value; }

    public String getImageURL() { return imageURL; }
    public void setImageURL(String value) { this.imageURL = value; }

    public List<OfferList> getOfferList() { return offerList; }
    public void setOfferList(List<OfferList> value) { this.offerList = value; }

    public List<Object> getInstructionList() { return instructionList; }
    public void setInstructionList(List<Object> value) { this.instructionList = value; }

    public List<ComplementList> getComplementList() { return complementList; }
    public void setComplementList(List<ComplementList> value) { this.complementList = value; }

    public List<SelfhelpList> getSelfhelpList() { return selfhelpList; }
    public void setSelfhelpList(List<SelfhelpList> value) { this.selfhelpList = value; }

    public List<SelfhelpList> getSelfhelpList2() { return selfhelpList2; }
    public void setSelfhelpList2(List<SelfhelpList> value) { this.selfhelpList2 = value; }
}
