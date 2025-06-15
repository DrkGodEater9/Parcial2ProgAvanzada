package Jugador.edu.progavud.parcial2.control;

import java.io.IOException;

/**
 * Clase principal que ejecuta la aplicación cliente del sistema de chat individual.
 * Punto de entrada para inicializar y arrancar el cliente de chat,
 * creando una instancia del controlador principal que gestiona toda la aplicación.
 * 
 * @author carlosmamut1
 * @author Alex M
 * @author batapop
 * @version 1.0
 */
public class LauncherCliente {
    public static void main(String[] args) throws IOException {
        new ControlCliente();
    }
}
