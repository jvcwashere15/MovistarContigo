package pe.com.qallarix.movistarcontigo.vacaciones.estado.pojos;

public class EstadoVacaciones {
    private String lider;
    private String fecha;
    private String fechaSolicitud;
    private String fechaInicio;
    private String fechaFin;
    private String descLider;
    private String descripcionEstado;
    private int diasSolicitados;
    private int estado;
    public static final int ESTADO_PENDIENTES = 1;
    public static final int ESTADO_APROBADAS = 2;
    public static final int ESTADO_GOZADAS = 3;
    public static final int ESTADO_RECHAZADAS = 4;


    public EstadoVacaciones(String lider, String fecha, String fechaSolicitud, String fechaInicio, String fechaFin, String descLider, String descripcionEstado, int diasSolicitados, int estado) {
        this.lider = lider;
        this.fecha = fecha;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descLider = descLider;
        this.descripcionEstado = descripcionEstado;
        this.diasSolicitados = diasSolicitados;
        this.estado = estado;
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

    public String getLider() {
        return lider;
    }

    public void setLider(String lider) {
        this.lider = lider;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public int getDiasSolicitados() {
        return diasSolicitados;
    }

    public void setDiasSolicitados(int diasSolicitados) {
        this.diasSolicitados = diasSolicitados;
    }

    public String getDescLider() {
        return descLider;
    }

    public void setDescLider(String descLider) {
        this.descLider = descLider;
    }
}
