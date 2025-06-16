package Jugador.edu.progavud.parcial2.modelo;

import java.io.*;
import java.net.*;

/**
 * Clase modelo que representa un cliente del sistema de chat individual.
 * Solo contiene datos, sin lógica de negocio ni validaciones.
 */
public class Cliente {
    public String IP_SERVER;
    public int PUERTO_SERVIDOR = 8080;
    private String nombreUsuario;
    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private boolean conectado;
    
    public Cliente(int codigo, String nombreUsuario) {
        this.conectado = false;
    }
    public Cliente() {};
    
    // Getters y Setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public DataInputStream getEntrada() {
        return entrada;
    }
    
    public void setEntrada(DataInputStream entrada) {
        this.entrada = entrada;
    }
    
    public DataOutputStream getSalida() {
        return salida;
    }
    
    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }
    
    public boolean isConectado() {
        return conectado;
    }
    
    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public String getIP_SERVER() {
        return IP_SERVER;
    }

    public void setIP_SERVER(String IP_SERVER) {
        this.IP_SERVER = IP_SERVER;
    }

    public int getPUERTO_SERVIDOR() {
        return PUERTO_SERVIDOR;
    }

    public void setPUERTO_SERVIDOR(int PUERTO_SERVIDOR) {
        this.PUERTO_SERVIDOR = PUERTO_SERVIDOR;
    }



    
}