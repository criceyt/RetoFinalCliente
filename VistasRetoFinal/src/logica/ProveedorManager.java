/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author oier
 */
public interface ProveedorManager {

    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;

    //public void edit_JSON(Object requestEntity, String id) throws ClientErrorException;

    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException;

    //public <T> T find_JSON(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException;

    //public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws ClientErrorException;

    public void create_XML(Object requestEntity) throws WebApplicationException;

    //public void create_JSON(Object requestEntity) throws ClientErrorException;

    public <T> List<T> filtradoPorDatePickerProveedores(GenericType<List<T>> responseType, String ultimaActividad) throws ClientErrorException;

    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException;

    //public <T> T findAll_JSON(Class<T> responseType) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    public void close();
    


    
}
