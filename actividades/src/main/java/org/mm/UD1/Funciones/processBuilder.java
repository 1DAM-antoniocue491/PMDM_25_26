package org.mm.UD1.Funciones;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class processBuilder {
    private static final Logger logger = Logger.getLogger(processBuilder.class.getName());

    public void ejecutar(List<String> commands)  {
        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder(commands);
            pb.start();
            System.out.println("Comando ejecutado");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al ejecutar el comando.", e);
        }
    }
}
