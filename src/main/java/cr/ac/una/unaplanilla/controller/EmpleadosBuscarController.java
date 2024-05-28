package cr.ac.una.unaplanilla.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author justi
 */
public class EmpleadosBuscarController extends Controller implements Initializable {

    @FXML
    private MFXTextField txfCedula;
    @FXML
    private MFXTextField txfNombre;
    @FXML
    private MFXTextField txfPrimerApellido;
    @FXML
    private MFXTextField txfSegundoApellido;
    @FXML
    private TableView<?> tbvTablaEmpleados;
    @FXML
    private TableColumn<?, ?> clmCedulaEmpleado;
    @FXML
    private TableColumn<?, ?> clmNombreEmpleado;
    @FXML
    private TableColumn<?, ?> clmPApellidoEmpleado;
    @FXML
    private TableColumn<?, ?> clmEmpleado;
    @FXML
    private MFXButton btnAceptar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnAceptar(ActionEvent event) {

    }

}
