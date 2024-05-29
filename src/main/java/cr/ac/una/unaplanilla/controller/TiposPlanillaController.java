package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.service.TipoPlanillaService;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Justin Mendez
 */
public class TiposPlanillaController extends Controller implements Initializable {

    @FXML
    private MFXTextField txfId;
    @FXML
    private MFXTextField txfCodigo;
    @FXML
    private MFXTextField txfDescripcion;
    @FXML
    private MFXTextField txfPlantillasPorMes;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private MFXButton btnNuevo1;
    @FXML
    private MFXButton btnGuardar1;
    @FXML
    private MFXButton btnEliminar1;
    private TipoPlanillaDto tipoPlanillaDto;
    private List<Node> requeridos;
    @FXML
    private MFXButton btnBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        this.tipoPlanillaDto = new TipoPlanillaDto();
        this.requeridos = new ArrayList<>();
        chkActivo.setUserData("A");
        txfId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txfCodigo.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(4));
        txfDescripcion.delegateSetTextFormatter(Formato.getInstance().letrasFormat(40));
        txfPlantillasPorMes.delegateSetTextFormatter(Formato.getInstance().integerFormatWithMaxLength(2));
        nuevoTipoPlanilla();
        IndicarRequeridos();
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Tipo Planilla", getStage(),
                "Desea limpiar el resgistro de Planilla?"))
        {
            nuevoTipoPlanilla();
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try
        {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank())
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Tipo Planilla", getStage(), invalidos);
            } else
            {
                TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
                Respuesta respuesta = tipoPlanillaService.guardarTipoPlanilla(this.tipoPlanillaDto);
                if (respuesta.getEstado())
                {
                    unbindTipoPlanilla();
                    this.tipoPlanillaDto = (TipoPlanillaDto) respuesta.getResultado("TipoPlanilla");
                    bindTipoPlanilla(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Tipo Planilla", getStage(), "Tipo de Planilla guardado correctamente.");
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Tipo Planilla", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error al guardar el tipo de planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Tipo Planilla", getStage(), "Ocurrió un error al guardar el tipo de planilla.");
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try
        {
            if (this.tipoPlanillaDto.getId() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Tipo Planilla", getStage(), "Favor consultar el tipo de planilla a eliminar.");
            } else
            {

                TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
                Respuesta respuesta = tipoPlanillaService.eliminarTipoPlanilla(this.tipoPlanillaDto.getId());
                if (respuesta.getEstado())
                {
                    nuevoTipoPlanilla();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar tipoPlanilla", getStage(), "La tipoPlanilla se elimino correctamente");
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar tipoPlanilla", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error al eliminar el tipo de planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Tipo Planilla", getStage(), "Ocurrió un error al eliminar el tipo de planilla.");
        }
    }

    @FXML
    private void onActionBtnGuardar1(ActionEvent event) {
    }

    private void cargarTipoPlanilla(Long id) {
        try
        {
            TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
            Respuesta respuesta = tipoPlanillaService.getTipoPlanilla(id);

            if (respuesta.getEstado())
            {
                unbindTipoPlanilla();
                this.tipoPlanillaDto = (TipoPlanillaDto) respuesta.getResultado("TipoPlanilla");
                bindTipoPlanilla(false);
                validarRequeridos();
            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Tipo Planilla", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex)
        {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error al consultar tipo de planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Tipo Planilla", this.getStage(), "Ocurrió un error al cargar el tipo de planilla.");
        }
    }

    private void IndicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txfCodigo, txfDescripcion, txfPlantillasPorMes));
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

    private void nuevoTipoPlanilla() {
        unbindTipoPlanilla();
        tipoPlanillaDto = new TipoPlanillaDto();
        bindTipoPlanilla(true);
        txfId.clear();
        txfId.requestFocus();
    }

    private void bindTipoPlanilla(Boolean nuevo) {
        if (!nuevo)
        {
            txfId.textProperty().bind(tipoPlanillaDto.id);
        }
        txfCodigo.textProperty().bindBidirectional(tipoPlanillaDto.codigo);
        txfPlantillasPorMes.textProperty().bindBidirectional(tipoPlanillaDto.planillasXMes);
        txfDescripcion.textProperty().bindBidirectional(tipoPlanillaDto.descripcion);
        chkActivo.selectedProperty().bindBidirectional(tipoPlanillaDto.estado);
    }

    public void unbindTipoPlanilla() {
        txfId.textProperty().unbind();
        txfCodigo.textProperty().unbindBidirectional(tipoPlanillaDto.codigo);
        txfPlantillasPorMes.textProperty().unbindBidirectional(tipoPlanillaDto.planillasXMes);
        txfDescripcion.textProperty().unbindBidirectional(tipoPlanillaDto.descripcion);
        chkActivo.selectedProperty().unbindBidirectional(tipoPlanillaDto.estado);
    }

    @FXML
    private void onKeyPressedTxfId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txfId.getText().isEmpty())
        {
            cargarTipoPlanilla(Long.valueOf(txfId.getText()));
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        PlanillasBuscarController busquedaController = (PlanillasBuscarController) FlowController.getInstance().getController("PlanillasBuscarView");
        FlowController.getInstance().goViewInWindowModal("PlanillasBuscarView", ((Stage) btnBuscar.getScene().getWindow()), true);
        TipoPlanillaDto tpla = (TipoPlanillaDto) busquedaController.getResultado();

        if (tpla != null)
        {
            cargarTipoPlanilla(tpla.getId());
        }
    }

}
