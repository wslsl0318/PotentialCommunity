package com.comparision.chen;

import java.util.Comparator;

public class CandidateNode implements Comparator<CandidateNode>
{
	public int id;
	public double L_temp;// decrease order

	public CandidateNode()
	{

	}

	public CandidateNode(int id, double deta_L_value)
	{
		this.id = id;
		this.L_temp = deta_L_value;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(L_temp);
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
		CandidateNode other = (CandidateNode) obj;
		if (Double.doubleToLongBits(L_temp) != Double
				.doubleToLongBits(other.L_temp))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "CandidateNode [id=" + id + ", L_temp=" + L_temp + "]";
	}

	@Override
	public int compare(CandidateNode o1, CandidateNode o2)
	{
		// TODO Auto-generated method stub
		CandidateNode node_1 = o1;
		CandidateNode node_2 = o2;
		if (node_1.L_temp < node_2.L_temp)
		{
			return 1;
		} else if (node_1.L_temp == node_2.L_temp)
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
		} else if (node_1.L_temp > node_2.L_temp)
		{
			return -1;
		}
		return 0;
	}
}
