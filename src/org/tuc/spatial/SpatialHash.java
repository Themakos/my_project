package org.tuc.spatial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpatialHash implements SpatialStructure{
    private final int K; /*world of size K*K */
    private final int D; /* distance of points to be considered "near" */
    private final int B; /*the world is subdivided into boxes of B*B */
    private final HashMap<Integer, ArrayList<TucPoint>> table;
    private int lastNodeCount; // counter for the last node count

    public SpatialHash(int K, int D, int B) {
        this.K = K;
        this.D = D;
        this.B = B;
        this.table = new HashMap<>();
    }

    public int getB() {
        return B;
    }

    public int getLastNodeCount() {
        return lastNodeCount;
    }

    private int hash(int x, int y) {
        return (y / B) * (K / B) + (x / B);
    }

    @Override
    public boolean insert(TucPoint tucPoint) {
        int index = hash(tucPoint.getX(), tucPoint.getY());
        
        
        ArrayList<TucPoint> cell = table.computeIfAbsent(index, k -> new ArrayList<>());
        /*Αν το κελι δν υπαρχει στο table τοτε το φτιαχνει και το προσθετει στο table
          αλλιως αν υπαρχει ηδη το επιστρεφει */
        

        for(TucPoint point : cell) {
            if(point.getX() == tucPoint.getX() && point.getY() == tucPoint.getY()) {
                return false; // Το σημειο υπαρχει ηδη
            }
        }

        cell.add(tucPoint);
        
        return true;
    }

    @Override
    public TucPoint search(int x, int y) {
        
        int countNodes = 0;
        int index = hash(x, y);
        List<TucPoint> cell = table.get(index);
        countNodes++; //+1


        if(cell != null) {
            
            for(TucPoint result : cell) {
                 countNodes++; //+1
                if(result.getX() == x && result.getY() == y) {
                    
                    lastNodeCount = countNodes; // Θετουμε το lastNodecount στην τιμη που θελουμε
                    return result; // Το σημειο βρεθηκε
                }
            }
        }
        lastNodeCount = countNodes; // Θετουμε το lastNodecount στην τιμη που θελουμε
        return null; // Το σημειο δεν βρεθηκε
    }

    @Override
    public ArrayList<TucPoint> rangeSearch(int x1, int y1, int x2, int y2) {
        ArrayList<TucPoint> result = new ArrayList<>();

        if(table == null) {
            System.out.println("Table is null");
            return result;
        }
        
        int minCellX = x1 / B;
        int maxCellX = x2 / B;
        int minCellY = y1 / B;
        int maxCellY = y2 / B;
        int nodeCount = 0;
        

        for(int i = minCellX; i <= maxCellX; i++) {
            for(int j = minCellY; j <= maxCellY; j++) {
                int cellIndex = (j * (K / B)) + i;
                
                List<TucPoint> cellPoints = table.get(cellIndex);
                nodeCount++;//+1
                
                
                if(cellPoints != null) {
                    for(TucPoint point : cellPoints) {
                        nodeCount++;//+1
                        if(point.getX() >= x1 && point.getX() <= x2 && point.getY() >= y1 && point.getY() <= y2) {
                            lastNodeCount = nodeCount;
                            result.add(point); // Το σημειο ειναι μεσα στο range
                        }
                    }
                }
                
            }
        }

        lastNodeCount = nodeCount;
        return result;
    }

    @Override
    public ArrayList<TucPoint> findNearPoints(int x, int y){

        return rangeSearch(x-D, y-D, x+D, y+D);
    }


    public void resetLastNodeCount() {
        lastNodeCount = 0;
    }
    
}
