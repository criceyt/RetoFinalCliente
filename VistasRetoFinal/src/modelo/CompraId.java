/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author 2dam
 */

@XmlRootElement
public class CompraId implements Serializable {

    private Long idPersona;
    private Long idVehiculo;

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdVehiculo() {
        return idVehiculo;
    }

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