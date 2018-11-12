/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tahwissa.desktop.admin.modifier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Elhraiech Nizar
 */
public class EditerController implements Initializable {

    @FXML
    private JFXTextField nom;
    @FXML
    private JFXTextField prenom;
    @FXML
    private JFXTextField mdp;
    @FXML
    private JFXTextField mdp1;
    @FXML
    private JFXDatePicker dateChoix;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton confirmer;
    @FXML
    private JFXButton annuler;
    @FXML
    private ToggleGroup groupe;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void confirmerButton(ActionEvent event) {
    }

    @FXML
    private void annulerButton(ActionEvent event) {
    }
    
}
