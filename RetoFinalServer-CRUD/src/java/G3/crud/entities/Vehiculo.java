package G3.crud.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "vehiculo", schema = "pruebadb")
@XmlRootElement
public class Vehiculo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idVehiculo;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private Integer potencia;
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    private String tipoVehiculo;

    // Relacion Vehiculo a Mantenimiento
    @OneToMany
    private Set<Mantenimiento> mantenimiento;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vehiculoProveedor", schema="concesionariodb",
            joinColumns = @JoinColumn(name = "vehiculos_idVehiculo",referencedColumnName="idVehiculo"),
            inverseJoinColumns = @JoinColumn(name = "proveedores_idProveedores", referencedColumnName="idProveedor"))
    private Set<Proveedor> proveedores;
        // Relacion de Vehiculo a Compra
    @OneToMany(cascade=ALL, mappedBy = "vehiculo")
    private Set<Compra> compras;

    @XmlTransient
    public Set<Mantenimiento> getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Set<Mantenimiento> mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    // Relacion Vehiculo con Proveedor
    
    
    @XmlTransient
    public Set<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(Set<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }
    

    
    
    // Getters y Setters
    public Long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPotencia() {
        return potencia;
    }

    public void setPotencia(Integer potencia) {
        this.potencia = potencia;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }
    
   

    
   

  
}