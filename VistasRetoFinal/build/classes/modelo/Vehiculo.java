package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Representa un vehículo con diversos atributos como marca, modelo, color,
 * potencia, kilometraje, precio, y fecha de alta. También puede tener mantenimientos,
 * proveedores asociados y compras realizadas.
 * <p>La clase <code>Vehiculo</code> contiene información detallada sobre un vehículo,
 * así como sus relaciones con otros objetos como mantenimientos, proveedores y compras.</p>
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
    private String ruta;

    private Date fechaAlta;
    private TipoVehiculo tipoVehiculo;

    private Set<Mantenimiento> mantenimientos;

    /**
     * Obtiene el conjunto de mantenimientos asociados al vehículo.
     * 
     * @return Un conjunto de objetos <code>Mantenimiento</code> que representan los mantenimientos realizados en el vehículo.
     */
    public Set<Mantenimiento> getMantenimientos() {
        return mantenimientos;
    }

    /**
     * Establece el conjunto de mantenimientos asociados al vehículo.
     * 
     * @param mantenimientos El conjunto de mantenimientos a establecer.
     */
    public void setMantenimientos(Set<Mantenimiento> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    private Set<Proveedor> proveedores;

    /**
     * Obtiene el conjunto de proveedores asociados al vehículo.
     * 
     * @return Un conjunto de objetos <code>Proveedor</code> que representan los proveedores asociados al vehículo.
     */
    @XmlTransient
    public Set<Proveedor> getProveedores() {
        return proveedores;
    }

    /**
     * Establece el conjunto de proveedores asociados al vehículo.
     * 
     * @param proveedores El conjunto de proveedores a establecer.
     */
    public void setProveedores(Set<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    private Set<Compra> compras;

    /**
     * Obtiene el conjunto de compras asociadas al vehículo.
     * 
     * @return Un conjunto de objetos <code>Compra</code> que representan las compras realizadas del vehículo.
     */
    public Set<Compra> getCompras() {
        return compras;
    }

    /**
     * Establece el conjunto de compras asociadas al vehículo.
     * 
     * @param compras El conjunto de compras a establecer.
     */
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

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
