# Hadoop MapReduce — Student Partitioning by Academic Level

Two implementations of the same MapReduce job, written for CSUF's Advanced Database Management Systems course: group student records by academic level, with each level routed to its own reducer.

## `java/` — native Hadoop job
- `StudentMapper` emits `(academicLevel, studentName)`
- `AcademicLevelPartitioner` is a custom `Partitioner` that sends each academic level to its own reduce task
- `StudentReducer` collects the students for each level
- `StudentDriver` configures and runs the job

```sh
hadoop jar student-partition.jar StudentDriver <input> <output>
```

## `python-streaming/` — Hadoop Streaming
- `mapper.py` / `reducer.py` read `name,level` lines from stdin and group by level
- `students.txt` is a small sample input; `sample-output/` holds the part files from a 4-reducer run

```sh
hadoop jar $HADOOP_HOME/share/hadoop/tools/lib/hadoop-streaming-*.jar \
  -input students.txt -output out \
  -mapper mapper.py -reducer reducer.py -file mapper.py -file reducer.py
```

Quick local check without Hadoop:

```sh
cat students.txt | ./mapper.py | sort | ./reducer.py
```
