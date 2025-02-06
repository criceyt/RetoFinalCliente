package test;

import controladores.ApplicationClientTrabajador;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import modelo.Proveedor;
import modelo.TipoVehiculo;
import modelo.Vehiculo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
public class TestTablaVehiculo extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new ApplicationClientTrabajador().start(stage);
    }

    private TableView<Vehiculo> tableView;
    LocalDate today = LocalDate.now();
    String hoy = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    @Test
    public void a_testTablaProveedores() {

        // TEST DE PROVEEDOR TABLA
        clickOn("#desplegableGestion");
        clickOn("#gestionVehiculos");

        // Verificamos que los botones y elementos estén visibles
        verifyThat("#deleteButton", isVisible());
        verifyThat("#refreshButton", isVisible());
        verifyThat("#addRowButton", isVisible());
        verifyThat("#datePickerFiltro", isVisible());

        // Insercion del Objeto Proveedor 
        tableView = lookup("#tableViewVehiculo").query();
        assertNotNull(tableView);
        esperar();
        int contadorRowAntes = tableView.getItems().size();
        esperar();
        clickOn("#addRowButton");
        esperar();
        assertEquals(contadorRowAntes + 1, tableView.getItems().size());

        // Edicion del Objeto Proveedor 
        int rowCount = tableView.getItems().size();

        // Modificar el modelo
        Node celda = lookup(".table-cell").nth((rowCount - 1) * tableView.getColumns().size() + 1).query();
        doubleClickOn(celda);
        clickOn(celda);
        for (int i = 0; i < 25; i++) {
            push(KeyCode.BACK_SPACE);  // Borra el contenido hacia atrás, 10 veces
            push(KeyCode.DELETE);  // Borra el contenido hacia adelante, 10 veces
        }
        write("Modelo New");
        push(KeyCode.ENTER);
        assertEquals("Modelo New", tableView.getItems().get(rowCount - 1).getModelo());
        
        // Modificar el modelo
        celda = lookup(".table-cell").nth((rowCount - 1) * tableView.getColumns().size() + 2).query();
        doubleClickOn(celda);
        clickOn(celda);
        for (int i = 0; i < 25; i++) {
            push(KeyCode.BACK_SPACE);  // Borra el contenido hacia atrás, 10 veces
            push(KeyCode.DELETE);  // Borra el contenido hacia adelante, 10 veces
        }
        write("Marca New");
        push(KeyCode.ENTER);
        assertEquals("Marca New", tableView.getItems().get(rowCount - 1).getMarca());
        
        // Modificar el modelo
        celda = lookup(".table-cell").nth((rowCount - 1) * tableView.getColumns().size() + 3).query();
        doubleClickOn(celda);
        clickOn(celda);
        for (int i = 0; i < 25; i++) {
            push(KeyCode.BACK_SPACE);  // Borra el contenido hacia atrás, 10 veces
            push(KeyCode.DELETE);  // Borra el contenido hacia adelante, 10 veces
        }
        write("Rojo");
        push(KeyCode.ENTER);
        assertEquals("Rojo", tableView.getItems().get(rowCount - 1).getColor());
        
        // Int

        // Modificar el modelo
        celda = lookup(".table-cell").nth((rowCount - 1) * tableView.getColumns().size() + 4).query();
        doubleClickOn(celda);
        clickOn(celda);
        for (int i = 0; i < 25; i++) {
            push(KeyCode.BACK_SPACE);  // Borra el contenido hacia atrás, 10 veces
            push(KeyCode.DELETE);  // Borra el contenido hacia adelante, 10 veces
        }
        write("123");
        push(KeyCode.ENTER);
        assertEquals(123L, tableView.getItems().get(rowCount - 1).getPotencia().longValue());        
        // Borrado del Objeto Proveedor
        esperar();
        int contadorRow = tableView.getItems().size();
        Node row = lookup(".table-row-cell").nth(contadorRow - 1).query();
        clickOn(row);
        clickOn("#deleteButton");
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
        write("23/01/2025");
        push(KeyCode.ENTER);
        assertEquals(1, tableView.getItems().size());
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