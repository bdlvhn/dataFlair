package com.df.fileJoin.adv;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class fileJoinDriver {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
		if (otherArgs.length != 3)
		{
			System.err.println("Usage: Join <in1> <in2> <out>");
			System.exit(2);
		}
				
		Job job = new Job(conf,"File Join");
		job.setJarByClass(fileJoinDriver.class);
		job.setMapperClass(fileJoinMapper.class);
		job.setReducerClass(fileJoinReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(JoinWritable.class);
		
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileInputFormat.addInputPath(job, new Path(otherArgs[1]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
//		
	}
}