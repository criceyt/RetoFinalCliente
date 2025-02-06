package logica;

import entidades.VehiculoRestFull;

/**
 * Fábrica para crear instancias de <code>VehiculoManager</code>.
 * 
 * <p>La clase <code>VehiculoManagerFactory</code> proporciona un punto centralizado para obtener una instancia única
 * de <code>VehiculoManager</code>. Utiliza el patrón de diseño Singleton para garantizar que solo haya una instancia
 * de <code>VehiculoManager</code> durante la ejecución de la aplicación.</p>
 * 
 * <p>En esta implementación, la instancia de <code>VehiculoManager</code> es proporcionada a través de un objeto
 * de tipo <code>VehiculoRestFull</code>.</p>
 * 
 * @author 2dam
 */
public class VehiculoManagerFactory {

    // Instancia única de VehiculoManager
    private static VehiculoManager vehiculoManager;

    /**
     * Obtiene la instancia única de <code>VehiculoManager</code>.
     * 
     * <p>Si la instancia aún no ha sido creada, se crea una nueva instancia de <code>VehiculoRestFull</code>
     * y se asigna a la variable <code>vehiculoManager</code>.</p>
     * 
     * @return La instancia única de <code>VehiculoManager</code>.
     */
    public static VehiculoManager get() {
        if (vehiculoManager == null) {
            vehiculoManager = new VehiculoRestFull();
        }
        return vehiculoManager;
    }
}
