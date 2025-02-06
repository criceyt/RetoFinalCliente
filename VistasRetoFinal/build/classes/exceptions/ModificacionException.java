package exceptions;

/**
 * Excepción personalizada que se lanza cuando ocurre un error al intentar modificar datos en el sistema.
 * Esta excepción extiende de {@link Exception} y proporciona un mensaje predeterminado de error.
 * 
 * <p>Esta excepción se utiliza para indicar que no se han podido modificar los datos debido a algún problema.</p>
 * 
 * @author urkiz
 */
public class ModificacionException extends Exception {

    /**
     * Constructor sin parámetros que invoca al constructor de la clase {@link Exception} con un mensaje de error predeterminado.
     */
    public ModificacionException() {
        super("Error: No se ha podido Modificar los datos");
    }
}
