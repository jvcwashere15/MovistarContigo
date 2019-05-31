package pe.com.qallarix.movistarcontigo.pojos;

public class Usuario {
    String dni;
    String nombres;
    String apellidos;
    String correo;
    String movil;
    String cargo;
    String img;
    String nom_ape;

    public Usuario() {
    }

    public Usuario(String dni, String nombres, String apellidos, String correo, String movil, String cargo, String img, String nom_ape) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.movil = movil;
        this.cargo = cargo;
        this.img = img;
        this.nom_ape = nom_ape;
    }

    public String getDni() {
        return dni;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getMovil() {
        return movil;
    }

    public String getCargo() {
        return cargo;
    }

    public String getImg() {
        return img;
    }

    public String getNom_ape() {
        return nom_ape;
    }
}
