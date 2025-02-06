package logica;

import entidades.CompraRestFull;

/**
 * Fábrica para obtener una instancia de un gestor de compras (CompraManager).
 * 
 * <p>La clase <code>CompraManagerFactory</code> implementa el patrón de diseño Singleton para garantizar que solo se 
 * cree una instancia de <code>CompraManager</code> y la reutilice cada vez que se solicite. En este caso, la implementación 
 * predeterminada del gestor de compras es <code>CompraRestFull</code>.</p>
 * 
 * <p>El patrón Singleton asegura que la clase <code>CompraManagerFactory</code> mantenga una única instancia de la interfaz
 * <code>CompraManager</code>, que puede ser utilizada para realizar operaciones de compra.</p>
 * 
 * @author 2dam
 */
public class CompraManagerFactory {

    // Instancia única de CompraManager
    private static CompraManager compraManager;

    /**
     * Obtiene la instancia de <code>CompraManager</code>.
     * 
     * <p>Si la instancia aún no ha sido creada, se inicializa como una instancia de <code>CompraRestFull</code>. 
     * Si ya ha sido creada, se devuelve la misma instancia.</p>
     * 
     * @return La instancia de <code>CompraManager</code>.
     */
    public static CompraManager get() {
        if (compraManager == null) {
            compraManager = new CompraRestFull();
        }
        return compraManager;
    }
}
