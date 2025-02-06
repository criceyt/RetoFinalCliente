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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.Vehiculo;

/**
 *
 * @author 2dam
 */
/**
 * Controlador de la vista que muestra la información extra de un vehículo. Esta
 * clase es responsable de inicializar los elementos de la interfaz y gestionar
 * las acciones del usuario.
 *
 * @implements Initializable
 */
public class InformacionExtraVehiculoControllerTrabajador implements Initializable {

    // Elementos de la Ventana (componentes gráficos)
    @FXML
    private Label marcaLabel;

    @FXML
    private Label modeloLabel;

    @FXML
    private Label colorLabel;

    @FXML
    private Label potenciaLabel;

    @FXML
    private Label kmLabel;

    @FXML
    private Label precioLabel;

    @FXML
    private Label tipoVehiculoLabel;

    @FXML
    private Button cerrarSesionBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private MenuItem gestionVehiculos;

    @FXML
    private MenuItem gestionProveedores;

    @FXML
    private MenuItem gestionMantenimientos;

    @FXML
    private ImageView imageView;

    // Atributo que almacena el objeto Vehiculo
    private Vehiculo vehiculo;

    /**
     * Método que se llama automáticamente cuando la vista es inicializada.
     * Configura los listeners de los botones y carga los datos del vehículo
     * para mostrarlos en la interfaz.
     *
     * @param location La ubicación relativa de la raíz del archivo FXML.
     * @param resources El conjunto de recursos para esta vista.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Se añaden los listeners a todos los botones.
        homeBtn.setOnAction(this::irAtras);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        gestionVehiculos.setOnAction(this::abrirVentanaGestionVehiculos);
        gestionProveedores.setOnAction(this::abrirVentanaGestionProveedores);
        gestionMantenimientos.setOnAction(this::abrirVentanaGestionMantenimientos);

        // Recogemos el Vehiculo y sacamos toda su información.
        this.vehiculo = VehiculoInfoExtraManager.getVehiculo();

        // Cargar los datos del Vehículo en las etiquetas correspondientes
        marcaLabel.setText(vehiculo.getMarca());
        modeloLabel.setText(vehiculo.getModelo());
        colorLabel.setText(vehiculo.getColor());
        potenciaLabel.setText(String.valueOf(vehiculo.getPotencia()));
        kmLabel.setText(String.valueOf(vehiculo.getKm()));
        precioLabel.setText(String.valueOf(vehiculo.getPrecio()));
        tipoVehiculoLabel.setText(vehiculo.getTipoVehiculo().toString());

        // Cargar la imagen del vehículo
        String rutaImagen = vehiculo.getRuta(); // Obtener la ruta de la imagen desde el vehículo

        // Si no hay imagen asociada, asignar una imagen predeterminada
        if (rutaImagen == null || rutaImagen.isEmpty()) {
            rutaImagen = "/img/sinImagen.jpg";
        }

        // Usar getClass().getResource() para acceder a la imagen desde el classpath
        Image image = new Image(getClass().getResource(rutaImagen).toExternalForm());
        imageView.setImage(image);
    }

    /**
     * Método que se activa cuando el usuario hace clic en el botón "Cerrar
     * sesión". Muestra un mensaje de confirmación antes de proceder con el
     * cierre de sesión.
     *
     * @param event El evento generado por la acción del usuario.
     */
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
                    // Se carga el FXML con la información de la vista SignInSignUp
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignInSignUp.fxml"));
                    Parent root = loader.load();

                    SignController controler = loader.getController();

                    // Obtener el Stage desde el nodo que disparó el evento
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                    stage.setTitle("SignIn & SignUp");
                    // Se crea un nuevo objeto de la clase Scene con el FXML cargado
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/stylesOscuro.css").toExternalForm());

                    // Establecer las propiedades de tamaño de la ventana
                    stage.setWidth(1000);  // Establecer el ancho
                    stage.setHeight(800);  // Establecer la altura

                    // Se muestra la nueva escena en la ventana
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    // Si ocurre una IOException, se muestra un mensaje de error
                    Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
                    new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
                }
            } else {
                // Lógica si el usuario cancela la operación
                System.out.println("Cancelado, no se cierra la sesión.");
            }
        });
    }

    /**
     * Método que maneja el evento de hacer clic en el botón "Home" para volver
     * atrás. Carga la vista principal de navegación del trabajador, utilizando
     * un ScrollPane para permitir el desplazamiento vertical. El método
     * configura el escenario y la escena, establece el estilo CSS y define las
     * propiedades de tamaño de la ventana. Si ocurre un error al cargar la
     * vista, se muestra un mensaje de error.
     *
     * @param event El evento generado por la acción del usuario al hacer clic
     * en el botón "Home".
     */
    private void irAtras(ActionEvent event) {
        try {
            // Cargar el FXML de la vista principal de navegación del trabajador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipalTrabajador.fxml"));
            Parent root = loader.load();

            // Crear un ScrollPane para envolver el contenido
            ScrollPane sc = new ScrollPane();
            sc.setContent(root);

            // Configurar el ScrollPane para permitir solo desplazamiento vertical
            sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Desactiva la barra de desplazamiento horizontal
            sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Activa la barra de desplazamiento vertical

            // Obtener el Stage actual y establecer la nueva escena
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Navegación Principal Trabajador");

            // Crear la nueva escena con el ScrollPane
            Scene scene = new Scene(sc);
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

            // Establecer las propiedades de tamaño
            stage.setWidth(1000);  // Establece el ancho
            stage.setHeight(800);  // Establece la altura

            // Establecer la nueva escena en el stage y mostrarla
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Método que maneja el evento de abrir la ventana de gestión de vehículos.
     * Carga la vista correspondiente a la tabla de vehículos, asigna un título
     * a la ventana, configura la escena con el estilo CSS correspondiente y
     * establece las propiedades de tamaño de la ventana. Si ocurre un error al
     * cargar la vista, se muestra un mensaje de error.
     *
     * @param event El evento generado por la acción del usuario al hacer clic
     * en el botón correspondiente.
     */
    private void abrirVentanaGestionVehiculos(ActionEvent event) {
        try {
            // Cargar el FXML de la vista de gestión de vehículos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TablaVehiculos.fxml"));
            Parent root = loader.load();

            // Obtener el Stage actual y establecer la nueva escena
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Gestión de Vehículos");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());

            // Establecer las propiedades de tamaño
            stage.setWidth(1000);  // Establece el ancho
            stage.setHeight(800);  // Establece la altura

            // Establecer la nueva escena en el stage y mostrarla
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Método que maneja el evento de abrir la ventana de gestión de
     * proveedores. Carga la vista correspondiente a la tabla de proveedores,
     * asigna un título a la ventana, configura la escena con el estilo CSS
     * correspondiente y establece las propiedades de tamaño de la ventana. Si
     * ocurre un error al cargar la vista, se muestra un mensaje de error.
     *
     * @param event El evento generado por la acción del usuario al hacer clic
     * en el botón correspondiente.
     */
    private void abrirVentanaGestionProveedores(ActionEvent event) {
        try {
            // Cargar el FXML de la vista de gestión de proveedores
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TablaProveedores.fxml"));
            Parent root = loader.load();

            // Obtener el Stage actual y establecer la nueva escena
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Gestión de Proveedores");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());

            // Establecer las propiedades de tamaño
            stage.setWidth(1000);  // Establece el ancho
            stage.setHeight(800);  // Establece la altura

            // Establecer la nueva escena en el stage y mostrarla
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Método que maneja el evento de abrir la ventana de gestión de
     * mantenimientos. Carga la vista correspondiente a la tabla de
     * mantenimientos, asigna un título a la ventana, configura la escena con el
     * estilo CSS correspondiente y establece las propiedades de tamaño de la
     * ventana. Si ocurre un error al cargar la vista, se muestra un mensaje de
     * error.
     *
     * @param event El evento generado por la acción del usuario al hacer clic
     * en el botón correspondiente.
     */
    private void abrirVentanaGestionMantenimientos(ActionEvent event) {
        try {
            // Cargar el FXML de la vista de gestión de mantenimientos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TablaMantenimiento.fxml"));
            Parent root = loader.load();

            // Obtener el Stage actual y establecer la nueva escena
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Gestión de Mantenimientos");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());

            // Establecer las propiedades de tamaño
            stage.setWidth(1000);  // Establece el ancho
            stage.setHeight(800);  // Establece la altura

            // Establecer la nueva escena en el stage y mostrarla
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }
}
