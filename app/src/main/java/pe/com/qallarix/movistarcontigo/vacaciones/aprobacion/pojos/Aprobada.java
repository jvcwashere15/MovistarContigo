package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos;

public class Aprobada {
    private String colaborador;
    private String fecha;
    private int estado;

    public Aprobada(String colaborador, String fecha, int estado) {
        this.colaborador = colaborador;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
