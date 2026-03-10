package ml.pipeline;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Кластеризация на графе похожести:
 * вершины — объекты, ребро создается если евклидово расстояние <= epsilon.
 * Кластеры — компоненты связности.
 */
public class GraphClusterer {

    public List<List<DataPoint>> cluster(List<DataPoint> points, double epsilon) {
        Map<String, Set<String>> graph = buildGraph(points, epsilon);
        Map<String, DataPoint> pointById = new HashMap<>();
        for (DataPoint p : points) {
            pointById.put(p.getId(), p);
        }

        List<List<DataPoint>> clusters = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (DataPoint point : points) {
            if (visited.contains(point.getId())) {
                continue;
            }

            List<DataPoint> cluster = new ArrayList<>();
            Deque<String> stack = new ArrayDeque<>();
            stack.push(point.getId());
            visited.add(point.getId());

            while (!stack.isEmpty()) {
                String current = stack.pop();
                cluster.add(pointById.get(current));

                for (String neighbor : graph.getOrDefault(current, Set.of())) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        stack.push(neighbor);
                    }
                }
            }
            clusters.add(cluster);
        }

        return clusters;
    }

    private Map<String, Set<String>> buildGraph(List<DataPoint> points, double epsilon) {
        Map<String, Set<String>> graph = new HashMap<>();
        for (DataPoint point : points) {
            graph.put(point.getId(), new HashSet<>());
        }

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                DataPoint a = points.get(i);
                DataPoint b = points.get(j);
                if (euclideanDistance(a, b) <= epsilon) {
                    graph.get(a.getId()).add(b.getId());
                    graph.get(b.getId()).add(a.getId());
                }
            }
        }
        return graph;
    }

    private double euclideanDistance(DataPoint a, DataPoint b) {
        if (!a.getFeatures().keySet().equals(b.getFeatures().keySet())) {
            throw new IllegalArgumentException("Нельзя сравнить точки с разными наборами признаков");
        }

        double sum = 0.0;
        for (String key : a.getFeatures().keySet()) {
            double diff = a.getFeature(key) - b.getFeature(key);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
