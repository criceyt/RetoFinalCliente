package controladores;

import java.io.IOException;
import java.net.URL;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logica.PersonaManagerFactory;
import logica.UsuarioManagerFactory;
import modelo.Persona;
import modelo.Usuario;

public class SignController implements Initializable {

    // Elementos de la Ventana
    @FXML
    private VBox loginPane;

    @FXML
    private VBox registerPane;

    @FXML
    private Label messageLabel;

    @FXML
    private VBox messagePane;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField telefonoField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nombreyApellidoField;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private TextField dniField;

    @FXML
    private TextField codigoPostalField;

    @FXML
    private TextField direccionField;

    @FXML
    private TextField emailField;

    @FXML
    private Button revealButton;

    @FXML
    private Button revealRegisterButton;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Scene scene;

    @FXML
    private Button revealConfirmButton;

    @FXML
    private TextField plainTextField;

    @FXML
    private TextField plainRegisterTextField;

    @FXML
    private TextField plainConfirmTextField;

    @FXML
    private HBox passwordFieldParent;

    @FXML
    private HBox registerPasswordFieldParent;

    @FXML
    private HBox confirmPasswordFieldParent;

    @FXML
    private CheckBox activoCheckBox;

    // Atributos
    private ContextMenu contextMenu;
    private boolean isDarkTheme = true;

    // Metodo Initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contextMenu = new ContextMenu();
        MenuItem optionLight = new MenuItem("Tema Claro");
        MenuItem optionDark = new MenuItem("Tema Oscuro");
        MenuItem optionRetro = new MenuItem("Retro");
        contextMenu.getItems().addAll(optionLight, optionDark, optionRetro);

        loginPane.setOnMousePressed(this::showContextMenu);
        registerPane.setOnMousePressed(this::showContextMenu);

        optionLight.setOnAction(this::changeToLightTheme);
        optionDark.setOnAction(this::changeToDarkTheme);
        optionRetro.setOnAction(this::changeToRetroTheme);

        passwordFieldParent = (HBox) passwordField.getParent();
        registerPasswordFieldParent = (HBox) registerPasswordField.getParent();
        confirmPasswordFieldParent = (HBox) confirmPasswordField.getParent();

        revealButton.setOnAction(this::togglePasswordVisibility);
        revealRegisterButton.setOnAction(this::toggleRegisterPasswordVisibility);
        revealConfirmButton.setOnAction(this::toggleConfirmPasswordVisibility);

        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(emailField);
            }
        });

        nombreyApellidoField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(nombreyApellidoField);
            }
        });

        dniField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(dniField);
            }
        });

        telefonoField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(telefonoField);
            }
        });

        codigoPostalField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(codigoPostalField);
            }
        });

        direccionField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(direccionField);
            }
        });

        registerPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(registerPasswordField);
            }
        });

        confirmPasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(confirmPasswordField);
            }
        });
    }

    // Abrir el formulario de Registro
    @FXML
    private void showRegister() {
        loginPane.setVisible(false);
        registerPane.setVisible(true);
        registerPane.setTranslateX(loginPane.getWidth());

        TranslateTransition transitionIn = new TranslateTransition(Duration.millis(300), registerPane);
        transitionIn.setToX(0);
        transitionIn.play();

        TranslateTransition messageTransition = new TranslateTransition(Duration.millis(300), messagePane);
        messageTransition.setFromX(0);
        messageTransition.setToX(-loginPane.getWidth());
        messageTransition.play();
    }

    // Abrir el formulario de Inicio de Sesion
    @FXML
    private void showLogin() {
        registerPane.setVisible(false);
        loginPane.setVisible(true);
        loginPane.setTranslateX(-loginPane.getWidth());

        TranslateTransition transitionIn = new TranslateTransition(Duration.millis(300), loginPane);
        transitionIn.setToX(0);
        transitionIn.play();

        TranslateTransition messageTransition = new TranslateTransition(Duration.millis(300), messagePane);
        messageTransition.setFromX(0);
        messageTransition.setToX(loginPane.getWidth());
        messageTransition.play();
    }

    // Meotodo al Pulsar el Boton
    @FXML
    private void inicioSesionBtn(ActionEvent event) {

        // Recoger datos de SignIn
        String login = usernameField.getText();
        String contrasena = passwordField.getText();

        System.out.println(login);
        System.out.println(contrasena);
        
        try {

            PersonaManagerFactory.get().inicioSesionPersona(Persona.class, login, contrasena);

            // Se carga el FXML con la información de la vista viewSignUp.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipal.fxml"));
            Parent root = loader.load();

            NavegacionPrincipalController controler = loader.getController();

            // Obtener el Stage desde el nodo que disparó el evento.
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setTitle("Navegacion Principal");
            // Se crea un nuevo objeto de la clase Scene con el FXML cargado.
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
            // Se muestra en la ventana el Scene creado.
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Si salta una IOException significa que ha habido algún 
            // problema al cargar el FXML o al intentar llamar a la nueva 
            // ventana, por lo que se mostrará un Alert con el mensaje 
            // "Error en la sincronización de ventanas, intentalo más tarde".
            Logger.getLogger(NavegacionPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();

            System.out.println("ERROR en el Login");
            new Alert(Alert.AlertType.ERROR, "Error, El Usuario o no existe o no coincide el Login y la Password", ButtonType.OK).showAndWait();
        }

    }

    // Meotodo al Pulsar el Boton
    @FXML
    private void registroBtn() {

    }

    // Metodo para hacer visible la Contraseña o para dejar de hacerla Visible
    private void togglePasswordVisibility(ActionEvent event) {
        if ("Mostrar".equals(revealButton.getText())) {
            passwordField.setVisible(false);
            plainTextField = new TextField(passwordField.getText());
            plainTextField.setVisible(true);

            passwordFieldParent.getChildren().set(0, plainTextField);
            revealButton.setText("Ocultar");

            plainTextField.textProperty().addListener((observable, oldValue, newValue) -> passwordField.setText(newValue));
        } else {
            passwordField.setText(plainTextField.getText());
            passwordField.setVisible(true);
            passwordFieldParent.getChildren().set(0, passwordField);
            revealButton.setText("Mostrar");
            plainTextField = null;
        }
    }

    // Metodo para hacer visible la Contraseña o para dejar de hacerla Visible
    private void toggleRegisterPasswordVisibility(ActionEvent event) {
        if ("Mostrar".equals(revealRegisterButton.getText())) {
            registerPasswordField.setVisible(false);
            plainRegisterTextField = new TextField(registerPasswordField.getText());
            plainRegisterTextField.setVisible(true);

            registerPasswordFieldParent.getChildren().set(0, plainRegisterTextField);
            revealRegisterButton.setText("Ocultar");

            plainRegisterTextField.textProperty().addListener((observable, oldValue, newValue) -> registerPasswordField.setText(newValue));
        } else {
            registerPasswordField.setText(plainRegisterTextField.getText());
            registerPasswordField.setVisible(true);
            registerPasswordFieldParent.getChildren().set(0, registerPasswordField);
            revealRegisterButton.setText("Mostrar");
            plainRegisterTextField = null;
        }
    }

    // Metodo para hacer visible la Contraseña o para dejar de hacerla Visible
    private void toggleConfirmPasswordVisibility(ActionEvent event) {
        if ("Mostrar".equals(revealConfirmButton.getText())) {
            confirmPasswordField.setVisible(false);
            plainConfirmTextField = new TextField(confirmPasswordField.getText());
            plainConfirmTextField.setVisible(true);

            confirmPasswordFieldParent.getChildren().set(0, plainConfirmTextField);
            revealConfirmButton.setText("Ocultar");

            plainConfirmTextField.textProperty().addListener((observable, oldValue, newValue) -> confirmPasswordField.setText(newValue));
        } else {
            confirmPasswordField.setText(plainConfirmTextField.getText());
            confirmPasswordField.setVisible(true);
            confirmPasswordFieldParent.getChildren().set(0, confirmPasswordField);
            revealConfirmButton.setText("Mostrar");
            plainConfirmTextField = null;
        }
    }

    // Menu Contextual
    private static ContextMenu createThemeMenu(Scene scene) {
        ContextMenu menu = new ContextMenu();

        MenuItem darkTheme = new MenuItem("Tema Oscuro");
        MenuItem lightTheme = new MenuItem("Tema Claro");
        MenuItem retroTheme = new MenuItem("Tema Retro");

        darkTheme.setOnAction(event -> changeTheme("/css/stylesLogout_Oscuro.css", scene));
        lightTheme.setOnAction(event -> changeTheme("/css/stylesLogout_Claro.css", scene));
        retroTheme.setOnAction(event -> changeTheme("/css/stylesLogout_Retro.css", scene));

        menu.getItems().addAll(darkTheme, lightTheme, retroTheme);

        return menu;
    }

    // Abrir el menu contextual
    private void showContextMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            contextMenu.show((Node) event.getSource(), event.getScreenX(), event.getScreenY());
        }
    }

    // Poner el fondo claro
    private void changeToLightTheme(ActionEvent e) {
        Scene scene = loginPane.getScene();
        if (scene != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/stylesClaro.css").toExternalForm());
        } else {
            System.out.println("La escena es null");
        }
    }

    // Poner el fondo oscuro
    private void changeToDarkTheme(ActionEvent e) {
        Scene scene = loginPane.getScene();
        if (scene != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/stylesOscuro.css").toExternalForm());
        } else {
            System.out.println("La escena es null");
        }
    }

    // Poner el fondo retro
    private void changeToRetroTheme(ActionEvent e) {
        Scene scene = loginPane.getScene();
        if (scene != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/stylesRetro.css").toExternalForm());
        } else {
            System.out.println("La escena es null");
        }
    }

    private static void changeTheme(String themeFile, Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(SignController.class.getResource(themeFile).toExternalForm());
    }

    /////////////////////////////////////////  VALIDACIONES DE REGISTRO  ///////////////////////////////////////////////////////////////
    // Validacion para que solo Pueda introducir Letras
    public boolean validarSoloLetrasNombre(String nombreyApellidos) {
        return nombreyApellidos.matches("[a-zA-Z]+");
    }

    // Validacion para que solo Pueda introducir Letras
    public boolean validarDni(String dni) {
        return dni.matches("\\d{8}[a-zA-Z]");
    }

    // Validacion de Correo Electronico
    private boolean esCorreoValido(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return email.matches(regex);
    }

    // Validacion de que el Telefono sean 9 Numeros
    private boolean esTelefonoCorrecto(String telefono) {
        return telefono.matches("\\d{9}");
    }

    // Validacion para que solo Pueda introducir Letras y Numeros (Caracteres especiones no (&$#"@))
    public boolean validarDireccion(String direccion) {
        return direccion.matches("[a-zA-Z][0-9]");
    }

    // Validacion de que el codigo Postal sean 5 Numeros
    private boolean esPostalCorrecto(String codigoPostal) {
        return codigoPostal.matches("\\d{5}");
    }

    private void validateField(TextField field) {
        if (field == emailField) {
            if (field.getText().isEmpty() || !esCorreoValido(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }
        if (field == nombreyApellidoField || !validarSoloLetrasNombre(field.getText())) {
            if (field.getText().isEmpty()) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }
        if (field == telefonoField || !esTelefonoCorrecto(field.getText())) {
            if (field.getText().isEmpty()) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }
        if (field == dniField) {
            if (field.getText().isEmpty() || !validarDni(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        if (field == codigoPostalField) {
            if (field.getText().isEmpty() || !esPostalCorrecto(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }
        if (field == direccionField) {
            if (field.getText().isEmpty() || !validarDireccion(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }
    }
}
