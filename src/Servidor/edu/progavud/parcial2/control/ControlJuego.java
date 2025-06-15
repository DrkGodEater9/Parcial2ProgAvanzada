package Servidor.edu.progavud.parcial2.control;

import java.util.Random;

/**
 *
 * @author carlosmamut1
 */
public class ControlJuego {
    private ControlPrincipalServidor cServidorS;
    private int[] posiciones;

    

    
    public void setearPosicionesIniciales() {
        for(int i = 0; i < posiciones.length/2; i++) {
            posiciones[i]=i;
            posiciones[posiciones.length - 1 - i] = i;
        }
        setearRandomPosiciones();
    }
    
    public void setearRandomPosiciones() {
        Random rand = new Random();
        for (int i = 0; i < posiciones.length; i++) {
            int randomIndexToSwap = rand.nextInt(posiciones.length);
            int temp = posiciones[randomIndexToSwap];
            posiciones[randomIndexToSwap] = posiciones[i];
            posiciones[i] = temp;
        }
    }
    
    public int[] getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(int[] posiciones) {
        this.posiciones = posiciones;
    }

    public ControlJuego(ControlPrincipalServidor cServidorS) {
        this.cServidorS = cServidorS;
        this.posiciones= new int[40];
    }
    
    
    
}
