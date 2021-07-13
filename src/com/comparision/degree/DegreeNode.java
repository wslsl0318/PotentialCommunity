package com.comparision.degree;

import java.util.Comparator;

public class DegreeNode implements Comparator<DegreeNode>
{
	public int id;
	public int value;
	
	public DegreeNode()
	{

	}

	public DegreeNode(int id, int value)
	{
		this.id = id;
		this.value = value;
	}
	
	@Override
	public int compare(DegreeNode o1, DegreeNode o2)
	{
		DegreeNode node1 = o1;
		DegreeNode node2 = o2;

		if (node1.value < node2.value)
		{
			return -1;
		} else if (node1.value == node2.value)
		{
			if (node1.id < node2.id)
			{
				return -1;
			} else if (node1.id == node2.id)
			{
				return 0;
			} else if (node1.id > node2.id)
			{
				return 1;
			}
		} else if (node1.value > node2.value)
		{
			return 1;
		}
		return 0;
	}
}
