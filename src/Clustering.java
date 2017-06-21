import java.util.*;
import java.util.Scanner;

public class Clustering {

    static class Edge {
        int p1, p2;
        double dist;
        Edge(int p1, int p2, double dist) {this.p1 = p1; this.p2 = p2; this.dist = dist;}

        @Override
        public String toString() {
            return p1 + " - " + p2 + "(" + dist + ")";
        }
    }

    private static double distBetween(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private static int djsFind(int[] djs, int i) {
        while (i != djs[i]) {
            i = djs[i];
        }
        return i;
    }

    private static void djsUnion(int[] djs, int[] rank, int i, int j) {
        int ri = djsFind(djs, i);
        int rj = djsFind(djs, j);
        if (ri == rj) {
            return;
        }
        if (rank[ri] < rank[rj]) {
            // hang ri under rj
            djs[ri] = rj;
        } else {
            djs[rj] = ri;
            if (rank[ri] == rank[rj]) {
                rank[ri] += 1;
            }
        }
    }

    private static double clustering(int[] x, int[] y, int k) {
        //write your code here

        int[] djsParent = new int[x.length];
        int[] djsRank = new int[x.length];

        for(int i = 0; i<x.length; i++) {
            djsParent[i] = i;
            djsRank[i] = 1;
        }

        ArrayList<Edge> edges = new ArrayList<>();
        for(int i = 0; i<x.length - 1; i++) {
            for (int j = i+1; j< x.length; j++) {
                edges.add(new Edge(i, j, distBetween(x[i], y[i], x[j], y[j])));
            }
        }
        edges.sort((o1, o2) -> Double.compare(o1.dist, o2.dist));

//        System.out.println("edges:");
//        for (Edge e : edges) {
//            System.out.print(e + ", ");
//        }
//        System.out.println("");

        int clusters = x.length;
        int i = 0;
        while (clusters > k) {
            Edge e = edges.get(i++);
            if (djsFind(djsParent, e.p1) != djsFind(djsParent, e.p2)) {
                // use this edge
                djsUnion(djsParent, djsRank, e.p1, e.p2);
                clusters--;
            }
        }

        for(; i<edges.size(); i++) {
            Edge e = edges.get(i);
            if (djsFind(djsParent, e.p1) != djsFind(djsParent, e.p2)) {
                break;
            }
        }

        return edges.get(i).dist;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        int k = scanner.nextInt();
        System.out.println(clustering(x, y, k));
    }
}

