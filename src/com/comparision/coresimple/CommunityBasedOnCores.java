package com.comparision.coresimple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.comparision.methods.NodeLevel;
import com.fileoperations.methods.FileReaders;
import com.pulicoperations.methods.AdjacencyTables;
import com.pulicoperations.methods.Partitions;

public class CommunityBasedOnCores
{
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public KCoreNode[] allCoreNodes;
	public ArrayList<ArrayList<Affiliation>> affiliationAdjacencyTable;
	public ArrayList<Integer> communityMembers;

	public CommunityBasedOnCores(FileReaders fr)
	{
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
		KCoreAlgorithm kca = new KCoreAlgorithm(fr);
		this.allCoreNodes = kca.kCoreAlgorithm();
		this.affiliationAdjacencyTable = new ArrayList<ArrayList<Affiliation>>();
		this.communityMembers = new ArrayList<Integer>();
	}

	public ArrayList<KCoreNode> getAllNodes()
	{
		ArrayList<KCoreNode> allNodes = new ArrayList<KCoreNode>();
		for (int i = 0; i < this.allCoreNodes.length; i++)
		{
			allNodes.add(this.allCoreNodes[i]);
		}
		return allNodes;
	}

	public ArrayList<Integer> getTopLevelNodes()
	{
		ArrayList<Integer> topLevelNodes = new ArrayList<Integer>();
		TreeSet<KCoreNode> orderedCoreNodes = new TreeSet<KCoreNode>(
				new KCoreNode());
		for (int i = 0; i < this.allCoreNodes.length; i++)
		{
			orderedCoreNodes.add(this.allCoreNodes[i]);
		}
		KCoreNode first = orderedCoreNodes.first();
		for (Iterator<KCoreNode> iter = orderedCoreNodes.iterator(); iter
				.hasNext();)
		{
			KCoreNode member = iter.next();
			if (member.value == first.value)
			{
				topLevelNodes.add(member.id);
			} else
			{
				break;
			}
		}
		return topLevelNodes;
	}

	/*
	 * To compare the importance between node and its neighbors. If neighbor has
	 * a higher importance than Affiliation is true
	 */
	public void setAffiliationAdjacencyTable()
	{
		for (Iterator<ArrayList<Integer>> iter = this.adjacencyTable.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> line = iter.next();
			Integer id2 = line.get(0);
			ArrayList<Affiliation> lineOfAffiliation = new ArrayList<Affiliation>();
			for (Iterator<Integer> iter2 = line.iterator(); iter2.hasNext();)
			{
				Integer id1 = iter2.next();
				if (id1 != id2)
				{
					if (this.allCoreNodes[id2].value > this.allCoreNodes[id1].value
							&& this.allCoreNodes[id2].level > this.allCoreNodes[id1].level)
					{
						Affiliation aff = new Affiliation(id1, id2, true);
						lineOfAffiliation.add(aff);
					} else
					{
						Affiliation aff = new Affiliation(id1, id2, false);
						lineOfAffiliation.add(aff);
					}
				}
			}
			/*************************************************************************************/
			// lineOfAffiliation.get(0).isAffiliation = true;//changed
			/*************************************************************************************/
			this.affiliationAdjacencyTable.add(lineOfAffiliation);
		}
	}

	/*
	 * To judge if a neighbor node has a value and level not higher than the
	 * members already exist in current community. If the value and level are
	 * both not higher than exist members than this node will be a candidate
	 * node
	 */
	public boolean isLowerThanAll(ArrayList<Integer> innerMembers,
			KCoreNode neighbor)
	{
		if (innerMembers.size() == 0)
		{
			return true;
		}
		int neighbor_value = neighbor.value;
		int neighbor_level = neighbor.level;
		for (Iterator<Integer> iter = this.adjacencyTable.get(neighbor.id)
				.iterator(); iter.hasNext();)
		{
			KCoreNode member = this.allCoreNodes[iter.next()];
			/*************************************************************************************/
			if (innerMembers.contains(member.id)
					&& member.value <= neighbor_value)
			// if (innerMembers.contains(member.id)
			// && (member.value < neighbor_value || member.level <
			// neighbor_level))
			{
				return false;
			}
			/*************************************************************************************/
		}
		return true;
	}

	public boolean closed_connected_with_current_community()
	{
		return true;
	}

	public ArrayList<Integer> getCoreBasedCommunity(int id)
	{
		TreeSet<KCoreNode> decreaseOrderQueue = new TreeSet<KCoreNode>(
				new KCoreNode());
		decreaseOrderQueue.add(this.allCoreNodes[id]);
		TreeSet<KCoreNode> decreaseOrderQueue_nextLevel = new TreeSet<KCoreNode>(
				new KCoreNode());
		while (decreaseOrderQueue.size() != 0)
		{
			TreeSet<KCoreNode> decreaseOrderQueue_nextLevel_nextNode = new TreeSet<KCoreNode>(
					new KCoreNode());
			KCoreNode first = decreaseOrderQueue.first();
			ArrayList<Affiliation> line = this.affiliationAdjacencyTable
					.get(first.id);
			for (Iterator<Affiliation> iter = line.iterator(); iter.hasNext();)
			{
				Affiliation member = iter.next();
				if (member.isAffiliation == true
						&& !decreaseOrderQueue
								.contains(this.allCoreNodes[member.id1])
						&& !communityMembers.contains(member.id1))
				{
					decreaseOrderQueue_nextLevel_nextNode
							.add(this.allCoreNodes[member.id1]);
				}
			}
			if (isLowerThanAll(communityMembers, first))
			{
				communityMembers.add(first.id);
				decreaseOrderQueue_nextLevel
						.addAll(decreaseOrderQueue_nextLevel_nextNode);
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
		ArrayList<Integer> may_members = new ArrayList<Integer>();
		for (Iterator<Integer> iter = this.adjacencyTable.get(id).iterator(); iter
				.hasNext();)
		{
			Integer neighbor = iter.next();
			if (!communityMembers.contains(neighbor))
			{
				communityMembers.add(neighbor);
				may_members.add(neighbor);
			}
		}

		TreeSet<Integer> members = new TreeSet<Integer>();
		members.addAll(communityMembers);
		communityMembers.clear();
		communityMembers.addAll(members);

		return communityMembers;
	}

	// public ArrayList<Integer> getCoreBasedCommunity(int id)
	// {
	// /*Initial a queue to keep undecided members*/
	// TreeSet<KCoreNode> decreaseOrderQueue = new TreeSet<KCoreNode>(
	// new KCoreNode());
	// decreaseOrderQueue.add(this.allCoreNodes[id]);
	// while(!decreaseOrderQueue.isEmpty())
	// {
	//
	// }
	// return communityMembers;
	// }

	public int calculate_all_shortest_path_length(
			ArrayList<Integer> local_community, int node)
	{
		int all_shortest_path_length = 0;
		TreeSet<Integer> local_community_temp = new TreeSet<Integer>();
		local_community_temp.addAll(local_community);
		NodeLevel root_node = new NodeLevel(node, 0);
		ArrayList<NodeLevel> queue = new ArrayList<NodeLevel>();
		queue.add(root_node);
		TreeSet<Integer> tested_nodes = new TreeSet<Integer>();
		while (!queue.isEmpty())
		{
			NodeLevel first_node = queue.get(0);
			for (Iterator<Integer> iter_neighbor = this.adjacencyTable.get(
					first_node.node).iterator(); iter_neighbor.hasNext();)
			{
				Integer neighbor = iter_neighbor.next();
				if (neighbor != first_node.node
						&& !tested_nodes.contains(neighbor)
						&& local_community_temp.contains(neighbor))
				{
					NodeLevel node_new_level = new NodeLevel(neighbor,
							first_node.level + 1);
					if (!queue.contains(node_new_level))
					{
						queue.add(node_new_level);
					}
				}
			}
			all_shortest_path_length += first_node.level;
			tested_nodes.add(first_node.node);
			// System.out.println(first_node.node+","+first_node.level);
			queue.remove(0);
		}
		// System.out.println(all_shortest_path_length);
		return all_shortest_path_length;
	}

	public boolean is_in_center(ArrayList<Integer> local_community, int node_id)
	{
		int node_all_shortest_path_length = calculate_all_shortest_path_length(
				local_community, node_id);
		// System.out.println(node_id + "," + node_all_shortest_path_length);
		for (Iterator<Integer> iter_member = local_community.iterator(); iter_member
				.hasNext();)
		{
			Integer member = iter_member.next();
			if (member != node_id)
			{
				int member_all_shortest_path_length = calculate_all_shortest_path_length(
						local_community, member);
				if (member_all_shortest_path_length < node_all_shortest_path_length)
				{
					// System.out.println(member + ","
					// + member_all_shortest_path_length);
					return false;
				}
				// System.out.println(member + ","
				// + member_all_shortest_path_length);
			}
		}
		return true;
	}

	public ArrayList<ArrayList<Integer>> runKCC(int id)
	{
		setAffiliationAdjacencyTable();
		ArrayList<Integer> innerMembers = getCoreBasedCommunity(id);
		ArrayList<Integer> outerMembers = Partitions.getOuterMembers(this.size,
				innerMembers);
		ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
		partitionF.add(innerMembers);
		partitionF.add(outerMembers);

		System.out.println("partitionF:");
		for (Iterator<ArrayList<Integer>> iter = partitionF.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> partition = iter.next();
			for (Iterator<Integer> iter2 = partition.iterator(); iter2
					.hasNext();)
			{
				Integer member = iter2.next();
				System.out.print(member + " ");
			}
			System.out.println();
		}

		// calculate_all_shortest_path_length(partitionF.get(0), id);
		// System.out.println(id+","+is_in_center(partitionF.get(0), id));
		return partitionF;
	}

	public static void main(String[] args)
	{
		String filePath = "D:/真实数据集/karate.txt";// 新的测试数据,真实数据集/graph,graph2,graph3,karate,
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

		CommunityBasedOnCores cbc = new CommunityBasedOnCores(fr);

		// for(int i=0;i<33;i++)
		// {
		// CommunityBasedOnCores cbc = new CommunityBasedOnCores(fr);
		// cbc.runCoresimple(i);
		// }
		cbc.runKCC(0);

		// cbc.getAllNodes();

		// for (int i = 0; i < cbc.size; i++)
		// {
		// cbc.getCoreBasedCommunity(i);
		// }
	}
}
