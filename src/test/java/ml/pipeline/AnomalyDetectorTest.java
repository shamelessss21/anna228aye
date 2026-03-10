package ml.pipeline;

import java.util.List;
import java.util.Map;

public class AnomalyDetectorTest {

    public static void run() {
        List<DataPoint> points = List.of(
                new DataPoint("p1", Map.of("income", 10.0)),
                new DataPoint("p2", Map.of("income", 11.0)),
                new DataPoint("p3", Map.of("income", 12.0)),
                new DataPoint("p4", Map.of("income", 30.0))
        );

        AnomalyDetector detector = new AnomalyDetector();
        List<DataPoint> anomalies = detector.detectByZScore(points, "income", 1.6);

        TestSupport.assertEquals(1, anomalies.size(), "ожидается одна аномалия");
        TestSupport.assertEquals("p4", anomalies.get(0).getId(), "аномалией должен быть p4");
    }
}
