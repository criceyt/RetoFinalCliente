package G3.crud.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2dam
 */
@Entity
@Table(name="trabajador",schema="pruebadb")
@XmlRootElement
public class Trabajador extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    
    private float sueldo;
    private String puesto;
    
    // getters and setters

    public float getSueldo() {
        return sueldo;
    }

    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}
