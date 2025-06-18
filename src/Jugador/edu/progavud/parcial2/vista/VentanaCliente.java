package Jugador.edu.progavud.parcial2.vista;
import Jugador.edu.progavud.parcial2.control.FachadaCliente;
import javax.swing.*;
import java.awt.*;

/**
 * Vista del cliente para el sistema de chat individual.
 * Proporciona la interfaz gráfica de usuario para la comunicación cliente-servidor.
 * Esta clase sigue el patrón MVC y únicamente contiene componentes gráficos,
 * sin lógica de negocio, validaciones, ni estructuras de control.
 * 
 * @author carlosmamut1
 * @author Alex M
 * @author batapop
 * @version 1.0
 */
public class VentanaCliente extends JFrame {
    
    /** Campo de texto para ingresar el nombre del usuario */
    public JTextField txtNombre;
    
    /** Botón para establecer conexión con el servidor */
    public JButton btnConectar;
    
    /** Botón para desconectar del servidor */
    public JButton btnDesconectar;
    
    /** Área de texto que muestra el historial del chat */
    public JTextArea txtAreaChat;
    
    /** Campo de texto para escribir mensajes */
    public JTextField txtMensaje;
    
    /** Botón para enviar mensajes al servidor */
    public JButton btnEnviar;
    
    /** Etiqueta que muestra el estado de conexión actual */
    public JLabel lblEstado;
    
    /** Selector de archivos para cargar archivos de propiedades */
    private JFileChooser fc;
    
    /** Referencia a la fachada del cliente para comunicación con el controlador */
    private FachadaCliente fachadaC;
    
    /**
     * Constructor de la ventana cliente.
     * Inicializa todos los componentes gráficos de la interfaz y establece
     * la referencia a la fachada del cliente para la comunicación con el controlador.
     * 
     * @param fachadaC La instancia de FachadaCliente que maneja la comunicación
     */
    public VentanaCliente(FachadaCliente fachadaC) {
        initComponents();
        this.fachadaC = fachadaC;
    }
    
    /**
     * Inicializa y configura todos los componentes de la interfaz gráfica.
     * Establece el layout, crea los paneles y configura las propiedades
     * visuales de cada componente.
     */
    private void initComponents() {
        setTitle("Cliente Chat Individual");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel superior - Conexión
        JPanel panelConexion = new JPanel(new FlowLayout());
        panelConexion.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(15);
        panelConexion.add(txtNombre);
        btnConectar = new JButton("Conectar");
        btnDesconectar = new JButton("Desconectar");
        btnDesconectar.setEnabled(false);
        panelConexion.add(btnConectar);
        panelConexion.add(btnDesconectar);
        
        // Panel central - Chat
        txtAreaChat = new JTextArea(15, 40);
        txtAreaChat.setEditable(false);
        txtAreaChat.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollChat = new JScrollPane(txtAreaChat);
        scrollChat.setBorder(BorderFactory.createTitledBorder("Chat con Servidor"));
        
        // Panel inferior - Envío y Estado
        JPanel panelInferior = new JPanel(new BorderLayout());
        
        // Subpanel para envío de mensajes
        JPanel panelEnvio = new JPanel(new BorderLayout());
        panelEnvio.add(new JLabel("Mensaje: "), BorderLayout.WEST);
        txtMensaje = new JTextField();
        txtMensaje.setEnabled(false);
        panelEnvio.add(txtMensaje, BorderLayout.CENTER);
        btnEnviar = new JButton("Enviar");
        btnEnviar.setEnabled(false);
        panelEnvio.add(btnEnviar, BorderLayout.EAST);
        
        // Label de estado
        lblEstado = new JLabel("Desconectado");
        lblEstado.setForeground(Color.RED);
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Agregar al panel inferior
        panelInferior.add(panelEnvio, BorderLayout.CENTER);
        panelInferior.add(lblEstado, BorderLayout.SOUTH);
        
        // Agregar todos los paneles al frame
        add(panelConexion, BorderLayout.NORTH);
        add(scrollChat, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Muestra un mensaje en el área de chat.
     * Agrega el mensaje al final del área de texto y ajusta la posición
     * del cursor para mostrar siempre el último mensaje.
     * 
     * @param mensaje El mensaje a mostrar en el área de chat
     */
    public void mostrarMensaje(String mensaje) {
        txtAreaChat.append(mensaje + "\n");
        txtAreaChat.setCaretPosition(txtAreaChat.getDocument().getLength());
    }
    
    /**
     * Establece el estado visual de la conexión en la interfaz.
     * Habilita o deshabilita los componentes según el estado de conexión
     * y actualiza la etiqueta de estado con el color correspondiente.
     * 
     * @param conectado true si está conectado, false si está desconectado
     */
    public void establecerEstadoConectado(boolean conectado) {
        btnConectar.setEnabled(!conectado);
        btnDesconectar.setEnabled(conectado);
        txtMensaje.setEnabled(conectado);
        btnEnviar.setEnabled(conectado);
        txtNombre.setEnabled(!conectado);
        lblEstado.setText(conectado ? "Conectado" : "Desconectado");
        lblEstado.setForeground(conectado ? Color.GREEN : Color.RED);
    }
    
    /**
     * Limpia el contenido del campo de texto de mensajes.
     * Utilizado después de enviar un mensaje para preparar el campo
     * para el siguiente mensaje.
     */
    public void limpiarCampoMensaje() {
        txtMensaje.setText("");
    }
    
    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     * Utiliza un JOptionPane con icono de error para mostrar
     * mensajes de error al usuario.
     * 
     * @param mensaje El mensaje de error a mostrar
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra un mensaje informativo en un cuadro de diálogo.
     * Utiliza un JOptionPane con icono informativo para mostrar
     * mensajes informativos al usuario.
     * 
     * @param mensaje El mensaje informativo a mostrar
     */
    public void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Permite al usuario seleccionar un archivo mediante un cuadro de diálogo.
     * Configura un JFileChooser para buscar archivos en el directorio de datos
     * del proyecto y retorna la ruta del archivo seleccionado.
     * 
     * @param titulo El título del cuadro de diálogo de selección
     * @return La ruta completa del archivo seleccionado como String
     */
    public String retribuirArchivo(String titulo) {
        this.fc = new JFileChooser(System.getProperty("user.dir")+"/src/Jugador/edu/progavud/parcial2/data");
        fc.setDialogTitle(titulo);
        this.fc.showOpenDialog(fc);
        return fc.getSelectedFile().toString();
    }
}