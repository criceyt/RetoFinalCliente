package controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.ws.rs.core.GenericType;
import logica.MantenimientoManagerFactory;
import modelo.Mantenimiento;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

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
    @FXML
    private Button btnGuardar;
    @FXML
    private Button printBtn;

    @FXML
    private DatePicker datePickerFiltro;

// Declaraciones
    private Logger LOGGER = Logger.getLogger(TablaMantenimientoController.class.getName());
    private Mantenimiento mantenimientoVacio;

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
        btnGuardar.setOnAction(this::guardarMantenimiento);
        printBtn.setOnAction(this::crearInforme);

        cargarDatosTabla(null);

        // Filtrado de DatePicker
        datePickerFiltro.setOnAction(event -> {
            // Obtener el valor seleccionado del DatePicker
            LocalDate filtro = datePickerFiltro.getValue();
            String filtroString = filtro.toString();

            // Limpia la tabla
            tableView.getItems().clear();

            // Coje los datos por query filtrado
            List<Mantenimiento> mantenimientoFiltro = MantenimientoManagerFactory.get().filtradoPorDatePickerMantenimiento(new GenericType<List<Mantenimiento>>() {
            }, filtroString);

            // Convertir la lista de proveedores en ObservableList para la TableView
            ObservableList<Mantenimiento> mantenimientoDataFiltro = FXCollections.observableArrayList(mantenimientoFiltro);

            // Establecer los datos en la tabla
            tableView.setItems(mantenimientoDataFiltro);
        });

        // Le indicamos a cada Columna qué atributo tiene
        idMantenimientoColumn.setCellValueFactory(new PropertyValueFactory<>("idMantenimiento"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        mantenimientoExitosoColumn.setCellValueFactory(new PropertyValueFactory<>("mantenimientoExitoso"));
        idVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("idVehiculo"));
        fechaFinalizacionColumn.setCellValueFactory(new PropertyValueFactory<>("fechaFinalizacion"));

        // Configurar tabla como editable
        tableView.setEditable(true);

        // Configurar las columnas de la tabla para ser editables
        descripcionColumn.setCellFactory(column -> new EditingCellMantenimiento());
        mantenimientoExitosoColumn.setCellFactory(column -> new EditingCellMantenimiento());
        fechaFinalizacionColumn.setCellFactory(column -> new EditingCellMantenimiento());
        idVehiculoColumn.setCellFactory(column -> new EditingCellMantenimiento<>());

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
            mantenimientoVacio = new Mantenimiento();
            mantenimientoVacio.setIdMantenimiento(siguienteId); // Carga el siguiente id
            mantenimientoVacio.setDescripcion("Introduzca aqui la descripcion del mantenimiento");
            mantenimientoVacio.setMantenimientoExitoso(false);  // Valor predeterminado para mantenimientoExitoso
            mantenimientoVacio.setFechaFinalizacion(new java.util.Date());
            mantenimientoVacio.setIdVehiculo(0L);

            // Añadir el nuevo objeto a la lista de la tabla
            tableView.getItems().add(mantenimientoVacio);

            // Actualizar la vista de la tabla
            tableView.refresh();
        } catch (Exception e) {
            // Manejo de cualquier excepción, como por ejemplo problemas en la adición de datos
            new Alert(Alert.AlertType.ERROR, "Error al crear fila");
        }
    }

    private void guardarMantenimiento(ActionEvent event) {
        if (mantenimientoVacio != null) {
            try {
                // Llamada al servicio REST para persistir el nuevo mantenimiento
                MantenimientoManagerFactory.get().create_XML(mantenimientoVacio);

                cargarDatosTabla(null);

                // Mostrar un mensaje de éxito
                new Alert(Alert.AlertType.INFORMATION, "Mantenimiento guardado con éxito.").showAndWait();

                // Limpiar la referencia para evitar conflictos
                mantenimientoVacio = null;
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error al guardar el mantenimiento.").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No hay un mantenimiento nuevo para guardar.").showAndWait();
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

// Metodo que crea el informe
    private void crearInforme(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport("src/informes/InformeMantenimiento.jrxml");

            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Mantenimiento>) this.tableView.getItems());

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
