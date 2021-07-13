package com.comparision.degree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.fileoperations.methods.FileReaders;
import com.pulicoperations.methods.AdjacencyTables;
import com.pulicoperations.methods.Partitions;

public class DegreeCentrality
{
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public DegreeNode[] allNodesDegree;

	public DegreeCentrality(FileReaders fr)
	{
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
	}

	public void getAllNodesDegree()
	{
		this.allNodesDegree = new DegreeNode[this.size];
		for (int i = 0; i < this.adjacencyTable.size(); i++)
		{
			int id = i;
			int value = this.adjacencyTable.get(i).size() - 1;
			this.allNodesDegree[i] = new DegreeNode(id, value);
		}

		// System.out.println("AllNodesDegree:");
		// for (int i = 0; i < this.size; i++)
		// {
		// System.out.println(this.allNodesDegree[i].id + ","
		// + this.allNodesDegree[i].value);
		// }
	}

	public double getDensity(ArrayList<Integer> innerMembers)
	{
		double density = 0;
		if (innerMembers.size() == 1)
		{
			density = 0;
			return density;
		} else
		{
			int nodes = 0;
			nodes = innerMembers.size();
			int links = 0;
			for (int i = 0; i < innerMembers.size(); i++)
			{
				for (int j = i + 1; j < innerMembers.size(); j++)
				{
					if (this.adjacencyMatrix[innerMembers.get(i)][innerMembers
							.get(j)] != 0)
					{
						links++;
					}
				}
			}
			density = (double) nodes / (double) links;
			return density;
		}
	}

	public boolean isLowerThanAll(ArrayList<Integer> innerMembers,
			DegreeNode neighbor)
	{
		if (innerMembers.size() == 0)
		{
			return true;
		}
		int neighbor_value = neighbor.value;
		// System.out.println("neighbor_value:" + neighbor.id + " "
		// + neighbor_value);
		for (Iterator<Integer> iter = this.adjacencyTable.get(neighbor.id)
				.iterator(); iter.hasNext();)
		{
			DegreeNode member = this.allNodesDegree[iter.next()];
			// System.out
			// .println("member_value:" + member.id + " " + member.value);
			if (innerMembers.contains(member.id)
					&& member.value <= neighbor_value)
			{
				return false;
			}
		}
		return true;
	}

	public ArrayList<ArrayList<Integer>> runDC(int id)
	{
		getAllNodesDegree();

		ArrayList<Integer> innerMembers = new ArrayList<Integer>();

		TreeSet<DegreeNode> decreaseOrderQueue = new TreeSet<DegreeNode>(
				new DegreeNode());
		TreeSet<DegreeNode> decreaseOrderQueue_nextLevel = new TreeSet<DegreeNode>(
				new DegreeNode());
		decreaseOrderQueue.add(this.allNodesDegree[id]);
		while (decreaseOrderQueue.size() != 0)
		// && decreaseOrderQueue_nextLevel.size() != 0)
		{
			TreeSet<DegreeNode> decreaseOrderQueue_nextLevel_nextNode = new TreeSet<DegreeNode>(
					new DegreeNode());
			DegreeNode first = decreaseOrderQueue.first();
			int value_first = first.value;
			for (Iterator<Integer> iter = this.adjacencyTable.get(first.id)
					.iterator(); iter.hasNext();)
			{
				// Integer neighbor = iter.next();
				DegreeNode neighbor = this.allNodesDegree[iter.next()];
				if (neighbor.value < value_first
						&& !decreaseOrderQueue.contains(neighbor)
						&& !innerMembers.contains(neighbor.id))
				{
					// decreaseOrderQueue_nextLevel.add(neighbor);
					decreaseOrderQueue_nextLevel_nextNode.add(neighbor);
				}
			}
			if (isLowerThanAll(innerMembers, first))
			{
				innerMembers.add(first.id);
				decreaseOrderQueue_nextLevel
						.addAll(decreaseOrderQueue_nextLevel_nextNode);
				// System.out.println(innerMembers);
			}

			decreaseOrderQueue.remove(first);

			if (decreaseOrderQueue.size() == 0)
			{
				if (decreaseOrderQueue_nextLevel.size() != 0)
				{
					decreaseOrderQueue.addAll(decreaseOrderQueue_nextLevel);
					decreaseOrderQueue_nextLevel.clear();
				}
			}
		}

		// ArrayList<Integer> queue = new ArrayList<Integer>();
		// queue.add(id);
		// while (queue.size() != 0)
		// {
		// Integer first = queue.get(0);
		// int value_first = this.allNodesDegree[first].value;
		// for (Iterator<Integer> iter = this.adjacencyTable.get(first)
		// .iterator(); iter.hasNext();)
		// {
		// Integer neighbor = iter.next();
		// int value_neighbor = this.allNodesDegree[neighbor].value;
		// if (value_neighbor < value_first && !queue.contains(neighbor)
		// && !innerMembers.contains(neighbor))
		// {
		// queue.add(neighbor);
		// }
		// }
		// innerMembers.add(first);
		// queue.remove(first);
		// // if(innerMembers.size()==0)
		// // {
		// // innerMembers.add(first);
		// // queue.remove(first);
		// // }else{
		// // ArrayList<Integer> innerMembers_temp = new ArrayList<Integer>();
		// // innerMembers_temp.addAll(innerMembers);
		// // innerMembers_temp.add(first);
		// // double old_density = getDensity(innerMembers);
		// // double new_density = getDensity(innerMembers_temp);
		// // System.out.println(old_density+","+new_density);
		// // if(new_density>old_density)
		// // {
		// // innerMembers.add(first);
		// // }
		// // queue.remove(first);
		// // }
		// }
		// System.out.println(innerMembers);
		// getDensity(innerMembers);

		ArrayList<Integer> may_members = new ArrayList<Integer>();

		for (Iterator<Integer> iter = this.adjacencyTable.get(id).iterator(); iter
				.hasNext();)
		{
			Integer neighbor = iter.next();
			if (!innerMembers.contains(neighbor))
			{
				innerMembers.add(neighbor);
				may_members.add(neighbor);
			}
		}
//		 System.out.println(may_members);
//		 System.out.println(innerMembers);

		// System.out.println("degree_maymembers:"+may_members);

		ArrayList<Integer> outerMembers = Partitions.getOuterMembers(this.size,
				innerMembers);

		ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();

		partitionF.add(innerMembers);
		partitionF.add(outerMembers);

		// System.out.println("partitionF:");
		// for (Iterator<ArrayList<Integer>> iter = partitionF.iterator(); iter
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

		return partitionF;
	}

	public static void main(String[] args)
	{
		String filePath = "D:/真实数据集/karate.txt";// 新的测试数据,真实数据集/graph,graph2,graph3,karate,krebs'
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

		DegreeCentrality dc = new DegreeCentrality(fr);

		dc.runDC(0);
	}
}
