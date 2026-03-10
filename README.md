# Курсовой проект: preprocessing pipeline для ML (Java)

Проект демонстрирует интеграцию структур данных и алгоритмов в систему предварительной обработки данных:
- **HashMap / TreeMap** для хранения объектов и индексации;
- **поиск аномалий** по Z-score (по всем признакам);
- **графовая кластеризация** через поиск компонент связности.

## Архитектура
- `CsvDataLoader` — загрузка `DataPoint` из CSV;
- `FeatureStore` — интерфейс хранилища;
- `HashFeatureStore` / `TreeFeatureStore` — реализации хранилища;
- `FeatureRangeIndex` — индекс на `TreeMap` для диапазонных запросов;
- `AnomalyDetector` — статистический детектор аномалий;
- `Graph`, `GraphBuilder`, `GraphClusterer` — построение графа и кластеризация;
- `PreprocessingPipeline` — orchestration всех этапов;
- `Main` — консольная демонстрация.

## Формат CSV
Пример `data/data.csv`:
```csv
age,income,spend
22,35,30
23,36,31
...
```

## Запуск
```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out ml.pipeline.Main
```

Можно передать путь к своему файлу:
```bash
java -cp out ml.pipeline.Main path/to/your.csv
```
