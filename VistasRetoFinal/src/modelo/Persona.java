package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Representa una persona en el sistema, que puede ser un usuario o un trabajador.
 * <p>Esta clase contiene información básica sobre una persona, como su
 * DNI, email, nombre completo, fecha de registro, dirección, teléfono,
 * y contraseña. Está marcada con la anotación <code>@XmlRootElement</code>
 * para permitir su conversión a formato XML.</p>
 * 
 * <p>La anotación <code>@XmlSeeAlso</code> permite que las clases
 * <code>Usuario</code> y <code>Trabajador</code> también puedan ser procesadas
 * como subclases de <code>Persona</code>.</p>
 * 
 * @author 2dam
 */
@XmlRootElement
@XmlSeeAlso({Usuario.class, Trabajador.class})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // Atributos
    private Long idPersona;
    private String dni;
    private String email;
    private String nombreCompleto;
    private Date fechaRegistro;
    private Integer codigoPostal;
    private Integer telefono;
    private String direccion;
    private String contrasena;

    // Getters y Setters

    /**
     * Obtiene el identificador de la persona.
     * 
     * @return El identificador de la persona.
     */
    public Long getIdPersona() {
        return idPersona;
    }

    /**
     * Establece el identificador de la persona.
     * 
     * @param idPersona El identificador de la persona.
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Obtiene el identificador de la persona. Es un alias para el método
     * <code>getIdPersona</code>.
     * 
     * @return El identificador de la persona.
     */
    public Long getId() {
        return idPersona;
    }

    /**
     * Establece el identificador de la persona. Es un alias para el método
     * <code>setIdPersona</code>.
     * 
     * @param idPersona El identificador de la persona.
     */
    public void setId(Long idPersona) {
        this.idPersona = idPersona;
    }

    /**
     * Obtiene el DNI de la persona.
     * 
     * @return El DNI de la persona.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI de la persona.
     * 
     * @param dni El DNI de la persona.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el email de la persona.
     * 
     * @return El email de la persona.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email de la persona.
     * 
     * @param email El email de la persona.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el nombre completo de la persona.
     * 
     * @return El nombre completo de la persona.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo de la persona.
     * 
     * @param nombreCompleto El nombre completo de la persona.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene la fecha de registro de la persona.
     * 
     * @return La fecha de registro de la persona.
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Establece la fecha de registro de la persona.
     * 
     * @param fechaRegistro La fecha de registro de la persona.
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Obtiene el código postal de la persona.
     * 
     * @return El código postal de la persona.
     */
    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Establece el código postal de la persona.
     * 
     * @param codigoPostal El código postal de la persona.
     */
    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * Obtiene el teléfono de la persona.
     * 
     * @return El teléfono de la persona.
     */
    public Integer getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono de la persona.
     * 
     * @param telefono El teléfono de la persona.
     */
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección de la persona.
     * 
     * @return La dirección de la persona.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la persona.
     * 
     * @param direccion La dirección de la persona.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la contraseña de la persona.
     * 
     * @return La contraseña de la persona.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Establece la contraseña de la persona.
     * 
     * @param contrasena La contraseña de la persona.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.idPersona == null && other.idPersona != null) || 
            (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "G3.crud.entities.Persona[ idPersona=" + idPersona + " ]";
    }
}
