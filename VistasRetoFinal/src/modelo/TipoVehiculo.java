package modelo;

/**
 * Enumeración que representa los tipos de vehículos disponibles en el sistema.
 * <p>La clase <code>TipoVehiculo</code> define tres tipos de vehículos:
 * <ul>
 *     <li><b>COCHE</b>: Representa un automóvil.</li>
 *     <li><b>MOTO</b>: Representa una motocicleta.</li>
 *     <li><b>CAMION</b>: Representa un camión.</li>
 * </ul>
 * </p>
 * 
 * <p>Además, incluye un método estático <code>fromString</code> que permite convertir
 * un valor de tipo <code>String</code> a un tipo de vehículo correspondiente.</p>
 * 
 * @author 2dam
 */
public enum TipoVehiculo {
    /**
     * Tipo de vehículo: Coche.
     */
    COCHE("COCHE"),

    /**
     * Tipo de vehículo: Moto.
     */
    MOTO("MOTO"),

    /**
     * Tipo de vehículo: Camión.
     */
    CAMION("CAMION");

    private String nombre;

    /**
     * Constructor de la enumeración <code>TipoVehiculo</code>.
     * 
     * @param nombre El nombre del tipo de vehículo.
     */
    TipoVehiculo(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre del tipo de vehículo.
     * 
     * @return El nombre del tipo de vehículo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Convierte un valor de tipo <code>String</code> a un tipo de vehículo correspondiente.
     * Si el texto no coincide con ninguno de los valores definidos, se lanza una excepción.
     * 
     * @param text El texto que representa el tipo de vehículo.
     * @return El tipo de vehículo correspondiente al texto.
     * @throws IllegalArgumentException Si el texto no coincide con ningún tipo de vehículo.
     */
    public static TipoVehiculo fromString(String text) {
        for (TipoVehiculo tipo : TipoVehiculo.values()) {
            if (tipo.nombre.equalsIgnoreCase(text)) {
                return tipo; // Devuelve el enum correspondiente si el nombre coincide
            }
        }
        throw new IllegalArgumentException("Tipo de vehículo no válido: " + text);
    }
}
