/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import restfullCliente.TrabajadorRestFull;

/**
 *
 * @author urkiz
 */
public class TrabajadorManagerFactory {
    
    private static TrabajadorManager trabajadorManager;

    public static TrabajadorManager get() {
        if (trabajadorManager == null) {
            trabajadorManager = new TrabajadorRestFull();
        }
        return trabajadorManager;
    }
    
}
