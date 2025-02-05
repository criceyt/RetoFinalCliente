package test;

import controladores.ApplicationClientTrabajador;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import jxl.common.Logger;
import modelo.Proveedor;
import static org.junit.Assert.assertEquals;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author oier
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTablaProveedor extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new ApplicationClientTrabajador().start(stage);
    }

    private TableView<Proveedor> tableView;

    @Test
    public void a_testTablaProveedores() {
        // TEST DE PROVEEDOR TABLA
        clickOn("#desplegableGestion");
        clickOn("#gestionProveedores");

        // Verificamos que los botones y elementos estén visibles
        verifyThat("#deleteButton", isVisible());
        verifyThat("#refreshButton", isVisible());
        verifyThat("#addRowButton", isVisible());
        verifyThat("#datePickerFiltro", isVisible());

        // Aquí se asegura de que la tabla esté correctamente inicializada
        tableView = lookup("#tableView").query();

        // Verificamos que la tabla no sea null
        assertNotNull(tableView);

        try {
            Thread.sleep(600);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        // Verificamos cuántas filas tiene la tabla antes de la acción
        int contadorRowAntes = tableView.getItems().size();

        try {
            Thread.sleep(600);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        // Simulamos el clic en el botón de agregar
        clickOn("#addRowButton");

        try {
            Thread.sleep(600);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        // Verificamos que el número de filas ha aumentado en 1
        assertEquals(contadorRowAntes + 1, tableView.getItems().size());

// INTENTAMOS MODIFICAR EL PROVEEDOR
        int rowCount = tableView.getItems().size();

// Modificar el nombre
// Aquí seleccionamos la primera columna (índice 0) de la última fila
        Node cell = lookup(".table-cell").nth((rowCount - 1) * tableView.getColumns().size() + 1).query();

// Hacemos doble clic en la celda seleccionada
        doubleClickOn(cell);

// Hacemos clic para asegurarnos de que la celda está activa
        clickOn(cell);

// Simulamos borrar el contenido de la celda hacia atrás (usando BACK_SPACE)
        for (int i = 0; i < 10; i++) {
            push(KeyCode.BACK_SPACE);  // Borra el contenido hacia atrás, 10 veces
            push(KeyCode.DELETE);  // Borra el contenido hacia adelante, 10 veces
        }

// Escribimos "Diablo" en la celda seleccionada
        write("Diablo");

// Presionamos ENTER para confirmar el cambio
        push(KeyCode.ENTER);

// Verificamos que el nombre se haya modificado en el modelo
        assertEquals("Diablo", tableView.getItems().get(rowCount - 1).getNombreProveedor());

    }

    @Test
    public void test_CrearProveedor() {
    }

}
