package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author oier
 */
@XmlRootElement
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos

    private Long idProveedor;
    private String nombreProveedor;
    private TipoVehiculo tipoVehiculo;

    private String especialidad;
    private Date ultimaActividad;

    private Set<Vehiculo> vehiculos;

    public Proveedor() {
        
    }

    
    public Proveedor(Long idProveedor, String nombreProveedor, TipoVehiculo tipoVehiculo, String especialidad, Date ultimaActividad, Set<Vehiculo> vehiculos) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.tipoVehiculo = tipoVehiculo;
        this.especialidad = especialidad;
        this.ultimaActividad = ultimaActividad;
        this.vehiculos = vehiculos;
    }
    
    


    public Set<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(Set<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    // getters and setters
    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Date getUltimaActividad() {
        return ultimaActividad;
    }

    public void setUltimaActividad(Date ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }

    // getters and setters de la lista
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.idProveedor == null && other.idProveedor != null) || (this.idProveedor != null && !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "G3.crud.entities.Proveedores[ id=" + idProveedor + " ]";
    }

}