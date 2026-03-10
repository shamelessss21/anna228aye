package ml.pipeline;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FeatureStoreTest {

    public static void run() {
        FeatureStore store = new FeatureStore();
        store.add(new DataPoint("p1", Map.of("income", 20.0)));
        store.add(new DataPoint("p2", Map.of("income", 40.0)));
        store.add(new DataPoint("p3", Map.of("income", 60.0)));

        List<DataPoint> points = store.rangeQuery("income", 30.0, 60.0);
        Set<String> ids = points.stream().map(DataPoint::getId).collect(Collectors.toSet());

        TestSupport.assertEquals(Set.of("p2", "p3"), ids, "rangeQuery должен вернуть p2 и p3");
    }
}
