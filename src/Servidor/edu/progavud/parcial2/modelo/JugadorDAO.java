/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        con = (Connection) cnxSQL.getConexion(); //Pide la coneccion
        st = con.createStatement(); //Crea la instruccion para ser ejecutada
    }

    /**
     * Cierra el {@link Statement} y desconecta la conexión activa con la base
     * de datos.
     *
     * @throws SQLException si ocurre un error al cerrar los recursos
     */
    public void cerrarElPuenteDeConexion() throws SQLException {
        st.close(); //Se cierra el statement (la instruccion para ser ejecutada)
        cnxSQL.desconectar(); //(Se desconecta la conexion, lo que genera que el puente de conexion se haya cerrado
    }
    
    public JugadorVO consultarJugador(int codigo) throws SQLException {
        JugadorVO jugador = null;
        String consulta = "SELECT * FROM gatos WHERE codigo =" + codigo + "";
        //consultaEspecifica es la consulta que vamos a hacer, dependiendo de con que argumento queremos consultar;

        abrirElPuenteDeConexion();
        rs = st.executeQuery(consulta); //Ejecuta la instruccion creada (statement)
        if (rs.next()) {
            jugador = new JugadorVO(rs.getInt("codigo"), rs.getString("nombreUsuario"));
            //Se crea el gato con los datos retribuidos de la tabla
        }
        cerrarElPuenteDeConexion();

        return jugador;
    }

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
