package logica;

import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz que define las operaciones para la gestión de mantenimientos.
 * 
 * <p>La interfaz <code>MantenimientoManager</code> proporciona una serie de métodos que permiten interactuar con los 
 * datos relacionados con los mantenimientos, tales como filtrarlos por diferentes parámetros, editar registros, 
 * obtener información sobre rangos de mantenimiento y contar registros, entre otros.</p>
 * 
 * <p>Estos métodos facilitan el acceso a los recursos RESTful para gestionar y manipular datos de mantenimiento 
 * en un sistema basado en servicios web.</p>
 * 
 * @author 2dam
 */
public interface MantenimientoManager {

    /**
     * Filtra los mantenimientos por fecha de finalización utilizando un filtro de tipo DatePicker.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param fechaFinalizacion La fecha de finalización para filtrar.
     * @param <T> El tipo de respuesta.
     * @return La lista de mantenimientos que coinciden con el filtro.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> List<T> filtradoPorDatePickerMantenimiento(GenericType<List<T>> responseType, String fechaFinalizacion) throws WebApplicationException;

    /**
     * Filtra los mantenimientos por el estado de si fueron exitosos o no.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param mantenimientoExitoso El estado de éxito del mantenimiento.
     * @param <T> El tipo de respuesta.
     * @return El mantenimiento filtrado por éxito o fracaso.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T filtrarPorMantenimientoExitoso(Class<T> responseType, String mantenimientoExitoso) throws WebApplicationException;

    /**
     * Obtiene el número de registros disponibles en el recurso de mantenimiento.
     * 
     * @return El número de registros en el recurso de mantenimiento.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public String countREST() throws WebApplicationException;

    /**
     * Modifica un mantenimiento existente utilizando un formato XML.
     * 
     * @param requestEntity El objeto con los datos modificados.
     * @param id El identificador del mantenimiento a modificar.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Modifica un mantenimiento existente utilizando un formato JSON.
     * 
     * @param requestEntity El objeto con los datos modificados.
     * @param id El identificador del mantenimiento a modificar.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Filtra los mantenimientos con fecha de finalización menor o igual a la fecha especificada.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param fechaFin La fecha de finalización para el filtro.
     * @param <T> El tipo de respuesta.
     * @return Los mantenimientos filtrados por la fecha de finalización.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T filtrarPorFechaFinParaAbajo(Class<T> responseType, String fechaFin) throws WebApplicationException;

    /**
     * Busca un mantenimiento por su identificador único (ID).
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param id El identificador del mantenimiento a buscar.
     * @param <T> El tipo de respuesta.
     * @return El mantenimiento con el ID especificado.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Busca un mantenimiento por su identificador único (ID) utilizando un formato JSON.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param id El identificador del mantenimiento a buscar.
     * @param <T> El tipo de respuesta.
     * @return El mantenimiento con el ID especificado.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T find_JSON(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Obtiene un rango de mantenimientos entre los índices proporcionados.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param from El índice inicial.
     * @param to El índice final.
     * @param <T> El tipo de respuesta.
     * @return El rango de mantenimientos entre los índices.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Obtiene un rango de mantenimientos entre los índices proporcionados utilizando un formato JSON.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param from El índice inicial.
     * @param to El índice final.
     * @param <T> El tipo de respuesta.
     * @return El rango de mantenimientos entre los índices.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws WebApplicationException;

    /**
     * Filtra los mantenimientos por el ID del vehículo.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param idVehiculo El identificador del vehículo.
     * @param <T> El tipo de respuesta.
     * @return Los mantenimientos filtrados por vehículo.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T filtrarPorIdVehiculo(Class<T> responseType, String idVehiculo) throws WebApplicationException;

    /**
     * Crea un nuevo mantenimiento utilizando un formato XML.
     * 
     * @param requestEntity El objeto con los datos del nuevo mantenimiento.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Crea un nuevo mantenimiento utilizando un formato JSON.
     * 
     * @param requestEntity El objeto con los datos del nuevo mantenimiento.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public void create_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Obtiene todos los mantenimientos en formato XML.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param <T> El tipo de respuesta.
     * @return La lista de todos los mantenimientos.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException;

    /**
     * Obtiene todos los mantenimientos en formato JSON.
     * 
     * @param responseType El tipo de respuesta esperado.
     * @param <T> El tipo de respuesta.
     * @return La lista de todos los mantenimientos.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public <T> T findAll_JSON(Class<T> responseType) throws WebApplicationException;

    /**
     * Elimina un mantenimiento utilizando su identificador único (ID).
     * 
     * @param id El identificador del mantenimiento a eliminar.
     * @throws WebApplicationException Si ocurre un error en la aplicación web.
     */
    public void remove(String id) throws WebApplicationException;

    /**
     * Cierra la conexión con el servicio de mantenimiento.
     */
    public void close();
}
