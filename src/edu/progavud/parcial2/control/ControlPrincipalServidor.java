package edu.progavud.parcial2.control;

/**
 *
 * @author a
 */
public class ControlPrincipalServidor {
    private FachadaServidor fachadaS;
    private ControlJuego cJuego;
    private int[] arregloClicksYPosiciones;
    
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
        arregloClicksYPosiciones = new int[]{0,-1,-1};
        this.cJuego.setearPosicionesIniciales();
        
    }
    
    
}
