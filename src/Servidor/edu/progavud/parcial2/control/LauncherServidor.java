package Servidor.edu.progavud.parcial2.control;

/**
 * Clase principal de entrada para la aplicación del servidor de juego.
 * 
 * 
 * @author carlosmamut1
 * @author AlexM
 * @author batapop
 * @version 1.0
 */
public class LauncherServidor {

    /**
     * Método principal de entrada de la aplicación.
     * 
     * Este método es ejecutado por la JVM cuando se inicia la aplicación.
     * Crea una instancia del controlador principal, lo cual desencadena
     * toda la inicialización en cascada del sistema:
     * 
     * @param args Argumentos de línea de comandos (actualmente no utilizados)
     */
    public static void main(String[] args) {
        // Crear instancia del controlador principal
        // Esto inicia todo el proceso de inicialización del servidor
        new ControlPrincipalServidor();
    }
}