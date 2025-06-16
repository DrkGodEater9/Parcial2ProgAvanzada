package Servidor.edu.progavud.parcial2.control;
import Servidor.edu.progavud.parcial2.modelo.ConexionPropiedadesDB;
import Servidor.edu.progavud.parcial2.modelo.JugadorDAO;
import Servidor.edu.progavud.parcial2.modelo.JugadorVO;

/**
 *
 * @author a
 */
public class ControlPrincipalServidor {
    private FachadaServidor fachadaS;
    private ControlJuego cJuego;
    private ControlServidor cServidor;
    private ConexionPropiedadesDB cnxPropiedadesDB;
    private JugadorDAO clienteDAO;
    
    private int[] arregloClicksYPosiciones;
    
    public void cargarDatosALaConexionSQL() {
        String[] datosDelServer = new String[3];
        try {
            datosDelServer = this.cnxPropiedadesDB.cargarFile(this.fachadaS.getvServidorChat().retribuirArchivo("ArchivoPropertiesServer"));
        } catch (Exception ex) {
            this.fachadaS.getvServidorChat().mostrarError("Hubo un error al intentar cargar los datos para la CnxSQL");
        }

        this.clienteDAO.getCnxSQL().setUsuario(datosDelServer[0]);
        this.clienteDAO.getCnxSQL().setContrasena(datosDelServer[1]);
        this.clienteDAO.getCnxSQL().setURLBD(datosDelServer[2]);
        this.cServidor.setPuertoServ(datosDelServer[3]);
    }

    /**
     * Valida las credenciales de un jugador (usuario y contraseña)
     * 
     * @param nombreUsuario el nombre de usuario a validar
     * @param contrasena la contraseña a validar
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean validarJugador(String nombreUsuario, String contrasena) {
        try {
            JugadorVO jugadorEncontrado = this.clienteDAO.consultarJugadorPorUsuarioYContrasena(nombreUsuario, contrasena);
            return jugadorEncontrado != null;
        } catch(Exception ex) {
            this.fachadaS.getvServidorChat().mostrarError("Error al validar jugador en la base de datos: " + ex.getMessage());
            return false;
        }
    }

    public boolean estaElJugador(String contrasena) {
        JugadorVO clienteEncontrado = this.buscarJugador(contrasena);
        if (clienteEncontrado != null) {
            return true;
        } else {
            return false;
        }
    }
    
    public JugadorVO buscarJugador(String contrasena) {
        try {
            JugadorVO clienteEncontrado = this.clienteDAO.consultarJugador(contrasena);
            if(clienteEncontrado != null) {
                return clienteEncontrado;
            }
        }
        catch(Exception ex) {
            this.fachadaS.getvServidorChat().mostrarError("Ha habido un error a la hora de buscar al jugador");
        }
        return null;
    }

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
    
    public ControlPrincipalServidor() {
        this.clienteDAO = new JugadorDAO(); // Inicializar antes de usar
        this.cnxPropiedadesDB = new ConexionPropiedadesDB();
        this.fachadaS = new FachadaServidor(this);
        this.cServidor= new ControlServidor(this);
        cargarDatosALaConexionSQL();
        
        
        this.cJuego = new ControlJuego(this);
        
        
        arregloClicksYPosiciones = new int[]{0,-1,-1};
        this.cJuego.setearPosicionesIniciales();
        this.setearImagenesEnControl();
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
}