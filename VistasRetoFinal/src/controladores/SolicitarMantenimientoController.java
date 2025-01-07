/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;

/**
 *
 * @author urkiz
 */
public class SolicitarMantenimientoController implements Initializable {
// Elementos de la Ventana
    @FXML
    private Button homeBtn;
    
    @FXML
    private Button solicitarMantenimientoBtn;
    
    @FXML
    private Button cerrarSesionBtn;
    
    @FXML
    private MenuItem gestionVehiculos;

    @FXML
    private MenuItem gestionProveedores;

    @FXML
    private MenuItem gestionMantenimientos;

    
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //Se añaden los listeners a todos los botones.
        solicitarMantenimientoBtn.setOnAction(this::abrirVentanaSolicitarMantenimiento);
        homeBtn.setOnAction(this::irAtras);
        
        gestionVehiculos.setOnAction(this::abrirVentanaGestionVehiculos);
        gestionProveedores.setOnAction(this::abrirVentanaGestionProveedores);
        gestionMantenimientos.setOnAction(this::abrirVentanaGestionMantenimientos);
        
        
        System.out.println("Ventana inicializada correctamente.");
    }
    
    // Boton HOME para volver atras
    private void irAtras(ActionEvent event) {
        try {
            // Se carga el FXML con la información de la vista viewSignUp.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipal.fxml"));
            Parent root = loader.load();

            NavegacionPrincipalController controler = loader.getController();

            // Obtener el Stage desde el nodo que disparó el evento.
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Navegacion Principal");
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
            Logger.getLogger(NavegacionPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
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

    
    // Abrir Ventana Solicitar Mantenimiento
    private void abrirVentanaSolicitarMantenimiento(ActionEvent event) {
        
       try {
            // Se carga el FXML con la información de la vista viewSignUp.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SolicitarMantenimiento.fxml"));
            Parent root = loader.load();

            SolicitarMantenimientoController controler = loader.getController();
            
            

            // Obtener el Stage desde el nodo que disparó el evento.
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Solicitar Mantenimiento");
            // Se crea un nuevo objeto de la clase Scene con el FXML cargado.
            Scene scene = new Scene(root);

            // Se muestra en la ventana el Scene creado.
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Si salta una IOException significa que ha habido algún 
            // problema al cargar el FXML o al intentar llamar a la nueva 
            // ventana, por lo que se mostrará un Alert con el mensaje 
            // "Error en la sincronización de ventanas, intentalo más tarde".
            Logger.getLogger(SolicitarMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }
    
}
