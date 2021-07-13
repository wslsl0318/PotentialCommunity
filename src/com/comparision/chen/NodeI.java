package com.comparision.chen;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class NodeI implements Comparator<NodeI>
{
	public int id;
	public double li_new;
	public double fenzi;
	public double fenmu;
	public int indi;
	public int outdi;
	public boolean flag;

	public NodeI()
	{

	}

	public NodeI(int id, double li_new, int indi, int outdi)
	{
		this.id = id;
		this.li_new = li_new;
		this.indi = indi;
		this.outdi = outdi;
	}

	@Override
	public int compare(NodeI o1, NodeI o2)
	{
		// TODO Auto-generated method stub
		NodeI i1 = o1;
		NodeI i2 = o2;
		
		if (i1.li_new > i2.li_new)
		{
			return -1;
		} else if (i1.li_new == i2.li_new)
		{
			if (i1.id < i2.id)
			{
				return -1;
			} else if (i1.id == i2.id)
			{
				return 0;
			} else if (i1.id > i2.id)
			{
				return 1;
			}
		} else if (i1.li_new < i2.li_new)
		{
			return 1;
		}
		return 0;
	}
	
	public static void main(String[] args)
	{
		TreeSet<NodeI> list = new TreeSet<NodeI>(new NodeI());
		NodeI i1 = new NodeI(3, 3, 0, 0);
		NodeI i2 = new NodeI(2, 2, 0, 0);
		NodeI i3 = new NodeI(1, 1, 0, 0);
		NodeI i4 = new NodeI(0, 0, 0, 0);
		list.add(i1);
		list.add(i2);
		list.add(i3);
		list.add(i4);
		
		for(Iterator<NodeI> iter = list.iterator();iter.hasNext();)
		{
			NodeI i = iter.next();
			System.out.println(i.li_new+","+i.id);
		}
		System.out.println();
	}
}
