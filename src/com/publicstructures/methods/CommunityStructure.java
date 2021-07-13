package com.publicstructures.methods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class CommunityStructure implements Comparator<Object>
{
	// To store member's NO.
	public int no;
	// To store member's comunity NO.
	public int cno;

	public CommunityStructure()
	{

	}

	public CommunityStructure(int no, int cno)
	{
		this.no = no;

		this.cno = cno;
	}

	/* To get components from String[] */
	public static ArrayList<ArrayList<Integer>> getComponentsFromString(
			String[] strs)
	{
		ArrayList<ArrayList<Integer>> components = new ArrayList<ArrayList<Integer>>();

		int componentsNumber = 0;

		for (int i = 2; i < strs.length; i++)
		{
			if (Integer.parseInt(strs[i])>componentsNumber)
			{
				componentsNumber=Integer.parseInt(strs[i]);
			}
		}
		
		for(int i=0;i<componentsNumber;i++)
		{
			ArrayList<Integer> component = new ArrayList<Integer>();
			
			components.add(component);
		}

		for (int i = 2; i < strs.length; i++)
		{
			int componentNo = Integer.parseInt(strs[i]);
			
			components.get(componentNo-1).add(i-2);
		}

		return components;
	}

	/* To get community structure from components */
	public static TreeSet<CommunityStructure> getCommunityStructureUnoverloop(
			ArrayList<ArrayList<Integer>> components)
	{
		TreeSet<CommunityStructure> tcs = new TreeSet<CommunityStructure>(
				new CommunityStructure());

		int cno = 0;

		for (Iterator<ArrayList<Integer>> iter = components.iterator(); iter
				.hasNext();)
		{
			cno++;

			ArrayList<Integer> line = iter.next();

			for (Iterator<Integer> iter2 = line.iterator(); iter2.hasNext();)
			{
				CommunityStructure cs = new CommunityStructure(iter2.next(),
						cno);

				tcs.add(cs);
			}
		}

		return tcs;
	}

	/* To get communities from adjacencyMatrix */
	public static ArrayList<ArrayList<Integer>> getComponentsFromAdjacencyMatrix(
			int size, int adjacencyMatrix[][])
	{
		int usedNodes = 0;

		boolean[] arrived = new boolean[size];

		MyQueue queue = new MyQueue();

		ArrayList<ArrayList<Integer>> components = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < size; i++)
		{
			if (arrived[i] == false)
			{
				ArrayList<Integer> component = new ArrayList<Integer>();

				usedNodes++;

				arrived[i] = true;

				component.add(i);

				queue.put(i);

				while (!queue.isEmpty())
				{
					int first = (Integer) queue.get();

					for (int j = 0; j < size; j++)
					{
						if (adjacencyMatrix[first][j] != 0
								&& arrived[j] == false)
						{
							arrived[j] = true;

							usedNodes++;

							component.add(j);

							queue.put(j);
						}
					}
				}

				components.add(component);

				if (usedNodes != size)
				{
					continue;
				} else
				{
					break;
				}
			} else
			{
				continue;
			}
		}

		return components;
	}

	/* To get communities from adjacencyTable */
	public static ArrayList<ArrayList<Integer>> getComponentsFromAdjacencyTable(
			int size, ArrayList<ArrayList<Integer>> adjacencyTable)
	{
		ArrayList<ArrayList<Integer>> components = new ArrayList<ArrayList<Integer>>();

		boolean isNodeUsed[] = new boolean[size];

		for (int i = 0; i < size; i++)
		{
			ArrayList<Integer> line = adjacencyTable.get(i);

			if (isNodeUsed[i] == false)
			{
				ArrayList<Integer> component = new ArrayList<Integer>();

				LinkedList<Integer> queue = new LinkedList<Integer>();

				if (line.size() > 1)
				{
					int firstNo = line.get(0);

					queue.add(Integer.valueOf(firstNo));

					isNodeUsed[firstNo] = true;

					while (!queue.isEmpty())
					{
						int first = queue.remove();

						component.add(Integer.valueOf(first));

						ArrayList<Integer> neighbors = new ArrayList<Integer>(
								adjacencyTable.get(first));

						for (Iterator<Integer> members = neighbors.iterator(); members
								.hasNext();)
						{
							int member = members.next();

							if (member != first && isNodeUsed[member] == false)
							{
								queue.add(Integer.valueOf(member));

								isNodeUsed[member] = true;
							}
						}
					}

					components.add(component);
				} else
				{
					component = line;

					components.add(component);
				}
			} else
			{
				continue;
			}
		}

		// System.out.println("Components:");
		// for (Iterator<ArrayList<Integer>> iter = components.iterator(); iter
		// .hasNext();)
		// {
		// ArrayList<Integer> line = iter.next();
		// for (Iterator<Integer> iter2 = line.iterator(); iter2.hasNext();)
		// {
		// int member = iter2.next();
		// System.out.print(member + " ");
		// }
		// System.out.println();
		// }

		return components;
	}

	@Override
	public int compare(Object o1, Object o2)
	{
		// TODO Auto-generated method stub
		CommunityStructure cs1 = (CommunityStructure) o1;
		CommunityStructure cs2 = (CommunityStructure) o2;

		if (cs1.no < cs2.no)
		{
			return -1;
		} else
		{
			return 1;
		}
	}

	public static void main(String[] args)
	{
		CommunityStructure cs1 = new CommunityStructure(2, 2);
		CommunityStructure cs2 = new CommunityStructure(1, 3);
		CommunityStructure cs3 = new CommunityStructure(4, 1);
		CommunityStructure cs4 = new CommunityStructure(3, 2);

		TreeSet<CommunityStructure> tcs = new TreeSet<CommunityStructure>(
				new CommunityStructure());

		tcs.add(cs1);
		tcs.add(cs2);
		tcs.add(cs3);
		tcs.add(cs4);

		for (Iterator<CommunityStructure> iter = tcs.iterator(); iter.hasNext();)
		{
			CommunityStructure cs = iter.next();

			// int no = cs.no;
			//
			// int cno = cs.cno;
			System.out.println(cs.no + " " + cs.cno);
		}

	}

}
