package Servidor.edu.progavud.parcial2.control;

import Servidor.edu.progavud.parcial2.modelo.ConexionPropiedades;
import Servidor.edu.progavud.parcial2.modelo.ConexionPropiedadesDB;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Controlador principal del servidor que coordina todos los subsistemas.
 * 
 * Esta clase actúa como el núcleo central del servidor, coordinando:
 * - Gestión de jugadores y autenticación
 * - Lógica del juego y sistema de turnos
 * - Comunicaciones de red y manejo de clientes
 * - Interfaz de usuario del servidor
 * - Configuración y propiedades del sistema
 * - Ciclo de vida completo de las partidas
 * 
 * 
 * @author carlosmamut1
 * @author AlexM
 * @author batapop
 * @version 1.0
 */
public class ControlPrincipalServidor {
    
    // ========== COMPONENTES PRINCIPALES ==========
    
    /** Fachada que maneja la interfaz de usuario del servidor */
    private FachadaServidor fachadaS;
    
    /** Controlador específico para gestión de jugadores */
    private ControlJugador cJugador;
    
    /** Controlador específico para lógica del juego */
    private ControlJuego cJuego;
    
    /** Controlador de comunicaciones de red */
    private ControlServidor cServidor;
    
    // ========== CONFIGURACIÓN Y PROPIEDADES ==========
    
    /** Manejo de propiedades de base de datos */
    private ConexionPropiedadesDB cnxPropiedadesDB;
    
    /** Manejo de propiedades generales */
    private ConexionPropiedades cnxPropiedades;
    
    // ========== ESTADO DEL JUEGO ==========
    
    /** Indica si la partida actual ha terminado */
    private boolean juegoTerminado;
    
    /** Lista de nombres de jugadores participando en la partida actual */
    private ArrayList<String> jugadoresEnJuego;
    
    /** Índice del jugador que tiene el turno actual (0 = primer jugador) */
    private int turnoActual;
    
    /** Nombre del jugador que actualmente tiene el turno */
    private String jugadorActual;
    
    // ========== ESTADÍSTICAS DE JUEGO ==========
    
    /** Array con aciertos de cada jugador (índice corresponde a turnoActual) */
    private int[] aciertosJugadores;
    
    /** Array con intentos totales de cada jugador (parejas intentadas) */
    private int[] intentosJugadores;
    
    /** Total de parejas en el juego (siempre 20 para tablero de 40 cartas) */
    private int totalParejas;
    
    /** 
     * Array de control de clicks: [total_clicks, posicion1, posicion2]
     * - posicion1: Primera carta seleccionada en el turno (-1 si no hay)
     * - posicion2: Segunda carta seleccionada en el turno (-1 si no hay)
     */
    private int[] arregloClicksYPosiciones;
    
    // ========== MÉTODOS DE CONFIGURACIÓN ==========
    
    /**
     * Carga los datos de configuración de la base de datos desde archivo properties.
     * 
     * Lee el archivo de propiedades del servidor y configura:
     * - Usuario de base de datos
     * - Contraseña de base de datos  
     * - URL de conexión a base de datos
     * - Puerto del servidor de red
     * 
     * En caso de error, muestra mensaje de error pero continúa la ejecución.
     */
    public void cargarDatosALaConexionSQL() {
        // Array para almacenar los 4 parámetros de configuración
        String[] datosDelServer = new String[4];
        
        try {
            // Cargar datos desde archivo properties del servidor
            datosDelServer = this.cnxPropiedadesDB.cargarFile(
                this.fachadaS.getvServidorChat().retribuirArchivo("ArchivoPropertiesServer")
            );
        } catch (Exception ex) {
            // Error no crítico - mostrar mensaje pero continuar
            this.fachadaS.getvServidorChat().mostrarError("Hubo un error al intentar cargar los datos para la CnxSQL");
        }

        // Configurar parámetros de conexión SQL en el DAO
        this.cJugador.getJugadorDAO().getCnxSQL().setUsuario(datosDelServer[0]);
        this.cJugador.getJugadorDAO().getCnxSQL().setContrasena(datosDelServer[1]);
        this.cJugador.getJugadorDAO().getCnxSQL().setURLBD(datosDelServer[2]);
        
        // Configurar puerto del servidor de red
        this.cServidor.setPuertoServ(datosDelServer[3]);
    }

    /**
     * Configura las imágenes en la interfaz según las posiciones del juego.
     * 
     * Recorre todas las posiciones del tablero y asigna a cada botón
     * la imagen correspondiente según el número de carta en esa posición.
     * Este método sincroniza el modelo (posiciones) con la vista (botones).
     */
    public void setearImagenesEnControl() {
        // Obtener array de posiciones desde el controlador del juego
        int[] posiciones = this.cJuego.getPosiciones();
        
        // Asignar imagen a cada botón según su posición
        for(int i = 0; i < posiciones.length; i++) {
           // posiciones[i] = número de la carta, i = posición del botón
           this.fachadaS.setearImagenes(posiciones[i], i);
        }
    }

    // ========== LÓGICA PRINCIPAL DEL JUEGO ==========

    /**
     * Valida si el jugador actual ha acertado una pareja de cartas.
     * 
     * Este método implementa la lógica central del juego:
     * - Verifica si se han seleccionado 2 cartas (clicks par)
     * - Compara si las cartas forman pareja
     * - Actualiza estadísticas de aciertos/intentos
     * - Gestiona cambios de turno
     * - Verifica condiciones de fin de juego
     * 
     * @return true si el jugador acertó una pareja, false en caso contrario
     */
    public boolean validarSiHaAcertado() {
        // Verificar si se completó un turno (clicks par) y las cartas coinciden
        if(arregloClicksYPosiciones[0] % 2 == 0 && 
           this.cJuego.getPosiciones()[arregloClicksYPosiciones[1]] == 
           this.cJuego.getPosiciones()[arregloClicksYPosiciones[2]]) {
            
            // ACIERTO: Incrementar tanto aciertos como intentos
            if (turnoActual < aciertosJugadores.length) {
                aciertosJugadores[turnoActual]++;
                intentosJugadores[turnoActual]++;
                
                // Verificar si el juego ha terminado (todas las parejas encontradas)
                verificarFinDelJuego();
            }
            return true;
            
        } else {
            // FALLO: Solo incrementar intentos y cambiar turno
            if (arregloClicksYPosiciones[0] % 2 == 0) {
                // Solo contar como intento cuando se completa el par de cartas
                if (turnoActual < intentosJugadores.length) {
                    intentosJugadores[turnoActual]++;
                }
                // El jugador pierde su turno al fallar
                cambiarTurno();
            }
        }
        return false;
    }

    /**
     * Cambia el turno al siguiente jugador en la secuencia.
     * 
     * Implementa rotación circular entre jugadores:
     * Jugador1 -> Jugador2 -> Jugador3 -> Jugador1 -> ...
     * 
     * También actualiza la interfaz para mostrar quién tiene el turno actual.
     */
    public void cambiarTurno() {
        // No cambiar turno si el juego terminó o no hay jugadores
        if (juegoTerminado || jugadoresEnJuego.isEmpty()) {
            return;
        }
        
        // Rotación circular: (turno + 1) % total_jugadores
        turnoActual = (turnoActual + 1) % jugadoresEnJuego.size();
        jugadorActual = jugadoresEnJuego.get(turnoActual);
        
        // Actualizar interfaz para mostrar el nuevo turno
        fachadaS.getvServidorJuego().actualizarTurno(jugadorActual);
        this.cServidor.enviarMensajeACliente(jugadorActual, "Tu Turno");
    }
    
    /**
     * Verifica si se han encontrado todas las parejas y el juego debe terminar.
     * 
     * Calcula el total de aciertos de todos los jugadores y lo compara
     * con el total de parejas disponibles (20). Si coinciden, termina el juego.
     */
    public void verificarFinDelJuego() {
        // Sumar aciertos de todos los jugadores
        int totalAciertos = 0;
        for (int aciertos : aciertosJugadores) {
            totalAciertos += aciertos;
        }
        
        // Si se encontraron todas las parejas, terminar juego
        if (totalAciertos >= totalParejas) {
            juegoTerminado = true;
            determinarGanadorYCerrar();
        }
    }
    
    /**
     * Determina el ganador basado en porcentaje de efectividad y cierra el juego.
     * 
     * Algoritmo de determinación de ganador:
     * 1. Calcula porcentaje de efectividad para cada jugador (aciertos/intentos * 100)
     * 2. Determina ganador por mejor porcentaje
     * 3. Detecta empates (diferencias menores al 0.01%)
     * 4. Muestra resultados en ventana modal
     * 5. Cierra toda la aplicación
     */
    public void determinarGanadorYCerrar() {
        double mejorPorcentaje = 0.0;
        String ganador = "";
        boolean empate = false;
        
        // Construir mensaje de resultados detallado
        StringBuilder mensajeResultado = new StringBuilder();
        mensajeResultado.append("=== JUEGO TERMINADO ===\n");
        mensajeResultado.append("Resultados finales:\n\n");
        
        // Calcular estadísticas para cada jugador
        for (int i = 0; i < jugadoresEnJuego.size(); i++) {
            int aciertos = aciertosJugadores[i];
            int intentos = intentosJugadores[i];
            
            // Calcular porcentaje de efectividad (evitar división por cero)
            double porcentaje = (intentos > 0) ? ((double) aciertos / intentos) * 100 : 0.0;
            
            // Agregar estadísticas del jugador al mensaje
            mensajeResultado.append(jugadoresEnJuego.get(i))
                           .append(": ")
                           .append(aciertos)
                           .append(" aciertos / ")
                           .append(intentos)
                           .append(" intentos = ")
                           .append(String.format("%.1f", porcentaje))
                           .append("%\n");
            
            // Determinar ganador por mejor porcentaje
            if (porcentaje > mejorPorcentaje) {
                mejorPorcentaje = porcentaje;
                ganador = jugadoresEnJuego.get(i);
                empate = false;
            } else if (Math.abs(porcentaje - mejorPorcentaje) < 0.01 && mejorPorcentaje > 0) {
                // Detectar empate (diferencia menor al 0.01%)
                empate = true;
            }
        }
        
        // Agregar resultado final al mensaje
        mensajeResultado.append("\n");
        if (empate) {
            mensajeResultado.append("¡EMPATE!");
        } else {
            mensajeResultado.append("¡GANADOR: ").append(ganador).append("!")
                          .append("\nCon ").append(String.format("%.1f", mejorPorcentaje)).append("% de efectividad");
        }
        
        // Mostrar resultados en ventana modal
        String mensajeFinal = mensajeResultado.toString();
        JOptionPane.showMessageDialog(null, mensajeFinal, "¡Fin del Juego!", JOptionPane.INFORMATION_MESSAGE);
        
        // Cerrar toda la aplicación
        cerrarAplicativo();
    }
    
    /**
     * Cierra todas las ventanas y termina completamente la aplicación.
     * 
     * Secuencia de cierre:
     * 1. Detener servidor de red
     * 2. Cerrar ventana de juego
     * 3. Cerrar ventana de chat
     * 4. Terminar proceso de JVM
     */
    public void cerrarAplicativo() {
        try {
            // Detener servidor de comunicaciones
            detenerServidor();
            
            // Cerrar ventanas de interfaz
            if (fachadaS.getvServidorJuego() != null) {
                fachadaS.getvServidorJuego().dispose();
            }
            if (fachadaS.getvServidorChat() != null) {
                fachadaS.getvServidorChat().dispose();
            }
            
            // Terminar aplicación completamente
            System.exit(0);
            
        } catch (Exception e) {
            // En caso de error, forzar cierre inmediato
            System.exit(0);
        }
    }
    
    // ========== GESTIÓN DE TURNOS ==========
    
    /**
     * Inicializa el sistema de turnos al comenzar una partida.
     * 
     * Configuración inicial:
     * - Obtiene lista de jugadores conectados
     * - Establece el primer jugador como turno inicial
     * - Inicializa arrays de estadísticas (aciertos e intentos)
     * - Configura total de parejas (20)
     * - Actualiza interfaz con turno actual
     */
    public void inicializarSistemaTurnos() {
        // Obtener jugadores actualmente conectados
        jugadoresEnJuego = new ArrayList<>(cServidor.obtenerNombresJugadores());
        
        if (!jugadoresEnJuego.isEmpty()) {
            // Inicializar sistema de turnos
            turnoActual = 0;
            jugadorActual = jugadoresEnJuego.get(0);
            
            // Inicializar arrays de estadísticas para cada jugador
            aciertosJugadores = new int[jugadoresEnJuego.size()];
            intentosJugadores = new int[jugadoresEnJuego.size()];
            
            // Configurar estado inicial del juego
            juegoTerminado = false;
            totalParejas = 20; // 40 cartas = 20 parejas
            
            // Actualizar interfaz con el primer turno
            fachadaS.getvServidorJuego().actualizarTurno(jugadorActual);
            this.cServidor.enviarMensajeACliente(jugadorActual, "Tu turno");
        }
    }
    
    /**
     * Inicia oficialmente el juego y bloquea nuevos clientes.
     * 
     * Este método marca el punto de no retorno - una vez iniciado el juego,
     * no se permiten más conexiones de clientes nuevos.
     */
    public void iniciarJuego() {
        // Bloquear nuevos clientes en el servidor de red
        this.cServidor.iniciarJuego();
        
        // Inicializar sistema de turnos con jugadores conectados
        inicializarSistemaTurnos();
    }
    
    // ========== CONSTRUCTOR E INICIALIZACIÓN ==========
    
    /**
     * Constructor principal del servidor.
     * 
     * Secuencia de inicialización:
     * 1. Crear componentes de configuración
     * 2. Crear fachada e interfaces
     * 3. Crear controladores especializados
     * 4. Cargar configuración de base de datos
     * 5. Cargar y crear jugadores desde properties
     * 6. Inicializar juego (tablero, cartas, imágenes)
     * 7. Configurar variables de control
     */
    public ControlPrincipalServidor() {
        // === FASE 1: Componentes básicos ===
        this.cnxPropiedadesDB = new ConexionPropiedadesDB();
        this.fachadaS = new FachadaServidor(this);
        
        // === FASE 2: Controladores especializados ===
        this.cServidor = new ControlServidor(this);
        this.cJugador = new ControlJugador(this);
        
        // === FASE 3: Configuración de base de datos ===
        cargarDatosALaConexionSQL();
        
        // === FASE 4: Configuración de jugadores ===
        this.cnxPropiedades = new ConexionPropiedades();
        metodoCrearJugadoresDeProperties();
        
        // === FASE 5: Configuración del juego ===
        this.cJuego = new ControlJuego(this);
        this.cJuego.setearPosicionesIniciales(); // Crear y aleatorizar tablero
        setearImagenesEnControl(); // Sincronizar imágenes con posiciones
        
        // === FASE 6: Variables de control ===
        this.jugadoresEnJuego = new ArrayList<>();
        this.turnoActual = 0;
        this.juegoTerminado = false;
        this.arregloClicksYPosiciones = new int[]{0, -1, -1}; // [contador, pos1, pos2]
    }
    
    /**
     * Crea jugadores en base de datos desde archivo de propiedades.
     * 
     * Lee archivo properties con jugadores predefinidos y los inserta en BD.
     * Formato esperado: pares de contraseña,nombreUsuario
     * 
     * Manejo de errores:
     * - SQLIntegrityConstraintViolationException: Jugador ya existe
     * - IOException/SQLException: Error de archivo o BD
     */
    public void metodoCrearJugadoresDeProperties() {
        try {
            // Obtener ruta del archivo de jugadores
            String link = this.fachadaS.getvServidorChat().retribuirArchivo("ArchivoPropiedadesJugadores");
            
            // Cargar datos del archivo properties
            String[] datosRetribuidosDeJugador = this.cnxPropiedades.cargarFile(link);
            
            // Procesar jugadores en pares (contraseña, nombreUsuario)
            for (int i = 0; i < (datosRetribuidosDeJugador.length) / 2; i++) {
                String contrasena = datosRetribuidosDeJugador[2 * i]; 
                String nombreUsuario = datosRetribuidosDeJugador[(2 * i) + 1];
                
                // Crear jugador en memoria
                this.cJugador.crearJugador(contrasena, nombreUsuario);
                
                // Persistir jugador en base de datos
                this.cJugador.getJugadorDAO().insertarDatosDeLosJugadores(this.cJugador.getJugador());
            }
        } catch (SQLIntegrityConstraintViolationException exp) {
            // Error esperado: jugador ya existe en BD
            this.fachadaS.getvServidorChat().mostrarError("Alguno de los jugadores del archivo propiedades ya está en la base de datos");
        } catch (IOException | SQLException ex) {
            // Error crítico: problema con archivo o BD
            this.fachadaS.getvServidorChat().mostrarError("No se pudieron rescatar los datos del jugador");
        }
    }
    
    // ========== MÉTODOS DE COMUNICACIÓN ==========
    
    /**
     * Envía un mensaje a un cliente específico.
     * 
     * @param nombreUsuario Nombre del cliente destinatario
     * @param mensaje Contenido del mensaje a enviar
     */
    public void enviarMensajeACliente(String nombreUsuario, String mensaje) {
        this.cServidor.enviarMensajeACliente(nombreUsuario, mensaje);
    }
    
    /**
     * Inicia el servidor de comunicaciones de red.
     * 
     * @throws Exception Si hay error al inicializar el servidor
     */
    public void iniciarServidor() throws Exception {
        this.cServidor.iniciarServidor();
    }
    
    /**
     * Detiene el servidor de comunicaciones de red.
     */
    public void detenerServidor() {
        this.cServidor.detenerServidor();
    }

    // ========== GETTERS Y SETTERS ==========
    
    public FachadaServidor getFachadaS() {
        return fachadaS;
    }

    public ControlJugador getcJugador() {
        return cJugador;
    }

    public void setcJugador(ControlJugador cJugador) {
        this.cJugador = cJugador;
    }
    
    public ControlServidor getcServidor() {
        return cServidor;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }

    public ArrayList<String> getJugadoresEnJuego() {
        return jugadoresEnJuego;
    }

    public void setJugadoresEnJuego(ArrayList<String> jugadoresEnJuego) {
        this.jugadoresEnJuego = jugadoresEnJuego;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }

    public String getJugadorActual() {
        return jugadorActual;
    }

    public void setJugadorActual(String jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    public int[] getAciertosJugadores() {
        return aciertosJugadores;
    }

    public void setAciertosJugadores(int[] aciertosJugadores) {
        this.aciertosJugadores = aciertosJugadores;
    }

    public int[] getIntentosJugadores() {
        return intentosJugadores;
    }

    public void setIntentosJugadores(int[] intentosJugadores) {
        this.intentosJugadores = intentosJugadores;
    }

    public int getTotalParejas() {
        return totalParejas;
    }

    public void setTotalParejas(int totalParejas) {
        this.totalParejas = totalParejas;
    }

    public int[] getArregloClicksYPosiciones() {
        return arregloClicksYPosiciones;
    }

    public void setArregloClicksYPosiciones(int[] arregloClicksYPosiciones) {
        this.arregloClicksYPosiciones = arregloClicksYPosiciones;
    }

    public ControlJuego getcJuego() {
        return cJuego;
    }

    public void setcJuego(ControlJuego cJuego) {
        this.cJuego = cJuego;
    }
}