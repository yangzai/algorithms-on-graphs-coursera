import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BFS {
    private static int distance(ArrayList<Integer>[] adj, int s, int t) {
        //write your code here
        if (s == t) return 0;

        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> distance = new HashMap<>();

        queue.add(s);
        distance.put(s, 0);

        while (!queue.isEmpty()) {
            int vertex = queue.remove();
            int nextDistance = distance.get(vertex) + 1;

            // imperative loop
            for (int v : adj[vertex]) {
                if (distance.containsKey(v)) continue;

                if (v == t) return nextDistance;

                distance.put(v, nextDistance);
                queue.add(v);
            }

            // functional stream
//            Predicate<Integer> hasDistance = distance::containsKey;
//            boolean isTargetFound = adj[vertex].stream()
//                    .filter(hasDistance.negate())
//                    .anyMatch(v -> {
//                        distance.put(v, nextDistance);
//
//                        if (v == t) return true;
//
//                        queue.add(v);
//                        return false;
//                    });
//
//            if (isTargetFound) return distance.get(t);
        }

        return -1;
    }

    // default main
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, x, y));
    }

    // dev main
//    public static void main (String[] args) {
//        try(Stream<String> stream = Files.lines(Paths.get("res/bfs-input-0.txt"))) {
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
//            list.forEach(p -> {
//                adj.get(p.get(0) - 1).add(p.get(1) - 1);
//                adj.get(p.get(1) - 1).add(p.get(0) - 1);
//            });
//
//            System.out.println(distance(adj.toArray(new ArrayList[adj.size()]), last.get(0) - 1, last.get(1) - 1));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

