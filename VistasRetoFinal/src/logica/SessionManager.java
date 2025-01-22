/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class SessionManager {

    private static Usuario usuario;

    // Método para obtener el usuario actual
    public static Usuario getUsuario() {
        return usuario;
    }

    // Método para establecer el usuario
    public static void setUsuario(Usuario usuario) {
        SessionManager.usuario = usuario;
    }
}
