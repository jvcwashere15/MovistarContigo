package pe.com.qallarix.movistarcontigo.pojos;

public class PaqueteMovil {
    private String datos;
    private String comparte_datos;
    private String apps;
    private int imagen_apps;
    private String descripcion_apps;
    private String llamadas_mensajes;
    private String megas_internacional;
    private String precio_regular;
    private String cargo_fijo;

    public PaqueteMovil(String datos, String comparte_datos, String apps, int imagen_apps, String descripcion_apps, String llamadas_mensajes, String megas_internacional, String precio_regular, String cargo_fijo) {
        this.datos = datos;
        this.comparte_datos = comparte_datos;
        this.apps = apps;
        this.imagen_apps = imagen_apps;
        this.descripcion_apps = descripcion_apps;
        this.llamadas_mensajes = llamadas_mensajes;
        this.megas_internacional = megas_internacional;
        this.precio_regular = precio_regular;
        this.cargo_fijo = cargo_fijo;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getComparte_datos() {
        return comparte_datos;
    }

    public void setComparte_datos(String comparte_datos) {
        this.comparte_datos = comparte_datos;
    }

    public String getApps() {
        return apps;
    }

    public void setApps(String apps) {
        this.apps = apps;
    }

    public int getImagen_apps() {
        return imagen_apps;
    }

    public void setImagen_apps(int imagen_apps) {
        this.imagen_apps = imagen_apps;
    }

    public String getDescripcion_apps() {
        return descripcion_apps;
    }

    public void setDescripcion_apps(String descripcion_apps) {
        this.descripcion_apps = descripcion_apps;
    }

    public String getLlamadas_mensajes() {
        return llamadas_mensajes;
    }

    public void setLlamadas_mensajes(String llamadas_mensajes) {
        this.llamadas_mensajes = llamadas_mensajes;
    }

    public String getMegas_internacional() {
        return megas_internacional;
    }

    public void setMegas_internacional(String megas_internacional) {
        this.megas_internacional = megas_internacional;
    }

    public String getPrecio_regular() {
        return precio_regular;
    }

    public void setPrecio_regular(String precio_regular) {
        this.precio_regular = precio_regular;
    }

    public String getCargo_fijo() {
        return cargo_fijo;
    }

    public void setCargo_fijo(String cargo_fijo) {
        this.cargo_fijo = cargo_fijo;
    }
}
