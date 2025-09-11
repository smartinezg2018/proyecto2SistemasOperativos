import kareltherobot.*;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;

class RacerAzul extends Robot implements Runnable {
    private Thread t;
    private AtomicInteger shared;

    public RacerAzul(int Street, int Avenue, Direction direction, int beeps, AtomicInteger ref) {
        super(Street, Avenue, direction, beeps, Color.BLUE);
        this.shared = ref;
        World.setupThread(this);
        t = new Thread(this);
    }
    
    public synchronized void race() {
        for (int i = 0; i < 27; i++) move();
        turnLeft();
        for (int i = 0; i < 8; i++) move();
        turnOff();
    }

    public void run() {
        race();
    }

    public void start() {
        t.start();
    }
}
