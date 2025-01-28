/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface VehiculoManager {
    
    public <T> T filtrarPorKm(Class<T> responseType, String km) throws WebApplicationException;

    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;

    //public void edit_JSON(Object requestEntity, String id) throws ClientErrorException {
    //    webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    //}

    public <T> T filtradoPrecioVehiculo(Class<T> responseType, String precio) throws WebApplicationException;

    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException;

    //public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws ClientErrorException {
    //   WebTarget resource = webTarget;
    //    resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{from, to}));
    //    return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    //}

    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException;

    //public <T> T findAll_JSON(Class<T> responseType) throws ClientErrorException {
    //    WebTarget resource = webTarget;
    //    return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    //}

    public void remove(String id) throws WebApplicationException;
    
    public String countREST() throws WebApplicationException;

    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException;

    //public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException {
    //    WebTarget resource = webTarget;
    //    resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
    //    return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    //}

    public void create_XML(Object requestEntity) throws WebApplicationException;
    
    //public void create_JSON(Object requestEntity) throws ClientErrorException {
    //    webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    //}

    public <T> T filtradoMarcaVehiculo(Class<T> responseType, String marca) throws WebApplicationException;

    public <T> T filtradoColorVehiculo(Class<T> responseType, String color) throws WebApplicationException;

    public <T> List<T> filtradoDatePickerVehiculo(GenericType<List<T>> responseType, String fechaAlta) throws WebApplicationException;

    public <T> T filtradoPotenciaVehiculo(Class<T> responseType, String potencia) throws WebApplicationException;

    public void close();
}
