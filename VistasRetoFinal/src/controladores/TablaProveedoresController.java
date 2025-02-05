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
    private TableView tableView;

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

    // Abrir Ventana SignIn & SignUp
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
                    // Se carga el FXML con la información de la vista viewSignUp.
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignInSignUp.fxml"));
                    Parent root = loader.load();

                    SignController controler = loader.getController();

                    // Obtener el Stage desde el nodo que disparó el evento.
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                    stage.setTitle("SignIn & SignUp");
                    // Se crea un nuevo objeto de la clase Scene con el FXML cargado.
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/stylesOscuro.css").toExternalForm());

                    // Se muestra en la ventana el Scene creado.
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    // Si salta una IOException significa que ha habido algún 
                    // problema al cargar el FXML o al intentar llamar a la nueva 
                    // ventana, por lo que se mostrará un Alert con el mensaje 
                    // "Error en la sincronización de ventanas, intentalo más tarde".
                    Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
                    new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
                }
                // Aquí puedes agregar el código necesario para cerrar la sesión
            } else {
                // Lógica si el usuario cancela
                System.out.println("Cancelado, no se cierra la sesión.");
            }
        });
    }

    // Boton HOME para volver atras
    private void irAtras(ActionEvent event) {
        try {
            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipalTrabajador.fxml"));
            Parent root = loader.load();

            // Crear un ScrollPane para envolver el contenido
            ScrollPane sc = new ScrollPane();
            sc.setContent(root);

            // Configurar el ScrollPane para que solo permita desplazamiento vertical
            sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Desactiva la barra de desplazamiento horizontal
            sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Activa la barra de desplazamiento vertical

            // Configurar el Scene
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Navegación Principal Trabajador");

            // Crear la nueva escena con el ScrollPane
            Scene scene = new Scene(sc);
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

            // Establecer la escena y mostrarla
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Abrir Ventana Gestion Proveedores
    private void abrirVentanaGestionProveedores(ActionEvent event) {
        try {
            // Se carga el FXML con la información de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TablaProveedores.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            TablaProveedoresController controller = loader.getController();

            // Obtener el Stage
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Gestión de Proveedores");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TablaProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Abrir Ventana Gestion Mantenimiento
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
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Abrir Ventana Gestion Vehiculos
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
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(NavegacionPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Metodo del Boton Refresh para cargar los elementos en la tabla
    private void cargartDatosTabla(ActionEvent event) {

        // Liampia la tabla antes de introducir los Items
        tableView.getItems().clear();

        // Obtener la lista de proveedores desde el servidor o el origen de datos
        List<Proveedor> proveedores = ProveedorManagerFactory.get().findAll_XML(new GenericType<List<Proveedor>>() {
        });

        // Convertir la lista de proveedores en ObservableList para la TableView
        ObservableList<Proveedor> proveedoresData = FXCollections.observableArrayList(proveedores);

        // Establecer los datos en la tabla
        tableView.setItems(proveedoresData);

    }

    // Metodo que borra al Proveedor de la tabla y de la base de datos
    private void borrarProveedor(ActionEvent event) {

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
                    // Si el usuario hace clic en "OK", borrar el mantenimiento
                    Long id = proveedorSeleccionado.getIdProveedor();
                    String idParseado = String.valueOf(id);

                    // Llamada al método para borrar el mantenimiento
                    ProveedorManagerFactory.get().remove(idParseado);

                    // Obtener la lista de proveedores desde el servidor o el origen de datos
                    List<Proveedor> proveedores = ProveedorManagerFactory.get().findAll_XML(new GenericType<List<Proveedor>>() {
                    });

                    // Convertir la lista de proveedores en ObservableList para la TableView
                    ObservableList<Proveedor> proveedoresData = FXCollections.observableArrayList(proveedores);

                    // Establecer los datos en la tabla
                    tableView.setItems(proveedoresData);

                    // Mostrar mensaje de éxito
                    //new Alert(Alert.AlertType.INFORMATION, "Mantenimiento eliminado correctamente.", ButtonType.OK).showAndWait();
                } else {
                    // Si el usuario cancela, no hacer nada
                    System.out.println("Borrado cancelado.");
                }
            });
        }
    }

    // Añadir Linea para insertar Proveedor
    private void añadirLinea(ActionEvent event) {

        try {

            Proveedor porveedorLinea = new Proveedor();

            // La fecha se puede cambiar pero debe ser automatica
            Date fechaAuto = new Date();
            porveedorLinea.setUltimaActividad(fechaAuto);
            porveedorLinea.setNombreProveedor("Introduce el Nombre del Nuevo Proveedor");
            porveedorLinea.setTipoVehiculo(TipoVehiculo.COCHE);
            porveedorLinea.setEspecialidad("Introduce la Especialidad del Nuevo Proveedor");

            ProveedorManagerFactory.get().create_XML(porveedorLinea);

            cargartDatosTabla(null);

        } catch (Exception e) {
            System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        }
    }

    // Metodo que crea el informe
    private void crearInforme(ActionEvent event) {

        try {

            JasperReport report = JasperCompileManager.compileReport("src/informes/InformeProveedor.jrxml");

            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Proveedor>) this.tableView.getItems());

            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

            jasperViewer.setVisible(true);

        } catch (JRException e) {

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
