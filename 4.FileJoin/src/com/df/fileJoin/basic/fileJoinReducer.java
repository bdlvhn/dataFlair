package com.df.fileJoin.basic;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class fileJoinReducer extends Reducer<Text,Text,NullWritable,Text>
{
	
	public void reduce(Text key, Iterable<Text> values,Context context) throws IOException,InterruptedException
	{
		
		StringBuffer rec = new StringBuffer(key.toString()).append(",");
		int count = 0;
		
		for(Text val:values)
		{
			rec.append(val.toString());
			if (count<1)
			{
				rec.append(",");
			}
			count++;
		}
		
		context.write(NullWritable.get(),new Text(rec.toString()));
		
//		
	}
}
