package org.example;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Calculadora {


    public static ArrayList<String> convertirAPostfija(String infija) {
        ArrayList<String> listaTokens = new ArrayList<>();
        Stack<String> pilaOperadores = new Stack<>();
        Scanner scanner = new Scanner(infija);

        while (scanner.hasNext()) {
            String token = scanner.next();

            // Si es numero, añadir a la lista de salida
            if (token.matches("\\d+")) {
                listaTokens.add(token);
            }
            // Si es un paréntesis izquierdo, lo ponemos en la pila
            else if (token.equals("(")) {
                pilaOperadores.push(token);
            }
            // Si es un paréntesis derecho, sacamos operadores hasta encontrar el izquierdo
            else if (token.equals(")")) {
                while (!pilaOperadores.isEmpty() && !pilaOperadores.peek().equals("(")) {
                    listaTokens.add(pilaOperadores.pop());
                }
                pilaOperadores.pop();
            }

            else if (esOperador(token)) {
                while (!pilaOperadores.isEmpty() && tieneMayorPrioridad(pilaOperadores.peek(), token)) {
                    listaTokens.add(pilaOperadores.pop());
                }
                pilaOperadores.push(token);
            }
        }

        // Sacamos todos los operadores que queden en la pila
        while (!pilaOperadores.isEmpty()) {
            listaTokens.add(pilaOperadores.pop());
        }

        scanner.close();
        return listaTokens;
    }
}
