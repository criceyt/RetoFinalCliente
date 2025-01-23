/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author 2dam
 */

@XmlRootElement
public class CompraId implements Serializable {

    private Long usuarioId;
    private Long vehiculoId;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    @Override
    public String toString() {
        return "CompraId{" + "usuarioId=" + usuarioId + ", vehiculoId=" + vehiculoId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (usuarioId != null ? usuarioId.hashCode() : 0);
        hash = 31 * hash + (vehiculoId != null ? vehiculoId.hashCode() : 0);
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
        if ((this.usuarioId == null && other.usuarioId != null)
                || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        if ((this.vehiculoId == null && other.vehiculoId != null)
                || (this.vehiculoId != null && !this.vehiculoId.equals(other.vehiculoId))) {
            return false;
        }
        return true;
    }

}
