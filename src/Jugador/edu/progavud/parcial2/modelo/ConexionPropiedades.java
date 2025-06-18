package Jugador.edu.progavud.parcial2.modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase modelo para manejar la configuración de conexión desde archivos de propiedades.
 * Proporciona funcionalidad para cargar y extraer datos de configuración del servidor
 * desde archivos .properties, facilitando la configuración externa de parámetros
 * de conexión como IP y puerto del servidor.
 * 
 * Esta clase encapsula el objeto Properties y proporciona métodos específicos
 * para obtener los datos de configuración del servidor necesarios para establecer
 * la conexión cliente-servidor.
 * 
 * @author carlosmamut1
 * @author Alex M
 * @author batapop  
 * @version 1.0
 */
public class ConexionPropiedades {
    
    /** Objeto Properties que almacena las propiedades cargadas del archivo */
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
     * @param props El nuevo objeto Properties a establecer
     */
    public void setProps(Properties props) {
        this.props = props;
    }

    /**
     * Constructor por defecto de la clase ConexionPropiedades.
     * Inicializa un nuevo objeto Properties vacío listo para cargar
     * configuraciones desde archivos externos.
     */
    public ConexionPropiedades() {
        props = new Properties();
    }
    
    /**
     * Carga un archivo de propiedades desde la URL especificada y extrae
     * los datos necesarios del servidor.
     * 
     * Este método abre el archivo, carga las propiedades, extrae los datos
     * del servidor mediante el método loaderDatosQueNecesito() y cierra el archivo.
     * 
     * @param url La ruta del archivo de propiedades a cargar
     * @return Un arreglo de String con los datos del servidor [IP, Puerto]
     * @throws IOException Si ocurre un error al leer el archivo o si el archivo no existe
     */
    public String[] cargarFile(String url) throws IOException{
        FileInputStream archivo = new FileInputStream(url);
        props.load(archivo);
        String datosQueNecesito[] = loaderDatosQueNecesito();
        archivo.close();
        return datosQueNecesito;
    }
    
    /**
     * Extrae los datos específicos del servidor desde las propiedades cargadas.
     * 
     * Este método busca las claves "servidor.ip" y "servidor.puerto" en el
     * objeto Properties y retorna sus valores en un arreglo de String.
     * 
     * @return Un arreglo de String donde:
     *         - Índice 0: IP del servidor (servidor.ip)
     *         - Índice 1: Puerto del servidor (servidor.puerto)
     */
    public String[] loaderDatosQueNecesito(){
        String[] datosServ = new String[2];
        datosServ[0] = props.getProperty("servidor.ip");
        datosServ[1] = props.getProperty("servidor.puerto");
        return datosServ;
    }
}