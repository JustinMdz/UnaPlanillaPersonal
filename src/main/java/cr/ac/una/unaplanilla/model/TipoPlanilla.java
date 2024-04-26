package cr.ac.una.unaplanilla.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author justi
 */
@Entity
@Table(name = "PLAM_TIPOPLANILLAS")
@NamedQueries({
    @NamedQuery(name = "TipoPlanilla.findAll", query = "SELECT t FROM TipoPlanilla t"),
    @NamedQuery(name = "TipoPlanilla.findByTplaId", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaId = :tplaId"),
    @NamedQuery(name = "TipoPlanilla.findByTplaCodigo", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaCodigo = :tplaCodigo"),
    @NamedQuery(name = "TipoPlanilla.findByTplaDescripcion", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaDescripcion = :tplaDescripcion"),
    @NamedQuery(name = "TipoPlanilla.findByTplaPlaxmes", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaPlaxmes = :tplaPlaxmes"),
    @NamedQuery(name = "TipoPlanilla.findByTplaAnoultpla", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaAnoultpla = :tplaAnoultpla"),
    @NamedQuery(name = "TipoPlanilla.findByTplaMesultpla", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaMesultpla = :tplaMesultpla"),
    @NamedQuery(name = "TipoPlanilla.findByTplaNumultpla", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaNumultpla = :tplaNumultpla"),
    @NamedQuery(name = "TipoPlanilla.findByTplaEstado", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaEstado = :tplaEstado"),
    @NamedQuery(name = "TipoPlanilla.findByTplaVersion", query = "SELECT t FROM TipoPlanilla t WHERE t.tplaVersion = :tplaVersion")})
public class TipoPlanilla implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TPLA_ID")
    private BigDecimal tplaId;
    @Basic(optional = false)
    @Column(name = "TPLA_CODIGO")
    private String tplaCodigo;
    @Basic(optional = false)
    @Column(name = "TPLA_DESCRIPCION")
    private String tplaDescripcion;
    @Basic(optional = false)
    @Column(name = "TPLA_PLAXMES")
    private BigInteger tplaPlaxmes;
    @Column(name = "TPLA_ANOULTPLA")
    private BigInteger tplaAnoultpla;
    @Column(name = "TPLA_MESULTPLA")
    private BigInteger tplaMesultpla;
    @Column(name = "TPLA_NUMULTPLA")
    private BigInteger tplaNumultpla;
    @Basic(optional = false)
    @Column(name = "TPLA_ESTADO")
    private String tplaEstado;
    @Basic(optional = false)
    @Column(name = "TPLA_VERSION")
    private BigInteger tplaVersion;
    @JoinTable(name = "PLAM_EMPLEADOSPLANILLA", joinColumns = {
        @JoinColumn(name = "EXP_IDTPLA", referencedColumnName = "TPLA_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "EXP_IDEMP", referencedColumnName = "EMP_ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Empleado> empleados;

    public TipoPlanilla() {
    }

    public TipoPlanilla(BigDecimal tplaId) {
        this.tplaId = tplaId;
    }

    public TipoPlanilla(BigDecimal tplaId, String tplaCodigo, String tplaDescripcion, BigInteger tplaPlaxmes, String tplaEstado, BigInteger tplaVersion) {
        this.tplaId = tplaId;
        this.tplaCodigo = tplaCodigo;
        this.tplaDescripcion = tplaDescripcion;
        this.tplaPlaxmes = tplaPlaxmes;
        this.tplaEstado = tplaEstado;
        this.tplaVersion = tplaVersion;
    }

    public BigDecimal getTplaId() {
        return tplaId;
    }

    public void setTplaId(BigDecimal tplaId) {
        this.tplaId = tplaId;
    }

    public String getTplaCodigo() {
        return tplaCodigo;
    }

    public void setTplaCodigo(String tplaCodigo) {
        this.tplaCodigo = tplaCodigo;
    }

    public String getTplaDescripcion() {
        return tplaDescripcion;
    }

    public void setTplaDescripcion(String tplaDescripcion) {
        this.tplaDescripcion = tplaDescripcion;
    }

    public BigInteger getTplaPlaxmes() {
        return tplaPlaxmes;
    }

    public void setTplaPlaxmes(BigInteger tplaPlaxmes) {
        this.tplaPlaxmes = tplaPlaxmes;
    }

    public BigInteger getTplaAnoultpla() {
        return tplaAnoultpla;
    }

    public void setTplaAnoultpla(BigInteger tplaAnoultpla) {
        this.tplaAnoultpla = tplaAnoultpla;
    }

    public BigInteger getTplaMesultpla() {
        return tplaMesultpla;
    }

    public void setTplaMesultpla(BigInteger tplaMesultpla) {
        this.tplaMesultpla = tplaMesultpla;
    }

    public BigInteger getTplaNumultpla() {
        return tplaNumultpla;
    }

    public void setTplaNumultpla(BigInteger tplaNumultpla) {
        this.tplaNumultpla = tplaNumultpla;
    }

    public String getTplaEstado() {
        return tplaEstado;
    }

    public void setTplaEstado(String tplaEstado) {
        this.tplaEstado = tplaEstado;
    }

    public BigInteger getTplaVersion() {
        return tplaVersion;
    }

    public void setTplaVersion(BigInteger tplaVersion) {
        this.tplaVersion = tplaVersion;
    }

    public List<Empleado> getEmpleadoList() {
        return empleados;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleados = empleadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tplaId != null ? tplaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPlanilla)) {
            return false;
        }
        TipoPlanilla other = (TipoPlanilla) object;
        if ((this.tplaId == null && other.tplaId != null) || (this.tplaId != null && !this.tplaId.equals(other.tplaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.TipoPlanilla[ tplaId=" + tplaId + " ]";
    }
    
}
