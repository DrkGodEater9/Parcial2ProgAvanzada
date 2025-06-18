package Servidor.edu.progavud.parcial2.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de acceso a datos (DAO) para la gestión de jugadores en la base de datos
 * del sistema de juego de memoria multijugador.
 * 
 * Esta clase implementa el patrón DAO (Data Access Object) proporcionando
 * métodos para realizar operaciones CRUD (Create, Read, Update, Delete) sobre
 * la tabla de jugadores en la base de datos. Maneja la conexión, consultas SQL
 * y mapeo de datos entre la base de datos y los objetos JugadorVO.
 * 
 * Operaciones disponibles:
 * - Consulta de jugadores por nombre de usuario
 * - Validación de credenciales (usuario y contraseña)
 * - Inserción de nuevos jugadores
 * - Gestión de conexiones de base de datos
 * 
 * @author carlosmamut
 * @author batapop
 * @author AlexM
 * @version 1.0
 * @since 2024
 */
public class JugadorDAO {
    
    /**
     * Conexión activa a la base de datos.
     */
    private Connection con;
    
    /**
     * Statement para ejecutar consultas SQL.
     */
    private Statement st;
    
    /**
     * ResultSet para almacenar resultados de consultas.
     */
    private ResultSet rs;
    
    /**
     * Objeto para gestionar la conexión SQL.
     */
    private ConexionSQL cnxSQL;

    /**
     * Constructor por defecto que inicializa las referencias de conexión
     * y crea una nueva instancia de ConexionSQL.
     */
    public JugadorDAO() {
        con = null;
        st = null;
        rs = null;
        cnxSQL = new ConexionSQL();
    }

    /**
     * Establece la conexión con la base de datos y crea un Statement
     * para ejecutar consultas SQL.
     * 
     * Este método debe llamarse antes de realizar cualquier operación
     * de base de datos para asegurar que existe una conexión activa.
     *
     * @throws SQLException Si ocurre un error al establecer la conexión
     *                     o crear el Statement
     */
    public void abrirElPuenteDeConexion() throws SQLException {
        con = (Connection) cnxSQL.getConexion();
        st = con.createStatement();
    }

    /**
     * Cierra el Statement activo y desconecta la conexión con la base de datos.
     * 
     * Libera los recursos de base de datos y debe llamarse después de
     * completar las operaciones SQL para evitar memory leaks.
     *
     * @throws SQLException Si ocurre un error al cerrar los recursos
     */
    public void cerrarElPuenteDeConexion() throws SQLException {
        st.close();
        cnxSQL.desconectar();
    }
    
    /**
     * Consulta un jugador en la base de datos por nombre de usuario.
     * 
     * Busca un jugador específico utilizando únicamente el nombre de usuario
     * como criterio de búsqueda y retorna todos sus datos.
     * 
     * @param nombreUsuario El nombre de usuario del jugador a buscar
     * @return JugadorVO con los datos del jugador encontrado, null si no existe
     * @throws SQLException Si ocurre un error durante la consulta
     */
    public JugadorVO consultarJugador(String nombreUsuario) throws SQLException {
        JugadorVO jugador = null;
        String consulta = "SELECT * FROM jugadores WHERE nombreUsuario = '" + nombreUsuario + "'";

        abrirElPuenteDeConexion();
        rs = st.executeQuery(consulta);
        if (rs.next()) {
            jugador = new JugadorVO(rs.getString("contrasena"), rs.getString("nombreUsuario"));
        }
        cerrarElPuenteDeConexion();

        return jugador;
    }

    /**
     * Valida las credenciales de un jugador consultando por nombre de usuario
     * y contraseña simultáneamente.
     * 
     * Realiza una consulta que verifica tanto el nombre de usuario como la
     * contraseña, útil para procesos de autenticación y login.
     * 
     * @param contrasena La contraseña a verificar
     * @param nombreUsuario El nombre de usuario a buscar
     * @return JugadorVO si las credenciales son válidas, null si no coinciden
     * @throws SQLException Si ocurre un error durante la consulta de validación
     */
    public JugadorVO consultarJugadorPorUsuarioYContrasena(String contrasena, String nombreUsuario) throws SQLException {
        JugadorVO jugador = null;
        String consulta = "SELECT * FROM jugadores WHERE nombreUsuario = '" + nombreUsuario + "' AND contrasena = '" + contrasena + "'";

        abrirElPuenteDeConexion();
        rs = st.executeQuery(consulta);
        if (rs.next()) {
            jugador = new JugadorVO(rs.getString("contrasena"), rs.getString("nombreUsuario"));
        }
        cerrarElPuenteDeConexion();

        return jugador;
    }
    
    /**
     * Inserta un nuevo jugador en la base de datos.
     * 
     * Toma un objeto JugadorVO y extrae sus datos para crear un nuevo
     * registro en la tabla de jugadores. La operación se realiza mediante
     * una consulta INSERT SQL.
     * 
     * @param jugador El objeto JugadorVO con los datos del nuevo jugador
     * @throws SQLException Si ocurre un error durante la inserción
     */
    public void insertarDatosDeLosJugadores(JugadorVO jugador) throws SQLException {
        abrirElPuenteDeConexion();
        String insercion = "INSERT INTO jugadores VALUES('" + jugador.getContrasena()+ "','" + jugador.getNombreUsuario()+"')";
        st.executeUpdate(insercion);
        cerrarElPuenteDeConexion();
    }
    
    // Getters y Setters
    
    /**
     * Obtiene la conexión activa a la base de datos.
     * 
     * @return La conexión actual a la base de datos
     */
    public Connection getCon() {
        return con;
    }

    /**
     * Establece la conexión a la base de datos.
     * 
     * @param con La conexión a asignar
     */
    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * Obtiene el Statement actual para consultas SQL.
     * 
     * @return El Statement activo
     */
    public Statement getSt() {
        return st;
    }

    /**
     * Establece el Statement para consultas SQL.
     * 
     * @param st El Statement a asignar
     */
    public void setSt(Statement st) {
        this.st = st;
    }

    /**
     * Obtiene el ResultSet actual con resultados de consultas.
     * 
     * @return El ResultSet con los datos de la última consulta
     */
    public ResultSet getRs() {
        return rs;
    }

    /**
     * Establece el ResultSet con resultados de consultas.
     * 
     * @param rs El ResultSet a asignar
     */
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    /**
     * Obtiene el objeto de gestión de conexión SQL.
     * 
     * @return El objeto ConexionSQL actual
     */
    public ConexionSQL getCnxSQL() {
        return cnxSQL;
    }

    /**
     * Establece el objeto de gestión de conexión SQL.
     * 
     * @param cnxSQL El objeto ConexionSQL a asignar
     */
    public void setCnxSQL(ConexionSQL cnxSQL) {
        this.cnxSQL = cnxSQL;
    }
}