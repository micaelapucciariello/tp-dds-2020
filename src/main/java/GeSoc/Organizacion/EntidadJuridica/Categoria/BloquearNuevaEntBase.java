package GeSoc.Organizacion.EntidadJuridica.Categoria;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
public class BloquearNuevaEntBase extends Funcionalidad {

	@Override
	public void puedeAgregarEntBase() {
		throw new RuntimeException("El agregado de nuevas entidades base esta bloqueado");
	}
}
