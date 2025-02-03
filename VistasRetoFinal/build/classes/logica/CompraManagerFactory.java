/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import entidades.CompraRestFull;

/**
 *
 * @author 2dam
 */
public class CompraManagerFactory {
    private static CompraManager compraManager;

    public static CompraManager get() {
        if (compraManager == null) {
            compraManager = new CompraRestFull();
        }
        return compraManager;
    }
}