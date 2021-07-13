package com.comparision.lmd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.fileoperations.methods.FileReaders;
import com.pulicoperations.methods.AdjacencyTables;

public class LMD
{
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;

	public LMD(FileReaders fr)
	{
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
	}

	public ArrayList<NodeDegree> getAllNodesDegree()
	{
		ArrayList<NodeDegree> allNodesDegree = new ArrayList<NodeDegree>();

		for (Iterator<ArrayList<Integer>> iter = this.adjacencyTable.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> line = iter.next();

			NodeDegree nd = new NodeDegree(line.get(0), line.size() - 1);

			allNodesDegree.add(nd);
		}

		for (Iterator<NodeDegree> iter = allNodesDegree.iterator(); iter
				.hasNext();)
		{
			NodeDegree nd = iter.next();

			System.out.println(nd.id + "," + nd.degree);
		}

		return allNodesDegree;
	}

	public ArrayList<Integer> getAllMaxDegreeNodes()
	{
		ArrayList<Integer> allMaxDegreeNodes =  new ArrayList<Integer>();
		
		ArrayList<NodeDegree> allNodesDegree = getAllNodesDegree();

		for (Iterator<NodeDegree> iter = allNodesDegree.iterator(); iter
				.hasNext();)
		{
			NodeDegree nd = iter.next();

			boolean flag = true;

			for (Iterator<Integer> iter2 = this.adjacencyTable.get(nd.id)
					.iterator(); iter2.hasNext();)
			{
				Integer neighbor = iter2.next();

				if (this.adjacencyMatrix[nd.id][neighbor] != 0)
				{
					if (nd.degree < allNodesDegree.get(neighbor).degree)
					{
						flag = false;

						break;
					}
				}
			}
			
			if(flag==true)
			{
				allMaxDegreeNodes.add(nd.id);
			}
		}
		
		System.out.println(allMaxDegreeNodes);
		
		return allMaxDegreeNodes;
	}
	
	public ArrayList<Integer> getAssociateMaxDegreeNodes(int id)
	{
		ArrayList<Integer> associateMaxDegreeNodes = new ArrayList<Integer>();
		
		ArrayList<Integer> queue = new ArrayList<Integer>();
		
		queue.add(id);
		
		while(!queue.isEmpty())
		{
			
		}
		
		return  associateMaxDegreeNodes;
	}

	public static void main(String[] args)
	{
		String filePath = "D:/新的测试数据/graph2.txt";// 新的测试数据,真实数据集/graph,graph2,graph3,karate,
		// krebs' book.txt,

		FileReaders fr = new FileReaders(filePath);

		try
		{
			fr.readFile(false, false);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LMD lmd = new LMD(fr);

		lmd.getAllMaxDegreeNodes();
	}
}
