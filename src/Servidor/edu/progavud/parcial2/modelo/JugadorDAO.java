package Servidor.edu.progavud.parcial2.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author carlosmamut1
 */
public class JugadorDAO {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private ConexionSQL cnxSQL;

    /**
     * Constructor por defecto que inicializa las referencias de conexión a
     * null.
     */
    public JugadorDAO() {
        con = null;
        st = null;
        rs = null;
        cnxSQL = new ConexionSQL();
    }

    /**
     * Abre la conexión con la base de datos y crea un {@link Statement} para
     * ejecutar consultas.
     *
     * @throws SQLException si ocurre un error al establecer la conexión
     */
    public void abrirElPuenteDeConexion() throws SQLException {
        con = (Connection) cnxSQL.getConexion();
        st = con.createStatement();
    }

    /**
     * Cierra el {@link Statement} y desconecta la conexión activa con la base
     * de datos.
     *
     * @throws SQLException si ocurre un error al cerrar los recursos
     */
    public void cerrarElPuenteDeConexion() throws SQLException {
        st.close();
        cnxSQL.desconectar();
    }
    
    /**
     * Consulta un jugador solo por contraseña (método original)
     */
    public JugadorVO consultarJugador(String contrasena) throws SQLException {
        JugadorVO jugador = null;
        String consulta = "SELECT * FROM jugadores WHERE contrasena = '" + contrasena + "'";

        abrirElPuenteDeConexion();
        rs = st.executeQuery(consulta);
        if (rs.next()) {
            jugador = new JugadorVO(rs.getString("contrasena"), rs.getString("nombreUsuario"));
        }
        cerrarElPuenteDeConexion();

        return jugador;
    }

    /**
     * Consulta un jugador por nombre de usuario Y contraseña
     * 
     * @param nombreUsuario el nombre de usuario a buscar
     * @param contrasena la contraseña a verificar
     * @return JugadorVO si encuentra coincidencia, null si no
     * @throws SQLException si ocurre un error en la consulta
     */
    public JugadorVO consultarJugadorPorUsuarioYContrasena(String nombreUsuario, String contrasena) throws SQLException {
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

    // Getters y Setters
    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public ConexionSQL getCnxSQL() {
        return cnxSQL;
    }

    public void setCnxSQL(ConexionSQL cnxSQL) {
        this.cnxSQL = cnxSQL;
    }
}