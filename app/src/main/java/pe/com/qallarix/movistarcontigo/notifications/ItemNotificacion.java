package pe.com.qallarix.movistarcontigo.notifications;

public class ItemNotificacion {
    private String titulo;
    private String timeline;
    private boolean leida;


    public ItemNotificacion(String titulo, String timeline, boolean leida) {
        this.titulo = titulo;
        this.timeline = timeline;
        this.leida = leida;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }
}
