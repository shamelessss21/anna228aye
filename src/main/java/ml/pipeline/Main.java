package ml.pipeline;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Точка входа демонстрационного приложения.
 */
public class Main {
    public static void main(String[] args) {
        String csvPath = args.length > 0 ? args[0] : "data/data.csv";

        try {
            CsvDataLoader loader = new CsvDataLoader();
            List<DataPoint> data = loader.load(csvPath);

            FeatureStore store = new HashFeatureStore(); // можно заменить на TreeFeatureStore
            AnomalyDetector detector = new AnomalyDetector(1.8);
            GraphBuilder graphBuilder = new GraphBuilder(15.0);
            GraphClusterer clusterer = new GraphClusterer();

            PreprocessingPipeline pipeline = new PreprocessingPipeline(store, detector, graphBuilder, clusterer);
            PipelineResult result = pipeline.run(data);

            System.out.println("Аномальные объекты:");
            result.getAnomalies().forEach(System.out::println);

            System.out.println("\nRange query по income [30..90]:");
            pipeline.rangeQuery("income", 30.0, 90.0).forEach(System.out::println);

            System.out.println("\nКластеры (ID):");
            int i = 1;
            for (Set<Integer> cluster : result.getClusters()) {
                System.out.println("Кластер " + i++ + ": " + cluster);
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения CSV: " + e.getMessage());
        }
    }
}
