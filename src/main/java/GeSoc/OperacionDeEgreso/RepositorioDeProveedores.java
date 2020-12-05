package GeSoc.OperacionDeEgreso;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioDeProveedores implements WithGlobalEntityManager {
    private static RepositorioDeProveedores instancia = null;

    private RepositorioDeProveedores() {}

    public static RepositorioDeProveedores getInstance() {
        if (instancia == null) {
            instancia = new RepositorioDeProveedores();
        }
        return instancia;
    }

    public void agregarProveedor(Proveedor proveedor) {
        entityManager().persist(proveedor);
    }

    public void eliminarProveedor(Proveedor proveedor) {
        entityManager().remove(proveedor);
    }


    @SuppressWarnings("unchecked")
    public List<Proveedor> getProveedores() {
        return entityManager()
                .createQuery("from Proveedor")
                .getResultList();
    }


    public List<Proveedor> buscarProveedor(Long id) {
        return entityManager()
                .createQuery("from Proveedor p where p.id = :id", Proveedor.class)
                .setParameter("id", id)
                .getResultList();
    }

}
