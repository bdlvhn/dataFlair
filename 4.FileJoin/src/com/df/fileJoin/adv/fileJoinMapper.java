package com.df.fileJoin.adv;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class fileJoinMapper extends Mapper<LongWritable,Text,Text,JoinWritable>
{
	String inputFileName;
	
	@Override
	protected void setup(Context context)
	{
		FileSplit fileSplit = (FileSplit) context.getInputSplit();
		inputFileName = fileSplit.getPath().getName();
	}
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		String[] tokens = value.toString().split(",");
		if (tokens.length == 2)
		{
			context.write(new Text(tokens[0]), new JoinWritable(tokens[1],inputFileName));
//									101							Gaurav		empname.txt
//									101							Sales		empdept.txt
			}
//		
	}
}