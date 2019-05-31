package pe.com.qallarix.movistarcontigo.embajador.hogar.pojos;

import java.io.Serializable;

public class CharacteristicList implements Serializable {
    private long order;
    private String characteristicName;
    private String characteristicValue;

    public long getOrder() { return order; }
    public void setOrder(long value) { this.order = value; }

    public String getCharacteristicName() { return characteristicName; }

    public void setCharacteristicName(String characteristicName) { this.characteristicName = characteristicName; }

    public String getCharacteristicValue() { return characteristicValue; }
    public void setCharacteristicValue(String value) { this.characteristicValue = value; }
}
