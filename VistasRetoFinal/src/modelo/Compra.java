package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa una compra realizada por un usuario de un vehículo en el sistema.
 * <p>La clase <code>Compra</code> está asociada a un usuario y un vehículo, y contiene la matrícula del vehículo
 * y la fecha de la compra.</p>
 * 
 * <p>La entidad <code>Compra</code> está marcada con <code>@XmlRootElement</code>, lo que permite su conversión
 * a formato XML.</p>
 * 
 * @author 2dam
 */
@XmlRootElement
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;

    private CompraId idCompra;
    private Usuario usuario;
    private Vehiculo vehiculo;
    private String matricula;
    private Date fechaCompra;

    /**
     * Obtiene el identificador de la compra.
     * 
     * @return El identificador de la compra.
     */
    public CompraId getIdCompra() {
        return idCompra;
    }

    /**
     * Establece el identificador de la compra.
     * 
     * @param idCompra El identificador de la compra.
     */
    public void setIdCompra(CompraId idCompra) {
        this.idCompra = idCompra;
    }

    /**
     * Obtiene el usuario que realizó la compra.
     * 
     * @return El usuario que realizó la compra.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que realizó la compra.
     * 
     * @param usuario El usuario que realizó la compra.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el vehículo que fue comprado.
     * 
     * @return El vehículo que fue comprado.
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Establece el vehículo que fue comprado.
     * 
     * @param vehiculo El vehículo que fue comprado.
     */
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     * Obtiene la matrícula del vehículo comprado.
     * 
     * @return La matrícula del vehículo comprado.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Establece la matrícula del vehículo comprado.
     * 
     * @param matricula La matrícula del vehículo comprado.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtiene la fecha de la compra.
     * 
     * @return La fecha de la compra.
     */
    public Date getFechaCompra() {
        return fechaCompra;
    }

    /**
     * Establece la fecha de la compra.
     * 
     * @param fechaCompra La fecha de la compra.
     */
    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompra != null ? idCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.idCompra == null && other.idCompra != null) || (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "G3.crud.entities.Compra[ idCompra=" + idCompra + " ]";
    }
    
}
