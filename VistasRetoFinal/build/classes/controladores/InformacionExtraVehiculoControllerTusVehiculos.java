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
 * Controlador para la ventana de información extra de un vehículo. Esta clase
 * se encarga de mostrar detalles adicionales de un vehículo, incluyendo su
 * marca, modelo, matrícula, color, potencia, kilometraje, precio, tipo de
 * vehículo y su imagen. Además, maneja eventos relacionados con la navegación
 * en la aplicación y el cierre de sesión.
 *
 * Utiliza una variable estática `matriculaPoner` para establecer la matrícula
 * del vehículo. La clase también maneja la carga de datos del vehículo desde un
 * gestor de información y la actualización de los elementos de la interfaz
 * gráfica correspondientes.
 */
public class InformacionExtraVehiculoControllerTusVehiculos implements Initializable {

    // Variable estática para almacenar la matrícula del vehículo
    private static String matriculaPoner;

    /**
     * Establece la matrícula a mostrar en la ventana de información del
     * vehículo.
     *
     * @param matricula La matrícula del vehículo que se quiere mostrar.
     */
    public static void setMatricula(String matricula) {
        InformacionExtraVehiculoControllerTusVehiculos.matriculaPoner = matricula;  // Acceder a la variable estática
    }

    // Elementos de la Ventana
    @FXML
    private Label marcaLabel;

    @FXML
    private Label modeloLabel;

    @FXML
    private Label matriculaLabel;

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
    private Button tusVehiculosBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private ImageView imageView;

    // Atributo
    private Vehiculo vehiculo;

    /**
     * Inicializa el controlador y establece los listeners para los botones.
     * Además, recoge la información del vehículo desde el gestor y carga los
     * datos correspondientes en los elementos de la interfaz.
     *
     * @param location La ubicación donde se cargó el archivo FXML.
     * @param resources El conjunto de recursos asociados al FXML.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Se añaden los listeners a todos los botones.
        homeBtn.setOnAction(this::irAtras);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        tusVehiculosBtn.setOnAction(this::abrirVentanaTusVehiculos);

        // Recogemos el Vehiculo y sacamos toda su info
        this.vehiculo = VehiculoInfoExtraManager.getVehiculo();

        // Cargar los datos del Vehiculo
        marcaLabel.setText(vehiculo.getMarca());
        modeloLabel.setText(vehiculo.getModelo());
        colorLabel.setText(vehiculo.getColor());
        potenciaLabel.setText(String.valueOf(vehiculo.getPotencia()));
        kmLabel.setText(String.valueOf(vehiculo.getKm()));
        precioLabel.setText(String.valueOf(vehiculo.getPrecio()));
        tipoVehiculoLabel.setText(vehiculo.getTipoVehiculo().toString());
        matriculaLabel.setText(matriculaPoner);

        // Cargar la imagen
        String rutaImagen = vehiculo.getRuta(); // Obtener la ruta de la imagen desde el vehiculo

        if (rutaImagen == null || rutaImagen.isEmpty()) {
            rutaImagen = "/img/sinImagen.jpg";
        }

        // Usar getClass().getResource() para acceder a la imagen desde el classpath
        Image image = new Image(getClass().getResource(rutaImagen).toExternalForm());
        imageView.setImage(image);
    }

    /**
     * Abre una ventana de inicio de sesión y registro (SignIn & SignUp).
     * Muestra un diálogo de confirmación preguntando al usuario si está seguro
     * de cerrar sesión. Si el usuario confirma, se carga la vista de inicio de
     * sesión y registro.
     *
     * @param event El evento de acción disparado por el botón de cerrar sesión.
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

                    // Establecer las propiedades de tamaño
                    stage.setWidth(1000);  // Establece el ancho
                    stage.setHeight(800);  // Establece la altura

                    // Se muestra en la ventana el Scene creado.
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    // Si salta una IOException significa que ha habido algún 
                    // problema al cargar el FXML o al intentar llamar a la nueva 
                    // ventana, por lo que se mostrará un Alert con el mensaje 
                    // "Error en la sincronización de ventanas, inténtalo más tarde".
                    Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
                    new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
                }
                // Aquí puedes agregar el código necesario para cerrar la sesión
            } else {
                // Lógica si el usuario cancela
                System.out.println("Cancelado, no se cierra la sesión.");
            }
        });
    }

    /**
     * Abre la ventana del perfil del usuario. Carga el FXML correspondiente a
     * la vista de perfil y muestra los detalles del usuario.
     *
     * @param event El evento de acción disparado al hacer clic en el ImageView
     * del perfil.
     */
    @FXML
    private void abrirPerfilBtn(javafx.scene.input.MouseEvent event) {
        try {
            // Se carga el FXML con la información de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Perfil.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            PerfilController controller = loader.getController();

            //controller.setUsuario((Usuario) usuario);
            // Obtener el Stage
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Perfil de Usuario");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/Perfil.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TablaProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Abre la ventana para solicitar mantenimiento (TusVehiculos). Carga el
     * FXML correspondiente a la vista de tus vehículos y muestra la información
     * de la solicitud de mantenimiento.
     *
     * @param event El evento de acción disparado por el botón de solicitar
     * mantenimiento.
     */
    private void abrirVentanaTusVehiculos(ActionEvent event) {
        try {
            // Se carga el FXML con la información de la vista TusVehiculos.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TusVehiculos.fxml"));
            Parent root = loader.load();

            TusVehiculosController controller = loader.getController();

            // Obtener el Stage desde el nodo que disparó el evento.
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Solicitar Mantenimiento");
            // Se crea un nuevo objeto de la clase Scene con el FXML cargado.
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

            // Se muestra en la ventana el Scene creado.
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Si ocurre una IOException, significa que ha habido un problema al cargar el FXML o cambiar de vista.
            // En tal caso, se muestra un Alert con el mensaje "Error en la sincronización de ventanas, inténtalo más tarde".
            Logger.getLogger(TusVehiculosController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Vuelve a la ventana de navegación principal (NavegacionPrincipal). Carga
     * el FXML correspondiente y muestra la vista principal en un ScrollPane.
     *
     * @param event El evento de acción disparado por el botón de volver atrás.
     */
    private void irAtras(ActionEvent event) {
        try {
            // Cargar el FXML de la vista NavegacionPrincipal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipal.fxml"));
            Parent root = loader.load();

            // Crear un ScrollPane para envolver el contenido
            ScrollPane sc = new ScrollPane();
            sc.setContent(root);

            // Configurar el ScrollPane para permitir solo desplazamiento vertical
            sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Desactiva la barra de desplazamiento horizontal
            sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Activa la barra de desplazamiento vertical

            // Configurar el Stage
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Navegación Principal");

            // Crear la nueva escena con el ScrollPane
            Scene scene = new Scene(sc);
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

            // Establecer la escena y mostrarla
            stage.setScene(scene);

            // Establecer las propiedades de tamaño
            stage.setWidth(1000);  // Establece el ancho
            stage.setHeight(800);  // Establece la altura

            stage.show();

        } catch (IOException ex) {
            // Si ocurre una IOException, significa que ha habido un problema al cargar el FXML o cambiar de vista.
            // Se muestra un Alert con el mensaje "Error en la sincronización de ventanas, inténtalo más tarde".
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }
}
