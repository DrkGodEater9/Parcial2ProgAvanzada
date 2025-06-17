/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor.edu.progavud.parcial2.modelo;

/**
 *
 * @author carlosmamut1
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase encargada de cargar un archivo de propiedades que contiene información
 * de múltiples objetos {@code GatoVO}. Permite extraer dichos valores en un
 * arreglo de {@code String}.
 *
 * @author carlosmamut1
 * @author FELIPE
 * @author AlexM
 */
public class ConexionPropiedades {

    private Properties props;

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    /**
     * Constructor que inicializa el objeto {@code Properties}.
     */
    public ConexionPropiedades() {
        props = new Properties();
    }

    /**
     * Carga un archivo de propiedades desde la ruta especificada
     * y extrae los datos necesarios en un arreglo.
     *
     * @param url ruta del archivo .properties
     * @return arreglo con los valores de propiedades requeridas
     * @throws IOException si ocurre un error al leer el archivo
     */
   


    public String[] cargarFile(String url) throws IOException {
        
       FileInputStream archivo = new FileInputStream(url);
        props.load(archivo);
        String datosQueNecesito[] = loaderDatosQueNecesito();
        archivo.close();
        return datosQueNecesito;

    } 

    

    /**
     * Carga los valores de propiedades específicas asociadas a varios objetos gato.
     *
     * @return arreglo con los valores de las propiedades cargadas
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
