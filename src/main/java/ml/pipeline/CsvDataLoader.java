package ml.pipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Загрузка данных из CSV формата:
 * age,income,spend
 * 22,35,30
 */
public class CsvDataLoader {

    public List<DataPoint> load(String filePath) throws IOException {
        List<DataPoint> points = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String headerLine = reader.readLine();
            if (headerLine == null || headerLine.isBlank()) {
                return points;
            }

            String[] featureNames = headerLine.split(",");
            String line;
            int id = 0;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] tokens = line.split(",");
                if (tokens.length != featureNames.length) {
                    throw new IllegalArgumentException("Некорректная строка CSV: " + line);
                }

                Map<String, Double> features = new HashMap<>();
                for (int i = 0; i < tokens.length; i++) {
                    features.put(featureNames[i].trim(), Double.parseDouble(tokens[i].trim()));
                }
                points.add(new DataPoint(id++, features));
            }
        }

        return points;
    }
}
