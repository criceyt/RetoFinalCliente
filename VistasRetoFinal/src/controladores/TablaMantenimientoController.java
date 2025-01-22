package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import logica.MantenimientoManagerFactory;
import logica.VehiculoManagerFactory;
import modelo.Mantenimiento;
import modelo.Vehiculo;

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
    @FXML
    private Button btnAñadirFila;

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
        btnAñadirFila.setOnAction(this::insertarMantenimiento);

        idMantenimientoColumn.setCellValueFactory(new PropertyValueFactory<>("idMantenimiento"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        mantenimientoExitosoColumn.setCellValueFactory(new PropertyValueFactory<>("mantenimientoExitoso"));
        fechaFinalizacionColumn.setCellValueFactory(new PropertyValueFactory<>("fechaFinalizacion"));
        idVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("idVehiculo"));

        // Configurar tabla como editable
        tableView.setEditable(true);
        // Configurar la columna de descripción para usar EditingCell
        descripcionColumn.setCellFactory(column -> new EditingCell());
        mantenimientoExitosoColumn.setCellFactory(column -> new EditingCell());
        fechaFinalizacionColumn.setCellFactory(column -> new EditingCell());
        idVehiculoColumn.setCellFactory(column -> new EditingCell());

        cargarDatosTabla(null);

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
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
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

        // Limpiar la tabla antes de introducir los Items
        tableView.getItems().clear();

        // Obtener la lista de mantenimientos desde el servidor o el origen de datos
        List<Mantenimiento> mantenimientos = MantenimientoManagerFactory.get().findAll_XML(new GenericType<List<Mantenimiento>>() {
        });

        // Convertir la lista de mantenimientos en ObservableList para la TableView
        ObservableList<Mantenimiento> mantenimientosData = FXCollections.observableArrayList(mantenimientos);

        // Establecer los datos en la tabla
        tableView.setItems(mantenimientosData);
    }

    private void insertarMantenimiento(ActionEvent event) {
        try {

            // Calcula el siguiente ID disponible
            Long siguienteId = obtenerSiguienteIdMantenimiento();

            // Crea un nuevo objeto Mantenimiento vacío (con valores predeterminados)
            Mantenimiento mantenimientoVacio = new Mantenimiento();
            mantenimientoVacio.setIdMantenimiento(siguienteId); //Carga el siguiente id
            mantenimientoVacio.setDescripcion("Introduzca aqui la descripcion del mantenimiento");
            mantenimientoVacio.setMantenimientoExitoso(false);  // Valor predeterminado para mantenimientoExitoso
            mantenimientoVacio.setFechaFinalizacion(new java.util.Date());
            mantenimientoVacio.setEditable(true); // Permitir edición del idVehiculo la primera vez

            // Llamada al servicio REST para enviar el nuevo mantenimiento
            MantenimientoManagerFactory.get().create_XML(mantenimientoVacio);

            // Si es necesario, también puedes agregar otras validaciones o condiciones aquí
            // Añadir el nuevo objeto a la lista de la tabla
            tableView.getItems().add(mantenimientoVacio);

            // Actualizar la vista de la tabla
            tableView.refresh();

        } catch (Exception e) {
            // Manejo de cualquier excepción, como por ejemplo problemas en la adición de datos
            //  Alert("Error al crear la fila:\n" + e.getMessage());
            // LOGGER.log(Level.SEVERE, "Error al añadir fila: {0}", e.getMessage());
        }
    }

    private Long obtenerSiguienteIdMantenimiento() {
        try {
            // Llama al servicio REST para obtener todos los mantenimientos
            List<Mantenimiento> mantenimientos = MantenimientoManagerFactory.get().findAll_XML(new GenericType<List<Mantenimiento>>() {
            });

            // Extrae los IDs actuales
            List<Long> ids = mantenimientos.stream().map(Mantenimiento::getIdMantenimiento).collect(Collectors.toList());

            // Encuentra el ID máximo y suma 1
            return ids.isEmpty() ? 1L : Collections.max(ids) + 1L;
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Error al obtener IDs de mantenimiento", ButtonType.OK).showAndWait();
            return 1L; // Retorna 1 como fallback
        }
    }

   /* private List<Long> obtenerIdVehiculosDisponibles() {
        try {
            // Llama al servicio REST para obtener todos los vehículos
            List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
            });
            return vehiculos.stream().map(Vehiculo::getIdVehiculo).collect(Collectors.toList());
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Error al obtener vehiculos", ButtonType.OK).showAndWait();
            return new ArrayList<>(); // Retorna una lista vacía si ocurre un error
        }
    }*/

}

