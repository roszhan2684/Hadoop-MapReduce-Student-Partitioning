import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Custom partitioner for Hadoop MapReduce that assigns partitions
 * based on the academic level of students.
 */
public class AcademicLevelPartitioner extends Partitioner<IntWritable, Text> {

    /**
     * Determines the partition for a given key-value pair.
     *
     * @param key The key representing the academic level.
     * @param value The value representing the data (not used in this case).
     * @param numReduceTasks The total number of reducers.
     * @return The partition number (0 to numReduceTasks - 1).
     */
    @Override
    public int getPartition(IntWritable key, Text value, int numReduceTasks) {
        // No reducers, default to partition 0
        if (numReduceTasks == 0) {
            return 0;
        }

        int academicLevel = key.get();

        // Assign partitions based on academicLevel
        switch (academicLevel) {
            case 1:
                return 0 % numReduceTasks; // part-00000
            case 2:
                return 1 % numReduceTasks; // part-00001
            case 3:
                return 2 % numReduceTasks; // part-00002
            default:
                return 3 % numReduceTasks; // part-00003
        }
    }
}
