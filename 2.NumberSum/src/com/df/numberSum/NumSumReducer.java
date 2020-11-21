package com.df.numberSum;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class NumSumReducer extends Reducer <Text, IntWritable, NullWritable, IntWritable>
{
	public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
	{
		int sum = 0;
		for (IntWritable val : values)
		{
			sum += val.get();
		}
		context.write(NullWritable.get(), new IntWritable(sum));
	}
	
	
}
