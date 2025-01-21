/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G3.crud.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author 2dam
 */
@Embeddable
public class CompraId implements Serializable {

    private Integer usuarioId;
    private Integer vehiculoId;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Integer vehiculoId) {
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
