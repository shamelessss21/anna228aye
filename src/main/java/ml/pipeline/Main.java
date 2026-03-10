package ml.pipeline;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Path csvPath = args.length > 0
                ? Path.of(args[0])
                : Path.of("data", "sample_dataset.csv");

        CsvDataLoader loader = new CsvDataLoader();
        List<DataPoint> dataset = loader.load(csvPath);

        PreprocessingPipeline pipeline = new PreprocessingPipeline();
        PipelineResult result = pipeline.run(dataset, "income", 2.0, 2.2);

        System.out.println("Источник данных: " + csvPath.toAbsolutePath());

        System.out.println("\n=== Аномалии ===");
        if (result.getAnomalies().isEmpty()) {
            System.out.println("Аномалии не найдены");
        } else {
            result.getAnomalies().forEach(System.out::println);
        }

        System.out.println("\n=== Пример range query (income 30..90) ===");
        result.getFeatureStore().rangeQuery("income", 30.0, 90.0)
                .forEach(System.out::println);

        System.out.println("\n=== Кластеры после очистки ===");
        int idx = 1;
        for (List<DataPoint> cluster : result.getClusters()) {
            System.out.println("Cluster " + idx++ + " (size=" + cluster.size() + "): " + cluster);
        }

        System.out.println("\n=== Статистика ===");
        System.out.println("Входных объектов: " + result.getInputCount());
        System.out.println("Найдено аномалий: " + result.getAnomalyCount());
        System.out.println("После очистки: " + result.getCleanCount());
        System.out.println("Количество кластеров: " + result.getClusterCount());
        System.out.println("Размеры кластеров: " + result.getClusterSizes());
    }
}
