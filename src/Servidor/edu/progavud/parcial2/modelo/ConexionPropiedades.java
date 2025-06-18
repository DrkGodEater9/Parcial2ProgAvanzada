package Servidor.edu.progavud.parcial2.modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase encargada de cargar y gestionar archivos de propiedades que contienen
 * información de configuración de jugadores del sistema de juego de memoria.
 * 
 * Esta clase maneja la lectura de archivos .properties que almacenan datos
 * de múltiples jugadores, incluyendo sus credenciales de acceso. Proporciona
 * métodos para extraer estos valores y organizarlos en estructuras de datos
 * apropiadas para su uso en el sistema.
 * 
 * El archivo de propiedades debe seguir el formato estándar:
 * jugadorN.contrasena=valor
 * jugadorN.nombreUsuario=valor
 * 
 * donde N es el número del jugador (1-6).
 *
 * @author carlosmamut
 * @author batapop
 * @author AlexM
 * @version 1.0
 * @since 2024
 */
public class ConexionPropiedades {

    /**
     * Objeto Properties para almacenar y gestionar las propiedades cargadas
     * desde el archivo de configuración.
     */
    private Properties props;

    /**
     * Obtiene el objeto Properties actual.
     * 
     * @return El objeto Properties con las propiedades cargadas
     */
    public Properties getProps() {
        return props;
    }

    /**
     * Establece el objeto Properties.
     * 
     * @param props El objeto Properties a asignar
     */
    public void setProps(Properties props) {
        this.props = props;
    }

    /**
     * Constructor que inicializa el objeto Properties.
     * Crea una nueva instancia de Properties lista para cargar datos.
     */
    public ConexionPropiedades() {
        props = new Properties();
    }

    /**
     * Carga un archivo de propiedades desde la ruta especificada
     * y extrae los datos de jugadores en un arreglo.
     * 
     * Este método abre el archivo, carga las propiedades, extrae los datos
     * necesarios y cierra el archivo automáticamente.
     *
     * @param url Ruta del archivo .properties a cargar
     * @return Arreglo con los valores de propiedades de los jugadores
     * @throws IOException Si ocurre un error al leer el archivo
     */
    public String[] cargarFile(String url) throws IOException {
        FileInputStream archivo = new FileInputStream(url);
        props.load(archivo);
        String datosQueNecesito[] = loaderDatosQueNecesito();
        archivo.close();
        return datosQueNecesito;
    } 

    /**
     * Extrae los valores de propiedades específicas asociadas a los jugadores
     * del sistema de juego de memoria.
     * 
     * Carga las credenciales (contraseña y nombre de usuario) de hasta 6 jugadores
     * organizándolos en un arreglo secuencial donde cada par de elementos
     * corresponde a la contraseña y nombre de usuario de un jugador.
     * 
     * Estructura del arreglo retornado:
     * [contraseña1, usuario1, contraseña2, usuario2, ..., contraseña6, usuario6]
     *
     * @return Arreglo con los valores de las propiedades de jugadores cargadas
     */
    public String[] loaderDatosQueNecesito() {
        String[] atributos = {
            props.getProperty("jugador1.contrasena"),
            props.getProperty("jugador1.nombreUsuario"),
            props.getProperty("jugador2.contrasena"),
            props.getProperty("jugador2.nombreUsuario"),
            props.getProperty("jugador3.contrasena"),
            props.getProperty("jugador3.nombreUsuario"),
            props.getProperty("jugador4.contrasena"),
            props.getProperty("jugador4.nombreUsuario"),
            props.getProperty("jugador5.contrasena"),
            props.getProperty("jugador5.nombreUsuario"),
            props.getProperty("jugador6.contrasena"),
            props.getProperty("jugador6.nombreUsuario"),
        };
        return atributos;
    }
}