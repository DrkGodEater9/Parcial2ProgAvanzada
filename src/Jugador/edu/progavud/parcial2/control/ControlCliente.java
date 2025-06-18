package Jugador.edu.progavud.parcial2.control;

import Jugador.edu.progavud.parcial2.modelo.Cliente;
import Jugador.edu.progavud.parcial2.modelo.ConexionPropiedades;
import java.io.*;
import java.net.*;

/**
 * Controlador principal del cliente que contiene toda la lógica de negocio y validaciones.
 * 
 * Esta clase implementa el patrón MVC como controlador, encargándose de:
 * - Gestionar las conexiones con el servidor
 * - Validar datos de entrada del usuario
 * - Coordinar la comunicación entre modelo y vista
 * - Manejar excepciones y errores de red
 * - Controlar el ciclo de vida de los hilos de comunicación
 * 
 * Actúa como intermediario entre la interfaz de usuario (fachada) y los datos
 * del cliente (modelo), asegurando que todas las operaciones cumplan con las
 * reglas de negocio establecidas para el sistema de chat.
 * 
 * @author carlosmamut1
 * @author Alex M
 * @author batapop
 * @version 1.0
 */
public class ControlCliente {
    
    /** Modelo que contiene los datos y estado del cliente */
    private Cliente cliente;
    
    /** Fachada para la comunicación con la interfaz de usuario */
    private FachadaCliente fachada;
    
    /** Hilo dedicado para escuchar mensajes del servidor */
    private HiloEscuchaCliente hiloEscucha;
    
    /** Utilidad para cargar configuración de conexión desde archivos properties */
    private ConexionPropiedades cnxPropiedades;
    
    /**
     * Constructor del controlador del cliente.
     * 
     * Inicializa todos los componentes necesarios para el funcionamiento del cliente:
     * - Crea la instancia del modelo Cliente
     * - Inicializa la fachada que maneja la interfaz
     * - Configura el manejador de propiedades de conexión
     * 
     * El constructor establece la arquitectura MVC del sistema y prepara
     * el cliente para poder establecer conexiones con el servidor.
     */
    public ControlCliente() throws IOException {
        this.cliente = new Cliente();
        this.fachada = new FachadaCliente(this);
        this.cnxPropiedades = new ConexionPropiedades();
    }

    /**
     * Establece conexión con el servidor utilizando el nombre de usuario proporcionado.
     * 
     * Este método realiza validaciones exhaustivas del nombre de usuario,
     * carga la configuración del servidor desde archivos properties,
     * establece la conexión TCP y configura los streams de comunicación.
     * También inicia el hilo de escucha para recibir mensajes del servidor.
     * 
     * Validaciones realizadas:
     * - Nombre no nulo ni vacío
     * - Longitud máxima de 20 caracteres
     * - No existe conexión previa activa
     * 
     * @param nombreUsuario El nombre de usuario para identificarse en el servidor
     */
    public void conectarAlServidor(String nombreUsuario) throws IOException {
        // Validaciones
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
        
        if (nombreUsuario.trim().length() > 20) {
            throw new IllegalArgumentException("El nombre de usuario no puede exceder 20 caracteres");
        }
        
        if (cliente.isConectado()) {
            throw new IllegalStateException("Ya existe una conexión activa");
        }
        
        try {
            String[] datosServ = cnxPropiedades.cargarFile(this.fachada.getVentanaCliente().retribuirArchivo("ArchivoPropertiesDerlServidor"));
            cliente.setIP_SERVER(datosServ[0]);
            cliente.setPUERTO_SERVIDOR(Integer.parseInt(datosServ[1]));
            // Crear conexión
            Socket socket;
            socket = new Socket(cliente.getIP_SERVER(), cliente.getPUERTO_SERVIDOR());
            cliente.setSocket(socket);
            cliente.setEntrada(new DataInputStream(socket.getInputStream()));
            cliente.setSalida(new DataOutputStream(socket.getOutputStream()));
            cliente.setNombreUsuario(nombreUsuario.trim());
            cliente.setConectado(true);
            
            // Enviar nombre al servidor
            cliente.getSalida().writeUTF(cliente.getNombreUsuario());
            
            // Iniciar hilo de escucha
            hiloEscucha = new HiloEscuchaCliente(cliente.getEntrada(), this);
            hiloEscucha.start();
            
            // Actualizar vista
            fachada.getVentanaCliente().establecerEstadoConectado(true);
            fachada.getVentanaCliente().mostrarMensaje("=== Conectado al servidor ===");
            fachada.getVentanaCliente().mostrarMensaje("Ingrese su contrasena:");
            
        } catch (IOException e) {
            cliente.setConectado(false);
            throw new IOException("Error al conectar con el servidor: " + e.getMessage());
        }
    }
    
    /**
     * Desconecta del servidor cerrando todas las conexiones y recursos.
     * 
     * Este método realiza una desconexión limpia del servidor:
     * - Interrumpe el hilo de escucha de mensajes
     * - Cierra el socket de conexión
     * - Actualiza el estado del cliente y la interfaz
     * - Maneja cualquier error durante el proceso de desconexión
     * 
     * Es seguro llamar este método incluso si no hay conexión activa.
     */
    public void desconectarDelServidor() {
        try {
            if (cliente.isConectado()) {
                // Detener hilo de escucha
                if (hiloEscucha != null) {
                    hiloEscucha.interrupt();
                }
                
                // Cerrar conexiones
                if (cliente.getSocket() != null && !cliente.getSocket().isClosed()) {
                    cliente.getSocket().close();
                }
                
                cliente.setConectado(false);
                
                // Actualizar vista
                fachada.getVentanaCliente().establecerEstadoConectado(false);
                fachada.getVentanaCliente().mostrarMensaje("=== Desconectado del servidor ===");
            }
        } catch (IOException e) {
            fachada.getVentanaCliente().mostrarError("Error al desconectar: " + e.getMessage());
        }
    }
    
    /**
     * Envía un mensaje al servidor después de realizar validaciones.
     * 
     * Este método valida el mensaje antes de enviarlo al servidor,
     * lo transmite a través del stream de salida y actualiza la interfaz
     * para mostrar el mensaje enviado y limpiar el campo de entrada.
     * 
     * Validaciones realizadas:
     * - Conexión activa con el servidor
     * - Mensaje no nulo ni vacío
     * - Longitud máxima de 500 caracteres
     * 
     * @param mensaje El mensaje de texto a enviar al servidor
     */
    public void enviarMensaje(String mensaje) throws IOException {
        // Validaciones
        if (!cliente.isConectado()) {
            throw new IllegalStateException("No hay conexión activa con el servidor");
        }
        
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }
        
        if (mensaje.trim().length() > 500) {
            throw new IllegalArgumentException("El mensaje no puede exceder 500 caracteres");
        }
        
        try {
            cliente.getSalida().writeUTF(mensaje.trim());
            fachada.getVentanaCliente().mostrarMensaje("Tú: " + mensaje.trim());
            fachada.getVentanaCliente().limpiarCampoMensaje();
        } catch (IOException e) {
            throw new IOException("Error al enviar mensaje: " + e.getMessage());
        }
    }
    
    /**
     * Procesa un mensaje recibido del servidor.
     * 
     * Este método es llamado por el hilo de escucha cuando recibe un mensaje
     * del servidor. Se encarga de formatear y mostrar el mensaje en la
     * interfaz de usuario con el prefijo "Servidor:" para identificar
     * claramente el origen del mensaje.
     * 
     * @param mensaje El mensaje de texto recibido del servidor
     */
    public void procesarMensajeRecibido(String mensaje) {
        fachada.getVentanaCliente().mostrarMensaje("Servidor: " + mensaje);
    }
    
    /**
     * Maneja la pérdida inesperada de conexión con el servidor.
     * 
     * Este método es invocado cuando se detecta una pérdida de conexión
     * no intencional (por ejemplo, caída del servidor o problemas de red).
     * Actualiza el estado del cliente, la interfaz de usuario y notifica
     * al usuario sobre la pérdida de conexión mediante un mensaje de error.
     */
    public void manejarPerdidaConexion() {
        cliente.setConectado(false);
        fachada.getVentanaCliente().establecerEstadoConectado(false);
        fachada.getVentanaCliente().mostrarMensaje("=== Conexión perdida con el servidor ===");
        fachada.getVentanaCliente().mostrarError("Se perdió la conexión con el servidor");
    }
    
    /**
     * Obtiene la instancia del modelo Cliente.
     * 
     * Proporciona acceso al objeto que contiene los datos y estado
     * del cliente para otros componentes que puedan necesitarlo.
     * 
     * @return La instancia del modelo Cliente
     */
    public Cliente getCliente() {
        return cliente;
    }
    
    /**
     * Obtiene la instancia de la fachada del cliente.
     * 
     * Proporciona acceso a la fachada que maneja la interfaz de usuario
     * para componentes que necesiten interactuar con la vista.
     * 
     * @return La instancia de FachadaCliente
     */
    public FachadaCliente getFachada() {
        return fachada;
    }
}