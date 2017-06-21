import java.util.*;

public class ConnectingPoints {
    private static double distBetween(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    static class Dest {
        double distance;
        int id;

        Dest(double d, int id) {
            this.distance = d;
            this.id = id;
        }
    }

    static class Edge {
        int p1, p2;
        Edge(int p1, int p2) {this.p1 = p1; this.p2 = p2;}

        @Override
        public String toString() {
            return p1 + " - " + p2;
        }
    }


    private static Dest findMinIndexNotInSet(double[] dist, HashSet<Integer> set) {
        double minValue = Double.MAX_VALUE;
        int idx = -1;
        for (int i = 0; i< dist.length; i++) {
            if (set.contains(i)) {
                continue;
            }
            if (minValue > dist[i]) {
                minValue = dist[i];
                idx = i;
            }
        }
        return new Dest(minValue, idx);
    }


    private static double minimumDistance(int[] x, int[] y) {
        double result = 0.;
        //write your code here
        HashSet<Integer> fixed = new HashSet<>();
        HashSet<Edge> mst = new HashSet<>();
        double[] dist = new double[x.length];
        Arrays.fill(dist, Double.MAX_VALUE);
        dist[0] = 0.0;
        int[] parent = new int[x.length];
        Arrays.fill(parent, -1);

        while (mst.size() < x.length - 1) { // loop until we find |V| -1 edges
            Dest dest = findMinIndexNotInSet(dist, fixed);
            int id = dest.id;
            fixed.add(id);
            if (parent[id] != -1) {
                mst.add(new Edge(parent[id], id));
                result += dest.distance;
            }

            for(int i = 0; i<x.length; i++) {
                if (fixed.contains(i)) {
                    continue;
                }
                double newDist = distBetween(x[id], y[id], x[i], y[i]);
                if (dist[i] > newDist) {
                    dist[i] = newDist;
                    parent[i] = id;
                }
            }
        }

//        System.out.println("mst edges:");
//        for (Edge e : mst) {
//            System.out.print(e + ", ");
//        }
//        System.out.println("");

        return result;
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
        System.out.println(minimumDistance(x, y));
    }
}

