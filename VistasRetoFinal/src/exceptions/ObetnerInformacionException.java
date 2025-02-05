package exceptions;

/**
 * Excepción personalizada que se lanza cuando ocurre un error al intentar obtener información del servidor.
 * Esta excepción extiende de {@link Exception} y proporciona un mensaje predeterminado de error.
 * 
 * <p>Se utiliza para indicar que no se han podido obtener los datos del servidor debido a un fallo en la comunicación o un problema en la consulta.</p>
 * 
 * @author urkiz
 */
public class ObetnerInformacionException extends Exception {

    /**
     * Constructor sin parámetros que invoca al constructor de la clase {@link Exception} con un mensaje de error predeterminado.
     */
    public ObetnerInformacionException() {
        super("Error: No se puede Obtener los datos del Servidor");
    }
}
