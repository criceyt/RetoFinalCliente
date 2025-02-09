/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.List;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import logica.MantenimientoManager;

/**
 * Cliente REST Jersey para interactuar con los servicios web relacionados con
 * el mantenimiento de vehículos. Esta clase proporciona métodos para realizar
 * operaciones de filtrado, creación, edición y eliminación de registros de
 * mantenimiento.
 * <p>
 * La clase implementa la interfaz {@link MantenimientoManager} y se comunica
 * con el servicio REST utilizando tanto el formato XML como JSON.
 * </p>
 *
 * <p>
 * USO:
 * <pre>
 *        MantenimientoRestFull client = new MantenimientoRestFull();
 *        client.filtrarPorIdVehiculo(String idVehiculo);
 *        client.close();
 * </pre>
 * </p>
 *
 * @author 2dam
 */
public class MantenimientoRestFull implements MantenimientoManager {

    /**
     * La URL base del servicio REST.
     */
    private static final String BASE_URI = ResourceBundle.getBundle("entidades.Ruta").getString("RUTA");

    /**
     * Cliente Jersey para hacer peticiones HTTP.
     */
    private Client client;

    /**
     * Objetivo del servicio REST, con la ruta específica para los recursos de
     * mantenimiento.
     */
    private WebTarget webTarget;

    /**
     * Constructor que inicializa el cliente REST y establece la ruta de acceso
     * a los recursos de mantenimiento.
     */
    public MantenimientoRestFull() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("mantenimiento");
    }

    /**
     * Filtra los mantenimientos según la fecha de finalización.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param fechaFinalizacion La fecha de finalización del mantenimiento a
     * filtrar.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de objetos {@link Mantenimiento} que coinciden con el
     * filtro de fecha de finalización.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> List<T> filtradoPorDatePickerMantenimiento(GenericType<List<T>> responseType, String fechaFinalizacion) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("fechaFinalizacion/{0}", new Object[]{fechaFinalizacion}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Filtra los mantenimientos según su éxito.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param mantenimientoExitoso Valor que indica si el mantenimiento fue
     * exitoso.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto {@link Mantenimiento} filtrado por éxito.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T filtrarPorMantenimientoExitoso(Class<T> responseType, String mantenimientoExitoso) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("mantenimientoExitoso/{0}", new Object[]{mantenimientoExitoso}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Devuelve el número total de mantenimientos.
     *
     * @return El número total de mantenimientos en el sistema.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public String countREST() throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Modifica un mantenimiento utilizando datos en formato XML.
     *
     * @param requestEntity Objeto {@link Mantenimiento} con los datos a
     * modificar.
     * @param id El identificador del mantenimiento a modificar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * PUT.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Modifica un mantenimiento utilizando datos en formato JSON.
     *
     * @param requestEntity Objeto {@link Mantenimiento} con los datos a
     * modificar.
     * @param id El identificador del mantenimiento a modificar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * PUT.
     */
    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Filtra los mantenimientos por la fecha de finalización.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param fechaFin La fecha de finalización que se utilizará como filtro.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto de tipo {@link Mantenimiento} filtrado por la fecha de
     * finalización.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T filtrarPorFechaFinParaAbajo(Class<T> responseType, String fechaFin) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("fechaFin/{0}", new Object[]{fechaFin}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca un mantenimiento por su identificador, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param id El identificador del mantenimiento.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto de tipo {@link Mantenimiento} correspondiente al
     * identificador.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca un mantenimiento por su identificador, utilizando formato JSON.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param id El identificador del mantenimiento.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto de tipo {@link Mantenimiento} correspondiente al
     * identificador.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T find_JSON(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca un rango de mantenimientos, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param from El índice inicial del rango.
     * @param to El índice final del rango.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de objetos {@link Mantenimiento} dentro del rango
     * especificado.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca un rango de mantenimientos, utilizando formato JSON.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param from El índice inicial del rango.
     * @param to El índice final del rango.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de objetos {@link Mantenimiento} dentro del rango
     * especificado.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Filtra los mantenimientos por el identificador del vehículo.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param idVehiculo El identificador del vehículo.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de objetos {@link Mantenimiento} relacionados con el
     * vehículo especificado.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T filtrarPorIdVehiculo(Class<T> responseType, String idVehiculo) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("idVehiculo/{0}", new Object[]{idVehiculo}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Crea un nuevo mantenimiento utilizando formato XML.
     *
     * @param requestEntity Objeto {@link Mantenimiento} con los datos del nuevo
     * mantenimiento.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * POST.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea un nuevo mantenimiento utilizando formato JSON.
     *
     * @param requestEntity Objeto {@link Mantenimiento} con los datos del nuevo
     * mantenimiento.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * POST.
     */
    public void create_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Obtiene todos los mantenimientos, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de todos los mantenimientos.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene todos los mantenimientos, utilizando formato JSON.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de todos los mantenimientos.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T findAll_JSON(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Elimina un mantenimiento específico por su identificador.
     *
     * @param id El identificador del mantenimiento a eliminar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * DELETE.
     */
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete();
    }

    /**
     * Cierra el cliente REST para liberar recursos.
     */
    public void close() {
        client.close();
    }
}
