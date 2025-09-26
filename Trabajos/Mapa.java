import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.Timer;



public class Mapa {
    private final Map<String, Semaphore> cellLocks = new ConcurrentHashMap<>();


    // private static final int DEFAULT_VALUE = 1;
    private static final int MAP_WIDTH = 31;
    private static final int MAP_HEIGHT = 20;

    private String getKey(int y, int x) {
        return y + "," + x;
    }

    private Semaphore getValue(int y, int x) {
        return cellLocks.get(getKey(y, x));
    }
    
    private void setValue(int y, int x, int value) {
        cellLocks.get(getKey(y, x)).release(value);
    }
    
    public Mapa() {
        String pos;
        for(int y = 1;y<=MAP_HEIGHT;y++){
            for(int x = 1;x<=MAP_WIDTH;x++){
                pos = getKey(y,x);
                Semaphore s = new Semaphore(1);
                cellLocks.put(pos, s); 
            }
        }
    }


    public boolean createRobot(int[] coor) {
        Semaphore lock = cellLocks.get(getKey(coor[0], coor[1]));
        return lock != null && lock.tryAcquire(); // occupy cell
    }


    public boolean tryMove(int[] fromCoor, int[] mov) {
    int toY = fromCoor[0] + mov[0];
    int toX = fromCoor[1] + mov[1];

    Semaphore fromLock = cellLocks.get(getKey(fromCoor[0], fromCoor[1]));
    Semaphore toLock = cellLocks.get(getKey(toY, toX));

    if (toLock != null && toLock.tryAcquire()) {
        // if(getKey(fromCoor[0], fromCoor[1]).equals("1,9")) return true;

        fromLock.release();
        return true;
    }
    return false;
    }
    

    public synchronized boolean inicioLlenoBajo(){
        int ans = 4;

        for(int x = 12;x<16;x++){
            String pos = getKey(1,x);
            ans-=cellLocks.get(pos).availablePermits(); 
        }
        
        return ans==4;
    }
    public synchronized boolean inicioLlenoArriba(){
        int ans = 4;
  
        for(int x = 25;x<=28;x++){
            String pos = getKey(10,x);
            ans-=cellLocks.get(pos).availablePermits(); 
        }
        
        return ans==4;
    }
}

