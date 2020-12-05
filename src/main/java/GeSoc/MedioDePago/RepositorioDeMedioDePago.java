package GeSoc.MedioDePago;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;

public class RepositorioDeMedioDePago implements WithGlobalEntityManager {
    private static RepositorioDeMedioDePago instancia = null;

    private RepositorioDeMedioDePago() {}

    public static RepositorioDeMedioDePago getInstance() {
        if (instancia == null) {
            instancia = new RepositorioDeMedioDePago();
        }
        return instancia;
    }

    public void agregarMedioDePago(MedioDePago medioDePago) {
        entityManager().persist(medioDePago);
    }

    public void eliminarMedioDePago(MedioDePago medioDePago) {
        entityManager().remove(medioDePago);
    }


    @SuppressWarnings("unchecked")
    public List<MedioDePago> getMediosDePago() {
        return entityManager()
                .createQuery("from MedioDePago")
                .getResultList();
    }


    public List<MedioDePago> buscarMedioDePago(Long id) {
        return entityManager()
                .createQuery("from MedioDePago m where m.id = :id", MedioDePago.class)
                .setParameter("id", id)
                .getResultList();
    }

}