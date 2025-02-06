package logica;

import exceptions.SignInErrorException;
import javax.ws.rs.WebApplicationException;

/**
 * Interfaz que define las operaciones de gestión de personas en el sistema.
 * 
 * <p>La interfaz <code>PersonaManager</code> proporciona los métodos necesarios para manejar operaciones relacionadas
 * con personas, tales como inicio de sesión, recuperación de contraseñas, actualización de datos, y más. Los métodos 
 * definen interacciones con los servicios RESTful mediante XML como formato de intercambio de datos.</p>
 * 
 * @author 2dam
 */
public interface PersonaManager {

    /**
     * Reinicia la contraseña de un usuario identificado por su correo electrónico.
     * 
     * @param responseType Tipo de respuesta esperado.
     * @param userEmail Correo electrónico del usuario cuya contraseña se va a restablecer.
     * @param <T> Tipo de la respuesta esperada.
     * @return La respuesta del servicio en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la operación.
     */
    public <T> T resetPassword_XML(Class<T> responseType, String userEmail) throws WebApplicationException;

    /**
     * Realiza el inicio de sesión de una persona con su correo electrónico y contraseña.
     * 
     * @param responseType Tipo de respuesta esperado.
     * @param email Correo electrónico de la persona.
     * @param contrasena Contraseña de la persona.
     * @param <T> Tipo de la respuesta esperada.
     * @return La respuesta del servicio en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la operación.
     * @throws SignInErrorException Si el inicio de sesión falla debido a credenciales incorrectas.
     */
    public <T> T inicioSesionPersona(Class<T> responseType, String email, String contrasena) throws WebApplicationException, SignInErrorException;

    /**
     * Actualiza la contraseña de una persona.
     * 
     * @param email Correo electrónico de la persona cuya contraseña será actualizada.
     * @param newPassword Nueva contraseña.
     * @throws WebApplicationException Si ocurre un error durante la operación.
     */
    public void updatePassword_XML(String email, String newPassword) throws WebApplicationException;

    /**
     * Obtiene el número total de registros disponibles.
     * 
     * @return El número total de registros en el sistema.
     * @throws WebApplicationException Si ocurre un error durante la operación.
     */
    public String countREST() throws WebApplicationException;

    /**
     * Edita la información de una persona con el identificador proporcionado.
     * 
     * @param requestEntity Objeto que contiene los nuevos datos para la persona.
     * @param id Identificador de la persona.
     * @throws WebApplicationException Si ocurre un error durante la operación.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Busca una persona utilizando su identificador y devuelve la información en formato XML.
     * 
     * @param responseType Tipo de respuesta esperado.
     * @param id Identificador de la persona.
     * @param <T> Tipo de la respuesta esperada.
     * @return La información de la persona en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la operación.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Busca un rango de personas en formato XML.
     * 
     * @param responseType Tipo de respuesta esperado.
     * @param from El índice de inicio del rango.
     * @param to El índice final del rango.
     * @param <T> Tipo de la respuesta esperada.
     * @return Una lista de personas en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la operación.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Crea una nueva persona con los datos proporcionados.
     * 
     * @param requestEntity Objeto que contiene los datos de la nueva persona.
     * @throws WebApplicationException Si ocurre un error durante la operación.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Recupera todas las personas en formato XML.
     * 
     * @param responseType Tipo de respuesta esperado.
     * @param <T> Tipo de la respuesta esperada.
     * @return Una lista de todas las personas en formato XML.
     * @throws WebApplicationException Si ocurre un error durante la operación.
     */
    public <T> T findAll_XML(Class<T> responseType) throws WebApplicationException;

    /**
     * Elimina la persona identificada por su ID.
     * 
     * @param id Identificador de la persona a eliminar.
     * @throws WebApplicationException Si ocurre un error durante la operación.
     */
    public void remove(String id) throws WebApplicationException;

    /**
     * Cierra la conexión o sesión actual de la persona.
     */
    public void close();
}
