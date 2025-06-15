package Jugador.edu.progavud.parcial2.vista;
import javax.swing.*;
import java.awt.*;

/**
 * Vista del cliente - Solo componentes gráficos, sin lógica de negocio.
 * No contiene ifs, fors, whiles ni try-catch.
 */
public class VentanaCliente extends JFrame {
    
    public JTextField txtNombre;
    public JButton btnConectar;
    public JButton btnDesconectar;
    public JTextArea txtAreaChat;
    public JTextField txtMensaje;
    public JButton btnEnviar;
    public JLabel lblEstado;
    private JFileChooser fc;
    
    public VentanaCliente() {
        initComponents();
    }
    
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
    
    public void mostrarMensaje(String mensaje) {
        txtAreaChat.append(mensaje + "\n");
        txtAreaChat.setCaretPosition(txtAreaChat.getDocument().getLength());
    }
    
    public void establecerEstadoConectado(boolean conectado) {
        btnConectar.setEnabled(!conectado);
        btnDesconectar.setEnabled(conectado);
        txtMensaje.setEnabled(conectado);
        btnEnviar.setEnabled(conectado);
        txtNombre.setEnabled(!conectado);
        lblEstado.setText(conectado ? "Conectado" : "Desconectado");
        lblEstado.setForeground(conectado ? Color.GREEN : Color.RED);
    }
    
    public void limpiarCampoMensaje() {
        txtMensaje.setText("");
    }
    
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public String retribuirArchivo(String titulo) {
        this.fc = new JFileChooser(System.getProperty("user.dir")+"/src/Jugador/edu/progavud/parcial2/data");
        fc.setDialogTitle(titulo);
        this.fc.showOpenDialog(fc);
        return fc.getSelectedFile().toString();
    }
}