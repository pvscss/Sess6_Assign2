package Assign2;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class Partitioners extends Partitioner<LongWritable,Text> implements Configurable
{
	int AvgValue2=0;
	
	public int getPartition(LongWritable key,Text value,int noReduceTasks)
	{
		int return_value=0;
		
		if(key.get()>=AvgValue2)
		{
			return_value=1;
		}
		return return_value;
	}

	@Override
	public Configuration getConf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConf(Configuration conf) 
	{
		// TODO Auto-generated method stub
		AvgValue2 = Integer.parseInt(conf.get("AvgValue1"));
	}
}	
