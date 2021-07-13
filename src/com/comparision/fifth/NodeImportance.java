package com.comparision.fifth;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class NodeImportance implements Comparator<NodeImportance>
{
	public int id;
	public int importance;

	public NodeImportance()
	{

	}

	public NodeImportance(int id, int value)
	{
		this.id = id;
		this.importance = value;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + importance;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeImportance other = (NodeImportance) obj;
		if (id != other.id)
			return false;
		if (importance != other.importance)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "NodeImportance [id=" + id + ", importance=" + importance
				+ "]";
	}

	@Override
	public int compare(NodeImportance o1, NodeImportance o2)
	{
		// TODO Auto-generated method stub
		NodeImportance node_1 = o1;
		NodeImportance node_2 = o2;
		if (node_1.importance < node_2.importance)
		{
			return 1;

		} else if (node_1.importance == node_2.importance)
		{
			if (node_1.id > node_2.id)
			{
				return 1;

			} else if (node_1.id == node_2.id)
			{
				return 0;
			} else if (node_1.id < node_2.id)
			{
				return -1;
			}
		} else if (node_1.importance > node_2.importance)
		{
			return -1;
		}
		return 0;
	}

	public static void main(String[] args)
	{
		TreeSet<NodeImportance> nodes = new TreeSet<NodeImportance>(
				new NodeImportance());
		NodeImportance n1 = new NodeImportance(1, 1);
		NodeImportance n2 = new NodeImportance(2, 4);
		NodeImportance n3 = new NodeImportance(3, 2);
		NodeImportance n4 = new NodeImportance(4, 1);
		NodeImportance n5 = new NodeImportance(1, 1);
		nodes.add(n5);
		nodes.add(n4);
		nodes.add(n3);
		nodes.add(n2);
		nodes.add(n1);
		for (Iterator<NodeImportance> iter = nodes.iterator(); iter
				.hasNext();)
		{
			System.out.println(iter.next());
		}
	}


	/* (Tested) Calculate node importance */
	public int calculate_node_importance(int node_id, ArrayList<ArrayList<Integer>> adjacencyTables)
	{
		TreeSet<Integer> neighbors_tree = getNeighbors(node_id, adjacencyTables);
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		neighbors.addAll(neighbors_tree);
		int links_between_neighbors = 0;
		int all_links = 0;
		for (int i = 0; i < neighbors.size(); i++)
		{
			int neighbor_1 = neighbors.get(i);
			for (int j = i + 1; j < neighbors.size(); j++)
			{
				int neighbor_2 = neighbors.get(j);
				if (adjacencyTables.get(neighbor_1).contains(neighbor_2))
				{
					links_between_neighbors++;
				}
			}
		}
		all_links = links_between_neighbors + neighbors.size();
		// System.out.println(all_links);
		return all_links;
	}

	/* (Tested) calculate_node_importance_for_all_nodes */
	public ArrayList<NodeImportance> calculate_node_importance_for_all_nodes(ArrayList<ArrayList<Integer>> adjacencyTables)
	{
		ArrayList<NodeImportance> nodes_importance = new ArrayList<NodeImportance>();
		for (int i = 0; i < adjacencyTables.size(); i++)
		{
			NodeImportance node_importance = new NodeImportance(i,
					calculate_node_importance(i, adjacencyTables));
			nodes_importance.add(node_importance);
		}
		return nodes_importance;
	}

	public TreeSet<Integer> getNeighbors(int id, ArrayList<ArrayList<Integer>> adjacencyTables) {
		TreeSet<Integer> neighbours = new TreeSet<>();
		neighbours.addAll(adjacencyTables.get(id));
		neighbours.remove((Integer)id);
		return neighbours;
	}
}
