/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import logica.ProveedorManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.ws.rs.core.GenericType;
import modelo.Proveedor;
import modelo.TipoVehiculo;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author urkiz
 */
public class TablaProveedoresController implements Initializable {

    // Elementos de la Ventana
    @FXML
    private Button homeBtn;

    @FXML
    private Button refreshButton;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cerrarSesionBtn;

    @FXML
    private MenuItem gestionVehiculos;

    @FXML
    private MenuItem gestionProveedores;

    @FXML
    private MenuItem gestionMantenimientos;

    @FXML
    private TableView<Proveedor> tableView;

    @FXML
    private TableColumn<Proveedor, Long> idProveedorColumn;

    @FXML
    private TableColumn<Proveedor, String> nombreColumn;

    @FXML
    private TableColumn<Proveedor, TipoVehiculo> tipoColumn;

    @FXML
    private TableColumn<Proveedor, String> especialidadColumn;

    @FXML
    private TableColumn<Proveedor, Date> ultimaActividadColumn;

    @FXML
    private TextField NombreField;

    @FXML
    private TextField especialidadField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private Button deleteButton;

    @FXML
    private Button printBtn;

    @FXML
    private Button addRowButton;

    @FXML
    private DatePicker datePickerFiltro;

// Declaraciones
    private Logger LOGGER = Logger.getLogger(TablaProveedoresController.class.getName());
    private DatePicker datePicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Se añaden los listeners a todos los botones.
        homeBtn.setOnAction(this::irAtras);
        gestionVehiculos.setOnAction(this::abrirVentanaGestionVehiculos);
        gestionProveedores.setOnAction(this::abrirVentanaGestionProveedores);
        gestionMantenimientos.setOnAction(this::abrirVentanaGestionMantenimientos);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        refreshButton.setOnAction(this::cargartDatosTabla);
        deleteButton.setOnAction(this::borrarProveedor);
        addRowButton.setOnAction(this::añadirLinea);
        printBtn.setOnAction(this::crearInforme);

        cargartDatosTabla(null);

        // Filtrado de DatePicker
        datePickerFiltro.setOnAction(event -> {
            LocalDate filtro = datePickerFiltro.getValue();
            String filtroString = filtro.toString();

            // Limpia la tabla
            tableView.getItems().clear();

            // Coje los datos por query filtrado
            List<Proveedor> proveedoresfiltro = ProveedorManagerFactory.get().filtradoPorDatePickerProveedores(new GenericType<List<Proveedor>>() {
            }, filtroString);

            // Convertir la lista de proveedores en ObservableList para la TableView
            ObservableList<Proveedor> proveedoresDataFiltro = FXCollections.observableArrayList(proveedoresfiltro);

            // Establecer los datos en la tabla
            tableView.setItems(proveedoresDataFiltro);
        });

        // Configuración de las columnas de la tabla.
        idProveedorColumn.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoVehiculo"));
        especialidadColumn.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        ultimaActividadColumn.setCellValueFactory(new PropertyValueFactory<>("ultimaActividad"));

        // Configurar tabla como editable
        tableView.setEditable(true);

        // Configurar la columna de descripción para usar EditingCell
        nombreColumn.setCellFactory(column -> new EditingCellProveedor());
        tipoColumn.setCellFactory(column -> new EditingCellProveedor());
        especialidadColumn.setCellFactory(column -> new EditingCellProveedor());
        ultimaActividadColumn.setCellFactory(column -> new EditingCellProveedor<>());

        // Borrado
        deleteButton.setDisable(true);
        // Listener para habilitar o deshabilitar el botón de borrado según la selección
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Si hay un elemento seleccionado, habilitar el botón de borrado
                deleteButton.setDisable(false);
            } else {
                // Si no hay elementos seleccionados, deshabilitar el botón de borrado
                deleteButton.setDisable(true);
            }
        });
    }

    private void abrirVentanaSignInSignUp(ActionEvent event) {
        // Crear un alert de tipo confirmación
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Estás seguro de que deseas cerrar sesión?");
        alert.setContentText("Perderás cualquier cambio no guardado.");

        // Mostrar la alerta y esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Cargar el FXML con la vista de SignIn/SignUp
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignInSignUp.fxml"));
                    Parent root = loader.load();

                    SignController controler = loader.getController();

                    // Obtener el Stage (ventana actual)
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                    // Configurar el título de la ventana
                    stage.setTitle("SignIn & SignUp");

                    // Crear una nueva escena con el contenido cargado
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/stylesOscuro.css").toExternalForm());

                    // Cambiar la escena y mostrarla
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    // Si ocurre un error al cargar el FXML o cambiar la ventana
                    Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
                    new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
                }
            } else {
                // Si el usuario cancela el cierre de sesión
                System.out.println("Cancelado, no se cierra la sesión.");
            }
        });
    }

    private void irAtras(ActionEvent event) {
        try {
            // Cargar el FXML de la vista NavegacionPrincipalTrabajador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipalTrabajador.fxml"));
            Parent root = loader.load();

            // Crear un ScrollPane para envolver el contenido
            ScrollPane sc = new ScrollPane();
            sc.setContent(root);

            // Configurar el ScrollPane para solo permitir desplazamiento vertical
            sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Desactiva la barra de desplazamiento horizontal
            sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Activa la barra de desplazamiento vertical

            // Obtener el Stage actual
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Navegación Principal Trabajador");

            // Crear la nueva escena con el ScrollPane
            Scene scene = new Scene(sc);
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

            // Establecer la escena y mostrarla
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            // Manejo de excepciones si hay problemas al cargar la nueva vista
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    private void abrirVentanaGestionProveedores(ActionEvent event) {
        try {
            // Cargar el FXML de la vista de gestión de proveedores
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TablaProveedores.fxml"));
            Parent root = loader.load();

            // Obtener el controlador asociado al FXML cargado
            TablaProveedoresController controller = loader.getController();

            // Obtener el Stage actual
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Gestión de Proveedores");

            // Crear la nueva escena con el contenido de la vista cargada
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());  // Estilo CSS para la tabla

            // Cambiar la escena y mostrarla
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Manejo de excepciones si hay problemas al cargar la vista
            Logger.getLogger(TablaProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    private void abrirVentanaGestionMantenimientos(ActionEvent event) {
        try {
            // Se carga el FXML con la información de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TablaMantenimiento.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            TablaMantenimientoController controller = loader.getController();

            // Obtener el Stage
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Gestión de Mantenimientos");

            // Crear la nueva escena con el contenido de la vista cargada
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());  // Estilo CSS para la tabla

            // Cambiar la escena y mostrarla
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Manejo de excepciones si hay problemas al cargar la vista
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    private void abrirVentanaGestionVehiculos(ActionEvent event) {
        try {
            // Se carga el FXML con la información de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TablaVehiculos.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            TablaVehiculosController controller = loader.getController();

            // Obtener el Stage
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Gestión de Vehículos");

            // Crear la nueva escena con el contenido de la vista cargada
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());  // Estilo CSS para la tabla

            // Cambiar la escena y mostrarla
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Manejo de excepciones si hay problemas al cargar la vista
            Logger.getLogger(NavegacionPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    private void cargartDatosTabla(ActionEvent event) {
        // Limpiar la tabla antes de introducir los nuevos elementos
        tableView.getItems().clear();

        // Obtener la lista de proveedores desde el servidor o el origen de datos
        List<Proveedor> proveedores = ProveedorManagerFactory.get().findAll_XML(new GenericType<List<Proveedor>>() {
        });

        // Convertir la lista de proveedores en ObservableList para la TableView
        ObservableList<Proveedor> proveedoresData = FXCollections.observableArrayList(proveedores);

        // Establecer los datos en la tabla
        tableView.setItems(proveedoresData);
    }

    private void borrarProveedor(ActionEvent event) {
        // Obtener el proveedor seleccionado de la tabla
        Proveedor proveedorSeleccionado = (Proveedor) tableView.getSelectionModel().getSelectedItem();

        if (proveedorSeleccionado != null) {
            // Crear la alerta de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de Borrado");
            alert.setHeaderText("¿Estás seguro de que deseas borrar este Proveedor?");
            alert.setContentText("Esta acción no se puede deshacer.");

            // Mostrar la alerta y esperar la respuesta del usuario
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Si el usuario hace clic en "OK", proceder con el borrado
                    Long id = proveedorSeleccionado.getIdProveedor();
                    String idParseado = String.valueOf(id);

                    // Llamada al método para borrar el proveedor
                    ProveedorManagerFactory.get().remove(idParseado);

                    // Obtener la lista actualizada de proveedores
                    List<Proveedor> proveedores = ProveedorManagerFactory.get().findAll_XML(new GenericType<List<Proveedor>>() {
                    });

                    // Convertir la lista de proveedores en ObservableList para la TableView
                    ObservableList<Proveedor> proveedoresData = FXCollections.observableArrayList(proveedores);

                    // Establecer los datos en la tabla
                    tableView.setItems(proveedoresData);

                    // Mostrar mensaje de éxito (comentado, pero podría habilitarse si se desea)
                    //new Alert(Alert.AlertType.INFORMATION, "Proveedor eliminado correctamente.", ButtonType.OK).showAndWait();
                } else {
                    // Si el usuario cancela, no hacer nada
                    System.out.println("Borrado cancelado.");
                }
            });
        }
    }

    private void añadirLinea(ActionEvent event) {
        try {
            // Crear un nuevo proveedor
            Proveedor porveedorLinea = new Proveedor();

            // Establecer la fecha actual automáticamente
            Date fechaAuto = new Date();
            porveedorLinea.setUltimaActividad(fechaAuto);
            porveedorLinea.setNombreProveedor("Introduce el Nombre del Nuevo Proveedor");
            porveedorLinea.setTipoVehiculo(TipoVehiculo.COCHE);
            porveedorLinea.setEspecialidad("Introduce la Especialidad del Nuevo Proveedor");

            // Crear el proveedor en la base de datos
            ProveedorManagerFactory.get().create_XML(porveedorLinea);

            // Recargar los datos en la tabla
            cargartDatosTabla(null);

        } catch (Exception e) {
            System.out.println("Error al añadir línea");
        }
    }

    private void crearInforme(ActionEvent event) {
        try {
            // Compilar el informe Jasper
            JasperReport report = JasperCompileManager.compileReport("src/informes/InformeProveedor.jrxml");

            // Crear una fuente de datos a partir de la tabla
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Proveedor>) this.tableView.getItems());

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();

            // Rellenar el informe con los datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            // Visualizar el informe
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

        } catch (JRException e) {
            // Manejo de excepciones y errores en la generación del informe
            LOGGER.log(Level.SEVERE, "Error al generar el informe", e);

            // Crear un Alert de tipo ERROR
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Generando Informe");
            alert.setHeaderText("Hubo un problema al generar el informe");
            alert.setContentText("Por favor, intente más tarde o contacte con el administrador.");

            // Mostrar el Alert
            alert.showAndWait();
        }
    }
}