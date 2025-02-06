package logica;

import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz para la gestión de vehículos.
 * 
 * <p>La interfaz <code>VehiculoManager</code> define los métodos necesarios para realizar operaciones CRUD sobre los vehículos
 * y permitir la búsqueda y filtrado de vehículos según diferentes criterios como kilometraje, precio, marca, color, potencia, etc.</p>
 * 
 * <p>Esta interfaz puede ser implementada para gestionar el acceso a los datos de vehículos a través de un servicio web RESTful.</p>
 * 
 * @author 2dam
 */
public interface VehiculoManager {
    
    /**
     * Filtra vehículos por kilometraje.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param km El kilometraje por el cual se filtran los vehículos.
     * @return Una respuesta de tipo <code>responseType</code> que contiene los vehículos filtrados.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public <T> T filtrarPorKm(Class<T> responseType, String km) throws WebApplicationException;

    /**
     * Edita un vehículo existente en formato XML.
     * 
     * @param requestEntity El objeto que representa los datos a actualizar.
     * @param id El ID del vehículo a actualizar.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Filtra vehículos por precio.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param precio El precio por el cual se filtran los vehículos.
     * @return Una respuesta de tipo <code>responseType</code> que contiene los vehículos filtrados.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public <T> T filtradoPrecioVehiculo(Class<T> responseType, String precio) throws WebApplicationException;

    /**
     * Recupera un rango de vehículos en formato XML.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param from El índice inicial del rango.
     * @param to El índice final del rango.
     * @return Una respuesta de tipo <code>responseType</code> que contiene el rango de vehículos.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Recupera todos los vehículos en formato XML.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @return Una lista de vehículos en formato XML.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException;

    /**
     * Elimina un vehículo por su ID.
     * 
     * @param id El ID del vehículo a eliminar.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public void remove(String id) throws WebApplicationException;
    
    /**
     * Cuenta la cantidad total de vehículos disponibles.
     * 
     * @return El número total de vehículos.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public String countREST() throws WebApplicationException;

    /**
     * Recupera un vehículo por su ID en formato XML.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param id El ID del vehículo a recuperar.
     * @return Una respuesta de tipo <code>responseType</code> que contiene el vehículo.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Crea un nuevo vehículo en formato XML.
     * 
     * @param requestEntity El objeto que representa los datos del nuevo vehículo.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Filtra vehículos por marca.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param marca La marca por la cual se filtran los vehículos.
     * @return Una respuesta de tipo <code>responseType</code> que contiene los vehículos filtrados.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public <T> T filtradoMarcaVehiculo(Class<T> responseType, String marca) throws WebApplicationException;

    /**
     * Filtra vehículos por color.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param color El color por el cual se filtran los vehículos.
     * @return Una respuesta de tipo <code>responseType</code> que contiene los vehículos filtrados.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public <T> T filtradoColorVehiculo(Class<T> responseType, String color) throws WebApplicationException;

    /**
     * Filtra vehículos por fecha de alta.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param fechaAlta La fecha de alta por la cual se filtran los vehículos.
     * @return Una lista de vehículos filtrados por la fecha de alta.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public <T> List<T> filtradoDatePickerVehiculo(GenericType<List<T>> responseType, String fechaAlta) throws WebApplicationException;

    /**
     * Filtra vehículos por potencia.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param potencia La potencia por la cual se filtran los vehículos.
     * @return Una respuesta de tipo <code>responseType</code> que contiene los vehículos filtrados.
     * @throws WebApplicationException Si ocurre un error en la aplicación.
     */
    public <T> T filtradoPotenciaVehiculo(Class<T> responseType, String potencia) throws WebApplicationException;

    /**
     * Cierra la conexión o recurso utilizado por el gestor de vehículos.
     */
    public void close();
}
