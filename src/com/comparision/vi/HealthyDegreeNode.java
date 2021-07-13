package com.comparision.vi;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class HealthyDegreeNode implements Comparator<HealthyDegreeNode>
{
	public int id;
	public double healthy_degree;

	public HealthyDegreeNode()
	{

	}

	public HealthyDegreeNode(int id, double healthy_degree)
	{
		this.id = id;
		this.healthy_degree = healthy_degree;
	}

	@Override
	public String toString()
	{
		return "HealthyDegreeNode [id=" + id + ", healthy_degree="
				+ healthy_degree + "]";
	}

	@Override
	public int compare(HealthyDegreeNode o1, HealthyDegreeNode o2)
	{
		// TODO Auto-generated method stub
		HealthyDegreeNode node_1 = o1;
		HealthyDegreeNode node_2 = o2;
		if (node_1.healthy_degree < node_2.healthy_degree)
		{
			return 1;

		} else if (node_1.healthy_degree == node_2.healthy_degree)
		{
			if (node_1.id > node_2.id)
			{
				return 1;

			} else if (node_1.id == node_2.id)
			{
				return 0;
			} else if (node_1.id < node_2.id)
			{
				return -1;
			}
		} else if (node_1.healthy_degree > node_2.healthy_degree)
		{
			return -1;
		}
		return 0;
	}

	public static void main(String[] args)
	{
		TreeSet<HealthyDegreeNode> nodes = new TreeSet<HealthyDegreeNode>(
				new HealthyDegreeNode());
		HealthyDegreeNode n1 = new HealthyDegreeNode(1, 1);
		HealthyDegreeNode n2 = new HealthyDegreeNode(2, 4);
		HealthyDegreeNode n3 = new HealthyDegreeNode(3, 2);
		HealthyDegreeNode n4 = new HealthyDegreeNode(4, 1);
		HealthyDegreeNode n5 = new HealthyDegreeNode(1, 1);
		nodes.add(n5);
		nodes.add(n4);
		nodes.add(n3);
		nodes.add(n2);
		nodes.add(n1);
		for (Iterator<HealthyDegreeNode> iter = nodes.iterator(); iter
				.hasNext();)
		{
			System.out.println(iter.next());
		}
	}
}
