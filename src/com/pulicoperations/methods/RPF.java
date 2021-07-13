package com.pulicoperations.methods;

import java.util.ArrayList;
import java.util.Collection;

public class RPF
{
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

	public static ArrayList<Double> getRecall_Precision_F(
			ArrayList<ArrayList<Integer>> partitionF,
			ArrayList<ArrayList<Integer>> partitionR)
	{
		if (partitionF.size() == 0)
		{
			ArrayList<Double> rpf = new ArrayList<Double>();

			return rpf;
		}

		ArrayList<Double> rpf = new ArrayList<Double>();

		double recall = 0;
		double precision = 0;
		double f_score = 0;

		ArrayList<Integer> innerMembersF = partitionF.get(0);
		ArrayList<Integer> innerMembersR = partitionR.get(0);

		int fenzi = intersect(innerMembersF, innerMembersR);
		int fenmu_R = innerMembersR.size();
		int fenmu_F = innerMembersF.size();

		recall = (double) fenzi / (double) fenmu_R;
		precision = (double) fenzi / (double) fenmu_F;
		f_score = 2 * (recall * precision) / (recall + precision);

		rpf.add(recall);
		rpf.add(precision);
		rpf.add(f_score);

		// System.out.println(rpf);

		return rpf;
	}
}
