package com.comparision.methods;

import java.util.ArrayList;

public class SeedAndCommunity
{
	public int seed;
	public ArrayList<Integer> community;

	public SeedAndCommunity()
	{
		this.community = new ArrayList<Integer>();
	}

	public SeedAndCommunity(int seed, ArrayList<Integer> community)
	{
		this.seed = seed;
		this.community = community;
	}

	@Override
	public String toString()
	{
		return "SeedAndCommunity [seed=" + seed + ", community=" + community
				+ "]";
	}

}
