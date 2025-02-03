/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import logica.CompraManagerFactory;
import logica.SessionManager;
import modelo.Compra;
import modelo.Usuario;
import modelo.Vehiculo;

/**
 *
 * @author urkiz
 */
public class TusVehiculosController implements Initializable {

    // Elementos de la Ventana
    @FXML
    private Button homeBtn;

    @FXML
    private Button tusVehiculosBtn;

    @FXML
    private Button cerrarSesionBtn;

    @FXML
    private GridPane gridPane;

    // Metodo Initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Se añaden los listeners a todos los botones.
        tusVehiculosBtn.setOnAction(this::abrirVentanaTusVehiculos);
        homeBtn.setOnAction(this::irAtras);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);

        generarBotones();

        System.out.println("Ventana inicializada correctamente.");
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

    // Abrir perfil mediante ImageView
    @FXML
    private void abrirPerfilBtn(javafx.scene.input.MouseEvent event) {
        try {
            // Se carga el FXML con la información de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Perfil.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            PerfilController controller = loader.getController();

            // Obtener el Stage
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Perfil de Usuario");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/Perfil.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TablaProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Boton HOME para volver atras
    private void irAtras(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipal.fxml"));
            Parent root = loader.load();

            ScrollPane sc = new ScrollPane();
            sc.setContent(root);

            sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Navegación Principal");

            Scene scene = new Scene(sc);
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

            // Establecer el tamaño de la ventana
            stage.setWidth(1000);  // Establecer el ancho
            stage.setHeight(800);  // Establecer la altura

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Abrir Ventana Solicitar Mantenimiento
    private void abrirVentanaTusVehiculos(ActionEvent event) {

        try {
            // Se carga el FXML con la información de la vista viewSignUp.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SolicitarMantenimiento.fxml"));
            Parent root = loader.load();

            TusVehiculosController controler = loader.getController();

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
            // Si salta una IOException significa que ha habido algún 
            // problema al cargar el FXML o al intentar llamar a la nueva 
            // ventana, por lo que se mostrará un Alert con el mensaje 
            // "Error en la sincronización de ventanas, intentalo más tarde".
            Logger.getLogger(TusVehiculosController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Metodo que genera botones
    private void generarBotones() {
        // Obtener la lista de vehículos desde la base de datos
        List<Compra> compras = CompraManagerFactory.get().findAll_XML(new GenericType<List<Compra>>() {
        });

        int fila = 0;
        int columna = 0;
        Usuario posibleUsuario = SessionManager.getUsuario();

        for (Compra compra : compras) {

            if (compra.getUsuario().getIdPersona().equals(posibleUsuario.getIdPersona())) {

                Vehiculo vehiculoDeUser = compra.getVehiculo();
                String matriulaVehiculo = compra.getMatricula();

                String rutaCoche = vehiculoDeUser.getRuta();
                // Usar getClass().getResource() para acceder a la imagen desde el classpath
                Image image = new Image(getClass().getResource(rutaCoche).toExternalForm());

                // Crear el nombre del vehículo
                String nombreVehiculo = vehiculoDeUser.getMarca() + " " + vehiculoDeUser.getModelo();

                // Crear el ImageView y ajustarlo al tamaño de la ventana
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(200);  // Ajustamos el tamaño de la imagen (más grande)
                imageView.setFitWidth(200);   // Ajustamos el tamaño de la imagen (más grande)
                imageView.setPreserveRatio(true);  // Preservamos la relación de aspecto de la imagen

                // Crear el Label para el nombre del vehículo y ponerlo en color blanco
                Label nombreLabel = new Label(nombreVehiculo);
                nombreLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");  // Establecer el color del texto a blanco y tamaño de fuente

                // Crear un VBox que contenga la imagen y el nombre
                VBox vbox = new VBox(5);  // Espacio de 5px entre la imagen y el texto
                vbox.getChildren().addAll(imageView, nombreLabel);

                // Ajustar el tamaño máximo del VBox para que se ajuste a la ventana
                vbox.setMaxWidth(200);   // Limitar el ancho máximo del VBox
                vbox.setMaxHeight(300);  // Limitar el alto máximo del VBox

                // Crear el botón y asignar el VBox como contenido gráfico
                Button button = new Button();
                button.setGraphic(vbox);

                // Asegurarse de que el botón se ajuste bien dentro del GridPane
                button.setMaxWidth(Double.MAX_VALUE);  // Hacer que el botón ocupe todo el espacio disponible en su celda
                button.setMaxHeight(Double.MAX_VALUE); // Hacer que el botón ocupe todo el espacio disponible en su celda

                // Agregar el listener de clic al botón
                button.setOnAction(event -> abrirVentanaInformacionVehiculo(null, vehiculoDeUser));

                // Añadir el botón al GridPane en la fila y columna correspondiente
                gridPane.add(button, columna, fila);

                // Actualizar fila y columna para el siguiente botón
                columna++;
                if (columna == 3) {  // Después de 3 botones, pasamos a la siguiente fila
                    columna = 0;
                    fila++;
                }
            }
            System.out.println("HAY POBRE DE TI");
        }
    }

    private void abrirVentanaInformacionVehiculo(Object object, Vehiculo vehiculo) {

    }

}
