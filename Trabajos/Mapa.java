import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.Timer;



public class Mapa {
    
    private Map<String, Integer> map = new ConcurrentHashMap<>();
    private final Map<String, Semaphore> cellLocks = new ConcurrentHashMap<>();
    private final Map<String, Semaphore> bahias = new ConcurrentHashMap<>();

    private static final int DEFAULT_VALUE = 1;
    private static final int MAP_WIDTH = 31;
    private static final int MAP_HEIGHT = 20;

    private String getKey(int y, int x) {
        return y + "," + x;
    }

    private int getValue(int y, int x) {
        return map.getOrDefault(getKey(y, x), DEFAULT_VALUE);
    }
    
    // Establecer valor
    private void setValue(int y, int x, int value) {
        if (value == DEFAULT_VALUE) {
            map.remove(getKey(y, x)); // No almacenar valores por defecto para ahorrar memoria
        } else {
            map.put(getKey(y, x), value);
        }
    }
    
    public Mapa() {
        }
    
    public void print() {
        // Gracias a ConcurrentHashMap, no necesitamos un bloqueo global para imprimir.
        // La impresión puede mostrar un estado intermedio, pero no lanzará una excepción.
        for(int y = 0; y < MAP_HEIGHT; y++) {
            for(int x = 0; x < MAP_WIDTH; x++) {
                System.out.print(getValue(y, x));
            }
            System.out.println();
        }
    }


    public boolean createRobot(int[] coor) {
        String key = getKey(coor[0], coor[1]);
        Semaphore lock = cellLocks.computeIfAbsent(key, k -> new Semaphore(1));

        if (lock.tryAcquire()) {
            // Se obtuvo el bloqueo de la celda, ahora se comprueba la capacidad.
            if (getValue(coor[0], coor[1]) - 1 >= 0) {
                setValue(coor[0], coor[1], getValue(coor[0], coor[1]) - 1);
                // Éxito. El hilo del robot ahora "posee" el bloqueo de esta celda.
                return true;
            } else {
                // No hay capacidad, se libera el bloqueo y se falla.
                lock.release();
                return false;
            }
        }
        // No se pudo adquirir el bloqueo, la celda está ocupada por otro robot.
        return false;
    }


    public boolean tryMove(int[] fromCoor, int[] mov) {
        int toY = fromCoor[0] + mov[0];
        int toX = fromCoor[1] + mov[1];
        String fromKey = getKey(fromCoor[0], fromCoor[1]);
        String toKey = getKey(toY, toX);

        Semaphore fromLock = cellLocks.get(fromKey);
        Semaphore toLock = cellLocks.computeIfAbsent(toKey, k -> new Semaphore(1));
        Semaphore bahiaFrom = cellLocks.get(fromKey);
        Semaphore bahiaTo = cellLocks.computeIfAbsent(toKey, k -> new Semaphore(1));
  

        if (toLock.tryAcquire()) {
            // revisar para quitar luego
            try {
                if (getValue(toY, toX) - 1 >= 0) {
                    setValue(fromCoor[0], fromCoor[1], getValue(fromCoor[0], fromCoor[1]) + 1);
                    setValue(toY, toX, getValue(toY, toX) - 1);
                    fromLock.release();
                    return true; 
                }
            } finally {
                if (!toLock.tryAcquire(0)) { 
                    toLock.release();
                }
            }
        }
        return false; 
    }
    
    public void printHashMapState() {
        System.out.println("HashMap contents:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        System.out.println("Total entries: " + map.size());
    }
}
