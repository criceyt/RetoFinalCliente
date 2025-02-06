package controladores;

import exceptions.CorreoODniRepeException;
import exceptions.SignInErrorException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
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
import javafx.scene.control.ScrollPane;
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
/////////////////////////////////////////  ELEMENTOS DE LA VENTANA Y METODOS  ///////////////////////////////////////////////////////////////

// Elementos de la ventana (FXML)
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

    /**
     * Método para recuperar la contraseña del usuario. Muestra un cuadro de
     * diálogo para ingresar el correo.
     *
     * @param event El evento de acción.
     */
    public void forgotPassword(ActionEvent event) {
        // Crear un cuadro de diálogo de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Recuperar Contraseña");
        alert.setHeaderText("Introduce tu correo electrónico");
        alert.setContentText(null);

        // Personalizar el contenido del cuadro de diálogo
        TextField emailField = new TextField();
        emailField.setPromptText("Correo electrónico");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(emailField);

        // Mostrar el cuadro de diálogo y capturar el resultado
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String email = emailField.getText();

            // Llamada al método para resetear la contraseña
            PersonaManagerFactory.get().resetPassword_XML(Persona.class, email);

            if (email.isEmpty()) {
                System.out.println("El campo de correo está vacío.");
            } else {
                System.out.println("Correo ingresado: " + email);
                // Aquí se podría agregar la lógica para enviar el correo de recuperación
            }
        }
    }

    /**
     * Método de inicialización para configurar los elementos de la ventana y
     * sus eventos.
     *
     * @param location El URL de ubicación.
     * @param resources Los recursos de internacionalización.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicialización del menú contextual
        contextMenu = new ContextMenu();
        MenuItem optionLight = new MenuItem("Tema Claro");
        MenuItem optionDark = new MenuItem("Tema Oscuro");
        MenuItem optionRetro = new MenuItem("Retro");
        contextMenu.getItems().addAll(optionLight, optionDark, optionRetro);

        // Configuración para mostrar el menú contextual
        loginPane.setOnMousePressed(this::showContextMenu);
        registerPane.setOnMousePressed(this::showContextMenu);

        // Configuración para cambiar el tema
        optionLight.setOnAction(this::changeToLightTheme);
        optionDark.setOnAction(this::changeToDarkTheme);
        optionRetro.setOnAction(this::changeToRetroTheme);

        // Inicialización de los contenedores de campos de contraseñas
        passwordFieldParent = (HBox) passwordField.getParent();
        registerPasswordFieldParent = (HBox) registerPasswordField.getParent();
        confirmPasswordFieldParent = (HBox) confirmPasswordField.getParent();

        // Asignar eventos para mostrar y ocultar contraseñas
        revealButton.setOnAction(this::togglePasswordVisibility);
        revealRegisterButton.setOnAction(this::toggleRegisterPasswordVisibility);
        revealConfirmButton.setOnAction(this::toggleConfirmPasswordVisibility);

        // Validación de campos cuando el foco cambia
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
/////////////////////////////////////////  METODOS DE NAVEGACION Y AUTENTICACION  ////////////////////////////////////////////////////////

// Abrir el formulario de Registro
    @FXML
    private void showRegister() {
        loginPane.setVisible(false);  // Ocultar el panel de login
        registerPane.setVisible(true);  // Mostrar el panel de registro
        registerPane.setTranslateX(loginPane.getWidth());  // Establecer la posición inicial de registro fuera de la vista

        // Transición para mostrar el formulario de registro
        TranslateTransition transitionIn = new TranslateTransition(Duration.millis(300), registerPane);
        transitionIn.setToX(0);  // Desplazar el formulario hacia la vista
        transitionIn.play();

        // Transición para ocultar el formulario de login
        TranslateTransition messageTransition = new TranslateTransition(Duration.millis(300), messagePane);
        messageTransition.setFromX(0);
        messageTransition.setToX(-loginPane.getWidth());  // Mover fuera de la vista
        messageTransition.play();
    }

// Abrir el formulario de Inicio de Sesion
    @FXML
    private void showLogin() {
        registerPane.setVisible(false);  // Ocultar el panel de registro
        loginPane.setVisible(true);  // Mostrar el panel de login
        loginPane.setTranslateX(-loginPane.getWidth());  // Establecer la posición inicial del login fuera de la vista

        // Transición para mostrar el formulario de login
        TranslateTransition transitionIn = new TranslateTransition(Duration.millis(300), loginPane);
        transitionIn.setToX(0);  // Desplazar el formulario hacia la vista
        transitionIn.play();

        // Transición para ocultar el mensaje
        TranslateTransition messageTransition = new TranslateTransition(Duration.millis(300), messagePane);
        messageTransition.setFromX(0);
        messageTransition.setToX(loginPane.getWidth());  // Mover fuera de la vista
        messageTransition.play();
    }

// Metodo al Pulsar el Boton de Inicio de Sesión
    @FXML
    private void inicioSesionBtn(ActionEvent event) throws UnsupportedEncodingException {

        // Recoger datos de SignIn
        String login = usernameField.getText();
        String contrasena = passwordField.getText();

        // Verificar si los campos están vacíos
        if (login.isEmpty() || contrasena.isEmpty()) {
            // Alerta si los datos son incorrectos
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Datos Vacios");
            alert.setHeaderText("Campos Incorrectos");
            alert.setContentText("Ni el campo Login ni el Campo Contraseña pueden estar vacios");
            alert.showAndWait();
        } else {
            try {
                // Encriptar la contraseña
                contrasena = ClienteRegistro.encriptarContraseña(contrasena);
                System.out.println(contrasena);

                // Codificar la contraseña
                contrasena = URLEncoder.encode(contrasena, "UTF-8");

                System.out.println(contrasena);

                // Llevar el Login y la Contraseña al servidor para obtener la persona
                Persona personaLogIn = PersonaManagerFactory.get().inicioSesionPersona(Persona.class, login, contrasena);
                try {

                    // Si la persona es un Usuario
                    if (personaLogIn instanceof Usuario) {

                        // Obtener la contraseña hash
                        String contrasenaHash = personaLogIn.getContrasena();

                        // Establecer el usuario en la sesión
                        SessionManager.setUsuario((Usuario) personaLogIn);

                        // Cargar la vista de Navegación Principal
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipal.fxml"));
                        Parent root = loader.load();

                        ScrollPane sc = new ScrollPane();
                        sc.setContent(root);

                        sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  // Desactivar la barra de desplazamiento horizontal
                        sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);  // Activar la barra de desplazamiento vertical

                        // Obtener el Stage actual
                        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Navegación Principal");

                        // Crear la nueva escena y establecerla en el Stage
                        Scene scene = new Scene(sc);
                        scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

                        // Establecer tamaño de la ventana
                        stage.setWidth(1000);  // Ancho
                        stage.setHeight(800);  // Alto

                        stage.setScene(scene);
                        stage.show();

                    } else if (personaLogIn instanceof Trabajador) {

                        try {
                            // Cargar el FXML de la vista principal de navegación del trabajador
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipalTrabajador.fxml"));
                            Parent root = loader.load();

                            // Crear un ScrollPane para envolver el contenido
                            ScrollPane sc = new ScrollPane();
                            sc.setContent(root);

                            // Configurar el ScrollPane para permitir solo desplazamiento vertical
                            sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Desactiva la barra de desplazamiento horizontal
                            sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Activa la barra de desplazamiento vertical

                            // Obtener el Stage actual y establecer la nueva escena
                            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                            stage.setTitle("Navegación Principal Trabajador");

                            // Crear la nueva escena con el ScrollPane
                            Scene scene = new Scene(sc);
                            scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

                            // Establecer las propiedades de tamaño
                            stage.setWidth(1000);  // Establece el ancho
                            stage.setHeight(800);  // Establece la altura

                            // Establecer la nueva escena en el stage y mostrarla
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException ex) {
                            Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
                            new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
                        }

                    } else {
                        // Si no es Usuario ni Trabajador, mostrar error
                        new Alert(Alert.AlertType.ERROR, "ERROR: No estas registrado ni como Usuario ni como Trabajador", ButtonType.OK).showAndWait();
                    }

                } catch (IOException ex) {
                    // Si ocurre un error al cargar la nueva ventana, mostrar mensaje de error
                    Logger.getLogger(NavegacionPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                    new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
                }
            } catch (WebApplicationException ex) {
                Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SignInErrorException ex) {
                Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex.getMessage());

                // Mostrar alerta por error de inicio de sesión
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de inicio de sesión");
                alert.setHeaderText(null);  // No se muestra encabezado
                alert.setContentText("El usuario no existe o los datos son incorrectos. Por favor, verifica e intenta nuevamente.");
                alert.showAndWait();
            }
        }
    }
/////////////////////////////////////////  METODO DE REGISTRO DE USUARIO  ////////////////////////////////////////////////////////

// Método al Pulsar el Boton de Registro
    @FXML
    private void registroBtn() {

        // Sacamos todos los datos de los campos y los asignamos a variables
        String nombreApellido = nombreyApellidoField.getText();
        String dni = dniField.getText();
        String email = emailField.getText();
        String direccion = direccionField.getText();
        String telefonoStr = telefonoField.getText();
        String codigoPostalStr = codigoPostalField.getText();

        // Obtener la contraseña
        String password = registerPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validación de los datos ingresados
        if (!nombreyApellidoField.getText().isEmpty() && validarSoloLetrasNombre(nombreApellido) && !direccionField.getText().isEmpty() && validarDireccion(direccion) && !telefonoField.getText().isEmpty() && esTelefonoCorrecto(telefonoStr) && !codigoPostalField.getText().isEmpty() && esPostalCorrecto(codigoPostalStr) && !dniField.getText().isEmpty() && validarDni(dni) && !emailField.getText().isEmpty() && esCorreoValido(email) && !registerPasswordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty() && password.equalsIgnoreCase(confirmPassword)) {

            Integer telefono = Integer.parseInt(telefonoStr);
            Integer codigoPostal = Integer.parseInt(codigoPostalStr);

            // Encriptar la contraseña antes de guardarla
            String encryptedPassword = ClienteRegistro.encriptarContraseña(password);

            System.out.println(encryptedPassword);

            // Obtener si el usuario se registra como premium
            boolean esPremium = activoCheckBox.isSelected();
            Date fechaRegistro = new Date();

            // Crear un nuevo objeto Usuario
            Usuario usuarioNuevo = new Usuario();

            // Insertar los datos recogidos en el objeto Usuario
            usuarioNuevo.setNombreCompleto(nombreApellido);
            usuarioNuevo.setDni(dni);
            usuarioNuevo.setEmail(email);
            usuarioNuevo.setDireccion(direccion);
            usuarioNuevo.setTelefono(telefono);
            usuarioNuevo.setCodigoPostal(codigoPostal);
            usuarioNuevo.setContrasena(encryptedPassword);  // Asignar la contraseña encriptada
            usuarioNuevo.setFechaRegistro(fechaRegistro);
            usuarioNuevo.setPremium(esPremium);

            // Verificar si el usuario desea registrarse como Premium
            if (esPremium) {
                // Mostrar un alert de confirmación
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Confirmación de Premium");
                alert.setHeaderText("¿Estás seguro de que deseas hacerte Premium?");
                alert.setContentText("Esta opción puede cambiarse desde la opción de Perfil.");

                // Crear un botón personalizado "Cancelar"
                ButtonType cancelButton = new ButtonType("Cancelar");

                // Agregar los botones OK y Cancelar
                alert.getButtonTypes().setAll(ButtonType.OK, cancelButton);

                // Mostrar el alert y esperar la respuesta del usuario
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Si el usuario confirma la opción Premium, continúa con la creación de usuario
                        refactorizacionDeCreacionDeUser(usuarioNuevo);
                    } else if (response == cancelButton) {
                        // Si el usuario cancela, muestra un mensaje
                        System.out.println("El usuario ha cancelado la opción Premium.");
                    }
                });
            } else {
                // Si no es Premium, continúa con la creación de usuario directamente
                refactorizacionDeCreacionDeUser(usuarioNuevo);
            }

        } else {
            // Si los datos no son correctos, muestra un alert
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error en el Registro");
            alert.setHeaderText(null);
            alert.setContentText("Introduzca bien los datos en los Campos de ROJO\n"
                    + "Los formatos introducidos no son correctos\n"
                    + "Correo: Debe de tener Formato de Correo\n"
                    + "Contraseñas: Las contraseñas deben de ser Iguales y deben de tener más de 8 caracteres y al menos una Mayúscula");

            alert.showAndWait();
        }
    }

// Refactorización para crear al usuario
    private void refactorizacionDeCreacionDeUser(Usuario usuarioNuevo) {

        try {
            // Mandar el Usuario al Server (suponiendo que tienes el método adecuado para esto)
            UsuarioManagerFactory.get().create_XML(usuarioNuevo);

            // Panel informativo de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Te has registrado!");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenido a Nuestro Concesionario ¡Inicia Sesion y no te Pierdas nada!");

            alert.showAndWait();

            // Ocultar el formulario de registro y mostrar el de inicio de sesión
            registerPane.setVisible(false);
            loginPane.setVisible(true);
            usernameField.setText(usuarioNuevo.getEmail());
        } catch (CorreoODniRepeException ex) {
            // Manejar el error si el correo o el DNI ya están registrados
            Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex.getMessage());

            // Crear un alert de tipo ERROR para mostrar que el correo ya existe
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de registro");
            alert.setHeaderText("Correo electrónico o DNI ya registrado");
            alert.setContentText("El correo o el DNI que has introducido ya existe en la base de datos. Por favor, utiliza otro correo o DNI.");

            alert.showAndWait();
        }

    }
//////////////////////////////////////////  MÉTODOS DE VISIBILIDAD DE CONTRASEÑA Y CAMBIO DE TEMA  //////////////////////////////////

// Método para hacer visible la Contraseña o para dejar de hacerla visible
    private void togglePasswordVisibility(ActionEvent event) {
        if ("Mostrar".equals(revealButton.getText())) {
            passwordField.setVisible(false);  // Ocultar campo de contraseña
            plainTextField = new TextField(passwordField.getText());  // Crear un campo de texto con el valor de la contraseña
            plainTextField.setVisible(true);  // Hacer visible el campo de texto

            // Reemplazar el campo de contraseña con el campo de texto plano
            passwordFieldParent.getChildren().set(0, plainTextField);
            revealButton.setText("Ocultar");  // Cambiar texto del botón

            // Sincronizar el texto entre los campos
            plainTextField.textProperty().addListener((observable, oldValue, newValue) -> passwordField.setText(newValue));
        } else {
            passwordField.setText(plainTextField.getText());  // Transferir el texto del campo de texto a la contraseña
            passwordField.setVisible(true);  // Hacer visible nuevamente el campo de contraseña
            passwordFieldParent.getChildren().set(0, passwordField);  // Reemplazar el campo de texto con el campo de contraseña
            revealButton.setText("Mostrar");  // Cambiar texto del botón
            plainTextField = null;  // Limpiar el campo de texto plano
        }
    }

// Método para hacer visible la Contraseña de registro o para dejar de hacerla visible
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

// Método para hacer visible la Contraseña de confirmación o para dejar de hacerla visible
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

// Menu Contextual para cambiar el tema
    private static ContextMenu createThemeMenu(Scene scene) {
        ContextMenu menu = new ContextMenu();

        MenuItem darkTheme = new MenuItem("Tema Oscuro");
        MenuItem lightTheme = new MenuItem("Tema Claro");
        MenuItem retroTheme = new MenuItem("Tema Retro");

        // Asignar acciones a los elementos del menú
        darkTheme.setOnAction(event -> changeTheme("/css/stylesLogout_Oscuro.css", scene));
        lightTheme.setOnAction(event -> changeTheme("/css/stylesLogout_Claro.css", scene));
        retroTheme.setOnAction(event -> changeTheme("/css/stylesLogout_Retro.css", scene));

        menu.getItems().addAll(darkTheme, lightTheme, retroTheme);

        return menu;
    }

// Método para abrir el menú contextual al hacer clic derecho
    private void showContextMenu(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            contextMenu.show((Node) event.getSource(), event.getScreenX(), event.getScreenY());
        }
    }

// Método para cambiar el fondo a un tema claro
    private void changeToLightTheme(ActionEvent e) {
        Scene scene = loginPane.getScene();
        if (scene != null) {
            // Limpiar los estilos previos y agregar el nuevo tema claro
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/stylesClaro.css").toExternalForm());
        } else {
            System.out.println("La escena es null");
        }
    }
//////////////////////////////////////////  CAMBIO DE TEMAS Y VALIDACIONES  //////////////////////////////////

// Método para poner el fondo en tema oscuro
    private void changeToDarkTheme(ActionEvent e) {
        Scene scene = loginPane.getScene();
        if (scene != null) {
            scene.getStylesheets().clear();  // Limpiar los estilos previos
            scene.getStylesheets().add(getClass().getResource("/css/stylesOscuro.css").toExternalForm());  // Cargar el tema oscuro
        } else {
            System.out.println("La escena es null");
        }
    }

// Método para poner el fondo en tema retro
    private void changeToRetroTheme(ActionEvent e) {
        Scene scene = loginPane.getScene();
        if (scene != null) {
            scene.getStylesheets().clear();  // Limpiar los estilos previos
            scene.getStylesheets().add(getClass().getResource("/css/stylesRetro.css").toExternalForm());  // Cargar el tema retro
        } else {
            System.out.println("La escena es null");
        }
    }

// Método para cambiar el tema general según el archivo CSS proporcionado
    private static void changeTheme(String themeFile, Scene scene) {
        scene.getStylesheets().clear();  // Limpiar los estilos previos
        scene.getStylesheets().add(SignController.class.getResource(themeFile).toExternalForm());  // Cargar el tema especificado
    }

/////////////////////////////////////  VALIDACIONES DE CAMPOS DE REGISTRO  //////////////////////////////////
// Validación para que solo se introduzcan letras (nombre y apellidos)
    public boolean validarSoloLetrasNombre(String nombreyApellidos) {
        return nombreyApellidos.matches("[a-zA-Z\\s]+");
    }

// Validación de contraseña (al menos 8 caracteres y al menos una mayúscula)
    public boolean esContrasenaValida(String registerPasswordField) {
        return registerPasswordField.matches("^(?=.*[A-Z]).{8,}$");
    }

// Validación de la confirmación de la contraseña (debe ser igual a la contraseña original)
    public boolean esConfirmarContrasenaValida(String confirmPasswordField) {
        return confirmPasswordField.matches("^(?=.*[A-Z]).{8,}$");
    }

// Validación de DNI (formato: 8 dígitos seguidos de una letra)
    public boolean validarDni(String dni) {
        return dni.matches("\\d{8}[a-zA-Z]");
    }

// Validación de correo electrónico
    private boolean esCorreoValido(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return email.matches(regex);
    }

// Validación de teléfono (debe ser de 9 dígitos)
    private boolean esTelefonoCorrecto(String telefono) {
        return telefono.matches("\\d{9}");
    }

// Validación de dirección (solo letras, números, espacios y caracteres especiales permitidos)
    public boolean validarDireccion(String direccion) {
        return direccion.matches("[a-zA-Z0-9\\s,.-]+");
    }

// Validación del código postal (5 dígitos)
    private boolean esPostalCorrecto(String codigoPostal) {
        return codigoPostal.matches("\\d{5}");
    }

// Método para validar un campo de texto en función de su tipo
    private void validateField(TextField field) {
        // Validar el campo de correo electrónico
        if (field == emailField) {
            if (field.getText().isEmpty() || !esCorreoValido(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validar el campo de nombre y apellidos
        if (field == nombreyApellidoField) {
            if (field.getText().isEmpty() || !validarSoloLetrasNombre(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validar el campo de teléfono
        if (field == telefonoField) {
            if (field.getText().isEmpty() || !esTelefonoCorrecto(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validar el campo de DNI
        if (field == dniField) {
            if (field.getText().isEmpty() || !validarDni(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validar el campo de código postal
        if (field == codigoPostalField) {
            if (field.getText().isEmpty() || !esPostalCorrecto(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validar el campo de dirección
        if (field == direccionField) {
            if (field.getText().isEmpty() || !validarDireccion(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validar el campo de contraseña
        if (field == registerPasswordField) {
            if (field.getText().isEmpty() || !esContrasenaValida(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validar el campo de confirmación de contraseña
        if (field == confirmPasswordField) {
            if (field.getText().isEmpty() || !esConfirmarContrasenaValida(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }
    }
}
