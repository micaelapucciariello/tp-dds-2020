package GeSoc.Usuario;


import java.util.List;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioDeUsuarios implements WithGlobalEntityManager {
    private static RepositorioDeUsuarios instancia = null;

    private RepositorioDeUsuarios() {}

    public static RepositorioDeUsuarios getInstance() {
        if (instancia == null) {
            instancia = new RepositorioDeUsuarios();
        }
        return instancia;
    }

    public void agregarUsuario(Usuario usuario) {
        entityManager().persist(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        entityManager().remove(usuario);
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> getUsuarios() {
        return entityManager()
                .createQuery("from Usuario ")
                .getResultList();
    }

    public List<Usuario> buscarUsuario(String nombre, byte[] password) {
        return entityManager()
                .createQuery("from Usuario u where u.user = :nombre and u.password = :password", Usuario.class)
                .setParameter("nombre", nombre)
                .setParameter("password", password)
                .getResultList();
    }

    public void borrarUsuarios() {
         entityManager()
                .createQuery("delete from Usuario ").executeUpdate();
    }

    public List<Usuario> obtenerUsuarioPorNombre (String nombre) {
        return entityManager()
                .createQuery("from Usuario u where u.user = :nombre", Usuario.class)
                .setParameter("nombre", nombre)
                .getResultList();
    }

    public List<Usuario> obtenerUsuarioPorId(Long id) {
        return entityManager()
                .createQuery("from Usuario u where u.id = :id", Usuario.class)
                .setParameter("id", id)
                .getResultList();
    }

}
