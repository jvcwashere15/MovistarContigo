package pe.com.qallarix.movistarcontigo.pojos;

public class Noticia {
    private String titulo;
    private String fecha;
    private String contenido;
    private int foto;

    public Noticia(String titulo, String fecha, String contenido, int foto) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.contenido = contenido;
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public int getFoto() {
        return foto;
    }
}
