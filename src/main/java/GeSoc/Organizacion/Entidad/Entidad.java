package GeSoc.Organizacion.Entidad;
import GeSoc.Etiquetas.*;
import GeSoc.OperacionDeEgreso.OperacionDeEgreso;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Usuario.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Entidad {
	@Id
	@GeneratedValue
	public Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	public Usuario usuario;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "entidad_id")
	List<OperacionDeEgreso> operacionesEgreso = new ArrayList<>();

	@ManyToOne(cascade = CascadeType.PERSIST)
	public Categoria categoria;

	@SuppressWarnings("null")
	public List<Reporte> generarReportePorEtiqueta() {
		List<Reporte> reportes = new ArrayList<>();
		List<Etiqueta> etiquetas = RepoEtiquetas.getInstance().etiquetas();
		etiquetas.forEach(et -> reportes.add(new Reporte(et)));
		return reportes;
	}

	public int getCantEgresosAceptados() {
		return operacionesEgreso.size();
	}

	public void aceptarEgreso(OperacionDeEgreso op) {
		categoria.aceptarEgreso(this, op);
	}

	public void agregarCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public void agregarOpEgreso(OperacionDeEgreso op) {
		op.agregarUsuario(usuario);
		operacionesEgreso.add(op);
	}

	public Long getId() {
		return id;
	}

	public List<OperacionDeEgreso> getOperacionesEgreso() {
		return operacionesEgreso;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}
}

