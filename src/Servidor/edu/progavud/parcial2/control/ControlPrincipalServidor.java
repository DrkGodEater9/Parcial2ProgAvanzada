package Servidor.edu.progavud.parcial2.control;

import Servidor.edu.progavud.parcial2.modelo.ConexionPropiedades;
import Servidor.edu.progavud.parcial2.modelo.ConexionPropiedadesDB;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

/**
 *
 * @author a
 */
public class ControlPrincipalServidor {
    private FachadaServidor fachadaS;
    private ControlJugador cJugador;
    private ControlJuego cJuego;
    private ControlServidor cServidor;
    private ConexionPropiedadesDB cnxPropiedadesDB;
    private ConexionPropiedades cnxPropiedades;
    private boolean juegoTerminado;
    private ArrayList<String> jugadoresEnJuego;
    private int turnoActual;
    private String jugadorActual;
    private int[] aciertosJugadores; // Array para guardar aciertos de cada jugador
    private int totalParejas; // Total de parejas en el juego (depende del número de cartas)
    private int[] arregloClicksYPosiciones;
    
    public void cargarDatosALaConexionSQL() {
        String[] datosDelServer = new String[4];
        try {
            datosDelServer = this.cnxPropiedadesDB.cargarFile(this.fachadaS.getvServidorChat().retribuirArchivo("ArchivoPropertiesServer"));
        } catch (Exception ex) {
            this.fachadaS.getvServidorChat().mostrarError("Hubo un error al intentar cargar los datos para la CnxSQL");
        }

        this.cJugador.getJugadorDAO().getCnxSQL().setUsuario(datosDelServer[0]);
        this.cJugador.getJugadorDAO().getCnxSQL().setContrasena(datosDelServer[1]);
        this.cJugador.getJugadorDAO().getCnxSQL().setURLBD(datosDelServer[2]);
        this.cServidor.setPuertoServ(datosDelServer[3]);
    }

    public void setearImagenesEnControl() {
        int[] posiciones = this.cJuego.getPosiciones();
        for(int i = 0; i < posiciones.length; i++) {
           this.fachadaS.setearImagenes(posiciones[i],i);
        }
    }

    public boolean validarSiHaAcertado() {
        if(arregloClicksYPosiciones[0] % 2 == 0 && this.cJuego.getPosiciones()[arregloClicksYPosiciones[1]] == this.cJuego.getPosiciones()[arregloClicksYPosiciones[2]]) {
            // INCREMENTAR ACIERTOS DEL JUGADOR ACTUAL
            if (turnoActual < aciertosJugadores.length) {
                aciertosJugadores[turnoActual]++;
                
                // Notificar acierto a todos los jugadores
                String mensaje = "¡" + jugadorActual + " encontró una pareja! Aciertos: " + aciertosJugadores[turnoActual];
                cServidor.enviarMensajeATodosLosClientes(mensaje);
                
                // Actualizar interfaz del servidor
                fachadaS.getvServidorChat().mostrarError(mensaje);
                
                // Verificar si el juego ha terminado
                verificarFinDelJuego();
            }
            return true;
        } else {
            // NOTIFICAR FALLO Y CAMBIAR TURNO
            if (arregloClicksYPosiciones[0] % 2 == 0) {
                String mensaje = jugadorActual + " no acertó. Turno perdido.";
                cServidor.enviarMensajeATodosLosClientes(mensaje);
                fachadaS.getvServidorChat().mostrarError(mensaje);
                
                cambiarTurno();
            }
        }
        return false;
    }

    public void cambiarTurno() {
        if (juegoTerminado || jugadoresEnJuego.isEmpty()) {
            return;
        }
        
        turnoActual = (turnoActual + 1) % jugadoresEnJuego.size();
        jugadorActual = jugadoresEnJuego.get(turnoActual);
        
        // Notificar a todos los jugadores sobre el cambio de turno
        String mensajeTurno = "Turno de: " + jugadorActual;
        cServidor.enviarMensajeATodosLosClientes(mensajeTurno);
        fachadaS.getvServidorChat().mostrarError(mensajeTurno);
    }
    
    /**
     * Verifica si el juego ha terminado
     */
    public void verificarFinDelJuego() {
        // Calcular total de aciertos
        int totalAciertos = 0;
        for (int aciertos : aciertosJugadores) {
            totalAciertos += aciertos;
        }
        
        // Si se encontraron todas las parejas, el juego termina
        if (totalAciertos >= totalParejas) {
            juegoTerminado = true;
            determinarGanador();
        }
    }
    
    public void determinarGanador() {
        int maxAciertos = 0;
        String ganador = "";
        boolean empate = false;
        
        // Encontrar el máximo número de aciertos
        for (int i = 0; i < aciertosJugadores.length; i++) {
            if (aciertosJugadores[i] > maxAciertos) {
                maxAciertos = aciertosJugadores[i];
                ganador = jugadoresEnJuego.get(i);
                empate = false;
            } else if (aciertosJugadores[i] == maxAciertos && maxAciertos > 0) {
                empate = true;
            }
        }
        
        // Crear mensaje de resultado
        StringBuilder mensajeResultado = new StringBuilder();
        mensajeResultado.append("=== JUEGO TERMINADO ===\n");
        mensajeResultado.append("Resultados finales:\n");
        
        for (int i = 0; i < jugadoresEnJuego.size(); i++) {
            mensajeResultado.append(jugadoresEnJuego.get(i))
                           .append(": ")
                           .append(aciertosJugadores[i])
                           .append(" aciertos\n");
        }
        
        if (empate) {
            mensajeResultado.append("¡EMPATE!");
        } else {
            mensajeResultado.append("¡GANADOR: ").append(ganador).append("!");
        }
        
        // Enviar resultado a todos los jugadores y mostrar en servidor
        String mensajeFinal = mensajeResultado.toString();
        cServidor.enviarMensajeATodosLosClientes(mensajeFinal);
        fachadaS.getvServidorChat().mostrarError(mensajeFinal);
    }
    
    public void inicializarSistemaTurnos() {
        jugadoresEnJuego = new ArrayList<>(cServidor.obtenerNombresJugadores());
        if (!jugadoresEnJuego.isEmpty()) {
            turnoActual = 0;
            jugadorActual = jugadoresEnJuego.get(0);
            aciertosJugadores = new int[jugadoresEnJuego.size()];
            juegoTerminado = false;
            
            // Calcular total de parejas (40 cartas = 20 parejas)
            totalParejas = 20;
            
            // Notificar inicio de turnos
            String mensajeInicio = "¡Juego iniciado! Turno de: " + jugadorActual;
            cServidor.enviarMensajeATodosLosClientes(mensajeInicio);
            fachadaS.getvServidorChat().mostrarError(mensajeInicio);
        }
    }
    
    /**
     * MÉTODO ACTUALIZADO - Inicia el juego y bloquea nuevos clientes
     */
    public void iniciarJuego() {
        // Bloquear nuevos clientes
        this.cServidor.iniciarJuego();
        
        // Inicializar sistema de turnos
        inicializarSistemaTurnos();
    }
    
    public ControlPrincipalServidor() {
        this.cnxPropiedadesDB = new ConexionPropiedadesDB();
        this.fachadaS = new FachadaServidor(this);
        this.cServidor= new ControlServidor(this);
        this.cJugador= new ControlJugador(this);
        cargarDatosALaConexionSQL();
        this.cnxPropiedades = new ConexionPropiedades();
        metodoCrearJugadoresDeProperties();
        
        this.cJuego = new ControlJuego(this);
        this.cJuego.setearPosicionesIniciales();
        setearImagenesEnControl();
        
        // Inicializar variables de juego
        this.jugadoresEnJuego = new ArrayList<>();
        this.turnoActual = 0;
        this.juegoTerminado = false;
        this.arregloClicksYPosiciones = new int[]{0, -1, -1}; // [contador clicks, pos1, pos2]
    }
    
    public void metodoCrearJugadoresDeProperties() {
        try {
            String link = this.fachadaS.getvServidorChat().retribuirArchivo("ArchivoPropiedadesJugadores");
            String[] datosRetribuidosDeJugador = this.cnxPropiedades.cargarFile(link);
            for (int i = 0; i < (datosRetribuidosDeJugador.length) / 2; i++) {
                String contrasena = datosRetribuidosDeJugador[2 * i]; 
                String nombreUsuario= datosRetribuidosDeJugador[(2 * i) + 1];
                this.cJugador.crearJugador(contrasena,nombreUsuario);
                this.cJugador.getJugadorDAO().insertarDatosDeLosJugadores(this.cJugador.getJugador());
            }
        } catch (SQLIntegrityConstraintViolationException exp) {
            this.fachadaS.getvServidorChat().mostrarError("Alguno de los jugadores del archivo propiedades ya está en la base de datos");
        } catch (IOException | SQLException ex) {
            this.fachadaS.getvServidorChat().mostrarError("No se pudieron rescatar los datos del jugador");
        }
    }
    
    public void enviarMensajeACliente(String nombreUsuario, String mensaje) {
        this.cServidor.enviarMensajeACliente(nombreUsuario, mensaje);
    }
    
    /**
     * Inicia el servidor de comunicaciones.
     */
    public void iniciarServidor() throws Exception {
        this.cServidor.iniciarServidor();
    }
    
    /**
     * Detiene el servidor de comunicaciones.
     */
    public void detenerServidor() {
        this.cServidor.detenerServidor();
    }

    // GETTERS Y SETTERS
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