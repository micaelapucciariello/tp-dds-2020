package GeSoc;

import GeSoc.OperacionDeEgreso.OperacionDeEgreso;
import GeSoc.OperacionDeEgreso.RepositorioDeEgresos;
import GeSoc.OperacionDeEgreso.ResultadoDeValidacion.ResultadoDeValidacion;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class ValidacionAutomatica extends TimerTask {

    RepositorioDeEgresos repositorioDeEgresos = RepositorioDeEgresos.getInstance();

    @Override
    public void run() {
        List<OperacionDeEgreso> egresosPendientes = repositorioDeEgresos.egresosPendientes()
                .stream()
                .filter(egreso -> egreso.getUsuarioRevisor() != null)
                .collect(Collectors.toList());
        System.out.println("Realizando validacion...");
        egresosPendientes.forEach(OperacionDeEgreso::validar);
        egresosPendientes.forEach(egreso -> {
            if(egreso.resultadoDeValidacion.equals(ResultadoDeValidacion.ACEPTADO)) {
                System.out.println("Egreso válido");
            }
            else {
                System.out.println("Egreso inválido");
            }
        });
        egresosPendientes.forEach(egreso -> repositorioDeEgresos.actualizarResultadoDeValidacion(egreso));
        egresosPendientes.clear();
        PerThreadEntityManagers.getEntityManager();
        PerThreadEntityManagers.closeEntityManager();
    }

}
