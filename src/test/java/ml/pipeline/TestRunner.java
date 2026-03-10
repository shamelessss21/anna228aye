package ml.pipeline;

public class TestRunner {
    public static void main(String[] args) {
        FeatureStoreTest.run();
        AnomalyDetectorTest.run();
        GraphClustererTest.run();
        PreprocessingPipelineTest.run();
        System.out.println("Все тесты пройдены");
    }
}
