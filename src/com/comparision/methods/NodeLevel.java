package com.comparision.methods;

public class NodeLevel
{
	public int node;
	public int level;

	public NodeLevel()
	{

	}

	public NodeLevel(int node, int level)
	{
		this.node = node;
		this.level = level;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + level;
		result = prime * result + node;
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
		NodeLevel other = (NodeLevel) obj;
		// if (level != other.level)
		// return false;
		if (node != other.node)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "NodeLevel [node=" + node + ", level=" + level + "]";
	}
}
