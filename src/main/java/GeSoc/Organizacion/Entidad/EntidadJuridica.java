package GeSoc.Organizacion.Entidad;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class EntidadJuridica extends Entidad {

	public String razonSocial;

	public String getRazonSocial() {
		return razonSocial;
	}

	public void agregarAEntBase(EntidadBase ent) {
		categoria.agregarEntBase(ent, this);
	}

}
