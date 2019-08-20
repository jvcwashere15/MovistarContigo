package pe.com.qallarix.movistarcontigo.ambassador.home.pojos;

import java.io.Serializable;
import java.util.List;

public class CharacteristicGroupList implements Serializable {
    private long order;
    private String characteristicName;
    private List<CharacteristicList> characteristicList;

    public long getOrder() { return order; }
    public void setOrder(long value) { this.order = value; }

    public String getCharacteristicName() { return characteristicName; }
    public void setCharacteristicName(String value) { this.characteristicName = value; }

    public List<CharacteristicList> getCharacteristicList() { return characteristicList; }
    public void setCharacteristicList(List<CharacteristicList> value) { this.characteristicList = value; }
}
