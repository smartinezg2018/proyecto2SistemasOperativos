import kareltherobot.*;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;

class Racer extends Robot implements Runnable {
    private Thread t;
    private int [] coor = new int[2];
    public static Mapa map;

    public Racer(int Street, int Avenue, Direction direction, Mapa map) {
        super(Street, Avenue, direction, 0, Color.BLUE);
        this.map = map;
        coor[0] = Street;
        coor[1] = Avenue;
        World.setupThread(this);
        t = new Thread(this);
        if(map.reviewLocation(coor))map.createRobot(coor);
    }
    public int[] getCurrentDirection() {
        if (facingNorth()) return new int[]{1, 0};
        else if (facingEast())  return new int[]{0, 1};
        else if (facingSouth()) return new int[]{-1, 0};
        else  return new int[]{0, -1};
        
    }

    public void move(){
        int[] mov = getCurrentDirection();
        map.move(coor,mov);
        map.print();
        coor[0]+=mov[0];
        coor[1]+=mov[1];
        super.move();
    }
    
    private synchronized void turnRight() {
        turnLeft();
        turnLeft();
        turnLeft();
    }
    public synchronized void largoAzul(){
        for (int i = 0; i < 4; i++) move();
        turnLeft();
        for (int i = 0; i < 10; i++) move();
        turnLeft();
        for (int i = 0; i < 3; i++) move();
        turnRight();
        for (int i = 0; i < 3; i++) move();
        turnRight();
        for (int i = 0; i < 8; i++) move();
        turnRight();
        for (int i = 0; i < 4; i++) move();
        turnRight();
        for (int i = 0; i < 3; i++) move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnLeft();
        for (int i = 0; i < 7; i++) move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnRight();
        for (int i = 0; i < 10; i++) move();
        turnLeft();
        for (int i = 0; i < 6; i++) move();
        turnLeft();
        move();
        turnLeft();
        move();
        turnRight();
        for (int i = 0; i < 6; i++) move();
        turnLeft();
        move();
        turnLeft();
        for (int i = 0; i < 6; i++) move();
        turnRight();

        for (int i = 0; i < 2; i++) move();
        turnRight();
        move();
        turnRight();
        move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnLeft();
        move();
        largoMorado();





    }



    public synchronized void largoMorado(){
        move();
        turnRight();
        for (int i = 0; i < 3; i++) move();
        turnRight();
        for (int i = 0; i < 8; i++) move();
        turnLeft();
        for (int i = 0; i < 2; i++) move();
        turnLeft();
        for (int i = 0; i < 4; i++) move();
        turnRight();
        for (int i = 0; i < 17; i++) move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnLeft();
        for (int i = 0; i < 9; i++) move();
        turnRight();
        for (int i = 0; i < 8; i++) move();
        turnRight();
        for (int i = 0; i < 2; i++) move();
        turnRight();
        // inicia recorrido zona verda
        for (int i = 0; i < 2; i++) move();
        turnLeft();
        for (int i = 0; i < 7; i++) move();
        turnLeft();
        move();
        turnLeft();
        for (int i = 0; i < 6; i++) move();
        turnRight();
        move();
        turnRight();
        for (int i = 0; i < 6; i++) move();
        turnLeft();
        move();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        move();





        largoAzul();

        turnOff();
    }

    public synchronized void race() {
        if(coor[0] == 12){
            this.largoMorado();
            return;

        }
        this.largoAzul();

    }

    public void run() {
        race();
    }

    public void start() {
        t.start(); 
    }
}
