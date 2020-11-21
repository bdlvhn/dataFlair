package com.df.fileJoin.adv;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class fileJoinReducer extends Reducer<Text,JoinWritable,NullWritable,Text>
{
	
	public void reduce(Text key, Iterable<JoinWritable> values,Context context) throws IOException,InterruptedException
	{
		String name = null;
		String dept = null;
		
		for(JoinWritable val:values)
		{
			if (val.getMrFileName().toString().equals("empname.txt"))
			{
				name = val.getMrValue().toString();
			}
			else if (val.getMrFileName().toString().equals("empdept.txt"))
			{
				dept = val.getMrValue().toString();
			}
		}
		
		StringBuffer rec = new StringBuffer(key.toString()).append(",");
		rec.append(name).append(",").append(dept);
		
		context.write(NullWritable.get(),new Text(rec.toString()));
//		
	}
}