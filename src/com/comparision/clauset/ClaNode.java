package com.comparision.clauset;

import java.util.Comparator;

public class ClaNode implements Comparator<ClaNode>
{
	int id;
	double detaR;

	public ClaNode()
	{

	}

	public ClaNode(int id, double detaR)
	{
		this.id = id;
		this.detaR = detaR;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(detaR);
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
		ClaNode other = (ClaNode) obj;
		if (Double.doubleToLongBits(detaR) != Double
				.doubleToLongBits(other.detaR))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compare(ClaNode o1, ClaNode o2)
	{
		// TODO Auto-generated method stub

		ClaNode cn1 = o1;
		ClaNode cn2 = o2;

		if (cn1.detaR > cn2.detaR)
		{
			return -1;
		} else if (cn1.detaR == cn2.detaR)
		{
			if (cn1.id < cn2.id)
			{
				return -1;
			} else if (cn1.id == cn2.id)
			{
				return 0;
			} else if (cn1.id > cn2.id)
			{
				return 1;
			}
		} else if (cn1.detaR < cn2.detaR)
		{
			return 1;
		}
		return 0;
	}

}
