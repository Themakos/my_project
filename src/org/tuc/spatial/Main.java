package org.tuc.spatial;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static final int[] Ns = {200 , 500, 2000, 5000, 10000};
	static final String[] Modes = { "sparse", "dense"};
	static final int K = 1024;
	static final int D = 10;
	//static final int[] Bs = {4, 32};

public static void main(String[] args) {

    System.out.println("\t\t\t\t\t\t\t========Nodes results========");
    
    System.out.printf("%-7s | %10s %10s %10s | %10s %10s %10s | %10s %10s %10s | %10s %10s %10s\n",
            "N",
            "S1 sparse", "S2 sparse", "S3 sparse",
            "R1 sparse", "R2 sparse", "R3 sparse",
            "S1 dense", "S2 dense", "S3 dense",
            "R1 dense", "R2 dense", "R3 dense");
    System.out.println("---------------------------------------------------------------------------------------------------------------");

    
    double[][] results = new double[Ns.length][12]; // 6 sparse + 6 dense
    double[][] resultsTime = new double[Ns.length][12]; // 6 sparse + 6 dense

    SpatialHash sh1Dense10000 = null;
    SpatialHash sh2Dense10000 = null;
    QuadTree qtDense10000 = null;
    List<Monster> monstersDense10000 = null;

    for (int i = 0; i < Ns.length; i++) {
        int N = Ns[i];
        // Για καθε Ν τρεχει και sparse και dense
        for (String density : Modes) {
            String singleSearchFile = "askhsh_2_files/single_search_" + density + "_" + N + ".bin";
            String nearSearchFile = "askhsh_2_files/near_search_" + density + "_" + N + ".bin";
            List<Monster> monsters = readMonsterFromDisk(N, density);
            List<TucPoint> singlePoint = readCoordinatesFromFile(singleSearchFile);
            List<TucPoint> nearPoints = readCoordinatesFromFile(nearSearchFile);

            SpatialHash sh1 = new SpatialHash(K, D, 4);
            SpatialHash sh2 = new SpatialHash(K, D, 32);
            QuadTree qt = new QuadTree(K, D);

            // Εισαγωγή των τεράτων στις δομές
            for (Monster m : monsters) {
                sh1.insert(m);
                sh2.insert(m);
                qt.insert(m);
            }

            double nodesS1 = 0, nodesS2 = 0, nodesS3 = 0;
            double nodesR1 = 0, nodesR2 = 0, nodesR3 = 0;
            double timeS1 = 0, timeS2 = 0, timeS3 = 0;
            double timeR1 = 0, timeR2 = 0, timeR3 = 0;
            for (TucPoint p : singlePoint) {
                long t0, t1, t2, t3, t4, t5;
                
                sh1.resetLastNodeCount();
                t0 = System.nanoTime();
                sh1.search(p.getX(), p.getY());
                t1 = System.nanoTime();
                timeS2 += t1- t0;
                nodesS2 += sh1.getLastNodeCount();

                sh2.resetLastNodeCount();
                t2 = System.nanoTime();
                sh2.search(p.getX(), p.getY());
                t3 = System.nanoTime();
                timeS3 += t3 - t2;
                nodesS3 += sh2.getLastNodeCount();

                qt.resetAccessCounter();
                t4 = System.nanoTime();
                qt.search(p.getX(), p.getY());
                t5 = System.nanoTime();
                timeS1 += t5 - t4;
                nodesS1 += qt.getAccessCounter();

            }
            for (TucPoint p : nearPoints) {
                long t0, t1, t2, t3, t4, t5;
              

                sh1.resetLastNodeCount();
                t0 = System.nanoTime();
                sh1.findNearPoints(p.getX(), p.getY());
                t1 = System.nanoTime();
                timeR2 += t1 - t0;
                nodesR2 += sh1.getLastNodeCount();

                sh2.resetLastNodeCount();
                t2 = System.nanoTime();
                sh2.findNearPoints(p.getX(), p.getY());
                t3 = System.nanoTime();
                timeR3 += t3 - t2;
                nodesR3 += sh2.getLastNodeCount();

                t4 = System.nanoTime();
                qt.findNearPoints(p.getX(), p.getY());
                t5 = System.nanoTime();
                timeR1 += t5 - t4;
                nodesR1 += qt.getAccessCounter();

            }
            int total = 100;
           
            //Υπολογισμος για την καταλληλη μεταβλητη για να μπει στον πινακα
            if (density.equals("sparse")) {
                results[i][0] = nodesS1 / total;
                results[i][1] = nodesS2 / total;
                results[i][2] = nodesS3 / total;
                results[i][3] = nodesR1 / total;
                results[i][4] = nodesR2 / total;
                results[i][5] = nodesR3 / total;
                resultsTime[i][0] = timeS1 / total;
                resultsTime[i][1] = timeS2 / total;
                resultsTime[i][2] = timeS3 / total;
                resultsTime[i][3] = timeR1 / total;
                resultsTime[i][4] = timeR2 / total;
                resultsTime[i][5] = timeR3 / total;

            } else { // dense
                results[i][6] = nodesS1 / total;
                results[i][7] = nodesS2 / total;
                results[i][8] = nodesS3 / total;
                results[i][9] = nodesR1 / total;
                results[i][10] = nodesR2 / total;
                results[i][11] = nodesR3 / total;
                resultsTime[i][6] = timeS1 / total;
                resultsTime[i][7] = timeS2 / total;
                resultsTime[i][8] = timeS3 / total;
                resultsTime[i][9] = timeR1 / total;
                resultsTime[i][10] = timeR2 / total;
                resultsTime[i][11] = timeR3 / total;
            }
            //Δηλωση στιγμιοτυπων για Ν = 10000 για την αναζητηση συγκεκριμενων τερατων
            if(density.equals("dense") && N == 10000) {
                sh1Dense10000 = sh1;
                sh2Dense10000 = sh2;
                qtDense10000 = qt;
                monstersDense10000 = monsters;
            }
        }
    }

    

    
    for (int i = 0; i < Ns.length; i++) {
        System.out.printf("%-7d | %10.2f %10.2f %10.2f | %10.2f %10.2f %10.2f | %10.2f %10.2f %10.2f | %10.2f %10.2f %10.2f\n",
                Ns[i],
                results[i][0], results[i][1], results[i][2],
                results[i][3], results[i][4], results[i][5],
                results[i][6], results[i][7], results[i][8],
                results[i][9], results[i][10], results[i][11]
        );
    }

    System.out.println("\n\t\t\t\t\t\t\t========Time results========");
    System.out.printf("%-7s | %10s %10s %10s | %10s %10s %10s | %10s %10s %10s | %10s %10s %10s\n",
            "N",
            "S1 sparse", "S2 sparse", "S3 sparse",
            "R1 sparse", "R2 sparse", "R3 sparse",
            "S1 dense", "S2 dense", "S3 dense",
            "R1 dense", "R2 dense", "R3 dense");
    System.out.println("---------------------------------------------------------------------------------------------------------------");
    for (int i = 0; i < Ns.length; i++) {
        System.out.printf("%-7d | %10.2f %10.2f %10.2f | %10.2f %10.2f %10.2f | %10.2f %10.2f %10.2f | %10.2f %10.2f %10.2f\n",
                Ns[i],
                resultsTime[i][0], resultsTime[i][1], resultsTime[i][2],
                resultsTime[i][3], resultsTime[i][4], resultsTime[i][5],
                resultsTime[i][6], resultsTime[i][7], resultsTime[i][8],
                resultsTime[i][9], resultsTime[i][10], resultsTime[i][11]
        );
    }

    int[][] searchPoints = {{1018, 558}, {992, 518}, {594, 646}, {581, 836}, {718, 827}, {633, 930}};
    String[] hashNames = {"sh1 (B=4)", "sh2 (B=32)", "qt"};
    SpatialStructure[] hashes = {sh1Dense10000, sh2Dense10000, qtDense10000};

    if(sh1Dense10000 != null && sh2Dense10000 != null && monstersDense10000 != null && qtDense10000 != null) {

        for(int i = 0; i < hashes.length; i++) {
            System.out.println("\nSearch result for spesific points using " + hashNames[i] + ":");
            for(int[] pt : searchPoints) {
                TucPoint found = hashes[i].search(pt[0], pt[1]);
                System.out.printf("Found (%d, %d): ", pt[0], pt[1]);
                if(found == null) {
                    System.out.println("No monsters found ");

                }else {
                    boolean monsterPrinted = false;
                    for(Monster m : monstersDense10000) {
                        if(m.getX() == found.getX() && m.getY() == found.getY()) {
                            System.out.println("Monster: " + m.getName());
                            monsterPrinted = true;
                        }
                    }
                    if(!monsterPrinted) {
                        System.out.println("No monsters found ");
                    }
                }
            }
        }
        System.out.println();
    }

    int nearX = 900;
    int nearY = 688;

    for(int i = 0; i < hashes.length; i++) {
        System.out.printf("Range search result for (%d, %d) using %s: \n", nearX, nearY, hashNames[i]);
        List<TucPoint> nearFound = hashes[i].findNearPoints(nearX, nearY);

        if(nearFound.isEmpty()) {
            System.out.println("No monsters found near the point");
        }else {
            boolean monsterPrinted = false;
            if(monstersDense10000 != null) {
                for(TucPoint tp : nearFound) {
                    for(Monster m : monstersDense10000) {
                        if(m.getX() == tp.getX() && m.getY() == tp.getY()) {
                            System.out.printf("(%d, %d): %s\n", tp.getX(), tp.getY(), m.getName());
                            monsterPrinted = true;
                        }
                    }
                }
            }
            if(!monsterPrinted) {
                System.out.println("No monster found near the point");
            }
        }
        System.out.println();
    }

}



    @SuppressWarnings("CallToPrintStackTrace")
    public static List<TucPoint> readCoordinatesFromFile(String inputFile) {
        ArrayList<TucPoint> coordList = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inputFile))) {
            while (dis.available() > 0) { // Ensure we have a full tuple to read
                int x = dis.readInt(); // Read the integer (4 bytes),
                                       // java default is Big Endian
                int y = dis.readInt();
                coordList.add(new TucPoint(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordList;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static List<Monster> readMonsterFromDisk(int N, String type) {
        String inputFile = "askhsh_2_files/monsters_" + type + "_" + N + ".bin";
        int TUPLE_SIZE = 58;
        final int STRING_LENGTH = 50;
        ArrayList<Monster> monsterList = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inputFile))) {
            while (dis.available() >= TUPLE_SIZE) { // Ensure we have a full tuple to read
                int x = dis.readInt(); // Read the integer (4 bytes),
                                       // java default is Big Endian
                int y = dis.readInt();
                byte[] stringBytes = new byte[STRING_LENGTH];
                dis.readFully(stringBytes); // Read the 50-byte string
                // Convert byte array to string, trimming trailing spaces
                String text = new String(stringBytes, StandardCharsets.UTF_8).trim();
                monsterList.add(new Monster(x, y, text));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return monsterList;
    }

}

    