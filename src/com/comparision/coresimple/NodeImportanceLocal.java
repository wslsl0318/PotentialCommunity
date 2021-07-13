package com.comparision.coresimple;

import java.util.Comparator;

public class NodeImportanceLocal implements Comparator<NodeImportanceLocal>
{
	public int id;
	public double value;

	public NodeImportanceLocal()
	{

	}

	public NodeImportanceLocal(int id, double value)
	{
		this.id = id;
		this.value = value;
	}

	@Override
	public String toString()
	{
		return "NodeImportanceLocal [id=" + id + ", value=" + value + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(value);
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
		NodeImportanceLocal other = (NodeImportanceLocal) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public int compare(NodeImportanceLocal o1, NodeImportanceLocal o2)
	{
		// TODO Auto-generated method stub
		NodeImportanceLocal node1 = o1;
		NodeImportanceLocal node2 = o2;
		if (node1.value < node2.value)
		{
			return -1;
		} else if (node1.value == node2.value)
		{
			return 0;
		} else if (node1.value > node2.value)
		{
			return 1;
		}
		return 0;
	}
}
