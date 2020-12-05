package GeSoc.Usuario;

import GeSoc.OperacionDeEgreso.OperacionDeEgreso;

import javax.persistence.*;

@Entity
public class Mensaje {
	@Id
	@GeneratedValue
	public Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	public OperacionDeEgreso egresoRelacionado;

	public Boolean egresoValido;

	public Mensaje(OperacionDeEgreso egresoRelacionado, Boolean egresoValido){
		this.egresoRelacionado = egresoRelacionado;
		this.egresoValido = egresoValido;
	}

	public Mensaje() {

	}

	public String getValidez () {
		if (egresoValido) {
			return "Egreso válido";
		}
		else return "Egreso inválido";
	}

	public OperacionDeEgreso getEgresoRelacionado() {
		return egresoRelacionado;
	}
}
