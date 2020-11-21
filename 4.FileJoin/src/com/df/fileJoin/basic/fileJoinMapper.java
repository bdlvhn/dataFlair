package com.df.fileJoin.basic;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class fileJoinMapper extends Mapper<LongWritable,Text,Text,Text>
{
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String[] tokens = value.toString().split(",");
		if (tokens.length == 2)
		{
			context.write(new Text(tokens[0]), new Text(tokens[1]));
			}
//		
	}
}
