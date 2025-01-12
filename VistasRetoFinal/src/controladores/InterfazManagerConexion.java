package controladores;

import entidades.ProveedorBean;
import controladores.TablaProveedoresController;
import java.util.Set;
import java.util.logging.Logger;
import javafx.collections.FXCollections;

/**
 *
 * @author 2dam
 */
public class InterfazManagerConexion implements InterfazManager {

    // Declaracion del Logger y las Colecciones de las TABLAS
    private static final Logger logger = Logger.getLogger("javafxapplicationud3example");
    private Set<ProveedorBean> proveedores;

    // Obtiene todos los datos de los Proveedores
    @Override
    public Set getProveedores() {
        logger.info("Obteniendo todos los Proveedores para la UI");
        return proveedores;
    }

    
}
