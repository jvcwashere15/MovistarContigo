package pe.com.qallarix.movistarcontigo.pojos;

import java.io.Serializable;

public class Combo implements Serializable {
    private int color;
    private String descripcion;
    private String megas;
    private String precio_embajador;
    private String precio;

    public Combo(int color, String descripcion, String megas, String precio_embajador, String precio) {
        this.color = color;
        this.descripcion = descripcion;
        this.megas = megas;
        this.precio_embajador = precio_embajador;
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMegas() {
        return megas;
    }

    public void setMegas(String megas) {
        this.megas = megas;
    }

    public String getPrecio_embajador() {
        return precio_embajador;
    }

    public void setPrecio_embajador(String precio_embajador) {
        this.precio_embajador = precio_embajador;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
