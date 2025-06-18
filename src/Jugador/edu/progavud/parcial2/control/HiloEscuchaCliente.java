package Jugador.edu.progavud.parcial2.control;

import java.io.*;

/**
 * Hilo dedicado para escuchar mensajes del servidor de forma asíncrona.
 * 
 * Esta clase extiende Thread para proporcionar comunicación no bloqueante
 * entre el cliente y el servidor. Se ejecuta en un hilo separado del hilo
 * principal de la interfaz gráfica, permitiendo que el cliente pueda recibir
 * mensajes del servidor mientras mantiene la interfaz responsiva.
 * 
 * El hilo permanece en ejecución continua leyendo mensajes del servidor
 * hasta que sea interrumpido o se pierda la conexión. Cuando recibe un
 * mensaje, lo delega al controlador para su procesamiento y visualización.
 * 
 * @author carlosmamut1
 * @author Alex M
 * @author batapop
 * @version 1.0
 */
public class HiloEscuchaCliente extends Thread {
    
    /** Stream de entrada para recibir datos del servidor */
    private DataInputStream entrada;
    
    /** Referencia al controlador del cliente para delegar el procesamiento de mensajes */
    private ControlCliente cCliente;
    
    /**
     * Constructor del hilo de escucha del cliente.
     * 
     * Inicializa el hilo con el stream de entrada del servidor y la referencia
     * al controlador que procesará los mensajes recibidos. Este constructor
     * prepara el hilo para comenzar a escuchar mensajes una vez que se inicie.
     * 
     * @param entrada El DataInputStream conectado al servidor para recibir mensajes
     * @param cCliente El controlador del cliente que procesará los mensajes recibidos
     */
    public HiloEscuchaCliente(DataInputStream entrada, ControlCliente controlador) {
        this.entrada = entrada;
        this.cCliente = controlador;
    }
    
    /**
     * Método principal del hilo que ejecuta el bucle de escucha de mensajes.
     * 
     * Este método se ejecuta cuando el hilo es iniciado mediante start().
     * Mantiene un bucle continuo leyendo mensajes del servidor hasta que:
     * - El hilo sea interrumpido externamente
     * - Ocurra una excepción de E/O (pérdida de conexión)
     * 
     * Cada mensaje recibido es inmediatamente delegado al controlador para
     * su procesamiento. Si ocurre una pérdida de conexión y el hilo no fue
     * interrumpido intencionalmente, notifica al controlador sobre la pérdida
     * de conexión para que pueda manejar la situación apropiadamente.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String mensaje = entrada.readUTF();
                cCliente.procesarMensajeRecibido(mensaje);
            }
        } catch (IOException e) {
            if (!Thread.currentThread().isInterrupted()) {
                cCliente.manejarPerdidaConexion();
            }
        }
    }
}