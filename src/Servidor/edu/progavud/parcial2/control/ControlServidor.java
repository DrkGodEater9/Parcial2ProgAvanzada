package Servidor.edu.progavud.parcial2.control;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JPanel;

/**
 * Controlador del servidor de red que maneja conexiones y comunicaciones.
 * 
 * Esta clase centraliza toda la funcionalidad de red del servidor:
 * - Gestión de conexiones TCP de múltiples clientes
 * - Autenticación y validación de credenciales
 * - Comunicación bidireccional con clientes
 * - Control de acceso según estado del juego
 * - Gestión de ventanas de chat por cliente
 * - Manejo concurrente de múltiples hilos de cliente
 * 
 * 
 * @author carlosmamut1
 * @author AlexM
 * @author batapop
 * @version 1.0
 */
public class ControlServidor {
    
    // ========== COMPONENTES PRINCIPALES ==========
    
    /** Referencia al controlador principal del servidor */
    private ControlPrincipalServidor cPrincipal;
    
    /** Socket principal del servidor para aceptar conexiones */
    private ServerSocket serverSocket;
    
    // ========== ESTADO DEL SERVIDOR ==========
    
    /** Indica si el servidor está activo y aceptando conexiones */
    private boolean servidorActivo;
    
    /** Indica si el juego ya ha iniciado (bloquea nuevos clientes) */
    private boolean juegoIniciado;
    
    /** Puerto en el que escucha el servidor */
    private String puertoServ;
    
    // ========== GESTIÓN DE CLIENTES ==========
    
    /** 
     * Mapa thread-safe de clientes conectados.
     * Key: nombreUsuario, Value: hilo que maneja ese cliente
     */
    private Map<String, HiloClienteServidor> clientesConectados;
    
    /** 
     * Mapa de ventanas de chat por cliente.
     * Key: nombreUsuario, Value: JPanel de la ventana de chat
     */
    private Map<String, JPanel> ventanasChat;
    
    /** Referencia al hilo actual de cliente (uso interno) */
    private HiloClienteServidor hiloClienteServidor;
    
    /**
     * Constructor del controlador de servidor.
     * 
     * Inicializa todas las estructuras de datos necesarias y establece
     * el estado inicial del servidor (inactivo, sin juego iniciado).
     * 
     * @param cPrincipal Referencia al controlador principal del sistema
     */
    public ControlServidor(ControlPrincipalServidor cPrincipal) {
        this.cPrincipal = cPrincipal;
        
        // Inicializar colecciones thread-safe para manejo concurrente
        this.clientesConectados = new ConcurrentHashMap<>();
        this.ventanasChat = new ConcurrentHashMap<>();
        
        // Estado inicial del servidor
        this.servidorActivo = false;
        this.juegoIniciado = false;
    }
    
    // ========== CONTROL DEL JUEGO ==========
    
    /**
     * Marca el inicio del juego y bloquea la entrada de nuevos clientes.
     * 
     * Una vez que se llama este método, cualquier cliente que intente
     * conectarse recibirá un mensaje de error y será desconectado.
     * Esto garantiza que solo los jugadores presentes al inicio participen.
     */
    public void iniciarJuego() {
        this.juegoIniciado = true;
        enviarMensajeATodosLosClientes("El juego ha iniciado, digita parejas de coordenadas del orden Fila,Columna");
        enviarMensajeATodosLosClientes("Las filas van del 1 al 5, y las columnas del 1 al 8");
        enviarMensajeATodosLosClientes("Espera a que el servidor marque tu turno");
        // Los nuevos clientes serán rechazados en registrarCliente()
    }
    
    /**
     * Obtiene la lista de nombres de todos los jugadores conectados.
     * 
     * @return ArrayList con los nombres de usuario de todos los clientes conectados
     */
    public ArrayList<String> obtenerNombresJugadores() {
        return new ArrayList<>(clientesConectados.keySet());
    }
    
    /**
     * Envía un mensaje a todos los clientes conectados (broadcast).
     * 
     * Útil para notificaciones globales como inicio de juego,
     * cambios de estado del servidor, etc.
     * 
     * @param mensaje Contenido del mensaje a enviar a todos los clientes
     */
    public void enviarMensajeATodosLosClientes(String mensaje) {
        for (String nombreUsuario : clientesConectados.keySet()) {
            enviarMensajeACliente(nombreUsuario, mensaje);
        }
    }
    
    // ========== AUTENTICACIÓN ==========
    
    /**
     * Valida las credenciales de un cliente contra la base de datos.
     * 
     * Delega la validación al controlador de jugadores, que consulta
     * la base de datos para verificar usuario y contraseña.
     * 
     * @param contrasena La contraseña proporcionada por el cliente
     * @param nombreUsuario El nombre de usuario proporcionado por el cliente
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean validarCredenciales(String contrasena, String nombreUsuario) {
        return cPrincipal.getcJugador().validarJugador(contrasena, nombreUsuario);
    }
    
    // ========== CONTROL DE VENTANA DE JUEGO ==========
    
    /**
     * Controla la visibilidad de la ventana de juego según el número de clientes.
     * 
     * Reglas de visibilidad:
     * - >= 2 clientes: Mostrar ventana de juego (permite iniciar partida)
     * - < 2 clientes: Ocultar ventana de juego (no se puede jugar)
     * 
     * Este método se llama automáticamente cuando se conecta/desconecta un cliente.
     */
    public void controlarVentanaJuego() {
        int numClientes = clientesConectados.size();
        
        if (numClientes >= 2) {
            // Mostrar ventana de juego - hay suficientes jugadores
            cPrincipal.getFachadaS().mostrarVentanaJuego();
        } else {
            // Ocultar ventana de juego - no hay suficientes jugadores
            cPrincipal.getFachadaS().ocultarVentanaJuego();
        }
    }
    
    // ========== GESTIÓN DEL SERVIDOR DE RED ==========
    
    /**
     * Inicia el servidor de red en el puerto configurado.
     * 
     * Secuencia de inicio:
     * 1. Verifica que el servidor no esté ya activo
     * 2. Crea ServerSocket en el puerto especificado
     * 3. Actualiza estado y vista del servidor
     * 4. Inicia hilo para aceptar conexiones entrantes
     * 
     * @throws IOException Si hay error al crear el ServerSocket o bind al puerto
     */
    public void iniciarServidor() throws IOException {
        // Verificar que no esté ya activo
        if (servidorActivo) {
            throw new IllegalStateException("El servidor ya está activo");
        }
        
        try {
            // Crear socket del servidor en el puerto configurado
            serverSocket = new ServerSocket(Integer.parseInt(puertoServ));
            servidorActivo = true;
            
            // Actualizar interfaz del servidor
            cPrincipal.getFachadaS().getvServidorChat().establecerEstadoServidor(true);
            
            // Iniciar hilo para aceptar conexiones de forma asíncrona
            new Thread(this::aceptarConexiones).start();
            
        } catch (IOException e) {
            // En caso de error, revertir estado y propagar excepción
            servidorActivo = false;
            throw new IOException("Error al iniciar servidor: " + e.getMessage());
        }
    }
    
    /**
     * Detiene completamente el servidor y desconecta todos los clientes.
     * 
     * Secuencia de detención:
     * 1. Marcar servidor como inactivo
     * 2. Reiniciar estado del juego
     * 3. Interrumpir todos los hilos de clientes
     * 4. Limpiar colecciones de clientes
     * 5. Cerrar ServerSocket
     * 6. Actualizar interfaz del servidor
     */
    public void detenerServidor() {
        try {
            // Cambiar estado del servidor
            servidorActivo = false;
            juegoIniciado = false; // Permitir nuevos clientes cuando se reinicie
            
            // Desconectar todos los clientes activos
            for (HiloClienteServidor hilo : clientesConectados.values()) {
                hilo.interrupt(); // Interrumpir hilo de cliente
            }
            
            // Limpiar colecciones
            clientesConectados.clear();
            ventanasChat.clear();
            
            // Ocultar ventana de juego al no haber clientes
            cPrincipal.getFachadaS().ocultarVentanaJuego();
            
            // Cerrar socket del servidor
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            
            // Actualizar interfaz del servidor
            cPrincipal.getFachadaS().getvServidorChat().establecerEstadoServidor(false);
            cPrincipal.getFachadaS().getvServidorChat().actualizarContadorClientes(0);
            
        } catch (IOException e) {
            // Error silencioso al detener - no es crítico
        }
    }
    
    /**
     * Bucle principal para aceptar conexiones entrantes de clientes.
     * 
     * Este método se ejecuta en un hilo separado y continuamente:
     * 1. Espera nuevas conexiones en el ServerSocket
     * 2. Por cada conexión, crea un HiloClienteServidor
     * 3. Inicia el hilo para manejar ese cliente específicamente
     * 
     * El bucle continúa mientras servidorActivo sea true.
     */
    public void aceptarConexiones() {
        while (servidorActivo) {
            try {
                // Esperar nueva conexión (operación bloqueante)
                Socket socketCliente = serverSocket.accept();
                
                // Crear hilo dedicado para manejar este cliente
                HiloClienteServidor hiloCliente = new HiloClienteServidor(socketCliente, this);
                hiloCliente.start();
                
            } catch (IOException e) {
                // Solo loggear error si el servidor sigue activo
                if (servidorActivo) {
                    // Error aceptando conexión - continúa el bucle
                }
            }
        }
    }
    
    // ========== GESTIÓN DE CLIENTES ==========
    
    /**
     * Registra un nuevo cliente después de validar sus credenciales.
     * 
     * Este método solo se llama desde HiloClienteServidor después de que
     * las credenciales han sido validadas exitosamente. Implementa la
     * lógica de bloqueo de nuevos clientes cuando el juego ya ha iniciado.
     * 
     * Validaciones:
     * - Juego no debe haber iniciado
     * - Nombre de usuario no debe estar vacío
     * - Cliente no debe estar ya conectado
     * 
     * @param nombreUsuario Nombre del usuario autenticado
     * @param hilo Hilo que maneja las comunicaciones con este cliente
     */
    public void registrarCliente(String nombreUsuario, HiloClienteServidor hilo) {
        // VALIDACIÓN CRÍTICA: Rechazar si el juego ya inició
        if (juegoIniciado) {
            try {
                // Enviar mensaje de error al cliente
                hilo.enviarMensaje("ERROR: El juego ya ha comenzado. No se pueden conectar más jugadores.");
                Thread.sleep(1000); // Dar tiempo para que llegue el mensaje
            } catch (Exception e) {
                // Error silencioso enviando mensaje
            }
            // Terminar el hilo del cliente
            hilo.interrupt();
            return;
        }
        
        // Validar nombre de usuario
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            hilo.interrupt();
            return;
        }
        
        // Verificar que el cliente no esté ya conectado
        if (clientesConectados.containsKey(nombreUsuario)) {
            hilo.interrupt();
            return;
        }
        
        // Registrar cliente en el sistema
        clientesConectados.put(nombreUsuario, hilo);
        
        // Crear ventana de chat específica para este cliente
        JPanel ventanaChat = cPrincipal.getFachadaS().getvServidorChat().crearVentanaChat(nombreUsuario);
        ventanasChat.put(nombreUsuario, ventanaChat);
        cPrincipal.getFachadaS().getvServidorChat().agregarVentanaCliente(ventanaChat);
        
        // Configurar listeners para envío de mensajes desde la ventana
        cPrincipal.getFachadaS().configurarChatCliente(nombreUsuario, ventanaChat);
        
        // Actualizar contador de clientes en la interfaz
        cPrincipal.getFachadaS().getvServidorChat().actualizarContadorClientes(clientesConectados.size());
        
        // Evaluar si mostrar/ocultar ventana de juego
        controlarVentanaJuego();
    }
    
    /**
     * Desregistra un cliente que se ha desconectado.
     * 
     * Limpia todas las referencias del cliente:
     * - Lo remueve del mapa de clientes conectados
     * - Elimina su ventana de chat de la interfaz
     * - Actualiza contadores y estado de la ventana de juego
     * 
     * @param nombreUsuario Nombre del usuario a desregistrar
     */
    public void desregistrarCliente(String nombreUsuario) {
        if (nombreUsuario != null && clientesConectados.containsKey(nombreUsuario)) {
            // Remover cliente del mapa
            clientesConectados.remove(nombreUsuario);
            
            // Remover y limpiar ventana de chat
            JPanel ventanaChat = ventanasChat.remove(nombreUsuario);
            if (ventanaChat != null) {
                cPrincipal.getFachadaS().getvServidorChat().removerVentanaCliente(ventanaChat);
            }
            
            // Actualizar contador en la interfaz
            cPrincipal.getFachadaS().getvServidorChat().actualizarContadorClientes(clientesConectados.size());
            
            // Reevaluar visibilidad de ventana de juego
            controlarVentanaJuego();
        }
    }
    
    // ========== COMUNICACIÓN CON CLIENTES ==========
    
    /**
     * Procesa un mensaje recibido de un cliente específico.
     * 
     * Implementa validaciones de contenido y longitud antes de
     * mostrar el mensaje en la ventana de chat correspondiente.
     * 
     * Validaciones aplicadas:
     * - Mensaje no debe ser null o vacío
     * - Longitud máxima de 500 caracteres
     * 
     * @param nombreUsuario Cliente que envió el mensaje
     * @param mensaje Contenido del mensaje recibido
     */
    public void procesarMensajeCliente(String nombreUsuario, String mensaje) {
        // Validar contenido del mensaje
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return;
        }
        
        // Validar longitud máxima
        if (mensaje.trim().length() > 500) {
            enviarMensajeACliente(nombreUsuario, "ERROR: Mensaje demasiado largo");
            return;
        }
        
        // Mostrar mensaje en la ventana de chat del cliente
        JPanel ventanaChat = ventanasChat.get(nombreUsuario);
        if (ventanaChat != null) {
            cPrincipal.getFachadaS().getvServidorChat().mostrarMensajeEnChat(
                nombreUsuario, 
                nombreUsuario + ": " + mensaje, 
                ventanaChat
            );
        }
    }
    
    /**
     * Envía un mensaje a un cliente específico.
     * 
     * Localiza el hilo correspondiente al cliente y envía el mensaje
     * a través de su stream de comunicación. También muestra el mensaje
     * en la ventana de chat del servidor con prefijo "Servidor:".
     * 
     * Validaciones aplicadas:
     * - Mensaje no debe ser null o vacío
     * - Longitud máxima de 500 caracteres
     * 
     * @param nombreUsuario Cliente destinatario del mensaje
     * @param mensaje Contenido del mensaje a enviar
     */
    public void enviarMensajeACliente(String nombreUsuario, String mensaje) {
        // Validar contenido del mensaje
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return;
        }
        
        // Validar longitud máxima
        if (mensaje.trim().length() > 500) {
            return;
        }
        
        // Obtener hilo del cliente destinatario
        HiloClienteServidor hilo = clientesConectados.get(nombreUsuario);
        if (hilo != null) {
            try {
                // Enviar mensaje al cliente
                hilo.enviarMensaje(mensaje.trim());
                
                // Mostrar mensaje en ventana de chat del servidor
                JPanel ventanaChat = ventanasChat.get(nombreUsuario);
                if (ventanaChat != null) {
                    cPrincipal.getFachadaS().getvServidorChat().mostrarMensajeEnChat(
                        nombreUsuario, 
                        "Servidor: " + mensaje, 
                        ventanaChat
                    );
                }
            } catch (IOException e) {
                // Error silencioso enviando mensaje - cliente probablemente desconectado
            }
        }
    }
    
    // ========== GETTERS Y SETTERS ==========
    
    /**
     * Obtiene referencia al controlador principal.
     * 
     * @return Instancia del controlador principal del servidor
     */
    public ControlPrincipalServidor getcPrincipal() {
        return cPrincipal;
    }
    
    /**
     * Verifica si el servidor está activo y aceptando conexiones.
     * 
     * @return true si el servidor está activo, false en caso contrario
     */
    public boolean isServidorActivo() {
        return servidorActivo;
    }

    /**
     * Obtiene el puerto en el que está configurado el servidor.
     * 
     * @return String con el número de puerto del servidor
     */
    public String getPuertoServ() {
        return puertoServ;
    }

    /**
     * Establece el puerto en el que debe escuchar el servidor.
     * 
     * @param puertoServ Número de puerto como String
     */
    public void setPuertoServ(String puertoServ) {
        this.puertoServ = puertoServ;
    }
    
    /**
     * Obtiene el número actual de clientes conectados.
     * 
     * @return Cantidad de clientes actualmente conectados
     */
    public int getNumeroClientesConectados() {
        return clientesConectados.size();
    }
    
    /**
     * Verifica si el juego ya ha sido iniciado.
     * 
     * @return true si el juego ya inició (no acepta nuevos clientes), false en caso contrario
     */
    public boolean isJuegoIniciado() {
        return juegoIniciado;
    }
}