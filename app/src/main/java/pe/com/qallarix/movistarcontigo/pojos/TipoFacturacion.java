package pe.com.qallarix.movistarcontigo.pojos;

import java.io.Serializable;

public class TipoFacturacion implements Serializable {
    private String descripcion;
    private int imagen;

    public TipoFacturacion(String descripcion, int imagen) {
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
