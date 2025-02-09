/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import logica.SessionManager;
import logica.VehiculoManagerFactory;
import modelo.Persona;
import modelo.Usuario;
import modelo.Vehiculo;

/**
 *
 * @author crice
 */
public class NavegacionPrincipalController implements Initializable {
// Elementos de la Ventana

    @FXML
    private Button homeBtn; // Botón para volver a la pantalla principal

    @FXML
    private GridPane gridPane; // Contenedor para los vehículos mostrados en la interfaz

    @FXML
    private Button tusVehiculosBtn; // Botón para abrir la ventana de tus vehículos

    @FXML
    private Button restablecerBtn; // Botón para restablecer los filtros de búsqueda

    @FXML
    private Button cerrarSesionBtn; // Botón para cerrar sesión

    @FXML
    private TextField barraBusqueda; // Campo de texto para la barra de búsqueda de vehículos

    /**
     * Muestra un popup con el filtro de kilometraje. Se asegura que el popup se
     * muestre debajo del botón que activó el evento.
     *
     * @param event El evento generado al hacer clic en el botón de filtro de
     * kilometraje.
     */
    @FXML
    public void mostrarFiltroKilometraje(MouseEvent event) {
        if (filtroKm == null) {
            filtroKm = crearPopup(crearRangoInputKm()); // Si el filtro no está creado, se crea
            Node node = (Node) event.getSource(); // Obtener el nodo del botón que disparó el evento
            Bounds bounds = node.localToScreen(node.getBoundsInLocal()); // Obtener la posición del botón
            filtroKm.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        } else {
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroKm.show(node, bounds.getMinX(), bounds.getMaxY());
        }
    }

    /**
     * Muestra un popup con el filtro de color. Se asegura que el popup se
     * muestre debajo del botón que activó el evento.
     *
     * @param event El evento generado al hacer clic en el botón de filtro de
     * color.
     */
    @FXML
    public void mostrarFiltroColor(MouseEvent event) {
        if (filtroColor == null) {
            filtroColor = crearPopup(crearPaletaDeColores()); // Si el filtro no está creado, se crea
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroColor.show(node, bounds.getMinX(), bounds.getMaxY());
        } else {
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroColor.show(node, bounds.getMinX(), bounds.getMaxY());
        }
    }

    /**
     * Muestra un popup con el filtro de precio. Se asegura que el popup se
     * muestre debajo del botón que activó el evento.
     *
     * @param event El evento generado al hacer clic en el botón de filtro de
     * precio.
     */
    @FXML
    public void mostrarFiltroPrecio(MouseEvent event) {
        if (filtroPrecio == null) {
            filtroPrecio = crearPopup(crearRangoInputPrecio()); // Si el filtro no está creado, se crea
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroPrecio.show(node, bounds.getMinX(), bounds.getMaxY());
        } else {
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroPrecio.show(node, bounds.getMinX(), bounds.getMaxY());
        }
    }

    /**
     * Muestra un popup con el filtro de marca. Se asegura que el popup se
     * muestre debajo del botón que activó el evento.
     *
     * @param event El evento generado al hacer clic en el botón de filtro de
     * marca.
     */
    @FXML
    public void mostrarFiltroMarca(MouseEvent event) {
        if (filtroMarca == null) {
            filtroMarca = crearPopup(cargarMarcas()); // Si el filtro no está creado, se crea
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroMarca.show(node, bounds.getMinX(), bounds.getMaxY());
        } else {
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroMarca.show(node, bounds.getMinX(), bounds.getMaxY());
        }
    }

    /**
     * Muestra un popup con el filtro de modelo. Se asegura que el popup se
     * muestre debajo del botón que activó el evento.
     *
     * @param event El evento generado al hacer clic en el botón de filtro de
     * modelo.
     */
    @FXML
    public void mostrarFiltroModelo(MouseEvent event) {
        if (filtroModelo == null) {
            filtroModelo = crearPopup(cargarModelos()); // Si el filtro no está creado, se crea
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroModelo.show(node, bounds.getMinX(), bounds.getMaxY());
        } else {
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroModelo.show(node, bounds.getMinX(), bounds.getMaxY());
        }
    }

    @FXML
    private ImageView backgroundImage; // Imagen de fondo de la ventana

    @FXML
    private StackPane stackPane; // Contenedor para la imagen de fondo y otros elementos

// Filtros de búsqueda inicializados como null
    private Popup filtroKm = null;
    private Popup filtroPrecio = null;
    private Popup filtroColor = null;
    private Popup filtroMarca = null;
    private Popup filtroModelo = null;

// Campos de texto para los filtros de kilometraje y precio
    TextField desdeKm = new TextField();
    TextField hastaKm = new TextField();
    TextField desdePrecio = new TextField();
    TextField hastaPrecio = new TextField();

// Listas para manejar los vehículos a mostrar
    private List<Vehiculo> vehi;
    private List<Vehiculo> vehiMostrar;
    private List<Vehiculo> listaOriginal = new ArrayList<>();

// ComboBoxes para los filtros de marca y modelo
    private ComboBox<String> comboBox = new ComboBox<>();
    private ComboBox<String> comboBoxModelos = new ComboBox<>();

    private Rectangle colorBox; // Cuadro de selección de color

// Declaración de un Popup para filtros
    private Popup popup;
    private List<Vehiculo> vehiculosSinFiltrar;
    private List<Vehiculo> vehiculosFiltrados = new ArrayList<>();

    private Usuario usuario; // Usuario logueado

    /**
     * Método de inicialización. Configura los listeners de los botones y de la
     * barra de búsqueda, y carga los vehículos.
     *
     * @param location La URL de la ubicación del FXML cargado.
     * @param resources Los recursos disponibles para la vista.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Añadir listeners a los botones de la vista
        tusVehiculosBtn.setOnAction(this::abrirVentanaTusVehiculos); // Acción para abrir la ventana de tus vehículos
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp); // Acción para cerrar sesión
        restablecerBtn.setOnMouseClicked(this::restablecerFiltros); // Acción para restablecer los filtros

        // Añadir listener a la barra de búsqueda para filtrar los vehículos
        barraBusqueda.textProperty().addListener((observable, oldValue, newValue) -> filtrarVehiculosBarra(newValue));

        // Cargar los vehículos desde la fuente de datos
        cargarVehiculos();

        System.out.println("Ventana inicializada correctamente.");
    }
// Abrir perfil mediante ImageView

    @FXML
    private void abrirPerfilBtn(javafx.scene.input.MouseEvent event) {
        try {
            // Se carga el FXML con la información de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/Perfil.fxml"));
            Parent root = loader.load();

            // Obtener el controlador para interactuar con la vista cargada
            PerfilController controller = loader.getController();

            //controller.setUsuario((Usuario) usuario); // Descomentado cuando se asigne un usuario
            // Obtener el Stage actual desde el nodo del botón
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Perfil de Usuario"); // Establecer título de la ventana
            Scene scene = new Scene(root); // Crear una nueva escena con el contenido cargado
            scene.getStylesheets().add(getClass().getResource("/css/Perfil.css").toExternalForm()); // Agregar estilos CSS
            stage.setScene(scene); // Establecer la nueva escena en el Stage
            stage.show(); // Mostrar la ventana
        } catch (IOException ex) {
            // Manejo de errores si ocurre una excepción al cargar el FXML
            Logger.getLogger(TablaProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
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
            if (response == ButtonType.OK) { // Si el usuario confirma cerrar sesión
                try {
                    // Se carga el FXML con la información de la vista SignIn & SignUp
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignInSignUp.fxml"));
                    Parent root = loader.load();

                    SignController controler = loader.getController(); // Obtener el controlador de la vista

                    // Obtener el Stage desde el nodo que disparó el evento
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                    stage.setTitle("SignIn & SignUp"); // Establecer el título de la ventana
                    Scene scene = new Scene(root); // Crear la escena con el contenido cargado
                    scene.getStylesheets().add(getClass().getResource("/css/stylesOscuro.css").toExternalForm()); // Agregar el CSS

                    // Mostrar la nueva escena en el Stage
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    // Si hay un error al cargar la vista, se muestra una alerta
                    Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
                    new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
                }
            } else {
                // Si el usuario cancela, no se cierra la sesión
                System.out.println("Cancelado, no se cierra la sesión.");
            }
        });
    }

// Abrir Ventana Tus Vehículos
    private void abrirVentanaTusVehiculos(ActionEvent event) {
        try {
            // Se carga el FXML con la información de la vista Tus Vehículos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TusVehiculos.fxml"));
            Parent root = loader.load();

            TusVehiculosController controler = loader.getController(); // Obtener el controlador de la vista

            // Obtener el Stage desde el nodo que disparó el evento
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Tus Vehiculos"); // Establecer título de la ventana
            Scene scene = new Scene(root); // Crear la nueva escena con el contenido cargado
            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm()); // Agregar estilos CSS

            // Mostrar la nueva escena en el Stage
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            // Manejo de errores si ocurre una excepción al cargar el FXML
            Logger.getLogger(TusVehiculosController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

// Abrir Ventana Información Extra Vehículo
    private void abrirVentanaInformacionVehiculo(ActionEvent event, Vehiculo vehiculo) {
        try {
            // Se asigna el vehículo a la clase que gestiona la información extra
            VehiculoInfoExtraManager.setVehiculo(vehiculo);

            // Se carga el FXML con la información de la vista de información extra del vehículo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/InformacionExtraVehiculo.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la vista cargada
            InformacionExtraVehiculoController controller = loader.getController();

            // Obtener el Stage desde el nodo que disparó el evento
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Información de Vehículo"); // Establecer el título de la ventana
            Scene scene = new Scene(root); // Crear la nueva escena con el contenido cargado
            scene.getStylesheets().add(getClass().getResource("/css/InfoVehiculo.css").toExternalForm()); // Agregar estilos CSS
            stage.setScene(scene); // Establecer la escena en el Stage

            // Ajustar el tamaño de la ventana
            stage.setWidth(1000);  // Establecer el ancho de la ventana
            stage.setHeight(600);  // Establecer el alto de la ventana

            // Si no deseas que el tamaño sea modificable, establecer a false
            stage.setResizable(false);

            stage.show(); // Mostrar la ventana

        } catch (IOException ex) {
            // Manejo de errores si ocurre una excepción al cargar el FXML
            Logger.getLogger(InformacionExtraVehiculoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    // Método para mostrar un popup con un contenido específico
    private void mostrarPopup(Object source, VBox contenido) {
        if (popup != null && popup.isShowing()) {
            popup.hide(); // Ocultar el popup anterior si está visible
        }

        popup = new Popup();  // Crear un nuevo Popup
        popup.getContent().add(contenido);  // Agregar el contenido al Popup
        popup.setAutoHide(true);  // Configurar el popup para que se cierre al hacer clic fuera de él

        Node node = (Node) source;
        Bounds bounds = node.localToScreen(node.getBoundsInLocal());  // Obtener la posición del botón en la pantalla
        popup.show(node, bounds.getMinX(), bounds.getMaxY());  // Mostrar el popup debajo del botón
    }

// Método para crear un VBox con dos campos de texto para un rango (Desde y Hasta)
    private VBox crearRangoInput() {
        VBox vbox = new VBox(10);  // Crear un VBox con espacio de 10px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");  // Estilo del VBox

        // Campo de texto para "Desde"
        TextField desde = new TextField();
        desde.setPromptText("Desde...");  // Texto de ayuda para el campo
        desde.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");  // Estilo del campo

        // Campo de texto para "Hasta"
        TextField hasta = new TextField();
        hasta.setPromptText("Hasta...");  // Texto de ayuda para el campo
        hasta.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");  // Estilo del campo

        // Etiqueta "Rango"
        Label label = new Label("Rango:");
        label.setStyle("-fx-text-fill: white;");  // Estilo de la etiqueta

        vbox.getChildren().addAll(label, desde, hasta);  // Añadir los elementos al VBox
        return vbox;  // Devolver el VBox con los campos
    }

// Método para crear un VBox con un ComboBox con opciones dadas
    private VBox crearComboBoxInput(String labelText, String... opciones) {
        VBox vbox = new VBox(10);  // Crear un VBox con espacio de 10px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");  // Estilo del VBox

        // ComboBox con opciones
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(opciones);  // Añadir las opciones al ComboBox
        comboBox.setPromptText(labelText);  // Establecer el texto del prompt
        comboBox.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");  // Estilo del ComboBox

        // Etiqueta con el texto dado
        Label label = new Label(labelText);
        label.setStyle("-fx-text-fill: white;");  // Estilo de la etiqueta

        vbox.getChildren().addAll(label, comboBox);  // Añadir la etiqueta y el ComboBox al VBox
        return vbox;  // Devolver el VBox con los elementos
    }

// Método para cargar los vehículos desde la base de datos y mostrarlos en el GridPane
    private void cargarVehiculos() {
        // Obtener la lista de vehículos desde la base de datos
        vehi = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });
        vehiMostrar = vehi;  // Guardar la lista de vehículos para mostrarla

        int fila = 0;
        int columna = 0;

        for (Vehiculo vehiculo : vehi) {
            // Ruta de la imagen del vehículo
            String rutaCoche = vehiculo.getRuta();
            System.out.println(rutaCoche);

            if (rutaCoche == null || rutaCoche.isEmpty()) {
                rutaCoche = "/img/sinImagen.jpg";  // Si no tiene ruta de imagen, usar una imagen predeterminada
            }

            // Usar getClass().getResource() para acceder a la imagen desde el classpath
            Image image = new Image(getClass().getResource(rutaCoche).toExternalForm());

            // Crear el nombre del vehículo (Marca + Modelo)
            String nombreVehiculo = vehiculo.getMarca() + " " + vehiculo.getModelo();

            // Crear el ImageView y ajustarlo al tamaño de la ventana
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(185);  // Ajustar el tamaño de la imagen (más grande)
            imageView.setFitWidth(185);   // Ajustar el tamaño de la imagen (más grande)
            imageView.setPreserveRatio(true);  // Preservar la relación de aspecto de la imagen

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
            button.setOnAction(event -> abrirVentanaInformacionVehiculo(null, vehiculo));

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

    // Método para restablecer todos los filtros y mostrar la lista completa de vehículos
    public void restablecerFiltros(MouseEvent event) {

        // Restaurar la lista completa de vehículos desde la base de datos o fuente original
        vehi = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });

        // Restablecer todas las listas de filtrado
        vehiMostrar = new ArrayList<>(vehi);  // Restaurar vehiMostrar
        vehiculosFiltrados = new ArrayList<>(vehi); // Restaurar vehiculosFiltrados
        vehiculosSinFiltrar = new ArrayList<>(vehi); // Restaurar vehiculosSinFiltrar

        // Limpiar los campos de texto
        desdeKm.clear();
        hastaKm.clear();
        desdePrecio.clear();
        hastaPrecio.clear();

        // Restablecer los ComboBox (desmarcar cualquier selección)
        comboBox.getSelectionModel().clearSelection();
        comboBoxModelos.getSelectionModel().clearSelection();

        // Actualizar la vista con la lista restaurada de vehículos
        actualizarVistaConVehiculos(vehi);
    }

// Método para actualizar la vista con la lista de vehículos filtrados
    private void actualizarVistaConVehiculos(List<Vehiculo> vehiculosFiltrados) {
        // Limpiar la interfaz actual (GridPane) para mostrar los nuevos vehículos
        gridPane.getChildren().clear();

        int fila = 0;
        int columna = 0;

        // Crear los botones con los vehículos filtrados
        for (Vehiculo vehiculo : vehiculosFiltrados) {
            // Ruta de la imagen del vehículo
            String rutaCoche = vehiculo.getRuta();
            if (rutaCoche == null || rutaCoche.isEmpty()) {
                rutaCoche = "/img/sinImagen.jpg";  // Usar una imagen predeterminada si no tiene ruta
            }

            // Crear la imagen para el vehículo
            Image image = new Image(getClass().getResource(rutaCoche).toExternalForm());
            String nombreVehiculo = vehiculo.getMarca() + " " + vehiculo.getModelo();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(185);  // Ajustar la altura de la imagen
            imageView.setFitWidth(185);   // Ajustar el ancho de la imagen
            imageView.setPreserveRatio(true);  // Mantener la relación de aspecto de la imagen

            // Crear el Label para el nombre del vehículo
            Label nombreLabel = new Label(nombreVehiculo);
            nombreLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");  // Establecer color blanco y tamaño de fuente

            // Crear un VBox para contener la imagen y el nombre del vehículo
            VBox vbox = new VBox(5);  // Espacio de 5px entre los elementos
            vbox.getChildren().addAll(imageView, nombreLabel);

            // Crear un botón y asignar el VBox como contenido gráfico
            Button button = new Button();
            button.setGraphic(vbox);
            button.setMaxWidth(Double.MAX_VALUE);  // Asegurarse de que el botón ocupe todo el espacio disponible
            button.setMaxHeight(Double.MAX_VALUE); // Asegurarse de que el botón ocupe todo el espacio disponible
            button.setOnAction(event -> abrirVentanaInformacionVehiculo(null, vehiculo));  // Acción al hacer clic

            // Añadir el botón al GridPane en la fila y columna correspondientes
            gridPane.add(button, columna, fila);

            // Actualizar fila y columna para el siguiente botón
            columna++;
            if (columna == 3) {  // Después de 3 botones, pasamos a la siguiente fila
                columna = 0;
                fila++;
            }
        }
    }

// Método para crear un Popup que contiene el contenido dado
    private Popup crearPopup(VBox contenido) {
        if (popup != null && popup.isShowing()) {
            popup.hide();  // Ocultar el popup anterior si está visible
        }
        popup = new Popup();  // Crear un nuevo Popup
        popup.getContent().add(contenido);  // Añadir el contenido al Popup
        popup.setAutoHide(true);  // Cerrar automáticamente al hacer clic fuera del popup    
        return popup;  // Devolver el Popup creado
    }

// Método para crear el campo de entrada para el rango de kilómetros
    private VBox crearRangoInputKm() {
        VBox vbox = new VBox(10);  // Crear un VBox con espacio de 10px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Campo de texto para "Desde"
        desdeKm.setPromptText("Desde...");
        desdeKm.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Campo de texto para "Hasta"
        hastaKm.setPromptText("Hasta...");
        hastaKm.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Etiqueta "Rango"
        Label label = new Label("Rango:");
        label.setStyle("-fx-text-fill: white;");

        vbox.getChildren().addAll(label, desdeKm, hastaKm);

        // Agregar listeners para detectar cambios en los campos de texto
        desdeKm.textProperty().addListener((observable, oldValue, newValue) -> setFilters(desdeKm, hastaKm));
        hastaKm.textProperty().addListener((observable, oldValue, newValue) -> setFilters(desdeKm, hastaKm));

        return vbox;  // Devolver el VBox con los elementos
    }

// Método para crear el campo de entrada para el rango de precios
    private VBox crearRangoInputPrecio() {
        VBox vbox = new VBox(10);  // Crear un VBox con espacio de 10px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Campo de texto para "Desde"
        desdePrecio.setPromptText("Desde...");
        desdePrecio.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Campo de texto para "Hasta"
        hastaPrecio.setPromptText("Hasta...");
        hastaPrecio.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Etiqueta "Rango"
        Label label = new Label("Rango:");
        label.setStyle("-fx-text-fill: white;");

        vbox.getChildren().addAll(label, desdePrecio, hastaPrecio);

        // Agregar listeners para detectar cambios en los campos de texto
        desdePrecio.textProperty().addListener((observable, oldValue, newValue) -> setFilters(desdePrecio, hastaPrecio));
        hastaPrecio.textProperty().addListener((observable, oldValue, newValue) -> setFilters(desdePrecio, hastaPrecio));

        return vbox;  // Devolver el VBox con los elementos
    }

    // Método para cargar las marcas de los vehículos
    private VBox cargarMarcas() {
        // Crear una lista de marcas únicas
        Set<String> marcasUnicas = new HashSet<>(); // Usamos un HashSet para evitar duplicados
        for (Vehiculo vehiculo : vehiMostrar) {
            marcasUnicas.add(vehiculo.getMarca());
        }

        // Crear un VBox para contener las marcas
        VBox vbox = new VBox(10); // Espaciado de 10 px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Agregar un título al VBox
        Label label = new Label("Selecciona una Marca:");
        label.setStyle("-fx-text-fill: white;");
        vbox.getChildren().add(label);

        // Crear un ComboBox para mostrar las marcas
        comboBox.getItems().addAll(marcasUnicas); // Agregar las marcas al ComboBox
        comboBox.setPromptText("Selecciona una marca...");
        comboBox.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Agregar un listener para cuando el usuario seleccione una marca
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filtrarPorMarca(newValue);
            }
        });

        // Agregar el ComboBox al VBox
        vbox.getChildren().add(comboBox);

        // Devolver el VBox con las marcas cargadas
        return vbox;
    }

// Método para cargar los modelos de los vehículos
    private VBox cargarModelos() {
        // Crear una lista de modelos únicos
        Set<String> modelosUnicos = new HashSet<>(); // Usamos un HashSet para evitar duplicados
        for (Vehiculo vehiculo : vehiMostrar) {
            modelosUnicos.add(vehiculo.getModelo());
        }

        // Crear un VBox para contener los modelos
        VBox vbox = new VBox(10); // Espaciado de 10 px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Agregar un título al VBox
        Label label = new Label("Selecciona un Modelo:");
        label.setStyle("-fx-text-fill: white;");
        vbox.getChildren().add(label);

        // Crear un ComboBox para mostrar los modelos
        comboBoxModelos.getItems().addAll(modelosUnicos); // Agregar los modelos al ComboBox
        comboBoxModelos.setPromptText("Selecciona un modelo...");
        comboBoxModelos.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Agregar un listener para cuando el usuario seleccione un modelo
        comboBoxModelos.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filtrarPorModelo(newValue);
            }
        });

        // Agregar el ComboBox al VBox
        vbox.getChildren().add(comboBoxModelos);

        // Devolver el VBox con los modelos cargados
        return vbox;
    }

// Modifica el método para aplicar el filtro de kilometraje y precio
    private List<Vehiculo> aplicarFiltroKilometraje(List<Vehiculo> vehi) {
        try {
            // Obtener los valores desde y hasta, con validación
            double desdeValor = desdeKm.getText().isEmpty() ? 0 : Double.parseDouble(desdeKm.getText());
            double hastaValor = hastaKm.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(hastaKm.getText());

            // Si la lista de vehículos filtrados está vacía, cargamos todos los vehículos
            if (vehiculosFiltrados.isEmpty()) {
                vehiculosSinFiltrar = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
                });
                vehiculosFiltrados = new ArrayList<>(vehiculosSinFiltrar); // Cargamos todos los vehículos al principio
            }

            // Filtrar los vehículos según el filtro de kilometraje y precio
            vehiculosFiltrados = vehiculosFiltrados.stream()
                    .filter(vehiculo -> {
                        double kilometraje = vehiculo.getKm(); // Obtener el kilometraje del vehículo

                        // Aplicar el filtro de kilometraje
                        boolean filtroKilometraje = (kilometraje >= desdeValor && kilometraje <= hastaValor);

                        // Si al menos uno de los filtros es válido, incluir el vehículo
                        return filtroKilometraje;
                    })
                    .collect(Collectors.toList());

            // Actualizar la vista con los vehículos filtrados
            actualizarVistaConVehiculos(vehiculosFiltrados);
        } catch (NumberFormatException e) {
            // Si hay un error en el formato de número, mostrar un mensaje
            System.out.println("Valor inválido para el filtro de kilometraje o precio.");
        }
        return vehiculosFiltrados;
    }

    private List<Vehiculo> aplicarFiltroPrecio(List<Vehiculo> vehi) {
        try {
            // Obtener los valores desde y hasta, con validación
            double desdeValor = desdePrecio.getText().isEmpty() ? 0 : Double.parseDouble(desdePrecio.getText());
            double hastaValor = hastaPrecio.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(hastaPrecio.getText());

            // Si la lista de vehículos filtrados está vacía, cargamos todos los vehículos
            if (vehiculosFiltrados.isEmpty()) {
                vehiculosSinFiltrar = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
                });
                vehiculosFiltrados = new ArrayList<>(vehiculosSinFiltrar); // Cargamos todos los vehículos al principio
            }

            // Filtrar los vehículos según el filtro de kilometraje y precio
            vehiculosFiltrados = vehiculosFiltrados.stream()
                    .filter(vehiculo -> {
                        double precio = vehiculo.getPrecio();  // Obtener el precio del vehículo

                        // Aplicar el filtro de precio
                        boolean filtroPrecio = (precio >= desdeValor && precio <= hastaValor);

                        // Si al menos uno de los filtros es válido, incluir el vehículo
                        return filtroPrecio;
                    })
                    .collect(Collectors.toList());

            // Actualizar la vista con los vehículos filtrados
            actualizarVistaConVehiculos(vehiculosFiltrados);
        } catch (NumberFormatException e) {
            // Si hay un error en el formato de número, mostrar un mensaje
            System.out.println("Valor inválido para el filtro de kilometraje o precio.");
        }
        return vehiculosFiltrados;
    }

// Crear el contenido del Popup para el filtro de colores (4 colores por fila)
    private VBox crearPaletaDeColores() {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Crear un GridPane para organizar los colores en 4 columnas
        GridPane grid = new GridPane();
        grid.setHgap(10); // Espaciado horizontal
        grid.setVgap(10); // Espaciado vertical

        // Lista de colores que quieres mostrar
        Color[] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PURPLE, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.BLACK, Color.WHITE, Color.GRAY, Color.BEIGE};

        // Agregar los colores al GridPane (4 por fila)
        int fila = 0;
        int columna = 0;

        for (Color color : colores) {
            colorBox = new Rectangle(30, 30, color); // Cambiar el tamaño de los cuadros a 30x30 píxeles
            colorBox.setOnMouseClicked(e -> filtrarPorColor(color)); // Manejar clics en el color

            // Agregar el color al GridPane
            grid.add(colorBox, columna, fila);

            columna++;
            if (columna == 4) {  // Cambiar de fila después de 4 colores
                columna = 0;
                fila++;
            }
        }

        vbox.getChildren().add(grid);  // Añadir el GridPane con los colores al VBox
        return vbox;
    }

    /**
     * Convierte un objeto Color a un nombre de color en español.
     *
     * @param color El color a convertir.
     * @return El nombre del color en español.
     */
    private String convertirColorAString(Color color) {
        if (color.equals(Color.RED)) {
            return "Rojo";
        } else if (color.equals(Color.BLUE)) {
            return "Azul";
        } else if (color.equals(Color.GREEN)) {
            return "Verde";
        } else if (color.equals(Color.YELLOW)) {
            return "Amarillo";
        } else if (color.equals(Color.PURPLE)) {
            return "Púrpura";
        } else if (color.equals(Color.ORANGE)) {
            return "Naranja";
        } else if (color.equals(Color.CYAN)) {
            return "Cian";
        } else if (color.equals(Color.MAGENTA)) {
            return "Magenta";
        } else if (color.equals(Color.BLACK)) {
            return "Negro";
        } else if (color.equals(Color.WHITE)) {
            return "Blanco";
        } else if (color.equals(Color.GRAY)) {
            return "Gris";
        } else if (color.equals(Color.BEIGE)) {
            return "Beige";
        } else {
            return "Desconocido"; // Para cualquier color no especificado
        }
    }

    /**
     * Filtra los vehículos por color seleccionado.
     *
     * @param color El color seleccionado para filtrar los vehículos.
     * @return La lista de vehículos filtrados por el color seleccionado.
     */
    private List<Vehiculo> filtrarPorColor(Color color) {
        Color colorSeleccionado = color;
        String nombreColor = convertirColorAString(colorSeleccionado);
        System.out.println("El color seleccionado es: " + nombreColor);

        gridPane.getChildren().clear();
        List<Vehiculo> lista = new ArrayList<>();

        for (Vehiculo v : vehiMostrar) {
            if (v.getColor().equalsIgnoreCase(nombreColor)) {
                lista.add(v);
            }
        }

        actualizarVistaConVehiculos(lista);
        return vehiMostrar;
    }

    /**
     * Filtra los vehículos por marca seleccionada.
     *
     * @param marcaSeleccionada La marca seleccionada para filtrar los
     * vehículos.
     * @return La lista de vehículos filtrados por la marca seleccionada.
     */
    private List<Vehiculo> filtrarPorMarca(String marcaSeleccionada) {
        // Filtrar los vehículos que coinciden con la marca seleccionada
        List<Vehiculo> vehiculosFiltrados = vehiMostrar.stream()
                .filter(vehiculo -> vehiculo.getMarca().equalsIgnoreCase(marcaSeleccionada))
                .collect(Collectors.toList());

        // Actualizar la vista con los vehículos filtrados
        actualizarVistaConVehiculos(vehiculosFiltrados);
        return vehiculosFiltrados;
    }

    /**
     * Filtra los vehículos por modelo seleccionado.
     *
     * @param modeloSeleccionado El modelo seleccionado para filtrar los
     * vehículos.
     * @return La lista de vehículos filtrados por el modelo seleccionado.
     */
    private List<Vehiculo> filtrarPorModelo(String modeloSeleccionado) {
        // Filtrar los vehículos que coinciden con el modelo seleccionado
        List<Vehiculo> vehiculosFiltrados = vehiMostrar.stream()
                .filter(vehiculo -> vehiculo.getModelo().equalsIgnoreCase(modeloSeleccionado))
                .collect(Collectors.toList());

        // Actualizar la vista con los vehículos filtrados
        actualizarVistaConVehiculos(vehiculosFiltrados);
        return vehiMostrar;
    }

    /**
     * Aplica filtros de kilometraje y precio a los vehículos mostrados.
     *
     * @param desde El campo de texto con el valor mínimo para el filtro.
     * @param hasta El campo de texto con el valor máximo para el filtro.
     */
    private void setFilters(TextField desde, TextField hasta) {
        if (!desde.getText().isEmpty()) {
            vehiMostrar = aplicarFiltroKilometraje(vehiMostrar);
        }

        if (!desde.getText().isEmpty()) {
            vehiMostrar = aplicarFiltroPrecio(vehiMostrar);
        }
        if (comboBox.getSelectionModel().getSelectedIndex() != -1) {
            vehiMostrar = filtrarPorMarca(comboBox.getSelectionModel().getSelectedItem());
        }
        if (comboBoxModelos.getSelectionModel().getSelectedIndex() != -1) {
            vehiMostrar = filtrarPorModelo(comboBoxModelos.getSelectionModel().getSelectedItem());
        }
        actualizarVistaConVehiculos(vehiMostrar);
    }

    /**
     * Filtra los vehículos en la barra de búsqueda por marca o modelo. Si la
     * barra está vacía, muestra todos los vehículos.
     *
     * @param filtro El texto ingresado en la barra de búsqueda para filtrar los
     * vehículos.
     */
    private void filtrarVehiculosBarra(String filtro) {
        // Si la barra está vacía, mostramos todos los vehículos
        if (filtro == null || filtro.trim().isEmpty()) {
            actualizarVistaConVehiculos(vehi); // Mostrar todos los vehículos originales
            return;
        }

        // Crear una variable local final para evitar el error
        final String filtroLower = filtro.toLowerCase();

        // Filtramos la lista original
        List<Vehiculo> vehiFiltrados = vehi.stream()
                .filter(v -> v.getMarca().toLowerCase().contains(filtroLower)
                || v.getModelo().toLowerCase().contains(filtroLower))
                .collect(Collectors.toList());

        // Actualizar la vista con los vehículos filtrados
        actualizarVistaConVehiculos(vehiFiltrados);
    }
}
