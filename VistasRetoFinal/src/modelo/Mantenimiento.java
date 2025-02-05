package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa el mantenimiento de un vehículo en el sistema.
 * <p>Esta clase contiene información sobre un mantenimiento específico,
 * incluyendo la descripción del mantenimiento, la fecha de finalización,
 * si el mantenimiento fue exitoso, y el vehículo asociado.</p>
 * 
 * <p>Está marcada con la anotación <code>@XmlRootElement</code> para permitir
 * su conversión a formato XML.</p>
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

    // Getters y Setters
    /**
     * Obtiene el identificador del mantenimiento.
     * 
     * @return El identificador del mantenimiento.
     */
    public Long getIdMantenimiento() {
        return idMantenimiento;
    }

    /**
     * Establece el identificador del mantenimiento.
     * 
     * @param idMantenimiento El identificador del mantenimiento.
     */
    public void setIdMantenimiento(Long idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    /**
     * Obtiene la descripción del mantenimiento.
     * 
     * @return La descripción del mantenimiento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del mantenimiento.
     * 
     * @param descripcion La descripción del mantenimiento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Verifica si el mantenimiento fue exitoso.
     * 
     * @return <code>true</code> si el mantenimiento fue exitoso, de lo contrario <code>false</code>.
     */
    public boolean isMantenimientoExitoso() {
        return mantenimientoExitoso;
    }

    /**
     * Establece si el mantenimiento fue exitoso.
     * 
     * @param mantenimientoExitoso <code>true</code> si el mantenimiento fue exitoso, de lo contrario <code>false</code>.
     */
    public void setMantenimientoExitoso(boolean mantenimientoExitoso) {
        this.mantenimientoExitoso = mantenimientoExitoso;
    }

    /**
     * Obtiene la fecha de finalización del mantenimiento.
     * 
     * @return La fecha de finalización del mantenimiento.
     */
    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    /**
     * Establece la fecha de finalización del mantenimiento.
     * 
     * @param fechaFinalizacion La fecha de finalización del mantenimiento.
     */
    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    /**
     * Obtiene el vehículo asociado al mantenimiento.
     * 
     * @return El vehículo asociado al mantenimiento.
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Establece el vehículo asociado al mantenimiento.
     * 
     * @param vehiculo El vehículo asociado al mantenimiento.
     */
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * Obtiene el identificador del vehículo asociado directamente desde
     * la tabla de vehículo. Devuelve <code>null</code> si no hay vehículo asociado.
     * 
     * @return El identificador del vehículo o <code>null</code> si no existe vehículo asociado.
     */
    public Long getIdVehiculo() {
        return vehiculo != null ? vehiculo.getIdVehiculo() : null;
    }

    /**
     * Establece el identificador del vehículo asociado directamente en
     * la tabla de vehículo. Si el vehículo no existe previamente, se crea uno nuevo.
     * 
     * @param idVehiculo El identificador del vehículo.
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
        if ((this.idMantenimiento == null && other.idMantenimiento != null) || 
            (this.idMantenimiento != null && !this.idMantenimiento.equals(other.idMantenimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "G3.crud.entities.Mantenimiento[ idMantenimiento=" + idMantenimiento + " ]";
    }
}
