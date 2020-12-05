package GeSoc.OperacionDeEgreso;

import GeSoc.Etiquetas.Etiqueta;
import GeSoc.MedioDePago.MedioDePago;
import GeSoc.OperacionDeEgreso.ResultadoDeValidacion.*;
import GeSoc.Organizacion.ValidacionPresupuestos;
import GeSoc.Usuario.Mensaje;
import GeSoc.Usuario.Usuario;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class OperacionDeEgreso {
    @Id
    @GeneratedValue
    public Long id;

    @ManyToMany(cascade = CascadeType.PERSIST)
    Collection<Etiqueta> etiquetas = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    Proveedor proveedor;

    @ManyToOne(cascade = CascadeType.ALL)
    MedioDePago medioDePago;

    @ManyToOne(cascade = CascadeType.ALL)
    DocumentoComercial documentoComercial;

    @ManyToOne(cascade = CascadeType.ALL)
    Usuario usuarioRevisor;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "OperacionDeEgreso_id")
    public Collection<Presupuesto> presupuestos = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    public Presupuesto presupuestoElegido;

    @ManyToMany(cascade = CascadeType.PERSIST)
    public List<ValidacionPresupuestos> validacionesPresupuesto = new ArrayList<>();

    @Enumerated
    @Column(nullable = true)
    public ResultadoDeValidacion resultadoDeValidacion = null;

    static final int PRESUPUESTOS_NECESARIOS = 2;

    public OperacionDeEgreso() {

    }

    public OperacionDeEgreso(Proveedor proveedor, MedioDePago medioDePago, DocumentoComercial documentoComercial) {
        this.proveedor = proveedor;
        this.medioDePago = medioDePago;
        this.documentoComercial = documentoComercial;
    }

    public BigDecimal valorTotal() {
    	return presupuestoElegido.valorTotal();
    }

    public void elegirPresupuesto(Presupuesto presupuesto) {
        this.presupuestoElegido = presupuesto;
    }

    public int cantidadDePresupuestos() {
        return presupuestos.size();
    }

    public List<BigDecimal> totalesPresupuestos() {
        List<BigDecimal> totales = new ArrayList<>();
        presupuestos.forEach(presupuesto -> totales.add(presupuesto.valorTotal()));
        return totales;
    }
    public void agregarUsuario(Usuario usuario) {
        usuarioRevisor = usuario;
    }

    public Usuario getUsuarioRevisor() {
        return usuarioRevisor;
    }

    public Long getId() {
        return id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public DocumentoComercial getDocumentoComercial() {
        return documentoComercial;
    }

    public Boolean coincideParaReporte(Month mesAProbar, Etiqueta etiqueta) {
        return LocalDate.now().getMonth().equals(mesAProbar) && etiquetas.contains(etiqueta);
    }

    public void agregarValidacion(ValidacionPresupuestos validacion) {
    	validacionesPresupuesto.add(validacion);
    }
    
    public void eliminarValidacion(int id) {
    	validacionesPresupuesto.remove(id);
    }
    
    public void enviarMensaje(Mensaje mensaje) {
       usuarioRevisor.recibirMensaje(mensaje);
    }

    Boolean pasaLasValidaciones() {
        return validacionesPresupuesto.stream().allMatch(validacion -> validacion.validar(this, PRESUPUESTOS_NECESARIOS));
    }

    public void validar() {
        if(resultadoDeValidacion != null) {
            return;
        }

        if(pasaLasValidaciones()) {
            resultadoDeValidacion = ResultadoDeValidacion.ACEPTADO;
        }
        else {
            resultadoDeValidacion = ResultadoDeValidacion.RECHAZADO;
        }

        resultadoDeValidacion.enviarMensaje(this);
    }

    public void agregarEtiqueta(Etiqueta etiqueta) {
        etiquetas.add(etiqueta);
    }

    public void agregarPresupuesto(Presupuesto presupuesto) {
        presupuestos.add(presupuesto);
    }

	public boolean estaAceptada() {
		return resultadoDeValidacion.equals(ResultadoDeValidacion.ACEPTADO);
	}
    
}
