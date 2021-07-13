package com.comparision.methods;

import java.util.*;

public class NodeDegree implements Comparator<NodeDegree>
{
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public TreeSet<Integer> getNeighbors() { return neighbors; }

	public void setNeighbors(TreeSet<Integer> neighbors) { this.neighbors = neighbors; }

	public int id;
	public int degree;
	public TreeSet<Integer> neighbors = new TreeSet<>();

	public NodeDegree()
	{

	}

	public NodeDegree(int id, int degree)
	{
		this.id = id;
		this.degree = degree;
	}

	public NodeDegree(int id, int degree, TreeSet<Integer> neighbors)
	{
		this.id = id;
		this.degree = degree;
		this.neighbors = neighbors;
	}

	@Override
	public String toString()
	{
		return "NodeDegree [id=" + id + ", degree=" + degree + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + degree;
		result = prime * result + id;
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
		NodeDegree other = (NodeDegree) obj;
		if (degree != other.degree)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compare(NodeDegree o1, NodeDegree o2)
	{
		// TODO Auto-generated method stub
		NodeDegree node_1 = o1;
		NodeDegree node_2 = o2;
		if (node_1.degree < node_2.degree)
		{
			return -1;
		} else if (node_1.degree == node_2.degree)
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
		} else if (node_1.degree > node_2.degree)
		{
			return 1;
		}
		return 0;
	}

	public TreeSet<NodeDegree> getNodeDegrees(ArrayList<ArrayList<Integer>> adjacencyTable) {
		int i = 0;
		TreeSet<NodeDegree> nodeDegrees = new TreeSet<NodeDegree>(new NodeDegree());
		for(Iterator<ArrayList<Integer>> iter_adjacencyTable = adjacencyTable.iterator(); iter_adjacencyTable.hasNext();) {
			ArrayList<Integer> temp = new ArrayList<>();
			temp.addAll(iter_adjacencyTable.next());
			NodeDegree nd = new NodeDegree();
			nd.id = i;
			nd.degree = temp.size() - 1;
			temp.remove((Integer)nd.id);
			nd.neighbors.addAll(temp);
			nodeDegrees.add(nd);
			i++;
		}
//		Collections.sort(nodeDegrees,new NodeDegree());
		return nodeDegrees;
	}

	public NodeDegree getNodeDegreeById(int id, TreeSet<NodeDegree> nodeDegrees) {
		TreeSet<NodeDegree> nds = new TreeSet<>(new NodeDegree());
		nds.addAll(nodeDegrees);
		NodeDegree nodeDegree = new NodeDegree();
		for(NodeDegree nd : nds) {
			if(id == nd.getId()) {
				nodeDegree = nd;
				break;
			}
		}
		return nodeDegree;
	}

	public static void main(String[] args)
	{
		TreeSet<NodeDegree> nodes = new TreeSet<NodeDegree>(new NodeDegree());
		NodeDegree n1= new NodeDegree(2, 3);
		NodeDegree n2= new NodeDegree(1, 2);
		NodeDegree n3= new NodeDegree(4, 5);
		NodeDegree n4= new NodeDegree(3, 1);
		nodes.add(n4);
		nodes.add(n3);
		nodes.add(n2);
		nodes.add(n1);
		for(Iterator<NodeDegree> iter = nodes.iterator();iter.hasNext();)
		{
			System.out.println(iter.next());
		}
	}
}
