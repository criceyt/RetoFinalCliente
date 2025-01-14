/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import modelo.Compra;
import modelo.Mantenimiento;
import modelo.Persona;
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

        System.out.println("Ventana inicializada correctamente.");

        // Configuración de las columnas
        if (idMantenimientoColumn == null || tableView == null) {
            System.err.println("Error: Columnas o tabla no inicializadas correctamente.");
            return;
        }

        idMantenimientoColumn.setCellValueFactory(new PropertyValueFactory<>("idMantenimiento"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        /*dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        matriculaColumn.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));*/
        mantenimientoExitosoColumn.setCellValueFactory(new PropertyValueFactory<>("mantenimientoExitoso"));
        fechaFinalizacionColumn.setCellValueFactory(new PropertyValueFactory<>("fechaFinalizacion"));
        idVehiculoColumn.setCellValueFactory(new PropertyValueFactory<>("vehiculo.idVehiculo"));

        // Cargar datos en la tabla
        List<Mantenimiento> mantenimientos = MantenimientoManagerFactory.get().findAll_XML(new GenericType<List<Mantenimiento>>() {});
        if (mantenimientos == null) {
            System.err.println("No se pudo obtener la lista de mantenimientos desde el servidor.");
            mantenimientos = new ArrayList<>();
        }

        ObservableList<Mantenimiento> mantenimientosData = FXCollections.observableArrayList(mantenimientos);
        tableView.setItems(mantenimientosData);
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
}
