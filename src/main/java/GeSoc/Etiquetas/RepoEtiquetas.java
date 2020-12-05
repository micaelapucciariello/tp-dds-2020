package GeSoc.Etiquetas;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;


public class RepoEtiquetas implements WithGlobalEntityManager {
	private static RepoEtiquetas instancia = null;

    private RepoEtiquetas() {}

    public static RepoEtiquetas getInstance() {
        if (instancia == null) {
            instancia = new RepoEtiquetas();
        }
        return instancia;
    }


    public void agregarEtiqueta(Etiqueta etiqueta) {
        entityManager().persist(etiqueta);
	}

    public void eliminarEtiqueta(Etiqueta etiqueta) {
        entityManager().remove(etiqueta);
	}

    @SuppressWarnings("unchecked")
    public List<Etiqueta> etiquetas() {
        return entityManager()
                .createQuery("from Etiqueta")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Etiqueta> filtrarPorIdentificador(String id) {
        return entityManager()
                .createQuery("from Etiqueta where identificador = :id")
                .setParameter("id", id)
                .getResultList();
    }

}
