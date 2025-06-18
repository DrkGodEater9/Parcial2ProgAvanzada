package Servidor.edu.progavud.parcial2.modelo;

/**
 * Clase de objeto de valor (VO) que representa un jugador en el sistema
 * de juego de memoria multijugador.
 * 
 * Esta clase implementa el patrón Value Object (VO) encapsulando los datos
 * de un jugador del sistema. Contiene información de credenciales para
 * autenticación y estadísticas de juego en tiempo de ejecución.
 * 
 * Los atributos transient (aciertos e intentos) no se persisten en la base
 * de datos y solo existen durante la sesión de juego activa para llevar
 * el seguimiento del rendimiento del jugador.
 * 
 * La clase sigue las convenciones de JavaBean con métodos getter y setter
 * para todos los atributos, facilitando su uso con frameworks de persistencia
 * y serialización.
 * 
 * @author carlosmamut
 * @author batapop
 * @author AlexM
 * @version 1.0
 * @since 2024
 */
public class JugadorVO {
    
    /**
     * Contraseña del jugador para autenticación en el sistema.
     * Se almacena en la base de datos y se utiliza para validar
     * el acceso del jugador al juego.
     */
    private String contrasena;
    
    /**
     * Nombre de usuario único que identifica al jugador en el sistema.
     * Se utiliza tanto para autenticación como para mostrar
     * información del jugador durante las partidas.
     */
    private String nombreUsuario;
    
    /**
     * Número de aciertos del jugador en la sesión actual de juego.
     * Atributo transient que no se persiste en la base de datos.
     * Se utiliza para calcular estadísticas de rendimiento.
     */
    private transient int aciertos;
    
    /**
     * Número total de intentos del jugador en la sesión actual de juego.
     * Atributo transient que no se persiste en la base de datos.
     * Se utiliza junto con aciertos para calcular porcentajes de efectividad.
     */
    private transient int intentos;

    /**
     * Constructor principal que inicializa un jugador con sus credenciales.
     * 
     * Crea una nueva instancia de JugadorVO con las credenciales proporcionadas.
     * Los contadores de estadísticas (aciertos e intentos) se inicializan
     * automáticamente en 0.
     * 
     * @param contrasena Contraseña del jugador para autenticación
     * @param nombreUsuario Nombre único que identifica al jugador
     */
    public JugadorVO(String contrasena, String nombreUsuario) {
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene la contraseña del jugador.
     * 
     * @return La contraseña actual del jugador
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña del jugador.
     * 
     * @param contrasena La nueva contraseña a asignar
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el nombre de usuario del jugador.
     * 
     * @return El nombre de usuario actual del jugador
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de usuario del jugador.
     * 
     * @param nombreUsuario El nuevo nombre de usuario a asignar
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}