import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Dijkstra {
    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t) {
        // option 0:    Unoptimised. Full Q init, remove and re-q to change priority.
        // option 1:    Optimised. Q init with only s, inf will never get q'ed. Re-q w/o removal.
        //              However, pair of node and priority has to be inserted to keep priority static.

        Set<Integer> visited = new HashSet<>(); //option 1
        Map<Integer, Integer> distance = new HashMap<>();
//        Queue<Integer> q = new PriorityQueue<>(11, (o1, o2) -> { //option 0
//            Integer d1 = distance.get(o1); //option 0
//            Integer d2 = distance.get(o2); //option 0
        Queue<Map.Entry<Integer, Integer>> q = new PriorityQueue<>(11, (o1, o2) -> { //option 1
            Integer d1 = o1.getValue(); //option 1
            Integer d2 = o2.getValue(); //option 1

            if (Objects.equals(d1, d2)) return 0;
            if (d1 == null) return 1;
            if (d2 == null) return -1;

            return d1 - d2;
        });

        distance.put(s, 0);
//        q.addAll(IntStream.range(0, adj.length).boxed().collect(Collectors.toList())); //option 0
        q.add(new AbstractMap.SimpleImmutableEntry<>(s, 0)); //option 1

        while (!q.isEmpty()) {
//            int u = q.remove(); //option 0
            int u = q.remove().getKey(); //option 1

            if (u == t) {
                Integer dist = distance.get(u);
                if (dist == null) return -1;
                return dist;
            }

            if (visited.contains(u)) continue; //option 1
            visited.add(u); //option 1

            List<Integer> adjList = adj[u];
            List<Integer> costList = cost[u];
            for (int i = 0; i < adjList.size(); i++) {
                int v = adjList.get(i);
                int w = costList.get(i);
                Integer dist = distance.get(v);
                Integer newDist = distance.get(u);
                if (newDist != null) newDist += w;

                if (newDist != null && (dist == null || dist > newDist)) {
                    //relax
                    distance.put(v, newDist);
//                    q.remove(v); //option 0
//                    q.add(v); //option 0
                    q.add(new AbstractMap.SimpleImmutableEntry<>(v, distance.get(v))); //option 1
                }
            }
        }

        return -1;
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y));
    }

    // dev main
//    public static void main (String[] args) {
//        try(Stream<String> stream = Files.lines(Paths.get("res/dijkstra-input-1.txt"))) {
//            List<List<Integer>> list = stream
//                    .map(s -> Arrays.stream(s.split(" "))
//                            .map(Integer::parseInt)
//                            .collect(Collectors.toList())).collect(Collectors.toList()
//                    );
//
//            List<Integer> last = list.remove(list.size() - 1);
//            List<Integer> first = list.remove(0);
//
//            List<List<Integer>> adj = IntStream.range(0, first.get(0))
//                    .mapToObj(i -> new ArrayList<Integer>())
//                    .collect(Collectors.toList());
//
//            List<List<Integer>> cost = IntStream.range(0, first.get(0))
//                    .mapToObj(i -> new ArrayList<Integer>())
//                    .collect(Collectors.toList());
//
//            list.forEach(p -> {
//                int x = p.get(0) - 1;
//                adj.get(x).add(p.get(1) - 1);
//                cost.get(x).add(p.get(2));
//            });
//
//            System.out.println(distance(
//                    adj.toArray(new ArrayList[adj.size()]), cost.toArray(new ArrayList[cost.size()]),
//                    last.get(0) - 1, last.get(1) - 1
//            ));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

