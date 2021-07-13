package com.pulicoperations.methods;

import java.util.ArrayList;

public class AdjacencyTables
{
	/*
	 * To change the adjacency matrix of the social network into a adjacency
	 * table
	 */
	public static ArrayList<ArrayList<Integer>> getAdjacencyTable(int size,
			int[][] adjacencyMatrix)
	{
		ArrayList<ArrayList<Integer>> adjacencyTable =new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < size; i++)
		{
			ArrayList<Integer> neighbors = new ArrayList<Integer>();

			neighbors.add(i);

			for (int j = 0; j < size; j++)
			{
				if (adjacencyMatrix[i][j] != 0)
				{
					neighbors.add(j);
				}
			}

			adjacencyTable.add(neighbors);
		}
		
		return adjacencyTable;
	}
}
