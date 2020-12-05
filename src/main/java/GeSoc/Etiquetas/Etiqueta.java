package GeSoc.Etiquetas;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Etiqueta {
	@Id
	String identificador;

	public Etiqueta(String identificador) {
		this.identificador = identificador;
	}

	public Etiqueta() {

	}


	public String getIdentificador(){
		return identificador;
	}
}
