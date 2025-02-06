package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Representa un proveedor de vehículos en el sistema.
 * <p>La clase <code>Proveedor</code> contiene información relevante sobre
 * un proveedor, como su nombre, especialidad, la última actividad realizada,
 * el tipo de vehículo que suministra y los vehículos asociados a él.</p>
 * 
 * <p>Los proveedores pueden estar asociados con múltiples vehículos, y esta
 * relación se representa con el atributo <code>vehiculos</code>.</p>
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

    // Constructor por defecto
    public Proveedor() {
    }

    /**
     * Constructor con parámetros para crear un objeto <code>Proveedor</code>.
     * 
     * @param idProveedor El identificador del proveedor.
     * @param nombreProveedor El nombre del proveedor.
     * @param tipoVehiculo El tipo de vehículo asociado al proveedor.
     * @param especialidad La especialidad del proveedor.
     * @param ultimaActividad La última actividad realizada por el proveedor.
     * @param vehiculos Los vehículos suministrados por el proveedor.
     */
    public Proveedor(Long idProveedor, String nombreProveedor, TipoVehiculo tipoVehiculo, String especialidad, Date ultimaActividad, Set<Vehiculo> vehiculos) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.tipoVehiculo = tipoVehiculo;
        this.especialidad = especialidad;
        this.ultimaActividad = ultimaActividad;
        this.vehiculos = vehiculos;
    }

    // Getters y setters

    /**
     * Obtiene el identificador del proveedor.
     * 
     * @return El identificador del proveedor.
     */
    public Long getIdProveedor() {
        return idProveedor;
    }

    /**
     * Establece el identificador del proveedor.
     * 
     * @param idProveedor El identificador del proveedor.
     */
    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * Obtiene el nombre del proveedor.
     * 
     * @return El nombre del proveedor.
     */
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    /**
     * Establece el nombre del proveedor.
     * 
     * @param nombreProveedor El nombre del proveedor.
     */
    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    /**
     * Obtiene el tipo de vehículo suministrado por el proveedor.
     * 
     * @return El tipo de vehículo.
     */
    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    /**
     * Establece el tipo de vehículo suministrado por el proveedor.
     * 
     * @param tipoVehiculo El tipo de vehículo.
     */
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    /**
     * Obtiene la especialidad del proveedor.
     * 
     * @return La especialidad del proveedor.
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece la especialidad del proveedor.
     * 
     * @param especialidad La especialidad del proveedor.
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Obtiene la fecha de la última actividad del proveedor.
     * 
     * @return La fecha de la última actividad.
     */
    public Date getUltimaActividad() {
        return ultimaActividad;
    }

    /**
     * Establece la fecha de la última actividad del proveedor.
     * 
     * @param ultimaActividad La fecha de la última actividad.
     */
    public void setUltimaActividad(Date ultimaActividad) {
        this.ultimaActividad = ultimaActividad;
    }

    /**
     * Obtiene los vehículos suministrados por el proveedor.
     * 
     * @return El conjunto de vehículos asociados al proveedor.
     */
    public Set<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    /**
     * Establece los vehículos suministrados por el proveedor.
     * 
     * @param vehiculos El conjunto de vehículos asociados al proveedor.
     */
    public void setVehiculos(Set<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
