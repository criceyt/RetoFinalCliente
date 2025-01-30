/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import static jxl.biff.BaseCellFeatures.logger;
import logica.ProveedorManagerFactory;
import logica.VehiculoManagerFactory;
import modelo.Proveedor;
import modelo.TipoVehiculo;
import modelo.Vehiculo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author crice
 */
public class TablaVehiculosController implements Initializable {

    // Elementos de la Ventana
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
    
    @FXML
    private TableView tableViewVehiculo;
    
    @FXML
    private TableColumn<Vehiculo, Long> idVehiculoColum;
    
    @FXML
    private TableColumn<Vehiculo, String> modeloColum;
    
    @FXML
    private TableColumn<Vehiculo, String> marcaColum;
    
    @FXML
    private TableColumn<Vehiculo, String> colorColum;
    
    @FXML
    private TableColumn<Vehiculo, Date> fechaAltaColum;
    
    @FXML
    private TableColumn<Vehiculo, Integer> potenciaColum;
    
    @FXML
    private TableColumn<Vehiculo, Integer> kmColum;
    
    @FXML
    private TableColumn<Vehiculo, Integer> precioColum;
    
    @FXML
    private TableColumn<Vehiculo, String> tipoColum;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private Button addRowButton;
    
    @FXML
    private Button printBtn;
    
    @FXML
    private DatePicker datePickerFiltro;

    // Declaraciones
    private Logger LOGGER = Logger.getLogger(TablaVehiculosController.class.getName());
    private DatePicker datePicker;

    // Metodo Initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Se añaden los listeners a todos los botones.
        homeBtn.setOnAction(this::irAtras);
        gestionVehiculos.setOnAction(this::abrirVentanaGestionVehiculos);
        gestionProveedores.setOnAction(this::abrirVentanaGestionProveedores);
        gestionMantenimientos.setOnAction(this::abrirVentanaGestionMantenimientos);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        deleteButton.setOnAction(this::borrarVehiculo);
        addRowButton.setOnAction(this::añadirLinea);
        refreshButton.setOnAction(this::cargarDatosTabla);
        printBtn.setOnAction(this::crearInforme);
        
        System.out.println("Ventana inicializada correctamente.");

        // Configuración de las columnas de la tabla.
        idVehiculoColum.setCellValueFactory(new PropertyValueFactory<>("idVehiculo"));
        marcaColum.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColum.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colorColum.setCellValueFactory(new PropertyValueFactory<>("color"));
        potenciaColum.setCellValueFactory(new PropertyValueFactory<>("potencia"));
        kmColum.setCellValueFactory(new PropertyValueFactory<>("km"));
        precioColum.setCellValueFactory(new PropertyValueFactory<>("precio"));
        fechaAltaColum.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        tipoColum.setCellValueFactory(new PropertyValueFactory<>("tipoVehiculo"));

        // Metodo que carga los datos de la Tabla
        cargarDatosTabla(null);

        // Filtrado de DatePicker
        datePickerFiltro.setOnAction(event -> {
            LocalDate filtro = datePickerFiltro.getValue();
            String filtroString = filtro.toString();

            // Limpia la tabla
            tableViewVehiculo.getItems().clear();

            // Coje los datos por query filtrado
            List<Vehiculo> vehiculofiltro = VehiculoManagerFactory.get().filtradoDatePickerVehiculo(new GenericType<List<Vehiculo>>() {
            }, filtroString);

            // Convertir la lista de proveedores en ObservableList para la TableView
            ObservableList<Vehiculo> vehiculosDataFiltro = FXCollections.observableArrayList(vehiculofiltro);

            // Establecer los datos en la tabla
            tableViewVehiculo.setItems(vehiculosDataFiltro);
        });

        // Configurar tabla como editable
        tableViewVehiculo.setEditable(true);

        // Configurar la columna de descripción para usar EditingCell
        marcaColum.setCellFactory(column -> new EditingCellVehiculo());
        modeloColum.setCellFactory(column -> new EditingCellVehiculo());
        colorColum.setCellFactory(column -> new EditingCellVehiculo());
        kmColum.setCellFactory(column -> new EditingCellVehiculo<>());
        potenciaColum.setCellFactory(column -> new EditingCellVehiculo());
        precioColum.setCellFactory(column -> new EditingCellVehiculo());
        tipoColum.setCellFactory(column -> new EditingCellVehiculo<>());
        fechaAltaColum.setCellFactory(column -> new EditingCellVehiculo<>());

        // Borrado
        deleteButton.setDisable(true);
        // Listener para habilitar o deshabilitar el botón de borrado según la selección
        tableViewVehiculo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
            Logger.getLogger(TablaProveedoresController.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TablaMantenimientoController.class
                    .getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
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
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(NavegacionPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Metodo que borra el Mantenimiento de la tabla y de la base de datos
    private void borrarVehiculo(ActionEvent event) {
        Vehiculo vehiculoSeleccionado = (Vehiculo) tableViewVehiculo.getSelectionModel().getSelectedItem();
        
        if (vehiculoSeleccionado != null) {
            // Crear la alerta de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de Borrado");
            alert.setHeaderText("¿Estás seguro de que deseas borrar este mantenimiento?");
            alert.setContentText("Esta acción no se puede deshacer.");

            // Mostrar la alerta y esperar la respuesta del usuario
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Si el usuario hace clic en "OK", borrar el mantenimiento
                    Long id = vehiculoSeleccionado.getIdVehiculo();
                    String idParseado = String.valueOf(id);

                    // Llamada al método para borrar el mantenimiento
                    VehiculoManagerFactory.get().remove(idParseado);

                    // Cargamos los datos de la Tabla
                    cargarDatosTabla(null);
                    
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

    // Metodo paar cargar los datos de la Tabla o para hacer un Refresh
    private void cargarDatosTabla(ActionEvent event) {

        // Liampia la tabla antes de introducir los Items
        tableViewVehiculo.getItems().clear();

        // Obtener la lista de proveedores desde el servidor o el origen de datos
        List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });

        // Convertir la lista de proveedores en ObservableList para la TableView
        ObservableList<Vehiculo> vehiculosData = FXCollections.observableArrayList(vehiculos);

        // Establecer los datos en la tabla
        tableViewVehiculo.setItems(vehiculosData);
    }

    // Metodo para Añadir un Vehiuculo Vacio
    public void añadirLinea(ActionEvent even) {
        
        try {
            
            Vehiculo vehiculoLinea = new Vehiculo();

            // La fecha se puede cambiar pero debe ser automatica
            Date fechaAuto = new Date();
            vehiculoLinea.setFechaAlta(fechaAuto);

            // String 
            vehiculoLinea.setMarca("Inserta la Marca");
            vehiculoLinea.setModelo("Inserta el Modelo");
            vehiculoLinea.setColor("Inserta el Color");

            // Integer
            vehiculoLinea.setKm(0);
            vehiculoLinea.setPotencia(0);
            vehiculoLinea.setPrecio(0);
            vehiculoLinea.setTipoVehiculo(TipoVehiculo.COCHE);

            // Enum
            vehiculoLinea.setTipoVehiculo(TipoVehiculo.COCHE);

            // La ruta de la imagen
            vehiculoLinea.setRuta(null);

            // Mnadar el Vehiculo
            VehiculoManagerFactory.get().create_XML(vehiculoLinea);

            // Cargamos la tabla de nuevo
            cargarDatosTabla(null);
            
        } catch (Exception e) {
            System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        }
    }

    // Metodo que crea el informe
    private void crearInforme(ActionEvent event) {
        
        try {
            
            JasperReport report = JasperCompileManager.compileReport("src/informes/InformeVehiculo.jrxml");
            
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Vehiculo>) this.tableViewVehiculo.getItems());
            
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
