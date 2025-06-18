package Servidor.edu.progavud.parcial2.control;
import Servidor.edu.progavud.parcial2.modelo.ConexionPropiedades;
import Servidor.edu.progavud.parcial2.modelo.ConexionPropiedadesDB;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author a
 */
public class ControlPrincipalServidor {
    private FachadaServidor fachadaS;
    private ControlJugador cJugador;
    private ControlJuego cJuego;
    private ControlServidor cServidor;
    private ConexionPropiedadesDB cnxPropiedadesDB;
    private ConexionPropiedades cnxPropiedades;
    
    private int[] arregloClicksYPosiciones;
    
    public void cargarDatosALaConexionSQL() {
        String[] datosDelServer = new String[4];
        try {
            datosDelServer = this.cnxPropiedadesDB.cargarFile(this.fachadaS.getvServidorChat().retribuirArchivo("ArchivoPropertiesServer"));
        } catch (Exception ex) {
            this.fachadaS.getvServidorChat().mostrarError("Hubo un error al intentar cargar los datos para la CnxSQL");
        }

        this.cJugador.getJugadorDAO().getCnxSQL().setUsuario(datosDelServer[0]);
        this.cJugador.getJugadorDAO().getCnxSQL().setContrasena(datosDelServer[1]);
        this.cJugador.getJugadorDAO().getCnxSQL().setURLBD(datosDelServer[2]);
        this.cServidor.setPuertoServ(datosDelServer[3]);
    }

    /**
     * Valida las credenciales de un jugador (usuario y contraseña)
     * 
     * @param nombreUsuario el nombre de usuario a validar
     * @param contrasena la contraseña a validar
     * @return true si las credenciales son válidas, false en caso contrario
     */

    public void setearImagenesEnControl() {
        int[] posiciones = this.cJuego.getPosiciones();
        for(int i = 0; i < posiciones.length; i++) {
           this.fachadaS.setearImagenes(posiciones[i],i);
        }
    }

    public boolean validarSiHaAcertado() {
        if(arregloClicksYPosiciones[0] % 2 == 0 && this.cJuego.getPosiciones()[arregloClicksYPosiciones[1]] == this.cJuego.getPosiciones()[arregloClicksYPosiciones[2]]) {
            //getAciertos++
            return true;
        }
        return false;
    }

    public int[] getArregloClicksYPosiciones() {
        return arregloClicksYPosiciones;
    }

    public void setArregloClicksYPosiciones(int[] arregloClicksYPosiciones) {
        this.arregloClicksYPosiciones = arregloClicksYPosiciones;
    }
    
    /**
     * NUEVO MÉTODO - Inicia el juego y bloquea nuevos clientes
     */
    public void iniciarJuego() {
        this.cServidor.iniciarJuego();
    }
    
    public ControlPrincipalServidor() {
        this.cnxPropiedadesDB = new ConexionPropiedadesDB();
        this.fachadaS = new FachadaServidor(this);
        this.cServidor= new ControlServidor(this);
        this.cJugador= new ControlJugador(this);
        cargarDatosALaConexionSQL();
        this.cnxPropiedades = new ConexionPropiedades();
        metodoCrearJugadoresDeProperties();
        
        this.cJuego = new ControlJuego(this);

        
        arregloClicksYPosiciones = new int[]{0,-1,-1};
        this.cJuego.setearPosicionesIniciales();
        setearImagenesEnControl();
    }
    
    public void metodoCrearJugadoresDeProperties() {
        try {
            String link = this.fachadaS.getvServidorChat().retribuirArchivo("ArchivoPropiedadesJugadores");
            String[] datosRetribuidosDeJugador = this.cnxPropiedades.cargarFile(link);
            for (int i = 0; i < (datosRetribuidosDeJugador.length) / 2; i++) {
                String contrasena = datosRetribuidosDeJugador[2 * i]; 
                String nombreUsuario= datosRetribuidosDeJugador[(2 * i) + 1];
                this.cJugador.crearJugador(contrasena,nombreUsuario);
                this.cJugador.getJugadorDAO().insertarDatosDeLosJugadores(this.cJugador.getJugador());
                
            }
        } catch (SQLIntegrityConstraintViolationException exp) {
            this.fachadaS.getvServidorChat().mostrarError("Alguno de los jugadores del archivo propiedades ya está en la base de datos");
        } catch (IOException | SQLException ex) {
            this.fachadaS.getvServidorChat().mostrarError("No se pudieron rescatar los datos del jugador");
        }
    }
    public void enviarMensajeACliente(String nombreUsuario, String mensaje) {
        this.cServidor.enviarMensajeACliente(nombreUsuario, mensaje);
    }
    
    /**
     * Inicia el servidor de comunicaciones.
     */
    public void iniciarServidor() throws Exception {
        this.cServidor.iniciarServidor();
    }
    
    /**
     * Detiene el servidor de comunicaciones.
     */
    public void detenerServidor() {
        this.cServidor.detenerServidor();
    }

    public FachadaServidor getFachadaS() {
        return fachadaS;
    }

    public ControlJugador getcJugador() {
        return cJugador;
    }

    public void setcJugador(ControlJugador cJugador) {
        this.cJugador = cJugador;
    }
    
    /**
     * NUEVO GETTER - Para acceder al controlador del servidor
     */
    public ControlServidor getcServidor() {
        return cServidor;
    }
    
}