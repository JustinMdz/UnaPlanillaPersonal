package cr.ac.una.unaplanilla.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TipoPlanillaDto {

    private static final long serialVersionUID = 1L;
    public SimpleStringProperty id;
    public SimpleStringProperty codigo;
    public SimpleStringProperty descripcion;
    public SimpleStringProperty planillasXMes;
    private Integer anoUltimaPlanilla;
    private Integer mesUltimaPlanilla;
    private Integer numeroUltimaPlanilla;
    public SimpleBooleanProperty estado;
    private boolean modificado;
    ObservableList<EmpleadoDto> empleados;
    List<EmpleadoDto> empleadosEliminados;

    public TipoPlanillaDto() {
        this.modificado = false;
        this.id = new SimpleStringProperty("");
        this.codigo = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.planillasXMes = new SimpleStringProperty("");
        this.estado = new SimpleBooleanProperty(true);
        empleados = FXCollections.observableArrayList();
        empleadosEliminados = new ArrayList<>();
    }

    public TipoPlanillaDto(TipoPlanilla tipoPlanilla) {
        this();
        this.id.set(tipoPlanilla.getTplaId().toString());
        this.codigo.set(tipoPlanilla.getTplaCodigo());
        this.descripcion.set(tipoPlanilla.getTplaDescripcion());
        this.planillasXMes.set(tipoPlanilla.getTplaPlaxmes().toString());
        this.anoUltimaPlanilla = tipoPlanilla.getTplaAnoultpla();
        this.mesUltimaPlanilla = tipoPlanilla.getTplaMesultpla();
        this.numeroUltimaPlanilla = tipoPlanilla.getTplaNumultpla();
        this.estado.setValue(tipoPlanilla.getTplaEstado().equalsIgnoreCase("A"));
    }

    public Long getId() {
        if (id.get() != null && !id.get().isEmpty())
        {
            return Long.valueOf(id.get());
        } else
        {
            return null;
        }
    }

    public void setId(Long tplaId) {
        this.id.set(tplaId.toString());
    }

    public String getCodigo() {
        return codigo.get();
    }

    public void setCodigo(String tplaCodigo) {
        this.codigo.set(tplaCodigo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String tplaDescripcion) {
        this.descripcion.set(tplaDescripcion);
    }

    public Integer gettplaPlaxmes() {
        if (planillasXMes.get() != null && !planillasXMes.get().isEmpty())
        {
            return Integer.valueOf(planillasXMes.get());
        } else
        {
            return null;
        }
    }

    public void setPlanillasPorMes(Integer tplaPlaxmes) {
        this.planillasXMes.set(tplaPlaxmes.toString());
    }

    public Integer getAnoUltimaPlanilla() {
        return anoUltimaPlanilla;
    }

    public void setAnoUltimaPlanilla(Integer anoUltimaPlanilla) {
        this.anoUltimaPlanilla = anoUltimaPlanilla;
    }

    public Integer getMesUltimaPlanilla() {
        return mesUltimaPlanilla;
    }

    public void setMesUltimaPlanilla(Integer mesUltimaPlanilla) {
        this.mesUltimaPlanilla = mesUltimaPlanilla;
    }

    public Integer getNumeroUltimaPlanilla() {
        return numeroUltimaPlanilla;
    }

    public void setNumeroUltimaPlanilla(Integer numeroUltimaPlanilla) {
        this.numeroUltimaPlanilla = numeroUltimaPlanilla;
    }

    public String getEstado() {
        return estado.getValue() ? "A" : "I";
    }

    public void setEstado(String tplaEstado) {
        this.estado.setValue(tplaEstado.equalsIgnoreCase("A"));
    }

    public boolean getModificado() {
        return modificado;
    }

    public void setModificado(boolean modificado) {
        this.modificado = modificado;
    }

    public ObservableList<EmpleadoDto> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ObservableList<EmpleadoDto> empleados) {
        this.empleados = empleados;
    }

    public List<EmpleadoDto> getEmpleadosEliminados() {
        return empleadosEliminados;
    }

    public void setEmpleadosEliminados(List<EmpleadoDto> empleadosEliminados) {
        this.empleadosEliminados = empleadosEliminados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPlanillaDto))
        {
            return false;
        }
        TipoPlanillaDto other = (TipoPlanillaDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.TipoPlanillaDto[ empId=" + id + " ]";
    }
}
