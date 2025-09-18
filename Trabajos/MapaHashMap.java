import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class MapaHashMap {
    
    private Map<String, Integer> map = new HashMap<>();
    private static final int DEFAULT_VALUE = 1;
    private static final int MAP_WIDTH = 31;
    private static final int MAP_HEIGHT = 20;
    private static final Semaphore semaphore = new Semaphore(1);
    

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
        try {
            semaphore.acquire();
            for(int y = 0; y < MAP_HEIGHT; y++) {
                for(int x = 0; x < MAP_WIDTH; x++) {
                    System.out.print(getValue(y, x));
                }
                System.out.println();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public void createRobot(int[] coor) {
        int currentValue = getValue(coor[0], coor[1]);
        setValue(coor[0], coor[1], currentValue - 1);
    }

    public boolean reviewLocation(int[] coor) {
        return getValue(coor[0], coor[1]) - 1 >= 0;
    }
    
    public void move(int[] coor, int[] mov) {
        int currentValue = getValue(coor[0], coor[1]);
        setValue(coor[0], coor[1], currentValue + 1);
        
        int newY = coor[0] + mov[0];
        int newX = coor[1] + mov[1];
        int newValue = getValue(newY, newX);
        setValue(newY, newX, newValue - 1);
    }
    
    public void printHashMapState() {
        System.out.println("HashMap contents:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        System.out.println("Total entries: " + map.size());
    }
}
