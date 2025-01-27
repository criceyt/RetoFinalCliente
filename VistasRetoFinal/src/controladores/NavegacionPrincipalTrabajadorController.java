/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import logica.VehiculoManagerFactory;
import modelo.Vehiculo;

/**
 *
 * @author crice
 */
public class NavegacionPrincipalTrabajadorController implements Initializable {

    // Elementos de la Ventana
    @FXML
    private GridPane gridPane;

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
    private Button abrirInfoVehiculoConcreto;

    @FXML
    public void mostrarFiltroKilometraje(MouseEvent event) {
        mostrarPopup(event.getSource(), crearRangoInput());
    }

    @FXML
    public void mostrarFiltroColor(MouseEvent event) {
        mostrarPopup(event.getSource(), crearComboBoxInput("Seleccione un color", "Rojo", "Azul", "Negro", "Blanco"));
    }

    @FXML
    public void mostrarFiltroPrecio(MouseEvent event) {
        mostrarPopup(event.getSource(), crearRangoInput());
    }

    @FXML
    public void mostrarFiltroMarca(MouseEvent event) {
        mostrarPopup(event.getSource(), crearComboBoxInput("Seleccione una marca", "Toyota", "Ford", "Honda", "BMW"));
    }

    @FXML
    public void mostrarFiltroModelo(MouseEvent event) {
        mostrarPopup(event.getSource(), crearComboBoxInput("Seleccione un modelo", "Modelo A", "Modelo B", "Modelo C", "Modelo D"));
    }

    // Declaracion del Popup
    private Popup popup;

    // Metodo Initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Se añaden los listeners a todos los botones.
        gestionVehiculos.setOnAction(this::abrirVentanaGestionVehiculos);
        gestionProveedores.setOnAction(this::abrirVentanaGestionProveedores);
        gestionMantenimientos.setOnAction(this::abrirVentanaGestionMantenimientos);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        //abrirInfoVehiculoConcreto.setOnAction(this::abrirVentanaInformacionVehiculo);

        generarBotones();

        System.out.println("Ventana inicializada correctamente.");
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

    // Metodo que abre la venatan de Informacion de Vehiculo
    private void abrirVentanaInformacionVehiculo(ActionEvent event) {
        try {
            // Se carga el FXML con la información de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/InformacionExtraVehiculo.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            InformacionExtraVehiculoController controller = loader.getController();

            // Obtener el Stage
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Informacion de Vehiculos");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(NavegacionPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    private void mostrarPopup(Object source, VBox contenido) {
        if (popup != null && popup.isShowing()) {
            popup.hide(); // Ocultar el popup anterior si está visible
        }

        popup = new Popup();
        popup.getContent().add(contenido);
        popup.setAutoHide(true); // Cerrar automáticamente al hacer clic fuera del popup

        Node node = (Node) source;
        Bounds bounds = node.localToScreen(node.getBoundsInLocal()); // Obtener la posición del botón
        popup.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar debajo del botón
    }

    private VBox crearRangoInput() {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Campo de texto desde
        TextField desde = new TextField();
        desde.setPromptText("Desde...");
        desde.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Campo de texto hasta
        TextField hasta = new TextField();
        hasta.setPromptText("Hasta...");
        hasta.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        Label label = new Label("Rango:");
        label.setStyle("-fx-text-fill: white;");

        vbox.getChildren().addAll(label, desde, hasta);
        return vbox;
    }

    private VBox crearComboBoxInput(String labelText, String... opciones) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // ComboBox
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(opciones);
        comboBox.setPromptText(labelText);
        comboBox.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: white;");

        vbox.getChildren().addAll(label, comboBox);
        return vbox;
    }

    private void generarBotones() {
        // Obtener la lista de vehículos desde la base de datos
        List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });

        int fila = 0;
        int columna = 0;

        for (Vehiculo vehiculo : vehiculos) {
            String rutaCoche = vehiculo.getRuta();

            // Crear el botón
            Button button = new Button();

            // Crear la imagen para el botón
            ImageView imageView = new ImageView(new Image("file:" + rutaCoche)); // Usamos "file:" para cargar imágenes desde el sistema de archivos
            imageView.setFitHeight(150);
            imageView.setFitWidth(200);
            button.setGraphic(imageView);

            // Añadir el botón al GridPane en la fila y columna correspondiente
            gridPane.add(button, columna, fila);

            // Actualizar fila y columna para el siguiente botón
            columna++;
            if (columna == 3) {  // Después de 3 botones, pasamos a la siguiente fila
                columna = 0;
                fila++;
            }
        }
    }
}
