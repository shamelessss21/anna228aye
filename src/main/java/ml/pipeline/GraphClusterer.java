package ml.pipeline;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Кластеризация как поиск компонент связности графа (BFS).
 */
public class GraphClusterer {

    public List<Set<Integer>> cluster(Graph graph) {
        List<Set<Integer>> clusters = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        for (Integer node : graph.getAdjacency().keySet()) {
            if (visited.contains(node)) {
                continue;
            }
            Set<Integer> component = bfs(node, graph, visited);
            clusters.add(component);
        }

        return clusters;
    }

    private Set<Integer> bfs(int start, Graph graph, Set<Integer> visited) {
        Set<Integer> component = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            component.add(current);

            for (Integer neighbor : graph.getAdjacency().getOrDefault(current, List.of())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return component;
    }
}
