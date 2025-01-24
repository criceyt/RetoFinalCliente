/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2dam
 */

@XmlRootElement
public class Usuario extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // Atributos
    private boolean premium;

    private Set<Compra> compras;
    
    
   // getters and setters
    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }

}
