package com.comparision.methods;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class NodeInfluence implements Comparator<NodeInfluence>
{
	public int id;
	public double influence;

	public NodeInfluence()
	{

	}

	public NodeInfluence(int id, double influence)
	{
		this.id = id;
		this.influence = influence;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(influence);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeInfluence other = (NodeInfluence) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(influence) != Double
				.doubleToLongBits(other.influence))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "NodeInfluence [id=" + id + ", influence=" + influence + "]";
	}

	@Override
	public int compare(NodeInfluence o1, NodeInfluence o2)
	{
		// TODO Auto-generated method stub
		NodeInfluence node1 = o1;
		NodeInfluence node2 = o2;
		if (node1.influence > node2.influence)
		{
			return -1;
		} else if (node1.influence == node2.influence)
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
		} else if (node1.influence < node2.influence)
		{
			return 1;
		}
		return 0;
	}

	public static void main(String[] args)
	{
		TreeSet<NodeInfluence> nodes = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		NodeInfluence node1 = new NodeInfluence(1, 2);
		NodeInfluence node2 = new NodeInfluence(2, 2);
		NodeInfluence node3 = new NodeInfluence(1, 3);
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		for (Iterator<NodeInfluence> iter_node = nodes.iterator(); iter_node
				.hasNext();)
		{
			NodeInfluence node = iter_node.next();
			System.out.println(node);
		}
		int i=8;
		System.out.println(i+","+(double)i+","+i);
	}
}
