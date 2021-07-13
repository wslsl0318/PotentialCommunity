package com.comparision.ls;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

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

	@Override
	public String toString()
	{
		return "LSNode [id=" + id + ", lsValue=" + lsValue + "]";
	}

	public static void main(String[] args)
	{
		TreeSet<LSNode> ns = new TreeSet<LSNode>(new LSNode());
		LSNode n1 = new LSNode(3, 3);
		LSNode n2 = new LSNode(4, 3);
		LSNode n3 = new LSNode(2, 3);
		LSNode n4 = new LSNode(1, 5);
		ns.add(n1);
		ns.add(n2);
		ns.add(n3);
		ns.add(n4);
		for (Iterator<LSNode> iter = ns.iterator(); iter.hasNext();)
		{
			System.out.println(iter.next());
		}
	}
}
