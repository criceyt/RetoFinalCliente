/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author urkiz
 */
public class ModificacionException extends Exception {
    
    public ModificacionException() {
        super("Error: No se ha podido Modificar los datos");
    }
}
