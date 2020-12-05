package GeSoc.MedioDePago;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
public class Debito extends MedioDePago {
	@Enumerated
	public TipoDeDebito tipo;

	public Debito(TipoDeDebito tipo) {
		this.tipo = tipo;
	}

	public Debito() {

	}

	@Override
	public String getTipo(){
		return tipo.name();
	}
}

