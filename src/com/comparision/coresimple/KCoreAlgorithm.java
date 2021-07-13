package com.comparision.coresimple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.fileoperations.methods.FileReaders;
import com.pulicoperations.methods.AdjacencyTables;

public class KCoreAlgorithm
{
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public ArrayList<ArrayList<Integer>> adjacencyTableTemp;
	public KCoreNode[] allCoreNodes;

	public KCoreAlgorithm(FileReaders fr)
	{
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
		this.adjacencyTableTemp = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
		this.allCoreNodes = new KCoreNode[this.size];
	}

	public ArrayList<Integer> initKCoreNodes(
			ArrayList<ArrayList<Integer>> adjacencyTableTemp, int k)
	{
		ArrayList<Integer> kCoreNodes = new ArrayList<Integer>();

		for (Iterator<ArrayList<Integer>> iter = adjacencyTableTemp.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> line = iter.next();
			if (line.size() - 1 == k)
			{
				kCoreNodes.add(line.get(0));
			}
		}

		return kCoreNodes;
	}

	public ArrayList<Integer> getAffectedNodes(
			ArrayList<ArrayList<Integer>> adjacencyTableTemp, int removedNode)
	{
		ArrayList<Integer> affectedNodes = new ArrayList<Integer>();

		for (Iterator<ArrayList<Integer>> iter = adjacencyTableTemp.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> line = iter.next();

			if (line.get(0) == removedNode)
			{

				ArrayList<Integer> lineTemp = new ArrayList<Integer>();

				lineTemp.addAll(line);

				lineTemp.remove(0);

				affectedNodes.addAll(lineTemp);

				return affectedNodes;
			}
		}

		return affectedNodes;
	}

	public ArrayList<ArrayList<Integer>> reflashAdjacencyTable(
			ArrayList<ArrayList<Integer>> adjacencyTableTemp,
			ArrayList<Integer> affectedNodes, int removedNode)
	{
		for (Iterator<ArrayList<Integer>> iter = adjacencyTableTemp.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> line = iter.next();

			if (line.get(0) == removedNode)
			{
				adjacencyTableTemp.remove(line);

				break;
			}
		}

		ArrayList<ArrayList<Integer>> affectedLines = new ArrayList<ArrayList<Integer>>();

		for (Iterator<Integer> iter = affectedNodes.iterator(); iter.hasNext();)
		{
			Integer affectedNode = iter.next();

			for (Iterator<ArrayList<Integer>> iter2 = adjacencyTableTemp
					.iterator(); iter2.hasNext();)
			{
				ArrayList<Integer> line = iter2.next();

				if (line.get(0) == affectedNode)
				{
					for (Iterator<Integer> iter3 = line.iterator(); iter3
							.hasNext();)
					{
						Integer member = iter3.next();

						if (member == removedNode)
						{
							line.remove(member);

							affectedLines.add(line);

							break;
						}
					}
				}
			}
		}

		return affectedLines;
	}

	public ArrayList<Integer> getNewKCoreNodes(
			ArrayList<ArrayList<Integer>> affectedLines, int k)
	{
		ArrayList<Integer> newKCoreNodes = new ArrayList<Integer>();

		for (Iterator<ArrayList<Integer>> iter = affectedLines.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> line = iter.next();

			if (line.size() - 1 <= k)
			{
				newKCoreNodes.add(line.get(0));
			}
		}

		return newKCoreNodes;
	}

	public KCoreNode[] kCoreAlgorithm()
	{
		ArrayList<KLevelNodes> kCoreNodeOfAllLevels = new ArrayList<KLevelNodes>();

		ArrayList<Integer> allKCoreNodes = new ArrayList<Integer>();

		int k = 0;

		HashSet<Integer> kCoreNodes = new HashSet<Integer>(initKCoreNodes(
				adjacencyTableTemp, k));

		int count = 0;

		while (count != this.size)
		{
			while (kCoreNodes.size() != 0)
			{
				while (kCoreNodes.size() != 0)
				{
					Integer removedNode = kCoreNodes.iterator().next();

					count++;

					allKCoreNodes.add(removedNode);

					kCoreNodes.remove(removedNode);

					ArrayList<Integer> affectedNodes = getAffectedNodes(
							adjacencyTableTemp, removedNode);

					ArrayList<ArrayList<Integer>> affectedLines = reflashAdjacencyTable(
							adjacencyTableTemp, affectedNodes, removedNode);

					ArrayList<Integer> newKCoreNodes = getNewKCoreNodes(
							affectedLines, k);

					if (newKCoreNodes.size() != 0)
					{
						kCoreNodes.addAll(newKCoreNodes);
					}
				}

				if (allKCoreNodes.size() != 0)
				{
					KLevelNodes kln = new KLevelNodes(k, allKCoreNodes);

					kCoreNodeOfAllLevels.add(kln);

					k++;

					allKCoreNodes = new ArrayList<Integer>();

					kCoreNodes.clear();

					kCoreNodes.addAll(initKCoreNodes(adjacencyTableTemp, k));
				} else
				{
					break;
				}
			}

			k++;

			allKCoreNodes = new ArrayList<Integer>();

			kCoreNodes.clear();

			kCoreNodes.addAll(initKCoreNodes(adjacencyTableTemp, k));
		}

		// System.out.println("K-Core Nodes Of All Levels:");
		// for (Iterator<KLevelNodes> iter = kCoreNodeOfAllLevels.iterator();
		// iter
		// .hasNext();)
		// {
		// KLevelNodes kln = iter.next();
		//
		// System.out.println(kln.k + "-core nodes");
		// for (Iterator<Integer> iter2 = kln.nodes.iterator(); iter2
		// .hasNext();)
		// {
		// System.out.print(iter2.next() + ",");
		// }
		// System.out.println();
		// }
		// System.out.println();

		/* put all k-core nodes into a array */
		for (Iterator<KLevelNodes> iter = kCoreNodeOfAllLevels.iterator(); iter
				.hasNext();)
		{
			KLevelNodes kln = iter.next();

			int level = kln.k;

			for (Iterator<Integer> iter2 = kln.nodes.iterator(); iter2
					.hasNext();)
			{
				Integer node = iter2.next();

				KCoreNode kcn = new KCoreNode(node, level);

				this.allCoreNodes[node] = kcn;
			}
		}

		for (int i = 0; i < size; i++)
		{
			KCoreNode kcn = this.allCoreNodes[i];

			int value = 0;

			ArrayList<Integer> neighbors = this.adjacencyTable.get(i);

			for (Iterator<Integer> iter = neighbors.iterator(); iter.hasNext();)
			{
				Integer neighbor = iter.next();

				value += this.allCoreNodes[neighbor].level;//this.allCoreNodes[neighbor].level;// 1;
				/****************************************************************************************************************/
			}

			kcn.value = value;
		}
		
		// for (int i = 0; i < size; i++)
		// {
		// KCoreNode kcn = this.allCoreNodes[i];
		//
		// int value = 0;
		//
		// ArrayList<Integer> neighbors = this.adjacencyTable.get(i);
		//
		// for(int m=1;m<neighbors.size();m++)
		// {
		// for(int n=m+1;n<neighbors.size();n++)
		// {
		// if(this.adjacencyMatrix[m][n]!=0)
		// {
		// value++;
		// }
		// }
		// }
		//
		// kcn.value = value;
		// }

		// System.out.println("allCoreNodes:");
		// for (int i = 0; i < size; i++)
		// {
		// KCoreNode kcn = this.allCoreNodes[i];
		//
		// System.out.println("("+kcn.id+","+kcn.level+","+kcn.value+")");
		// }
		// System.out.println();

		return this.allCoreNodes;
	}

	public static void main(String[] args)
	{
		String filePath = "D:/新的测试数据/graph2.txt";// 新的测试数据,真实数据集/graph,graph2,graph3,karate,krebs'
		// book.txt,

		FileReaders fr = new FileReaders(filePath);

		try
		{
			fr.readFile(false, false);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		KCoreAlgorithm kca = new KCoreAlgorithm(fr);

		kca.kCoreAlgorithm();
	}
}
