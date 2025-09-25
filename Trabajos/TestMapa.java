public class TestMapa {
    public static void main(String[] args) {
        System.out.println("=== Prueba de MapaHashMap ===");
        
        // Crear instancia del mapa
        Mapa map = new Mapa();
        
        // Probar impresión inicial
        System.out.println("\n1. Impresión inicial (debería mostrar todos 1s):");
        map.print();
        
        // Probar crear robot
        System.out.println("\n2. Creando robot en posición (5, 10):");
        int[] coor1 = {5, 10};
        boolean created = map.createRobot(coor1);
        System.out.println("Robot creado exitosamente: " + created);
        System.out.println("Después de crear robot:");
        map.print();
        
        // Probar crear robot en celda ocupada (debería fallar)
        System.out.println("\n3. Intentando crear otro robot en (5, 10) - debería fallar:");
        boolean canPlaceAgain = map.createRobot(coor1);
        System.out.println("¿Pudo crear otro robot? " + !canPlaceAgain);
        
        // Probar movimiento
        System.out.println("\n4. Moviendo robot de (5, 10) a (6, 10):");
        int[] mov = {1, 0}; // Mover hacia el sur
        boolean moved = map.tryMove(coor1, mov);
        System.out.println("Movimiento exitoso: " + moved);
        System.out.println("Después del movimiento:");
        map.print();
        
        // Verificar que la celda original está libre creando un nuevo robot
        System.out.println("\n5. Verificando que la celda original (5, 10) está libre:");
        boolean originalCellFree = map.createRobot(coor1);
        System.out.println("¿Pudo crear un robot en la celda original? " + originalCellFree);
        
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
