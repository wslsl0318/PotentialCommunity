package com.comparision.lwp;

import java.util.Comparator;
import java.util.TreeSet;

public class LWPNode implements Comparator<LWPNode>
{
	int id;
	int degree;

	public LWPNode()
	{

	}

	public LWPNode(int id, int degree)
	{
		this.id = id;
		this.degree = degree;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(degree);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
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
		LWPNode other = (LWPNode) obj;
		if (Double.doubleToLongBits(degree) != Double
				.doubleToLongBits(other.degree))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compare(LWPNode o1, LWPNode o2)
	{
		// TODO Auto-generated method stub
		LWPNode lwpn1 = o1;
		LWPNode lwpn2 = o2;

		if (lwpn1.degree < lwpn2.degree)
		{
			return -1;
		} else if (lwpn1.degree == lwpn2.degree)
		{
			if (lwpn1.id < lwpn2.id)
			{
				return -1;
			} else if (lwpn1.id == lwpn2.id)
			{
				return 0;
			} else if (lwpn1.id > lwpn2.id)
			{
				return 1;
			}
		} else if (lwpn1.degree > lwpn2.degree)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public String toString()
	{
		return "LWPNode [id=" + id + ", degree=" + degree + "]";
	}

	public static void main(String[] args)
	{
		TreeSet<LWPNode> nodes = new TreeSet<LWPNode>(new LWPNode());
		LWPNode n1 = new LWPNode(1, 1);
		LWPNode n2 = new LWPNode(1, 2);
		LWPNode n3 = new LWPNode(3, 1);
		LWPNode n4 = new LWPNode(1, 4);
		nodes.add(n1);
		nodes.add(n2);
		nodes.add(n3);
		nodes.add(n4);
		System.out.println(nodes);
	}
}
