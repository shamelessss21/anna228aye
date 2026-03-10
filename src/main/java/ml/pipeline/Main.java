package ml.pipeline;

import java.util.List;
import java.util.Map;

/**
 * Демонстрация работы preprocessing pipeline для ML.
 */
public class Main {
    public static void main(String[] args) {
        List<DataPoint> dataset = List.of(
                new DataPoint("u1", Map.of("age", 22.0, "income", 35.0, "spend", 30.0)),
                new DataPoint("u2", Map.of("age", 23.0, "income", 36.0, "spend", 31.0)),
                new DataPoint("u3", Map.of("age", 24.0, "income", 37.0, "spend", 29.0)),
                new DataPoint("u4", Map.of("age", 45.0, "income", 80.0, "spend", 75.0)),
                new DataPoint("u5", Map.of("age", 46.0, "income", 82.0, "spend", 74.0)),
                new DataPoint("u6", Map.of("age", 90.0, "income", 300.0, "spend", 5.0)) // аномалия
        );

        PreprocessingPipeline pipeline = new PreprocessingPipeline();
        PipelineResult result = pipeline.run(dataset, "income", 2.0, 15.0);

        System.out.println("=== Аномалии ===");
        result.getAnomalies().forEach(System.out::println);

        System.out.println("\n=== Пример range query по дереву (income 30..90) ===");
        result.getFeatureStore().rangeQuery("income", 30.0, 90.0)
                .forEach(System.out::println);

        System.out.println("\n=== Кластеры чистых данных ===");
        int idx = 1;
        for (List<DataPoint> cluster : result.getClusters()) {
            System.out.println("Cluster " + idx++ + ": " + cluster);
        }
    }
}
