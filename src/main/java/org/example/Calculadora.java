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

    public static double evaluarPostfija(ArrayList<String> postfija) {
        Stack<Double> pilaResultados = new Stack<>();

        for (String token : postfija) {
            if (token.matches("\\d+")) {
                pilaResultados.push(Double.parseDouble(token)); // Añade los números a la pila
            } else if (esOperador(token)) {
                double operando2 = pilaResultados.pop();
                double operando1 = pilaResultados.pop();
                double resultado = aplicarOperador(token, operando1, operando2);
                pilaResultados.push(resultado);
            }
        }

        return pilaResultados.pop(); // El resultado final queda en la pila
    }


    public static double aplicarOperador(String operador, double operando1, double operando2) {
        switch (operador) {
            case "+":
                return operando1 + operando2;
            case "-":
                return operando1 - operando2;
            case "*":
                return operando1 * operando2;
            case "/":
                return operando1 / operando2;
            case "^":
                return Math.pow(operando1, operando2);
            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }

    public static boolean esOperador(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
    }

    public static boolean tieneMayorPrioridad(String op1, String op2) {
        return obtenerPrecedencia(op1) >= obtenerPrecedencia(op2);
    }

    public static int obtenerPrecedencia(String operador) {
        switch (operador) {
            case "^":
                return 3;
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        while (true) {
            System.out.println("Introduce una expresión aritmética en notación infija (o 'quit' para salir): ");
            String expresionInfija = teclado.nextLine();

            if (expresionInfija.equalsIgnoreCase("quit")) {
                break;
            }

            ArrayList<String> expresionPostfija = convertirAPostfija(expresionInfija);
            System.out.println("Expresión en notación postfija: " + expresionPostfija);

            double resultado = evaluarPostfija(expresionPostfija);
            System.out.println("Resultado: " + resultado);
        }

        teclado.close();
    }
}
