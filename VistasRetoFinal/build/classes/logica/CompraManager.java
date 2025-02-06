package logica;

import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz que define los métodos para gestionar las operaciones de compra a través de un servicio RESTful.
 * 
 * <p>Esta interfaz proporciona métodos para crear, editar, eliminar y consultar compras en formato XML y JSON. 
 * Los métodos permiten la gestión de compras en una base de datos a través de un cliente RESTful, utilizando operaciones estándar como GET, POST, PUT y DELETE.</p>
 * 
 * <p>Los métodos que trabajan con rangos permiten obtener un subconjunto de compras, y aquellos que recuperan todas las compras devuelven una lista de objetos.</p>
 * 
 * @author 2dam
 */
public interface CompraManager {

    /**
     * Modifica los datos de una compra en formato XML.
     * 
     * @param requestEntity Los datos de la compra que se van a modificar.
     * @param id El identificador de la compra que se va a modificar.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Modifica los datos de una compra en formato JSON.
     * 
     * @param requestEntity Los datos de la compra que se van a modificar.
     * @param id El identificador de la compra que se va a modificar.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Recupera los datos de una compra en formato XML por su identificador.
     * 
     * @param responseType El tipo de respuesta esperada.
     * @param id El identificador de la compra a recuperar.
     * @param <T> El tipo de respuesta.
     * @return El objeto correspondiente a la compra en formato XML.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Recupera los datos de una compra en formato JSON por su identificador.
     * 
     * @param responseType El tipo de respuesta esperada.
     * @param id El identificador de la compra a recuperar.
     * @param <T> El tipo de respuesta.
     * @return El objeto correspondiente a la compra en formato JSON.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public <T> T find_JSON(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Recupera un rango de compras en formato XML.
     * 
     * @param responseType El tipo de respuesta esperada.
     * @param from El índice de inicio del rango.
     * @param to El índice final del rango.
     * @param <T> El tipo de respuesta.
     * @return Un objeto que representa el rango de compras en formato XML.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Recupera un rango de compras en formato JSON.
     * 
     * @param responseType El tipo de respuesta esperada.
     * @param from El índice de inicio del rango.
     * @param to El índice final del rango.
     * @param <T> El tipo de respuesta.
     * @return Un objeto que representa el rango de compras en formato JSON.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Crea una nueva compra en formato XML.
     * 
     * @param requestEntity Los datos de la compra que se van a crear.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Crea una nueva compra en formato JSON.
     * 
     * @param requestEntity Los datos de la compra que se van a crear.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public void create_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Recupera todas las compras en formato XML.
     * 
     * @param responseType El tipo de respuesta esperada.
     * @param <T> El tipo de respuesta.
     * @return Una lista de objetos que representan las compras en formato XML.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException;

    /**
     * Recupera todas las compras en formato JSON.
     * 
     * @param responseType El tipo de respuesta esperada.
     * @param <T> El tipo de respuesta.
     * @return Una lista de objetos que representan las compras en formato JSON.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public <T> T findAll_JSON(Class<T> responseType) throws WebApplicationException;

    /**
     * Elimina una compra por su identificador.
     * 
     * @param id El identificador de la compra que se va a eliminar.
     * @throws WebApplicationException Si ocurre un error en la aplicación web al intentar realizar la operación.
     */
    public void remove(String id) throws WebApplicationException;

    /**
     * Cierra la conexión del cliente con el servicio RESTful.
     */
    public void close();
}
