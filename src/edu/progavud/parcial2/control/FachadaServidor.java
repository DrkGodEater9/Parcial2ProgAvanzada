package edu.progavud.parcial2.control;

import edu.progavud.parcial2.vista.VentanaServidorJuego;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author a
 */
public class FachadaServidor implements ActionListener {

    private ControlServidor cServidor;
    private VentanaServidorJuego vServidorJuego;

    public FachadaServidor(ControlServidor cServidor) {
        this.cServidor = cServidor;
        this.vServidorJuego = new VentanaServidorJuego(this);
        this.vServidorJuego.setVisible(true);

        this.vServidorJuego.vServJuegoBtn1.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn2.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn3.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn4.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn5.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn6.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn7.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn8.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn9.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn10.addActionListener(this);
        
        this.vServidorJuego.vServJuegoBtn11.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn12.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn13.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn14.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn15.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn16.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn17.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn18.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn19.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn20.addActionListener(this);
        
        this.vServidorJuego.vServJuegoBtn21.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn22.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn23.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn24.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn25.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn26.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn27.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn28.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn29.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn30.addActionListener(this);
        
        this.vServidorJuego.vServJuegoBtn31.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn32.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn33.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn34.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn35.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn36.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn37.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn38.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn39.addActionListener(this);
        this.vServidorJuego.vServJuegoBtn40.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        //no hay boton cerrar jaja, aun
        if (e.getActionCommand().equals("cerrar")) {
            //this.menu.setVisible(false);
            //this.menu.dispose();
            System.exit(0);
        }
        if (e.getActionCommand().equals("1")) {
            this.vServidorJuego.vServJuegoBtn1.setVisible(false);
            System.out.println("Hola");
        }
        if (e.getActionCommand().equals("2")) {
            this.vServidorJuego.vServJuegoBtn2.setVisible(false);
        }
        if (e.getActionCommand().equals("3")) {
            this.vServidorJuego.vServJuegoBtn3.setVisible(false);
        }
        if (e.getActionCommand().equals("4")) {
            this.vServidorJuego.vServJuegoBtn4.setVisible(false);
        }
        if (e.getActionCommand().equals("5")) {
            this.vServidorJuego.vServJuegoBtn5.setVisible(false);
        }
        if (e.getActionCommand().equals("6")) {
            this.vServidorJuego.vServJuegoBtn6.setVisible(false);
        }
        if (e.getActionCommand().equals("7")) {
            this.vServidorJuego.vServJuegoBtn7.setVisible(false);
        }
        if (e.getActionCommand().equals("8")) {
            this.vServidorJuego.vServJuegoBtn8.setVisible(false);
        }
        if (e.getActionCommand().equals("9")) {
            this.vServidorJuego.vServJuegoBtn9.setVisible(false);
        }
        if (e.getActionCommand().equals("10")) {
            this.vServidorJuego.vServJuegoBtn10.setVisible(false);
        }
        if (e.getActionCommand().equals("11")) {
            this.vServidorJuego.vServJuegoBtn11.setVisible(false);
        }
        if (e.getActionCommand().equals("12")) {
            this.vServidorJuego.vServJuegoBtn12.setVisible(false);
        }
        if (e.getActionCommand().equals("13")) {
            this.vServidorJuego.vServJuegoBtn13.setVisible(false);
        }
        if (e.getActionCommand().equals("14")) {
            this.vServidorJuego.vServJuegoBtn14.setVisible(false);
        }
        if (e.getActionCommand().equals("15")) {
            this.vServidorJuego.vServJuegoBtn15.setVisible(false);
        }
        if (e.getActionCommand().equals("16")) {
            this.vServidorJuego.vServJuegoBtn16.setVisible(false);
        }
        if (e.getActionCommand().equals("17")) {
            this.vServidorJuego.vServJuegoBtn17.setVisible(false);
        }
        if (e.getActionCommand().equals("18")) {
            this.vServidorJuego.vServJuegoBtn18.setVisible(false);
        }
        if (e.getActionCommand().equals("19")) {
            this.vServidorJuego.vServJuegoBtn19.setVisible(false);
        }
        if (e.getActionCommand().equals("20")) {
            this.vServidorJuego.vServJuegoBtn20.setVisible(false);
        }
        if (e.getActionCommand().equals("21")) {
            this.vServidorJuego.vServJuegoBtn21.setVisible(false);
        }
        if (e.getActionCommand().equals("22")) {
            this.vServidorJuego.vServJuegoBtn22.setVisible(false);
        }
        if (e.getActionCommand().equals("23")) {
            this.vServidorJuego.vServJuegoBtn23.setVisible(false);
        }
        if (e.getActionCommand().equals("24")) {
            this.vServidorJuego.vServJuegoBtn24.setVisible(false);
        }
        if (e.getActionCommand().equals("25")) {
            this.vServidorJuego.vServJuegoBtn25.setVisible(false);
        }
        if (e.getActionCommand().equals("26")) {
            this.vServidorJuego.vServJuegoBtn26.setVisible(false);
        }
        if (e.getActionCommand().equals("27")) {
            this.vServidorJuego.vServJuegoBtn27.setVisible(false);
        }
        if (e.getActionCommand().equals("28")) {
            this.vServidorJuego.vServJuegoBtn28.setVisible(false);
        }
        if (e.getActionCommand().equals("29")) {
            this.vServidorJuego.vServJuegoBtn29.setVisible(false);
        }
        if (e.getActionCommand().equals("30")) {
            this.vServidorJuego.vServJuegoBtn30.setVisible(false);
        }
        if (e.getActionCommand().equals("31")) {
            this.vServidorJuego.vServJuegoBtn31.setVisible(false);
        }
        if (e.getActionCommand().equals("32")) {
            this.vServidorJuego.vServJuegoBtn32.setVisible(false);
        }
        if (e.getActionCommand().equals("33")) {
            this.vServidorJuego.vServJuegoBtn33.setVisible(false);
        }
        if (e.getActionCommand().equals("34")) {
            this.vServidorJuego.vServJuegoBtn34.setVisible(false);
        }
        if (e.getActionCommand().equals("35")) {
            this.vServidorJuego.vServJuegoBtn35.setVisible(false);
        }
        if (e.getActionCommand().equals("36")) {
            this.vServidorJuego.vServJuegoBtn36.setVisible(false);
        }
        if (e.getActionCommand().equals("37")) {
            this.vServidorJuego.vServJuegoBtn37.setVisible(false);
        }
        if (e.getActionCommand().equals("38")) {
            this.vServidorJuego.vServJuegoBtn38.setVisible(false);
        }
        if (e.getActionCommand().equals("39")) {
            this.vServidorJuego.vServJuegoBtn39.setVisible(false);
        }
        if (e.getActionCommand().equals("40")) {
            this.vServidorJuego.vServJuegoBtn40.setVisible(false);
        }
    }

}
