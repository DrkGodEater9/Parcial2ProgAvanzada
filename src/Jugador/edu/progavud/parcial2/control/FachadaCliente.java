package Jugador.edu.progavud.parcial2.control;

import Jugador.edu.progavud.parcial2.vista.VentanaCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Fachada del cliente que maneja los eventos de la interfaz gráfica.
 * 
 * Esta clase implementa el patrón Facade y actúa como intermediario entre
 * la vista (VentanaCliente) y el controlador (ControlCliente). Se encarga
 * de capturar y procesar los eventos generados por la interfaz de usuario,
 * delegando las operaciones correspondientes al controlador.
 * 
 * Implementa ActionListener para manejar los eventos de los botones y
 * campos de texto de la ventana del cliente, proporcionando una separación
 * clara entre la lógica de presentación y la lógica de negocio.
 * 
 * @author carlosmamut1
 * @author Alex M
 * @author batapop
 * @version 1.0
 */
public class FachadaCliente implements ActionListener {
    
    /** Referencia al controlador del cliente para delegar operaciones */
    private ControlCliente cCliente;
    
    /** Referencia a la ventana del cliente para manejo de la interfaz */
    private VentanaCliente vCliente;
    
    /**
     * Constructor de la fachada del cliente.
     * 
     * Inicializa la fachada estableciendo la referencia al controlador,
     * creando la ventana del cliente, configurando los listeners de eventos
     * y haciendo visible la ventana.
     * 
     * @param cCliente El controlador del cliente que manejará la lógica de negocio
     */
    public FachadaCliente(ControlCliente cCliente) throws IOException {
        this.cCliente = cCliente;
        this.vCliente = new VentanaCliente(this);
        configurarListeners();
        vCliente.setVisible(true);
    }
    
    /**
     * Configura los listeners de eventos para los componentes de la interfaz.
     * 
     * Asocia esta instancia como ActionListener para los botones de conectar,
     * desconectar, enviar mensaje y el campo de texto de mensajes, permitiendo
     * que la fachada capture y procese los eventos de la interfaz.
     */
    public void configurarListeners() {
        vCliente.btnConectar.addActionListener(this);
        vCliente.btnDesconectar.addActionListener(this);
        vCliente.btnEnviar.addActionListener(this);
        vCliente.txtMensaje.addActionListener(this);
    }
    
    /**
     * Maneja los eventos de acción generados por los componentes de la interfaz.
     * 
     * Este método es llamado automáticamente cuando el usuario interactúa con
     * los botones o presiona Enter en el campo de mensaje. Identifica la fuente
     * del evento y delega el procesamiento al método correspondiente.
     * Captura y maneja cualquier excepción mostrando el error al usuario.
     * 
     * @param e El evento de acción que contiene información sobre el componente activado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == vCliente.btnConectar) {
                procesarConexion();
            } else if (e.getSource() == vCliente.btnDesconectar) {
                procesarDesconexion();
            } else if (e.getSource() == vCliente.btnEnviar || 
                       e.getSource() == vCliente.txtMensaje) {
                procesarEnvioMensaje();
            }
        } catch (Exception ex) {
            vCliente.mostrarError(ex.getMessage());
        }
    }
    
    /**
     * Procesa el evento de conexión al servidor.
     * 
     * Obtiene el nombre de usuario ingresado en la interfaz y solicita
     * al controlador que establezca la conexión con el servidor.
     */
    public void procesarConexion() throws IOException {
        String nombre = vCliente.txtNombre.getText();
        cCliente.conectarAlServidor(nombre);
    }
    
    /**
     * Procesa el evento de desconexión del servidor.
     * 
     * Solicita al controlador que cierre la conexión actual con el servidor
     * y actualice el estado de la interfaz correspondientemente.
     */
    public void procesarDesconexion() {
        cCliente.desconectarDelServidor();
    }
    
    /**
     * Procesa el evento de envío de mensaje.
     * 
     * Obtiene el mensaje escrito por el usuario en el campo de texto
     * y solicita al controlador que lo envíe al servidor.
     */
    public void procesarEnvioMensaje() throws IOException {
        String mensaje = vCliente.txtMensaje.getText();
        cCliente.enviarMensaje(mensaje);
    }
    
    /**
     * Obtiene la referencia a la ventana del cliente.
     * 
     * Proporciona acceso a la instancia de VentanaCliente para que
     * el controlador pueda actualizar la interfaz cuando sea necesario.
     * 
     * @return La instancia de VentanaCliente asociada a esta fachada
     */
    public VentanaCliente getVentanaCliente() {
        return vCliente;
    }
}