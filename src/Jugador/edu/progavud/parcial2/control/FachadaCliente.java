package Jugador.edu.progavud.parcial2.control;

import Jugador.edu.progavud.parcial2.vista.VentanaCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Fachada del cliente - Maneja eventos de la interfaz.
 * Implementa el patrón Facade siguiendo la estructura del proyecto original.
 */
public class FachadaCliente implements ActionListener {
    
    private ControlCliente cCliente;
    private VentanaCliente vCliente;
    
    public FachadaCliente(ControlCliente cCliente) throws IOException {
        this.cCliente = cCliente;
        this.vCliente = new VentanaCliente();
        configurarListeners();
        vCliente.setVisible(true);
    }
    
    private void configurarListeners() {
        vCliente.btnConectar.addActionListener(this);
        vCliente.btnDesconectar.addActionListener(this);
        vCliente.btnEnviar.addActionListener(this);
        vCliente.txtMensaje.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == vCliente.btnConectar) {
                procesarConexion();
            } else if (e.getSource() == vCliente.btnDesconectar) {
                procesarDesconexion();
            } else if (e.getSource() == vCliente.btnEnviar || 
                       e.getSource() == vCliente.txtMensaje) {
                procesarEnvioMensaje();
            }
        } catch (Exception ex) {
            vCliente.mostrarError(ex.getMessage());
        }
    }
    
    private void procesarConexion() throws IOException {
        String nombre = vCliente.txtNombre.getText();
        cCliente.conectarAlServidor(nombre);
    }
    
    private void procesarDesconexion() {
        cCliente.desconectarDelServidor();
    }
    
    private void procesarEnvioMensaje() throws IOException {
        String mensaje = vCliente.txtMensaje.getText();
        cCliente.enviarMensaje(mensaje);
    }
    
    public VentanaCliente getVentanaCliente() {
        return vCliente;
    }
}
