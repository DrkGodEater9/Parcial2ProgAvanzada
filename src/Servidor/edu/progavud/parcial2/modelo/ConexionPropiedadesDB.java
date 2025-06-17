/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor.edu.progavud.parcial2.modelo;

import Jugador.edu.progavud.parcial2.modelo.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author carlosmamut1
 */
public class ConexionPropiedadesDB {

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
    public ConexionPropiedadesDB() {
        props = new Properties();
    }

    /**
     * Carga un archivo de propiedades desde la ruta especificada y extrae los
     * datos necesarios en un arreglo.
     *
     * @param url ruta del archivo .properties
     * @return arreglo con los valores de propiedades requeridas
     * @throws IOException si ocurre un error al leer el archivo
     */
    public String[] cargarFile(String url) throws IOException {
        FileInputStream archivo = new FileInputStream(url);
        props.load(archivo);
        String datos[] = datosParaConexionSQL();
        archivo.close();
        return datos;
    }

    /**
     * Obtiene los datos necesarios para la conexión a la base de datos desde un
     * archivo de propiedades.
     *
     * @return Un array de cadenas con el usuario, la contraseña y la URL de la
     * base de datos.
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
