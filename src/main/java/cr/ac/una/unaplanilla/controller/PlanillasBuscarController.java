package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.service.TipoPlanillaService;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PlanillasBuscarController extends Controller implements Initializable {

    ObservableList<TipoPlanillaDto> tiposPlanilla = FXCollections.observableArrayList();
    private TipoPlanillaDto resultado;
    private TipoPlanillaService tplaService;

    @FXML
    private MFXTextField txfCodigo;
    @FXML
    private MFXTextField txfDescripcion;
    @FXML
    private MFXTextField txfPlanillasXMes;
    @FXML
    private MFXTextField txfIdEmpleado;
    @FXML
    private MFXTextField txfCedula;
    @FXML
    private MFXButton btnFiltrar;
    @FXML
    private TableView<TipoPlanillaDto> tbvTablaEmpleados;
    @FXML
    private MFXButton btnAceptar;
    @FXML
    private TableColumn<TipoPlanillaDto, String> clmCodigo;
    @FXML
    private TableColumn<TipoPlanillaDto, String> clmDescripcion;
    @FXML
    private TableColumn<TipoPlanillaDto, String> clmPlanillasXMes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        tplaService = new TipoPlanillaService();
        txfCodigo.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(4));
        txfDescripcion.delegateSetTextFormatter(Formato.getInstance().letrasFormat(40));
        txfPlanillasXMes.delegateSetTextFormatter(Formato.getInstance().integerFormatWithMaxLength(2));

        clmCodigo.setCellValueFactory(cd -> cd.getValue().codigo);
        clmDescripcion.setCellValueFactory(cd -> cd.getValue().descripcion);
        clmPlanillasXMes.setCellValueFactory(cd -> cd.getValue().planillasXMes);
    }

    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
        filtrarDatos();
    }

    private void filtrarDatos() {
        String codigo = txfCodigo.getText();
        String descripcion = txfDescripcion.getText();
        String planillasXMes = txfPlanillasXMes.getText();

        Respuesta respuesta = tplaService.getTiposPlanillaFiltros(codigo, descripcion, planillasXMes);
        if (respuesta.getEstado())
        {
            tiposPlanilla.clear();
            tiposPlanilla.addAll((List<TipoPlanillaDto>) respuesta.getResultado("TiposPlanilla"));
            tbvTablaEmpleados.setItems(tiposPlanilla);
            tbvTablaEmpleados.refresh();

        } else
        {
            System.err.println("Error al obtener las tipoPlanillas: " + respuesta.getMensajeInterno());
        }
    }

    @FXML
    private void onActionBtnAceptar(ActionEvent event) {
        resultado = tbvTablaEmpleados.getSelectionModel().getSelectedItem();
        ((Stage) tbvTablaEmpleados.getScene().getWindow()).close();
    }

    @FXML
    private void OnMousePressedTbvTablaEmpleados(MouseEvent event) {

        if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
        {
            onActionBtnAceptar(null);
        }
    }

    public TipoPlanillaDto getResultado() {
        return resultado;
    }

}
