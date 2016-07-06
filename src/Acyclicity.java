import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Acyclicity {
    private static int acyclic(ArrayList<Integer>[] adj) {
        //write your code here
        Stack<Integer> stack = new Stack<>();
        Set<Integer> explored = new HashSet<>();

        //redundancy
        Set<Integer> path = new HashSet<>();

        for (int i = 0; i < adj.length; i++) {
            if (explored.contains(i)) continue;

            // using delayed pop, list removal and stack-set redundancy
            // note: this should also allow post order processing without 2nd stack
            stack.push(i);
            path.add(i);
            explored.add(i);
            while (!stack.isEmpty()) {
                int top = stack.peek();
                List<Integer> list = adj[top];

                if (!list.isEmpty()) {
                    int take = list.remove(list.size() - 1); //remove last is O(1)

                    if (path.contains(take)) return 1; //back edge
                    if (explored.contains(take)) continue;

                    stack.push(take);
                    path.add(take);
                    explored.add(take);
                } else path.remove(stack.pop());
            }
        }

        return 0;
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
        }
        System.out.println(acyclic(adj));
    }

    // dev main
//    public static void main (String[] args) {
//        try(Stream<String> stream = Files.lines(Paths.get("res/acyclicity-input-2.txt"))) {
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
//            list.forEach(p -> adj.get(p.get(0) - 1).add(p.get(1) - 1));
//
//            System.out.println(acyclic(adj.toArray(new ArrayList[adj.size()])));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

