public class TestMapaHashMap {
    public static void main(String[] args) {
        System.out.println("=== Prueba de MapaHashMap ===");
        
        // Crear instancia del mapa
        MapaHashMap map = new MapaHashMap();
        
        // Probar impresión inicial
        System.out.println("\n1. Impresión inicial (debería mostrar todos 1s):");
        map.print();
        
        // Probar crear robot
        System.out.println("\n2. Creando robot en posición (5, 10):");
        int[] coor1 = {5, 10};
        map.createRobot(coor1);
        System.out.println("Después de crear robot:");
        map.print();
        
        // Probar reviewLocation
        System.out.println("\n3. Revisando ubicación (5, 10) - debería ser true:");
        boolean canPlace = map.reviewLocation(coor1);
        System.out.println("¿Puede colocar robot? " + canPlace);
        
        // Probar reviewLocation en posición ocupada
        System.out.println("\n4. Revisando ubicación (5, 10) otra vez - debería ser false:");
        boolean canPlaceAgain = map.reviewLocation(coor1);
        System.out.println("¿Puede colocar robot otra vez? " + canPlaceAgain);
        
        // Probar movimiento
        System.out.println("\n5. Moviendo robot de (5, 10) a (6, 10):");
        int[] mov = {1, 0}; // Mover hacia el sur
        map.move(coor1, mov);
        System.out.println("Después del movimiento:");
        map.print();
        
        // Verificar estado del HashMap
        System.out.println("\n6. Estado interno del HashMap:");
        map.printHashMapState();
        
        // Probar múltiples robots
        System.out.println("\n7. Creando múltiples robots:");
        int[] coor2 = {3, 15};
        int[] coor3 = {7, 20};
        map.createRobot(coor2);
        map.createRobot(coor3);
        System.out.println("Después de crear 2 robots más:");
        map.print();
        
        // Verificar estado final del HashMap
        System.out.println("\n8. Estado final del HashMap:");
        map.printHashMapState();
        
        System.out.println("\n=== Prueba completada ===");
    }
}
