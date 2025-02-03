/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import logica.CompraManagerFactory;
import logica.SessionManager;
import modelo.Compra;
import modelo.CompraId;
import modelo.Usuario;
import modelo.Vehiculo;

/**
 *
 * @author 2dam
 */
public class InformacionExtraVehiculoController implements Initializable {

    // Elementos de la Ventana
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
    private Button comprarBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button tusVehiculosBtn;

    @FXML
    private ImageView imageView;

    // Atributo
    private Vehiculo vehiculo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Se añaden los listeners a todos los botones.
        homeBtn.setOnAction(this::irAtras);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        comprarBtn.setOnAction(this::comprarVehiculo);
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

        // Cargar la imagen
        String rutaImagen = vehiculo.getRuta(); // Obtener la ruta de la imagen desde el vehiculo

        if (rutaImagen == null || rutaImagen.isEmpty()) {
            rutaImagen = "/img/sinImagen.jpg";
        }

        // Usar getClass().getResource() para acceder a la imagen desde el classpath
        Image image = new Image(getClass().getResource(rutaImagen).toExternalForm());
        imageView.setImage(image);

    }

    @FXML
    private void abrirPerfilBtn(javafx.scene.input.MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Perfil.fxml"));
            Parent root = loader.load();

            PerfilController controller = loader.getController();

            // Obtener el Stage
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Perfil de Usuario");

            // Crear la nueva escena y establecer el tamaño
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/Perfil.css").toExternalForm());

            stage.setWidth(1000);  // Establecer el ancho
            stage.setHeight(800);  // Establecer la altura

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TablaProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    private void abrirVentanaSignInSignUp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Estás seguro de que deseas cerrar sesión?");
        alert.setContentText("Perderás cualquier cambio no guardado.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignInSignUp.fxml"));
                    Parent root = loader.load();

                    SignController controler = loader.getController();

                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("SignIn & SignUp");

                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/stylesOscuro.css").toExternalForm());

                    // Establecer el tamaño de la ventana
                    stage.setWidth(1000);  // Establecer el ancho
                    stage.setHeight(800);  // Establecer la altura

                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
                    new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
                }
            } else {
                System.out.println("Cancelado, no se cierra la sesión.");
            }
        });
    }

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TusVehiculos.fxml"));
            Parent root = loader.load();

            TusVehiculosController controler = loader.getController();

            // Obtener el Stage desde el nodo que disparó el evento.
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Tus Vehiculos");
            // Se crea un nuevo objeto de la clase Scene con el FXML cargado.
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());
            
            // Establecer el tamaño de la ventana
            stage.setWidth(1000);  // Establecer el ancho
            stage.setHeight(800);  // Establecer la altura

            // Se muestra en la ventana el Scene creado.
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            // Si salta una IOException significa que ha habido algún 
            // problema al cargar el FXML o al intentar llamar a la nueva 
            // ventana, por lo que se mostrará un Alert con el mensaje 
            // "Error en la sincronización de ventanas, intentalo más tarde".
            Logger.getLogger(TusVehiculosController.class
                    .getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Metodo que se Activa cuando se le da a Comprar (Button)
    private void comprarVehiculo(ActionEvent event) {

        boolean compradoYa = false;
        // Crear un nuevo objeto Compra
        Compra compra = new Compra();

        // Obtener el usuario y el vehículo
        Usuario usuario = SessionManager.getUsuario();
        Vehiculo vehiculo = VehiculoInfoExtraManager.getVehiculo();

        // Crear y establecer el id compuesto de Compra (CompraId)
        CompraId compraId = new CompraId();
        compraId.setIdPersona(usuario.getIdPersona());
        compraId.setIdVehiculo(vehiculo.getIdVehiculo());
        compra.setIdCompra(compraId);  // Establecer la clave primaria compuesta

        // Generar la matrícula y establecerla
        String matriculaNueva = generarMatricula();
        compra.setMatricula(matriculaNueva);

        // Establecer la fecha de compra
        Date date = new Date();
        compra.setFechaCompra(date);

        // Ahora la valdadera vuelta
        List<Compra> compras = CompraManagerFactory.get().findAll_XML(new GenericType<List<Compra>>() {
        });

        for (Compra c : compras) {
            if (c.getIdCompra().getIdPersona().equals(usuario.getIdPersona()) && c.getIdCompra().getIdVehiculo().equals(vehiculo.getIdVehiculo())) {
                System.out.println("Vehiculo Comprado");
                compradoYa = true;
            }

        }

        if (!compradoYa) {
            // Mostrar una alerta de confirmación para asegurarse de que el usuario quiere comprar el coche
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Estás seguro de que deseas comprar este vehículo?");
            alert.setContentText("Una vez comprada, la compra no podrá ser revertida.");

            // Mostrar la alerta y esperar la respuesta
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si el usuario confirma la compra (presiona "OK")
                // Llamada al método de gestión de compras (presumiblemente para persistir la compra)
                CompraManagerFactory.get().create_XML(compra);

                // Se inserta el Usuario y el Vehiculo
                compraId.setIdPersona(usuario.getIdPersona());
                String parseado = String.valueOf(usuario.getIdPersona());

                compra.setUsuario(usuario);
                compra.setVehiculo(vehiculo);

                CompraManagerFactory.get().edit_XML(compra, parseado);
            } else {
                // Si el usuario cancela la operación, no hace nada (o puedes mostrar otro mensaje)
                System.out.println("Compra cancelada.");
            }
        } else {
            // Mostrar un mensaje de alerta si el coche ya fue comprado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);  // No tiene cabecera
            alert.setContentText("Este coche ya lo has comprado.");

            // Mostrar la alerta
            alert.showAndWait();
        }

    }

    // Generador de Matricula Aleatoria
    public static String generarMatricula() {
        Random rand = new Random();

        // Generar los 4 dígitos aleatorios
        int numeros = rand.nextInt(9000) + 1000;  // Asegura que siempre sean 4 dígitos (1000-9999)

        // Generar las 3 letras aleatorias
        char letra1 = (char) (rand.nextInt(26) + 'A'); // Letras entre A-Z
        char letra2 = (char) (rand.nextInt(26) + 'A');
        char letra3 = (char) (rand.nextInt(26) + 'A');

        // Devolver la matrícula en el formato "1234 ABC"
        return String.format("%d %c%c%c", numeros, letra1, letra2, letra3);
    }
}
