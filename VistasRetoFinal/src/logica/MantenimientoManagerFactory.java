/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import entidades.MantenimientoRestFull;

/**
 *
 * @author gorka
 */
public class MantenimientoManagerFactory {
    
    private static MantenimientoManager mantenimientoManager;

    public static MantenimientoManager get() {
        if (mantenimientoManager == null) {
            mantenimientoManager = new MantenimientoRestFull();
        }
        return mantenimientoManager;
    }
}
