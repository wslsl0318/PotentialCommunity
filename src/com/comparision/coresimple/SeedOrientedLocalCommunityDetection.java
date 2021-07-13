package com.comparision.coresimple;

import java.util.ArrayList;

import com.fileoperations.methods.FileReaders;
import com.pulicoperations.methods.AdjacencyTables;

public class SeedOrientedLocalCommunityDetection
{
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public KCoreNode[] allCoreNodes;
	public ArrayList<Integer> communityMembers;
	
	public SeedOrientedLocalCommunityDetection(FileReaders fr)
	{
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
		KCoreAlgorithm kca = new KCoreAlgorithm(fr);
		this.allCoreNodes = kca.kCoreAlgorithm();
		this.communityMembers = new ArrayList<Integer>();
	}
}
