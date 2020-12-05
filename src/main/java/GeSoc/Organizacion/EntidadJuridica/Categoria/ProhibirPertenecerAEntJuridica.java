package GeSoc.Organizacion.EntidadJuridica.Categoria;

import GeSoc.Organizacion.Entidad.EntidadJuridica;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("A")
public class ProhibirPertenecerAEntJuridica extends Funcionalidad{

	@ManyToOne(cascade = CascadeType.PERSIST)
	EntidadJuridica entJuridica;

	public ProhibirPertenecerAEntJuridica(EntidadJuridica entidadJuridica) {
		this.entJuridica = entidadJuridica;
	}

	public ProhibirPertenecerAEntJuridica() {

	}

	@Override
	public void puedePertenecerA(EntidadJuridica entJur) {
		if(entJuridica == entJur) {
			throw new RuntimeException("Esta entidad base no puede pertenecer a esta determinada entidad juridica");
		}
	}
}
