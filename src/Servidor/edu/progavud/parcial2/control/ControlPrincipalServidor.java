package Servidor.edu.progavud.parcial2.control;

/**
 *
 * @author a
 */
public class ControlPrincipalServidor {
    private FachadaServidor fachadaS;
    private ControlJuego cJuego;
    private ControlServidor cServidor;
    private int[] arregloClicksYPosiciones;
    
    
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
