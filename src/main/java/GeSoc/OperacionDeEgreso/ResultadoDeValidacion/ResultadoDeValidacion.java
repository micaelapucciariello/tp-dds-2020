package GeSoc.OperacionDeEgreso.ResultadoDeValidacion;

import GeSoc.OperacionDeEgreso.OperacionDeEgreso;
import GeSoc.Usuario.Mensaje;

public enum ResultadoDeValidacion {

    RECHAZADO(false),
    ACEPTADO(true);

    private final boolean resultado;

    ResultadoDeValidacion(boolean resultado) {
        this.resultado = resultado;
    }

    public void enviarMensaje(OperacionDeEgreso egreso) {
        egreso.getUsuarioRevisor().recibirMensaje(new Mensaje(egreso, this.resultado));
    }

}
