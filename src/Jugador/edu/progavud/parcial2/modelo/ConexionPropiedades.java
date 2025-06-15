package Jugador.edu.progavud.parcial2.modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author a
 */
public class ConexionPropiedades {
    private Properties props;


    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public ConexionPropiedades() {
        props = new Properties();
    }
    
    public String[] cargarFile(String url) throws IOException{
        FileInputStream archivo = new FileInputStream(url);
        props.load(archivo);
        String datosQueNecesito[] = loaderDatosQueNecesito();
        archivo.close();
        return datosQueNecesito;
    }
    
    public String[] loaderDatosQueNecesito(){
        String[] datosServ = new String[2];
        datosServ[0]=props.getProperty("servidor.ip");
        datosServ[1]=props.getProperty("servidor.puerto");
        return datosServ;
        
    }
    
    
}
