/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import exceptions.BorradoException;
import logica.ProveedorManager;
import java.util.List;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.Proveedor;

/**
 * Cliente REST Jersey generado para la interfaz ProveedorManager, que
 * interactúa con el servicio REST de "Proveedor". Esta clase proporciona
 * métodos para operaciones como la creación, actualización, eliminación y
 * búsqueda de proveedores.
 * <p>
 * USO:
 * <pre>
 *        ProveedorRestFull client = new ProveedorRestFull();
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
public class ProveedorRestFull implements ProveedorManager {

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
    private static final String BASE_URI = ResourceBundle.getBundle("entidades.Ruta").getString("RUTA");

    /**
     * Constructor que inicializa el cliente REST y establece la URL base para
     * acceder a los recursos de proveedor.
     */
    public ProveedorRestFull() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("/proveedor");
    }

    /**
     * Obtiene el número total de proveedores en el sistema.
     *
     * @return El número total de proveedores.
     * @throws ClientErrorException Si ocurre un error durante la solicitud GET.
     */
    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Modifica la información de un proveedor identificado por su ID,
     * utilizando formato XML.
     *
     * @param requestEntity El objeto {@link Proveedor} con los nuevos datos.
     * @param id El ID del proveedor a modificar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * PUT.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Proveedor.class);
    }

    /**
     * Busca un proveedor por su ID, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param id El ID del proveedor a buscar.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto de tipo {@link Proveedor} correspondiente al ID.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca un rango de proveedores entre los índices proporcionados,
     * utilizando formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param from El índice inicial del rango.
     * @param to El índice final del rango.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de proveedores dentro del rango especificado.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Crea un nuevo proveedor utilizando formato XML.
     *
     * @param requestEntity El objeto {@link Proveedor} con los datos del nuevo
     * proveedor.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * POST.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Filtra proveedores por la fecha de la última actividad.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param ultimaActividad La fecha de la última actividad para filtrar los
     * proveedores.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de proveedores filtrados por la última actividad.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> List<T> filtradoPorDatePickerProveedores(GenericType<List<T>> responseType, String ultimaActividad) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ultimaActividad/{0}", new Object[]{ultimaActividad}));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene todos los proveedores, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de todos los proveedores.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Elimina un proveedor por su ID.
     *
     * @param id El ID del proveedor a eliminar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * DELETE.
     */
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete(Proveedor.class);
    }

    /**
     * Cierra el cliente REST, liberando los recursos.
     */
    public void close() {
        client.close();
    }
}
