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
public class SignInErrorException extends Exception {
    
    public SignInErrorException() {
        super("Error: El Usuario o no Existe o no son Correctos los datos introducidos");
    }
}
