package GeSoc.Organizacion;

import GeSoc.OperacionDeEgreso.OperacionDeEgreso;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("A")
public class EnBaseAPresupuesto extends ValidacionPresupuestos {
    public Boolean validar(OperacionDeEgreso egreso, int presupuestosNecesariosPorEgreso) {
        if(presupuestosNecesariosPorEgreso == 0) {
            return true;
        }
        BigDecimal totalCompra = egreso.valorTotal();
        return egreso.totalesPresupuestos().contains(totalCompra);
    }
}
