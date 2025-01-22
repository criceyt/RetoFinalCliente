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
public interface MantenimientoManager {
    
     public <T> T filtradoPorDatePickerMantenimiento(Class<T> responseType, String fechaFinalizacion) throws WebApplicationException;

    public <T> T filtrarPorMantenimientoExitoso(Class<T> responseType, String mantenimientoExitoso) throws WebApplicationException;
    
    public String countREST() throws WebApplicationException;

    public void edit_XML(Object requestEntity, String id) throws WebApplicationException;
    
    public void edit_JSON(Object requestEntity, String id) throws WebApplicationException;

    public <T> T filtrarPorFechaFinParaAbajo(Class<T> responseType, String fechaFin) throws WebApplicationException;

    public <T> T find_XML(Class<T> responseType, String id) throws WebApplicationException ;

    public <T> T find_JSON(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws WebApplicationException;

    public <T> T findRange_JSON(Class<T> responseType, String from, String to) throws WebApplicationException;
    
    public <T> T filtrarPorIdVehiculo(Class<T> responseType, String idVehiculo) throws WebApplicationException;

    public void create_XML(Object requestEntity) throws WebApplicationException ;

    public void create_JSON(Object requestEntity) throws WebApplicationException ;
    
    public <T> List<T> findAll_XML(GenericType<List<T>> responseType) throws WebApplicationException;

    public <T> T findAll_JSON(Class<T> responseType) throws WebApplicationException;
    
    public void remove(String id) throws WebApplicationException;

    public void close();
}
