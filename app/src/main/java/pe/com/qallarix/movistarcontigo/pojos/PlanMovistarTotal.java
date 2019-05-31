package pe.com.qallarix.movistarcontigo.pojos;

import java.io.Serializable;

public class PlanMovistarTotal implements Serializable {
    private String internetMegas;
    private String internetIncluye;
    private String tvCanales;
    private String tvIncluye;
    private String fijaDescripcion;
    private String movilNLineas;
    private String movilGigas;
    private int apps_ilimitadas;
    private String precioRegular;
    private String precioEmbajador;

    public PlanMovistarTotal(String internetMegas, String internetIncluye, String tvCanales, String tvIncluye, String fijaDescripcion, String movilNLineas, String movilGigas, int apps_ilimitadas, String precioRegular, String precioEmbajador) {
        this.internetMegas = internetMegas;
        this.internetIncluye = internetIncluye;
        this.tvCanales = tvCanales;
        this.tvIncluye = tvIncluye;
        this.fijaDescripcion = fijaDescripcion;
        this.movilNLineas = movilNLineas;
        this.movilGigas = movilGigas;
        this.apps_ilimitadas = apps_ilimitadas;
        this.precioRegular = precioRegular;
        this.precioEmbajador = precioEmbajador;
    }

    public String getInternetMegas() {
        return internetMegas;
    }

    public void setInternetMegas(String internetMegas) {
        this.internetMegas = internetMegas;
    }

    public String getInternetIncluye() {
        return internetIncluye;
    }

    public void setInternetIncluye(String internetIncluye) {
        this.internetIncluye = internetIncluye;
    }

    public String getTvCanales() {
        return tvCanales;
    }

    public void setTvCanales(String tvCanales) {
        this.tvCanales = tvCanales;
    }

    public String getTvIncluye() {
        return tvIncluye;
    }

    public void setTvIncluye(String tvIncluye) {
        this.tvIncluye = tvIncluye;
    }

    public String getFijaDescripcion() {
        return fijaDescripcion;
    }

    public void setFijaDescripcion(String fijaDescripcion) {
        this.fijaDescripcion = fijaDescripcion;
    }

    public String getMovilNLineas() {
        return movilNLineas;
    }

    public void setMovilNLineas(String movilNLineas) {
        this.movilNLineas = movilNLineas;
    }

    public String getMovilGigas() {
        return movilGigas;
    }

    public void setMovilGigas(String movilGigas) {
        this.movilGigas = movilGigas;
    }

    public int getApps_ilimitadas() {
        return apps_ilimitadas;
    }

    public void setApps_ilimitadas(int apps_ilimitadas) {
        this.apps_ilimitadas = apps_ilimitadas;
    }

    public String getPrecioRegular() {
        return precioRegular;
    }

    public void setPrecioRegular(String precioRegular) {
        this.precioRegular = precioRegular;
    }

    public String getPrecioEmbajador() {
        return precioEmbajador;
    }

    public void setPrecioEmbajador(String precioEmbajador) {
        this.precioEmbajador = precioEmbajador;
    }
}
