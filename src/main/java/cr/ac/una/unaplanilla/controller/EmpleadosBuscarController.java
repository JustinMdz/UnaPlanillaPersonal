package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private TableView<EmpleadoDto> tbvTablaEmpleados;
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
    @FXML
    private MFXButton btnFiltrar;

    ObservableList<EmpleadoDto> empleados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnAceptar(ActionEvent event) {
        System.out.println("AceptarButton");
    }

    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
        System.out.println("FiltrarButton");
    }

}
