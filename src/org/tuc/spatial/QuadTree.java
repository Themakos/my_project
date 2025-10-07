package org.tuc.spatial;
import java.util.ArrayList;
public class QuadTree implements SpatialStructure {

    private int K; // world of size K x K //
    final private int D; // distance of points to be considered "near" //
    final private QuadTreeNode root;


    public QuadTree(int K, int D) {
        this.K = K;
        this.D = D;
        this.root = new QuadTreeNode(0,0, K, K);
    }


    @Override
    public boolean insert(TucPoint tucPoint) {
        return root.insert(tucPoint);
    }

    @Override
    public TucPoint search(int x, int y) {
        resetAccessCounter();
        return root.search(x, y, this);
    }

    @Override
    public ArrayList<TucPoint> rangeSearch(int x1, int y1, int x2, int y2) {
        resetAccessCounter();
        ArrayList<TucPoint> result = new ArrayList<>();
        root.rangeSearch(x1, y1, x2, y2, result, this );
        return result;
    }

    @Override
    public ArrayList<TucPoint> findNearPoints(int x, int y) {
        resetAccessCounter();
        ArrayList<TucPoint> result = new ArrayList<>();
        root.findNearPoints(x, y, D, result, this  );
        return result;
    }

    public int getK() {
        return K;
    }

    public void setK(int k) {
        K = k;
    }

    private int totalAccessCounter = 0;

    public void resetAccessCounter() {
        totalAccessCounter = 0;
    }

    public void incrementAccessCounter() {
        totalAccessCounter++;
    }

    public int getAccessCounter() {
        return totalAccessCounter;
    }
}