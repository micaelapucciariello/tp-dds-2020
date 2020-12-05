package GeSoc.OperacionDeEgreso;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import GeSoc.Etiquetas.Etiqueta;
import GeSoc.OperacionDeEgreso.ResultadoDeValidacion.ResultadoDeValidacion;

import GeSoc.Organizacion.Entidad.Entidad;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioDeEgresos implements WithGlobalEntityManager {
    private static RepositorioDeEgresos instancia = null;

    private RepositorioDeEgresos() {}

    public static RepositorioDeEgresos getInstance() {
        if (instancia == null) {
            instancia = new RepositorioDeEgresos();
        }
        return instancia;
    }

    public void agregarEgreso(OperacionDeEgreso egreso) {
        entityManager().persist(egreso);
    }

    public void eliminarEgreso(OperacionDeEgreso egreso) {
        entityManager().remove(egreso);
    }


    @SuppressWarnings("unchecked")
    public List<OperacionDeEgreso> getEgresos() {
        return entityManager()
                .createQuery("from OperacionDeEgreso")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<OperacionDeEgreso> listarOperacionesParaReporte(Etiqueta etiqueta) {
        Month mesActual = LocalDate.now().getMonth();
        return (List<OperacionDeEgreso>) entityManager()
                .createQuery("FROM OperacionDeEgreso")
                .getResultList()
                .stream()
                .filter(op -> ((OperacionDeEgreso) op).coincideParaReporte(mesActual, etiqueta))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<OperacionDeEgreso> egresosPendientes() {
        return entityManager()
                .createQuery("from OperacionDeEgreso where resultadoDeValidacion is null")
                .getResultList();
    }

    public void actualizarResultadoDeValidacion(OperacionDeEgreso egreso) {
        entityManager().getTransaction().begin();
        entityManager()
                .createQuery("update OperacionDeEgreso e SET resultadoDeValidacion = :resultado WHERE e.id = :id")
                .setParameter("resultado", egreso.resultadoDeValidacion)
                .setParameter("id", egreso.id);
        entityManager().getTransaction().commit();
    }
    public List<OperacionDeEgreso> buscarEgreso(Long id) {
        return entityManager()
                .createQuery("from OperacionDeEgreso e where e.id = :id", OperacionDeEgreso.class)
                .setParameter("id", id)
                .getResultList();
    }

}
