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
import java.time.LocalDateTime;
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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.ws.rs.core.GenericType;
import logica.ProveedorManager;
import modelo.Proveedor;
import modelo.TipoVehiculo;
import modelo.Vehiculo;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import sun.font.TextLabel;

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
    private TableColumn<Proveedor, String> tipoColumn;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Se añaden los listeners a todos los botones.
        homeBtn.setOnAction(this::irAtras);
        gestionVehiculos.setOnAction(this::abrirVentanaGestionVehiculos);
        gestionProveedores.setOnAction(this::abrirVentanaGestionProveedores);
        gestionMantenimientos.setOnAction(this::abrirVentanaGestionMantenimientos);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        saveBtn.setOnAction(this::proveedorNuevo);
        refreshButton.setOnAction(this::cargartDatosTabla);
        deleteButton.setOnAction(this::borrarProveedor);

        System.out.println("Ventana inicializada correctamente.");

        // Configuración de las columnas de la tabla.
        idProveedorColumn.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoVehiculo"));
        especialidadColumn.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        ultimaActividadColumn.setCellValueFactory(new PropertyValueFactory<>("ultimaActividad"));

        // Obtener la lista de proveedores desde el servidor o el origen de datos
        List<Proveedor> proveedores = ProveedorManagerFactory.get().findAll_XML(new GenericType<List<Proveedor>>() {
        });

        // Convertir la lista de proveedores en ObservableList para la TableView
        ObservableList<Proveedor> proveedoresData = FXCollections.observableArrayList(proveedores);

        // Establecer los datos en la tabla
        tableView.setItems(proveedoresData);

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

    private void proveedorNuevo(ActionEvent event) {

        String nombre = NombreField.getText();
        String especilalidad = especialidadField.getText();
        String TipoVehiculoString = categoryComboBox.getValue();

        TipoVehiculo valor;

        if (TipoVehiculoString.equals("MOTO")) {
            valor = TipoVehiculo.MOTO;
        } else if (TipoVehiculoString.equals("CAMION")) {
            valor = TipoVehiculo.CAMION;
        } else {
            valor = TipoVehiculo.COCHE;
        }

        Proveedor proveedorNuevo = new Proveedor();
        proveedorNuevo.setNombreProveedor(nombre);
        proveedorNuevo.setEspecialidad(especilalidad);
        proveedorNuevo.setTipoVehiculo(valor);

        Date fechaHoy = new Date();
        fechaHoy.getDate();
        proveedorNuevo.setUltimaActividad(fechaHoy);

        try {

            ProveedorManagerFactory.get().create_XML(proveedorNuevo);

        } catch (Exception ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        tableView.refresh();

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
            // Se carga el FXML con la información de la vista viewSignUp.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipalTrabajador.fxml"));
            Parent root = loader.load();

            NavegacionPrincipalTrabajadorController controler = loader.getController();

            // Obtener el Stage desde el nodo que disparó el evento.
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Navegacion Principal Trabajador");
            // Se crea un nuevo objeto de la clase Scene con el FXML cargado.
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());

            // Se muestra en la ventana el Scene creado.
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Si salta una IOException significa que ha habido algún 
            // problema al cargar el FXML o al intentar llamar a la nueva 
            // ventana, por lo que se mostrará un Alert con el mensaje 
            // "Error en la sincronización de ventanas, intentalo más tarde".
            Logger.getLogger(NavegacionPrincipalTrabajadorController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
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
            // Aquí puedes llamar a otro método con el objeto seleccionado como parámetro
            Long id = proveedorSeleccionado.getIdProveedor();
            String idParseado = String.valueOf(id);
            
            ProveedorManagerFactory.get().remove(idParseado);
            
            tableView.refresh();
        
            
        } else {
            // Si no hay un elemento seleccionado, mostrar mensaje de advertencia o manejar el error
            System.out.println("No se ha seleccionado un proveedor para borrar.");
        }
    }
}
