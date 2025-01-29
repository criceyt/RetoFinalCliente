package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
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
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.ws.rs.WebApplicationException;
import logica.ClienteRegistro;
import logica.SessionManager;
import logica.PersonaManagerFactory;
import logica.UsuarioManagerFactory;
import modelo.Persona;
import modelo.Trabajador;
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
    private boolean datosCorrectos = true;

    public void forgotPassword(ActionEvent event) {
        // Crear un cuadro de diálogo
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Recuperar Contraseña");
        alert.setHeaderText("Introduce tu correo electrónico");
        alert.setContentText(null);

        // Personalizar el contenido del diálogo
        TextField emailField = new TextField();
        emailField.setPromptText("Correo electrónico");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(emailField);

        // Mostrar el diálogo y capturar el resultado
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String email = emailField.getText();

            try {
                // Llamada al método findEmailPersona
                PersonaManagerFactory.get().resetPassword_XML(Persona.class, email);

            } catch (WebApplicationException e) {
                // Maneja las excepciones relacionadas con WebApplicationException
                System.out.println("No se encontró el correo electrónico: " + email);
            } catch (Exception e) {
                // Si se produce otro error inesperado
                System.out.println("Se produjo un error inesperado: " + e.getMessage());
            }

            if (email.isEmpty()) {
                System.out.println("El campo de correo está vacío.");
            } else {
                System.out.println("Correo ingresado: " + email);
                // Aquí puedes agregar la lógica para enviar el correo de recuperación
            }
        }
    }

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

        if (login.isEmpty() || contrasena.isEmpty()) {
            // Alerta que indica que los datos no son correctos
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Datos Vacios");
            alert.setHeaderText("Campos Incorrectos");
            alert.setContentText("Ni el campo Login ni el Campo Contraseña pueden estar vacios");
            alert.showAndWait();

        } else {
            contrasena = ClienteRegistro.encriptarContraseña(contrasena);
            System.out.println(contrasena);
            // Llevar la Password y el login al server para que retorne una 
                Persona personaLogIn = PersonaManagerFactory.get().inicioSesionPersona(Persona.class, login, contrasena);

            try {

                // Si la Persona es Usuario entra en este metido Sino va al Otro
                if (personaLogIn instanceof Usuario) {

                    SessionManager.setUsuario((Usuario) personaLogIn);

                    // Se carga el FXML con la información de la vista viewSignUp.
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipal.fxml"));
                    Parent root = loader.load();

                    NavegacionPrincipalController controller = loader.getController();
                    //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                    // Llevar el Usuario a Navegacion Principal
                    //controller.setUsuario((Usuario) personaLogIn);
                    //System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                    // Obtener el Stage desde el nodo que disparó el evento.
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                    stage.setTitle("Navegacion Principal");
                    // Se crea un nuevo objeto de la clase Scene con el FXML cargado.
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/CSSTabla.css").toExternalForm());
                    // Se muestra en la ventana el Scene creado.
                    stage.setScene(scene);
                    stage.show();

                } else if (personaLogIn instanceof Trabajador) {

                    // Se carga el FXML con la información de la vista viewSignUp.
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipalTrabajador.fxml"));
                    Parent root = loader.load();

                    NavegacionPrincipalTrabajadorController controler = loader.getController();

                    // Obtener el Stage desde el nodo que disparó el evento.
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                    stage.setTitle("Navegacion Principal Trabajador");
                    // Se crea un nuevo objeto de la clase Scene con el FXML cargado.
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());
                    // Se muestra en la ventana el Scene creado.
                    stage.setScene(scene);
                    stage.show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "ERROR: No estas registrado ni como Usuario ni como Tarbajador", ButtonType.OK).showAndWait();

                }

            } catch (IOException ex) {
                // Si salta una IOException significa que ha habido algún 
                // problema al cargar el FXML o al intentar llamar a la nueva 
                // ventana, por lo que se mostrará un Alert con el mensaje 
                // "Error en la sincronización de ventanas, intentalo más tarde".
                Logger.getLogger(NavegacionPrincipalController.class
                        .getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
            }
        }

    }

    // Meotodo al Pulsar el Boton
    @FXML
    private void registroBtn() {

        if (datosCorrectos) {

            // Sacamos todos los datos y los metemos en variables
            String nombreApellido = nombreyApellidoField.getText();
            String dni = dniField.getText();
            String email = emailField.getText();
            String direccion = direccionField.getText();
            // Tel
            String telefonoStr = telefonoField.getText();
            Integer telefono = Integer.parseInt(telefonoStr);
            // CP
            String codigoPostalStr = codigoPostalField.getText();
            Integer codigoPostal = Integer.parseInt(codigoPostalStr);

            // Obtener la contraseña
            String password = registerPasswordField.getText();

            // Encriptar la contraseña antes de guardarla
            String encryptedPassword = ClienteRegistro.encriptarContraseña(password);

            System.out.println(encryptedPassword);

            boolean esPremium = activoCheckBox.isSelected();
            Date fechaRegistro = new Date();

            Usuario usuarioNuevo = new Usuario();

            // Insertar los datos recogidos en el usuario Nuevo
            usuarioNuevo.setNombreCompleto(nombreApellido);
            usuarioNuevo.setDni(dni);
            usuarioNuevo.setEmail(email);
            usuarioNuevo.setDireccion(direccion);
            usuarioNuevo.setTelefono(telefono);
            usuarioNuevo.setCodigoPostal(codigoPostal);
            usuarioNuevo.setContrasena(encryptedPassword);  // Asignar la contraseña encriptada
            usuarioNuevo.setFechaRegistro(fechaRegistro);
            usuarioNuevo.setPremium(esPremium);

            // Mandar el Usuario al Server (suponiendo que tienes el método adecuado para esto)
            UsuarioManagerFactory.get().create_XML(usuarioNuevo);

            // Panel informativo de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Te has registrado!");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenido a Nuestro Concesionario ¡Inicia Sesion y no te Pierdas nada!");

            // Mostrar el alert
            alert.showAndWait();

            registerPane.setVisible(false);
            loginPane.setVisible(true);

        } else {
            // Crear un alert para indicar que los datos no son correctos
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en el Registro");
            alert.setHeaderText(null);
            alert.setContentText("Introduzca bien los datos en los Campos de ROJO\n"
                    + "Los formatos introducidos no son correctos\n"
                    + "Correo: Debe de tener Formato de Correo\n"
                    + "Contraseñas: Las contraseñas deben de ser Iguales y deben de tener más de 8 caracteres y al menos una Mayúscula");

            // Mostrar el alert
            alert.showAndWait();
        }

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
        scene
                .getStylesheets().add(SignController.class
                        .getResource(themeFile).toExternalForm());
    }

    /////////////////////////////////////////  VALIDACIONES DE REGISTRO  ///////////////////////////////////////////////////////////////
    // Validacion para que solo Pueda introducir Letras
    public boolean validarSoloLetrasNombre(String nombreyApellidos) {
        return nombreyApellidos.matches("[a-zA-Z\\s]+");  // Permite letras y espacios
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
        return direccion.matches("[a-zA-Z0-9\\s,.-]+");
    }

    // Validacion de que el codigo Postal sean 5 Numeros
    private boolean esPostalCorrecto(String codigoPostal) {
        return codigoPostal.matches("\\d{5}");
    }

    private void validateField(TextField field) {

        if (field == emailField) {
            if (field.getText().isEmpty() || !esCorreoValido(field.getText())) {
                field.setStyle("-fx-border-color: red;");
                datosCorrectos = false;
            } else {
                field.setStyle("-fx-border-color: transparent;");
                datosCorrectos = true;
            }
        }

        if (field == nombreyApellidoField || !validarSoloLetrasNombre(field.getText())) {
            if (field.getText().isEmpty()) {
                field.setStyle("-fx-border-color: red;");
                datosCorrectos = false;
            } else {
                field.setStyle("-fx-border-color: transparent;");
                datosCorrectos = true;
            }
        }

        if (field == telefonoField || !esTelefonoCorrecto(field.getText())) {
            if (field.getText().isEmpty()) {
                field.setStyle("-fx-border-color: red;");
                datosCorrectos = false;
            } else {
                field.setStyle("-fx-border-color: transparent;");
                datosCorrectos = true;
            }
        }

        if (field == dniField) {
            if (field.getText().isEmpty() || !validarDni(field.getText())) {
                field.setStyle("-fx-border-color: red;");
                datosCorrectos = false;
            } else {
                field.setStyle("-fx-border-color: transparent;");
                datosCorrectos = true;
            }
        }

        if (field == codigoPostalField) {
            if (field.getText().isEmpty() || !esPostalCorrecto(field.getText())) {
                field.setStyle("-fx-border-color: red;");
                datosCorrectos = false;
            } else {
                field.setStyle("-fx-border-color: transparent;");
                datosCorrectos = true;
            }
        }

        if (field == direccionField) {
            if (field.getText().isEmpty() || !validarDireccion(field.getText())) {
                field.setStyle("-fx-border-color: red;");
                datosCorrectos = false;
            } else {
                field.setStyle("-fx-border-color: transparent;");
                datosCorrectos = true;
            }
        }
    }

}
