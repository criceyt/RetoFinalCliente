package logica;

import exceptions.CorreoODniRepeException;
import java.util.List;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz para gestionar las operaciones relacionadas con los usuarios.
 * 
 * <p>La interfaz <code>UsuarioManager</code> define los métodos para realizar operaciones CRUD sobre los usuarios,
 * como la creación, modificación, obtención y eliminación. Los métodos también permiten filtrar los usuarios por
 * diferentes criterios, así como manejar excepciones específicas como la repetición de correo o DNI.</p>
 * 
 * <p>Esta interfaz proporciona un conjunto de operaciones para interactuar con los datos del usuario en el sistema.</p>
 * 
 * @author 2dam
 */
public interface UsuarioManager {

    /**
     * Edita los datos de un usuario en formato XML.
     * 
     * @param requestEntity Objeto que contiene los datos a modificar.
     * @param id El identificador del usuario a modificar.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Obtiene los datos de un usuario en formato XML.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param id El identificador del usuario a obtener.
     * @param <T> El tipo de respuesta.
     * @return Los datos del usuario en formato XML.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Obtiene un rango de usuarios en formato XML.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param from El primer valor del rango.
     * @param to El último valor del rango.
     * @param <T> El tipo de respuesta.
     * @return La lista de usuarios en el rango especificado.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Muestra los datos de un usuario en función del identificador de la persona.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param idPersona El identificador de la persona cuyo usuario se desea mostrar.
     * @param <T> El tipo de respuesta.
     * @return Los datos del usuario de la persona.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T mostrarDatosUser(Class<T> responseType, String idPersona) throws WebApplicationException;

    /**
     * Crea un nuevo usuario en formato XML.
     * 
     * @param requestEntity El objeto que contiene los datos del nuevo usuario.
     * @throws CorreoODniRepeException Si el correo o DNI ya están en uso.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public void create_XML(Object requestEntity) throws CorreoODniRepeException, WebApplicationException;

    /**
     * Obtiene todos los usuarios en formato XML.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param responseType El tipo de respuesta esperado.
     * @param <T> El tipo de respuesta.
     * @return La lista de todos los usuarios.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException;

    /**
     * Elimina un usuario por su identificador.
     * 
     * @param id El identificador del usuario a eliminar.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public void remove(String id) throws WebApplicationException;

    /**
     * Cierra la sesión o la conexión activa con el gestor de usuarios.
     */
    public void close();
}
