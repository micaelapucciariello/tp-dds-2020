package GeSoc.Organizacion;

import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;

public class RepositorioDeEntidades implements WithGlobalEntityManager {

    private static RepositorioDeEntidades instancia = null;

    private RepositorioDeEntidades() {}

    public static RepositorioDeEntidades getInstance() {
        if (instancia == null) {
            instancia = new RepositorioDeEntidades();
        }
        return instancia;
    }

    public void agregarEntidad(Entidad entidad) {
        entityManager().persist(entidad);
    }

    public void eliminarEntidad(Entidad entidad) {
        entityManager().remove(entidad);
    }

    @SuppressWarnings("unchecked")
    public List<Entidad> listarEntidades() {
        return entityManager()
                .createQuery("from Entidad")
                .getResultList();
    }

    public List<Entidad> listarEntidadesPorUsuario(Long idUsuario) {
        return entityManager()
                .createQuery("from Entidad e	where e.usuario.id = :usuario", Entidad.class)
                .setParameter("usuario",idUsuario)
                .getResultList();
    }

    public List<Entidad> listarEntidadesPorCategoria(Long idCategoria) {
        return entityManager()
                .createQuery("from Entidad e	where e.categoria.id = :categoria", Entidad.class)
                .setParameter("categoria",idCategoria)
                .getResultList();
    }

    public List<Entidad> buscarEntidad(Long id) {
        return entityManager()
                .createQuery("from Entidad e where e.id = :id", Entidad.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void borrarEntidades() {
        entityManager()
                .createQuery("delete from Entidad ").executeUpdate();
    }

    public void modificarCategoria(Long entidadId, Categoria categoria) {
        entityManager()
                .createQuery("update Entidad ent SET ent.categoria.id = :categoriaNuevaId WHERE ent.id = :entidadId")
                .setParameter("categoriaNuevaId", categoria.getId())
                .setParameter("entidadId", entidadId);
    }
}
