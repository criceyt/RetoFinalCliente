package logica;

import entidades.ProveedorRestFull;

/**
 * Fábrica para obtener una instancia del gestor de proveedores.
 * 
 * <p>La clase <code>ProveedorManagerFactory</code> proporciona un método estático para obtener una instancia de
 * un gestor de proveedores. Si la instancia no ha sido creada aún, el método la inicializa y la devuelve. Este patrón
 * de diseño es útil para gestionar la creación de objetos y evitar la creación múltiple de instancias.</p>
 * 
 * @author oier
 */
public class ProveedorManagerFactory {

    private static ProveedorManager proveedorManager;

    /**
     * Obtiene una instancia del gestor de proveedores.
     * 
     * @return Una instancia del gestor de proveedores.
     */
    public static ProveedorManager get() {
        if (proveedorManager == null) {
            proveedorManager = new ProveedorRestFull();
        }
        return proveedorManager;
    }
}
