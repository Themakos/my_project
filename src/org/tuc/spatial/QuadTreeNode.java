package org.tuc.spatial;

import java.util.ArrayList;

public class QuadTreeNode {

    int x, y, width , height ;
    TucPoint point;
    QuadTreeNode nw , ne , sw , se;



    public QuadTreeNode(int x , int y , int width , int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.point= null;
        this.nw  = null;
        this.ne   = null;
        this.sw  = null;
        this.se  = null;
    }
    boolean isLeaf(){
        return nw==null&&  ne == null && sw== null && se==null ;
 }
    boolean contains (int px , int py ){
        return px >= x && px < x + width && py >= y && py < y + height;

 }

    boolean insert (TucPoint tucPoint){
        if(!contains(tucPoint.getX(), tucPoint.getY())){
            return false;
        }

        if(isLeaf()){
            if(point==null){
                point= tucPoint;
                return true;
            }else {
                // αν υπαρχει ηδη σημειο εκει που παω να κανω ινσερτ, το υποδιαιρω περεταιρω :
                split();
                //εισαγωγη του σημειου στη σωστη θεση σε εναν απο τα 4 υποτετραγωνα(sw, se ,nw , ne)
                insertToChildren(point);
                // ==null γιατι το σημειο που ηταν να εισαχθει στον κομβο , εχει μεταφερθει σε ενα παιδι του
                point= null;
            }
        }
     // Προσπάθεια εισαγωγής στο κατάλληλο παιδί
     return insertToChildren(tucPoint);
 }

    private boolean insertToChildren(TucPoint p) {
        if (nw.insert(p))
            return true;
        if (ne.insert(p))
            return true;
        if (sw.insert(p))
            return true;
        if (se.insert(p))
            return true;
        else
            return false;
    }

    private void split() {
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        nw = new QuadTreeNode(x, y, halfWidth, halfHeight);
        ne = new QuadTreeNode(x + halfWidth, y, halfWidth, halfHeight);
        sw = new QuadTreeNode(x, y + halfHeight, halfWidth, halfHeight);
        se = new QuadTreeNode(x + halfWidth, y + halfHeight, halfWidth, halfHeight);
    }

    public TucPoint search(int px, int py, QuadTree tree) {
        tree.incrementAccessCounter();
        if (!contains(px, py)) return null;

        if (isLeaf()) {
            if (point != null && point.getX() == px && point.getY() == py) {
                return point;
            }
            return null;
        }
        //Μόλις επιλεχθεί το κατάλληλο παιδί,καλείται η search
        //αναδρομικά πάνω σε αυτό το παιδί. Η διαδικασία αυτή επαναλαμβάνεται μέχρι να φτάσει σε έναν κόμβο-φύλλο.
        // ελεγχουμε καθε nw, ne , sw ,se και αναθετουμε στο καταλληλο το εργο αναζητησης
        if (nw.contains(px, py)) return nw.search(px, py, tree);
        if (ne.contains(px, py)) return ne.search(px, py, tree);
        if (sw.contains(px, py)) return sw.search(px, py, tree);
        if (se.contains(px, py)) return se.search(px, py, tree);

        return null;
    }

    public void rangeSearch(int x1, int y1, int x2, int y2, ArrayList<TucPoint> result, QuadTree tree) {
        tree.incrementAccessCounter();
        if (x + width < x1 || x > x2 || y + height < y1 || y > y2)
            return; // Χωρίς επικάλυψη

        if (isLeaf()) {
            if (point != null && point.getX() >= x1 && point.getX() <= x2 &&
                    point.getY() >= y1 && point.getY() <= y2) {
                result.add(point);
            }
            return;
        }

        nw.rangeSearch(x1, y1, x2, y2, result, tree);
        ne.rangeSearch(x1, y1, x2, y2, result, tree);
        sw.rangeSearch(x1, y1, x2, y2, result, tree);
        se.rangeSearch(x1, y1, x2, y2, result, tree);
    }

    public void findNearPoints(int px, int py, int distance, ArrayList<TucPoint> result, QuadTree tree) {
        tree.incrementAccessCounter();
        if (x > px + distance || x + width < px - distance || y > py + distance || y + height < py - distance) return;

        if (isLeaf()) {
            if (point != null && point.isNear(px, py, distance)) {
                result.add(point);
            }
            return;
        }

        nw.findNearPoints(px, py, distance, result, tree);
        ne.findNearPoints(px, py, distance, result, tree);
        sw.findNearPoints(px, py, distance, result, tree);
        se.findNearPoints(px, py, distance, result, tree);
    }
}