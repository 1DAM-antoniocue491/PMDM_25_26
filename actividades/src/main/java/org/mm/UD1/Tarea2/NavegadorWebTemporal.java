package org.mm.UD1.Tarea2;

import org.mm.UD1.Funciones.processBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NavegadorWebTemporal {
    public static void main(String[] args) throws InterruptedException {
        Scanner teclado = new Scanner(System.in);
        processBuilder processBuilder = new processBuilder();

        while (true) {
            System.out.print("Indica la URL que desees abrir con Chrome: ");
            String url = teclado.next();
            List<String> command = new ArrayList<>(0);
            command.add(0, "C:\\Users\\2DAM-acuelop369\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
            command.add(1, url);
            processBuilder.ejecutar(command);
            Thread.sleep(10000);
        }
    }
}
