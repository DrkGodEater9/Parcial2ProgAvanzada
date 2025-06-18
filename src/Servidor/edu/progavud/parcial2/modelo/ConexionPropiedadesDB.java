package Servidor.edu.progavud.parcial2.modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase especializada en cargar y gestionar archivos de propiedades que contienen
 * configuración específica para la conexión a la base de datos del sistema.
 * 
 * Esta clase maneja la lectura de archivos .properties que almacenan parámetros
 * críticos para establecer conexiones con la base de datos, incluyendo credenciales,
 * URLs de conexión y configuraciones del servidor. Proporciona métodos específicos
 * para extraer y organizar estos datos de configuración.
 * 
 * El archivo de propiedades debe contener las siguientes claves:
 * - dato.usuario: Usuario de la base de datos
 * - dato.contrasena: Contraseña de la base de datos
 * - dato.URLBD: URL de conexión a la base de datos
 * - dato.puerto: Puerto del servidor
 * 
 * @author carlosmamut
 * @author batapop
 * @author AlexM
 * @version 1.0
 * @since 2024
 */
public class ConexionPropiedadesDB {

    /**
     * Objeto Properties para almacenar y gestionar las propiedades de
     * configuración de base de datos cargadas desde el archivo.
     */
    private Properties props;

    /**
     * Obtiene el objeto Properties actual con la configuración de BD.
     * 
     * @return El objeto Properties con las propiedades de base de datos cargadas
     */
    public Properties getProps() {
        return props;
    }

    /**
     * Establece el objeto Properties para la configuración de BD.
     * 
     * @param props El objeto Properties a asignar
     */
    public void setProps(Properties props) {
        this.props = props;
    }

    /**
     * Constructor que inicializa el objeto Properties.
     * Crea una nueva instancia de Properties lista para cargar
     * configuraciones de base de datos.
     */
    public ConexionPropiedadesDB() {
        props = new Properties();
    }

    /**
     * Carga un archivo de propiedades desde la ruta especificada y extrae
     * los datos de configuración de base de datos en un arreglo.
     * 
     * Este método abre el archivo de configuración, carga las propiedades
     * relacionadas con la base de datos, extrae los datos necesarios para
     * la conexión y cierra el archivo automáticamente.
     *
     * @param url Ruta del archivo .properties de configuración de BD
     * @return Arreglo con los valores de configuración de base de datos
     * @throws IOException Si ocurre un error al leer el archivo de configuración
     */
    public String[] cargarFile(String url) throws IOException {
        FileInputStream archivo = new FileInputStream(url);
        props.load(archivo);
        String datos[] = datosParaConexionSQL();
        archivo.close();
        return datos;
    }

    /**
     * Obtiene los datos necesarios para establecer la conexión a la base de datos
     * desde las propiedades cargadas.
     * 
     * Extrae los parámetros críticos de conexión organizándolos en un arreglo
     * en el orden requerido por el sistema de conexión SQL.
     * 
     * Estructura del arreglo retornado:
     * [usuario, contraseña, URL_BD, puerto]
     *
     * @return Array de cadenas con los parámetros de conexión a la base de datos
     */
    public String[] datosParaConexionSQL() {
        String[] datos = {
            props.getProperty("dato.usuario"),
            props.getProperty("dato.contrasena"),
            props.getProperty("dato.URLBD"),
            props.getProperty("dato.puerto"),
        };
        return datos;
    }
}