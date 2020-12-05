package GeSoc.MedioDePago;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
public class Credito extends MedioDePago {
	@Enumerated
	public TipoDeCredito tipo;

	public Credito(TipoDeCredito tipo) {
		this.tipo = tipo;
	}

	public Credito() {

	}

	@Override
	public String getTipo(){
		return tipo.name();
	}
}

