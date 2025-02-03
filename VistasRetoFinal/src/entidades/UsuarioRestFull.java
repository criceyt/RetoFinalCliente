/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import exceptions.CorreoODniRepeException;
import java.util.List;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import logica.UsuarioManager;
import modelo.Usuario;

/**
 * Jersey REST client generated for REST resource:UsuarioFacadeREST
 * [usuario]<br>
 * USAGE:
 * <pre>
 *        UsuarioRestFull client = new UsuarioRestFull();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class UsuarioRestFull implements UsuarioManager {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/WebApplicationSample/webresources";

    public UsuarioRestFull() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("usuario");
    }

    public String countREST() throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        System.out.println("aaaaaaaaaaaaaaaaaa");
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Usuario.class);
    }

    //public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
    //    webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    //}
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    //public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException {
    //    WebTarget resource = webTarget;
    //    resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
    //    return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    //}
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    //public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws ClientErrorException {
    //    WebTarget resource = webTarget;
    //    resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
    //    return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    //}
    public <T> T mostrarDatosUser(Class<T> responseType, String idPersona) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("idPersona/{0}", new Object[]{idPersona}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void create_XML(Object requestEntity) throws CorreoODniRepeException, WebApplicationException {
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        //webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
        WebTarget resource = webTarget;

        Response response = resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
        
        if(response.getStatus() == 406) {
            throw new CorreoODniRepeException();
        }
        
    }

    //public void create_JSON(Object requestEntity) throws ClientErrorException {
    //    webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    //}
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    //public <T> T findAll_JSON(Class<T> responseType) throws ClientErrorException {
    //    WebTarget resource = webTarget;
    //    return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    //}
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Usuario.class);
    }

    public void close() {
        client.close();
    }

}
