/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import entidades.UsuarioRestFull;

/**
 *
 * @author 2dam
 */
public class UsuarioManagerFactory {
    
    private static UsuarioManager usuarioManager;

    public static UsuarioManager get() {
        if (usuarioManager == null) {
            usuarioManager = new UsuarioRestFull();
        }
        return usuarioManager;
    }
}
