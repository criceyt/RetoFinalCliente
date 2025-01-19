/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import entidades.PersonaRestFull;

/**
 *
 * @author urkiz
 */
public class PersonaManagerFactory {
    private static PersonaManager personaManager;

    public static PersonaManager get() {
        if (personaManager == null) {
            personaManager = new PersonaRestFull();
        }
        return personaManager;
    }
}
