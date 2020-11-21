package com.df.wc;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class TokenizerMapper extends Mapper <Object, Text, Text, IntWritable>
{

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException
	{
//		key = byteOffset
//		value = complete line
		/* Hello
		   World */
		StringTokenizer itr = new StringTokenizer(value.toString());
		while (itr.hasMoreTokens())
		{
			context.write(new Text(itr.nextToken()), new IntWritable(1));
//															Hello, 1
//															World, 1
		}
	}
}