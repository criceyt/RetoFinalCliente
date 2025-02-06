package exceptions;

/**
 * Excepción personalizada que se lanza cuando se intenta registrar un correo o DNI que ya existe en el sistema.
 * Esta excepción extiende de {@link Exception} y proporciona un mensaje predeterminado de error.
 * 
 * <p>Esta excepción se utiliza para indicar que un correo o un DNI ya está registrado y no se puede utilizar de nuevo.</p>
 * 
 * @author 2dam
 */
public class CorreoODniRepeException extends Exception {

    /**
     * Constructor sin parámetros que invoca al constructor de la clase {@link Exception} con un mensaje de error predeterminado.
     */
    public CorreoODniRepeException() {
        super("Error: El correo o el DNI que has introducido ya Existe");
    }
}
