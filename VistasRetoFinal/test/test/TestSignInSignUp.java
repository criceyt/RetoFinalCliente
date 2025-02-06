package test;

import controladores.ApplicationClient;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author oier
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSignInSignUp extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new ApplicationClient().start(stage);
    }

    @Test
    public void a_testTablaProveedores() {

        // Primer Alert
        clickOn("#inicioSesionBtn");
        clickOn("Aceptar");

        // Segundo Alert
        clickOn("#usernameField");
        write("juan@example.com");
        clickOn("#inicioSesionBtn");
        clickOn("Aceptar");
        clickOn("#usernameField");
        eraseText(16);

        // Tercer Alert
        clickOn("#passwordField");
        write("Abcd*1234");
        clickOn("#inicioSesionBtn");
        clickOn("Aceptar");
        clickOn("#passwordField");
        eraseText(16);

        // Entramos con el User
        clickOn("#usernameField");
        write("juan@example.com");
        clickOn("#passwordField");
        write("Abcd*1234");
        clickOn("#inicioSesionBtn");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        // Cerramos Sesion
        clickOn("#cerrarSesionBtn");
        clickOn("Aceptar");

        // Hacemos un Registro
        clickOn("#registerButton");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }
        verifyThat("#registerPane", isVisible());

        // Primer Registro Mal
        clickOn("#nombreyApellidoField");
        write("123");
        clickOn("#dniField");
        write("euryyeur");
        clickOn("#emailField");
        write("erge45");
        clickOn("#telefonoField");
        write("123123");
        clickOn("#direccionField");
        write("erg&");
        clickOn("#codigoPostalField");
        write("123");
        clickOn("#registerPasswordField");
        write("123");
        clickOn("#confirmPasswordField");
        write("1rtg");

        clickOn("#registroBtn");
        clickOn("Aceptar");

        // Segundo Registro Bien
        clickOn("#nombreyApellidoField");
        eraseText(25);
        write("Usuario de Test");
        clickOn("#dniField");
        eraseText(25);
        write("11111111A");
        clickOn("#emailField");
        eraseText(25);
        write("usu@gmail.com");
        clickOn("#telefonoField");
        eraseText(25);
        write("124123123");
        clickOn("#direccionField");
        eraseText(25);
        write("bilbo");
        clickOn("#codigoPostalField");
        eraseText(25);
        write("12345");
        clickOn("#registerPasswordField");
        eraseText(25);
        write("Abcd*1234");
        clickOn("#confirmPasswordField");
        eraseText(25);
        write("Abcd*1234");
        clickOn("#revealConfirmButton");
        clickOn("#registroBtn");
        clickOn("Aceptar");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        push(KeyCode.ENTER);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        // Demostramos que no se puede hacer un registro con mismo DNI y Email
        clickOn("#registerButton");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        clickOn("#registroBtn");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        clickOn("Aceptar");
        clickOn("Aceptar");
        clickOn("#emailField");
        eraseText(25);
        write("usuDistinto@gmail.com");
        clickOn("#registroBtn");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        clickOn("Aceptar");
        clickOn("Aceptar");
        clickOn("#dniField");
        eraseText(25);
        write("11111111R");
        clickOn("#registroBtn");
        clickOn("Aceptar");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        push(KeyCode.ENTER);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
          
        }

        verifyThat("#loginPane", isVisible());

        // Iniciamos Sesion
        clickOn("#usernameField");
        eraseText(25);
        write("usu@gmail.com");

        clickOn("#passwordField");
        write("Abcd*1234");
        clickOn("#inicioSesionBtn");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        verifyThat("#homeBtn", isVisible());
    }
}