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
/**
 * Controlador que maneja la vista de información extra de un vehículo. Se
 * encarga de mostrar los detalles del vehículo, como marca, modelo, color,
 * potencia, precio, entre otros, así como gestionar las acciones de navegación
 * y compra.
 */
public class InformacionExtraVehiculoController implements Initializable {

    // Elementos de la ventana
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

    // Atributo para almacenar el vehículo mostrado
    private Vehiculo vehiculo;

    /**
     * Método que se ejecuta cuando la vista se inicializa. Se configura la
     * interfaz, incluyendo la carga de la información del vehículo y la
     * asignación de los listeners para los botones de la ventana.
     *
     * @param location URL de la vista FXML cargada.
     * @param resources Recursos adicionales relacionados con la vista FXML.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Se añaden los listeners a todos los botones.
        homeBtn.setOnAction(this::irAtras);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        comprarBtn.setOnAction(this::comprarVehiculo);
        tusVehiculosBtn.setOnAction(this::abrirVentanaTusVehiculos);

        // Recuperamos el Vehículo y mostramos toda su información.
        this.vehiculo = VehiculoInfoExtraManager.getVehiculo();

        // Cargar los datos del Vehículo en las etiquetas correspondientes
        marcaLabel.setText(vehiculo.getMarca());
        modeloLabel.setText(vehiculo.getModelo());
        colorLabel.setText(vehiculo.getColor());
        potenciaLabel.setText(String.valueOf(vehiculo.getPotencia()));
        kmLabel.setText(String.valueOf(vehiculo.getKm()));
        precioLabel.setText(String.valueOf(vehiculo.getPrecio()));
        tipoVehiculoLabel.setText(vehiculo.getTipoVehiculo().toString());

        // Cargar la imagen asociada al vehículo
        String rutaImagen = vehiculo.getRuta(); // Obtener la ruta de la imagen desde el vehículo

        if (rutaImagen == null || rutaImagen.isEmpty()) {
            rutaImagen = "/img/sinImagen.jpg";  // Ruta predeterminada si no se especifica ninguna
        }

        // Usamos getClass().getResource() para acceder a la imagen desde el classpath
        Image image = new Image(getClass().getResource(rutaImagen).toExternalForm());
        imageView.setImage(image);
    }

    /**
     * Método que se ejecuta cuando el usuario hace clic en el botón para abrir
     * su perfil. Este método carga la ventana del perfil de usuario.
     *
     * @param event El evento generado por el clic en el botón.
     */
    @FXML
    private void abrirPerfilBtn(javafx.scene.input.MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Perfil.fxml"));
            Parent root = loader.load();

            PerfilController controller = loader.getController();

            // Obtener el Stage de la ventana actual
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Perfil de Usuario");

            // Crear la nueva escena y establecer el tamaño de la ventana
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

    /**
     * Método que se ejecuta cuando el usuario hace clic en el botón de cerrar
     * sesión. Muestra una alerta de confirmación antes de proceder con el
     * cierre de sesión. Si el usuario confirma, se abre la ventana de inicio de
     * sesión y registro.
     *
     * @param event El evento generado por el clic en el botón.
     */
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

    /**
     * Método que maneja la acción de ir hacia atrás en la interfaz. Carga la
     * vista principal de navegación y la establece en una nueva ventana.
     *
     * @param event El evento generado por la acción del usuario al hacer clic
     * en el botón.
     */
    private void irAtras(ActionEvent event) {
        try {
            // Se carga la vista FXML para la pantalla principal de navegación
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipal.fxml"));
            Parent root = loader.load();

            // Se coloca la vista cargada en un ScrollPane
            ScrollPane sc = new ScrollPane();
            sc.setContent(root);

            // Configuración del comportamiento de las barras de desplazamiento
            sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

            // Obtener el Stage de la ventana actual
            Stage stage = (Stage) homeBtn.getScene().getWindow();
            stage.setTitle("Navegación Principal");

            // Crear la nueva escena con la vista cargada y asignar la hoja de estilos
            Scene scene = new Scene(sc);
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

            // Establecer las dimensiones de la ventana
            stage.setWidth(1000);  // Establecer el ancho
            stage.setHeight(800);  // Establecer la altura

            // Establecer la nueva escena en el Stage y mostrarla
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Si hay algún error al cargar la vista, se muestra un mensaje de error
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Método que maneja la acción de abrir la ventana "Tus Vehículos". Carga la
     * vista correspondiente y la muestra en una nueva ventana.
     *
     * @param event El evento generado por la acción del usuario al hacer clic
     * en el botón.
     */
    private void abrirVentanaTusVehiculos(ActionEvent event) {
        try {
            // Se carga la vista FXML para la pantalla de "Tus Vehículos"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TusVehiculos.fxml"));
            Parent root = loader.load();

            // Se obtiene el controlador de la nueva vista cargada
            TusVehiculosController controler = loader.getController();

            // Obtener el Stage desde el nodo que disparó el evento
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Establecer el título de la ventana
            stage.setTitle("Tus Vehiculos");

            // Crear la nueva escena con la vista cargada y asignar la hoja de estilos
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

            // Establecer las dimensiones de la ventana
            stage.setWidth(1000);  // Establecer el ancho
            stage.setHeight(800);  // Establecer la altura

            // Establecer la nueva escena en el Stage y mostrarla
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            // Si hay algún error al cargar la vista, se muestra un mensaje de error
            Logger.getLogger(TusVehiculosController.class
                    .getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Método que se activa cuando el usuario hace clic en el botón de "Comprar"
     * un vehículo. Este método gestiona el proceso de compra del vehículo
     * seleccionado, incluyendo la validación de si el usuario ya ha comprado el
     * vehículo y la confirmación de la compra.
     *
     * @param event El evento generado por la acción del usuario al hacer clic
     * en el botón de compra.
     */
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

        // Validar si el usuario ya ha comprado el vehículo
        List<Compra> compras = CompraManagerFactory.get().findAll_XML(new GenericType<List<Compra>>() {
        });
        for (Compra c : compras) {
            if (c.getIdCompra().getIdPersona().equals(usuario.getIdPersona())
                    && c.getIdCompra().getIdVehiculo().equals(vehiculo.getIdVehiculo())) {
                System.out.println("Vehículo Comprado");
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

    /**
     * Genera una matrícula aleatoria en el formato "1234 ABC", donde los
     * primeros 4 dígitos son números aleatorios entre 1000 y 9999, y las
     * últimas 3 letras son aleatorias entre A y Z.
     *
     * @return La matrícula generada como un String en el formato "1234 ABC".
     */
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
