package GeSoc;

import GeSoc.CreadorDeUsuarios.CreadorDeUsuarios;
import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Organizacion.EntidadJuridica.Categoria.RepoCategorias;
import GeSoc.Usuario.Administrador;
import GeSoc.Usuario.Estandar;
import GeSoc.Usuario.RepositorioDeUsuarios;
import GeSoc.exceptions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class TestCreadorDeUsuarios implements WithGlobalEntityManager, TransactionalOps {
    CreadorDeUsuarios creador;
    RepositorioDeUsuarios repoUsuarios;

    @Before
    public void init() {
        creador = CreadorDeUsuarios.getInstance();
        repoUsuarios = RepositorioDeUsuarios.getInstance();
    }

    @Test(expected = PasswordDebilException.class)
    public void UsarPasswordDebil() {
        creador.crearUsuario("usuario","password", new Estandar());
    }

    @Test
    public void AgregarDosUsuariosDeCadaTipo(){
        withTransaction(() -> {
            repoUsuarios.agregarUsuario(creador.crearUsuario("usuario1","contraseniaDificil", new Estandar()));
            repoUsuarios.agregarUsuario(creador.crearUsuario("usuario2","contraseniaDificil", new Estandar()));
            repoUsuarios.agregarUsuario(creador.crearUsuario("usuario3","contraseniaDificil", new Administrador()));
            repoUsuarios.agregarUsuario(creador.crearUsuario("usuario4","contraseniaDificil", new Administrador()));
        });
        Assert.assertNotEquals(0, repoUsuarios.getUsuarios().size());
    }

    @Test(expected = PasswordCortaException.class)
    public void UsarPasswordCorta() {
        creador.crearUsuario("usuario","corta", new Estandar());
    }

    @Test(expected = PasswordLargaException.class)
    public void UsarPasswordLarga() {
        creador.crearUsuario("usuario","aslkdhask;ldjha;skljdhak;lsjdhqiowueyhqoiywgelkahsbdlkjashgdlakshgdlkasgd", new Estandar());
    }

    @Test(expected = PasswordDebilException.class)
    public void PasswordConCaracteresRepetidosFalla() {
        creador.crearUsuario("usuario","aaaaaaaaaaaaaaaaaaaaaaa", new Estandar());
    }
}
