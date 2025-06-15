package Servidor.edu.progavud.parcial2.vista;
import javax.swing.*;
import java.awt.*;

/**
 * Vista del servidor - Solo componentes gráficos, sin lógica de negocio.
 * No contiene ifs, fors, whiles ni try-catch.
 */
public class VentanaServidorChat extends JFrame {
    
    public JTextArea txtAreaLog;
    public JButton btnIniciar;
    public JButton btnDetener;
    public JLabel lblEstado;
    public JLabel lblClientesConectados;
    public JPanel panelClientes;
    
    public VentanaServidorChat() {
        initComponents();
    }
    
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
        
        // Panel izquierdo - Log
        txtAreaLog = new JTextArea(20, 30);
        txtAreaLog.setEditable(false);
        txtAreaLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        JScrollPane scrollLog = new JScrollPane(txtAreaLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log del Servidor"));
        
        // Panel derecho - Ventanas de clientes
        panelClientes = new JPanel();
        panelClientes.setLayout(new BoxLayout(panelClientes, BoxLayout.Y_AXIS));
        JScrollPane scrollClientes = new JScrollPane(panelClientes);
        scrollClientes.setBorder(BorderFactory.createTitledBorder("Chats de Clientes"));
        scrollClientes.setPreferredSize(new Dimension(400, 300));
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLog, scrollClientes);
        splitPane.setDividerLocation(400);
        
        add(panelControl, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    public void agregarLog(String mensaje) {
        txtAreaLog.append("[" + java.time.LocalTime.now() + "] " + mensaje + "\n");
        txtAreaLog.setCaretPosition(txtAreaLog.getDocument().getLength());
    }
    
    public void establecerEstadoServidor(boolean activo) {
        btnIniciar.setEnabled(!activo);
        btnDetener.setEnabled(activo);
        lblEstado.setText(activo ? "Servidor Activo" : "Servidor Detenido");
        lblEstado.setForeground(activo ? Color.GREEN : Color.RED);
    }
    
    public void actualizarContadorClientes(int cantidad) {
        lblClientesConectados.setText("Clientes: " + cantidad);
    }
    
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
    
    public void agregarVentanaCliente(JPanel ventanaChat) {
        panelClientes.add(ventanaChat);
        panelClientes.revalidate();
        panelClientes.repaint();
    }
    
    public void removerVentanaCliente(JPanel ventanaChat) {
        panelClientes.remove(ventanaChat);
        panelClientes.revalidate();
        panelClientes.repaint();
    }
    
    public void mostrarMensajeEnChat(String nombreCliente, String mensaje, JPanel ventanaChat) {
        JTextArea area = (JTextArea) ventanaChat.getClientProperty("areaChat");
        area.append(mensaje + "\n");
        area.setCaretPosition(area.getDocument().getLength());
    }
}