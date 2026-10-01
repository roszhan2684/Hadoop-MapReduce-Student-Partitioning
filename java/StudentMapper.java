import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class StudentMapper extends Mapper<Object, Text, IntWritable, Text> {
    private IntWritable academicLevelKey = new IntWritable();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(",");
        
        if (fields.length >= 2) { // Ensure record has enough fields
            int academicLevel = Integer.parseInt(fields[1].trim()); // Assuming level is in the second column
            academicLevelKey.set(academicLevel);
            context.write(academicLevelKey, value);
        }
    }
}
