package pe.com.qallarix.movistarcontigo.ambassador.mobile.pojos;

import java.io.Serializable;
import java.util.List;

public class OfferList implements Serializable {
    private long order;
    private List<CharacteristicGroupList> characteristicGroupList;

    public long getOrder() { return order; }
    public void setOrder(long value) { this.order = value; }

    public List<CharacteristicGroupList> getCharacteristicGroupList() { return characteristicGroupList; }
    public void setCharacteristicGroupList(List<CharacteristicGroupList> value) { this.characteristicGroupList = value; }
}
