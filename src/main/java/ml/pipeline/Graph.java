package ml.pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Неориентированный граф близости объектов.
 */
public class Graph {
    private final Map<Integer, List<Integer>> adjacency = new HashMap<>();

    public void addNode(int nodeId) {
        adjacency.computeIfAbsent(nodeId, k -> new ArrayList<>());
    }

    public void addEdge(int u, int v) {
        addNode(u);
        addNode(v);
        adjacency.get(u).add(v);
        adjacency.get(v).add(u);
    }

    public Map<Integer, List<Integer>> getAdjacency() {
        return adjacency;
    }
}
