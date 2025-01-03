/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanacoches;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author crice
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private void handleHomeButtonAction() {
        System.out.println("HOME button clicked");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Ventana inicializada correctamente.");
    }

}
