package com.comparision.coresimple;

import java.util.Comparator;

public class KCoreNode implements Comparator<KCoreNode>
{
	public int id;
	public int level;
	public int value;

	public KCoreNode()
	{

	}

	public KCoreNode(int id, int level)
	{
		this.id = id;
		this.level = level;
	}

	@Override
	public int compare(KCoreNode o1, KCoreNode o2)
	{
		KCoreNode node1 = o1;
		KCoreNode node2 = o2;

		if (node1.value < node2.value)
		{
			return -1;
		} else if (node1.value == node2.value)
		{
			if (node1.level < node2.level)
			{
				return -1;
			} else if (node1.level == node2.level)
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
			} else if (node1.level > node2.level)
			{
				return 1;
			}
		} else if (node1.value > node2.value)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public String toString()
	{
		return "KCoreNode [id=" + id + ", level=" + level + ", value=" + value
				+ "]";
	}
	
}
