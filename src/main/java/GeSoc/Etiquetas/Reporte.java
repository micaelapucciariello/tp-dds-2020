package GeSoc.Etiquetas;
import GeSoc.OperacionDeEgreso.OperacionDeEgreso;
import GeSoc.OperacionDeEgreso.RepositorioDeEgresos;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Reporte {
	@Id
	@GeneratedValue
	public Long id;

	@ManyToOne
	Etiqueta etiqueta;

	@OneToMany
	List<OperacionDeEgreso> operaciones;

	public LocalDate fecha;

	public Reporte(Etiqueta etiqueta){
		this.etiqueta = etiqueta;
		this.operaciones = RepositorioDeEgresos.getInstance().listarOperacionesParaReporte(etiqueta);
		this.fecha = LocalDate.now();
	}

	public Reporte() {

	}

	public BigDecimal gastosRealizados() {
		return operaciones
				.stream()
				.map(OperacionDeEgreso::valorTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
