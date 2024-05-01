package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.AppContext;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private MFXButton btnCancelar;
    @FXML
    private MFXButton btnIngresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionbtnCancelar(ActionEvent event) {
        ((Stage) btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionbtnIngresar(ActionEvent event) {
        if (txfUsuario.getText().isBlank())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Validacion Usuario", getStage(), "Es necesario digitar un usuario para ingresar al sistema");
        } else if (psfClave.getText().isBlank())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Validacion Usuario", getStage(), "Es necesario digitar una clave para ingresar al sistema");
        } else
        {
            EmpleadoService empleadoService = new EmpleadoService();
            Respuesta respuesta = empleadoService.getUsuario(txfUsuario.getText(), psfClave.getText());

            if (respuesta.getEstado())
            {
                AppContext.getInstance().set("Usuario", respuesta.getResultado("Usuario"));
                FlowController.getInstance().goMain();
                Stage stage = (Stage) this.root.getScene().getWindow();
                stage.close();
            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validacion Usuario", getStage(), respuesta.getMensaje());
            }
        }

    }


}
