package GeSoc.Organizacion;

import GeSoc.OperacionDeEgreso.OperacionDeEgreso;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
public class CantidadPresupuestos extends ValidacionPresupuestos {
	
    public Boolean validar(OperacionDeEgreso egreso, int presupuestosNecesariosPorEgreso) {
        return egreso.cantidadDePresupuestos() == presupuestosNecesariosPorEgreso;
    }
}
