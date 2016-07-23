import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NegativeCycle {
    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost) {
        // write your code here
        Map<Integer, Integer> distance = new HashMap<>();

        // using a virtual source node with 0 distance to all nodes
        // i.e. null values in map are 0, not inf

        for (int j = 0; j < adj.length - 1; j++) { //v - 1 iteration
            for (int u = 0; u < adj.length; u++) {
                List<Integer> adjList = adj[u];
                List<Integer> costList = cost[u];
                for (int k = 0; k < adjList.size(); k++) {
                    int v = adjList.get(k);
                    int w = costList.get(k);
                    int dist = distance.getOrDefault(v, 0);
                    int newDist = distance.getOrDefault(u, 0) + w;

                    if (dist > newDist) distance.put(v, newDist); //relax
                }
            }
        }

        // negative cycle iteration, refactor?
        for (int u = 0; u < adj.length; u++) {
            List<Integer> adjList = adj[u];
            List<Integer> costList = cost[u];
            for (int k = 0; k < adjList.size(); k++) {
                int v = adjList.get(k);
                int w = costList.get(k);
                int dist = distance.getOrDefault(v, 0);
                int newDist = distance.getOrDefault(u, 0) + w;

                if (dist > newDist) return 1;
            }
        }

        return 0;
    }

    //default main
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(negativeCycle(adj, cost));
    }

    // dev main
//    public static void main (String[] args) {
//        try(Stream<String> stream = Files.lines(Paths.get("res/negative-cycle-input-0.txt"))) {
//            List<List<Integer>> list = stream
//                    .map(s -> Arrays.stream(s.split(" "))
//                            .map(Integer::parseInt)
//                            .collect(Collectors.toList())).collect(Collectors.toList()
//                    );
//
//            List<Integer> first = list.remove(0);
//            int n = first.get(0);
//            List<List<Integer>> adj = IntStream.range(0, n)
//                    .mapToObj(i -> new ArrayList<Integer>())
//                    .collect(Collectors.toList());
//
//            List<List<Integer>> cost = IntStream.range(0, n)
//                    .mapToObj(i -> new ArrayList<Integer>())
//                    .collect(Collectors.toList());
//
//            list.forEach(p -> {
//                int x = p.get(0) - 1;
//                adj.get(x).add(p.get(1) - 1);
//                cost.get(x).add(p.get(2));
//            });
//
//            System.out.println(negativeCycle(
//                    adj.toArray(new ArrayList[adj.size()]), cost.toArray(new ArrayList[cost.size()])
//            ));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

