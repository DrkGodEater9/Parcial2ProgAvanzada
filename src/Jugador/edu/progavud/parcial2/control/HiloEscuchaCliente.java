package Jugador.edu.progavud.parcial2.control;

import java.io.*;

/**
 * Hilo para escuchar mensajes del servidor.
 */
public class HiloEscuchaCliente extends Thread {
    
    private DataInputStream entrada;
    private ControlCliente controlador;
    
    public HiloEscuchaCliente(DataInputStream entrada, ControlCliente controlador) {
        this.entrada = entrada;
        this.controlador = controlador;
    }
    
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String mensaje = entrada.readUTF();
                controlador.procesarMensajeRecibido(mensaje);
            }
        } catch (IOException e) {
            if (!Thread.currentThread().isInterrupted()) {
                controlador.manejarPerdidaConexion();
            }
        }
    }
}
