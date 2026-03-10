package ml.pipeline;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvDataLoader {

    public List<DataPoint> load(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);
        if (lines.isEmpty()) {
            return List.of();
        }

        String[] header = splitCsvLine(lines.get(0));
        if (header.length < 2) {
            throw new IllegalArgumentException("CSV должен содержать id и хотя бы один признак");
        }

        List<RawRow> rawRows = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            rawRows.add(new RawRow(splitCsvLine(line)));
        }

        double[] means = calculateMeans(rawRows, header.length);

        List<DataPoint> result = new ArrayList<>();
        for (RawRow row : rawRows) {
            if (row.values.length != header.length || row.values[0].trim().isEmpty()) {
                continue;
            }

            Map<String, Double> features = new LinkedHashMap<>();
            boolean valid = true;

            for (int i = 1; i < header.length; i++) {
                String raw = row.values[i].trim();
                if (raw.isEmpty()) {
                    features.put(header[i], means[i]);
                } else {
                    try {
                        features.put(header[i], Double.parseDouble(raw));
                    } catch (NumberFormatException ex) {
                        valid = false;
                        break;
                    }
                }
            }

            if (valid) {
                result.add(new DataPoint(row.values[0].trim(), features));
            }
        }

        return result;
    }

    private double[] calculateMeans(List<RawRow> rows, int columns) {
        double[] sums = new double[columns];
        int[] counts = new int[columns];

        for (RawRow row : rows) {
            if (row.values.length != columns) {
                continue;
            }
            for (int i = 1; i < columns; i++) {
                String raw = row.values[i].trim();
                if (raw.isEmpty()) {
                    continue;
                }
                try {
                    sums[i] += Double.parseDouble(raw);
                    counts[i]++;
                } catch (NumberFormatException ignored) {
                }
            }
        }

        double[] means = new double[columns];
        for (int i = 1; i < columns; i++) {
            means[i] = counts[i] == 0 ? 0.0 : sums[i] / counts[i];
        }
        return means;
    }

    private String[] splitCsvLine(String line) {
        return line.split(",", -1);
    }

    private static class RawRow {
        private final String[] values;
        private RawRow(String[] values) {
            this.values = values;
        }
    }
}
