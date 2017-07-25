package Assign2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Sequence_Reader 
{
	public static void main(String[] args) throws Exception 
	{
		if (args.length != 5) 
			{
		      System.err.println("Enter all the parameters");
		      System.exit(-1);
		    }
		
		Configuration conf=new Configuration();
		
		int MaxValue = Integer.parseInt(args[2]);
		int MinValue = Integer.parseInt(args[3]);
		int NoOfReducers = Integer.parseInt(args[4]);

		int AvgValue = (MaxValue+MinValue)/NoOfReducers;
		conf.set("AvgValue1", String.valueOf(AvgValue));
				
		@SuppressWarnings("deprecation")
		Job job=new Job(conf,"TV");
		job.setJarByClass(Sequence_Reader.class);
		
		
		//Provide paths to pick the input file for the job
		FileInputFormat.setInputPaths(job,new Path(args[0]));
		
		//Provide paths to pick the output file for the job, and delete it if already present
		Path OutputPath=new Path(args[1]);
		FileOutputFormat.setOutputPath(job, OutputPath);
		OutputPath.getFileSystem(conf).delete(OutputPath,true);
		
		
		//To set the mapper and reducer of this job
		job.setMapperClass(Mappers.class);
		job.setReducerClass(Reducer.class);
		job.setNumReduceTasks(NoOfReducers);
		job.setPartitionerClass(Partitioners.class);
		
		//set the input and output format class
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		//Set mapper key-value class types
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);
						
		//Execute the Job
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}	

}
