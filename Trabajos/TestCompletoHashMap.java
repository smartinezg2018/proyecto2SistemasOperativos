import kareltherobot.*; 
import java.awt.Color; 

public class TestCompletoHashMap implements Directions { 
    public static void main(String[] args) { 

        World.readWorld("mundos/parte2.kwld"); 
        World.setVisible(true); 
        World.setDelay(50);

        // Usar la nueva implementación con HashMap
        MapaHashMap map = new MapaHashMap();

        System.out.println("=== Iniciando simulación con HashMap ===");
        System.out.println("Mapa inicial:");
        map.print();
        
        System.out.println("\nEstado inicial del HashMap:");
        map.printHashMapState();

        // Crear solo 2 robots para prueba
        RacerHashMap second = new RacerHashMap(1, 7, East, map); // largoAzul
        RacerHashMap first = new RacerHashMap(12, 23, South, map); // largoMorado
        
        System.out.println("\nEstado después de crear robots:");
        map.printHashMapState();
        
        first.start();
        second.start();

    }
} 
