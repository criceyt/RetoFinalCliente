/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import logica.ProveedorManager;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import modelo.Proveedor;

/**
 * Jersey REST client generated for REST resource:ProveedorFacadeREST
 * [g3.crud.entities.proveedor]<br>
 * USAGE:
 * <pre>
 *        ProveedorRestFull client = new ProveedorRestFull();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class ProveedorRestFull implements ProveedorManager {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/WebApplicationSample/webresources";


    public ProveedorRestFull() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("/proveedor");
    }

    public String countREST() throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Proveedor.class);
    }

    //public void edit_JSON(Object requestEntity, String id) throws WebApplicationException {
    //    webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    //}

    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    //public <T> T find_JSON(Class<T> responseType, String id) throws WebApplicationException {
    //    WebTarget resource = webTarget;
     //   resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
     //   return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    //}

    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    //public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws WebApplicationException {
    //    WebTarget resource = webTarget;
    //    resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
    //    return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    //}

    public void create_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    //public void create_JSON(Object requestEntity) throws WebApplicationException {
    //    webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    //}

    public <T> T filtradoPorDatePickerProveedores(Class<T> responseType, String ultimaActividad) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ultimaActividad/{0}", new Object[]{ultimaActividad}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    //public <T> T findAll_JSON(Class<T> responseType) throws WebApplicationException {
    //    WebTarget resource = webTarget;
    //    return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    //}

    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Proveedor.class);
    }

    public void close() {
        client.close();
    }

}
