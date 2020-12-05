package GeSoc.Organizacion.EntidadJuridica.Categoria;

import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.Entidad.EntidadJuridica;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Funcionalidad {
	@Id
	@GeneratedValue
	public Long id;

	void puedeAceptarEgreso(Entidad ent){}
	void puedeAgregarEntBase(){}
	void puedePertenecerA(EntidadJuridica entJur){}
}
