package com.comparision.methods;

import java.util.ArrayList;

public class Measurements
{
	public String algorithm_name;
	public long costTime_average;
	public double NMI_average;
	public double nodes_correctly_classified_percentage;
	public ArrayList<Double> RPF_average;

	public Measurements()
	{

	}

	public Measurements(String algorithm_name, long costTime_average,
			double NMI_average, double nodes_correctly_classified_percentage,
			ArrayList<Double> RPF_average)
	{
		this.algorithm_name = algorithm_name;
		this.costTime_average = costTime_average;
		this.NMI_average = NMI_average;
		this.nodes_correctly_classified_percentage = nodes_correctly_classified_percentage;
		this.RPF_average = RPF_average;
	}
}
