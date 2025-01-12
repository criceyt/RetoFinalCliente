package entidades;

import java.util.Date;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author oier
 */

public class ProveedorBean {

    // Atributos
    private final SimpleLongProperty idProveedor;            // Para Long
    private final SimpleStringProperty nombreProveedor;      // Para String
    private final SimpleObjectProperty<TipoVehiculo> tipoVehiculo;  // Para enumeración (si tienes una clase TipoVehiculo)
    private final SimpleStringProperty especialidad;         // Para String
    private final SimpleObjectProperty<Date> ultimaActividad;   // Para Date

    // Constructor
    public ProveedorBean(Long idProveedor, String nombreProveedor, TipoVehiculo tipoVehiculo, String especialidad, Date ultimaActividad) {
        this.idProveedor = new SimpleLongProperty(idProveedor);
        this.nombreProveedor = new SimpleStringProperty(nombreProveedor);
        this.tipoVehiculo = new SimpleObjectProperty<>(tipoVehiculo);
        this.especialidad = new SimpleStringProperty(especialidad);
        this.ultimaActividad = new SimpleObjectProperty<>(ultimaActividad);
    }

    // Getters y setters
    public long getIdProveedor() {
        return idProveedor.get();
    }

    public void setIdProveedor(long idProveedor) {
        this.idProveedor.set(idProveedor);
    }

    public String getNombreProveedor() {
        return nombreProveedor.get();
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor.set(nombreProveedor);
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo.get();
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo.set(tipoVehiculo);
    }

    public String getEspecialidad() {
        return especialidad.get();
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad.set(especialidad);
    }

    public Date getUltimaActividad() {
        return ultimaActividad.get();
    }

    public void setUltimaActividad(Date ultimaActividad) {
        this.ultimaActividad.set(ultimaActividad);
    }
    
}
