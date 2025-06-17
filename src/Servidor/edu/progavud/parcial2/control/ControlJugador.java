/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor.edu.progavud.parcial2.control;

import Servidor.edu.progavud.parcial2.modelo.JugadorDAO;
import Servidor.edu.progavud.parcial2.modelo.JugadorVO;

/**
 *
 * @author carlosmamut1
 */
public class ControlJugador {
    private ControlPrincipalServidor cPrincipalServidor;
    private JugadorVO jugador;
    private JugadorDAO jugadorDAO;
    
    
    public void crearJugador(String contrasena, String nombreUsuario) {
        this.jugador = new JugadorVO(contrasena,nombreUsuario);
    }
    public boolean validarJugador(String contrasena, String nombreUsuario) {
        try {
            JugadorVO jugadorEncontrado = this.jugadorDAO.consultarJugadorPorUsuarioYContrasena(contrasena, nombreUsuario);
            return jugadorEncontrado != null;
        } catch(Exception ex) {
            this.cPrincipalServidor.getFachadaS().getvServidorChat().mostrarError("Error al validar jugador en la base de datos: " + ex.getMessage());
            return false;
        }
    }

    public boolean estaElJugador(String nombreUsuario) {
        JugadorVO clienteEncontrado = this.buscarJugador(nombreUsuario);
        if (clienteEncontrado != null) {
            return true;
        } else {
            return false;
        }
    }
    
    public JugadorVO buscarJugador(String nombreUsuario) {
        try {
            JugadorVO clienteEncontrado = this.jugadorDAO.consultarJugador(nombreUsuario);
            if(clienteEncontrado != null) {
                return clienteEncontrado;
            }
        }
        catch(Exception ex) {
            this.cPrincipalServidor.getFachadaS().getvServidorChat().mostrarError("Ha habido un error a la hora de buscar al jugador");
        }
        return null;
    }

    public JugadorDAO getJugadorDAO() {
        return jugadorDAO;
    }

    public void setJugadorDAO(JugadorDAO jugadorDAO) {
        this.jugadorDAO = jugadorDAO;
    }

    public JugadorVO getJugador() {
        return jugador;
    }

    public void setJugador(JugadorVO jugador) {
        this.jugador = jugador;
    }

    public ControlJugador(ControlPrincipalServidor cPrincipalServidor) {
        this.cPrincipalServidor = cPrincipalServidor;
        this.jugadorDAO = new JugadorDAO();
    }
    
    
}
