package pe.com.qallarix.movistarcontigo.pojos;

import java.io.Serializable;

public class Requisito implements Serializable {
    String numero;
    String descripcion;

    public Requisito(String numero, String descripcion) {
        this.numero = numero;
        this.descripcion = descripcion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
