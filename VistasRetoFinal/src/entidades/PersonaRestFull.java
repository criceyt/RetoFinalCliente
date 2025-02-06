/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import exceptions.SignInErrorException;
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
import java.util.ResourceBundle;
import modelo.Persona;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 * Cliente REST Jersey generado para la interfaz PersonaManager, que interactúa
 * con el servicio REST de "Persona". Esta clase proporciona métodos para
 * operaciones como inicio de sesión, actualización de contraseñas y la gestión
 * de personas.
 * <p>
 * USO:
 * <pre>
 *        PersonaRestFull client = new PersonaRestFull();
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
public class PersonaRestFull implements PersonaManager {

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
     * acceder a los recursos de persona.
     */
    public PersonaRestFull() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("persona");
    }

    /**
     * Reinicia la contraseña de un usuario identificado por su correo
     * electrónico.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param email El correo electrónico del usuario cuya contraseña se desea
     * reiniciar.
     * @param <T> El tipo de respuesta esperada.
     * @return La respuesta del servidor al reiniciar la contraseña.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T resetPassword_XML(Class<T> responseType, String email) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("reiniciarContrasena/{0}", new Object[]{email}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Realiza el inicio de sesión de una persona utilizando su correo
     * electrónico y contraseña.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param email El correo electrónico de la persona.
     * @param contrasena La contraseña de la persona.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto que contiene la información de la persona si el inicio
     * de sesión es exitoso.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     * @throws SignInErrorException Si las credenciales no son válidas (código
     * de respuesta 404).
     */
    public <T> T inicioSesionPersona(Class<T> responseType, String email, String contrasena) throws WebApplicationException, SignInErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("inicioSesionPersona/{0}/{1}", new Object[]{email, contrasena}));

        // Realiza la solicitud y captura la respuesta
        Response response = resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .get();

        if (response.getStatus() == 404) {
            throw new SignInErrorException();
        }

        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Actualiza la contraseña de una persona utilizando su correo electrónico y
     * la nueva contraseña.
     *
     * @param email El correo electrónico de la persona cuya contraseña se
     * actualizará.
     * @param newPassword La nueva contraseña que se asignará.
     * @throws ClientErrorException Si ocurre un error durante la solicitud PUT.
     */
    public void updatePassword_XML(String email, String newPassword) throws ClientErrorException {
        String xmlData = "<updatePasswordRequest>"
                + "<email>" + email + "</email>"
                + "<newPassword>" + newPassword + "</newPassword>"
                + "</updatePasswordRequest>";

        webTarget.path("updatePassword")
                .request(MediaType.APPLICATION_XML)
                .put(Entity.entity(xmlData, MediaType.APPLICATION_XML));
    }

    /**
     * Obtiene el número total de personas en el sistema.
     *
     * @return El número total de personas.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public String countREST() throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Modifica la información de una persona identificada por su ID.
     *
     * @param requestEntity El objeto {@link Persona} con los nuevos datos.
     * @param id El ID de la persona a modificar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * PUT.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Persona.class);
    }

    /**
     * Busca una persona por su ID, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param id El ID de la persona a buscar.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto de tipo {@link Persona} correspondiente al ID.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca un rango de personas entre los índices proporcionados, utilizando
     * formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param from El índice inicial del rango.
     * @param to El índice final del rango.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de personas dentro del rango especificado.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Crea una nueva persona utilizando formato XML.
     *
     * @param requestEntity El objeto {@link Persona} con los datos de la nueva
     * persona.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * POST.
     */
    public void create_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Obtiene todas las personas, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de todas las personas.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T findAll_XML(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Elimina una persona por su ID.
     *
     * @param id El ID de la persona a eliminar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * DELETE.
     */
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete(Persona.class);
    }

    /**
     * Cierra el cliente REST, liberando los recursos.
     */
    public void close() {
        client.close();
    }
}
