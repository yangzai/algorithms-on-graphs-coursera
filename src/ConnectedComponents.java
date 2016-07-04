import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConnectedComponents {
    private static int numberOfComponents(ArrayList<Integer>[] adj) {
        int result = 0;
        //write your code here
        Stack<Integer> stack = new Stack<>();
        Set<Integer> explored = new HashSet<>();

        for (int i = 0; i < adj.length; i++) {
            if (explored.contains(i)) continue;

            stack.push(i);

            // core
            while (!stack.isEmpty()) {
                int vertex = stack.pop();

                if (explored.contains(vertex)) continue;

                explored.add(vertex);
                adj[vertex].forEach(stack::push);
            }

            // alt
//            explored.add(i);
//            while (!stack.isEmpty()) {
//                int top = stack.peek();
//                List<Integer> list = adj[top];
//
//                if (!list.isEmpty()) {
//                    int take = list.remove(list.size() - 1); //remove last is O(1)
//                    if (explored.contains(take)) continue;
//
//                    explored.add(take);
//                    stack.push(take);
//                } else stack.pop();
//            }

            result++;
        }

        return result;
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
        System.out.println(numberOfComponents(adj));
    }

    // dev main
//    public static void main (String[] args) {
//        try(Stream<String> stream = Files.lines(Paths.get("res/connected-components-input-0.txt"))) {
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
//            System.out.println(numberOfComponents(adj.toArray(new ArrayList[adj.size()])));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}