/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import exceptions.CorreoODniRepeException;
import java.util.List;
import java.util.ResourceBundle;
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
 * Cliente REST Jersey generado para la interfaz UsuarioManager, que interactúa
 * con el servicio REST de "Usuario". Esta clase proporciona métodos para
 * operaciones como la creación, actualización, eliminación y búsqueda de
 * usuarios.
 * <p>
 * USO:
 * <pre>
 *        UsuarioRestFull client = new UsuarioRestFull();
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
public class UsuarioRestFull implements UsuarioManager {

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
     * acceder a los recursos de usuario.
     */
    public UsuarioRestFull() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("usuario");
    }

    /**
     * Obtiene el número total de usuarios en el sistema.
     *
     * @return El número total de usuarios.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public String countREST() throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path("count");
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    /**
     * Modifica la información de un usuario identificado por su ID, utilizando
     * formato XML.
     *
     * @param requestEntity El objeto {@link Usuario} con los nuevos datos.
     * @param id El ID del usuario a modificar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * PUT.
     */
    public void edit_XML(Object requestEntity, String id) throws WebApplicationException {
        System.out.println("aaaaaaaaaaaaaaaaaa");
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Usuario.class);
    }

    /**
     * Busca un usuario por su ID, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param id El ID del usuario a buscar.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto de tipo {@link Usuario} correspondiente al ID.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca un rango de usuarios entre los índices proporcionados, utilizando
     * formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param from El índice inicial del rango.
     * @param to El índice final del rango.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de usuarios dentro del rango especificado.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Muestra los datos de un usuario por su ID de persona, utilizando formato
     * XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param idPersona El ID de persona del usuario a buscar.
     * @param <T> El tipo de respuesta esperada.
     * @return Un objeto de tipo {@link Usuario} correspondiente al ID de
     * persona.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> T mostrarDatosUser(Class<T> responseType, String idPersona) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("idPersona/{0}", new Object[]{idPersona}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Crea un nuevo usuario utilizando formato XML.
     *
     * @param requestEntity El objeto {@link Usuario} con los datos del nuevo
     * usuario.
     * @throws CorreoODniRepeException Si ocurre un error debido a un correo o
     * DNI repetido.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * POST.
     */
    public void create_XML(Object requestEntity) throws CorreoODniRepeException, WebApplicationException {

        WebTarget resource = webTarget;

        Response response = resource
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));

        if (response.getStatus() == 406) {
            throw new CorreoODniRepeException();
        }
    }

    /**
     * Obtiene todos los usuarios, utilizando formato XML.
     *
     * @param responseType El tipo de respuesta esperada.
     * @param <T> El tipo de respuesta esperada.
     * @return Una lista de todos los usuarios.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * GET.
     */
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     * @throws WebApplicationException Si ocurre un error durante la solicitud
     * DELETE.
     */
    public void remove(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request().delete(Usuario.class);
    }

    /**
     * Cierra el cliente REST, liberando los recursos.
     */
    public void close() {
        client.close();
    }
}
