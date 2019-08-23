package pe.com.qallarix.movistarcontigo.flexplace.myteam.pojos;

import java.util.List;

import pe.com.qallarix.movistarcontigo.flexplace.myteam.pojos.FlexEquipo;

public class Team {
    private List<FlexEquipo> cinco;
    private List<FlexEquipo> cuatro;
    private List<FlexEquipo> dos;
    private List<FlexEquipo> tres;
    private List<FlexEquipo> uno;

    public List<FlexEquipo> getCinco() {
        return cinco;
    }

    public void setCinco(List<FlexEquipo> cinco) {
        this.cinco = cinco;
    }

    public List<FlexEquipo> getCuatro() {
        return cuatro;
    }

    public void setCuatro(List<FlexEquipo> cuatro) {
        this.cuatro = cuatro;
    }

    public List<FlexEquipo> getDos() {
        return dos;
    }

    public void setDos(List<FlexEquipo> dos) {
        this.dos = dos;
    }

    public List<FlexEquipo> getTres() {
        return tres;
    }

    public void setTres(List<FlexEquipo> tres) {
        this.tres = tres;
    }

    public List<FlexEquipo> getUno() {
        return uno;
    }

    public void setUno(List<FlexEquipo> uno) {
        this.uno = uno;
    }
}
