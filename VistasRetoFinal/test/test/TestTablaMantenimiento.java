package test;

import controladores.ApplicationClientTrabajador;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import modelo.Mantenimiento;
import modelo.Proveedor;
import modelo.TipoVehiculo;
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
public class TestTablaMantenimiento extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new ApplicationClientTrabajador().start(stage);
    }

    private TableView<Mantenimiento> tableView;
    LocalDate today = LocalDate.now();
    String hoy = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    @Test
    public void a_testTablaProveedores() {

        // TEST DE MANTENIMIENTO TABLA
        clickOn("#desplegableGestion");
        clickOn("#gestionMantenimientos");

        // Verificamos que los botones y elementos estén visibles
        verifyThat("#btnBorrar", isVisible());
        verifyThat("#refreshButton", isVisible());
        verifyThat("#btnAñadirFila", isVisible());
        verifyThat("#datePickerFiltro", isVisible());

        // Insercion del Objeto Proveedor
        tableView = lookup("#tableView").query();
        assertNotNull(tableView);
        esperar();
        int contadorRowAntes = tableView.getItems().size();
        esperar();
        clickOn("#btnAñadirFila");
        esperar();
        assertEquals(contadorRowAntes + 1, tableView.getItems().size());

        // Edicion del Objeto Proveedor 
        int rowCount = tableView.getItems().size();

        // Modificar la Descripccion
        Node celda = lookup(".table-cell").nth((rowCount - 1) * tableView.getColumns().size() + 1).query();
        doubleClickOn(celda);
        clickOn(celda);
        for (int i = 0; i < 31; i++) {
            push(KeyCode.BACK_SPACE);  // Borra el contenido hacia atrás, 10 veces
            push(KeyCode.DELETE);  // Borra el contenido hacia adelante, 10 veces
        }

        write("Fallo de Filtro");
        push(KeyCode.ENTER);
        assertEquals("Fallo de Filtro", tableView.getItems().get(rowCount - 1).getDescripcion());
        esperar();
        push(KeyCode.ENTER);
        clickOn("Aceptar");
        esperar();

        // Modificamos la Ultima Actividad DatePicker)
        celda = lookup(".table-cell").nth((rowCount - 1) * tableView.getColumns().size() + 3).query();
        doubleClickOn(celda);
        clickOn(celda);
        write("05/02/2026");
        push(KeyCode.ENTER);
        esperar();
        press(KeyCode.ENTER);

        // Modificar el idVehiculo
        celda = lookup(".table-cell").nth((rowCount - 1) * tableView.getColumns().size() + 4).query();
        doubleClickOn(celda);
        clickOn(celda);
        Node choiceBox = lookup(".choice-box").query();
        clickOn(choiceBox);
        clickOn("20");
        push(KeyCode.ENTER);
        assertEquals(20L, tableView.getItems().get(rowCount - 1).getIdVehiculo().longValue());
        push(KeyCode.ENTER);

        // Borrado del Objeto Mantenimiento
        esperar();
        int contadorRow = tableView.getItems().size();
        Node row = lookup(".table-row-cell").nth(contadorRow - 1).query();
        clickOn(row);
        clickOn(row);
        clickOn("#btnBorrar");
        clickOn("Aceptar");
        assertEquals(contadorRow - 1, tableView.getItems().size());

        // Filtrado Tabla DatePicker (Sin Datos Coincidan)
        rowCount = tableView.getItems().size();
        clickOn("#datePickerFiltro");
        write("05/02/2026");
        push(KeyCode.ENTER);
        assertEquals(0, tableView.getItems().size());
        esperar();

        // Filtrado Tabla DatePicker (Con Datos Coincidan)
        rowCount = tableView.getItems().size();
        clickOn("#datePickerFiltro");
        for (int i = 0; i < 25; i++) {
            push(KeyCode.BACK_SPACE);  // Borra el contenido hacia atrás, 10 veces
            push(KeyCode.DELETE);  // Borra el contenido hacia adelante, 10 veces
        }
        write("3/02/2025");
        push(KeyCode.ENTER);
        assertEquals(2, tableView.getItems().size());
    }

    // Metodo que hace un Sleep
    public void esperar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("Error al intetar Hacer el Sleep");
        }
    }
}
