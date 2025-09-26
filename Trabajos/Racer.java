import kareltherobot.*;
import java.awt.Color;
import java.util.concurrent.Semaphore;

class Racer extends Robot implements Runnable {
    private Thread t;
    private int [] coor = new int[2];
    private final Mapa map;
    String dir;

    private static final Object bridge1Lock = new Object();
    private static final Object bridge2Lock = new Object();
    private static final Object bridge3Lock = new Object();

    private static final Semaphore permisorightCorto = new Semaphore(4);


    public Racer(int Street, int Avenue, Direction direction, Mapa map) {
        super(Street, Avenue, direction, 0, Color.BLUE);
        this.map = map;
        coor[0] = Street;
        coor[1] = Avenue;
        World.setupThread(this);
        t = new Thread(this);
        

        while (!map.createRobot(coor)) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }



    public int[] getCurrentDirection() {
        if (facingNorth()) return new int[]{1, 0};
        else if (facingEast())  return new int[]{0, 1};
        else if (facingSouth()) return new int[]{-1, 0};
        else  return new int[]{0, -1};
    }


    private synchronized void turnRight() {
        turnLeft();
        turnLeft();
        turnLeft();
    }


    public synchronized void move(){
        int[] mov = getCurrentDirection(); 

        while (!map.tryMove(coor, mov)) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        coor[0] += mov[0];
        coor[1] += mov[1];
        
        super.move(); 
    }
    
    
    public synchronized boolean nextCellFree(){
        int[] mov = getCurrentDirection(); 

        if(!map.nextCellFree(coor, mov)) {
            return false;
        }
        return true;

    }


    public void moveBridge1() {
        synchronized (bridge1Lock) {
            if ("r".equals(dir)) {
                move();
                permisorightCorto.release();
                for (int i = 0; i < 5; i++) move(); 
            } else {
                move();
                turnRight();
                for (int i = 0; i < 5; i++) move();
                turnRight();
                move();
            }
        }
    }


    public void moveBridge2() {
        synchronized (bridge2Lock) {
            if ("r".equals(dir)) {
                for (int i = 0; i < 5; i++) move(); 
            } else {
                move();
                turnRight();
                for (int i = 0; i < 3; i++) move();
                turnRight();
                move();
            }
        }
    }


    public void moveBridge3() {
        synchronized (bridge3Lock) {
            if ("r".equals(dir)) {
                for (int i = 0; i < 7; i++) move();
            } else {
                move();
                turnRight();
                for (int i = 0; i < 5; i++) move();
                turnRight();
                move();
            }
        }
    }


    public synchronized void zonaVerde() { // Robot expected at 12,30
        for(int i = 0; i<4;i++){
            if(!anyBeepersInBeeperBag())continue;
            putBeeper();
            
        }

        for (int i = 0; i < 4; i++) move();
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
        move(); // Ends at 12,23

        left();

    }

    public synchronized void zonaAzul() { // Robot expected at 2,8
        for(int i = 0; i<4;i++){
            if(!anyBeepersInBeeperBag())continue;
            putBeeper();
            
        }

        turnRight();
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
        for (int i = 0; i < 6; i++) move(); // Ends at 1,7
        right();
    }

    public synchronized void largoRight() {
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
        for (int i = 0; i < 2; i++) move();
        zonaVerde();
        
        largoLeft();
    }

    public synchronized void rapidoRight() {
        dir = "r";
        for (int i = 0; i < 4; i++) move(); // enter fast lane   
        moveBridge1();
        for (int i = 0; i < 4; i++) move(); 
        moveBridge2();

        turnLeft();
        for (int i = 0; i < 3; i++) move();

        moveBridge3();
        move();

        zonaVerde();

 

    }
    
    public synchronized void right() { 
        if (map.tryTakeBeepers("azul", 4)) {
            for (int i = 0; i < 4; i++) pickBeeper();
        }
        for (int i = 0; i < 4; i++) move(); 

        if(permisorightCorto.tryAcquire()){
            rapidoRight();
        }
        else largoRight();

        
  
    }

    public synchronized void largoLeft() { // Robot expected at 12,23

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
        for (int i = 0; i < 2; i++) move(); // ends at 2,8

        zonaAzul();
        largoRight();
  
    }


    public synchronized void rapidoLeft(){
        dir = "l";
        move();

        turnLeft();
        for (int i = 0; i < 5; i++) move(); 
        move();
        
        moveBridge3();
        turnLeft();
        for (int i = 0; i < 3; i++) move();

        moveBridge2();
        turnLeft();
        for (int i = 0; i < 5; i++) move();
        turnLeft();

        moveBridge1();
        turnLeft();
        for (int i = 0; i < 8; i++) move();

        zonaAzul();
        right();
        
    }

    public synchronized void left() {
        if (map.tryTakeBeepers("verde", 4)) {
            for (int i = 0; i < 4; i++) pickBeeper();
        }

        
        move(); 

       if (nextCellFree()) {
           rapidoLeft();
        }
        else{
            largoLeft();
        }
        
    }

    public synchronized void race() {
        if (coor[0] == 12) {
            this.left();
            return;
        }
        this.right();
    }

    public void run() {
        race();
    }

    public void start() {
        t.start(); 
    }
}
