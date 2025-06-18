package Servidor.edu.progavud.parcial2.control;

import Servidor.edu.progavud.parcial2.vista.VentanaServidorChat;
import Servidor.edu.progavud.parcial2.vista.VentanaServidorJuego;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author a
 */
public class FachadaServidor implements ActionListener {

    private ControlPrincipalServidor cPrincipalS;
    private VentanaServidorJuego vServidorJuego;
    private VentanaServidorChat vServidorChat;
    private boolean processingCards = false;

    public void activarActionListener() {
        JButton[] botones = this.vServidorJuego.getBotones();
        for (int i = 0; i < botones.length; i++) {
            botones[i].addActionListener(this);
        }
        this.vServidorJuego.vServJuegoBtnInicia.addActionListener(this);
    }
    public void aparecerYDesaparecer(JButton button, int numButtonPresionado) {
        String jugadorActual = this.cPrincipalS.getJugadorActual();
        if (jugadorActual != null) {
            String mensajeTurno = "Turno actual: " + jugadorActual;


        if (processingCards) {
            return;
        }
        int[] arregloDePosiciones = this.cPrincipalS.getArregloClicksYPosiciones();
        JButton[] botones = this.vServidorJuego.getBotones();
        button.setVisible(false);

        if (arregloDePosiciones[1] == -1) {
            arregloDePosiciones[1] = numButtonPresionado;
        } else {
            arregloDePosiciones[2] = numButtonPresionado;
            processingCards = true;
        }

        if (!this.cPrincipalS.validarSiHaAcertado() && arregloDePosiciones[0] % 2 == 0) {
            Timer timer = new Timer(800, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (arregloDePosiciones[1] >= 0 && arregloDePosiciones[1] < botones.length) {
                        botones[arregloDePosiciones[1]].setVisible(true);
                    }
                    if (arregloDePosiciones[2] >= 0 && arregloDePosiciones[2] < botones.length) {
                        botones[arregloDePosiciones[2]].setVisible(true);
                    }
                    arregloDePosiciones[1] = -1;
                    arregloDePosiciones[2] = -1;
                    processingCards = false;
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else if (arregloDePosiciones[0] % 2 == 0) {
            arregloDePosiciones[1] = -1;
            arregloDePosiciones[2] = -1;
            processingCards = false;
        }
        }
    }

    public void setearImagenes(int numero, int otroNumero) {
        this.vServidorJuego.agregarFoto(numero, otroNumero);
    }

    /**
     * Muestra la ventana de juego cuando hay suficientes jugadores
     */
    public void mostrarVentanaJuego() {
        if (vServidorJuego != null) {
            vServidorJuego.setVisible(true);
            vServidorJuego.toFront(); // Traer al frente
        }
    }

    /**
     * Oculta la ventana de juego cuando no hay suficientes jugadores
     */
    public void ocultarVentanaJuego() {
        if (vServidorJuego != null) {
            vServidorJuego.setVisible(false);
        }
    }

    public FachadaServidor(ControlPrincipalServidor cPrincipalS) {
        this.cPrincipalS = cPrincipalS;
        this.vServidorJuego = new VentanaServidorJuego(this);
        // NO HACER VISIBLE LA VENTANA DE JUEGO INICIALMENTE
        // this.vServidorJuego.setVisible(true); ← COMENTADO
        
        this.vServidorChat = new VentanaServidorChat(this);
        this.vServidorChat.setVisible(true);
        activarActionListener();
        this.vServidorChat.btnIniciar.addActionListener(this);
        this.vServidorChat.btnDetener.addActionListener(this);
    }

    /**
     * Configura los listeners para una ventana de chat de cliente específica.
     */
    public void configurarChatCliente(String nombreCliente, JPanel ventanaChat) {
        JButton btnEnviar = (JButton) ventanaChat.getClientProperty("btnEnviar");
        JTextField txtMensaje = (JTextField) ventanaChat.getClientProperty("txtMensaje");

        btnEnviar.addActionListener(this);
        txtMensaje.addActionListener(this);
    }

    /**
     * Procesa el envío de mensajes desde las ventanas de chat de clientes.
     */
    private void procesarEnvioMensajeCliente(ActionEvent evt) {
        // Buscar en todas las ventanas de chat cuál generó el evento
        for (int i = 0; i < vServidorChat.panelClientes.getComponentCount(); i++) {
            JPanel panelChat = (JPanel) vServidorChat.panelClientes.getComponent(i);
            JButton btnEnviar = (JButton) panelChat.getClientProperty("btnEnviar");
            JTextField txtMensaje = (JTextField) panelChat.getClientProperty("txtMensaje");

            if (evt.getSource() == btnEnviar || evt.getSource() == txtMensaje) {
                String nombreCliente = (String) panelChat.getClientProperty("nombreCliente");
                String mensaje = txtMensaje.getText();

                this.cPrincipalS.enviarMensajeACliente(nombreCliente, mensaje);
                txtMensaje.setText("");
                break;
            }
        }
    }

    // MODIFICAR SOLO ESTE MÉTODO EN FachadaServidor.java

public void actionPerformed(ActionEvent e) {
    JButton[] botones = this.vServidorJuego.getBotones();

    // NUEVA FUNCIONALIDAD - Iniciar juego y bloquear nuevos clientes
    if (e.getActionCommand().equals("iniciaJuego")) {
        // Verificar que hay al menos 2 jugadores
        if (this.cPrincipalS.getcServidor().getNumeroClientesConectados() >= 2) {
            this.cPrincipalS.iniciarJuego();
            
            // Deshabilitar el botón para evitar que se presione de nuevo
            this.vServidorJuego.vServJuegoBtnInicia.setEnabled(false);
            this.vServidorJuego.vServJuegoBtnInicia.setText("Juego Iniciado");
            
            // Mostrar mensaje en el servidor
            this.vServidorChat.mostrarError("Juego iniciado, No se aceptarán más jugadores");
        } else {
            this.vServidorChat.mostrarError("Se necesitan al menos 2 jugadores para iniciar el juego.");
        }
        return; // Importante: salir aquí para no procesar otros eventos
    }
    
    // NUEVA VALIDACIÓN - Verificar si el juego ya terminó
    if (this.cPrincipalS.isJuegoTerminado()) {
        this.vServidorChat.mostrarError("El juego ya ha terminado.");
        return;
    }
    
    
    if (processingCards) {
        return;
    }
    if (e.getActionCommand().equals("1")) {
        if (!botones[0].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[0], 0);
    }
    if (e.getActionCommand().equals("2")) {
        if (!botones[1].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[1], 1);
    }
    if (e.getActionCommand().equals("3")) {
        if (!botones[2].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[2], 2);
    }
    if (e.getActionCommand().equals("4")) {
        if (!botones[3].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[3], 3);
    }
    if (e.getActionCommand().equals("5")) {
        if (!botones[4].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[4], 4);
    }
    if (e.getActionCommand().equals("6")) {
        if (!botones[5].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[5], 5);
    }
    if (e.getActionCommand().equals("7")) {
        if (!botones[6].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[6], 6);
    }
    if (e.getActionCommand().equals("8")) {
        if (!botones[7].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[7], 7);
    }
    if (e.getActionCommand().equals("9")) {
        if (!botones[8].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[8], 8);
    }
    if (e.getActionCommand().equals("10")) {
        if (!botones[9].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[9], 9);
    }
    if (e.getActionCommand().equals("11")) {
        if (!botones[10].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[10], 10);
    }
    if (e.getActionCommand().equals("12")) {
        if (!botones[11].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[11], 11);
    }
    if (e.getActionCommand().equals("13")) {
        if (!botones[12].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[12], 12);
    }
    if (e.getActionCommand().equals("14")) {
        if (!botones[13].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[13], 13);
    }
    if (e.getActionCommand().equals("15")) {
        if (!botones[14].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[14], 14);
    }
    if (e.getActionCommand().equals("16")) {
        if (!botones[15].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[15], 15);
    }
    if (e.getActionCommand().equals("17")) {
        if (!botones[16].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[16], 16);
    }
    if (e.getActionCommand().equals("18")) {
        if (!botones[17].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[17], 17);
    }
    if (e.getActionCommand().equals("19")) {
        if (!botones[18].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[18], 18);
    }
    if (e.getActionCommand().equals("20")) {
        if (!botones[19].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[19], 19);
    }
    if (e.getActionCommand().equals("21")) {
        if (!botones[20].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[20], 20);
    }
    if (e.getActionCommand().equals("22")) {
        if (!botones[21].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[21], 21);
    }
    if (e.getActionCommand().equals("23")) {
        if (!botones[22].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[22], 22);
    }
    if (e.getActionCommand().equals("24")) {
        if (!botones[23].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[23], 23);
    }
    if (e.getActionCommand().equals("25")) {
        if (!botones[24].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[24], 24);
    }
    if (e.getActionCommand().equals("26")) {
        if (!botones[25].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[25], 25);
    }
    if (e.getActionCommand().equals("27")) {
        if (!botones[26].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[26], 26);
    }
    if (e.getActionCommand().equals("28")) {
        if (!botones[27].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[27], 27);
    }
    if (e.getActionCommand().equals("29")) {
        if (!botones[28].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[28], 28);
    }
    if (e.getActionCommand().equals("30")) {
        if (!botones[29].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[29], 29);
    }
    if (e.getActionCommand().equals("31")) {
        if (!botones[30].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[30], 30);
    }
    if (e.getActionCommand().equals("32")) {
        if (!botones[31].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[31], 31);
    }
    if (e.getActionCommand().equals("33")) {
        if (!botones[32].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[32], 32);
    }
    if (e.getActionCommand().equals("34")) {
        if (!botones[33].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[33], 33);
    }
    if (e.getActionCommand().equals("35")) {
        if (!botones[34].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[34], 34);
    }
    if (e.getActionCommand().equals("36")) {
        if (!botones[35].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[35], 35);
    }
    if (e.getActionCommand().equals("37")) {
        if (!botones[36].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[36], 36);
    }
    if (e.getActionCommand().equals("38")) {
        if (!botones[37].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[37], 37);
    }
    if (e.getActionCommand().equals("39")) {
        if (!botones[38].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[38], 38);
    }
    if (e.getActionCommand().equals("40")) {
        if (!botones[39].isVisible()) {
            return;
        }
        this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
        aparecerYDesaparecer(botones[39], 39);
    }

    // Iniciar servidor
    if (e.getActionCommand().equals("iniciarServ")) {
        try {
            this.cPrincipalS.iniciarServidor();
        } catch (Exception ex) {
            // Error silencioso
        }
    } 
    // Detener servidor
    else if (e.getActionCommand().equals("detenerServ")) {
        this.cPrincipalS.detenerServidor();
    } 
    // Envío de mensaje desde ventana de chat de cliente
    else {
        procesarEnvioMensajeCliente(e);
    }
}

    public VentanaServidorJuego getvServidorJuego() {
        return vServidorJuego;
    }

    public VentanaServidorChat getvServidorChat() {
        return vServidorChat;
    }

}