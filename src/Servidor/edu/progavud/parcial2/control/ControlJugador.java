package Servidor.edu.progavud.parcial2.control;

import Servidor.edu.progavud.parcial2.modelo.JugadorDAO;
import Servidor.edu.progavud.parcial2.modelo.JugadorVO;
import com.google.common.collect.BiMap; // Se importa de guava el bimap, el cual puede acceder como a las keys (si uno no las conoce, más fácilmente)
import com.google.common.collect.HashBiMap;

/**
 * Controlador de gestión de jugadores del sistema.
 * 
 * Esta clase centraliza toda la funcionalidad relacionada con jugadores:
 * - Creación y validación de jugadores
 * - Autenticación contra base de datos
 * - Búsqueda y verificación de existencia
 * - Gestión de credenciales y perfiles
 * 
 * 
 * @author carlosmamut1
 * @author AlexM
 * @author batapop
 * @version 1.0
 */
public class ControlJugador {
    
    /** Referencia al controlador principal del servidor */
    private ControlPrincipalServidor cPrincipalServidor;
    
    /** 
     * Instancia actual del jugador en procesamiento.
     * Se utiliza temporalmente durante operaciones de creación/validación.
     */
    private JugadorVO jugador;
    
    /** 
     * Objeto de acceso a datos para operaciones de base de datos.
     * Maneja todas las consultas SQL relacionadas con jugadores.
     */
    private JugadorDAO jugadorDAO;
    
    /**
     * Crea un nuevo objeto jugador en memoria (no lo persiste automáticamente).
     * 
     * Este método solo instancia un JugadorVO con las credenciales proporcionadas.
     * Para persistir el jugador en base de datos, debe llamarse posteriormente
     * a los métodos del JugadorDAO.
     * 
     * @param contrasena Contraseña del jugador (se almacena tal como se recibe)
     * @param nombreUsuario Nombre único del usuario en el sistema
     */
    public void crearJugador(String contrasena, String nombreUsuario) {
        // Crear nueva instancia de Value Object con las credenciales
        this.jugador = new JugadorVO(contrasena, nombreUsuario);
    }
    
    /**
     * Valida las credenciales de un jugador contra la base de datos.
     * 
     * Este método es utilizado principalmente para autenticación de clientes
     * que intentan conectarse al servidor. Consulta la base de datos para
     * verificar que las credenciales sean correctas.
     * 
     * @param contrasena Contraseña a validar
     * @param nombreUsuario Nombre de usuario a validar
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean validarJugador(String contrasena, String nombreUsuario) {
        try {
            // Consultar base de datos por usuario y contraseña
            JugadorVO jugadorEncontrado = this.jugadorDAO.consultarJugadorPorUsuarioYContrasena(contrasena, nombreUsuario);
            
            // Si se encuentra un jugador, las credenciales son válidas
            return jugadorEncontrado != null;
            
        } catch(Exception ex) {
            // En caso de error en BD, mostrar mensaje y denegar acceso
            this.cPrincipalServidor.getFachadaS().getvServidorChat().mostrarError("Error al validar jugador en la base de datos: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un jugador existe en el sistema por nombre de usuario.
     * 
     * Método de conveniencia que encapsula la lógica de verificación de existencia.
     * Utiliza internamente buscarJugador() y evalúa si el resultado es null.
     * 
     * @param nombreUsuario Nombre del usuario a verificar
     * @return true si el jugador existe, false en caso contrario
     */
    public boolean estaElJugador(String nombreUsuario) {
        // Buscar jugador y evaluar si existe
        JugadorVO clienteEncontrado = this.buscarJugador(nombreUsuario);
        
        // Simplificar la lógica booleana
        return clienteEncontrado != null;
    }
    
    /**
     * Busca un jugador específico por nombre de usuario en la base de datos.
     * 
     * Este método realiza una consulta a la base de datos para encontrar
     * un jugador específico. Si ocurre algún error durante la consulta,
     * se maneja graciosamente mostrando un mensaje de error.
     * 
     * @param nombreUsuario Nombre del usuario a buscar
     * @return JugadorVO del jugador encontrado, null si no existe o hay error
     */
    public JugadorVO buscarJugador(String nombreUsuario) {
        try {
            // Realizar consulta en base de datos
            JugadorVO clienteEncontrado = this.jugadorDAO.consultarJugador(nombreUsuario);
            
            // Retornar resultado (puede ser null si no se encuentra)
            if(clienteEncontrado != null) {
                return clienteEncontrado;
            }
        }
        catch(Exception ex) {
            // Manejar error de consulta mostrando mensaje en interfaz
            this.cPrincipalServidor.getFachadaS().getvServidorChat().mostrarError("Ha habido un error a la hora de buscar al jugador");
        }
        
        // Retornar null si no se encuentra o hay error
        return null;
    }

    /**
     * Obtiene la instancia del DAO de jugadores.
     * 
     * @return Instancia actual de JugadorDAO
     */
    public JugadorDAO getJugadorDAO() {
        return jugadorDAO;
    }

    /**
     * Establece una nueva instancia del DAO de jugadores.
     * 
     * @param jugadorDAO Nueva instancia de JugadorDAO a utilizar
     */
    public void setJugadorDAO(JugadorDAO jugadorDAO) {
        this.jugadorDAO = jugadorDAO;
    }

    /**
     * Obtiene la instancia actual del jugador en procesamiento.
     * 
     * @return JugadorVO actualmente en uso, puede ser null
     */
    public JugadorVO getJugador() {
        return jugador;
    }

    /**
     * Establece un nuevo jugador para procesamiento.
     * 
     * @param jugador Nueva instancia de JugadorVO a utilizar
     */
    public void setJugador(JugadorVO jugador) {
        this.jugador = jugador;
    }

    /**
     * Constructor del controlador de jugadores.
     * 
     * Inicializa el controlador con una referencia al servidor principal
     * y crea una nueva instancia del DAO para acceso a datos.
     * 
     * @param cPrincipalServidor Referencia al controlador principal del servidor
     */
    public ControlJugador(ControlPrincipalServidor cPrincipalServidor) {
        // Establecer referencia al controlador principal
        this.cPrincipalServidor = cPrincipalServidor;
        
        // Inicializar DAO para operaciones de base de datos
        this.jugadorDAO = new JugadorDAO();
    }
}