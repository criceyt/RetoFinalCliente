package modelo;

import java.io.Serializable;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa un usuario, que es un tipo de persona con atributos adicionales
 * como el estado premium y las compras realizadas.
 * <p>La clase <code>Usuario</code> hereda de la clase <code>Persona</code> y agrega
 * los atributos específicos relacionados con el usuario, como si es premium o no,
 * y las compras que ha realizado.</p>
 * 
 * @author 2dam
 */
@XmlRootElement
public class Usuario extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    private boolean premium;
    private Set<Compra> compras;

    /**
     * Obtiene el estado premium del usuario.
     * 
     * @return <code>true</code> si el usuario es premium, <code>false</code> en caso contrario.
     */
    public boolean isPremium() {
        return premium;
    }

    /**
     * Establece el estado premium del usuario.
     * 
     * @param premium El valor a establecer, <code>true</code> si es premium, <code>false</code> en caso contrario.
     */
    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    /**
     * Obtiene el conjunto de compras realizadas por el usuario.
     * 
     * @return Un conjunto de objetos <code>Compra</code> que representan las compras realizadas por el usuario.
     */
    public Set<Compra> getCompras() {
        return compras;
    }

    /**
     * Establece las compras realizadas por el usuario.
     * 
     * @param compras El conjunto de compras a establecer.
     */
    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }
}
