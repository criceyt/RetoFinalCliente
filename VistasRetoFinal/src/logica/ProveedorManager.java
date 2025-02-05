package logica;

import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz para la gestión de proveedores en el sistema.
 * 
 * <p>La interfaz <code>ProveedorManager</code> define los métodos necesarios para realizar operaciones sobre
 * proveedores, como crear, editar, eliminar, y consultar proveedores. Además, permite filtrar y obtener información
 * relacionada con proveedores utilizando criterios específicos.</p>
 * 
 * @author oier
 */
public interface ProveedorManager {

    /**
     * Edita un proveedor utilizando formato XML.
     * 
     * @param requestEntity El objeto que contiene los datos a actualizar.
     * @param id El identificador del proveedor a editar.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Obtiene un proveedor utilizando formato XML.
     * 
     * @param responseType El tipo de respuesta esperada.
     * @param id El identificador del proveedor a buscar.
     * @return El proveedor correspondiente.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Obtiene un rango de proveedores utilizando formato XML.
     * 
     * @param responseType El tipo de respuesta esperada.
     * @param from El índice de inicio del rango.
     * @param to El índice final del rango.
     * @return La lista de proveedores en el rango especificado.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Crea un nuevo proveedor utilizando formato XML.
     * 
     * @param requestEntity El objeto que contiene los datos del nuevo proveedor.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Filtra proveedores utilizando un criterio basado en la fecha de su última actividad.
     * 
     * @param responseType El tipo de respuesta esperada.
     * @param ultimaActividad La fecha de la última actividad para filtrar los proveedores.
     * @return La lista de proveedores filtrados.
     * @throws ClientErrorException Si ocurre un error del cliente.
     */
    public <T> List<T> filtradoPorDatePickerProveedores(GenericType<List<T>> responseType, String ultimaActividad) throws ClientErrorException;

    /**
     * Obtiene todos los proveedores utilizando formato XML.
     * 
     * @param responseType El tipo de respuesta esperada.
     * @return La lista de todos los proveedores.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException;

    /**
     * Elimina un proveedor identificado por su ID.
     * 
     * @param id El identificador del proveedor a eliminar.
     * @throws ClientErrorException Si ocurre un error del cliente.
     */
    public void remove(String id) throws ClientErrorException;

    /**
     * Cierra el gestor de proveedores y libera los recursos.
     */
    public void close();
}
