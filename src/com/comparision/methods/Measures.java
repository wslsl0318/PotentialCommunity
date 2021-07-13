package com.comparision.methods;

import java.util.ArrayList;

public class Measures
{
	public double NMI;
	// effectiveness: that the seed node shou in centre of local community
	public double E;
	// rate of effective local communities to all communities
	// public double rate;
	// rate of distinct effective communities to all effective local communities
	public double rate_distinct;
	//effective*distinct
	public double ED;
	public ArrayList<Double> rpf;
	public ArrayList<Double> node_rank_prcent;

	public Measures()
	{
		this.rpf = new ArrayList<Double>();
		this.node_rank_prcent = new ArrayList<Double>();
	}

	public Measures(double NMI, ArrayList<Double> rpf, double E, // double rate,
			double rate_distinct, double ED, ArrayList<Double> node_rank_prcent)
	{
		this.NMI = NMI;
		this.rpf = rpf;
		this.E = E;
		// this.rate = rate;
		this.rate_distinct = rate_distinct;
		this.ED = ED;
		this.node_rank_prcent = node_rank_prcent;
	}

	@Override
	public String toString()
	{
		return "Measures [NMI=" + NMI + ", E=" + E + ", rate_distinct="
				+ rate_distinct + ", ED=" + ED + ", rpf=" + rpf
				+ ", node_rank_prcent=" + node_rank_prcent + "]";
	}
}
