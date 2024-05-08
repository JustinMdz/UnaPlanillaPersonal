/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.AppContext;
import cr.ac.una.unaplanilla.util.BindingUtils;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Justin Méndez
 */
public class EmpleadosController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private MFXRadioButton rdbMasculino;
    @FXML
    private MFXRadioButton rdbFemenino;
    @FXML
    private MFXTextField txtPApellido;
    @FXML
    private MFXTextField txtSApellido;
    @FXML
    private MFXTextField txtCedula;
    @FXML
    private MFXTextField txtCorreo;
    @FXML
    private MFXTextField txtUsuario;
    @FXML
    private MFXPasswordField txtClave;

    private EmpleadoDto empleadoDto;
    List<Node> requeridos = new ArrayList<>();
    @FXML
    private MFXDatePicker dtpFIngreso;
    @FXML
    private MFXDatePicker dtpFSalida;
    @FXML
    private MFXCheckbox chkAdministrador;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private ToggleGroup tggGenero;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdbMasculino.setUserData("M");
        rdbFemenino.setUserData("F");
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        txtPApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtCedula.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(40));
        txtCorreo.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(80));
        txtUsuario.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtClave.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(8));
        empleadoDto = new EmpleadoDto();
        nuevoEmpleado();
        IndicarRequeridos();
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Empleado", getStage(), "¿Esta seguro que desea limipiar el registro?"))
        {
            nuevoEmpleado();
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try
        {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank())
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "GuardarEmpleado", getStage(), invalidos);
            } else
            {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.guardarEmpleado(this.empleadoDto);
                if (respuesta.getEstado())
                {
                    unbindEmpleado();
                    this.empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                    bindEmpleado(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar empleado", getStage(), "El empleado se guardó correctamente");
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Empleado", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Empleado", getStage(), "Ocurrio un error guardando el empleado.");
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try
        {
            if (this.empleadoDto.getId() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Empleado", getStage(), "Favor consultar el empleado a elimniar");
            } else
            {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.eliminarEmpleado(this.empleadoDto.getId());
                if (respuesta.getEstado())
                {
                    nuevoEmpleado();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar empleado", getStage(), "El empleado se elimino correctamente");
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Empleado", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error eliminando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Empleado", getStage(), "Ocurrio un error eliminando el empleado.");
        }
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isBlank())
        {
            cargarEmpleado(Long.valueOf(txtId.getText()));
        }
    }

    @FXML
    private void onActionCheckAdministrador(ActionEvent event) {
        validarAdministrador();
    }

    private void cargarEmpleado(Long id) {
        try
        {
            EmpleadoService empleadoService = new EmpleadoService();
            Respuesta respuesta = empleadoService.getEmpleado(id);

            if (respuesta.getEstado())
            {
                unbindEmpleado();
                this.empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                bindEmpleado(false);
                validarAdministrador();
                validarRequeridos();
            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Empleado", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex)
        {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error consultando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Empleado", getStage(), "Ocurrio un error consultando el empleado.");
        }
    }

    private void IndicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtCedula, txtNombre, txtPApellido, dtpFIngreso));
    }

    private void validarAdministrador() {
        if (chkAdministrador.isSelected())
        {
            requeridos.addAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.setDisable(false);
            txtClave.setDisable(false);
        } else
        {
            requeridos.removeAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.clear();
            txtUsuario.setDisable(true);
            txtClave.clear();
            txtClave.setDisable(true);
        }
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos)
        {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank()))
            {
                if (validos)
                {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else
                {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXPasswordField && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank()))
            {
                if (validos)
                {
                    invalidos += ((MFXPasswordField) node).getFloatingText();
                } else
                {
                    invalidos += "," + ((MFXPasswordField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null)
            {
                if (validos)
                {
                    invalidos += ((MFXDatePicker) node).getFloatingText();
                } else
                {
                    invalidos += "," + ((MFXDatePicker) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0)
            {
                if (validos)
                {
                    invalidos += ((MFXComboBox) node).getFloatingText();
                } else
                {
                    invalidos += "," + ((MFXComboBox) node).getFloatingText();
                }
                validos = false;
            }
        }
        if (validos)
        {
            return "";
        } else
        {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    private void nuevoEmpleado() {
        unbindEmpleado();
        empleadoDto = new EmpleadoDto();
        bindEmpleado(true);
        txtId.clear();
        txtId.requestFocus();

    }

    private void bindEmpleado(Boolean nuevo) {
        if (!nuevo)
        {
            txtId.textProperty().bind(empleadoDto.id);
        }
        txtCedula.textProperty().bindBidirectional(empleadoDto.cedula);
        txtNombre.textProperty().bindBidirectional(empleadoDto.nombre);
        txtPApellido.textProperty().bindBidirectional(empleadoDto.primerApellido);
        txtSApellido.textProperty().bindBidirectional(empleadoDto.segundoApellido);
        txtUsuario.textProperty().bindBidirectional(empleadoDto.usuario);
        txtClave.textProperty().bindBidirectional(empleadoDto.clave);
        txtCorreo.textProperty().bindBidirectional(empleadoDto.correo);
        dtpFIngreso.valueProperty().bindBidirectional(empleadoDto.fechaIngreso);
        dtpFSalida.valueProperty().bindBidirectional(empleadoDto.fechaSalida);
        chkActivo.selectedProperty().bindBidirectional(empleadoDto.estado);
        chkAdministrador.selectedProperty().bindBidirectional(empleadoDto.administrador);
        BindingUtils.bindToggleGroupToProperty(tggGenero, empleadoDto.genero);
    }

    private void unbindEmpleado() {
        txtId.textProperty().unbind();
        txtCedula.textProperty().unbindBidirectional(empleadoDto.cedula);
        txtNombre.textProperty().unbindBidirectional(empleadoDto.nombre);
        txtPApellido.textProperty().unbindBidirectional(empleadoDto.primerApellido);
        txtSApellido.textProperty().unbindBidirectional(empleadoDto.segundoApellido);
        txtUsuario.textProperty().unbindBidirectional(empleadoDto.usuario);
        txtClave.textProperty().unbindBidirectional(empleadoDto.clave);
        txtCorreo.textProperty().unbindBidirectional(empleadoDto.correo);
        dtpFIngreso.valueProperty().unbindBidirectional(empleadoDto.fechaIngreso);
        dtpFSalida.valueProperty().unbindBidirectional(empleadoDto.fechaSalida);
        chkActivo.selectedProperty().unbindBidirectional(empleadoDto.estado);
        chkAdministrador.selectedProperty().unbindBidirectional(empleadoDto.administrador);
        BindingUtils.unbindToggleGroupToProperty(tggGenero, empleadoDto.genero);
    }

}
