package pe.com.qallarix.movistarcontigo.pojos;

public class Estudio {
    private String titulo;
    private String descripcion1;
    private String cantidad;
    private String descripcion2;
    private int imagen;
    private String descripcion3;
    private int logo;

    public Estudio(String titulo, String descripcion1, String cantidad, String descripcion2, int imagen, String descripcion3, int logo) {
        this.titulo = titulo;
        this.descripcion1 = descripcion1;
        this.cantidad = cantidad;
        this.descripcion2 = descripcion2;
        this.imagen = imagen;
        this.descripcion3 = descripcion3;
        this.logo = logo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion3() {
        return descripcion3;
    }

    public void setDescripcion3(String descripcion3) {
        this.descripcion3 = descripcion3;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
