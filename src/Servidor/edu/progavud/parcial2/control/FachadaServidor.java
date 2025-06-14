package Servidor.edu.progavud.parcial2.control;

import Servidor.edu.progavud.parcial2.vista.VentanaServidorJuego;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 *
 * @author a
 */
public class FachadaServidor implements ActionListener {

    private ControlPrincipalServidor cPrincipalS;
    private VentanaServidorJuego vServidorJuego;
    
    public void activarActionListener() {
        JButton[] botones = this.vServidorJuego.getBotones();
        for(int i = 0; i < botones.length; i++) {
            botones[i].addActionListener(this);
        }
    }
    public void aparecerYDesaparecer(JButton button, int numButtonPresionado) {
        int[] arregloDePosiciones = this.cPrincipalS.getArregloClicksYPosiciones();
        JButton[] botones = this.vServidorJuego.getBotones();
        button.setVisible(false);
        if(arregloDePosiciones[1] == -1) {
            arregloDePosiciones[1] = numButtonPresionado;
        }
        else {
            arregloDePosiciones[2] = numButtonPresionado;
        }
        if(!this.cPrincipalS.validarSiHaAcertado() && arregloDePosiciones[0] % 2 == 0) {
            Timer timer = new Timer(800, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botones[arregloDePosiciones[1]].setVisible(true);
                    botones[arregloDePosiciones[2]].setVisible(true);
                    arregloDePosiciones[1] = -1;
                    arregloDePosiciones[2] = -1;
                }
            });
            timer.setRepeats(false); // Make sure the timer only runs once
            timer.start();
        }
        else if(arregloDePosiciones[0] % 2 == 0) {
            arregloDePosiciones[1] = -1;
            arregloDePosiciones[2] = -1;
        }
    }
    public FachadaServidor(ControlPrincipalServidor cPrincipalS) {
        this.cPrincipalS = cPrincipalS;
        this.vServidorJuego = new VentanaServidorJuego(this);
        this.vServidorJuego.setVisible(true);
        activarActionListener();
        
    }
 public void actionPerformed(ActionEvent e) {
    JButton[] botones = this.vServidorJuego.getBotones();

    if (e.getActionCommand().equals("cerrar")) {
        System.exit(0);
    }

    if (e.getActionCommand().equals("1")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[0], 0);
    }
    if (e.getActionCommand().equals("2")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[1], 1);
    }
    if (e.getActionCommand().equals("3")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[2], 2);
    }
    if (e.getActionCommand().equals("4")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[3], 3);
    }
    if (e.getActionCommand().equals("5")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[4], 4);
    }
    if (e.getActionCommand().equals("6")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[5], 5);
    }
    if (e.getActionCommand().equals("7")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[6], 6);
    }
    if (e.getActionCommand().equals("8")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[7], 7);
    }
    if (e.getActionCommand().equals("9")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[8], 8);
    }
    if (e.getActionCommand().equals("10")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[9], 9);
    }
    if (e.getActionCommand().equals("11")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[10], 10);
    }
    if (e.getActionCommand().equals("12")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[11], 11);
    }
    if (e.getActionCommand().equals("13")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[12], 12);
    }
    if (e.getActionCommand().equals("14")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[13], 13);
    }
    if (e.getActionCommand().equals("15")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[14], 14);
    }
    if (e.getActionCommand().equals("16")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[15], 15);
    }
    if (e.getActionCommand().equals("17")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[16], 16);
    }
    if (e.getActionCommand().equals("18")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[17], 17);
    }
    if (e.getActionCommand().equals("19")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[18], 18);
    }
    if (e.getActionCommand().equals("20")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[19], 19);
    }
    if (e.getActionCommand().equals("21")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[20], 20);
    }
    if (e.getActionCommand().equals("22")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[21], 21);
    }
    if (e.getActionCommand().equals("23")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[22], 22);
    }
    if (e.getActionCommand().equals("24")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[23], 23);
    }
    if (e.getActionCommand().equals("25")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[24], 24);
    }
    if (e.getActionCommand().equals("26")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[25], 25);
    }
    if (e.getActionCommand().equals("27")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[26], 26);
    }
    if (e.getActionCommand().equals("28")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[27], 27);
    }
    if (e.getActionCommand().equals("29")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[28], 28);
    }
    if (e.getActionCommand().equals("30")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[29], 29);
    }
    if (e.getActionCommand().equals("31")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[30], 30);
    }
    if (e.getActionCommand().equals("32")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[31], 31);
    }
    if (e.getActionCommand().equals("33")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[32], 32);
    }
    if (e.getActionCommand().equals("34")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[33], 33);
    }
    if (e.getActionCommand().equals("35")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[34], 34);
    }
    if (e.getActionCommand().equals("36")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[35], 35);
    }
    if (e.getActionCommand().equals("37")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[36], 36);
    }
    if (e.getActionCommand().equals("38")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[37], 37);
    }
    if (e.getActionCommand().equals("39")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[38], 38);
    }
    if (e.getActionCommand().equals("40")) {
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[39], 39);
    }
}
}

 


