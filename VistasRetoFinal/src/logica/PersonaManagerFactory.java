package logica;

import entidades.PersonaRestFull;

/**
 * Fábrica para la creación y obtención de una instancia de <code>PersonaManager</code>.
 * 
 * <p>La clase <code>PersonaManagerFactory</code> proporciona un método para obtener una instancia única de 
 * <code>PersonaManager</code>, la cual se utiliza para realizar operaciones relacionadas con la gestión de personas
 * en el sistema. Si la instancia no ha sido creada previamente, se crea una nueva.</p>
 * 
 * @author 2dam
 */
public class PersonaManagerFactory {

    // Instancia única de PersonaManager
    private static PersonaManager personaManager;

    /**
     * Obtiene la instancia única de <code>PersonaManager</code>.
     * 
     * <p>Si la instancia aún no ha sido creada, se crea una nueva instancia de <code>PersonaRestFull</code> 
     * (implementación de <code>PersonaManager</code>).</p>
     * 
     * @return La instancia de <code>PersonaManager</code>.
     */
    public static PersonaManager get() {
        if (personaManager == null) {
            personaManager = new PersonaRestFull();
        }
        return personaManager;
    }
}
