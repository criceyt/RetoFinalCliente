/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import logica.PersonaManager;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import modelo.Persona;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 * Jersey REST client generated for REST resource:PersonaFacadeREST
 * [persona]<br>
 * USAGE:
 * <pre>
 *        PersonaRestFull client = new PersonaRestFull();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class PersonaRestFull implements PersonaManager {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/WebApplicationSample/webresources";

    public PersonaRestFull() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("persona");
    }

    public <T> T resetPassword_XML(Class<T> responseType, String email) throws WebApplicationException {
        WebTarget resource = webTarget;
        System.out.println(email);
        resource = resource.path(java.text.MessageFormat.format("reiniciarContrasena/{0}", new Object[]{email}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T inicioSesionPersona(Class<T> responseType, String email, String contrasena) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("inicioSesionPersona/{0}/{1}", new Object[]{email, contrasena}));

        System.out.println("Clase recibida: " + responseType.getName());

        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);

    }

    public void updatePassword_XML(String email, String newPassword) throws ClientErrorException {
        String xmlData = "<updatePasswordRequest>"
                + "<email>" + email + "</email>"
                + "<newPassword>" + newPassword + "</newPassword>"
                + "</updatePasswordRequest>";

        webTarget.path("updatePassword")
                .request(MediaType.APPLICATION_XML)
                .put(Entity.entity(xmlData, MediaType.APPLICATION_XML));
    }

    public String countREST() throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Persona.class);
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
    //    resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
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
        System.out.println("eeeeeeeeeeeeeeeeeee");
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    //public void create_JSON(Object requestEntity) throws WebApplicationException {
    //    webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    //}
    public <T> T findAll_XML(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    //public <T> T findAll_JSON(Class<T> responseType) throws WebApplicationException {
    //    WebTarget resource = webTarget;
    //    return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    //}
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Persona.class);
    }

    public void close() {
        client.close();
    }

}
