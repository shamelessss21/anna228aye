package ml.pipeline;

import java.util.List;
import java.util.Map;

public class GraphClustererTest {

    public static void run() {
        List<DataPoint> points = List.of(
                new DataPoint("a1", Map.of("x", 1.0, "y", 1.0)),
                new DataPoint("a2", Map.of("x", 1.1, "y", 1.1)),
                new DataPoint("b1", Map.of("x", 8.0, "y", 8.0)),
                new DataPoint("b2", Map.of("x", 8.2, "y", 8.1))
        );

        GraphClusterer clusterer = new GraphClusterer();
        List<List<DataPoint>> clusters = clusterer.cluster(points, 0.5);

        TestSupport.assertEquals(2, clusters.size(), "ожидается два кластера");
        TestSupport.assertEquals(2, clusters.get(0).size(), "в первом кластере две точки");
        TestSupport.assertEquals(2, clusters.get(1).size(), "во втором кластере две точки");
    }
}
