package logica;

import modelo.Usuario;

/**
 * Clase para gestionar la sesión del usuario actual.
 * 
 * <p>La clase <code>SessionManager</code> se encarga de mantener y gestionar la información relacionada con la
 * sesión del usuario. Permite establecer y obtener el usuario actual que ha iniciado sesión en el sistema.</p>
 * 
 * <p>Esta clase utiliza un enfoque estático para almacenar la información del usuario y es útil para acceder a los
 * datos del usuario desde cualquier parte de la aplicación durante su sesión.</p>
 * 
 * @author 2dam
 */
public class SessionManager {

    private static Usuario usuario;

    /**
     * Obtiene el usuario actual de la sesión.
     * 
     * @return El usuario actualmente almacenado en la sesión.
     */
    public static Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario en la sesión.
     * 
     * @param usuario El usuario que se desea establecer como el usuario actual de la sesión.
     */
    public static void setUsuario(Usuario usuario) {
        SessionManager.usuario = usuario;
    }
}
