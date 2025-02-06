/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import modelo.Vehiculo;

/**
 *
 * @author urkiz
 */
public class VehiculoInfoExtraManager {
    
    private static Vehiculo vehiculo;

    // Método para obtener el vehiculo actual
    public static Vehiculo getVehiculo() {
        return vehiculo;
    }

    // Método para establecer el usuario
    public static void setVehiculo(Vehiculo vehiculo) {
        VehiculoInfoExtraManager.vehiculo = vehiculo;
    }
}