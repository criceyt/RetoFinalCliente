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

    /**
     * Elementos de la ventana para la gestión del perfil de usuario. Define los
     * botones y campos de texto necesarios para mostrar y editar la información
     * del usuario.
     */
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

    /**
     * Atributos para cargar los datos del usuario y validar la entrada de
     * datos.
     */
    private Usuario usuario;
    private boolean datosCorrectos;

    /**
     * Método de inicialización que se llama cuando la ventana es cargada.
     * Establece listeners para validar los campos y configura los botones y la
     * vista con los datos del usuario.
     *
     * @param location La URL de ubicación del recurso.
     * @param resources El conjunto de recursos utilizados por la ventana.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Establecer listeners para validar los campos al perder el foco
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

        // Asignar acciones a los botones
        homeBtn.setOnAction(this::irAtras);
        tusVehiculosBtn.setOnAction(this::abrirVentanaTusVehiculos);
        volverBtn.setOnAction(this::irAtras);
        cerrarSesionBtn.setOnAction(this::abrirVentanaSignInSignUp);
        guardarBtn.setOnAction(this::guardarDatosUsuario);

        // Obtener el usuario actual desde SessionManager
        this.usuario = SessionManager.getUsuario();

        // Deshabilitar los campos DNI, Email y Fecha de registro
        textFieldDni.setDisable(true);
        textFieldEmail.setDisable(true);
        textFieldFechaRegistro.setDisable(true);

        // Rellenar los campos con los datos del usuario
        textFieldDni.setText(usuario.getDni());
        textFieldEmail.setText(usuario.getEmail());
        textFieldNombre.setText(usuario.getNombreCompleto());
        textFieldDireccion.setText(usuario.getDireccion());

        // Convertir el número de teléfono a String y asignarlo
        int telefonoInt = usuario.getTelefono();
        textFieldTelefono.setText(String.valueOf(telefonoInt));

        // Convertir el código postal a String y asignarlo
        int codigoPostalInt = usuario.getCodigoPostal();
        textFieldCodigoPostal.setText(String.valueOf(codigoPostalInt));

        // Formatear la fecha de registro a "yyyy-MM-dd" y asignarla
        Date fechaString = usuario.getFechaRegistro();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = sdf.format(fechaString);
        textFieldFechaRegistro.setText(fechaFormateada);

        // Establecer si el usuario tiene cuenta premium
        chkTerms.setSelected(usuario.isPremium());

        System.out.println("Ventana inicializada correctamente.");
    }

    /**
     * Método para abrir la ventana de inicio de sesión y registro (SignIn &
     * SignUp). Muestra un alert de confirmación antes de cerrar sesión y
     * cambiar a la vista de inicio de sesión.
     *
     * @param event El evento de acción disparado por el usuario.
     */
    private void abrirVentanaSignInSignUp(ActionEvent event) {
        // Crear un alert de tipo confirmación antes de cerrar sesión
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Estás seguro de que deseas cerrar sesión?");
        alert.setContentText("Perderás cualquier cambio no guardado.");

        // Mostrar la alerta y esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Cargar el FXML de la vista SignInSignUp
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/SignInSignUp.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la vista cargada
                    SignController controler = loader.getController();

                    // Obtener el Stage desde el nodo que disparó el evento
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                    // Configurar el título y escena de la ventana
                    stage.setTitle("SignIn & SignUp");
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/css/stylesOscuro.css").toExternalForm());

                    // Establecer la nueva escena y mostrarla
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    // Si ocurre un error al cargar la ventana, se muestra un error
                    Logger.getLogger(SignController.class.getName()).log(Level.SEVERE, null, ex);
                    new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
                }
            } else {
                // Si el usuario cancela el cierre de sesión
                System.out.println("Cancelado, no se cierra la sesión.");
            }
        });
    }

    /**
     * Método para regresar a la pantalla principal desde cualquier vista.
     * Verifica si hay cambios no guardados antes de regresar.
     *
     * @param event El evento de acción disparado por el usuario.
     */
    private void irAtras(ActionEvent event) {
        if (verificarCambiosNoGuardados()) {
            try {
                // Cargar el FXML de la vista principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/NavegacionPrincipal.fxml"));
                Parent root = loader.load();

                // Crear un ScrollPane para la vista cargada
                ScrollPane sc = new ScrollPane();
                sc.setContent(root);

                // Configurar las barras de desplazamiento
                sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                sc.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

                // Obtener el Stage actual y configurar la nueva escena
                Stage stage = (Stage) homeBtn.getScene().getWindow();
                stage.setTitle("Navegación Principal");

                Scene scene = new Scene(sc);
                scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

                // Establecer el tamaño de la ventana
                stage.setWidth(1000);  // Establecer el ancho
                stage.setHeight(800);  // Establecer la altura

                // Mostrar la nueva escena
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                // Manejo de errores al cargar la vista principal
                Logger.getLogger(TablaMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, inténtalo más tarde.", ButtonType.OK).showAndWait();
            }
        }
    }

    /**
     * Método para abrir la ventana "Tus Vehículos". Verifica si hay cambios no
     * guardados antes de cambiar a la vista de los vehículos del usuario.
     *
     * @param event El evento de acción disparado por el usuario.
     */
    private void abrirVentanaTusVehiculos(ActionEvent event) {
        if (verificarCambiosNoGuardados()) {
            try {
                // Cargar el FXML de la vista "Tus Vehículos"
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/TusVehiculos.fxml"));
                Parent root = loader.load();

                // Obtener el controlador de la vista cargada
                TusVehiculosController controler = loader.getController();

                // Obtener el Stage desde el nodo que disparó el evento
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

                // Configurar el título y escena de la ventana
                stage.setTitle("Tus Vehículos");
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/css/NavegacionPrincipal.css").toExternalForm());

                // Establecer la nueva escena y mostrarla
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                // Manejo de errores al cargar la vista
                Logger.getLogger(TusVehiculosController.class.getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, "Error en la sincronización de ventanas, intentalo más tarde.", ButtonType.OK).showAndWait();
            }
        }
    }

    /**
     * Método para guardar los datos modificados del usuario. Verifica que los
     * datos sean correctos antes de actualizar la base de datos.
     *
     * @param event El evento de acción disparado por el usuario.
     */
    private void guardarDatosUsuario(ActionEvent event) {
        // Obtener los nuevos valores desde los campos de texto
        String nuevoNombre = textFieldNombre.getText();
        String nuevaDireccion = textFieldDireccion.getText();
        String nuevoTelefono = textFieldTelefono.getText();
        String nuevoCodigoPostal = textFieldCodigoPostal.getText();
        boolean isPremiumNuevo = chkTerms.isSelected();

        // Validar que todos los campos tienen los datos correctos
        if (!textFieldNombre.getText().isEmpty() && validarSoloLetrasNombre(nuevoNombre) && !textFieldDireccion.getText().isEmpty() && validarDireccion(nuevaDireccion) && !textFieldTelefono.getText().isEmpty() && esTelefonoCorrecto(nuevoTelefono) && !textFieldCodigoPostal.getText().isEmpty() && esPostalCorrecto(nuevoCodigoPostal)) {
            // Si alguno de los datos son nuevos, actualizamos los datos del usuario
            if (!nuevoNombre.equalsIgnoreCase(usuario.getNombreCompleto()) || !nuevaDireccion.equalsIgnoreCase(usuario.getDireccion()) || !nuevoTelefono.equalsIgnoreCase(String.valueOf(usuario.getTelefono())) || !nuevoCodigoPostal.equalsIgnoreCase(String.valueOf(usuario.getCodigoPostal()))) {

                // Actualizamos los datos del usuario
                usuario.setNombreCompleto(nuevoNombre);
                usuario.setDireccion(nuevaDireccion);

                int telefono = Integer.parseInt(nuevoTelefono);
                usuario.setTelefono(telefono);

                int codigoPostal = Integer.parseInt(nuevoCodigoPostal);
                usuario.setCodigoPostal(codigoPostal);

                usuario.setPremium(isPremiumNuevo);

                // Parseamos a string el idPersona que queremos modificar
                String idUsuario = String.valueOf(usuario.getIdPersona());

                // Actualizamos la base de datos con los nuevos datos del usuario
                UsuarioManagerFactory.get().edit_XML(usuario, idUsuario);

                // Mostrar panel informativo de éxito
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("¡Perfil Modificado!");
                alert.setHeaderText(null);
                alert.setContentText("Tu Perfil ha sido Modificado con Exito");

                // Mostrar el alert
                alert.showAndWait();

            } else {
                // Si no se ha modificado ningún dato, mostramos una alerta
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sin Cambios");
                alert.setHeaderText(null);
                alert.setContentText("No se ha modificado ningún dato. Por favor, realiza algún cambio.");
                alert.showAndWait();
            }

        } else {
            // Si los datos son incorrectos, mostramos una alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Datos Incorrectos");
            alert.setHeaderText("Campos Incorrectos");
            alert.setContentText("Por favor, revisa los campos en rojo y asegúrate de que los datos son correctos.");
            alert.showAndWait();
        }
    }
/////////////////////////////////////////  VALIDACIONES DE REGISTRO  ///////////////////////////////////////////////////////////////

    /**
     * Valida que el nombre y apellidos solo contengan letras.
     *
     * @param nombreyApellidos El nombre y apellidos a validar.
     * @return true si solo contiene letras, false en caso contrario.
     */
    public boolean validarSoloLetrasNombre(String nombreyApellidos) {
        return nombreyApellidos.matches("[a-zA-Z]+");
    }

    /**
     * Valida que el teléfono tenga exactamente 9 dígitos numéricos.
     *
     * @param telefono El número de teléfono a validar.
     * @return true si es un número de teléfono válido (9 dígitos), false en
     * caso contrario.
     */
    private boolean esTelefonoCorrecto(String telefono) {
        return telefono.matches("\\d{9}");
    }

    /**
     * Valida que la dirección solo contenga letras, números, espacios, comas,
     * puntos y guiones.
     *
     * @param direccion La dirección a validar.
     * @return true si es válida, false en caso contrario.
     */
    public boolean validarDireccion(String direccion) {
        return direccion.matches("[a-zA-Z0-9\\s,.-]+");
    }

    /**
     * Valida que el código postal tenga exactamente 5 dígitos numéricos.
     *
     * @param codigoPostal El código postal a validar.
     * @return true si es un código postal válido (5 dígitos), false en caso
     * contrario.
     */
    private boolean esPostalCorrecto(String codigoPostal) {
        return codigoPostal.matches("\\d{5}");
    }

    /**
     * Método para validar el campo de texto y aplicar el estilo según si es
     * correcto o no.
     *
     * @param field El campo de texto a validar.
     */
    private void validateField(TextField field) {

        // Validación para el campo "Nombre"
        if (field == textFieldNombre) {
            if (field.getText().isEmpty() || !validarSoloLetrasNombre(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validación para el campo "Teléfono"
        if (field == textFieldTelefono) {
            if (field.getText().isEmpty() || !esTelefonoCorrecto(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validación para el campo "Código Postal"
        if (field == textFieldCodigoPostal) {
            if (field.getText().isEmpty() || !esPostalCorrecto(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }

        // Validación para el campo "Dirección"
        if (field == textFieldDireccion) {
            if (field.getText().isEmpty() || !validarDireccion(field.getText())) {
                field.setStyle("-fx-border-color: red;");
            } else {
                field.setStyle("-fx-border-color: transparent;");
            }
        }
    }
/////////////////////////////////////////  MÉTODOS PARA VERIFICAR CAMBIOS Y ACTUALIZAR CONTRASEÑA  ///////////////////////////////////////////////////////////////

    /**
     * Verifica si hay cambios no guardados en los campos de texto y muestra una
     * alerta de confirmación.
     *
     * @return true si no hay cambios o si el usuario confirma que desea salir
     * sin guardar los cambios, false si el usuario cancela la operación.
     */
    private boolean verificarCambiosNoGuardados() {
        // Obtener los valores actuales de los campos de texto
        String nuevoNombre = textFieldNombre.getText();
        String nuevaDireccion = textFieldDireccion.getText();
        String nuevoTelefono = textFieldTelefono.getText();
        String nuevoCodigoPostal = textFieldCodigoPostal.getText();

        // Comprobar si hay cambios en los campos
        boolean hayCambios = !nuevoNombre.equalsIgnoreCase(usuario.getNombreCompleto())
                || !nuevaDireccion.equalsIgnoreCase(usuario.getDireccion())
                || !nuevoTelefono.equalsIgnoreCase(String.valueOf(usuario.getTelefono()))
                || !nuevoCodigoPostal.equalsIgnoreCase(String.valueOf(usuario.getCodigoPostal()));

        // Si hay cambios, mostrar una alerta para confirmar si el usuario desea salir sin guardar
        if (hayCambios) {
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

            // Si el usuario cancela, no realizar el cambio de ventana
            if (result.isPresent() && result.get() == buttonCancelar) {
                return false;  // No cambiar de ventana
            }
        }

        // Si no hay cambios o el usuario confirma que desea salir, devolver 'true'
        return true;
    }

    /**
     * Permite actualizar la contraseña del usuario. Solicita el correo y la
     * nueva contraseña.
     *
     * @param event El evento de acción.
     */
    @FXML
    private void updatePassword(ActionEvent event) {
        // Obtener el correo electrónico del usuario actual
        String emailUsuarioActual = usuario.getEmail();

        // Crear diálogo para solicitar el correo electrónico del usuario
        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("Actualizar Contraseña");
        emailDialog.setHeaderText("Introduce tu correo electrónico:");
        emailDialog.setContentText("Correo:");

        Optional<String> emailInput = emailDialog.showAndWait();

        // Si el correo es válido
        if (emailInput.isPresent() && !emailInput.get().isEmpty()) {
            String emailIngresado = emailInput.get();

            // Verificar que el correo ingresado corresponde al usuario actual
            if (!emailIngresado.equalsIgnoreCase(emailUsuarioActual)) {
                new Alert(Alert.AlertType.ERROR, "No puedes cambiar la contraseña de otro usuario.").showAndWait();
                return;
            }

            // Crear un diálogo para ingresar la nueva contraseña
            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setTitle("Actualizar Contraseña");
            passwordDialog.setHeaderText("Introduce tu nueva contraseña:");
            passwordDialog.setContentText("Nueva contraseña:");

            Optional<String> passwordInput = passwordDialog.showAndWait();

            // Si se ingresó una nueva contraseña
            if (passwordInput.isPresent()) {
                String newPassword = passwordInput.get();

                // Validar la nueva contraseña
                if (!isPasswordValid(newPassword)) {
                    new Alert(Alert.AlertType.ERROR, "La contraseña debe tener al menos 8 caracteres y un número.").showAndWait();
                    return;
                }

                try {
                    // Actualizar la contraseña en el sistema
                    PersonaManagerFactory.get().updatePassword_XML(emailIngresado, newPassword);

                    // Mostrar mensaje de éxito
                    new Alert(Alert.AlertType.INFORMATION, "Contraseña actualizada exitosamente.").showAndWait();
                } catch (ClientErrorException e) {
                    // Si ocurre un error al intentar actualizar la contraseña
                    new Alert(Alert.AlertType.ERROR, "Error al actualizar la contraseña. Verifica que el correo es correcto.").showAndWait();
                }
            }
        }
    }

    /**
     * Valida que la contraseña cumpla con los requisitos de longitud mínima y
     * contenga al menos un número.
     *
     * @param password La contraseña a validar.
     * @return true si la contraseña es válida, false en caso contrario.
     */
    private boolean isPasswordValid(String password) {
        // La contraseña debe tener al menos 8 caracteres y contener al menos un número
        return password.length() >= 8 && password.matches(".*\\d.*");
    }
}
