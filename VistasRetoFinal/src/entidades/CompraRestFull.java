/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import logica.CompraManager;
import modelo.Compra;

/**
 * Clase cliente REST para interactuar con los servicios web relacionados con
 * las compras. Utiliza la especificación Jersey para realizar peticiones HTTP a
 * un servicio REST que gestiona entidades de tipo {@link Compra}.
 * <p>
 * Esta clase implementa la interfaz {@link CompraManager} y proporciona métodos
 * para realizar operaciones CRUD sobre las compras.
 * </p>
 *
 * <p>
 * USO:
 * <pre>
 *        CompraRestFull client = new CompraRestFull();
 *        client.create_JSON(Compra objetoCompra);
 *        client.close();
 * </pre>
 * </p>
 *
 * @author 2dam
 */
public class CompraRestFull implements CompraManager {

    /**
     * La URL base del servicio REST.
     */
    private static final String BASE_URI = "http://localhost:8080/WebApplicationSample/webresources";

    /**
     * Instancia del cliente Jersey.
     */
    private Client client;

    /**
     * Objetivo del servicio REST, con la ruta específica a los recursos de
     * compra.
     */
    private WebTarget webTarget;

    /**
     * Constructor que inicializa el cliente REST y establece la ruta de acceso
     * a los recursos de compra.
     */
    public CompraRestFull() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("compra");
    }

    /**
     * Devuelve el número total de compras en el sistema.
     *
     * @return El número total de compras.
     * @throws WebApplicationException Si ocurre un error durante la llamada al
     * servicio.
     */
    public String countREST() throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Modifica una compra utilizando datos en formato XML.
     *
     * @param requestEntity Objeto {@link Compra} con los datos a modificar.
     * @param id El identificador de la compra a modificar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * PUT.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Compra.class);
    }

    /**
     * Modifica una compra utilizando datos en formato JSON.
     *
     * @param requestEntity Objeto {@link Compra} con los datos a modificar.
     * @param id El identificador de la compra a modificar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * PUT.
     */
    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Busca una compra por su identificador, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param id El identificador de la compra.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto de tipo {@link Compra} correspondiente al
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
     * Busca una compra por su identificador, utilizando formato JSON.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param id El identificador de la compra.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto de tipo {@link Compra} correspondiente al
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
     * Busca un rango de compras, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param from El índice inicial del rango.
     * @param to El índice final del rango.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de objetos {@link Compra} dentro del rango
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
     * Busca un rango de compras, utilizando formato JSON.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param from El índice inicial del rango.
     * @param to El índice final del rango.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de objetos {@link Compra} dentro del rango
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
     * Crea una nueva compra utilizando formato XML.
     *
     * @param requestEntity Objeto {@link Compra} con los datos de la nueva
     * compra.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * POST.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea una nueva compra utilizando formato JSON.
     *
     * @param requestEntity Objeto {@link Compra} con los datos de la nueva
     * compra.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * POST.
     */
    public void create_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Obtiene todas las compras en formato XML.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de objetos {@link Compra}.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene todas las compras en formato JSON.
     *
     * @param responseType El tipo de respuesta que se espera recibir.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de objetos {@link Compra}.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T findAll_JSON(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Elimina una compra identificada por su ID.
     *
     * @param id El identificador de la compra a eliminar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * DELETE.
     */
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Compra.class);
    }

    /**
     * Cierra el cliente REST para liberar los recursos.
     */
    public void close() {
        client.close();
    }
}
