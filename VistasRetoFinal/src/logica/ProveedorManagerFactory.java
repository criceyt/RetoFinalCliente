/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import restfullCliente.ProveedorRestFull;

/**
 *
 * @author oier
 */
public class ProveedorManagerFactory {

    private static ProveedorManager proveedorManager;

    public static ProveedorManager get() {
        if (proveedorManager == null) {
            proveedorManager = new ProveedorRestFull();
        }
        return proveedorManager;
    }
}
