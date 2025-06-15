package Jugador.edu.progavud.parcial2.control;

import Jugador.edu.progavud.parcial2.modelo.Cliente;
import Jugador.edu.progavud.parcial2.modelo.ConexionPropiedades;
import java.io.*;
import static java.lang.Integer.parseInt;
import java.net.*;

/**
 * Controlador del cliente - Contiene toda la lógica de negocio y validaciones.
 */
public class ControlCliente {
    
    private Cliente cliente;
    private FachadaCliente fachada;
    private HiloEscuchaCliente hiloEscucha;
    private ConexionPropiedades cnxPropiedades;
    
    public ControlCliente() throws IOException {
        this.cliente = new Cliente();
        this.fachada = new FachadaCliente(this);
        this.cnxPropiedades=new ConexionPropiedades();
        
    }
    
    /**
     * Establece conexión con el servidor.
     * Contiene validaciones y manejo de errores.
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
            
        } catch (IOException e) {
            cliente.setConectado(false);
            throw new IOException("Error al conectar con el servidor: " + e.getMessage());
        }
    }
    
    /**
     * Desconecta del servidor.
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
     * Envía un mensaje al servidor.
     * Contiene validaciones del mensaje.
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
     * Procesa mensaje recibido del servidor.
     */
    public void procesarMensajeRecibido(String mensaje) {
        fachada.getVentanaCliente().mostrarMensaje("Servidor: " + mensaje);
    }
    
    /**
     * Maneja la pérdida de conexión.
     */
    public void manejarPerdidaConexion() {
        cliente.setConectado(false);
        fachada.getVentanaCliente().establecerEstadoConectado(false);
        fachada.getVentanaCliente().mostrarMensaje("=== Conexión perdida con el servidor ===");
        fachada.getVentanaCliente().mostrarError("Se perdió la conexión con el servidor");
    }
    
    // Getters
    public Cliente getCliente() {
        return cliente;
    }
    
    public FachadaCliente getFachada() {
        return fachada;
    }
}
