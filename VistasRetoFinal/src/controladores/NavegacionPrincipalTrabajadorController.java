/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import javafx.scene.input.MouseEvent;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private Button restablecerBtn;

    @FXML
    private MenuItem gestionVehiculos;

    @FXML
    private MenuItem gestionProveedores;

    @FXML
    private MenuItem gestionMantenimientos;

    @FXML
    public void mostrarFiltroKilometraje(MouseEvent event) {
        // Antes de abrir el popup, aseguramos que se haya seleccionado el filtro correcto
        mostrarPopup(event.getSource(), crearRangoInput());
    }

    @FXML
    public void mostrarFiltroColor(MouseEvent event) {
        mostrarPopup(event.getSource(), crearPaletaDeColores());
    }

    @FXML
    public void mostrarFiltroPrecio(MouseEvent event) {
        // Antes de abrir el popup, aseguramos que se haya seleccionado el filtro correcto
        mostrarPopup(event.getSource(), crearRangoInput());
    }

    @FXML
    public void mostrarFiltroMarca(MouseEvent event) {
        mostrarPopup(event.getSource(), cargarMarcas());
    }

    @FXML
    public void mostrarFiltroModelo(MouseEvent event) {
        mostrarPopup(event.getSource(), cargarModelos());
    }

    // Declaracion del Popup
    private Popup popup;
    private List<Vehiculo> vehiculosSinFiltrar;
    private List<Vehiculo> vehiculosFiltrados = new ArrayList<>();

    // Metodo Initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Se añaden los listeners a todos los botones.
        gestionVehiculos.setOnAction(this::abrirVentanaGestionVehiculos);
        gestionProveedores.setOnAction(this::abrirVentanaGestionProveedores);
        gestionMantenimientos.setOnAction(this::abrirVentanaGestionMantenimientos);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        restablecerBtn.setOnMouseClicked(this::restablecerFiltros);

        generarBotones();

    }

    public void restablecerFiltros(MouseEvent event) {
        vehiculosSinFiltrar = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });
        actualizarVistaConVehiculos(vehiculosSinFiltrar);
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

    private void abrirVentanaInformacionVehiculo(ActionEvent event, Vehiculo vehiculo) {
        try {

            VehiculoInfoExtraManager.setVehiculo(vehiculo);

            // Se carga el FXML con la información de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/InformacionExtraVehiculo.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            InformacionExtraVehiculoController controller = loader.getController();

            // Guardamos el objeto en la clase para que pueda ser utilizado en el controlador
            // Obtener el Stage
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Información de Vehículo");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(InformacionExtraVehiculoController.class.getName()).log(Level.SEVERE, null, ex);
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

        // Agregar listeners para detectar cambios en los campos de texto
        desde.textProperty().addListener((observable, oldValue, newValue) -> aplicarFiltroKilometrajePrecio(desde, hasta));
        hasta.textProperty().addListener((observable, oldValue, newValue) -> aplicarFiltroKilometrajePrecio(desde, hasta));

        return vbox;
    }

    // Método para cargar las marcas de los vehículos
    private VBox cargarMarcas() {
        List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });

        // Crear una lista de marcas únicas
        Set<String> marcasUnicas = new HashSet<>(); // Usamos un HashSet para evitar duplicados
        for (Vehiculo vehiculo : vehiculos) {
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
        ComboBox<String> comboBox = new ComboBox<>();
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

    // Método para cargar las marcas de los vehículos
    private VBox cargarModelos() {
        List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });

        // Crear una lista de marcas únicas
        Set<String> modelosUnicos = new HashSet<>(); // Usamos un HashSet para evitar duplicados
        for (Vehiculo vehiculo : vehiculos) {
            modelosUnicos.add(vehiculo.getModelo());
        }

        // Crear un VBox para contener las marcas
        VBox vbox = new VBox(10); // Espaciado de 10 px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Agregar un título al VBox
        Label label = new Label("Selecciona una Marca:");
        label.setStyle("-fx-text-fill: white;");
        vbox.getChildren().add(label);

        // Crear un ComboBox para mostrar las marcas
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(modelosUnicos); // Agregar las marcas al ComboBox
        comboBox.setPromptText("Selecciona una marca...");
        comboBox.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Agregar un listener para cuando el usuario seleccione un modelo
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filtrarPorModelo(newValue);
            }
        });

        // Agregar el ComboBox al VBox
        vbox.getChildren().add(comboBox);

        // Devolver el VBox con las marcas cargadas
        return vbox;
    }

    // Modifica el método para aplicar el filtro de kilometraje y precio
    private void aplicarFiltroKilometrajePrecio(TextField desde, TextField hasta) {
        try {
            // Obtener los valores desde y hasta, con validación
            double desdeValor = desde.getText().isEmpty() ? 0 : Double.parseDouble(desde.getText());
            double hastaValor = hasta.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(hasta.getText());

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
                        double kilometraje = vehiculo.getKm(); // Obtener el kilometraje del vehículo

                        // Aplicar el filtro de precio y kilometraje
                        boolean filtroPrecio = (precio >= desdeValor && precio <= hastaValor);
                        boolean filtroKilometraje = (kilometraje >= desdeValor && kilometraje <= hastaValor);

                        // Si al menos uno de los filtros es válido, incluir el vehículo
                        return filtroPrecio || filtroKilometraje;
                    })
                    .collect(Collectors.toList());

            // Actualizar la vista con los vehículos filtrados
            actualizarVistaConVehiculos(vehiculosFiltrados);
        } catch (NumberFormatException e) {
            // Si hay un error en el formato de número, mostrar un mensaje
            System.out.println("Valor inválido para el filtro de kilometraje o precio.");
        }
    }

    private void generarBotones() {
        // Obtener la lista de vehículos desde la base de datos
        List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });

        int fila = 0;
        int columna = 0;

        for (Vehiculo vehiculo : vehiculos) {
            // Ruta de la Imagen
            String rutaCoche = vehiculo.getRuta();
            System.out.println(rutaCoche);

            if (rutaCoche == null || rutaCoche.isEmpty()) {
                rutaCoche = "/img/sinImagen.jpg";
            }

            // Usar getClass().getResource() para acceder a la imagen desde el classpath
            Image image = new Image(getClass().getResource(rutaCoche).toExternalForm());

            // Crear el nombre del vehículo
            String nombreVehiculo = vehiculo.getMarca() + " " + vehiculo.getModelo();

            // Crear el ImageView y ajustarlo al tamaño de la ventana
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);  // Ajustamos el tamaño de la imagen (más grande)
            imageView.setFitWidth(200);   // Ajustamos el tamaño de la imagen (más grande)
            imageView.setPreserveRatio(true);  // Preservamos la relación de aspecto de la imagen

            // Crear el Label para el nombre del vehículo y ponerlo en color negro
            Label nombreLabel = new Label(nombreVehiculo);
            nombreLabel.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");  // Establecer el color del texto a negro y tamaño de fuente

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

    private void actualizarVistaConVehiculos(List<Vehiculo> vehiculosFiltrados) {
        // Limpiar la interfaz actual
        gridPane.getChildren().clear();

        // Generar los botones con los vehículos filtrados
        int fila = 0;
        int columna = 0;

        for (Vehiculo vehiculo : vehiculosFiltrados) {
            // La lógica de creación de botones sigue igual
            String rutaCoche = vehiculo.getRuta();
            if (rutaCoche == null || rutaCoche.isEmpty()) {
                rutaCoche = "/img/sinImagen.jpg";
            }

            Image image = new Image(getClass().getResource(rutaCoche).toExternalForm());
            String nombreVehiculo = vehiculo.getMarca() + " " + vehiculo.getModelo();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            Label nombreLabel = new Label(nombreVehiculo);
            nombreLabel.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");

            VBox vbox = new VBox(5);
            vbox.getChildren().addAll(imageView, nombreLabel);

            Button button = new Button();
            button.setGraphic(vbox);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setMaxHeight(Double.MAX_VALUE);
            button.setOnAction(event -> abrirVentanaInformacionVehiculo(null, vehiculo));

            gridPane.add(button, columna, fila);

            columna++;
            if (columna == 3) {
                columna = 0;
                fila++;
            }
        }
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
            Rectangle colorBox = new Rectangle(30, 30, color); // Cambiar el tamaño de los cuadros a 30x30 píxeles
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

    private String convertirColorAString(Color color) {
        // Compara el color con los valores predefinidos y devuelve el nombre en String
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

    private void filtrarPorColor(Color color) {
        // Filtrar los vehículos por color
        List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });

        Color colorSeleccionado = color;
        String nombreColor = convertirColorAString(colorSeleccionado);
        System.out.println("El color seleccionado es: " + nombreColor);

        gridPane.getChildren().clear();
        List<Vehiculo> lista = new ArrayList<>();

        for (Vehiculo v : vehiculos) {
            if (v.getColor().equalsIgnoreCase(nombreColor)) {
                lista.add(v);
            }
        }

        actualizarVistaConVehiculos(lista);
    }

    // Método para filtrar los vehículos por marca
    private void filtrarPorMarca(String marcaSeleccionada) {
        List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });

        // Filtrar los vehículos que coinciden con la marca seleccionada
        List<Vehiculo> vehiculosFiltrados = vehiculos.stream()
                .filter(vehiculo -> vehiculo.getMarca().equalsIgnoreCase(marcaSeleccionada))
                .collect(Collectors.toList());

        // Actualizar la vista con los vehículos filtrados
        actualizarVistaConVehiculos(vehiculosFiltrados);
    }

    // Método para filtrar los vehículos por marca
    private void filtrarPorModelo(String modeloSeleccionado) {
        List<Vehiculo> vehiculos = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });

        // Filtrar los vehículos que coinciden con la marca seleccionada
        List<Vehiculo> vehiculosFiltrados = vehiculos.stream()
                .filter(vehiculo -> vehiculo.getModelo().equalsIgnoreCase(modeloSeleccionado))
                .collect(Collectors.toList());

        // Actualizar la vista con los vehículos filtrados
        actualizarVistaConVehiculos(vehiculosFiltrados);
    }

}
