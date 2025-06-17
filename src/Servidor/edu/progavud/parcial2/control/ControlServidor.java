package Servidor.edu.progavud.parcial2.control;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JPanel;

/**
 * Controlador del servidor - Contiene toda la lógica de negocio y validaciones.
 */
public class ControlServidor {
    
    private ControlPrincipalServidor cPrincipal;
    private ServerSocket serverSocket;
    private boolean servidorActivo;
    private String puertoServ;
    private Map<String, HiloClienteServidor> clientesConectados;
    private Map<String, JPanel> ventanasChat;
    
    public ControlServidor(ControlPrincipalServidor cPrincipal) {
        this.cPrincipal = cPrincipal;
        this.clientesConectados = new ConcurrentHashMap<>();
        this.ventanasChat = new ConcurrentHashMap<>();
        this.servidorActivo = false;
    }
    
    /**
     * Valida las credenciales de un cliente
     * 
     * @param nombreUsuario el nombre de usuario a validar
     * @param contrasena la contraseña a validar
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean validarCredenciales(String contrasena, String nombreUsuario) {
        return cPrincipal.getcJugador().validarJugador(contrasena, nombreUsuario);
    }
    
    /**
     * Controla la visibilidad de la ventana de juego según el número de clientes
     */
    private void controlarVentanaJuego() {
        int numClientes = clientesConectados.size();
        
        if (numClientes >= 2) {
            // Mostrar ventana de juego cuando hay 2 o más clientes
            cPrincipal.getFachadaS().mostrarVentanaJuego();
            
            // Notificar a todos los clientes que el juego puede comenzar
            enviarMensajeATodosLosClientes("¡Hay " + numClientes + " jugadores conectados! El juego puede comenzar.");
            
        } else {
            // Ocultar ventana de juego cuando hay menos de 2 clientes
            cPrincipal.getFachadaS().ocultarVentanaJuego();
            
            // Notificar a los clientes que necesitan más jugadores
            if (numClientes == 1) {
                enviarMensajeATodosLosClientes("Esperando un jugador más para comenzar el juego...");
            }
        }
    }
    
    /**
     * Envía un mensaje a todos los clientes conectados
     */
    private void enviarMensajeATodosLosClientes(String mensaje) {
        for (String nombreUsuario : clientesConectados.keySet()) {
            enviarMensajeACliente(nombreUsuario, mensaje);
        }
    }
    
    /**
     * Inicia el servidor.
     */
    public void iniciarServidor() throws IOException {
        if (servidorActivo) {
            throw new IllegalStateException("El servidor ya está activo");
        }
        
        try {
            serverSocket = new ServerSocket(Integer.parseInt(puertoServ));
            servidorActivo = true;
            
            // Actualizar vista
            cPrincipal.getFachadaS().getvServidorChat().establecerEstadoServidor(true);
            
            // Iniciar hilo para aceptar conexiones
            new Thread(this::aceptarConexiones).start();
            
        } catch (IOException e) {
            servidorActivo = false;
            throw new IOException("Error al iniciar servidor: " + e.getMessage());
        }
    }
    
    /**
     * Detiene el servidor.
     */
    public void detenerServidor() {
        try {
            servidorActivo = false;
            
            // Desconectar todos los clientes
            for (HiloClienteServidor hilo : clientesConectados.values()) {
                hilo.interrupt();
            }
            clientesConectados.clear();
            ventanasChat.clear();
            
            // Ocultar ventana de juego al detener servidor
            cPrincipal.getFachadaS().ocultarVentanaJuego();
            
            // Cerrar server socket
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            
            // Actualizar vista
            cPrincipal.getFachadaS().getvServidorChat().establecerEstadoServidor(false);
            cPrincipal.getFachadaS().getvServidorChat().actualizarContadorClientes(0);
            
        } catch (IOException e) {
            // Error silencioso al detener servidor
        }
    }
    
    /**
     * Acepta conexiones de clientes.
     */
    private void aceptarConexiones() {
        while (servidorActivo) {
            try {
                Socket socketCliente = serverSocket.accept();
                
                // Crear hilo para manejar cliente
                HiloClienteServidor hiloCliente = new HiloClienteServidor(socketCliente, this);
                hiloCliente.start();
                
            } catch (IOException e) {
                if (servidorActivo) {
                    // Error silencioso aceptando conexión
                }
            }
        }
    }
    
    /**
     * Registra un nuevo cliente conectado (solo se llama después de validar credenciales).
     */
    public void registrarCliente(String nombreUsuario, HiloClienteServidor hilo) {
        // Validaciones
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            return;
        }
        
        if (clientesConectados.containsKey(nombreUsuario)) {
            hilo.interrupt();
            return;
        }
        
        clientesConectados.put(nombreUsuario, hilo);
        
        // Crear ventana de chat para este cliente
        JPanel ventanaChat = cPrincipal.getFachadaS().getvServidorChat().crearVentanaChat(nombreUsuario);
        ventanasChat.put(nombreUsuario, ventanaChat);
        cPrincipal.getFachadaS().getvServidorChat().agregarVentanaCliente(ventanaChat);
        
        // Configurar listener para envío de mensajes
        cPrincipal.getFachadaS().configurarChatCliente(nombreUsuario, ventanaChat);
        
        // Actualizar vista
        cPrincipal.getFachadaS().getvServidorChat().actualizarContadorClientes(clientesConectados.size());
        
        // NUEVO: Controlar visibilidad de ventana de juego
        controlarVentanaJuego();
    }
    
    /**
     * Desregistra un cliente desconectado.
     */
    public void desregistrarCliente(String nombreUsuario) {
        if (nombreUsuario != null && clientesConectados.containsKey(nombreUsuario)) {
            clientesConectados.remove(nombreUsuario);
            
            // Remover ventana de chat
            JPanel ventanaChat = ventanasChat.remove(nombreUsuario);
            if (ventanaChat != null) {
                cPrincipal.getFachadaS().getvServidorChat().removerVentanaCliente(ventanaChat);
            }
            
            // Actualizar vista
            cPrincipal.getFachadaS().getvServidorChat().actualizarContadorClientes(clientesConectados.size());
            
            // NUEVO: Controlar visibilidad de ventana de juego
            controlarVentanaJuego();
        }
    }
    
    /**
     * Procesa mensaje recibido de un cliente.
     */
    public void procesarMensajeCliente(String nombreUsuario, String mensaje) {
        // Validaciones
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return;
        }
        
        if (mensaje.trim().length() > 500) {
            enviarMensajeACliente(nombreUsuario, "ERROR: Mensaje demasiado largo");
            return;
        }
        
        // Mostrar mensaje en la ventana del cliente
        JPanel ventanaChat = ventanasChat.get(nombreUsuario);
        if (ventanaChat != null) {
            cPrincipal.getFachadaS().getvServidorChat().mostrarMensajeEnChat(nombreUsuario, nombreUsuario + ": " + mensaje, ventanaChat);
        }
    }
    
    /**
     * Envía mensaje a un cliente específico.
     */
    public void enviarMensajeACliente(String nombreUsuario, String mensaje) {
        // Validaciones
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return;
        }
        
        if (mensaje.trim().length() > 500) {
            return;
        }
        
        HiloClienteServidor hilo = clientesConectados.get(nombreUsuario);
        if (hilo != null) {
            try {
                hilo.enviarMensaje(mensaje.trim());
                JPanel ventanaChat = ventanasChat.get(nombreUsuario);
                if (ventanaChat != null) {
                    cPrincipal.getFachadaS().getvServidorChat().mostrarMensajeEnChat(nombreUsuario, "Servidor: " + mensaje, ventanaChat);
                }
            } catch (IOException e) {
                // Error silencioso enviando mensaje
            }
        }
    }
    
    // Getters
    public ControlPrincipalServidor getcPrincipal() {
        return cPrincipal;
    }
    
    public boolean isServidorActivo() {
        return servidorActivo;
    }

    public String getPuertoServ() {
        return puertoServ;
    }

    public void setPuertoServ(String puertoServ) {
        this.puertoServ = puertoServ;
    }
    
    /**
     * Obtiene el número de clientes conectados
     */
    public int getNumeroClientesConectados() {
        return clientesConectados.size();
    }
}