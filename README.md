# Java ML Preprocessing Pipeline

Пример интеграции структур данных и алгоритмов в preprocessing для ML:
- HashMap + TreeMap для хранения и индексирования признаков;
- поиск аномалий (Z-score);
- графовая кластеризация (компоненты связности по порогу расстояния).

## Запуск
```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out ml.pipeline.Main
```
