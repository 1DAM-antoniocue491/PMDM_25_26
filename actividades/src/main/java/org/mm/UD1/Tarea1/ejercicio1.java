package org.mm.UD1.Tarea1;

import org.mm.UD1.Funciones.processBuilder;
import org.mm.UD1.Funciones.runTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ejercicio1 {
    public static void main(String[] args) {
        List<String> argumentos = new ArrayList<>(Arrays.stream(args).toList());

        if (argumentos.size() >= 2) {
            if (argumentos.get(1).equals("C")) {
                argumentos.set(1, "C:\\Users\\2DAM-acuelop369\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
            } else {
                argumentos.set(1, "C:\\Windows\\System32\\notepad.exe");
            }
        }

        lanzamiento(argumentos);
    }

    private static void lanzamiento(List<String> commands) {
        if (commands.get(0).equals("P")) {
            commands.remove(0);
            processBuilder processBuilder = new processBuilder();
            processBuilder.ejecutar(commands);
        } else {
            commands.remove(0);
            runTime runTime = new runTime();
            runTime.ejecutar(commands.toArray(new String[0]));
        }
    }
}
