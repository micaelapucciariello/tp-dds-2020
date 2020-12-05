package GeSoc.Organizacion.EntidadJuridica.Categoria;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;

public class RepoCategorias implements WithGlobalEntityManager {

	private static RepoCategorias instancia = null;

    private RepoCategorias() {}

    public static RepoCategorias getInstance() {
        if (instancia == null) {
            instancia = new RepoCategorias();
        }
        return instancia;
    }
	
	public void agregarCategoria(Categoria cat) {
		entityManager().persist(cat);
	}
	
	public void eliminarCategoria(Categoria cat) {
		entityManager().remove(cat);
	}
	
	public void modificarCategoria(Categoria catVieja, Categoria catNueva) {
		eliminarCategoria(catVieja);
		agregarCategoria(catNueva);
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> getCategorias() {
		return entityManager()
				.createQuery("from Categoria	")
				.getResultList();
	}

	public void borrarCategorias() {
		entityManager()
				.createQuery("delete from Categoria ")
				.executeUpdate();
	}

	public List<Categoria> getCategoriaById(Long id){
		return entityManager()
				.createQuery("from Categoria where id = :id", Categoria.class)
				.setParameter("id",id)
				.getResultList();
	}
	public List<Categoria> getCategoriasByUsuario(Long idUsuario){
		return entityManager()
				.createQuery("from Categoria where usuario.id = :id", Categoria.class)
				.setParameter("id",idUsuario)
				.getResultList();
	}

}
