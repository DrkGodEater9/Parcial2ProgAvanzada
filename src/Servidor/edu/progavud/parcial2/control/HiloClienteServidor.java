package Servidor.edu.progavud.parcial2.control;

import java.io.*;
import java.net.*;

/**
 * Hilo dedicado para manejar cada cliente conectado al servidor.
 * 
 * Esta clase extiende Thread y proporciona manejo concurrente de clientes:
 * - Cada cliente conectado ejecuta en su propio hilo independiente
 * - Maneja el protocolo completo de autenticación del cliente
 * - Gestiona comunicación bidireccional (envío/recepción de mensajes)
 * - Implementa ciclo de vida completo desde conexión hasta desconexión
 * - Manejo robusto de errores y limpieza de recursos
 * 
 * Protocolo de comunicación:
 * 1. Cliente se conecta → Se crea este hilo
 * 2. Cliente envía nombre de usuario
 * 3. Cliente envía contraseña
 * 4. Servidor valida credenciales en BD
 * 5. Si válido: registra cliente y confirma conexión
 * 6. Si inválido: envía error y termina conexión
 * 7. Loop de mensajes hasta desconexión
 * 
 * 
 * @author carlosmamut1
 * @author AlexM
 * @author batapop
 * @version 1.0
 */
public class HiloClienteServidor extends Thread {
    
    // ========== COMPONENTES DE COMUNICACIÓN ==========
    
    /** Socket TCP para comunicación con el cliente específico */
    private Socket socket;
    
    /** Stream de entrada para recibir datos del cliente */
    private DataInputStream entrada;
    
    /** Stream de salida para enviar datos al cliente */
    private DataOutputStream salida;
    
    // ========== REFERENCIAS DEL SISTEMA ==========
    
    /** Referencia al controlador del servidor para callbacks */
    private ControlServidor cServidor;
    
    /** Nombre de usuario del cliente autenticado (null hasta autenticación) */
    private String nombreUsuario;
    
    /**
     * Constructor del hilo de cliente.
     * 
     * Inicializa el hilo con el socket del cliente y referencia al controlador.
     * El hilo no se inicia automáticamente - debe llamarse start() externamente.
     * 
     * @param socket Socket TCP establecido con el cliente
     * @param cServidor Referencia al controlador del servidor
     */
    public HiloClienteServidor(Socket socket, ControlServidor cServidor) {
        this.socket = socket;
        this.cServidor = cServidor;
    }
    
    /**
     * Método principal del hilo que implementa el ciclo de vida completo del cliente.
     * 
     * Secuencia de ejecución:
     * 1. INICIALIZACIÓN: Crear streams de comunicación
     * 2. AUTENTICACIÓN: Recibir y validar credenciales
     * 3. REGISTRO: Si válido, registrar cliente en servidor
     * 4. COMUNICACIÓN: Loop de recepción de mensajes
     * 5. LIMPIEZA: Cerrar recursos y desregistrar cliente
     * 
     * Manejo de errores:
     * - IOException: Cliente desconectado o error de red
     * - InterruptedException: Hilo interrumpido por el servidor
     * - Exception general: Errores durante autenticación
     */
    @Override
    public void run() {
        try {
            // ========== FASE 1: INICIALIZACIÓN DE STREAMS ==========
            
            // Crear streams para comunicación bidireccional
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            
            // ========== FASE 2: PROTOCOLO DE AUTENTICACIÓN ==========
            
            // Recibir credenciales del cliente (protocolo definido)
            nombreUsuario = entrada.readUTF();  // Primera transmisión: usuario
            String contrasena = entrada.readUTF();  // Segunda transmisión: contraseña
            
            // Validar credenciales contra base de datos
            if (!cServidor.validarCredenciales(contrasena, nombreUsuario)) {
                // AUTENTICACIÓN FALLIDA: Enviar error y terminar conexión
                enviarMensaje("ERROR: Credenciales inválidas. Usuario o contraseña incorrectos. Conexión terminada.");
                Thread.sleep(1000); // Dar tiempo para que el mensaje llegue al cliente
                return; // Termina el hilo y cierra la conexión automáticamente
            }
            
            // ========== FASE 3: REGISTRO EXITOSO ==========
            
            // Registrar cliente en el sistema (puede ser rechazado si el juego ya inició)
            cServidor.registrarCliente(nombreUsuario, this);
            
            // Enviar confirmación de conexión exitosa al cliente
            enviarMensaje("Conectado exitosamente al servidor. ¡Bienvenido " + nombreUsuario + "!");
            
            // ========== FASE 4: LOOP DE COMUNICACIÓN ==========
            
            // Escuchar mensajes del cliente hasta que se desconecte o sea interrumpido
            while (!Thread.currentThread().isInterrupted()) {
                String mensaje = entrada.readUTF(); // Operación bloqueante
                
                // Delegar procesamiento del mensaje al controlador del servidor
                cServidor.procesarMensajeCliente(nombreUsuario, mensaje);
            }
            
        } catch (IOException e) {
            // Cliente se desconectó o error de comunicación
            // Esto es normal cuando el cliente cierra la aplicación
            
        } catch (InterruptedException e) {
            // Hilo interrumpido por el servidor (detener servidor, etc.)
            // Esto es normal durante el cierre del servidor
            
        } finally {
            // ========== FASE 5: LIMPIEZA GARANTIZADA ==========
            
            // Cerrar todos los recursos de red
            cerrarConexion();
            
            // Desregistrar cliente del servidor (si fue registrado)
            if (nombreUsuario != null) {
                cServidor.desregistrarCliente(nombreUsuario);
            }
        }
    }
    
    /**
     * Envía un mensaje al cliente a través del stream de salida.
     * 
     * Este método es thread-safe y puede ser llamado desde otros hilos
     * para enviar mensajes al cliente (ej: desde la interfaz del servidor).
     * 
     * Características:
     * - Utiliza writeUTF() para codificación Unicode
     * - Llama flush() para garantizar envío inmediato
     * - Lanza IOException si hay error de comunicación
     * 
     * @param mensaje Contenido del mensaje a enviar al cliente
     * @throws IOException Si hay error en la transmisión o el cliente está desconectado
     */
    public void enviarMensaje(String mensaje) throws IOException {
        if (salida != null) {
            salida.writeUTF(mensaje);
            salida.flush(); // Forzar envío inmediato del buffer
        }
    }
    
    /**
     * Cierra todos los recursos de comunicación de forma segura.
     * 
     * Este método garantiza que todos los streams y sockets se cierren
     * correctamente, evitando memory leaks y conexiones colgadas.
     * 
     * Orden de cierre:
     * 1. DataInputStream (entrada)
     * 2. DataOutputStream (salida)  
     * 3. Socket TCP
     * 
     * Los errores de cierre se ignoran silenciosamente ya que el objetivo
     * es liberar recursos, no reportar errores de una conexión que ya terminó.
     */
    public void cerrarConexion() {
        try {
            // Cerrar stream de entrada
            if (entrada != null) {
                entrada.close();
            }
        } catch (IOException e) {
            // Error silencioso - el objetivo es liberar recursos
        }
        
        try {
            // Cerrar stream de salida
            if (salida != null) {
                salida.close();
            }
        } catch (IOException e) {
            // Error silencioso - el objetivo es liberar recursos
        }
        
        try {
            // Cerrar socket principal
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            // Error silencioso - el objetivo es liberar recursos
        }
    }
    
    /**
     * Obtiene el nombre de usuario del cliente autenticado.
     * 
     * @return Nombre del usuario, null si aún no se ha autenticado
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
}