/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G3.crud.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author gorka
 */
@Entity
@Table(name="mantenimiento",schema="pruebadb")
@NamedQuery(name="buscarTodosMantenimientos", query="SELECT a FROM Mantenimiento a ORDER BY a.id DESC")


@XmlRootElement
public class Mantenimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Atributos
    @Id     
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMantenimiento;
    private String descripcion;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizacion;
    private boolean mantenimientoExitoso;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Vehiculo vehiculo;
    
    // getters and setters
    
    public Long getId() {
        return idMantenimiento;
    }

    public void setId(Long idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDireccion(String descripcion) {
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
