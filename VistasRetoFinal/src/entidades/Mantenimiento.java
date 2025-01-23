package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gorka
 */
@XmlRootElement
public class Mantenimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private Long idMantenimiento;
    private String descripcion;
    private Date fechaFinalizacion;
    private boolean mantenimientoExitoso;

    private Vehiculo vehiculo;

    // getters and setters
    public Long getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(Long idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isMantenimientoExitoso() {
        return mantenimientoExitoso;
    }

    public void setMantenimientoExitoso(Boolean mantenimientoExitoso) {
        this.mantenimientoExitoso = mantenimientoExitoso;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * Getter adicional para obtener directamente el ID del vehículo asociado en
     * la tabla. Devuelve null si no hay un vehículo asociado.
     */
    public Long getIdVehiculo() {
        return vehiculo != null ? vehiculo.getIdVehiculo() : null;
    }

    /**
     * Setter adicional para establecer directamente el ID del vehículo asociado
     * en la tabla. Crea un nuevo objeto Vehiculo si no existe previamente.
     *
     * @param idVehiculo El ID del vehículo a establecer.
     */
    public void setIdVehiculo(Long idVehiculo) {
        if (vehiculo == null) {
            vehiculo = new Vehiculo();
        }
        vehiculo.setIdVehiculo(idVehiculo);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMantenimiento != null ? idMantenimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mantenimiento)) {
            return false;
        }
        Mantenimiento other = (Mantenimiento) object;
        if ((this.idMantenimiento == null && other.idMantenimiento != null) || (this.idMantenimiento != null && !this.idMantenimiento.equals(other.idMantenimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "G3.crud.entities.Mantenimiento[ idMantenimiento=" + idMantenimiento + " ]";
    }

}