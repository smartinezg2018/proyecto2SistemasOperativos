import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Mapa {
    
    int[][] map = new int[20][31]; 
    private static final Semaphore semaphore = new Semaphore(1);
    

    public Mapa(){
        for(int i = 0; i <19;i++){
            Arrays.fill(map[i], 1);
        }
    }
    
    public void print(){
            
        try {
            semaphore.acquire(); 
            for(int y = 0; y<19;y++){
                for(int x = 0; x<30;x++) System.out.print(map[y][x]);
                System.out.println();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); 
        }


    }

    public void createRobot(int[] coor){
        map[coor[0]][coor[1]]-=1;
    }

    public boolean reviewLocation(int [] coor){
        return map[coor[0]][coor[1]]-1>=0;
    }
    
    public void move(int[] coor ,int[] mov){
        // print();
        map[coor[0]][coor[1]]+=1;
        map[coor[0]+mov[0]][coor[1]+mov[1]]-=1;
    }


}
