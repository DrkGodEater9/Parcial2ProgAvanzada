package Servidor.edu.progavud.parcial2.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión a la base de datos mediante JDBC
 * para el sistema de juego de memoria multijugador.
 * 
 * Esta clase proporciona métodos para establecer, mantener y cerrar conexiones
 * con la base de datos utilizando el patrón Singleton para la conexión.
 * Maneja los parámetros de conexión (URL, usuario, contraseña) de forma estática
 * para asegurar consistencia en toda la aplicación.
 * 
 * Utiliza JDBC (Java Database Connectivity) para interactuar con la base de datos
 * y gestiona automáticamente los errores de conexión. La clase está diseñada
 * para trabajar con bases de datos relacionales compatibles con JDBC.
 * 
 * @author carlosmamut
 * @author batapop
 * @author AlexM
 * @version 1.0
 * @since 2024
 */
public class ConexionSQL {

    /**
     * Conexión estática a la base de datos. Se utiliza patrón Singleton
     * para mantener una única instancia de conexión activa.
     */
    private static Connection cn = null;
    
    /**
     * URL de conexión a la base de datos. Contiene la dirección
     * y parámetros específicos del servidor de base de datos.
     */
    private static String URLBD;
    
    /**
     * Nombre de usuario para autenticación en la base de datos.
     */
    private static String usuario;
    
    /**
     * Contraseña para autenticación en la base de datos.
     */
    private static String contrasena;

    /**
     * Establece la conexión con la base de datos utilizando los parámetros
     * configurados previamente.
     * 
     * Utiliza DriverManager de JDBC para crear una nueva conexión con los
     * parámetros estáticos configurados. En caso de error de conexión,
     * maneja la excepción de forma silenciosa.
     * 
     * @return La conexión activa a la base de datos, null si falla la conexión
     */
    public Connection getConexion() {
        try {
            cn = DriverManager.getConnection(URLBD, usuario, contrasena);
        } catch (SQLException ex) {
            // Manejo silencioso de errores de conexión
        }
        return cn;
    }

    /**
     * Establece la URL de conexión a la base de datos.
     * 
     * Configura la cadena de conexión que incluye el protocolo, servidor,
     * puerto y nombre de la base de datos a utilizar.
     * 
     * @param URLBD La URL completa de conexión a la base de datos
     */
    public void setURLBD(String URLBD) {
        ConexionSQL.URLBD = URLBD;
    }

    /**
     * Establece el nombre de usuario para la autenticación en la base de datos.
     * 
     * Configura las credenciales de usuario que serán utilizadas para
     * establecer la conexión con la base de datos.
     * 
     * @param usuario El nombre de usuario para la conexión a la base de datos
     */
    public void setUsuario(String usuario) {
        ConexionSQL.usuario = usuario;
    }

    /**
     * Establece la contraseña para la autenticación en la base de datos.
     * 
     * Configura la contraseña que será utilizada junto con el usuario
     * para autenticarse en la base de datos.
     * 
     * @param contrasena La contraseña para la conexión a la base de datos
     */
    public void setContrasena(String contrasena) {
        ConexionSQL.contrasena = contrasena;
    }

    /**
     * Cierra la conexión activa con la base de datos.
     * 
     * Libera los recursos de conexión estableciendo la referencia
     * estática como null. Este método debe llamarse cuando ya no
     * se requiera acceso a la base de datos.
     */
    public void desconectar() {
        cn = null;
    }
}