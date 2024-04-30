package cr.ac.una.unaplanilla.model;

import java.io.Serializable;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmpleadoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public SimpleStringProperty id;
    public SimpleStringProperty nombre;
    public SimpleStringProperty primerApellido;
    public SimpleStringProperty segundoApellido;
    public SimpleStringProperty cedula;
    public ObjectProperty<String> genero;
    public SimpleStringProperty correo;
    public SimpleBooleanProperty administrador;
    public SimpleStringProperty usuario;
    public SimpleStringProperty clave;
    public ObjectProperty<LocalDate> fechaIngreso;
    public ObjectProperty<LocalDate> fechaSalida;
    public SimpleBooleanProperty estado;
    private Long version;
    private boolean modificado;
    // private List<TipoPlanilla> tiposPlanilla;

    public EmpleadoDto() {
        this.id = new SimpleStringProperty("");
        this.nombre = new SimpleStringProperty("");
        this.primerApellido = new SimpleStringProperty("");
        this.segundoApellido = new SimpleStringProperty("");
        this.cedula = new SimpleStringProperty("");
        this.genero = new SimpleObjectProperty("M");
        this.correo = new SimpleStringProperty("");
        this.administrador = new SimpleBooleanProperty(false);
        this.usuario = new SimpleStringProperty("");
        this.clave = new SimpleStringProperty("");
        this.fechaIngreso = new SimpleObjectProperty(LocalDate.now());
        this.fechaSalida = new SimpleObjectProperty();
        this.estado = new SimpleBooleanProperty(true);
        this.modificado = false;
    }

    public EmpleadoDto(Empleado empleado) {
        this();
        this.nombre.set(empleado.getNombre());
        this.primerApellido.set(empleado.getPrimerApellido());
        this.segundoApellido.set(empleado.getSegundoApellido());
        this.cedula.set(empleado.getCedula());
        this.genero.set(empleado.getGenero());
        this.correo.set(empleado.getCorreo());
        this.administrador.set(empleado.getAdministrador().equals("S"));
        this.usuario.set(empleado.getUsuario());
        this.clave.set(empleado.getClave());
        this.fechaIngreso.set(empleado.getFechaIngreso());
        this.fechaSalida.set(empleado.getFechaSalida());
        this.estado.set(empleado.getEstado().equals("A"));
        this.version = empleado.getVersion();
    }

//    public EmpleadoDto(SimpleStringProperty id, SimpleStringProperty nombre, SimpleStringProperty primerApellido, SimpleStringProperty segundoApellido, SimpleStringProperty cedula, ObjectProperty<String> genero, SimpleStringProperty correo, SimpleStringProperty administrador, SimpleStringProperty usuario, SimpleStringProperty clave, ObjectProperty<LocalDate> fechaIngreso, ObjectProperty<LocalDate> fechaSalida, SimpleStringProperty estado) {
//        this.id = id;
//        this.nombre = nombre;
//        this.primerApellido = primerApellido;
//        this.segundoApellido = segundoApellido;
//        this.cedula = cedula;
//        this.genero = genero;
//        this.correo = correo;
//        this.administrador = administrador;
//        this.usuario = usuario;
//        this.clave = clave;
//        this.fechaIngreso = fechaIngreso;
//        this.fechaSalida = fechaSalida;
//        this.estado = estado;
//    }
    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank())
        {
            return Long.valueOf(this.id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getPrimerApellido() {
        return primerApellido.get();
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido.set(primerApellido);
    }

    public String getSegundoApellido() {
        return segundoApellido.get();
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido.set(segundoApellido);
    }

    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getGenero() {
        return genero.get();
    }

    public void setGenero(String genero) {
        this.genero.set(genero);
    }

    public String getCorreo() {
        return correo.get();
    }

    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

    public String getAdministrador() {
        return administrador.get() ? "S" : "N";
    }

    public void setAdministrador(String administrador) {
        this.administrador.set(administrador.equalsIgnoreCase("S"));
    }

    public String getUsuario() {
        return usuario.get();
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }

    public String getClave() {
        return clave.get();
    }

    public void setClave(String clave) {
        this.clave.set(clave);
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso.get();
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso.set(fechaIngreso);
    }

    public LocalDate getFechaSalida() {
        return fechaSalida.get();
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida.set(fechaSalida);
    }

    public String getEstado() {
        return estado.get() ? "A" : "I";
    }

    public void setEstado(String estado) {
        this.estado.set(estado.equalsIgnoreCase("A"));
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isModificado() {
        return modificado;
    }

    public void setModificado(boolean modificado) {
        this.modificado = modificado;
    }

//    public List<TipoPlanilla> getTiposPlanilla() {
//        return tiposPlanilla;
//    }
//
//    public void setTiposPlanilla(List<TipoPlanilla> tiposPlanilla) {
//        this.tiposPlanilla = tiposPlanilla;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoDto))
        {
            return false;
        }
        EmpleadoDto other = (EmpleadoDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.EmpleadoDto[ empId=" + id + " ]";
    }

}
