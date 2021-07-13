package com.pulicoperations.methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import com.fileoperations.methods.FileReaders;

public class Modularity
{
	public static double modularityFromFile(
			ArrayList<ArrayList<Integer>> components, String sourcePath)
			throws IOException
	{
		FileReaders fr = new FileReaders(sourcePath);

		fr.readFile(false, false);

		int size = fr.getSize();

		int[][] adjacencyMatrix = new int[size][size];

		adjacencyMatrix = fr.getAdjacencyMatrix();

		System.out.println("AdjacencyMatrix:");
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				System.out.print(adjacencyMatrix[i][j] + " ");
			}
			System.out.println();
		}

		double modularity = 0.0;

		int allLinksNumber = 0;

		for (int i = 0; i < size; i++)
		{
			for (int j = i + 1; j < size; j++)
			{
				if (adjacencyMatrix[i][j] != 0)
				{
					allLinksNumber++;
				}
			}
		}

		for (Iterator<ArrayList<Integer>> communities = components.iterator(); communities
				.hasNext();)
		{
			ArrayList<Integer> community = communities.next();

			int linksInCommunity = 0;

			for (int i = 0; i < community.size(); i++)
			{
				int no1 = community.get(i);

				for (int j = i + 1; j < community.size(); j++)
				{
					int no2 = community.get(j);

					if (adjacencyMatrix[no1][no2] != 0)
					{
						linksInCommunity++;
					}
				}
			}

			System.out.println("I:" + linksInCommunity);

			int allDegreesInCommunity = 0;

			for (Iterator<Integer> members = community.iterator(); members
					.hasNext();)
			{
				int member = members.next();

				for (int i = 0; i < size; i++)
				{
					if (adjacencyMatrix[member][i] != 0)
					{
						allDegreesInCommunity++;
					}
				}
			}

			System.out.println("2I+O:" + allDegreesInCommunity);

			modularity += (((double) linksInCommunity / allLinksNumber) - Math
					.pow(((double) allDegreesInCommunity / (allLinksNumber * 2)),
							2));
		}

		System.out.println("Modularity:" + modularity);

		return modularity;
	}

	public static double modularityFromAdjacencyMatrix(
			ArrayList<ArrayList<Integer>> components, int size,
			int[][] adjacencyMatrix)
	{
		// System.out.println("AdjacencyMatrix:");
		// for (int i = 0; i < size; i++)
		// {
		// for (int j = 0; j < size; j++)
		// {
		// System.out.print(adjacencyMatrix[i][j] + " ");
		// }
		// System.out.println();
		// }

		double modularity = 0.0;

		int allLinksNumber = 0;

		for (int i = 0; i < size; i++)
		{
			for (int j = i + 1; j < size; j++)
			{
				if (adjacencyMatrix[i][j] != 0)
				{
					allLinksNumber++;
				}
			}
		}

		for (Iterator<ArrayList<Integer>> communities = components.iterator(); communities
				.hasNext();)
		{
			ArrayList<Integer> community = communities.next();

			int linksInCommunity = 0;

			for (int i = 0; i < community.size(); i++)
			{
				int no1 = community.get(i);

				for (int j = i + 1; j < community.size(); j++)
				{
					int no2 = community.get(j);

					if (adjacencyMatrix[no1][no2] != 0)
					{
						linksInCommunity++;
					}
				}
			}

			// System.out.println("I:" + linksInCommunity);

			int allDegreesInCommunity = 0;

			for (Iterator<Integer> members = community.iterator(); members
					.hasNext();)
			{
				int member = members.next();

				for (int i = 0; i < size; i++)
				{
					if (adjacencyMatrix[member][i] != 0)
					{
						allDegreesInCommunity++;
					}
				}
			}

			// System.out.println("2I+O:" + allDegreesInCommunity);

			modularity += (((double) linksInCommunity / allLinksNumber) - Math
					.pow(((double) allDegreesInCommunity / (allLinksNumber * 2)),
							2));
		}

		// System.out.println("Modularity:" + modularity);

		return modularity;
	}

	public static void main(String[] args) throws IOException
	{
		String sourcePath = "D:/新的测试数据/modularity.txt";// 悲惨世界,空手道,tree,graph,非连通图,modularity

		ArrayList<ArrayList<Integer>> components = new ArrayList<ArrayList<Integer>>();

		ArrayList<Integer> component1 = new ArrayList<Integer>();

		component1.add(0);
		component1.add(1);
		component1.add(2);
		component1.add(3);

		ArrayList<Integer> component2 = new ArrayList<Integer>();

		component2.add(4);
		component2.add(5);
		component2.add(6);

		ArrayList<Integer> component3 = new ArrayList<Integer>();

		component3.add(7);
		component3.add(8);
		component3.add(9);

		components.add(component1);
		components.add(component2);
		components.add(component3);

		// Meatures.modularityFromFile(components, sourcePath);

		FileReaders fr = new FileReaders(sourcePath);

		fr.readFile(false, false);

		int size = fr.getSize();

		int[][] adjacencyMatrix = new int[size][size];

		adjacencyMatrix = fr.getAdjacencyMatrix();

		Modularity.modularityFromAdjacencyMatrix(components, size,
				adjacencyMatrix);
	}
}
