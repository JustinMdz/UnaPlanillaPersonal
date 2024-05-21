/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author justi
 */
public class PrincipalController extends Controller implements Initializable {

    @FXML
    private BorderPane PanelRoot;
    @FXML
    private MFXButton btnSalir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        
    }

    @FXML
    private void onActionBtnEmpleados(ActionEvent event) {
        FlowController.getInstance().goView("EmpleadosView");
    }

    @FXML
    private void onActionBtnTiposPlanilla(ActionEvent event) {
        FlowController.getInstance().goView("TiposPlanillaView");
    }

    @FXML
    private void OnActionBtnSalir(ActionEvent event) {
    }
    
}
