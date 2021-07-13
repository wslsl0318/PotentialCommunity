package com.pulicoperations.methods;

import java.util.ArrayList;
import java.util.Collection;

public class NMI
{
	public static double NMIPartitionUnoverLap(
			ArrayList<ArrayList<Integer>> partitionF,
			ArrayList<ArrayList<Integer>> partitionR, int nodeCount)
	{
		double[][] XY = new double[partitionR.size()][partitionF.size()];
		double[] X = new double[partitionR.size()];
		double[] Y = new double[partitionF.size()];
		int i = 0;
		int j = 0;
		for (ArrayList<Integer> com1 : partitionR)
		{
			j = 0;
			for (ArrayList<Integer> com2 : partitionF)
			{
				XY[i][j] = intersect(com1, com2);
				X[i] += XY[i][j];
				Y[j] += XY[i][j];
				j++;
			}
			i++;
		}
		int N = nodeCount;
		double Ixy = 0;
		for (i = 0; i < partitionR.size(); i++)
		{
			for (j = 0; j < partitionF.size(); j++)
			{
				if (XY[i][j] > 0)
				{
					Ixy += ((double) XY[i][j] / N)
							* (Math.log((double) XY[i][j] * N / (X[i] * Y[j]))
							/ Math.log(2.0));
				}
			}
		}
		double Hx = 0;
		double Hy = 0;
		for (i = 0; i < partitionR.size(); i++)
		{
			if (X[i] > 0)
				Hx += h((double) X[i] / N);
		}
		for (j = 0; j < partitionF.size(); j++)
		{
			if (Y[j] > 0)
				Hy += h((double) Y[j] / N);
		}
		double InormXY = 2 * Ixy / (Hx + Hy);

		//check the wrong value of NMI
		if(Double.isNaN(InormXY)) {
			System.out.println("Here is wrong!!");
		}
		return InormXY;
	}

	public static double NMIPartitionOverLap(
			Collection<Collection<Integer>> partitionF,
			Collection<Collection<Integer>> partitionR, int nodeCount)
	{
		int[][] XY = new int[partitionR.size()][partitionF.size()];
		int[] X = new int[partitionR.size()];
		int[] Y = new int[partitionF.size()];
		int i = 0;
		int j = 0;
		for (Collection<Integer> com1 : partitionR)
		{
			j = 0;
			for (Collection<Integer> com2 : partitionF)
			{
				XY[i][j] = intersect(com1, com2);
				X[i] += XY[i][j];
				Y[j] += XY[i][j];
				j++;
			}
			i++;
		}
		int N = nodeCount;
		double Ixy = 0;
		for (i = 0; i < partitionR.size(); i++)
		{
			for (j = 0; j < partitionF.size(); j++)
			{
				if (XY[i][j] > 0)
				{
					Ixy += ((double) XY[i][j] / N)
							* (Math.log((double) XY[i][j] * N / (X[i] * Y[j])) / Math
									.log(2.0));
				}
			}
		}
		double Hx = 0;
		double Hy = 0;
		for (i = 0; i < partitionR.size(); i++)
		{
			if (X[i] > 0)
				Hx += h((double) X[i] / N);
		}
		for (j = 0; j < partitionF.size(); j++)
		{
			if (Y[j] > 0)
				Hy += h((double) Y[j] / N);
		}
		double InormXY = Ixy / Math.sqrt(Hx * Hy);
		return InormXY;
	}

	private static double h(double p)
	{
		return -p * (Math.log(p) / Math.log(2.0));
	}

	private static int intersect(Collection<Integer> com1,
			Collection<Integer> com2)
	{
		int num = 0;
		for (Integer v1 : com1)
		{
			if (com2.contains(v1))
				num++;
		}
		return num;
	}

	// public static double NMIPartitionLocal(int[][] x, int nodeCount)
	// {
	// double y = 0;
	// double y1 = 0;
	// double y2 = 0;
	// double irf = 0;
	//
	// for (int i = 0; i < 2; i++)
	// {
	// for (int j = 0; j < 2; j++)
	// {
	// int xi = 0;
	// for (int k = 0; k < 2; k++)
	// {
	// xi += x[i][k];
	// }
	//
	// int xj = 0;
	// for (int k = 0; k < 2; k++)
	// {
	// xj += x[k][j];
	// }
	//
	// y += x[i][j]
	// * Math.log((double) (x[i][j] * nodeCount)
	// / (double) (xi * xj));
	// }
	// }
	//
	// y = y * (-2);
	//
	// for (int i = 0; i < 2; i++)
	// {
	// int xi = 0;
	// for (int k = 0; k < 2; k++)
	// {
	// xi += x[i][k];
	// }
	//
	// y1 += xi * Math.log((double) xi / (double) nodeCount);
	// }
	//
	// for (int j = 0; j < 2; j++)
	// {
	// int xj = 0;
	// for (int k = 0; k < 2; k++)
	// {
	// xj += x[k][j];
	// }
	//
	// y2 += xj * Math.log((double) xj / (double) nodeCount);
	// }
	//
	// irf = y / (y1 * y2);
	//
	// return irf;
	// }

	public static void main(String[] args)
	{

	}
}
