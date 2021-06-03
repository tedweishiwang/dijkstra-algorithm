package main.java.dijkstra;

public class Node {

    int shortestPath;
    Node previousNode;
    String val;

    public Node(String val) {
        this.val = val;
        shortestPath = Integer.MAX_VALUE;
        previousNode = null;
    }
}