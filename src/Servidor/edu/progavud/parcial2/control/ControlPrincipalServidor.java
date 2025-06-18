package Servidor.edu.progavud.parcial2.control;

import Servidor.edu.progavud.parcial2.modelo.ConexionPropiedades;
import Servidor.edu.progavud.parcial2.modelo.ConexionPropiedadesDB;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

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
    private int[] intentosJugadores; // NUEVO: Array para guardar intentos de cada jugador
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
        // ACIERTO: Incrementar aciertos E intentos del jugador actual
        if (turnoActual < aciertosJugadores.length) {
            aciertosJugadores[turnoActual]++;
            intentosJugadores[turnoActual]++;
            
            // Verificar si el juego ha terminado
            verificarFinDelJuego();
        }
        return true;
    } else {
        // FALLO: Solo incrementar intentos y cambiar turno
        if (arregloClicksYPosiciones[0] % 2 == 0) {
            if (turnoActual < intentosJugadores.length) {
                intentosJugadores[turnoActual]++;
            }
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
        
        // ACTUALIZAR EL LABEL DE TURNO EN LA VENTANA DE JUEGO
        fachadaS.getvServidorJuego().actualizarTurno(jugadorActual);
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
            determinarGanadorYCerrar();
        }
    }
    
    public void determinarGanadorYCerrar() {
        double mejorPorcentaje = 0.0;
        String ganador = "";
        boolean empate = false;
        
        // Calcular porcentajes (aciertos / intentos) para cada jugador
        StringBuilder mensajeResultado = new StringBuilder();
        mensajeResultado.append("=== JUEGO TERMINADO ===\n");
        mensajeResultado.append("Resultados finales:\n\n");
        
        for (int i = 0; i < jugadoresEnJuego.size(); i++) {
            int aciertos = aciertosJugadores[i];
            int intentos = intentosJugadores[i];
            double porcentaje = (intentos > 0) ? ((double) aciertos / intentos) * 100 : 0.0;
            
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
                empate = true;
            }
        }
        
        mensajeResultado.append("\n");
        if (empate) {
            mensajeResultado.append("¡EMPATE!");
        } else {
            mensajeResultado.append("¡GANADOR: ").append(ganador).append("!")
                          .append("\nCon ").append(String.format("%.1f", mejorPorcentaje)).append("% de efectividad");
        }
        
        // Mostrar resultado en JOptionPane
        String mensajeFinal = mensajeResultado.toString();
        JOptionPane.showMessageDialog(null, mensajeFinal, "¡Fin del Juego!", JOptionPane.INFORMATION_MESSAGE);
        
        // CERRAR TODO EL APLICATIVO
        cerrarAplicativo();
    }
    
    /**
     * Cierra todas las ventanas y termina el aplicativo
     */
    public void cerrarAplicativo() {
        try {
            // Detener el servidor
            detenerServidor();
            
            // Cerrar ventanas
            if (fachadaS.getvServidorJuego() != null) {
                fachadaS.getvServidorJuego().dispose();
            }
            if (fachadaS.getvServidorChat() != null) {
                fachadaS.getvServidorChat().dispose();
            }
            
            // Terminar aplicación
            System.exit(0);
            
        } catch (Exception e) {
            // Forzar cierre si hay error
            System.exit(0);
        }
    }
    
    public void inicializarSistemaTurnos() {
        jugadoresEnJuego = new ArrayList<>(cServidor.obtenerNombresJugadores());
        if (!jugadoresEnJuego.isEmpty()) {
            turnoActual = 0;
            jugadorActual = jugadoresEnJuego.get(0);
            aciertosJugadores = new int[jugadoresEnJuego.size()];
            intentosJugadores = new int[jugadoresEnJuego.size()]; // INICIALIZAR INTENTOS
            juegoTerminado = false;
            
            // Calcular total de parejas (40 cartas = 20 parejas)
            totalParejas = 20;
            
            // ACTUALIZAR EL LABEL DE TURNO EN LA VENTANA DE JUEGO
            fachadaS.getvServidorJuego().actualizarTurno(jugadorActual);
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