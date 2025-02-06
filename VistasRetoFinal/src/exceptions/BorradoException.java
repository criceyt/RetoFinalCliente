package exceptions;

/**
 * Excepción personalizada que se lanza cuando ocurre un error al intentar borrar un objeto que tiene dependencias.
 * Esta excepción extiende de {@link Exception} y proporciona un mensaje predeterminado de error.
 * 
 * <p>Esta excepción se utiliza para indicar que no se puede borrar un objeto debido a que existen dependencias relacionadas que lo impiden.</p>
 * 
 * @author urkiz
 */
public class BorradoException extends Exception {

    /**
     * Constructor sin parámetros que invoca al constructor de la clase {@link Exception} con un mensaje de error predeterminado.
     */
    public BorradoException() {
        super("Error: No se puede borrar, puede que tenga dependencias");
    }
}
