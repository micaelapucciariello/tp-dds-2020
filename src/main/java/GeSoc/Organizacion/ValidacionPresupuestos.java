package GeSoc.Organizacion;

import GeSoc.OperacionDeEgreso.OperacionDeEgreso;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ValidacionPresupuestos {
    @Id
    @GeneratedValue
    public Long id;

    public abstract Boolean validar(OperacionDeEgreso egreso, int presupuestosNecesariosPorEgreso);
}
