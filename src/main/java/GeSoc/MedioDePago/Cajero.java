package GeSoc.MedioDePago;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
public class Cajero extends MedioDePago {
	@Enumerated
	public TipoDeCajero tipo;

	public Cajero(TipoDeCajero tipo) {
		this.tipo = tipo;
	}

	public Cajero() {

	}

	@Override
	public String getTipo(){
		return tipo.name();
	}
}

