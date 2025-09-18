import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class MapaHashMap {
    
    // Usamos ConcurrentHashMap para ser thread-safe, especialmente para lecturas como print()
    private Map<String, Integer> map = new ConcurrentHashMap<>();
    // Mapa para almacenar un semáforo por cada celda, permitiendo bloqueos específicos (fine-grained locking)
    private final Map<String, Semaphore> cellLocks = new ConcurrentHashMap<>();

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
    
    public MapaHashMap() {
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

    /**
     * Intenta crear un robot en una celda. Es una operación atómica y segura para hilos.
     * El robot que llama a este método adquiere el bloqueo de la celda si tiene éxito.
     * @param coor Coordenadas [y, x] donde crear el robot.
     * @return true si el robot fue creado y el bloqueo adquirido, false en caso contrario.
     */
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

    /**
     * Intenta mover un robot de una celda a otra de forma atómica.
     * @param fromCoor Coordenadas de origen.
     * @param mov      Vector de movimiento [dy, dx].
     * @return true si el movimiento fue exitoso, false si la celda de destino está ocupada o no tiene capacidad.
     */
    public boolean tryMove(int[] fromCoor, int[] mov) {
        int toY = fromCoor[0] + mov[0];
        int toX = fromCoor[1] + mov[1];

        String fromKey = getKey(fromCoor[0], fromCoor[1]);
        String toKey = getKey(toY, toX);

        Semaphore fromLock = cellLocks.get(fromKey);
        Semaphore toLock = cellLocks.computeIfAbsent(toKey, k -> new Semaphore(1));

        if (toLock.tryAcquire()) {
            // Se obtuvo el bloqueo de la celda de destino.
            try {
                if (getValue(toY, toX) - 1 >= 0) {
                    // La celda de destino tiene capacidad. Se realiza el movimiento.
                    setValue(fromCoor[0], fromCoor[1], getValue(fromCoor[0], fromCoor[1]) + 1);
                    setValue(toY, toX, getValue(toY, toX) - 1);

                    // Se libera el bloqueo de la celda de origen.
                    fromLock.release();
                    return true; // Movimiento exitoso.
                }
            } finally {
                // Si el movimiento no fue exitoso (p.ej. no hay capacidad), se libera el bloqueo de destino.
                if (!toLock.tryAcquire(0)) { // Si el bloqueo sigue en nuestro poder
                    toLock.release();
                }
            }
        }
        return false; // No se pudo adquirir el bloqueo de destino o no había capacidad.
    }
    
    public void printHashMapState() {
        // Gracias a ConcurrentHashMap, esta operación es segura.
        System.out.println("HashMap contents:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        System.out.println("Total entries: " + map.size());
    }
}
