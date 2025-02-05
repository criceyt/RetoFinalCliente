package controladores;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Clase principal de la aplicación RETO. Esta clase extiende {@link javafx.application.Application} 
 * e inicializa la aplicación JavaFX cargando la pantalla de inicio de sesión.
 *
 * @author Ekain
 */
public class ApplicationClient extends javafx.application.Application {

    /**
     * Inicia la aplicación JavaFX configurando la escena y el escenario principal.
     *
     * @param stage El escenario principal de la aplicación.
     * @throws Exception Si ocurre un error durante la carga del archivo FXML.
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/SignInSignUp.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/stylesOscuro.css").toExternalForm());

        stage.setWidth(1000);
        stage.setHeight(800);
        stage.setResizable(false);
        stage.setTitle("SignIn & SignUp");

        // Agregar icono a la aplicación
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icono.png")));
        stage.setScene(scene);

        // Manejar evento de cierre de ventana con confirmación
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume(); // Evita que la ventana se cierre inmediatamente
                mostrarConfirmacionCerrar(stage);
            }
        });

        stage.show();
    }

    /**
     * Muestra una alerta de confirmación cuando el usuario intenta cerrar la aplicación.
     * Si el usuario confirma la acción, la ventana se cierra.
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
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
