package pe.com.qallarix.movistarcontigo.vacaciones.aprobacion.pojos;

public class Pendiente {
    private String colaborador;
    private String fecha;

    public Pendiente(String colaborador, String fecha) {
        this.colaborador = colaborador;
        this.fecha = fecha;
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
}
