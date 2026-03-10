package ml.pipeline;

import java.util.List;
import java.util.Map;

public class PreprocessingPipelineTest {

    public static void run() {
        List<DataPoint> data = List.of(
                new DataPoint("p1", Map.of("age", 20.0, "income", 30.0)),
                new DataPoint("p2", Map.of("age", 21.0, "income", 31.0)),
                new DataPoint("p3", Map.of("age", 50.0, "income", 90.0)),
                new DataPoint("p4", Map.of("age", 51.0, "income", 91.0)),
                new DataPoint("outlier", Map.of("age", 22.0, "income", 300.0))
        );

        PreprocessingPipeline pipeline = new PreprocessingPipeline();
        PipelineResult result = pipeline.run(data, "income", 1.7, 0.5);

        TestSupport.assertEquals(5, result.getInputCount(), "должно быть 5 входных объектов");
        TestSupport.assertEquals(1, result.getAnomalyCount(), "должна быть 1 аномалия");
        TestSupport.assertEquals(4, result.getCleanCount(), "после очистки должно остаться 4 объекта");
        TestSupport.assertEquals(2, result.getClusterCount(), "ожидается 2 кластера");
        TestSupport.assertEquals(List.of(2, 2), result.getClusterSizes(), "размеры кластеров должны быть [2, 2]");
    }
}
