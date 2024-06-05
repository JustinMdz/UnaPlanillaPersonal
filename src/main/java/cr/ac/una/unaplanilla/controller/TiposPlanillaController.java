package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Justin Mendez
 */
public class TiposPlanillaController extends Controller implements Initializable {

    private TipoPlanillaDto tipoPlanillaDto;
    private List<Node> requeridos;
    private EmpleadoDto empleadoDto;

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
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private TabPane tbpTipoPlanilla;
    @FXML
    private Tab tabTipoPlanillas;
    @FXML
    private Tab tabEmpleados;
    @FXML
    private MFXTextField txfIdEmpleado;
    @FXML
    private MFXTextField txfNombreEmpleado;
    @FXML
    private Button btnAgregarEmpleado;
    @FXML
    private TableView<EmpleadoDto> tbvEmpleados;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcIdEmpleado;
    @FXML
    private TableColumn<EmpleadoDto, String> tbcNombreEmpleado;
    @FXML
    private TableColumn<EmpleadoDto, Boolean> tbcEliminarEmpleado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        this.tipoPlanillaDto = new TipoPlanillaDto();
        this.empleadoDto = new EmpleadoDto();
        this.requeridos = new ArrayList<>();
        chkActivo.setUserData("A");
        txfId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txfCodigo.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(4));
        txfDescripcion.delegateSetTextFormatter(Formato.getInstance().letrasFormat(40));
        txfPlantillasPorMes.delegateSetTextFormatter(Formato.getInstance().integerFormatWithMaxLength(2));
        txfIdEmpleado.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        nuevoTipoPlanilla();
        IndicarRequeridos();

        tbcIdEmpleado.setCellValueFactory(cd -> cd.getValue().id);
        tbcNombreEmpleado.setCellValueFactory(cd -> cd.getValue().nombre);

        tbcEliminarEmpleado.setCellValueFactory((TableColumn.CellDataFeatures<EmpleadoDto, Boolean> p) -> new SimpleBooleanProperty(p.getValue() != null));
        tbcEliminarEmpleado.setCellFactory((TableColumn<EmpleadoDto, Boolean> p) -> new ButtonCell());

        tbvEmpleados.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends EmpleadoDto> observable, EmpleadoDto oldValue, EmpleadoDto newValue) ->
        {
            unbindEmpleado();
            if (newValue != null)
            {
                this.empleadoDto = newValue;
                bindEmpleado(false);
            }
        });

    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {

        if (tabEmpleados.isSelected())
        {
            nuevoEmpleado();
        } else if (tabTipoPlanillas.isSelected())
        {
            if (new Mensaje().showConfirmation("Limpiar tipo planilla", getStage(), "¿Está seguro que desea limpiar el registro?"))
            {
                nuevoTipoPlanilla();
            }
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
                    cargarEmpleados();
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

    @FXML
    private void onKeyPressedTxfIdEmpleado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txfId.getText().isEmpty())
        {
            cargarEmpleado(Long.valueOf(txfIdEmpleado.getText()));
        }
    }

    @FXML
    private void OnActionBtnAgregarEmpleado(ActionEvent event) {
        if (this.empleadoDto.getId() == null || this.empleadoDto.getNombre().isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Agregar empleado", getStage(),
                    "Es necesario cargar un empleado para agregarlo a la lista.");
        } else if (tbvEmpleados.getItems() == null || tbvEmpleados.getItems().stream().noneMatch(e -> e.equals(this.empleadoDto)))
        {
            this.empleadoDto.setModificado(true);
            tbvEmpleados.getItems().add(this.empleadoDto);
            tbvEmpleados.refresh();
        }
        nuevoEmpleado();
    }

    @FXML
    private void OnSelectionChangedTabEmpleados(Event event) {
        if (tabEmpleados.isSelected())
        {
            if (this.tipoPlanillaDto.getId() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Tipo planilla", getStage(), "Debe cargar el tipo de planilla al que desea agregar empleados.");
                tbpTipoPlanilla.getSelectionModel().select(tabTipoPlanillas);
            }
        }
    }

    private void nuevoTipoPlanilla() {
        unbindTipoPlanilla();
        tipoPlanillaDto = new TipoPlanillaDto();
        bindTipoPlanilla(true);
        txfId.clear();
        txfId.requestFocus();
        nuevoEmpleado();
        cargarEmpleados();
    }

    private void nuevoEmpleado() {
        unbindEmpleado();
        tbvEmpleados.getSelectionModel().clearSelection();
        // tbvEmpleados.getSelectionModel().select(null);
        this.empleadoDto = new EmpleadoDto();
        bindEmpleado(true);
        txfIdEmpleado.clear();
        txfIdEmpleado.requestFocus();
    }

    private void cargarEmpleados() {
        tbvEmpleados.getItems().clear();
        tbvEmpleados.setItems(this.tipoPlanillaDto.getEmpleados());

        System.out.println("INFO EMPLEADOS");
        for (EmpleadoDto emp : this.tipoPlanillaDto.getEmpleados())
        {
            System.out.println("Emp: " + emp.getNombre());
        }
        tbvEmpleados.refresh();
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

    private void bindEmpleado(Boolean nuevo) {
        if (!nuevo)
        {
            txfIdEmpleado.textProperty().bind(empleadoDto.id);
        }
        txfNombreEmpleado.textProperty().bindBidirectional(empleadoDto.nombre);
    }

    public void unbindEmpleado() {
        txfIdEmpleado.textProperty().unbind();
        txfNombreEmpleado.textProperty().unbindBidirectional(empleadoDto.nombre);
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
                cargarEmpleados();
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

    private class ButtonCell extends TableCell<EmpleadoDto, Boolean> {

        final Button cellButton = new Button();

        ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbveliminar");
            cellButton.setOnAction((t) ->
            {
                EmpleadoDto empDto = (EmpleadoDto) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                tipoPlanillaDto.getEmpleadosEliminados().add(empDto);
                tbvEmpleados.getItems().remove(empDto);
                tbvEmpleados.refresh();
            });

        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty)
            {
                setGraphic(cellButton);
            }
        }

    }

}
