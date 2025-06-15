package Servidor.edu.progavud.parcial2.control;

import java.io.*;
import java.net.*;

/**
 * Hilo para manejar cada cliente conectado al servidor.
 */
public class HiloClienteServidor extends Thread {
    
    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private ControlServidor cServidor;
    private String nombreUsuario;
    
    public HiloClienteServidor(Socket socket, ControlServidor cServidor) {
        this.socket = socket;
        this.cServidor = cServidor;
    }
    
    @Override
    public void run() {
        try {
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            
            // Recibir nombre del usuario
            nombreUsuario = entrada.readUTF();
            cServidor.registrarCliente(nombreUsuario, this);
            
            // Escuchar mensajes del cliente
            while (!Thread.currentThread().isInterrupted()) {
                String mensaje = entrada.readUTF();
                cServidor.procesarMensajeCliente(nombreUsuario, mensaje);
            }
            
        } catch (IOException e) {
            // Cliente se desconectó
        } finally {
            cerrarConexion();
            cServidor.desregistrarCliente(nombreUsuario);
        }
    }
    
    /**
     * Envía un mensaje al cliente.
     */
    public void enviarMensaje(String mensaje) throws IOException {
        if (salida != null) {
            salida.writeUTF(mensaje);
        }
    }
    
    /**
     * Cierra la conexión con el cliente.
     */
    private void cerrarConexion() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            // Ignorar errores al cerrar
        }
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
