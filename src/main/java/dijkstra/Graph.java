package main.java.dijkstra;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Graph {
    Map<String, Node> allNodes;
    Map<Node, Map<Node, Integer>> graph;

    public Graph() {
        allNodes = new HashMap<>();
        graph = new HashMap<>();
    }

    public void buildGraph(Map<String, Map<String, Integer>> tickers) {
        for (String pair : tickers.keySet()) {
            String first = pair.split("-")[0];
            String second = pair.split("-")[1];

            Map<String, Integer> edgeToChildren = tickers.get(pair);
            Integer fromFtoS = edgeToChildren.get("firstToSecond");
            Integer fromStoF = edgeToChildren.get("secondToFirst");

            allNodes.put(first, allNodes.getOrDefault(first, new Node(first)));
            allNodes.put(second, allNodes.getOrDefault(second, new Node(second)));

            graph.put(allNodes.get(first), graph.getOrDefault(allNodes.get(first), new HashMap<>()));
            graph.put(allNodes.get(second), graph.getOrDefault(allNodes.get(second), new HashMap<>()));

            graph.get(allNodes.get(first)).put(allNodes.get(second), fromFtoS);
            graph.get(allNodes.get(second)).put(allNodes.get(first), fromStoF);

        }
    }

    public void printGraph() {
        System.out.println("The graph has the following structure:");
        graph.keySet().stream().forEach(node -> {
            System.out.print(node.val + ": ");
            graph.get(node).keySet().stream().forEach(
                child -> {
                    System.out.print("(" + child.val + ", " + graph.get(node).get(child) + ") ");
                });
            System.out.println();
        });
        System.out.println();
    }

    public int shortestPath(String[] verticesPair) {
        String start = verticesPair[0];
        String goal = verticesPair[1];

        Set<String> visited = new HashSet<>();

        Queue<Node> q = new LinkedList<>();

        allNodes.get(start).shortestPath = 0;
        q.add(allNodes.get(start));

        while(!q.isEmpty()) {
            Node cur = q.poll();
            visited.add(cur.val);

            PriorityQueue<Node> children = new PriorityQueue<>((a, b) -> (graph.get(cur).get(a) - graph.get(cur).get(b)));

            for (Node child : graph.get(cur).keySet()) {
                children.add(child);
            }

            for (Node child : children) {
                if (visited.contains(child.val)) continue;
                if (child.shortestPath > cur.shortestPath + graph.get(cur).get(child)) {
                    child.shortestPath = cur.shortestPath + graph.get(cur).get(child);
                    child.previousNode = cur;
                }
                q.offer(child);
            }
        }

        return allNodes.get(goal).shortestPath;
    }

    public void printPath(String goal) {
        Node node = allNodes.get(goal);
        List<Node> path = new ArrayList<>();
        while (node.previousNode != null) {
            path.add(node);
            node = node.previousNode;
        }
        path.add(node);

        Collections.reverse(path);

        path.stream().forEach(n -> {
            if (n == allNodes.get(goal)) System.out.println(n.val);
            else System.out.print(n.val + " --> ");
        });

        System.out.println();
    }
}
