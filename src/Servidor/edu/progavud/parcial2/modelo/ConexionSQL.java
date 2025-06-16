package Servidor.edu.progavud.parcial2.modelo;

import Jugador.edu.progavud.parcial2.modelo.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión a la base de datos mediante JDBC.
 * Proporciona métodos para obtener y cerrar la conexión con la base de datos.
 * Se importaron tambien las librerias para poder manejar todo lo relacionado a sql conection
 * 
 * @author carlosmamut1
 * @author FELIPE
 * @author AlexM
 */
public class ConexionSQL {

    private static Connection cn = null;
    private static String URLBD;
    private static String usuario;
    private static String contrasena;

    /**
     * Establece la conexión con la base de datos utilizando los parámetros configurados.
     * 
     * @return La conexión a la base de datos.
     */
    public Connection getConexion() {
        try {
            cn = DriverManager.getConnection(URLBD, usuario, contrasena);
        } catch (SQLException ex) {
            //mostrarMensaje("No se puede cargar el controlador");
        }
        return cn;
    }

    /**
     * Establece la URL de la base de datos.
     * 
     * @param URLBD La URL de la base de datos.
     */
    public void setURLBD(String URLBD) {
        ConexionSQL.URLBD = URLBD;
    }

    /**
     * Establece el nombre de usuario para la conexión a la base de datos.
     * 
     * @param usuario El nombre de usuario.
     */
    public void setUsuario(String usuario) {
        ConexionSQL.usuario = usuario;
    }

    /**
     * Establece la contraseña para la conexión a la base de datos.
     * 
     * @param contrasena La contraseña de la base de datos.
     */
    public void setContrasena(String contrasena) {
        ConexionSQL.contrasena = contrasena;
    }

    /**
     * Desconecta la conexión a la base de datos.
     */
    public void desconectar() {
        cn = null;
    }
}