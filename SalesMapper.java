package SalesCountry;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class SalesMapper extends MapReduceBase 
implements Mapper<LongWritable, Text, Text, IntWritable> {

    public void map(LongWritable key, Text value,
        OutputCollector<Text, IntWritable> output,
        Reporter reporter) throws IOException {

        String line = value.toString();
        String[] parts = line.split(",");

        if (parts.length == 3) {
            String user = parts[0];

            String login = parts[1];
            String logout = parts[2];

            int loginTime = convertToMinutes(login);
            int logoutTime = convertToMinutes(logout);

            int duration = logoutTime - loginTime;

            output.collect(new Text(user), new IntWritable(duration));
        }
    }

    private int convertToMinutes(String time) {
        String[] t = time.split(":");
        return Integer.parseInt(t[0]) * 60 + Integer.parseInt(t[1]);
    }
}



