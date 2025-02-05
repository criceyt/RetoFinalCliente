package exceptions;

/**
 * Excepción personalizada que se lanza cuando ocurre un error en el proceso de inicio de sesión.
 * Esta excepción extiende de {@link Exception} y proporciona un mensaje predeterminado de error.
 * 
 * <p>Se utiliza para indicar que el nombre de usuario no existe o los datos introducidos son incorrectos durante el proceso de inicio de sesión.</p>
 * 
 * @author urkiz
 */
public class SignInErrorException extends Exception {

    /**
     * Constructor sin parámetros que invoca al constructor de la clase {@link Exception} con un mensaje de error predeterminado.
     */
    public SignInErrorException() {
        super("Error: El Usuario o no Existe o no son Correctos los datos introducidos");
    }
}
