package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import logica.MantenimientoManagerFactory;
import entidades.Mantenimiento;

public class TablaMantenimientoController implements Initializable {

    // Botones de navegación y menú
    @FXML
    private Button homeBtn;
    @FXML
    private Button cerrarSesionBtn;
    @FXML
    private MenuItem gestionVehiculos;
    @FXML
    private MenuItem gestionProveedores;
    @FXML
    private MenuItem gestionMantenimientos;

    // Tabla y columnas
    @FXML
    private TableView<Mantenimiento> tableView;
    @FXML
    private TableColumn<Mantenimiento, Long> idMantenimientoColumn;
    @FXML
    private TableColumn<Mantenimiento, String> descripcionColumn;
    /*@FXML
    private TableColumn<Persona, String> dniColumn;
    @FXML
    private TableColumn<Compra, String> matriculaColumn;
    @FXML
    private TableColumn<Vehiculo, String> marcaColumn;
    @FXML
    private TableColumn<Vehiculo, String> modeloColumn;*/
    @FXML
    private TableColumn<Mantenimiento, Boolean> mantenimientoExitosoColumn;
    @FXML
    private TableColumn<Mantenimiento, Date> fechaFinalizacionColumn;
    @FXML
    private TableColumn<Mantenimiento, Long> idVehiculoColumn;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button refreshButton;

    // Campos adicionales del formulario
    @FXML
    private TextField dniField;
    @FXML
    private TextField descripcionField;
    @FXML
    private TextField matriculaField;
    @FXML
    private TextField marcaField;
    @FXML
    private TextField modeloField;
    @FXML
    private DatePicker Finalizacion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Listeners para los botones de navegación
        homeBtn.setOnAction(this::irAtras);
        gestionVehiculos.setOnAction(this::abrirVentanaGestionVehiculos);
        gestionProveedores.setOnAction(this::abrirVentanaGestionProveedores);
        gestionMantenimientos.setOnAction(this::abrirVentanaGestionMantenimientos);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        btnBorrar.setOnAction(this::borrarMantenimiento);
        refreshButton.setOnAction(this::cargarDatosTabla);

       
        idMantenimientoColumn.setCellValueFactory(new PropertyValueFactory<>("idMantenimiento"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        /*dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        matriculaColumn.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));*/
        mantenimientoExitosoColumn.setCellValueFactory(new PropertyValueFactory<>("mantenimientoExitoso"));
        fechaFinalizacionColumn.setCellValueFactory(new PropertyValueFactory<>("fechaFinalizacion"));
        idVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("idVehiculo"));
       
        // Configurar tabla como editable
        tableView.setEditable(true);

        // Cargar datos en la tabla
        List<Mantenimiento> mantenimientos = MantenimientoManagerFactory.get().findAll_XML(new GenericType<List<Mantenimiento>>() {
        });
        if (mantenimientos == null) {
            System.err.println("No se pudo obtener la lista de mantenimientos desde el servidor.");
            mantenimientos = new ArrayList<>();
        }

        ObservableList<Mantenimiento> mantenimientosData = FXCollections.observableArrayList(mantenimientos);
        tableView.setItems(mantenimientosData);

        // Borrado
        btnBorrar.setDisable(true);
        // Listener para habilitar o deshabilitar el botón de borrado según la selección
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Si hay un elemento seleccionado, habilitar el botón de borrado
                btnBorrar.setDisable(false);
            } else {
                // Si no hay elementos seleccionados, deshabilitar el botón de borrado
                btnBorrar.setDisable(true);
            }
        });
    }

    private void abrirVentanaSignInSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignInSignUp.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("SignIn & SignUp");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/stylesOscuro.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    private void irAtras(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipalTrabajador.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Navegación Principal Trabajador");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    private void abrirVentanaGestionVehiculos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TablaVehiculos.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Gestión de Vehículos");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    private void abrirVentanaGestionProveedores(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TablaProveedores.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Gestión de Proveedores");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    private void abrirVentanaGestionMantenimientos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TablaMantenimiento.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Gestión de Mantenimientos");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Metodo que borra el Mantenimiento de la tabla y de la base de datos
    private void borrarMantenimiento(ActionEvent event) {
        Mantenimiento mantenimientoSeleccionado = tableView.getSelectionModel().getSelectedItem();

        if (mantenimientoSeleccionado != null) {
            // Crear la alerta de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de Borrado");
            alert.setHeaderText("¿Estás seguro de que deseas borrar este mantenimiento?");
            alert.setContentText("Esta acción no se puede deshacer.");

            // Mostrar la alerta y esperar la respuesta del usuario
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Si el usuario hace clic en "OK", borrar el mantenimiento
                    Long id = mantenimientoSeleccionado.getIdMantenimiento();
                    String idParseado = String.valueOf(id);

                    // Llamada al método para borrar el mantenimiento
                    MantenimientoManagerFactory.get().remove(idParseado);

                    // Actualizar la lista de mantenimientos en la tabla
                    List<Mantenimiento> mantenimientos = MantenimientoManagerFactory.get().findAll_XML(new GenericType<List<Mantenimiento>>() {
                    });
                    ObservableList<Mantenimiento> mantenimientosData = FXCollections.observableArrayList(mantenimientos);
                    tableView.setItems(mantenimientosData);

                    // Mostrar mensaje de éxito
                    //new Alert(Alert.AlertType.INFORMATION, "Mantenimiento eliminado correctamente.", ButtonType.OK).showAndWait();
                } else {
                    // Si el usuario cancela, no hacer nada
                    System.out.println("Borrado cancelado.");
                }
            });
        } else {
            // Si no hay un mantenimiento seleccionado, mostrar mensaje de advertencia
            new Alert(Alert.AlertType.WARNING, "Por favor, selecciona un mantenimiento para eliminar.", ButtonType.OK).showAndWait();
        }
    }

    // Metodo del Boton Refresh para cargar los elementos en la tabla
    private void cargarDatosTabla(ActionEvent event) {

        // Liampia la tabla antes de introducir los Items
        tableView.getItems().clear();

        // Obtener la lista de mantenimientos desde el servidor o el origen de datos
        List<Mantenimiento> mantenimientos = MantenimientoManagerFactory.get().findAll_XML(new GenericType<List<Mantenimiento>>() {
        });

        // Convertir la lista de mantenimientos en ObservableList para la TableView
        ObservableList<Mantenimiento> mantenimientosData = FXCollections.observableArrayList(mantenimientos);

        // Establecer los datos en la tabla
        tableView.setItems(mantenimientosData);

    }

}