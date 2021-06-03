package main.java.dijkstra;

import java.util.Map;

public class ShortestPath {

    public static void main(String[] args) {
        Map tickers = Map.of(
            "A-B", Map.of("firstToSecond", 100, "secondToFirst", 99),
            "A-C", Map.of("firstToSecond", 1200, "secondToFirst", 1150),
            "D-B", Map.of("firstToSecond", 200, "secondToFirst", 180),
            "D-C", Map.of("firstToSecond", 220, "secondToFirst", 210),
            "A-D", Map.of("firstToSecond", 6, "secondToFirst", 5)
        );

        Graph graph = new Graph();
        graph.buildGraph(tickers);
        graph.printGraph();
        System.out.println("Shortest path from B-C is: " + graph.shortestPath(new String[]{"B", "C"}));
    }
}
