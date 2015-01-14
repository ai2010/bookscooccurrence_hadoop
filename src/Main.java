//package src;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;

//import org.apache.log4j.BasicConfigurator;

public class Main {

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, MyWritable> {

    	//avoid the first line (header)
        private static boolean isHeader(String line) {
            return line.indexOf("\'user\'") != -1;
        }

        @Override
        public void map(LongWritable key, Text value, OutputCollector<Text, MyWritable> output, Reporter reporter) throws IOException {
            String rowText = value.toString();
            if (!isHeader(rowText) ) {
                CsvRow row = new CsvRow(rowText);
				//multiple output...(number of combinations b1b2 b3b4 ...)
				for(int i=0; i< row.numberofcouples; i++)
				{
	                output.collect(new Text(String.format("%s", row.couples.get(i))), new MyWritable(1));	
				}
            }

        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, MyWritable, Text, MyWritable> {

        @Override
        public void reduce(Text key, Iterator<MyWritable> iterator, OutputCollector<Text, MyWritable> output, Reporter reporter) throws IOException {
            MyWritable val = new MyWritable();
            
            MyWritable o;
            while (iterator.hasNext()) {
                o = iterator.next();
                val.numpair += o.numpair;
            }
            output.collect(key, val);
        }
    }

    public static void main(String[] args) throws IOException {
		//org.apache.log4j.BasicConfigurator.configure();
		
        JobConf conf = new JobConf(Main.class);
        conf.setJobName("BooksHadoop");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(MyWritable.class);

        conf.setMapperClass(Map.class);
        conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(MyOutputFormat.class);

		//conf.set("mapred.job.tracker", "local"); // use localjobrunner
		//conf.set("fs.default.name", "file:///"); // read from local hard disk

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }
}
