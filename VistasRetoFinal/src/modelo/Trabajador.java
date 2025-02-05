package modelo;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa un trabajador, que es un tipo de persona con atributos adicionales
 * como el sueldo y el puesto.
 * <p>La clase <code>Trabajador</code> hereda de la clase <code>Persona</code> y agrega
 * los atributos específicos relacionados con el empleo del trabajador, como el sueldo
 * y el puesto.</p>
 * 
 * @author 2dam
 */
@XmlRootElement
public class Trabajador extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private float sueldo;
    private String puesto;

    /**
     * Obtiene el sueldo del trabajador.
     * 
     * @return El sueldo del trabajador.
     */
    public float getSueldo() {
        return sueldo;
    }

    /**
     * Establece el sueldo del trabajador.
     * 
     * @param sueldo El sueldo a establecer para el trabajador.
     */
    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }

    /**
     * Obtiene el puesto del trabajador.
     * 
     * @return El puesto del trabajador.
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * Establece el puesto del trabajador.
     * 
     * @param puesto El puesto a establecer para el trabajador.
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}
