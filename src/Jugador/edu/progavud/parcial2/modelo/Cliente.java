package Jugador.edu.progavud.parcial2.modelo;

import java.io.*;
import java.net.*;

/**
 * Clase modelo que representa un cliente del sistema de chat individual.
 * Encapsula los datos y el estado de conexión del cliente, incluyendo
 * la información de red y los streams de comunicación con el servidor.
 * Esta clase sigue el patrón MVC como modelo de datos, sin contener
 * lógica de negocio ni validaciones.
 * 
 * @author carlosmamut1
 * @author Alex M  
 * @author batapop
 * @version 1.0
 */
public class Cliente {
    
    /** Dirección IP del servidor al cual se conectará el cliente */
    public String IP_SERVER;
    
    /** Puerto del servidor para establecer la conexión. Valor por defecto: 8080 */
    public int PUERTO_SERVIDOR = 8080;
    
    /** Nombre de usuario del cliente en el sistema de chat */
    private String nombreUsuario;
    
    /** Socket para la conexión TCP con el servidor */
    private Socket socket;
    
    /** Stream de entrada para recibir datos del servidor */
    private DataInputStream entrada;
    
    /** Stream de salida para enviar datos al servidor */
    private DataOutputStream salida;
    
    /** Estado de conexión del cliente (true = conectado, false = desconectado) */
    private boolean conectado;
    
    /**
     * Constructor parametrizado del cliente.
     * Inicializa un cliente con código y nombre de usuario específicos.
     * El estado de conexión se establece como falso por defecto.
     * 
     * @param codigo Código identificador del cliente (no utilizado actualmente)
     * @param nombreUsuario Nombre de usuario para identificar al cliente
     */
    public Cliente(int codigo, String nombreUsuario) {
        this.conectado = false;
    }
    
    /**
     * Constructor por defecto del cliente.
     * Crea una instancia de cliente sin parámetros iniciales.
     */
    public Cliente() {}
    
    /**
     * Obtiene el nombre de usuario del cliente.
     * 
     * @return El nombre de usuario actual del cliente
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    /**
     * Establece el nombre de usuario del cliente.
     * 
     * @param nombreUsuario El nuevo nombre de usuario para el cliente
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    /**
     * Obtiene el socket de conexión TCP del cliente.
     * 
     * @return El objeto Socket utilizado para la conexión con el servidor
     */
    public Socket getSocket() {
        return socket;
    }
    
    /**
     * Establece el socket de conexión TCP del cliente.
     * 
     * @param socket El objeto Socket para la conexión con el servidor
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    /**
     * Obtiene el stream de entrada de datos del cliente.
     * 
     * @return El DataInputStream para recibir datos del servidor
     */
    public DataInputStream getEntrada() {
        return entrada;
    }
    
    /**
     * Establece el stream de entrada de datos del cliente.
     * 
     * @param entrada El DataInputStream para recibir datos del servidor
     */
    public void setEntrada(DataInputStream entrada) {
        this.entrada = entrada;
    }
    
    /**
     * Obtiene el stream de salida de datos del cliente.
     * 
     * @return El DataOutputStream para enviar datos al servidor
     */
    public DataOutputStream getSalida() {
        return salida;
    }
    
    /**
     * Establece el stream de salida de datos del cliente.
     * 
     * @param salida El DataOutputStream para enviar datos al servidor
     */
    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }
    
    /**
     * Verifica si el cliente está conectado al servidor.
     * 
     * @return true si el cliente está conectado, false en caso contrario
     */
    public boolean isConectado() {
        return conectado;
    }
    
    /**
     * Establece el estado de conexión del cliente.
     * 
     * @param conectado true para marcar como conectado, false para desconectado
     */
    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    /**
     * Obtiene la dirección IP del servidor.
     * 
     * @return La dirección IP del servidor como String
     */
    public String getIP_SERVER() {
        return IP_SERVER;
    }

    /**
     * Establece la dirección IP del servidor.
     * 
     * @param IP_SERVER La nueva dirección IP del servidor
     */
    public void setIP_SERVER(String IP_SERVER) {
        this.IP_SERVER = IP_SERVER;
    }

    /**
     * Obtiene el puerto del servidor.
     * 
     * @return El número de puerto del servidor
     */
    public int getPUERTO_SERVIDOR() {
        return PUERTO_SERVIDOR;
    }

    /**
     * Establece el puerto del servidor.
     * 
     * @param PUERTO_SERVIDOR El nuevo número de puerto del servidor
     */
    public void setPUERTO_SERVIDOR(int PUERTO_SERVIDOR) {
        this.PUERTO_SERVIDOR = PUERTO_SERVIDOR;
    }
}