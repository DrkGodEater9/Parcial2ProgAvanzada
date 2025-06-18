package Servidor.edu.progavud.parcial2.vista;

import Servidor.edu.progavud.parcial2.control.FachadaServidor;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de administración del servidor de chat y juego de memoria.
 * 
 * Esta clase implementa la interfaz gráfica principal del servidor que permite
 * controlar el estado del servidor, gestionar conexiones de clientes y supervisar
 * las conversaciones de chat individuales. Forma parte del patrón MVC como 
 * componente Vista, manteniendo separación entre presentación y lógica de negocio.
 * 
 * Funcionalidades principales:
 * - Control del estado del servidor (iniciar/detener)
 * - Monitoreo en tiempo real de clientes conectados
 * - Gestión dinámica de ventanas de chat por cliente
 * - Envío de mensajes desde servidor hacia clientes
 * - Selección de archivos de configuración del sistema
 * 
 * La interfaz está organizada con un panel superior para controles del servidor
 * y un área central con scroll para mostrar múltiples chats de clientes.
 * 
 * @author Carlos Mamut
 * @author Felipe Rodriguez  
 * @author Alex Martinez
 * @version 2.1
 * @since 2024
 */
public class VentanaServidorChat extends JFrame {
    
    /**
     * Botón para iniciar el servidor de comunicaciones.
     * Se habilita cuando el servidor está detenido.
     */
    public JButton btnIniciar;
    
    /**
     * Botón para detener el servidor de comunicaciones.
     * Se habilita cuando el servidor está activo.
     */
    public JButton btnDetener;
    
    /**
     * Etiqueta que muestra el estado actual del servidor.
     * Cambia de color según el estado: verde (activo) o rojo (detenido).
     */
    public JLabel lblEstado;
    
    /**
     * Etiqueta que muestra la cantidad de clientes conectados.
     * Se actualiza automáticamente cuando hay cambios en las conexiones.
     */
    public JLabel lblClientesConectados;
    
    /**
     * Panel contenedor principal para ventanas de chat individuales.
     * Utiliza BoxLayout vertical dentro de un JScrollPane.
     */
    public JPanel panelClientes;
    
    /**
     * Referencia al controlador principal del servidor.
     * Facilita la comunicación con la lógica de negocio.
     */
    private FachadaServidor fachadaS;
    
    /**
     * Selector de archivos para configuraciones del servidor.
     * Configurado para navegar en el directorio de datos del proyecto.
     */
    private JFileChooser fc;
    
    /**
     * Constructor principal de la ventana del servidor.
     * Inicializa la interfaz gráfica y establece los comandos de acción.
     * 
     * @param fachada Controlador principal que maneja la lógica del servidor.
     *                Debe ser una instancia válida de FachadaServidor.
     */
    public VentanaServidorChat(FachadaServidor fachada) {
        this.fachadaS = fachada;
        initComponents();
        btnIniciar.setActionCommand("iniciarServ");
        btnDetener.setActionCommand("detenerServ");
    }
    
    /**
     * Inicializa y configura todos los componentes gráficos de la ventana.
     * Configura el layout, crea los paneles de control y establece 
     * las propiedades visuales de los componentes.
     */
    private void initComponents() {
        setTitle("Servidor Chat Individual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel superior - Control servidor
        JPanel panelControl = new JPanel(new FlowLayout());
        btnIniciar = new JButton("Iniciar Servidor");
        btnDetener = new JButton("Detener Servidor");
        btnDetener.setEnabled(false);
        panelControl.add(btnIniciar);
        panelControl.add(btnDetener);
        
        lblEstado = new JLabel("Servidor Detenido");
        lblEstado.setForeground(Color.RED);
        panelControl.add(lblEstado);
        
        lblClientesConectados = new JLabel("Clientes: 0");
        panelControl.add(lblClientesConectados);
        
        // Panel central - Ventanas de clientes
        panelClientes = new JPanel();
        panelClientes.setLayout(new BoxLayout(panelClientes, BoxLayout.Y_AXIS));
        JScrollPane scrollClientes = new JScrollPane(panelClientes);
        scrollClientes.setBorder(BorderFactory.createTitledBorder("Chats de Clientes"));
        scrollClientes.setPreferredSize(new Dimension(600, 400));
        
        add(panelControl, BorderLayout.NORTH);
        add(scrollClientes, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Actualiza el estado visual del servidor en la interfaz.
     * Controla la habilitación de botones y el color de las etiquetas
     * para reflejar el estado actual del servidor.
     * 
     * @param activo true si el servidor está funcionando, false si está detenido
     */
    public void establecerEstadoServidor(boolean activo) {
        btnIniciar.setEnabled(!activo);
        btnDetener.setEnabled(activo);
        lblEstado.setText(activo ? "Servidor Activo" : "Servidor Detenido");
        lblEstado.setForeground(activo ? Color.GREEN : Color.RED);
    }
    
    /**
     * Actualiza el contador de clientes conectados en tiempo real.
     * Modifica la etiqueta para mostrar el número actual de conexiones.
     * 
     * @param cantidad Número actual de clientes conectados al servidor
     */
    public void actualizarContadorClientes(int cantidad) {
        lblClientesConectados.setText("Clientes: " + cantidad);
    }
    
    /**
     * Crea una ventana de chat completa para un cliente específico.
     * Construye dinámicamente la interfaz de chat incluyendo área de mensajes,
     * campo de entrada y botón de envío. Almacena referencias internas 
     * usando ClientProperty para acceso posterior.
     * 
     * @param nombreCliente Nombre del cliente para identificar la ventana
     * @return Panel configurado con interfaz de chat completa
     */
    public JPanel crearVentanaChat(String nombreCliente) {
        JPanel panelChat = new JPanel(new BorderLayout());
        panelChat.setBorder(BorderFactory.createTitledBorder("Chat con " + nombreCliente));
        panelChat.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        JTextArea areaChat = new JTextArea(8, 30);
        areaChat.setEditable(false);
        areaChat.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        JScrollPane scrollChat = new JScrollPane(areaChat);
        
        JPanel panelEnvio = new JPanel(new BorderLayout());
        JTextField txtMensaje = new JTextField();
        JButton btnEnviar = new JButton("Enviar");
        
        panelEnvio.add(txtMensaje, BorderLayout.CENTER);
        panelEnvio.add(btnEnviar, BorderLayout.EAST);
        
        panelChat.add(scrollChat, BorderLayout.CENTER);
        panelChat.add(panelEnvio, BorderLayout.SOUTH);
        
        // Almacenar referencias para uso posterior
        panelChat.putClientProperty("areaChat", areaChat);
        panelChat.putClientProperty("txtMensaje", txtMensaje);
        panelChat.putClientProperty("btnEnviar", btnEnviar);
        panelChat.putClientProperty("nombreCliente", nombreCliente);
        
        return panelChat;
    }
    
    /**
     * Agrega una ventana de chat al panel principal de clientes.
     * Incorpora la ventana al contenedor y actualiza la visualización.
     * 
     * @param ventanaChat Panel de chat previamente creado con crearVentanaChat()
     */
    public void agregarVentanaCliente(JPanel ventanaChat) {
        panelClientes.add(ventanaChat);
        panelClientes.revalidate();
        panelClientes.repaint();
    }
    
    /**
     * Remueve una ventana de chat del panel principal de clientes.
     * Elimina la ventana del contenedor y actualiza la visualización.
     * 
     * @param ventanaChat Panel de chat a eliminar del contenedor principal
     */
    public void removerVentanaCliente(JPanel ventanaChat) {
        panelClientes.remove(ventanaChat);
        panelClientes.revalidate();
        panelClientes.repaint();
    }
    
    /**
     * Muestra un mensaje en el área de chat de un cliente específico.
     * Agrega el mensaje al área de texto y posiciona el cursor al final
     * para mostrar automáticamente los mensajes más recientes.
     * 
     * @param nombreCliente Nombre del cliente (para referencia)
     * @param mensaje Contenido del mensaje a mostrar en el chat
     * @param ventanaChat Panel de chat donde mostrar el mensaje
     */
    public void mostrarMensajeEnChat(String nombreCliente, String mensaje, JPanel ventanaChat) {
        JTextArea area = (JTextArea) ventanaChat.getClientProperty("areaChat");
        area.append(mensaje + "\n");
        area.setCaretPosition(area.getDocument().getLength());
    }
    
    /**
     * Muestra un mensaje de error en un cuadro de diálogo modal.
     * Presenta errores del sistema al administrador del servidor.
     * 
     * @param mensaje Texto descriptivo del error ocurrido
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Abre un selector de archivos para configuraciones del servidor.
     * Configurado para navegar en el directorio de datos del proyecto
     * y permitir selección de archivos de configuración.
     * 
     * @param titulo Título personalizado para la ventana del selector
     * @return Ruta completa del archivo seleccionado por el usuario
     */
    public String retribuirArchivo(String titulo) {
        this.fc = new JFileChooser(System.getProperty("user.dir")+"/src/Servidor/edu/progavud/parcial2/data");
        fc.setDialogTitle(titulo);
        this.fc.showOpenDialog(fc);
        return fc.getSelectedFile().toString();
    }
}