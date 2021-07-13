package com.pulicoperations.methods;

import java.util.ArrayList;
import java.util.Collection;

public class ARI
{
	public static double ARIPartitionUnoverLap(
			ArrayList<ArrayList<Integer>> partitionF,
			ArrayList<ArrayList<Integer>> partitionR, int nodeCount)
	{
		int[][] XY = new int[partitionR.size()][partitionF.size()];

		int[] X = new int[partitionR.size()];

		int[] Y = new int[partitionF.size()];

		int i = 0;

		int j = 0;

		double ari = 0;

		double sum1 = 0;

		for (ArrayList<Integer> com1 : partitionR)
		{
			j = 0;
			for (ArrayList<Integer> com2 : partitionF)
			{
				XY[i][j] = intersect(com1, com2);

				sum1 += (double) (XY[i][j] * (XY[i][j] - 1)) / 2;

				j++;
			}
			i++;
		}

		// System.out.println("X[][]");
		// for (i = 0; i < 2; i++)
		// {
		// for (j = 0; j < 2; j++)
		// {
		// System.out.print(XY[i][j] + " ");
		// }
		// System.out.println();
		// }
		// System.out.println();

		double t1 = 0;

		for (i = 0; i < partitionR.size(); i++)
		{
			X[i] = partitionR.get(i).size();

			t1 += (double) (X[i] * (X[i] - 1)) / 2;
		}

		// System.out.println("X[]");
		// for(i=0;i<2;i++)
		// {
		// System.out.print(X[i]+" ");
		// }
		// System.out.println();

		double t2 = 0;

		for (j = 0; j < partitionF.size(); j++)
		{
			Y[j] = partitionR.get(j).size();

			t2 += (double) (Y[j] * (Y[j] - 1)) / 2;
		}

		// System.out.println("Y[]");
		// for(i=0;i<2;i++)
		// {
		// System.out.print(Y[i]+" ");
		// }
		// System.out.println();

		double t3 = 0;

		t3 = 2 * t1 * t2 / (double) (nodeCount * (nodeCount - 1));

		ari = (sum1 - t3) / (0.5 * (t1 + t2) - t3);

		// System.out.println(sum1 + " " + t1 + " " + t2 + " " + t3);

		return ari;
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

	public static void main(String[] args)
	{
		ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> partitionR = new ArrayList<ArrayList<Integer>>();
		int nodeCount = 10;
		ArrayList<Integer> f1 = new ArrayList<Integer>();
		ArrayList<Integer> f2 = new ArrayList<Integer>();
		f1.add(0);
		f1.add(8);
		f1.add(7);
		f1.add(6);
		f1.add(5);
		f2.add(4);
		f2.add(3);
		f2.add(2);
		f2.add(1);
		f2.add(9);
		ArrayList<Integer> r1 = new ArrayList<Integer>();
		ArrayList<Integer> r2 = new ArrayList<Integer>();
		r1.add(0);
		r1.add(1);
		r1.add(2);
		r1.add(3);
		r1.add(4);
		r2.add(5);
		r2.add(6);
		r2.add(7);
		r2.add(8);
		r2.add(9);
		partitionF.add(f1);
		partitionF.add(f2);
		partitionR.add(r1);
		partitionR.add(r2);
		System.out.println(ARI.ARIPartitionUnoverLap(partitionF, partitionR,
				nodeCount));
	}
}
