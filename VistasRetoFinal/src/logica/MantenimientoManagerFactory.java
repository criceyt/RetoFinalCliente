package logica;

import entidades.MantenimientoRestFull;

/**
 * Fábrica para crear y obtener instancias de <code>MantenimientoManager</code>.
 * 
 * <p>La clase <code>MantenimientoManagerFactory</code> proporciona un método estático para obtener una única 
 * instancia de la implementación de <code>MantenimientoManager</code>, asegurando que se utilice el patrón de diseño 
 * Singleton en la creación del objeto. La implementación concreta utilizada es <code>MantenimientoRestFull</code>.</p>
 * 
 * <p>Este patrón permite centralizar la creación de instancias y garantizar que solo haya una instancia de 
 * <code>MantenimientoManager</code> en toda la aplicación.</p>
 * 
 * @author 2dam
 */
public class MantenimientoManagerFactory {
    /**
     * Instancia estática de <code>MantenimientoManager</code>.
     */
    private static MantenimientoManager mantenimientoManager;

    /**
     * Obtiene la instancia de <code>MantenimientoManager</code>. Si no existe, se crea una nueva.
     * 
     * @return La instancia de <code>MantenimientoManager</code>.
     */
    public static MantenimientoManager get() {
        if (mantenimientoManager == null) {
            mantenimientoManager = new MantenimientoRestFull();
        }
        return mantenimientoManager;
    }
}
