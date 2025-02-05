package logica;

import entidades.UsuarioRestFull;

/**
 * Fábrica para la gestión de la instancia de <code>UsuarioManager</code>.
 * 
 * <p>La clase <code>UsuarioManagerFactory</code> proporciona un método estático para obtener la instancia de 
 * <code>UsuarioManager</code>, asegurando que se cree solo una vez y reutilizando la misma instancia cuando sea necesario.</p>
 * 
 * <p>Esta clase facilita la creación de la instancia de <code>UsuarioManager</code> de forma controlada y garantiza que 
 * solo se cree una única instancia de <code>UsuarioRestFull</code>.</p>
 * 
 * @author 2dam
 */
public class UsuarioManagerFactory {
    
    private static UsuarioManager usuarioManager;

    /**
     * Obtiene la instancia de <code>UsuarioManager</code>. Si no existe, la crea.
     * 
     * @return La instancia de <code>UsuarioManager</code>.
     */
    public static UsuarioManager get() {
        if (usuarioManager == null) {
            usuarioManager = new UsuarioRestFull();
        }
        return usuarioManager;
    }
}
