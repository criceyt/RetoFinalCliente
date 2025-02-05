/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import logica.VehiculoManager;
import modelo.Vehiculo;

/**
 * Cliente REST Jersey generado para la interfaz VehiculoManager, que interactúa
 * con el servicio REST de "Vehiculo". Esta clase proporciona métodos para
 * realizar operaciones como la creación, eliminación, edición y filtrado de
 * vehículos.
 * <p>
 * USO:
 * <pre>
 *        VehiculoRestFull client = new VehiculoRestFull();
 *        Object response = client.XXX(...);
 *        // Hacer lo necesario con la respuesta
 *        client.close();
 * </pre>
 * </p>
 *
 * <p>
 * Esta clase interactúa con la URL base:
 * <code>http://localhost:8080/WebApplicationSample/webresources</code>.
 * </p>
 *
 * @author 2dam
 */
public class VehiculoRestFull implements VehiculoManager {

    /**
     * El objeto WebTarget para las solicitudes REST.
     */
    private WebTarget webTarget;

    /**
     * Cliente Jersey para realizar las solicitudes HTTP.
     */
    private Client client;

    /**
     * URL base del servicio REST.
     */
    private static final String BASE_URI = "http://localhost:8080/WebApplicationSample/webresources";

    /**
     * Constructor que inicializa el cliente REST y establece la URL base para
     * acceder a los recursos de vehiculo.
     */
    public VehiculoRestFull() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("vehiculo");
    }

    /**
     * Filtra los vehículos por el kilometraje (km).
     *
     * @param responseType El tipo de respuesta esperada.
     * @param km El kilometraje para filtrar los vehículos.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de vehículos filtrados por el kilometraje.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T filtrarPorKm(Class<T> responseType, String km) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("km/{0}", new Object[]{km}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Modifica la información de un vehículo utilizando formato XML.
     *
     * @param requestEntity El objeto {@link Vehiculo} con los nuevos datos.
     * @param id El ID del vehículo a modificar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * PUT.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Vehiculo.class);
    }

    /**
     * Filtra los vehículos por precio.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param precio El precio para filtrar los vehículos.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de vehículos filtrados por el precio.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T filtradoPrecioVehiculo(Class<T> responseType, String precio) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("precio/{0}", new Object[]{precio}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene un rango de vehículos entre los índices proporcionados.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param from El índice inicial del rango.
     * @param to El índice final del rango.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de vehículos dentro del rango especificado.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene todos los vehículos, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de todos los vehículos.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Elimina un vehículo por su ID.
     *
     * @param id El ID del vehículo a eliminar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * DELETE.
     */
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete(Vehiculo.class);
    }

    /**
     * Obtiene el número total de vehículos en el sistema.
     *
     * @return El número total de vehículos.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public String countREST() throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Busca un vehículo por su ID, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param id El ID del vehículo a buscar.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto de tipo {@link Vehiculo} correspondiente al ID.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Crea un nuevo vehículo utilizando formato XML.
     *
     * @param requestEntity El objeto {@link Vehiculo} con los datos del nuevo
     * vehículo.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * POST.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Filtra los vehículos por marca.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param marca La marca para filtrar los vehículos.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de vehículos filtrados por marca.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T filtradoMarcaVehiculo(Class<T> responseType, String marca) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("marca/{0}", new Object[]{marca}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Filtra los vehículos por color.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param color El color para filtrar los vehículos.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de vehículos filtrados por color.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T filtradoColorVehiculo(Class<T> responseType, String color) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("color/{0}", new Object[]{color}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Filtra los vehículos por fecha de alta.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param fechaAlta La fecha de alta para filtrar los vehículos.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de vehículos filtrados por la fecha de alta.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> List<T> filtradoDatePickerVehiculo(GenericType<List<T>> responseType, String fechaAlta) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("fechaAlta/{0}", new Object[]{fechaAlta}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Filtra los vehículos por potencia.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param potencia La potencia para filtrar los vehículos.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de vehículos filtrados por potencia.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T filtradoPotenciaVehiculo(Class<T> responseType, String potencia) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("potencia/{0}", new Object[]{potencia}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Cierra el cliente REST, liberando los recursos.
     */
    public void close() {
        client.close();
    }
}
