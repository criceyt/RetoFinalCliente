package modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa la clave primaria compuesta de una compra, que se forma por el identificador
 * de la persona (usuario) y el identificador del vehículo asociado a la compra.
 * <p>La clase <code>CompraId</code> es utilizada para identificar de forma única una compra
 * en el sistema, basada en los identificadores de la persona y el vehículo.</p>
 * 
 * <p>Esta clase está marcada con <code>@XmlRootElement</code> para permitir su conversión
 * a formato XML.</p>
 * 
 * @author 2dam
 */
@XmlRootElement
public class CompraId implements Serializable {

    private Long idPersona;
    private Long idVehiculo;

    /**
     * Obtiene el identificador de la persona asociada a la compra.
     * 
     * @return El identificador de la persona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Establece el identificador de la persona asociada a la compra.
     * 
     * @param idPersona El identificador de la persona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Obtiene el identificador del vehículo asociado a la compra.
     * 
     * @return El identificador del vehículo.
     */
    public Long getIdVehiculo() {
        return idVehiculo;
    }

    /**
     * Establece el identificador del vehículo asociado a la compra.
     * 
     * @param idVehiculo El identificador del vehículo.
     */
    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    @Override
    public String toString() {
        return "CompraId{" + "idPersona=" + idPersona + ", idVehiculo=" + idVehiculo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (idPersona != null ? idPersona.hashCode() : 0);
        hash = 31 * hash + (idVehiculo != null ? idVehiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CompraId other = (CompraId) obj;
        if ((this.idPersona == null && other.idPersona != null)
                || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        if ((this.idVehiculo == null && other.idVehiculo != null)
                || (this.idVehiculo != null && !this.idVehiculo.equals(other.idVehiculo))) {
            return false;
        }
        return true;
    }
}
