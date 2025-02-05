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

    /**
     * Filtro de vehículos por kilometraje.
     */
    private Popup filtroKm = null;

    /**
     * Filtro de vehículos por precio.
     */
    private Popup filtroPrecio = null;

    /**
     * Filtro de vehículos por color.
     */
    private Popup filtroColor = null;

    /**
     * Filtro de vehículos por marca.
     */
    private Popup filtroMarca = null;

    /**
     * Filtro de vehículos por modelo.
     */
    private Popup filtroModelo = null;

    /**
     * Campo de texto para definir el kilometraje mínimo.
     */
    TextField desdeKm = new TextField();

    /**
     * Campo de texto para definir el kilometraje máximo.
     */
    TextField hastaKm = new TextField();

    /**
     * Campo de texto para definir el precio mínimo.
     */
    TextField desdePrecio = new TextField();

    /**
     * Campo de texto para definir el precio máximo.
     */
    TextField hastaPrecio = new TextField();

    /**
     * Lista de vehículos cargados desde la base de datos.
     */
    private List<Vehiculo> vehi;

    /**
     * Lista de vehículos a mostrar, después de aplicar los filtros.
     */
    private List<Vehiculo> vehiMostrar;

    /**
     * Lista original de vehículos sin aplicar ningún filtro.
     */
    private List<Vehiculo> listaOriginal = new ArrayList<>();

    /**
     * ComboBox para seleccionar un filtro adicional de opciones.
     */
    private ComboBox<String> comboBox = new ComboBox<>();

    /**
     * Caja de color para mostrar el filtro de color.
     */
    private Rectangle colorBox;

    /**
     * ComboBox para seleccionar el modelo de los vehículos.
     */
    private ComboBox<String> comboBoxModelos = new ComboBox<>();

// Elementos de la ventana UI
    /**
     * GridPane que organiza la disposición de los botones de los vehículos.
     */
    @FXML
    private GridPane gridPane;

    /**
     * Botón para ir a la página de inicio.
     */
    @FXML
    private Button homeBtn;

    /**
     * Botón para cerrar sesión.
     */
    @FXML
    private Button cerrarSesionBtn;

    /**
     * Botón para restablecer los filtros aplicados.
     */
    @FXML
    private Button restablecerBtn;

    /**
     * Menú para gestionar vehículos.
     */
    @FXML
    private MenuItem gestionVehiculos;

    /**
     * Menú para gestionar proveedores.
     */
    @FXML
    private MenuItem gestionProveedores;

    /**
     * Menú para gestionar mantenimientos.
     */
    @FXML
    private MenuItem gestionMantenimientos;

    /**
     * Barra de búsqueda para filtrar vehículos por marca o modelo.
     */
    @FXML
    private TextField barraBusqueda;

    /**
     * Muestra el popup para filtrar por kilometraje.
     *
     * @param event El evento del clic en el botón del filtro.
     */
    @FXML
    public void mostrarFiltroKilometraje(MouseEvent event) {
        // Si el popup no está creado, lo creamos
        if (filtroKm == null) {
            filtroKm = crearPopup(crearRangoInputKm());
            // Obtener la posición del botón para mostrar el popup debajo
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroKm.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        } else {
            // Si el popup ya existe, simplemente lo mostramos
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroKm.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        }
    }

    /**
     * Muestra el popup para filtrar por color de vehículo.
     *
     * @param event El evento del clic en el botón del filtro.
     */
    @FXML
    public void mostrarFiltroColor(MouseEvent event) {
        // Si el popup no está creado, lo creamos
        if (filtroColor == null) {
            filtroColor = crearPopup(crearPaletaDeColores());
            // Obtener la posición del botón para mostrar el popup debajo
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroColor.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        } else {
            // Si el popup ya existe, simplemente lo mostramos
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroColor.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        }
    }

    /**
     * Muestra el popup para filtrar por precio de vehículo.
     *
     * @param event El evento del clic en el botón del filtro.
     */
    @FXML
    public void mostrarFiltroPrecio(MouseEvent event) {
        // Si el popup no está creado, lo creamos
        if (filtroPrecio == null) {
            filtroPrecio = crearPopup(crearRangoInputPrecio());
            // Obtener la posición del botón para mostrar el popup debajo
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroPrecio.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        } else {
            // Si el popup ya existe, simplemente lo mostramos
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroPrecio.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        }
    }

    /**
     * Muestra el popup para filtrar por marca de vehículo.
     *
     * @param event El evento del clic en el botón del filtro.
     */
    @FXML
    public void mostrarFiltroMarca(MouseEvent event) {
        // Si el popup no está creado, lo creamos
        if (filtroMarca == null) {
            filtroMarca = crearPopup(cargarMarcas());
            // Obtener la posición del botón para mostrar el popup debajo
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroMarca.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        } else {
            // Si el popup ya existe, simplemente lo mostramos
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroMarca.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        }
    }

    /**
     * Muestra el popup para filtrar por modelo de vehículo.
     *
     * @param event El evento del clic en el botón del filtro.
     */
    @FXML
    public void mostrarFiltroModelo(MouseEvent event) {
        // Si el popup no está creado, lo creamos
        if (filtroModelo == null) {
            filtroModelo = crearPopup(cargarModelos());
            // Obtener la posición del botón para mostrar el popup debajo
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroModelo.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        } else {
            // Si el popup ya existe, simplemente lo mostramos
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            filtroModelo.show(node, bounds.getMinX(), bounds.getMaxY()); // Mostrar el popup debajo del botón
        }
    }

    /**
     * Popup utilizado para mostrar los filtros.
     */
    private Popup popup;

    /**
     * Lista de vehículos sin aplicar ningún filtro.
     */
    private List<Vehiculo> vehiculosSinFiltrar;

    /**
     * Lista de vehículos que cumplen con los filtros aplicados.
     */
    private List<Vehiculo> vehiculosFiltrados = new ArrayList<>();

    /**
     * Método de inicialización de la interfaz gráfica. Configura los listeners
     * para los botones y carga los vehículos iniciales.
     *
     * @param location La ubicación de la vista en el sistema de archivos.
     * @param resources Los recursos utilizados en la interfaz gráfica.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Se añaden los listeners a los botones para gestionar las ventanas de la aplicación
        gestionVehiculos.setOnAction(this::abrirVentanaGestionVehiculos);
        gestionProveedores.setOnAction(this::abrirVentanaGestionProveedores);
        gestionMantenimientos.setOnAction(this::abrirVentanaGestionMantenimientos);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        restablecerBtn.setOnMouseClicked(this::restablecerFiltros);

        // Se configura el listener para la barra de búsqueda de vehículos
        barraBusqueda.textProperty().addListener((observable, oldValue, newValue) -> filtrarVehiculosBarra(newValue));

        // Cargar los vehículos al inicio
        cargarVehiculos();
    }

    /**
     * Filtra los vehículos mostrados según el texto introducido en la barra de
     * búsqueda.
     *
     * @param filtro El texto utilizado para filtrar los vehículos.
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

    /**
     * Carga los vehículos desde la base de datos y los muestra en la interfaz.
     */
    private void cargarVehiculos() {
        // Obtener la lista de vehículos desde la base de datos
        vehi = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });
        vehiMostrar = vehi;

        int fila = 0;
        int columna = 0;

        // Iterar sobre la lista de vehículos cargados
        for (Vehiculo vehiculo : vehi) {
            // Ruta de la imagen asociada al vehículo
            String rutaCoche = vehiculo.getRuta();
            System.out.println(rutaCoche);

            // Si la ruta es nula o vacía, usar una imagen predeterminada
            if (rutaCoche == null || rutaCoche.isEmpty()) {
                rutaCoche = "/img/sinImagen.jpg";
            }

            // Usar getClass().getResource() para acceder a la imagen desde el classpath
            Image image = new Image(getClass().getResource(rutaCoche).toExternalForm());

            // Crear el nombre del vehículo (marca y modelo)
            String nombreVehiculo = vehiculo.getMarca() + " " + vehiculo.getModelo();

            // Crear el ImageView y ajustarlo al tamaño adecuado
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(185);  // Ajustar la altura de la imagen
            imageView.setFitWidth(185);   // Ajustar el ancho de la imagen
            imageView.setPreserveRatio(true);  // Preservar la relación de aspecto de la imagen

            // Crear un Label con el nombre del vehículo y estilizarlo
            Label nombreLabel = new Label(nombreVehiculo);
            nombreLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");  // Color del texto y tamaño de fuente

            // Crear un VBox para organizar la imagen y el nombre del vehículo
            VBox vbox = new VBox(5);  // Espacio entre la imagen y el texto
            vbox.getChildren().addAll(imageView, nombreLabel);

            // Ajustar el tamaño máximo del VBox
            vbox.setMaxWidth(200);   // Limitar el ancho máximo
            vbox.setMaxHeight(300);  // Limitar el alto máximo

            // Crear un botón y asignar el VBox como su contenido gráfico
            Button button = new Button();
            button.setGraphic(vbox);

            // Asegurarse de que el botón ocupe todo el espacio disponible en su celda del GridPane
            button.setMaxWidth(Double.MAX_VALUE);  // Maximizar el ancho del botón
            button.setMaxHeight(Double.MAX_VALUE); // Maximizar la altura del botón

            // Agregar el listener para abrir la ventana con la información del vehículo al hacer clic
            button.setOnAction(event -> abrirVentanaInformacionVehiculo(null, vehiculo));

            // Añadir el botón al GridPane en la fila y columna correspondiente
            gridPane.add(button, columna, fila);

            // Actualizar la fila y columna para el siguiente vehículo
            columna++;
            if (columna == 3) {  // Después de 3 vehículos, pasamos a la siguiente fila
                columna = 0;
                fila++;
            }
        }
    }

    /**
     * Actualiza la vista mostrando los vehículos filtrados en la interfaz.
     * Limpia la vista actual y genera botones con los vehículos filtrados.
     *
     * @param vehiculosFiltrados Lista de vehículos que se mostrarán en la
     * vista.
     */
    private void actualizarVistaConVehiculos(List<Vehiculo> vehiculosFiltrados) {
        // Limpiar la interfaz actual (el GridPane)
        gridPane.getChildren().clear();

        // Inicializar variables para la fila y columna en el GridPane
        int fila = 0;
        int columna = 0;

        // Iterar sobre los vehículos filtrados
        for (Vehiculo vehiculo : vehiculosFiltrados) {
            // Ruta de la imagen del vehículo
            String rutaCoche = vehiculo.getRuta();
            if (rutaCoche == null || rutaCoche.isEmpty()) {
                rutaCoche = "/img/sinImagen.jpg"; // Imagen predeterminada si no hay ruta
            }

            // Crear la imagen del vehículo
            Image image = new Image(getClass().getResource(rutaCoche).toExternalForm());

            // Crear el nombre del vehículo
            String nombreVehiculo = vehiculo.getMarca() + " " + vehiculo.getModelo();

            // Crear el ImageView y ajustarlo al tamaño adecuado
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);  // Ajustar altura
            imageView.setFitWidth(200);   // Ajustar ancho
            imageView.setPreserveRatio(true);  // Mantener la relación de aspecto

            // Crear el Label para el nombre del vehículo y estilizarlo
            Label nombreLabel = new Label(nombreVehiculo);
            nombreLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

            // Crear un VBox para contener la imagen y el nombre
            VBox vbox = new VBox(5);  // Espacio entre la imagen y el texto
            vbox.getChildren().addAll(imageView, nombreLabel);

            // Crear un botón y asignar el VBox como su contenido gráfico
            Button button = new Button();
            button.setGraphic(vbox);

            // Asegurarse de que el botón ocupe todo el espacio disponible en la celda del GridPane
            button.setMaxWidth(Double.MAX_VALUE);  // Maximizar ancho
            button.setMaxHeight(Double.MAX_VALUE); // Maximizar altura

            // Agregar el listener para abrir la ventana de información del vehículo
            button.setOnAction(event -> abrirVentanaInformacionVehiculo(null, vehiculo));

            // Añadir el botón al GridPane en la fila y columna correspondiente
            gridPane.add(button, columna, fila);

            // Actualizar la fila y columna para el siguiente botón
            columna++;
            if (columna == 3) {  // Después de 3 botones, pasamos a la siguiente fila
                columna = 0;
                fila++;
            }
        }
    }

    /**
     * Crea un Popup con el contenido proporcionado. Si el Popup ya está
     * visible, lo oculta antes de mostrar uno nuevo.
     *
     * @param contenido El contenido (VBox) que se mostrará en el Popup.
     * @return El Popup creado.
     */
    private Popup crearPopup(VBox contenido) {
        if (popup != null && popup.isShowing()) {
            popup.hide(); // Ocultar el popup anterior si está visible
        }
        popup = new Popup();  // Crear un nuevo Popup
        popup.getContent().add(contenido);  // Añadir el contenido al Popup
        popup.setAutoHide(true);  // El Popup se cierra automáticamente al hacer clic fuera
        return popup;  // Devolver el Popup creado
    }

    /**
     * Crea un VBox con campos de texto para filtrar por el rango de kilómetros.
     * Los campos de texto permiten al usuario ingresar un valor desde... y
     * hasta... para el rango.
     *
     * @return El VBox con los campos de entrada para el rango de kilómetros.
     */
    private VBox crearRangoInputKm() {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Crear campo de texto para el valor "desde"
        desdeKm.setPromptText("Desde...");
        desdeKm.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Crear campo de texto para el valor "hasta"
        hastaKm.setPromptText("Hasta...");
        hastaKm.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Crear etiqueta "Rango:"
        Label label = new Label("Rango:");
        label.setStyle("-fx-text-fill: white;");

        // Añadir los campos de texto y la etiqueta al VBox
        vbox.getChildren().addAll(label, desdeKm, hastaKm);

        // Agregar listeners para detectar cambios en los campos de texto
        desdeKm.textProperty().addListener((observable, oldValue, newValue) -> setFilters(desdeKm, hastaKm));
        hastaKm.textProperty().addListener((observable, oldValue, newValue) -> setFilters(desdeKm, hastaKm));

        return vbox;  // Devolver el VBox con los campos de texto
    }

    /**
     * Crea un VBox con campos de texto para filtrar por el rango de precios.
     * Los campos de texto permiten al usuario ingresar un valor desde... y
     * hasta... para el rango de precios.
     *
     * @return El VBox con los campos de entrada para el rango de precios.
     */
    private VBox crearRangoInputPrecio() {
        VBox vbox = new VBox(10);  // Crear un VBox con un espaciado de 10px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Crear campo de texto para el valor "desde"
        desdePrecio.setPromptText("Desde...");  // Establecer el texto de sugerencia
        desdePrecio.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Crear campo de texto para el valor "hasta"
        hastaPrecio.setPromptText("Hasta...");  // Establecer el texto de sugerencia
        hastaPrecio.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Crear una etiqueta "Rango:"
        Label label = new Label("Rango:");
        label.setStyle("-fx-text-fill: white;");

        // Añadir los campos de texto y la etiqueta al VBox
        vbox.getChildren().addAll(label, desdePrecio, hastaPrecio);

        // Agregar listeners para detectar cambios en los campos de texto
        desdePrecio.textProperty().addListener((observable, oldValue, newValue) -> setFilters(desdePrecio, hastaPrecio));
        hastaPrecio.textProperty().addListener((observable, oldValue, newValue) -> setFilters(desdePrecio, hastaPrecio));

        return vbox;  // Devolver el VBox con los campos de texto
    }

    /**
     * Carga las marcas de los vehículos y las muestra en un ComboBox. El
     * ComboBox permite al usuario seleccionar una marca, lo que activa el
     * filtro por marca.
     *
     * @return El VBox que contiene el ComboBox con las marcas de los vehículos.
     */
    private VBox cargarMarcas() {
        // Crear una lista de marcas únicas utilizando un HashSet para evitar duplicados
        Set<String> marcasUnicas = new HashSet<>();
        for (Vehiculo vehiculo : vehiMostrar) {
            marcasUnicas.add(vehiculo.getMarca());
        }

        // Crear un VBox para contener las marcas
        VBox vbox = new VBox(10);  // Espaciado de 10px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Agregar una etiqueta de título
        Label label = new Label("Selecciona una Marca:");
        label.setStyle("-fx-text-fill: white;");
        vbox.getChildren().add(label);

        // Crear un ComboBox para mostrar las marcas
        comboBox.getItems().addAll(marcasUnicas);  // Agregar las marcas únicas al ComboBox
        comboBox.setPromptText("Selecciona una marca...");  // Texto de sugerencia
        comboBox.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Agregar un listener para detectar cuando el usuario selecciona una marca
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filtrarPorMarca(newValue);  // Filtrar los vehículos por la marca seleccionada
            }
        });

        // Agregar el ComboBox al VBox
        vbox.getChildren().add(comboBox);

        return vbox;  // Devolver el VBox con las marcas cargadas
    }

    /**
     * Carga los modelos de los vehículos y los muestra en un ComboBox. El
     * ComboBox permite al usuario seleccionar un modelo, lo que activa el
     * filtro por modelo.
     *
     * @return El VBox que contiene el ComboBox con los modelos de los
     * vehículos.
     */
    private VBox cargarModelos() {
        // Crear una lista de modelos únicos utilizando un HashSet para evitar duplicados
        Set<String> modelosUnicos = new HashSet<>();
        for (Vehiculo vehiculo : vehiMostrar) {
            modelosUnicos.add(vehiculo.getModelo());
        }

        // Crear un VBox para contener los modelos
        VBox vbox = new VBox(10);  // Espaciado de 10px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Agregar una etiqueta de título
        Label label = new Label("Selecciona una Modelo:");
        label.setStyle("-fx-text-fill: white;");
        vbox.getChildren().add(label);

        // Crear un ComboBox para mostrar los modelos
        comboBoxModelos.getItems().addAll(modelosUnicos);  // Agregar los modelos únicos al ComboBox
        comboBoxModelos.setPromptText("Selecciona una modelo...");  // Texto de sugerencia
        comboBoxModelos.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-prompt-text-fill: #aaa;");

        // Agregar un listener para detectar cuando el usuario selecciona un modelo
        comboBoxModelos.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filtrarPorModelo(newValue);  // Filtrar los vehículos por el modelo seleccionado
            }
        });

        // Agregar el ComboBox al VBox
        vbox.getChildren().add(comboBoxModelos);

        return vbox;  // Devolver el VBox con los modelos cargados
    }

    /**
     * Aplica el filtro de kilometraje a la lista de vehículos. Filtra los
     * vehículos que se encuentren dentro del rango de kilometraje especificado.
     *
     * @param vehi Lista de vehículos a filtrar.
     * @return La lista de vehículos filtrados por kilometraje.
     */
    private List<Vehiculo> aplicarFiltroKilometraje(List<Vehiculo> vehi) {
        try {
            // Obtener los valores desde y hasta, con validación
            double desdeValor = desdeKm.getText().isEmpty() ? 0 : Double.parseDouble(desdeKm.getText());
            double hastaValor = hastaKm.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(hastaKm.getText());

            // Si la lista de vehículos filtrados está vacía, cargamos todos los vehículos
            if (vehiculosFiltrados.isEmpty()) {
                vehiculosSinFiltrar = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
                });
                vehiculosFiltrados = new ArrayList<>(vehiculosSinFiltrar);  // Cargamos todos los vehículos al principio
            }

            // Filtrar los vehículos según el filtro de kilometraje
            vehiculosFiltrados = vehiculosFiltrados.stream()
                    .filter(vehiculo -> {
                        double kilometraje = vehiculo.getKm();  // Obtener el kilometraje del vehículo

                        // Aplicar el filtro de kilometraje
                        boolean filtroKilometraje = (kilometraje >= desdeValor && kilometraje <= hastaValor);

                        // Incluir el vehículo si el filtro de kilometraje es válido
                        return filtroKilometraje;
                    })
                    .collect(Collectors.toList());

            // Actualizar la vista con los vehículos filtrados
            actualizarVistaConVehiculos(vehiculosFiltrados);
        } catch (NumberFormatException e) {
            // Si hay un error en el formato de número, mostrar un mensaje
            System.out.println("Valor inválido para el filtro de kilometraje.");
        }
        return vehiculosFiltrados;  // Devolver la lista de vehículos filtrados por kilometraje
    }

    /**
     * Aplica el filtro de precio a la lista de vehículos. Filtra los vehículos
     * que se encuentren dentro del rango de precio especificado.
     *
     * @param vehi Lista de vehículos a filtrar.
     * @return La lista de vehículos filtrados por precio.
     */
    private List<Vehiculo> aplicarFiltroPrecio(List<Vehiculo> vehi) {
        try {
            // Obtener los valores desde y hasta, con validación
            double desdeValor = desdePrecio.getText().isEmpty() ? 0 : Double.parseDouble(desdePrecio.getText());
            double hastaValor = hastaPrecio.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(hastaPrecio.getText());

            // Si la lista de vehículos filtrados está vacía, cargamos todos los vehículos
            if (vehiculosFiltrados.isEmpty()) {
                vehiculosSinFiltrar = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
                });
                vehiculosFiltrados = new ArrayList<>(vehiculosSinFiltrar);  // Cargamos todos los vehículos al principio
            }

            // Filtrar los vehículos según el filtro de precio
            vehiculosFiltrados = vehiculosFiltrados.stream()
                    .filter(vehiculo -> {
                        double precio = vehiculo.getPrecio();  // Obtener el precio del vehículo

                        // Aplicar el filtro de precio
                        boolean filtroPrecio = (precio >= desdeValor && precio <= hastaValor);

                        // Incluir el vehículo si el filtro de precio es válido
                        return filtroPrecio;
                    })
                    .collect(Collectors.toList());

            // Actualizar la vista con los vehículos filtrados
            actualizarVistaConVehiculos(vehiculosFiltrados);
        } catch (NumberFormatException e) {
            // Si hay un error en el formato de número, mostrar un mensaje
            System.out.println("Valor inválido para el filtro de precio.");
        }
        return vehiculosFiltrados;  // Devolver la lista de vehículos filtrados por precio
    }

    /**
     * Crea el contenido del Popup para el filtro de colores. Los colores se
     * organizan en filas, mostrando 4 colores por cada fila en un GridPane.
     *
     * @return El VBox que contiene el GridPane con los colores para el filtro.
     */
    private VBox crearPaletaDeColores() {
        VBox vbox = new VBox(10);  // Crear un VBox con un espaciado de 10 px entre los elementos
        vbox.setStyle("-fx-padding: 10; -fx-background-color: #2e1a1a; -fx-border-color: #004fff; -fx-border-radius: 5;");

        // Crear un GridPane para organizar los colores en 4 columnas
        GridPane grid = new GridPane();
        grid.setHgap(10); // Espaciado horizontal
        grid.setVgap(10); // Espaciado vertical

        // Lista de colores que quieres mostrar en el filtro
        Color[] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PURPLE, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.BLACK, Color.WHITE, Color.GRAY, Color.BEIGE};

        // Agregar los colores al GridPane (4 por fila)
        int fila = 0;
        int columna = 0;

        // Iterar sobre cada color en la lista
        for (Color color : colores) {
            // Crear un cuadro de color y asignarle un tamaño de 30x30 px
            colorBox = new Rectangle(30, 30, color);
            colorBox.setOnMouseClicked(e -> filtrarPorColor(color)); // Acción al hacer clic sobre un color

            // Agregar el cuadro de color al GridPane en la posición adecuada
            grid.add(colorBox, columna, fila);

            columna++;  // Avanzar a la siguiente columna
            if (columna == 4) {  // Si hemos llegado a 4 colores en la fila, pasamos a la siguiente fila
                columna = 0;
                fila++;
            }
        }

        // Añadir el GridPane con los colores al VBox
        vbox.getChildren().add(grid);
        return vbox;
    }

    /**
     * Convierte el objeto de color a un nombre de color en formato String.
     * Compara el color con los valores predefinidos y devuelve el nombre
     * correspondiente.
     *
     * @param color El color a convertir.
     * @return El nombre del color en String.
     */
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

    /**
     * Filtra los vehículos por el color seleccionado. Busca en la lista de
     * vehículos aquellos que coincidan con el color seleccionado.
     *
     * @param color El color por el cual filtrar los vehículos.
     * @return La lista de vehículos filtrados por color.
     */
    private List<Vehiculo> filtrarPorColor(Color color) {
        // Convertir el color seleccionado a su nombre en String
        String nombreColor = convertirColorAString(color);
        System.out.println("El color seleccionado es: " + nombreColor);

        gridPane.getChildren().clear();  // Limpiar la vista actual antes de mostrar los resultados
        List<Vehiculo> lista = new ArrayList<>();  // Lista para almacenar los vehículos filtrados

        // Iterar sobre todos los vehículos y verificar si su color coincide con el color seleccionado
        for (Vehiculo v : vehiMostrar) {
            if (v.getColor().equalsIgnoreCase(nombreColor)) {
                lista.add(v);  // Añadir el vehículo a la lista si coincide el color
            }
        }

        // Actualizar la vista con los vehículos filtrados por color
        actualizarVistaConVehiculos(lista);
        return vehiMostrar;  // Devolver la lista de vehículos no filtrados para mantener la consistencia
    }

    /**
     * Filtra los vehículos que coinciden con la marca seleccionada. Utiliza un
     * stream para filtrar los vehículos por marca de manera insensible al caso.
     *
     * @param marcaSeleccionada La marca por la cual filtrar los vehículos.
     * @return Una lista de vehículos filtrados por marca.
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
     * Filtra los vehículos que coinciden con el modelo seleccionado. Utiliza un
     * stream para filtrar los vehículos por modelo de manera insensible al
     * caso.
     *
     * @param modeloSeleccionado El modelo por el cual filtrar los vehículos.
     * @return Una lista de vehículos filtrados por modelo.
     */
    private List<Vehiculo> filtrarPorModelo(String modeloSeleccionado) {
        // Filtrar los vehículos que coinciden con el modelo seleccionado
        List<Vehiculo> vehiculosFiltrados = vehiMostrar.stream()
                .filter(vehiculo -> vehiculo.getModelo().equalsIgnoreCase(modeloSeleccionado))
                .collect(Collectors.toList());

        // Actualizar la vista con los vehículos filtrados
        actualizarVistaConVehiculos(vehiculosFiltrados);
        return vehiMostrar; // Devolver vehiMostrar, aunque debería devolver vehiculosFiltrados
    }

    /**
     * Aplica los filtros seleccionados en los TextFields y ComboBoxes a la
     * lista de vehículos. Los filtros incluyen kilometraje, precio, marca y
     * modelo.
     *
     * @param desde El TextField que contiene el valor mínimo de kilometraje.
     * @param hasta El TextField que contiene el valor máximo de kilometraje.
     */
    private void setFilters(TextField desde, TextField hasta) {
        // Filtrar por kilometraje si el campo "desde" no está vacío
        if (!desde.getText().isEmpty()) {
            vehiMostrar = aplicarFiltroKilometraje(vehiMostrar);
        }

        // Filtrar por precio si el campo "desde" no está vacío
        if (!desde.getText().isEmpty()) {
            vehiMostrar = aplicarFiltroPrecio(vehiMostrar);
        }
        // Filtrar por marca si se ha seleccionado una marca en el ComboBox
        if (comboBox.getSelectionModel().getSelectedIndex() != -1) {
            vehiMostrar = filtrarPorMarca(comboBox.getSelectionModel().getSelectedItem());
        }
        // Filtrar por modelo si se ha seleccionado un modelo en el ComboBox de modelos
        if (comboBoxModelos.getSelectionModel().getSelectedIndex() != -1) {
            vehiMostrar = filtrarPorModelo(comboBoxModelos.getSelectionModel().getSelectedItem());
        }
        // Actualizar la vista con los vehículos filtrados
        actualizarVistaConVehiculos(vehiMostrar);
    }

    /**
     * Restablece todos los filtros aplicados y muestra nuevamente la lista
     * completa de vehículos. También limpia los campos de texto y deselecciona
     * las opciones de los ComboBoxes.
     *
     * @param event El evento de clic que desencadena el restablecimiento de los
     * filtros.
     */
    public void restablecerFiltros(MouseEvent event) {
        // Restaurar la lista completa de vehículos desde la base de datos o fuente original
        vehi = VehiculoManagerFactory.get().findAll_XML(new GenericType<List<Vehiculo>>() {
        });

        // Restablecer todas las listas de filtrado
        vehiMostrar = new ArrayList<>(vehi);  // Restaurar vehiMostrar
        vehiculosFiltrados = new ArrayList<>(vehi); // Restaurar vehiculosFiltrados
        vehiculosSinFiltrar = new ArrayList<>(vehi); // Restaurar vehiculosSinFiltrar

        // Limpiar los TextFields de kilometraje y precio
        desdeKm.clear();
        hastaKm.clear();
        desdePrecio.clear();
        hastaPrecio.clear();

        // Restablecer los ComboBoxes
        comboBox.getSelectionModel().clearSelection();
        comboBoxModelos.getSelectionModel().clearSelection();

        // Actualizar la vista con la lista restaurada
        actualizarVistaConVehiculos(vehi);
    }

    /**
     * Abre la ventana de inicio de sesión y registro (SignIn & SignUp). Muestra
     * un mensaje de advertencia antes de cerrar sesión, con opciones para
     * confirmar o cancelar.
     *
     * @param event El evento de clic que abre la ventana de inicio de sesión.
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

    /**
     * Abre la ventana de gestión de proveedores. Carga el FXML correspondiente,
     * establece el controlador y el stage, y muestra la ventana. Si ocurre un
     * error, se muestra una alerta de error.
     *
     * @param event El evento de clic que abre la ventana de gestión de
     * proveedores.
     */
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
            // Manejo de error de carga del FXML
            Logger.getLogger(TablaProveedoresController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Abre la ventana de gestión de mantenimientos. Carga el FXML
     * correspondiente, establece el controlador y el stage, y muestra la
     * ventana. Si ocurre un error, se muestra una alerta de error.
     *
     * @param event El evento de clic que abre la ventana de gestión de
     * mantenimientos.
     */
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
            // Manejo de error de carga del FXML
            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Abre la ventana de gestión de vehículos. Carga el FXML correspondiente,
     * establece el controlador y el stage, y muestra la ventana. Si ocurre un
     * error, se muestra una alerta de error.
     *
     * @param event El evento de clic que abre la ventana de gestión de
     * vehículos.
     */
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
            // Manejo de error de carga del FXML
            Logger.getLogger(NavegacionPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Abre la ventana de información adicional del vehículo. Establece el
     * vehículo seleccionado en el gestor y carga la vista correspondiente. Si
     * ocurre un error, se muestra una alerta de error.
     *
     * @param event El evento de clic que abre la ventana de información
     * adicional del vehículo.
     * @param vehiculo El vehículo cuya información se mostrará.
     */
    private void abrirVentanaInformacionVehiculo(ActionEvent event, Vehiculo vehiculo) {
        try {
            // Establecer el vehículo en el gestor de información
            VehiculoInfoExtraManager.setVehiculo(vehiculo);

            // Se carga el FXML con la información de la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/InformacionExtraVehiculoTrabajador.fxml"));
            Parent root = loader.load();

            // Obtener el controlador
            InformacionExtraVehiculoControllerTrabajador controller = loader.getController();

            // Obtener el Stage
            Stage stage = (Stage) homeBtn.getScene().getWindow();  // Obtener Stage desde cualquier nodo ya cargado
            stage.setTitle("Información de Vehículo");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/InfoVehiculo.css").toExternalForm());
            stage.setScene(scene);

            // Ajustar tamaño de la ventana
            stage.setWidth(1000);  // Ancho de la ventana
            stage.setHeight(600); // Alto de la ventana

            // Deshabilitar redimensionamiento de la ventana
            stage.setResizable(false);

            // Mostrar la ventana
            stage.show();
        } catch (IOException ex) {
            // Manejo de error de carga del FXML
            Logger.getLogger(InformacionExtraVehiculoControllerTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
        }
    }
}
