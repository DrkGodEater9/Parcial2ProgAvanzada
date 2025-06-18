package Servidor.edu.progavud.parcial2.modelo;

/**
 *
 * @author carlosmamut1
 */
public class JugadorVO {
    private String contrasena;
    private String nombreUsuario;
    private transient int aciertos;
    private transient int intentos;

    public JugadorVO(String contrasena, String nombreUsuario) {
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    
}
