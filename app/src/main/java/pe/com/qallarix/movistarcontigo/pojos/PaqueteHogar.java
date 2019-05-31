package pe.com.qallarix.movistarcontigo.pojos;

import java.io.Serializable;

public class PaqueteHogar implements Serializable {
    private String megas1;
    private String megas2;
    private String trio;
    private String hd;
    private String modem;
    private String precio1;
    private String precio2;
    private String precio3;

    public String getMegas1() {
        return megas1;
    }

    public void setMegas1(String megas1) {
        this.megas1 = megas1;
    }

    public String getMegas2() {
        return megas2;
    }

    public void setMegas2(String megas2) {
        this.megas2 = megas2;
    }

    public String getTrio() {
        return trio;
    }

    public void setTrio(String trio) {
        this.trio = trio;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public String getModem() {
        return modem;
    }

    public void setModem(String modem) {
        this.modem = modem;
    }

    public String getPrecio1() {
        return precio1;
    }

    public void setPrecio1(String precio1) {
        this.precio1 = precio1;
    }

    public String getPrecio2() {
        return precio2;
    }

    public void setPrecio2(String precio2) {
        this.precio2 = precio2;
    }

    public String getPrecio3() {
        return precio3;
    }

    public void setPrecio3(String precio3) {
        this.precio3 = precio3;
    }
}
