package com.comparision.traids;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.fileoperations.methods.FileReaders;
import com.pulicoperations.methods.AdjacencyTables;

public class Traids
{
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public ArrayList<Integer> c;
	public ArrayList<Integer> s;

	public Traids(FileReaders fr)
	{
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
		this.c = new ArrayList<Integer>();
		this.s = new ArrayList<Integer>();
	}

//	public int getTin(ArrayList<Integer> ctemp)
//	{
//		int tin = 0;
//
//	}

	/* get init core to start the algorithm */
	public void initCore()
	{
		for (int i = 0; i < this.s.size(); i++)
		{
			Integer nodei = s.get(i);
			for (int j = i + 1; j < this.s.size(); j++)
			{
				Integer nodej = s.get(j);
				if (this.adjacencyMatrix[nodei][nodej] != 0)
				{
					if (!this.c.contains(nodei))
					{
						this.c.add(nodei);
					}
					if (!this.c.contains(nodej))
					{
						this.c.add(nodej);
					}
				}
			}
		}

		System.out.println("Set C:");
		for (Iterator<Integer> iter = this.c.iterator(); iter.hasNext();)
		{
			System.out.print(iter.next() + " ");
		}
		System.out.println();
	}

	public void runTraids(int id)
	{
		// init C,S(tested)
		this.c.add(id);
		for (Iterator<Integer> iter = this.adjacencyTable.get(id).iterator(); iter
				.hasNext();)
		{
			Integer neighbor = iter.next();
			this.s.add(neighbor);
			this.s.remove((Integer) id);
		}

		System.out.println("Set C:");
		for (Iterator<Integer> iter = this.c.iterator(); iter.hasNext();)
		{
			System.out.print(iter.next() + " ");
		}
		System.out.println();
		System.out.println("Set S:");
		for (Iterator<Integer> iter = this.s.iterator(); iter.hasNext();)
		{
			System.out.print(iter.next() + " ");
		}
		System.out.println();
	}

	public static void main(String[] args)
	{
		String filePath = "F:/1papers/20190411 first/data set/dolphin/dolphin_community.txt";// �µĲ�������,��ʵ���ݼ�/graph,graph2,graph3,karate,krebs'
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

		Traids t = new Traids(fr);

		t.runTraids(11);
		t.initCore();
	}
}
