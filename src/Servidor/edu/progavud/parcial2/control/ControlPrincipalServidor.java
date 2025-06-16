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
        String[] datosDelSQL = new String[3];
        try {
            datosDelSQL = this.cnxPropiedadesDB.cargarFile(this.fachadaS.getvServidorChat().retribuirArchivo("ArchivoPropertiesSQL"));
        } catch (Exception ex) {
            this.fachadaS.getvServidorChat().mostrarError("Hubo un error al intentar cargar los datos para la CnxSQL");
        }

        this.clienteDAO.getCnxSQL().setUsuario(datosDelSQL[0]);
        this.clienteDAO.getCnxSQL().setContrasena(datosDelSQL[1]);
        this.clienteDAO.getCnxSQL().setURLBD(datosDelSQL[2]);
    }
        public boolean estaElJugador(int codigo) {

        JugadorVO clienteEncontrado = this.buscarJugador(codigo);
        if (clienteEncontrado != null) {
            return true;
        } else {
            return false;
        }

    }
    
    public JugadorVO buscarJugador(int codigo) {
        try {
            JugadorVO clienteEncontrado = this.clienteDAO.consultarJugador(codigo);
            if(clienteEncontrado != null) {
                return clienteEncontrado;
            }
        }
        catch(Exception ex) {
            this.fachadaS.getvServidorChat().mostrarError("Ha habido un error a la hora de buscar al gato");
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
        this.fachadaS = new FachadaServidor(this);
        this.cJuego = new ControlJuego(this);
        this.cServidor= new ControlServidor(this);
        this.cnxPropiedadesDB = new ConexionPropiedadesDB();
        cargarDatosALaConexionSQL();
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
