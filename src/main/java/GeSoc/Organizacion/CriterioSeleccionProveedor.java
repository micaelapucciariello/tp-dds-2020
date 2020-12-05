package GeSoc.Organizacion;

import GeSoc.OperacionDeEgreso.OperacionDeEgreso;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Entity
@DiscriminatorValue("B")
public class CriterioSeleccionProveedor extends ValidacionPresupuestos {
    public Boolean validar(OperacionDeEgreso egreso, int presupuestosNecesariosPorEgreso) {
        List<BigDecimal> listaPresupuestos = egreso.totalesPresupuestos();
        BigDecimal minimo = Collections.min(listaPresupuestos);
        return egreso.valorTotal().equals(minimo);
    }
}
