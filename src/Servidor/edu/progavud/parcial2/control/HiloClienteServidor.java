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
            
            // Recibir contraseña como primer mensaje
            String contrasena = entrada.readUTF();
            
            // Validar credenciales en la base de datos
            if (!cServidor.validarCredenciales(nombreUsuario, contrasena)) {
                enviarMensaje("ERROR: Credenciales inválidas. Usuario o contraseña incorrectos. Conexión terminada.");
                Thread.sleep(1000); // Dar tiempo para que el mensaje llegue al cliente
                return; // Esto terminará el hilo y cerrará la conexión
            }
            
            // Si las credenciales son válidas, registrar el cliente
            cServidor.registrarCliente(nombreUsuario, this);
            
            // Enviar confirmación de conexión exitosa
            enviarMensaje("Conectado exitosamente al servidor. ¡Bienvenido " + nombreUsuario + "!");
            
            // Escuchar mensajes del cliente
            while (!Thread.currentThread().isInterrupted()) {
                String mensaje = entrada.readUTF();
                cServidor.procesarMensajeCliente(nombreUsuario, mensaje);
            }
            
        } catch (IOException e) {
            // Cliente se desconectó o error de comunicación
        } catch (InterruptedException e) {
            // Hilo interrumpido
        } finally {
            cerrarConexion();
            if (nombreUsuario != null) {
                cServidor.desregistrarCliente(nombreUsuario);
            }
        }
    }
    
    /**
     * Envía un mensaje al cliente.
     */
    public void enviarMensaje(String mensaje) throws IOException {
        if (salida != null) {
            salida.writeUTF(mensaje);
            salida.flush(); // Asegurar que el mensaje se envíe inmediatamente
        }
    }
    
    /**
     * Cierra la conexión con el cliente.
     */
    private void cerrarConexion() {
        try {
            if (entrada != null) {
                entrada.close();
            }
            if (salida != null) {
                salida.close();
            }
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