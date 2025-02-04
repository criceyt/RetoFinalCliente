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
public class BorradoException extends Exception {
    
    public BorradoException() {
        super("Error: No se puede borra puede que tenga Dependencias");
    }
}
