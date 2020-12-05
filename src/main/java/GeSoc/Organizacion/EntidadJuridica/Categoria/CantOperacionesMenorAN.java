package GeSoc.Organizacion.EntidadJuridica.Categoria;

import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.Entidad.EntidadJuridica;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class CantOperacionesMenorAN extends Funcionalidad{
	public int cantidadDeOperaciones;

	public CantOperacionesMenorAN(int cantidadDeOperaciones) {
		this.cantidadDeOperaciones = cantidadDeOperaciones;
	}

	public CantOperacionesMenorAN() {

	}

	@Override
	public void  puedeAceptarEgreso(Entidad ent){
		if( ent.getCantEgresosAceptados() >= cantidadDeOperaciones) {
			throw new RuntimeException("No puede aceptar egreso porque su cantidad de egresos excede el limite");
		}
	}

}
