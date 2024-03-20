package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author justi
 */
public class LogInViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txfUsuario;
    @FXML
    private MFXPasswordField psfClave;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnIngresar;
    @FXML
    private Button btnClose;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
    }    

    @FXML
    private void onActionbtnCancelar(ActionEvent event) {
       ((Stage) btnCancelar.getScene().getWindow()).close();    
    }

    @FXML
    private void onActionbtnIngresar(ActionEvent event) {
       FlowController.getInstance().goMain();
        
    }

    @FXML
    private void onActionbtnClose(ActionEvent event) {
    }

    @Override
    public void initialize() {
       this.txfUsuario.setText(null);
        this.psfClave.setText(null);
    }
    
}
