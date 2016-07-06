import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StronglyConnected {
    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
        //write your code here
        int result = 0;

        // setup reverseAdj
        ArrayList<Integer>[] reverseAdj = (ArrayList<Integer>[])new ArrayList[adj.length];
        for (int i = 0; i < reverseAdj.length; i++)
            reverseAdj[i] = new ArrayList<>();
        for (int i = 0; i < adj.length; i++)
            for (int e : adj[i]) reverseAdj[e].add(i);

        Stack<Integer> stack = new Stack<>();
        Set<Integer> explored = new HashSet<>();

        Stack<Integer> reversePostOrder = new Stack<>();

        // reverse graph phase, derive reverse post order
        for (int i = 0; i < adj.length; i++) {
            if (explored.contains(i)) continue;

            stack.push(i);

            // using delayed pop and list removal
            // note: this should also allow post order processing without 2nd stack
            explored.add(i);
            while (!stack.isEmpty()) {
                int top = stack.peek();
                List<Integer> list = reverseAdj[top];

                if (!list.isEmpty()) {
                    int take = list.remove(list.size() - 1); //remove last is O(1)

                    if (explored.contains(take)) continue;

                    explored.add(take);
                    stack.push(take);
                } else reversePostOrder.push(stack.pop()); //post order
            }
        }

        stack = new Stack<>();
        explored = new HashSet<>();

        // component phase, traverse and count components
        while (!reversePostOrder.isEmpty()) {
            int maxPostOrder = reversePostOrder.pop();

            if (explored.contains(maxPostOrder)) continue;

            // standard dfs unlike top
            // breaks on explored
            stack.push(maxPostOrder);
            while (!stack.isEmpty()) {
                int vertex = stack.pop();

                if (explored.contains(vertex)) continue;

                explored.add(vertex);
                adj[vertex].forEach(stack::push);
            }

            result++;
        }

        return result;
//        return 0;
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
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }

    // dev main
//    public static void main (String[] args) {
//        try(Stream<String> stream = Files.lines(Paths.get("res/strongly-connected-input-1.txt"))) {
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
//            System.out.println(numberOfStronglyConnectedComponents(adj.toArray(new ArrayList[adj.size()])));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

