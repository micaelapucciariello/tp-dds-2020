package GeSoc;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {

        Routes.main(null);
        Timer timer = new Timer();
        ValidacionAutomatica validacion = new ValidacionAutomatica();

        // cada 5 segundos
        timer.schedule(validacion, 0, 5000);
    }
}
