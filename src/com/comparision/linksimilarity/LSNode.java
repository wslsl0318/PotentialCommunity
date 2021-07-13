package com.comparision.linksimilarity;

import java.util.Comparator;

public class LSNode implements Comparator<LSNode>
{
	public int id;
	public double lsValue;

	public LSNode()
	{

	}

	public LSNode(int id, double lsValue)
	{
		this.id = id;
		this.lsValue = lsValue;
	}
	
	@Override
	public int compare(LSNode o1, LSNode o2)
	{
		// TODO Auto-generated method stub
		LSNode ls1 = o1;
		LSNode ls2 = o2;
		
		if (ls1.lsValue > ls2.lsValue)
		{
			return -1;
		} else if (ls1.lsValue == ls2.lsValue)
		{
			if (ls1.id < ls2.id)
			{
				return -1;
			} else if (ls1.id == ls2.id)
			{
				return 0;
			} else if (ls1.id > ls2.id)
			{
				return 1;
			}
		} else if (ls1.lsValue < ls2.lsValue)
		{
			return 1;
		}
		return 0;
	}
	
	public static void main(String[] args)
	{
		
	}
}
