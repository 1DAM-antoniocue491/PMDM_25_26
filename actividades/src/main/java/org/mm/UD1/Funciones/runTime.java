package org.mm.UD1.Funciones;

import java.util.logging.Level;
import java.util.logging.Logger;

public class runTime {
    private static final Logger logger = Logger.getLogger(runTime.class.getName());

    public void ejecutar(String[] commands) {
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec(commands);
            System.out.println("Comando ejecutado");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al ejecutar el comando.", e);
        }
    }
}
