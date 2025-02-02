package controladores;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javax.ws.rs.ClientErrorException;
import logica.PersonaManagerFactory;
import logica.SessionManager;
import logica.UsuarioManagerFactory;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author urkizu
 */
public class PerfilController implements Initializable {

    // Elementos de la Ventana
    @FXML
    private Button homeBtn;

    @FXML
    private Button tusVehiculosBtn;

    @FXML
    private Button cerrarSesionBtn;

    @FXML
    private Button volverBtn;

    @FXML
    private Button guardarBtn;

    @FXML
    private TextField textFieldDni;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldTelefono;

    @FXML
    private TextField textFieldDireccion;

    @FXML
    private TextField textFieldCodigoPostal;

    @FXML
    private TextField textFieldFechaRegistro;

    @FXML
    private CheckBox chkTerms;

    // Atributo para Cargar los datos del Usuario
    private Usuario usuario;
    private boolean datosCorrectos;

    // Metodo Initialize
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textFieldNombre.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(textFieldNombre);
            }
        });

        textFieldDireccion.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(textFieldDireccion);
            }
        });

        textFieldTelefono.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(textFieldTelefono);
            }
        });

        textFieldCodigoPostal.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateField(textFieldCodigoPostal);
            }
        });

        //Se añaden los listeners a todos los botones.
        homeBtn.setOnAction(this::irAtras);
        tusVehiculosBtn.setOnAction(this::abrirVentanaTusVehiculos);
        volverBtn.setOnAction(this::irAtras);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        guardarBtn.setOnAction(this::guardarDatosUsuario);

        // Acceder al usuario desde SessionManager
        this.usuario = SessionManager.getUsuario();

        // Deshabilitar campos DNI, Email y Fecha Registro
        textFieldDni.setDisable(true);
        textFieldEmail.setDisable(true);
        textFieldFechaRegistro.setDisable(true);

        // Metemos todos los datos del Usuario en su sitios correspondiente
        textFieldDni.setText(usuario.getDni());
        textFieldEmail.setText(usuario.getEmail());
        textFieldNombre.setText(usuario.getNombreCompleto());
        textFieldDireccion.setText(usuario.getDireccion());

        // Este integer necesita un Parseo
        int telefonoInt = usuario.getTelefono();
        textFieldTelefono.setText(String.valueOf(telefonoInt));

        int codigoPostalInt = usuario.getCodigoPostal();
        textFieldCodigoPostal.setText(String.valueOf(codigoPostalInt));

        // Parseo de la fecha
        Date fechaString = usuario.getFechaRegistro();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = sdf.format(fechaString);
        textFieldFechaRegistro.setText(String.valueOf(fechaFormateada));

        // ComboBox
        chkTerms.setSelected(usuario.isPremium());

        System.out.println("Ventana inicializada correctamente.");
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

// Boton HOME para volver atras
    private void irAtras(ActionEvent event) {

        if (verificarCambiosNoGuardados()) {
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
    }

    // Abrir Ventana Solicitar Mantenimiento
    private void abrirVentanaTusVehiculos(ActionEvent event) {
        if (verificarCambiosNoGuardados()) {
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
    }

    // Guardar los datos que ha introducido el Usuario para Modificar sus datos
    private void guardarDatosUsuario(ActionEvent event) {

        // Obtener los nuevos valores desde los campos de texto
        String nuevoNombre = textFieldNombre.getText();
        String nuevaDireccion = textFieldDireccion.getText();
        String nuevoTelefono = textFieldTelefono.getText();
        String nuevoCodigoPostal = textFieldCodigoPostal.getText();
        boolean isPremiumNuevo = chkTerms.isSelected();

        if (!textFieldNombre.getText().isEmpty() && validarSoloLetrasNombre(nuevoNombre) && !textFieldDireccion.getText().isEmpty() && validarDireccion(nuevaDireccion) && !textFieldTelefono.getText().isEmpty() && esTelefonoCorrecto(nuevoTelefono) && !textFieldCodigoPostal.getText().isEmpty() && esPostalCorrecto(nuevoCodigoPostal)) {
            // Si alguno de los datos son nuevos cambiamos los datos
            if (!nuevoNombre.equalsIgnoreCase(usuario.getNombreCompleto()) || !nuevaDireccion.equalsIgnoreCase(usuario.getDireccion()) || !nuevoTelefono.equalsIgnoreCase(String.valueOf(usuario.getTelefono())) || !nuevoCodigoPostal.equalsIgnoreCase(String.valueOf(usuario.getCodigoPostal()))) {

                usuario.setNombreCompleto(nuevoNombre);
                usuario.setDireccion(nuevaDireccion);

                int telefono = Integer.parseInt(nuevoTelefono);
                usuario.setTelefono(telefono);

                int codigoPostal = Integer.parseInt(nuevoCodigoPostal);
                usuario.setCodigoPostal(codigoPostal);

                usuario.setPremium(isPremiumNuevo);

                // Parseamos a string el idPersona que queremos modificar
                String idUsuario = String.valueOf(usuario.getIdPersona());

                // Llevamos al usuario modificado ala base de datos
                UsuarioManagerFactory.get().edit_XML(usuario, idUsuario);

                // Panel informativo de éxito
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("¡Perfil Modificado!");
                alert.setHeaderText(null);
                alert.setContentText("Tu Perfil ha sido Modificado con Exito");

                // Mostrar el alert
                alert.showAndWait();

            } else {
                // Alerta de que no ha cambiado nada
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sin Cambios");
                alert.setHeaderText(null);
                alert.setContentText("No se ha modificado ningún dato. Por favor, realiza algún cambio.");
                alert.showAndWait();
            }

        } else {
            // Alerta que indica que los datos no son correctos
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Datos Incorrectos");
            alert.setHeaderText("Campos Incorrectos");
            alert.setContentText("Por favor, revisa los campos en rojo y asegúrate de que los datos son correctos.");
            alert.showAndWait();
        }
    }

/////////////////////////////////////////  VALIDACIONES DE REGISTRO  ///////////////////////////////////////////////////////////////
// Validacion para que solo Pueda introducir Letras
    public boolean validarSoloLetrasNombre(String nombreyApellidos) {
        return nombreyApellidos.matches("[a-zA-Z]+");
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

        // Validación para Nombre
        if (field == textFieldNombre) {
            if (field.getText().isEmpty() || !validarSoloLetrasNombre(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validación para Teléfono
        if (field == textFieldTelefono) {
            if (field.getText().isEmpty() || !esTelefonoCorrecto(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validación para Código Postal
        if (field == textFieldCodigoPostal) {
            if (field.getText().isEmpty() || !esPostalCorrecto(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validación para Dirección
        if (field == textFieldDireccion) {
            if (field.getText().isEmpty() || !validarDireccion(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }
    }

    // Comprobar si hay cambios no guardados y mostrar una alerta si es necesario
    private boolean verificarCambiosNoGuardados() {
        // Obtener los valores actuales de los campos de texto
        String nuevoNombre = textFieldNombre.getText();
        String nuevaDireccion = textFieldDireccion.getText();
        String nuevoTelefono = textFieldTelefono.getText();
        String nuevoCodigoPostal = textFieldCodigoPostal.getText();

        // Comprobar si hay cambios
        boolean hayCambios = !nuevoNombre.equalsIgnoreCase(usuario.getNombreCompleto())
                || !nuevaDireccion.equalsIgnoreCase(usuario.getDireccion())
                || !nuevoTelefono.equalsIgnoreCase(String.valueOf(usuario.getTelefono()))
                || !nuevoCodigoPostal.equalsIgnoreCase(String.valueOf(usuario.getCodigoPostal()));

        if (hayCambios) {
            // Si hay cambios, mostrar la alerta de confirmación
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cambios No Guardados");
            alert.setHeaderText("¿Estás seguro de que deseas salir?");
            alert.setContentText("Si haces clic en 'Volver', los cambios no guardados se perderán.");

            // Botones personalizados
            ButtonType buttonVolver = new ButtonType("Salir");
            ButtonType buttonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonVolver, buttonCancelar);

            // Mostrar la alerta y esperar la respuesta del usuario
            Optional<ButtonType> result = alert.showAndWait();

            // Si el usuario cancela la operación, se devuelve 'false'
            if (result.isPresent() && result.get() == buttonCancelar) {
                return false;  // No cambiar de ventana
            }
        }

        // Si no hay cambios o el usuario acepta salir, devolver 'true'
        return true;
    }

    @FXML
    private void updatePassword(ActionEvent event) {
        // Obtener el usuario actual en sesión
        String emailUsuarioActual = usuario.getEmail();

        // Crear diálogo para pedir el email
        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("Actualizar Contraseña");
        emailDialog.setHeaderText("Introduce tu correo electrónico:");
        emailDialog.setContentText("Correo:");

        Optional<String> emailInput = emailDialog.showAndWait();

        if (emailInput.isPresent() && !emailInput.get().isEmpty()) {
            String emailIngresado = emailInput.get();

            // Verificar si el correo ingresado coincide con el del usuario en sesión
            if (!emailIngresado.equalsIgnoreCase(emailUsuarioActual)) {
                new Alert(Alert.AlertType.ERROR, "No puedes cambiar la contraseña de otro usuario.").showAndWait();
                return;
            }

            // Crear diálogo para pedir la nueva contraseña
            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setTitle("Actualizar Contraseña");
            passwordDialog.setHeaderText("Introduce tu nueva contraseña:");
            passwordDialog.setContentText("Nueva contraseña:");

            Optional<String> passwordInput = passwordDialog.showAndWait();

            if (passwordInput.isPresent()) {
                String newPassword = passwordInput.get();

                // Validar la nueva contraseña
                if (!isPasswordValid(newPassword)) {
                    new Alert(Alert.AlertType.ERROR, "La contraseña debe tener al menos 8 caracteres y un número.").showAndWait();
                    return;
                }

                try {
                    // Llamar al método del cliente REST para actualizar la contraseña
                    PersonaManagerFactory.get().updatePassword_XML(emailIngresado, newPassword);

                    // Mostrar mensaje de éxito
                    new Alert(Alert.AlertType.INFORMATION, "Contraseña actualizada exitosamente.").showAndWait();
                } catch (ClientErrorException e) {
                    new Alert(Alert.AlertType.ERROR, "Error al actualizar la contraseña. Verifica que el correo es correcto.").showAndWait();
                }
            }
        }
    }

    private boolean isPasswordValid(String password) {
        // Validar que la contraseña tenga al menos 8 caracteres y contenga al menos un número
        return password.length() >= 8 && password.matches(".*\\d.*");
    }
}
