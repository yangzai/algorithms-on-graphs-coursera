import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Bipartite {
    private static int bipartite(ArrayList<Integer>[] adj) {
        //write your code here
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Boolean> black = new HashMap<>();

        for (int i = 0; i < adj.length; i++) {
            if (black.containsKey(i)) continue;

            queue.add(i);
            black.put(i, true);

            while (!queue.isEmpty()) {
                int parent = queue.remove();
                boolean isParentBlack = black.get(parent);

                for (int child : adj[parent]) {
                    Boolean isChildBlack = black.get(child);

                    if (isChildBlack == null) { //unexplored
                        black.put(child, !isParentBlack);
                        queue.add(child);
                        continue;
                    }

                    //if colour is same
                    if (isChildBlack == isParentBlack) return 0;
                }
            }
        }

        return 1;
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
        System.out.println(bipartite(adj));
    }

    // dev main
//    public static void main (String[] args) {
//        try(Stream<String> stream = Files.lines(Paths.get("res/bipartite-input-3.txt"))) {
//            List<List<Integer>> list = stream
//                    .map(s -> Arrays.stream(s.split(" "))
//                            .map(Integer::parseInt)
//                            .collect(Collectors.toList())).collect(Collectors.toList()
//                    );
//
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
//            System.out.println(bipartite(adj.toArray(new ArrayList[adj.size()])));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

