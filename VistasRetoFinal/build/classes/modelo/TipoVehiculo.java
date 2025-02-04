package modelo;

/**
 *
 * @author 2dam
 */
public enum TipoVehiculo {
    COCHE("COCHE"),
    MOTO("MOTO"),
    CAMION("CAMION");

    private String nombre;

    // Constructor
    TipoVehiculo(String nombre) {
        this.nombre = nombre;
    }

    // Getter para el nombre
    public String getNombre() {
        return nombre;
    }

    // Método estático para convertir un String a un TipoVehiculo
    public static TipoVehiculo fromString(String text) {
        for (TipoVehiculo tipo : TipoVehiculo.values()) {
            if (tipo.nombre.equalsIgnoreCase(text)) {
                return tipo; // Devuelve el enum correspondiente si el nombre coincide
            }
        }
        throw new IllegalArgumentException("Tipo de vehículo no válido: " + text);
    }
}