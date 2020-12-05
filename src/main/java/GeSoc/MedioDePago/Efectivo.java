package GeSoc.MedioDePago;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
public class Efectivo extends MedioDePago {
	@Enumerated
	public TipoDeEfectivo tipo;

	public Efectivo(TipoDeEfectivo tipo) {
		this.tipo = tipo;
	}

	public Efectivo() {

	}

	@Override
	public String getTipo(){
		return tipo.name();
	}
}

