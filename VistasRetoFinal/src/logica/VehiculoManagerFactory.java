/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import restfullCliente.VehiculoRestFull;

/**
 *
 * @author 2dam
 */
public class VehiculoManagerFactory {

    private static VehiculoManager vehiculoManager;

    public static VehiculoManager get() {
        if (vehiculoManager == null) {
            vehiculoManager = new VehiculoRestFull();
        }
        return vehiculoManager;
    }
}
