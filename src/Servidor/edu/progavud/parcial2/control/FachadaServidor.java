package Servidor.edu.progavud.parcial2.control;

import Servidor.edu.progavud.parcial2.vista.VentanaServidorChat;
import Servidor.edu.progavud.parcial2.vista.VentanaServidorJuego;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * Fachada del servidor que maneja toda la interfaz de usuario y eventos.
 * 
 * Esta clase implementa el patrón Facade, proporcionando una interfaz unificada
 * para todas las operaciones relacionadas con la interfaz gráfica del servidor:
 * - Gestión de eventos de botones del juego (40 cartas)
 * - Control de visibilidad de cartas y animaciones
 * - Manejo de ventanas de chat por cliente
 * - Control de estado de ventanas según número de jugadores
 * - Coordinación entre modelo de juego y vista
 * 
 * 
 * @author carlosmamut1
 * @author AlexM
 * @author batapop
 * @version 1.0
 */
public class FachadaServidor implements ActionListener {

    // ========== COMPONENTES PRINCIPALES ==========
    
    /** Referencia al controlador principal del servidor */
    private ControlPrincipalServidor cPrincipalS;
    
    /** Ventana principal del juego con tablero de 40 cartas */
    private VentanaServidorJuego vServidorJuego;
    
    /** Ventana de chat y administración del servidor */
    private VentanaServidorChat vServidorChat;
    
    // ========== CONTROL DE ESTADO ==========
    
    /** 
     * Flag de control para evitar procesamiento simultáneo de cartas.
     * Previene que se procesen clicks mientras se muestran animaciones
     * o se están validando parejas de cartas.
     */
    private boolean processingCards = false;

    /**
     * Activa los ActionListeners para todos los botones del juego.
     * 
     * Configura los eventos para:
     * - 40 botones del tablero de juego (cartas)
     * - Botón "Iniciar Juego"
     * 
     * Cada botón se configura para enviar eventos a este ActionListener.
     */
    public void activarActionListener() {
        // Configurar listeners para todos los botones del tablero
        JButton[] botones = this.vServidorJuego.getBotones();
        for (int i = 0; i < botones.length; i++) {
            botones[i].addActionListener(this);
        }
        
        // Configurar listener para botón de inicio de juego
        this.vServidorJuego.vServJuegoBtnInicia.addActionListener(this);
    }
    
    // ========== LÓGICA DE CARTAS Y ANIMACIONES ==========
    
    /**
     * Controla la mecánica principal de mostrar/ocultar cartas del juego.
     * 
     * Esta es la función core del juego que implementa:
     * 1. Control de concurrencia (evita clicks simultáneos)
     * 2. Gestión de estado de cartas seleccionadas
     * 3. Validación de parejas encontradas
     * 4. Animaciones de cartas no coincidentes (800ms delay)
     * 5. Reset de estado después de cada turno
     * 
     * Flujo de ejecución:
     * - Primer click: Guarda posición1, oculta carta
     * - Segundo click: Guarda posición2, oculta carta, valida pareja
     * - Si NO es pareja: Timer de 800ms para mostrar cartas nuevamente
     * - Si SÍ es pareja: Las cartas permanecen ocultas
     * 
     * @param button Botón/carta que fue presionado
     * @param numButtonPresionado Índice del botón en el array (0-39)
     */
    public void aparecerYDesaparecer(JButton button, int numButtonPresionado) {
        // Prevenir procesamiento si ya hay cartas siendo procesadas
        if (processingCards) {
            return;
        }
        
        // Obtener referencias necesarias
        int[] arregloDePosiciones = this.cPrincipalS.getArregloClicksYPosiciones();
        JButton[] botones = this.vServidorJuego.getBotones();
        
        // Ocultar la carta inmediatamente al hacer click
        button.setVisible(false);

        // Gestionar las dos posiciones del turno actual
        if (arregloDePosiciones[1] == -1) {
            // Primera carta del turno
            arregloDePosiciones[1] = numButtonPresionado;
        } else {
            // Segunda carta del turno - activar procesamiento
            arregloDePosiciones[2] = numButtonPresionado;
            processingCards = true;
        }

        // Validar pareja solo cuando se han seleccionado 2 cartas
        if (!this.cPrincipalS.validarSiHaAcertado() && arregloDePosiciones[0] % 2 == 0) {
            // NO HAY PAREJA: Programar timer para mostrar cartas nuevamente
            Timer timer = new Timer(800, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Restaurar visibilidad de ambas cartas después del delay
                    if (arregloDePosiciones[1] >= 0 && arregloDePosiciones[1] < botones.length) {
                        botones[arregloDePosiciones[1]].setVisible(true);
                    }
                    if (arregloDePosiciones[2] >= 0 && arregloDePosiciones[2] < botones.length) {
                        botones[arregloDePosiciones[2]].setVisible(true);
                    }
                    
                    // Reset del estado para el siguiente turno
                    arregloDePosiciones[1] = -1;
                    arregloDePosiciones[2] = -1;
                    processingCards = false;
                }
            });
            timer.setRepeats(false); // Solo ejecutar una vez
            timer.start();
            
        } else if (arregloDePosiciones[0] % 2 == 0) {
            // SÍ HAY PAREJA: Reset inmediato sin mostrar cartas
            arregloDePosiciones[1] = -1;
            arregloDePosiciones[2] = -1;
            processingCards = false;
        }
    }

    /**
     * Asigna imágenes a los botones del tablero según las posiciones del juego.
     * 
     * Este método sincroniza el modelo (posiciones de cartas) con la vista
     * (imágenes de los botones). Se llama durante la inicialización del juego.
     * 
     * @param numero Número/tipo de carta (0-19, representa la imagen a mostrar)
     * @param otroNumero Posición en el tablero (0-39, representa el botón)
     */
    public void setearImagenes(int numero, int otroNumero) {
        this.vServidorJuego.agregarFoto(numero, otroNumero);
    }

    // ========== CONTROL DE VENTANAS ==========

    /**
     * Muestra la ventana de juego cuando hay suficientes jugadores (≥2).
     * 
     * Se llama automáticamente desde ControlServidor cuando el número
     * de clientes conectados alcanza el mínimo requerido para jugar.
     */
    public void mostrarVentanaJuego() {
        if (vServidorJuego != null) {
            vServidorJuego.setVisible(true);
            vServidorJuego.toFront(); // Traer ventana al frente
        }
    }

    /**
     * Oculta la ventana de juego cuando no hay suficientes jugadores (<2).
     * 
     * Se llama automáticamente desde ControlServidor cuando el número
     * de clientes conectados cae por debajo del mínimo requerido.
     */
    public void ocultarVentanaJuego() {
        if (vServidorJuego != null) {
            vServidorJuego.setVisible(false);
        }
    }

    // ========== CONSTRUCTOR E INICIALIZACIÓN ==========

    /**
     * Constructor de la fachada del servidor.
     * 
     * Secuencia de inicialización:
     * 1. Crear ventana de juego (inicialmente oculta)
     * 2. Crear ventana de chat (siempre visible)
     * 3. Activar todos los ActionListeners
     * 4. Configurar listeners de botones de servidor
     * 
     * @param cPrincipalS Referencia al controlador principal del sistema
     */
    public FachadaServidor(ControlPrincipalServidor cPrincipalS) {
        this.cPrincipalS = cPrincipalS;
        
        // Crear ventana de juego (inicialmente oculta hasta tener ≥2 jugadores)
        this.vServidorJuego = new VentanaServidorJuego(this);
        // NO hacer visible automáticamente - se controla por número de clientes
        
        // Crear ventana de chat (siempre visible para administración)
        this.vServidorChat = new VentanaServidorChat(this);
        this.vServidorChat.setVisible(true);
        
        // Configurar todos los event listeners
        activarActionListener();
        this.vServidorChat.btnIniciar.addActionListener(this);
        this.vServidorChat.btnDetener.addActionListener(this);
    }

    // ========== CONFIGURACIÓN DE CHAT ==========

    /**
     * Configura los listeners para una ventana de chat de cliente específica.
     * 
     * Cada cliente conectado tiene su propia ventana de chat en el servidor.
     * Este método configura los eventos para el botón "Enviar" y el campo
     * de texto de esa ventana específica.
     * 
     * @param nombreCliente Nombre del cliente para identificar la ventana
     * @param ventanaChat JPanel que contiene los componentes de chat
     */
    public void configurarChatCliente(String nombreCliente, JPanel ventanaChat) {
        // Obtener componentes específicos de esta ventana de chat
        JButton btnEnviar = (JButton) ventanaChat.getClientProperty("btnEnviar");
        JTextField txtMensaje = (JTextField) ventanaChat.getClientProperty("txtMensaje");

        // Configurar listeners para envío de mensajes
        btnEnviar.addActionListener(this);   // Click en botón
        txtMensaje.addActionListener(this);  // Enter en campo de texto
    }

    /**
     * Procesa el envío de mensajes desde las ventanas de chat de clientes.
     * 
     * Busca en todas las ventanas de chat cuál generó el evento y procesa
     * el envío del mensaje correspondiente. Limpia el campo de texto después
     * del envío.
     * 
     * @param evt Evento de acción que contiene la fuente del evento
     */
    public void procesarEnvioMensajeCliente(ActionEvent evt) {
        // Buscar en todas las ventanas de chat activas
        for (int i = 0; i < vServidorChat.panelClientes.getComponentCount(); i++) {
            JPanel panelChat = (JPanel) vServidorChat.panelClientes.getComponent(i);
            JButton btnEnviar = (JButton) panelChat.getClientProperty("btnEnviar");
            JTextField txtMensaje = (JTextField) panelChat.getClientProperty("txtMensaje");

            // Verificar si este panel generó el evento
            if (evt.getSource() == btnEnviar || evt.getSource() == txtMensaje) {
                // Obtener datos y enviar mensaje
                String nombreCliente = (String) panelChat.getClientProperty("nombreCliente");
                String mensaje = txtMensaje.getText();

                this.cPrincipalS.enviarMensajeACliente(nombreCliente, mensaje);
                txtMensaje.setText(""); // Limpiar campo después del envío
                break;
            }
        }
    }

    // ========== MANEJO PRINCIPAL DE EVENTOS ==========

    /**
     * Método principal que maneja todos los eventos de la interfaz de usuario.
     * 
     * Este método centraliza el manejo de eventos para:
     * - Botón "Iniciar Juego"
     * - 40 botones del tablero de cartas
     * - Botones "Iniciar/Detener Servidor"
     * - Envío de mensajes en chats de clientes
     * 
     * Utiliza ActionCommand para identificar qué componente generó el evento.
     * 
     * @param e Evento de acción generado por algún componente de la UI
     */
    public void actionPerformed(ActionEvent e) {
        JButton[] botones = this.vServidorJuego.getBotones();

        // ========== CONTROL DE INICIO DE JUEGO ==========
        if (e.getActionCommand().equals("iniciaJuego")) {
            // Verificar que hay suficientes jugadores para iniciar
            if (this.cPrincipalS.getcServidor().getNumeroClientesConectados() >= 2) {
                // Iniciar juego oficialmente
                this.cPrincipalS.iniciarJuego();
                
                // Deshabilitar botón para evitar múltiples inicios
                this.vServidorJuego.vServJuegoBtnInicia.setEnabled(false);
                this.vServidorJuego.vServJuegoBtnInicia.setText("Juego Iniciado");
                
                // Notificar en el chat del servidor
                this.vServidorChat.mostrarError("Juego iniciado, No se aceptarán más jugadores");
            } else {
                // Error: no hay suficientes jugadores
                this.vServidorChat.mostrarError("Se necesitan al menos 2 jugadores para iniciar el juego.");
            }
            return; // Salir para no procesar otros eventos
        }
        
        // ========== CONTROL DE PROCESAMIENTO DE CARTAS ==========
        
        // Prevenir clicks durante procesamiento de animaciones
        if (processingCards) {
            return;
        }
        
        // ========== MANEJO DE BOTONES DEL TABLERO (40 CARTAS) ==========
        // Cada botón tiene un ActionCommand numérico del "1" al "40"
        
        if (e.getActionCommand().equals("1")) {
            if (!botones[0].isVisible()) return; // Carta ya oculta
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++; // Incrementar contador de clicks
            aparecerYDesaparecer(botones[0], 0);
        }
        if (e.getActionCommand().equals("2")) {
            if (!botones[1].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[1], 1);
        }
        if (e.getActionCommand().equals("3")) {
            if (!botones[2].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[2], 2);
        }
        if (e.getActionCommand().equals("4")) {
            if (!botones[3].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[3], 3);
        }
        if (e.getActionCommand().equals("5")) {
            if (!botones[4].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[4], 4);
        }
        if (e.getActionCommand().equals("6")) {
            if (!botones[5].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[5], 5);
        }
        if (e.getActionCommand().equals("7")) {
            if (!botones[6].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[6], 6);
        }
        if (e.getActionCommand().equals("8")) {
            if (!botones[7].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[7], 7);
        }
        if (e.getActionCommand().equals("9")) {
            if (!botones[8].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[8], 8);
        }
        if (e.getActionCommand().equals("10")) {
            if (!botones[9].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[9], 9);
        }
        if (e.getActionCommand().equals("11")) {
            if (!botones[10].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[10], 10);
        }
        if (e.getActionCommand().equals("12")) {
            if (!botones[11].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[11], 11);
        }
        if (e.getActionCommand().equals("13")) {
            if (!botones[12].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[12], 12);
        }
        if (e.getActionCommand().equals("14")) {
            if (!botones[13].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[13], 13);
        }
        if (e.getActionCommand().equals("15")) {
            if (!botones[14].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[14], 14);
        }
        if (e.getActionCommand().equals("16")) {
            if (!botones[15].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[15], 15);
        }
        if (e.getActionCommand().equals("17")) {
            if (!botones[16].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[16], 16);
        }
        if (e.getActionCommand().equals("18")) {
            if (!botones[17].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[17], 17);
        }
        if (e.getActionCommand().equals("19")) {
            if (!botones[18].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[18], 18);
        }
        if (e.getActionCommand().equals("20")) {
            if (!botones[19].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[19], 19);
        }
        if (e.getActionCommand().equals("21")) {
            if (!botones[20].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[20], 20);
        }
        if (e.getActionCommand().equals("22")) {
            if (!botones[21].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[21], 21);
        }
        if (e.getActionCommand().equals("23")) {
            if (!botones[22].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[22], 22);
        }
        if (e.getActionCommand().equals("24")) {
            if (!botones[23].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[23], 23);
        }
        if (e.getActionCommand().equals("25")) {
            if (!botones[24].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[24], 24);
        }
        if (e.getActionCommand().equals("26")) {
            if (!botones[25].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[25], 25);
        }
        if (e.getActionCommand().equals("27")) {
            if (!botones[26].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[26], 26);
        }
        if (e.getActionCommand().equals("28")) {
            if (!botones[27].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[27], 27);
        }
        if (e.getActionCommand().equals("29")) {
            if (!botones[28].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[28], 28);
        }
        if (e.getActionCommand().equals("30")) {
            if (!botones[29].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[29], 29);
        }
        if (e.getActionCommand().equals("31")) {
            if (!botones[30].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[30], 30);
        }
        if (e.getActionCommand().equals("32")) {
            if (!botones[31].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[31], 31);
        }
        if (e.getActionCommand().equals("33")) {
            if (!botones[32].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[32], 32);
        }
        if (e.getActionCommand().equals("34")) {
            if (!botones[33].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[33], 33);
        }
        if (e.getActionCommand().equals("35")) {
            if (!botones[34].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[34], 34);
        }
        if (e.getActionCommand().equals("36")) {
            if (!botones[35].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[35], 35);
        }
        if (e.getActionCommand().equals("37")) {
            if (!botones[36].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[36], 36);
        }
        if (e.getActionCommand().equals("38")) {
            if (!botones[37].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[37], 37);
        }
        if (e.getActionCommand().equals("39")) {
            if (!botones[38].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[38], 38);
        }
        if (e.getActionCommand().equals("40")) {
            if (!botones[39].isVisible()) return;
            this.cPrincipalS.getArregloClicksYPosiciones()[0]++;
            aparecerYDesaparecer(botones[39], 39);
        }

        // ========== CONTROL DEL SERVIDOR DE RED ==========
        
        if (e.getActionCommand().equals("iniciarServ")) {
            // Iniciar servidor de comunicaciones
            try {
                this.cPrincipalS.iniciarServidor();
            } catch (Exception ex) {
                // Error silencioso - la interfaz ya muestra el estado
            }
        } 
        else if (e.getActionCommand().equals("detenerServ")) {
            // Detener servidor de comunicaciones
            this.cPrincipalS.detenerServidor();
        } 
        else {
            // ========== MANEJO DE CHAT DE CLIENTES ==========
            // Si no es ningún comando conocido, debe ser envío de mensaje
            procesarEnvioMensajeCliente(e);
        }
    }

    // ========== GETTERS ==========

    /**
     * Obtiene la ventana de juego.
     * 
     * @return Instancia de la ventana del juego con tablero
     */
    public VentanaServidorJuego getvServidorJuego() {
        return vServidorJuego;
    }

    /**
     * Obtiene la ventana de chat y administración.
     * 
     * @return Instancia de la ventana de chat del servidor
     */
    public VentanaServidorChat getvServidorChat() {
        return vServidorChat;
    }
}