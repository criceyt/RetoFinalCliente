package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ekain
 */

@XmlRootElement
public class Vehiculo implements Serializable {

    // Atributos
    private Long idVehiculo;
    private String marca;
    private String modelo;
    private String color;
    private Integer potencia;
    private Integer km;
    private Integer precio;

    private Date fechaAlta;
    private String tipoVehiculo;
    
    // Constructor

    


    private Set<Mantenimiento> mantenimientos;

    public Set<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(Set<Mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    
    private Set<Proveedor> proveedores;

    @XmlTransient
    public Set<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(Set<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    private Set<Compra> compras;

    
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
    
    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }
    
    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
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

