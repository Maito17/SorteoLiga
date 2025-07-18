package com.mycompany.sorteoliga;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SorteoLiga { 

    public static List<String> obtenerEquipos(String etapa, int numEquipos) {
        Scanner scanner = new Scanner(System.in);
        List<String> equipos = new ArrayList<>();
        System.out.println("\n--- Ingrese los nombres de los equipos para " + etapa + " ---");
        for (int i = 0; i < numEquipos; i++) {
            while (true) {
                System.out.print("Ingrese el nombre del equipo " + (i + 1) + ": ");
                String nombreEquipo = scanner.nextLine().trim();
                if (!nombreEquipo.isEmpty()) {
                    equipos.add(nombreEquipo);
                    break;
                } else {
                    System.out.println("El nombre del equipo no puede estar vacío. Intente de nuevo.");
                }
            }
        }
        return equipos;
    }

    
    public static List<String[]> sorteoRecursivo(List<String> equiposDisponibles) {
       
        if (equiposDisponibles.size() < 2) {
            return new ArrayList<>();
        }

        // Crear una nueva lista para evitar modificar la lista original directamente
        List<String> equiposActualesDisponibles = new ArrayList<>(equiposDisponibles);
        Collections.shuffle(equiposActualesDisponibles); 

        String equipo1 = equiposActualesDisponibles.remove(0); 
        String equipo2 = equiposActualesDisponibles.remove(0); 

        String[] partidoActual = {equipo1, equipo2};

        
        List<String[]> partidosRestantes = sorteoRecursivo(equiposActualesDisponibles);

        
        partidosRestantes.add(0, partidoActual); 

        return partidosRestantes;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¡Bienvenido al sorteo de la liga de fútbol profesional!");

        // Definir el número de equipos para cada etapa
        Map<String, Integer> etapas = new HashMap<>();
        etapas.put("octavos", 16);
        etapas.put("cuartos", 8);
        etapas.put("semifinales", 4);
        etapas.put("finales", 2);

        while (true) {
            System.out.println("\nEtapas disponibles: octavos, cuartos, semifinales, finales");
            System.out.print("Ingrese la etapa para el sorteo (o 'salir' para terminar): ");
            String entradaEtapa = scanner.nextLine().toLowerCase().trim();

            if (entradaEtapa.equals("salir")) {
                System.out.println("¡Gracias por usar la aplicación de sorteo!");
                break;
            }

            if (!etapas.containsKey(entradaEtapa)) {
                System.out.println("Etapa no válida. Por favor, intente de nuevo.");
                continue;
            }

            int numEquiposParaEtapa = etapas.get(entradaEtapa);
            System.out.println("\nPreparando el sorteo para " + entradaEtapa + " de final (" + numEquiposParaEtapa + " equipos).");

            List<String> equipos = obtenerEquipos(entradaEtapa, numEquiposParaEtapa);

            
            List<String> equiposParaSorteo = new ArrayList<>(equipos);

            
            List<String[]> partidos = sorteoRecursivo(equiposParaSorteo);

            System.out.println("\n--- Sorteo de " + capitalizarPrimeraLetra(entradaEtapa) + " de Final ---");
            if (!partidos.isEmpty()) {
                for (int i = 0; i < partidos.size(); i++) {
                    String[] partido = partidos.get(i);
                    System.out.println("Partido " + (i + 1) + ": " + partido[0] + " vs " + partido[1]);
                }
            } else {
                System.out.println("No se pudieron generar partidos. Asegúrese de ingresar un número par de equipos.");
            }
        }
        scanner.close(); 
    }

    private static String capitalizarPrimeraLetra(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}