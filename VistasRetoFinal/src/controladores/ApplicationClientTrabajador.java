package controladores;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Clase principal de la aplicación RETO. Inicializa la aplicación JavaFX y
 * carga la pantalla de inicio de sesión.
 *
 * @author Ekain
 */
public class ApplicationClientTrabajador extends javafx.application.Application {

    /**
     * Método que se llama al iniciar la aplicación.
     *
     * @param stage El escenario principal de la aplicación.
     * @throws Exception si ocurre un error durante la carga del archivo FXML.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Cargar el FXML
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/NavegacionPrincipalTrabajador.fxml"));

        // Crear un ScrollPane y asignar el contenido
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);  // Establecer el contenido de la vista cargada en el ScrollPane

        // Desactivar el desplazamiento horizontal
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Desactiva la barra de desplazamiento horizontal

        // Establecer el ajuste automático al ancho (solo el desplazamiento vertical está habilitado)
        scrollPane.setFitToWidth(true);  // Permite que el contenido se ajuste al ancho de la ventana
        scrollPane.setFitToHeight(true); // Permite que el contenido se ajuste a la altura de la ventana

        // Crear la escena con el ScrollPane como root
        Scene scene = new Scene(scrollPane);
        scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

        // Configurar las propiedades de la ventana
        stage.setWidth(1000);
        stage.setHeight(800);
        stage.setResizable(false);
        stage.setTitle("Navegacion de Trabajador");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icono.png")));
        stage.setScene(scene);

        // Confirmar antes de cerrar la ventana
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();  // Evita que la ventana se cierre inmediatamente
                mostrarConfirmacionCerrar(stage);
            }
        });

        // Mostrar la ventana
        stage.show();
    }

    /**
     * Muestra una alerta de confirmación al intentar cerrar la aplicación.
     *
     * @param stage El escenario principal de la aplicación.
     */
    private void mostrarConfirmacionCerrar(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Cierre");
        alert.setHeaderText("¿Estás seguro de que quieres cerrar?");
        alert.setContentText("Se perderán los cambios no guardados.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                stage.close(); // Cierra la ventana si el usuario confirma
            }
        });
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
