package com.pulicoperations.methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.fileoperations.methods.FileReaders;
import com.publicstructures.methods.CommunityStructure;

public class Partitions
{
	public TreeSet<CommunityStructure> tcs;

	public Partitions(ArrayList<ArrayList<Integer>> components)
	{
		tcs = CommunityStructure.getCommunityStructureUnoverloop(components);
	}

	/* get members out of the found community */
	public static ArrayList<Integer> getOuterMembers(int size,
			ArrayList<Integer> innerMembers)
	{
		ArrayList<Integer> outerMembers = new ArrayList<Integer>();

		for (int i = 0; i < size; i++)
		{
			if (!innerMembers.contains((Integer) i))
			{
				outerMembers.add(i);
			}
		}

		return outerMembers;
	}

	/* get the real partation of a specific node */
	public static ArrayList<ArrayList<Integer>> getPartitionROfSpecificNode(
			int id, ArrayList<ArrayList<Integer>> realCommunities, int size)
	{
		ArrayList<ArrayList<Integer>> partitionR = new ArrayList<ArrayList<Integer>>();

		ArrayList<Integer> innerMembers = new ArrayList<Integer>();

		for (Iterator<ArrayList<Integer>> iter = realCommunities.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> community = iter.next();

			if (community.contains((Integer) id))
			{
				innerMembers = community;

				break;
			}
		}

		ArrayList<Integer> outerMembers = Partitions.getOuterMembers(size,
				innerMembers);

		partitionR.add(innerMembers);
		partitionR.add(outerMembers);

		// System.out.println("partitionR:");
		// for (Iterator<ArrayList<Integer>> iter = partitionR.iterator(); iter
		// .hasNext();)
		// {
		// ArrayList<Integer> partition = iter.next();
		// for (Iterator<Integer> iter2 = partition.iterator(); iter2
		// .hasNext();)
		// {
		// Integer member = iter2.next();
		// System.out.print(member + " ");
		// }
		// System.out.println();
		// }

		return partitionR;
	}

	/* get the real partation of a specific node */
	public static ArrayList<ArrayList<ArrayList<Integer>>> getPartitionRsOfSpecificNode(
			int id, ArrayList<ArrayList<Integer>> realCommunities, int size)
	{
		ArrayList<ArrayList<ArrayList<Integer>>> partitionRs = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for (Iterator<ArrayList<Integer>> iter = realCommunities.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> community = iter.next();
			if (community.contains((Integer) id))
			{
				ArrayList<ArrayList<Integer>> partitionR = new ArrayList<ArrayList<Integer>>();
				ArrayList<Integer> innerMembers = new ArrayList<Integer>();
				innerMembers = community;
				ArrayList<Integer> outerMembers = Partitions.getOuterMembers(
						size, innerMembers);
				partitionR.add(innerMembers);
				partitionR.add(outerMembers);
				partitionRs.add(partitionR);
			}
		}

		// System.out.println("partitionR:");
		// for (Iterator<ArrayList<Integer>> iter = partitionR.iterator(); iter
		// .hasNext();)
		// {
		// ArrayList<Integer> partition = iter.next();
		// for (Iterator<Integer> iter2 = partition.iterator(); iter2
		// .hasNext();)
		// {
		// Integer member = iter2.next();
		// System.out.print(member + " ");
		// }
		// System.out.println();
		// }

		return partitionRs;
	}

	public static void main(String[] args) throws IOException
	{
		String filePath = "D:/新的测试数据/足球.txt";
		// String filePath = "D:/新的测试数据/海豚网.txt";
		// String filePath = "D:/新的测试数据/悲惨世界.txt";
		// String filePath = "D:/新的测试数据/合作.txt";
		// String filePath = "D:/second(测试)/2.txt";

		FileReaders fr = new FileReaders(filePath);

		try
		{
			fr.readFile(false, false);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// FastPartition fp = new FastPartition(fr);
		//
		// fp.rageLord();
		//
		// Partitions p= new Partitions(fp.getCommunities());
		//
		// System.out.println("Partitions");
		// for (Iterator<CommunityStructure> iter = p.tcs.iterator(); iter
		// .hasNext();)
		// {
		//
		// CommunityStructure member = iter.next();
		//
		// System.out.println(member.no+" "+member.cno);
		// }
	}
}
