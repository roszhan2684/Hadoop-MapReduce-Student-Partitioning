<div align="center">

# Hadoop MapReduce · Student Partitioning

**Route every record to the right reducer. A custom Hadoop `Partitioner` that splits students by academic level into separate output files, in native Java and in Python streaming.**

![Hadoop](https://img.shields.io/badge/Apache%20Hadoop-MapReduce-66CCFF?style=flat-square&logo=apachehadoop&logoColor=black)
![Java](https://img.shields.io/badge/Java-Mapper%20%7C%20Partitioner%20%7C%20Reducer-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Python](https://img.shields.io/badge/Python-Hadoop%20Streaming-3776AB?style=flat-square&logo=python&logoColor=white)

<img src="docs/screenshots/hadoop-run.png" width="760" alt="Hadoop job run and partitioned output" />

</div>

---

## Why it matters

By default Hadoop hashes keys to reducers, so you can't predict which output file a record lands in. When downstream teams want **one file per category** (per region, per tier, or here per academic level), you need a custom `Partitioner`. This project shows the pattern end to end on a small, readable dataset.

## How the data flows

```mermaid
flowchart LR
    A["students.txt<br/>name,level"] --> M["StudentMapper<br/>emit (level, record)"]
    M --> P{"AcademicLevelPartitioner<br/>level → reducer"}
    P -->|level 1| R0["Reducer 0 → part-r-00000"]
    P -->|level 2| R1["Reducer 1 → part-r-00001"]
    P -->|level 3| R2["Reducer 2 → part-r-00002"]
    P -->|level 4| R3["Reducer 3 → part-r-00003"]
```

## What's inside

| Path | Description |
|---|---|
| `java/StudentMapper.java` | Parses `name,level` and emits `(level, record)` |
| `java/AcademicLevelPartitioner.java` | Custom `Partitioner<IntWritable, Text>` mapping each level to its own reduce task |
| `java/StudentReducer.java` | Writes each level's records to its partition file |
| `java/StudentDriver.java` | Configures the job with 4 reduce tasks and the custom partitioner |
| `python-streaming/` | The same grouping as a Hadoop Streaming `mapper.py` / `reducer.py`, plus sample input and output |

## Run it

**Java (Hadoop local mode)**

```sh
cd java
javac -cp "$(hadoop classpath)" -d classes *.java
jar cf student-partition.jar -C classes .
hadoop jar student-partition.jar StudentDriver ../python-streaming/students.txt out
head out/part-r-*
```

**Python streaming**

```sh
cd python-streaming
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-*.jar \
  -input students.txt -output out \
  -mapper mapper.py -reducer reducer.py -file mapper.py -file reducer.py

# or check it without Hadoop:
cat students.txt | ./mapper.py | sort | ./reducer.py
```

## About

Built by **Roszhan Raj** for Advanced Database Management Systems at California State University, Fullerton.
