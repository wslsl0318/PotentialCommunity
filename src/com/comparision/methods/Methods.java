package com.comparision.methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.comparision.coresimple.CommunityBasedOnCores;
import com.comparision.degree.DegreeCentrality;
import com.fileoperations.methods.FileReaders;
import com.publicstructures.methods.CommunityStructure;
import com.pulicoperations.methods.AdjacencyTables;
import com.pulicoperations.methods.NMI;
import com.pulicoperations.methods.Partitions;
import com.pulicoperations.methods.RPF;

public class Methods
{
	public String filePath;
	public String filePathOfRealCommunities;
	public FileReaders fr;
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public ArrayList<ArrayList<Integer>> realCommunities;
	public ArrayList<ArrayList<Integer>> realCommunities_overlap;

	public Methods()
	{
		this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
		this.realCommunities = new ArrayList<ArrayList<Integer>>();
		this.realCommunities_overlap = new ArrayList<ArrayList<Integer>>();
	}

	public Methods(String filePath, String filePathOfRealCommunities)
			throws IOException
	{
		this.filePath = filePath;
		this.filePathOfRealCommunities = filePathOfRealCommunities;
		this.fr = new FileReaders(filePath);
		try
		{
			fr.readFile(false, false);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
		/**********************************************************************************/
		String[] strs = FileReaders
				.readRealCommunities(filePathOfRealCommunities);
		this.realCommunities = CommunityStructure.getComponentsFromString(strs);
		/**********************************************************************************/
		// ArrayList<CommunityStructureOverlap> communities_structure_overlap =
		// FileReaders
		// .get_communities_structure_overlap(filePathOfRealCommunities);
		// this.realCommunities_overlap = CommunityStructureOverlap
		// .get_communities_overlap(communities_structure_overlap);
		/**********************************************************************************/
	}

	/* return nodes rank 0%~10%,10%~20%,...90%~100% in communities */
	public ArrayList<TreeSet<NodeInfluence>> rank_nodes_influence(
			ArrayList<ArrayList<Integer>> partition)
	{
		ArrayList<TreeSet<NodeInfluence>> node_influence_ranks = new ArrayList<TreeSet<NodeInfluence>>();
		TreeSet<NodeInfluence> node_influence_rank_0_10 = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		TreeSet<NodeInfluence> node_influence_rank_10_20 = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		TreeSet<NodeInfluence> node_influence_rank_20_30 = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		TreeSet<NodeInfluence> node_influence_rank_30_40 = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		TreeSet<NodeInfluence> node_influence_rank_40_50 = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		TreeSet<NodeInfluence> node_influence_rank_50_60 = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		TreeSet<NodeInfluence> node_influence_rank_60_70 = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		TreeSet<NodeInfluence> node_influence_rank_70_80 = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		TreeSet<NodeInfluence> node_influence_rank_80_90 = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		TreeSet<NodeInfluence> node_influence_rank_90_100 = new TreeSet<NodeInfluence>(
				new NodeInfluence());
		for (Iterator<ArrayList<Integer>> iter_community = partition.iterator(); iter_community
				.hasNext();)
		{
			ArrayList<Integer> community = iter_community.next();
			TreeSet<NodeInfluence> nodes_influence = new TreeSet<NodeInfluence>(
					new NodeInfluence());
			for (Iterator<Integer> iter_member = community.iterator(); iter_member
					.hasNext();)
			{
				Integer member = iter_member.next();
				NodeInfluence node_new = new NodeInfluence(member,
						adjacencyTable.get(member).size() - 1);
				nodes_influence.add(node_new);
			}
			ArrayList<NodeInfluence> nodes_influence_temp = new ArrayList<NodeInfluence>();
			nodes_influence_temp.addAll(nodes_influence);
			int node_number = nodes_influence_temp.size();
			for (int i = 0; i < node_number; i++)
			{
				if ((double) (i + 1) <= (((double) node_number) / 10) * 1)
				{
					node_influence_rank_0_10.add(nodes_influence_temp.get(i));
				} else if ((double) (i + 1) <= (((double) node_number) / 10) * 2)
				{
					node_influence_rank_10_20.add(nodes_influence_temp.get(i));
				} else if ((double) (i + 1) <= (((double) node_number) / 10) * 3)
				{
					node_influence_rank_20_30.add(nodes_influence_temp.get(i));
				} else if ((double) (i + 1) <= (((double) node_number) / 10) * 4)
				{
					node_influence_rank_30_40.add(nodes_influence_temp.get(i));
				} else if ((double) (i + 1) <= (((double) node_number) / 10) * 5)
				{
					node_influence_rank_40_50.add(nodes_influence_temp.get(i));
				} else if ((double) (i + 1) <= (((double) node_number) / 10) * 6)
				{
					node_influence_rank_50_60.add(nodes_influence_temp.get(i));
				} else if ((double) (i + 1) <= (((double) node_number) / 10) * 7)
				{
					node_influence_rank_60_70.add(nodes_influence_temp.get(i));
				} else if ((double) (i + 1) <= (((double) node_number) / 10) * 8)
				{
					node_influence_rank_70_80.add(nodes_influence_temp.get(i));
				} else if ((double) (i + 1) <= (((double) node_number) / 10) * 9)
				{
					node_influence_rank_80_90.add(nodes_influence_temp.get(i));
				} else if ((double) (i + 1) <= (((double) node_number) / 10) * 10)
				{
					node_influence_rank_90_100.add(nodes_influence_temp.get(i));
				}
			}
		}
		node_influence_ranks.add(node_influence_rank_0_10);
		node_influence_ranks.add(node_influence_rank_10_20);
		node_influence_ranks.add(node_influence_rank_20_30);
		node_influence_ranks.add(node_influence_rank_30_40);
		node_influence_ranks.add(node_influence_rank_40_50);
		node_influence_ranks.add(node_influence_rank_50_60);
		node_influence_ranks.add(node_influence_rank_60_70);
		node_influence_ranks.add(node_influence_rank_70_80);
		node_influence_ranks.add(node_influence_rank_80_90);
		node_influence_ranks.add(node_influence_rank_90_100);
		return node_influence_ranks;
	}

	/* if the influence of a seed node ranks a%~b% return b/10 in a community */
	public int get_seed_node_influence_rank(
			ArrayList<TreeSet<NodeInfluence>> node_influence_ranks,
			int seed_node)
	{
		int seed_node_influence_rank = 0;
		NodeInfluence seed_node_influence = new NodeInfluence(seed_node,
				adjacencyTable.get(seed_node).size() - 1);
		for (int i = 0; i < 10; i++)
		{
			TreeSet<NodeInfluence> node_influence_rank_i = node_influence_ranks
					.get(i);
			if (node_influence_rank_i.contains(seed_node_influence))
			{
				seed_node_influence_rank = i;
				return seed_node_influence_rank;
			}
		}
		return seed_node_influence_rank;
	}

	/* Get average node influence rank percent */
	public void get_average_node_influence_rank_percent(
			ArrayList<Double> node_influence_ranks_percent_average,
			int valid_counter)
	{
		for (Iterator<Double> iter_node_influence_rank_percent_average = node_influence_ranks_percent_average
				.iterator(); iter_node_influence_rank_percent_average.hasNext();)
		{
			Double node_influence_rank_percent_average = iter_node_influence_rank_percent_average
					.next();
			node_influence_rank_percent_average /= valid_counter;
		}
	}

	/*
	 * Calculate all shortest path length of a specific node in the local
	 * community (tested)
	 */
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

	/* To judge if the seed node is still int the centre of the local community */
	public boolean is_in_center(ArrayList<Integer> local_community, int node_id)
	{
		/* weak local community */
		if (!local_community.contains(node_id))
		{
			return false;
		}
		/***********************************************************************************/
		/* strong local community */
		int node_all_shortest_path_length = calculate_all_shortest_path_length(
				local_community, node_id);
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
					return false;
				}
			}
		}
		/***********************************************************************************/
		return true;
	}

	/* Get distinct effective local community size (tested) */
	public int get_community_size_distinct(
			ArrayList<TreeSet<Integer>> communities)
	{
		int community_size_distinct = 0;
		for (int i = 0; i < communities.size(); i++)
		{
			TreeSet<Integer> tested_community = communities.get(i);
			boolean is_existed = false;
			for (int j = i + 1; j < communities.size(); j++)
			{
				TreeSet<Integer> current_community = communities.get(j);
				if (tested_community.equals(current_community))
				{
					is_existed = true;
					break;
				}
			}
			if (is_existed == false)
			{
				community_size_distinct++;
			}
		}
		return community_size_distinct;
	}

	public void out_print_communities(ArrayList<TreeSet<Integer>> communities)
	{
		for (Iterator<TreeSet<Integer>> iter_community = communities.iterator(); iter_community
				.hasNext();)
		{
			TreeSet<Integer> community = iter_community.next();
			System.out.println(community);
		}
	}

	public ArrayList<Measures> runMethods(String targetPath) throws IOException
	{
		System.out.println(this.filePath + "(clu)");

		int DC_valid_counter = 0;
		int KCC_valid_counter = 0;
		int Clauset_valid_counter = 0;
		int LWP_valid_counter = 0;
		int Chen_valid_counter = 0;
		int LS_valid_counter = 0;
		int LCD_valid_counter = 0;

		double DC_NMI_average = 0;
		double KCC_NMI_average = 0;
		double Clauset_NMI_average = 0;
		double LWP_NMI_average = 0;
		double Chen_NMI_average = 0;
		double LS_NMI_average = 0;
		double LCD_NMI_average = 0;

		double DC_R_average = 0;
		double KCC_R_average = 0;
		double Clauset_R_average = 0;
		double LWP_R_average = 0;
		double Chen_R_average = 0;
		double LS_R_average = 0;
		double LCD_R_average = 0;

		double DC_P_average = 0;
		double KCC_P_average = 0;
		double Clauset_P_average = 0;
		double LWP_P_average = 0;
		double Chen_P_average = 0;
		double LS_P_average = 0;
		double LCD_P_average = 0;

		double DC_F_average = 0;
		double KCC_F_average = 0;
		double Clauset_F_average = 0;
		double LWP_F_average = 0;
		double Chen_F_average = 0;
		double LS_F_average = 0;
		double LCD_F_average = 0;

		double DC_E = 0;
		double KCC_E = 0;
		double Clauset_E = 0;
		double LWP_E = 0;
		double Chen_E = 0;
		double LS_E = 0;
		double LCD_E = 0;

		ArrayList<TreeSet<Integer>> DC_communities = new ArrayList<TreeSet<Integer>>();
		ArrayList<TreeSet<Integer>> KCC_communities = new ArrayList<TreeSet<Integer>>();
		ArrayList<TreeSet<Integer>> Clauset_communities = new ArrayList<TreeSet<Integer>>();
		ArrayList<TreeSet<Integer>> LWP_communities = new ArrayList<TreeSet<Integer>>();
		ArrayList<TreeSet<Integer>> Chen_communities = new ArrayList<TreeSet<Integer>>();
		ArrayList<TreeSet<Integer>> LS_communities = new ArrayList<TreeSet<Integer>>();
		ArrayList<TreeSet<Integer>> LCD_communities = new ArrayList<TreeSet<Integer>>();

		int DC_communities_size = 0;
		int KCC_communities_size = 0;
		int Clauset_communities_size = 0;
		int LWP_communities_size = 0;
		int Chen_communities_size = 0;
		int LS_communities_size = 0;
		int LCD_communities_size = 0;

		int DC_distinct_communities_size = 0;
		int KCC_distinct_communities_size = 0;
		int Clauset_distinct_communities_size = 0;
		int LWP_distinct_communities_size = 0;
		int Chen_distinct_communities_size = 0;
		int LS_distinct_communities_size = 0;
		int LCD_distinct_communities_size = 0;

		// double DC_rate = 0;
		// double KCC_rate = 0;
		// double Clauset_rate = 0;
		// double LWP_rate = 0;
		// double Chen_rate = 0;
		// double LS_rate = 0;
		// double LCD_rate = 0;

		double DC_rate_distinct = 0;
		double KCC_rate_distinct = 0;
		double Clauset_rate_distinct = 0;
		double LWP_rate_distinct = 0;
		double Chen_rate_distinct = 0;
		double LS_rate_distinct = 0;
		double LCD_rate_distinct = 0;

		double DC_ed = 0;
		double KCC_ed = 0;
		double Clauset_ed = 0;
		double LWP_ed = 0;
		double Chen_ed = 0;
		double LS_ed = 0;
		double LCD_ed = 0;

		ArrayList<Double> DC_node_influence_ranks_percent_average = new ArrayList<Double>();
		double DC_node_influence_rank_0_10_percent_average = 0;
		double DC_node_influence_rank_10_20_percent_average = 0;
		double DC_node_influence_rank_20_30_percent_average = 0;
		double DC_node_influence_rank_30_40_percent_average = 0;
		double DC_node_influence_rank_40_50_percent_average = 0;
		double DC_node_influence_rank_50_60_percent_average = 0;
		double DC_node_influence_rank_60_70_percent_average = 0;
		double DC_node_influence_rank_70_80_percent_average = 0;
		double DC_node_influence_rank_80_90_percent_average = 0;
		double DC_node_influence_rank_90_100_percent_average = 0;

		ArrayList<Double> KCC_node_influence_ranks_percent_average = new ArrayList<Double>();
		double KCC_node_influence_rank_0_10_percent_average = 0;
		double KCC_node_influence_rank_10_20_percent_average = 0;
		double KCC_node_influence_rank_20_30_percent_average = 0;
		double KCC_node_influence_rank_30_40_percent_average = 0;
		double KCC_node_influence_rank_40_50_percent_average = 0;
		double KCC_node_influence_rank_50_60_percent_average = 0;
		double KCC_node_influence_rank_60_70_percent_average = 0;
		double KCC_node_influence_rank_70_80_percent_average = 0;
		double KCC_node_influence_rank_80_90_percent_average = 0;
		double KCC_node_influence_rank_90_100_percent_average = 0;

		ArrayList<Double> Clauset_node_influence_ranks_percent_average = new ArrayList<Double>();
		double Clauset_node_influence_rank_0_10_percent_average = 0;
		double Clauset_node_influence_rank_10_20_percent_average = 0;
		double Clauset_node_influence_rank_20_30_percent_average = 0;
		double Clauset_node_influence_rank_30_40_percent_average = 0;
		double Clauset_node_influence_rank_40_50_percent_average = 0;
		double Clauset_node_influence_rank_50_60_percent_average = 0;
		double Clauset_node_influence_rank_60_70_percent_average = 0;
		double Clauset_node_influence_rank_70_80_percent_average = 0;
		double Clauset_node_influence_rank_80_90_percent_average = 0;
		double Clauset_node_influence_rank_90_100_percent_average = 0;

		ArrayList<Double> LWP_node_influence_ranks_percent_average = new ArrayList<Double>();
		double LWP_node_influence_rank_0_10_percent_average = 0;
		double LWP_node_influence_rank_10_20_percent_average = 0;
		double LWP_node_influence_rank_20_30_percent_average = 0;
		double LWP_node_influence_rank_30_40_percent_average = 0;
		double LWP_node_influence_rank_40_50_percent_average = 0;
		double LWP_node_influence_rank_50_60_percent_average = 0;
		double LWP_node_influence_rank_60_70_percent_average = 0;
		double LWP_node_influence_rank_70_80_percent_average = 0;
		double LWP_node_influence_rank_80_90_percent_average = 0;
		double LWP_node_influence_rank_90_100_percent_average = 0;

		ArrayList<Double> Chen_node_influence_ranks_percent_average = new ArrayList<Double>();
		double Chen_node_influence_rank_0_10_percent_average = 0;
		double Chen_node_influence_rank_10_20_percent_average = 0;
		double Chen_node_influence_rank_20_30_percent_average = 0;
		double Chen_node_influence_rank_30_40_percent_average = 0;
		double Chen_node_influence_rank_40_50_percent_average = 0;
		double Chen_node_influence_rank_50_60_percent_average = 0;
		double Chen_node_influence_rank_60_70_percent_average = 0;
		double Chen_node_influence_rank_70_80_percent_average = 0;
		double Chen_node_influence_rank_80_90_percent_average = 0;
		double Chen_node_influence_rank_90_100_percent_average = 0;

		ArrayList<Double> LS_node_influence_ranks_percent_average = new ArrayList<Double>();
		double LS_node_influence_rank_0_10_percent_average = 0;
		double LS_node_influence_rank_10_20_percent_average = 0;
		double LS_node_influence_rank_20_30_percent_average = 0;
		double LS_node_influence_rank_30_40_percent_average = 0;
		double LS_node_influence_rank_40_50_percent_average = 0;
		double LS_node_influence_rank_50_60_percent_average = 0;
		double LS_node_influence_rank_60_70_percent_average = 0;
		double LS_node_influence_rank_70_80_percent_average = 0;
		double LS_node_influence_rank_80_90_percent_average = 0;
		double LS_node_influence_rank_90_100_percent_average = 0;

		ArrayList<Double> LCD_node_influence_ranks_percent_average = new ArrayList<Double>();
		double LCD_node_influence_rank_0_10_percent_average = 0;
		double LCD_node_influence_rank_10_20_percent_average = 0;
		double LCD_node_influence_rank_20_30_percent_average = 0;
		double LCD_node_influence_rank_30_40_percent_average = 0;
		double LCD_node_influence_rank_40_50_percent_average = 0;
		double LCD_node_influence_rank_50_60_percent_average = 0;
		double LCD_node_influence_rank_60_70_percent_average = 0;
		double LCD_node_influence_rank_70_80_percent_average = 0;
		double LCD_node_influence_rank_80_90_percent_average = 0;
		double LCD_node_influence_rank_90_100_percent_average = 0;

		ArrayList<ArrayList<Integer>> partitionR = new ArrayList<ArrayList<Integer>>();
		/**********************************************************************************/
		// ArrayList<TreeSet<NodeInfluence>> node_influence_ranks =
		// rank_nodes_influence(realCommunities);// changed
		ArrayList<TreeSet<NodeInfluence>> node_influence_ranks = rank_nodes_influence(realCommunities);// changed(realCommunities)
		/**********************************************************************************/
		for (int id = 0; id < size; id++)
		{
			int node_influence_rank = get_seed_node_influence_rank(
					node_influence_ranks, id);
			// System.out.println(id + "," + node_influence_rank);
			ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
			/**********************************************************************************/
			// partitionR = Partitions.getPartitionROfSpecificNode(id,
			// realCommunities, size);// changed
			partitionR = Partitions.getPartitionROfSpecificNode(id,
					realCommunities, size);// changed(realCommunities)
			/**********************************************************************************/
			/**********************************************************************************/
			DegreeCentrality DC = new DegreeCentrality(fr);
			partitionF = DC.runDC(id);
			if (!partitionF.isEmpty() && is_in_center(partitionF.get(0), id))
			{
				TreeSet<Integer> DC_community = new TreeSet<Integer>();
				DC_community.addAll(partitionF.get(0));
				DC_communities.add(DC_community);
				DC_valid_counter++;
				if (node_influence_rank == 0)
				{
					DC_node_influence_rank_0_10_percent_average++;
				} else if (node_influence_rank == 1)
				{
					DC_node_influence_rank_10_20_percent_average++;
				} else if (node_influence_rank == 2)
				{
					DC_node_influence_rank_20_30_percent_average++;
				} else if (node_influence_rank == 3)
				{
					DC_node_influence_rank_30_40_percent_average++;
				} else if (node_influence_rank == 4)
				{
					DC_node_influence_rank_40_50_percent_average++;
				} else if (node_influence_rank == 5)
				{
					DC_node_influence_rank_50_60_percent_average++;
				} else if (node_influence_rank == 6)
				{
					DC_node_influence_rank_60_70_percent_average++;
				} else if (node_influence_rank == 7)
				{
					DC_node_influence_rank_70_80_percent_average++;
				} else if (node_influence_rank == 8)
				{
					DC_node_influence_rank_80_90_percent_average++;
				} else if (node_influence_rank == 9)
				{
					DC_node_influence_rank_90_100_percent_average++;
				}
				double DC_NMI = NMI.NMIPartitionUnoverLap(partitionF,
						partitionR, size);
				DC_NMI_average += DC_NMI;
				ArrayList<Double> DC_RPF = RPF.getRecall_Precision_F(
						partitionF, partitionR);
				DC_R_average += DC_RPF.get(0);
				DC_P_average += DC_RPF.get(1);
				DC_F_average += DC_RPF.get(2);
			}
			/**********************************************************************************/
			CommunityBasedOnCores KCC = new CommunityBasedOnCores(fr);
			partitionF = KCC.runKCC(id);
			if (!partitionF.isEmpty() && is_in_center(partitionF.get(0), id))
			{
				TreeSet<Integer> KCC_community = new TreeSet<Integer>();
				KCC_community.addAll(partitionF.get(0));
				KCC_communities.add(KCC_community);
				KCC_valid_counter++;
				if (node_influence_rank == 0)
				{
					KCC_node_influence_rank_0_10_percent_average++;
				} else if (node_influence_rank == 1)
				{
					KCC_node_influence_rank_10_20_percent_average++;
				} else if (node_influence_rank == 2)
				{
					KCC_node_influence_rank_20_30_percent_average++;
				} else if (node_influence_rank == 3)
				{
					KCC_node_influence_rank_30_40_percent_average++;
				} else if (node_influence_rank == 4)
				{
					KCC_node_influence_rank_40_50_percent_average++;
				} else if (node_influence_rank == 5)
				{
					KCC_node_influence_rank_50_60_percent_average++;
				} else if (node_influence_rank == 6)
				{
					KCC_node_influence_rank_60_70_percent_average++;
				} else if (node_influence_rank == 7)
				{
					KCC_node_influence_rank_70_80_percent_average++;
				} else if (node_influence_rank == 8)
				{
					KCC_node_influence_rank_80_90_percent_average++;
				} else if (node_influence_rank == 9)
				{
					KCC_node_influence_rank_90_100_percent_average++;
				}
				double KCC_NMI = NMI.NMIPartitionUnoverLap(partitionF,
						partitionR, size);
				KCC_NMI_average += KCC_NMI;
				ArrayList<Double> KCC_RPF = RPF.getRecall_Precision_F(
						partitionF, partitionR);
				KCC_R_average += KCC_RPF.get(0);
				KCC_P_average += KCC_RPF.get(1);
				KCC_F_average += KCC_RPF.get(2);
			}
			/**********************************************************************************/
			// ClausetAlgorithm Clauset = new ClausetAlgorithm(fr);
			// partitionF = Clauset.run_clauset(id);
			// if (!partitionF.isEmpty() && is_in_center(partitionF.get(0), id))
			// {
			// TreeSet<Integer> Clauset_community = new TreeSet<Integer>();
			// Clauset_community.addAll(partitionF.get(0));
			// Clauset_communities.add(Clauset_community);
			// Clauset_valid_counter++;
			// if (node_influence_rank == 0)
			// {
			// Clauset_node_influence_rank_0_10_percent_average++;
			// } else if (node_influence_rank == 1)
			// {
			// Clauset_node_influence_rank_10_20_percent_average++;
			// } else if (node_influence_rank == 2)
			// {
			// Clauset_node_influence_rank_20_30_percent_average++;
			// } else if (node_influence_rank == 3)
			// {
			// Clauset_node_influence_rank_30_40_percent_average++;
			// } else if (node_influence_rank == 4)
			// {
			// Clauset_node_influence_rank_40_50_percent_average++;
			// } else if (node_influence_rank == 5)
			// {
			// Clauset_node_influence_rank_50_60_percent_average++;
			// } else if (node_influence_rank == 6)
			// {
			// Clauset_node_influence_rank_60_70_percent_average++;
			// } else if (node_influence_rank == 7)
			// {
			// Clauset_node_influence_rank_70_80_percent_average++;
			// } else if (node_influence_rank == 8)
			// {
			// Clauset_node_influence_rank_80_90_percent_average++;
			// } else if (node_influence_rank == 9)
			// {
			// Clauset_node_influence_rank_90_100_percent_average++;
			// }
			// double Clauset_NMI = NMI.NMIPartitionUnoverLap(partitionF,
			// partitionR, size);
			// Clauset_NMI_average += Clauset_NMI;
			// ArrayList<Double> Clauset_RPF = RPF.getRecall_Precision_F(
			// partitionF, partitionR);
			// Clauset_R_average += Clauset_RPF.get(0);
			// Clauset_P_average += Clauset_RPF.get(1);
			// Clauset_F_average += Clauset_RPF.get(2);
			// }
			/**********************************************************************************/
			// LWPAlgorithm LWP = new LWPAlgorithm(fr);
			// partitionF = LWP.runLWP(id);
			// if (!partitionF.isEmpty() && is_in_center(partitionF.get(0), id))
			// {
			// TreeSet<Integer> LWP_community = new TreeSet<Integer>();
			// LWP_community.addAll(partitionF.get(0));
			// LWP_communities.add(LWP_community);
			// LWP_valid_counter++;
			// if (node_influence_rank == 0)
			// {
			// LWP_node_influence_rank_0_10_percent_average++;
			// } else if (node_influence_rank == 1)
			// {
			// LWP_node_influence_rank_10_20_percent_average++;
			// } else if (node_influence_rank == 2)
			// {
			// LWP_node_influence_rank_20_30_percent_average++;
			// } else if (node_influence_rank == 3)
			// {
			// LWP_node_influence_rank_30_40_percent_average++;
			// } else if (node_influence_rank == 4)
			// {
			// LWP_node_influence_rank_40_50_percent_average++;
			// } else if (node_influence_rank == 5)
			// {
			// LWP_node_influence_rank_50_60_percent_average++;
			// } else if (node_influence_rank == 6)
			// {
			// LWP_node_influence_rank_60_70_percent_average++;
			// } else if (node_influence_rank == 7)
			// {
			// LWP_node_influence_rank_70_80_percent_average++;
			// } else if (node_influence_rank == 8)
			// {
			// LWP_node_influence_rank_80_90_percent_average++;
			// } else if (node_influence_rank == 9)
			// {
			// LWP_node_influence_rank_90_100_percent_average++;
			// }
			// double LWP_NMI = NMI.NMIPartitionUnoverLap(partitionF,
			// partitionR, size);
			// LWP_NMI_average += LWP_NMI;
			// ArrayList<Double> LWP_RPF = RPF.getRecall_Precision_F(
			// partitionF, partitionR);
			// LWP_R_average += LWP_RPF.get(0);
			// LWP_P_average += LWP_RPF.get(1);
			// LWP_F_average += LWP_RPF.get(2);
			// }
			/**********************************************************************************/
			// ConnectionDensity Chen = new ConnectionDensity(fr);
			// partitionF = Chen.runChen(id);
			// if (!partitionF.isEmpty() && is_in_center(partitionF.get(0), id))
			// {
			// TreeSet<Integer> Chen_community = new TreeSet<Integer>();
			// Chen_community.addAll(partitionF.get(0));
			// Chen_communities.add(Chen_community);
			// Chen_valid_counter++;
			// if (node_influence_rank == 0)
			// {
			// Chen_node_influence_rank_0_10_percent_average++;
			// } else if (node_influence_rank == 1)
			// {
			// Chen_node_influence_rank_10_20_percent_average++;
			// } else if (node_influence_rank == 2)
			// {
			// Chen_node_influence_rank_20_30_percent_average++;
			// } else if (node_influence_rank == 3)
			// {
			// Chen_node_influence_rank_30_40_percent_average++;
			// } else if (node_influence_rank == 4)
			// {
			// Chen_node_influence_rank_40_50_percent_average++;
			// } else if (node_influence_rank == 5)
			// {
			// Chen_node_influence_rank_50_60_percent_average++;
			// } else if (node_influence_rank == 6)
			// {
			// Chen_node_influence_rank_60_70_percent_average++;
			// } else if (node_influence_rank == 7)
			// {
			// Chen_node_influence_rank_70_80_percent_average++;
			// } else if (node_influence_rank == 8)
			// {
			// Chen_node_influence_rank_80_90_percent_average++;
			// } else if (node_influence_rank == 9)
			// {
			// Chen_node_influence_rank_90_100_percent_average++;
			// }
			// double Chen_NMI = NMI.NMIPartitionUnoverLap(partitionF,
			// partitionR, size);
			// Chen_NMI_average += Chen_NMI;
			// ArrayList<Double> Chen_RPF = RPF.getRecall_Precision_F(
			// partitionF, partitionR);
			// Chen_R_average += Chen_RPF.get(0);
			// Chen_P_average += Chen_RPF.get(1);
			// Chen_F_average += Chen_RPF.get(2);
			// }
			/**********************************************************************************/
			// LinkSimilarity LS = new LinkSimilarity(fr);
			// partitionF = LS.run_ls(id);
			// if (!partitionF.isEmpty() && is_in_center(partitionF.get(0), id))
			// {
			// TreeSet<Integer> LS_community = new TreeSet<Integer>();
			// LS_community.addAll(partitionF.get(0));
			// LS_communities.add(LS_community);
			// LS_valid_counter++;
			// if (node_influence_rank == 0)
			// {
			// LS_node_influence_rank_0_10_percent_average++;
			// } else if (node_influence_rank == 1)
			// {
			// LS_node_influence_rank_10_20_percent_average++;
			// } else if (node_influence_rank == 2)
			// {
			// LS_node_influence_rank_20_30_percent_average++;
			// } else if (node_influence_rank == 3)
			// {
			// LS_node_influence_rank_30_40_percent_average++;
			// } else if (node_influence_rank == 4)
			// {
			// LS_node_influence_rank_40_50_percent_average++;
			// } else if (node_influence_rank == 5)
			// {
			// LS_node_influence_rank_50_60_percent_average++;
			// } else if (node_influence_rank == 6)
			// {
			// LS_node_influence_rank_60_70_percent_average++;
			// } else if (node_influence_rank == 7)
			// {
			// LS_node_influence_rank_70_80_percent_average++;
			// } else if (node_influence_rank == 8)
			// {
			// LS_node_influence_rank_80_90_percent_average++;
			// } else if (node_influence_rank == 9)
			// {
			// LS_node_influence_rank_90_100_percent_average++;
			// }
			// double LS_NMI = NMI.NMIPartitionUnoverLap(partitionF,
			// partitionR, size);
			// LS_NMI_average += LS_NMI;
			// ArrayList<Double> LS_RPF = RPF.getRecall_Precision_F(
			// partitionF, partitionR);
			// LS_R_average += LS_RPF.get(0);
			// LS_P_average += LS_RPF.get(1);
			// LS_F_average += LS_RPF.get(2);
			// }
			/**********************************************************************************/
			// LCDAlgorithm LCD = new LCDAlgorithm(fr);
			// ArrayList<ArrayList<ArrayList<Integer>>> partitionFS = LCD
			// .run_lcd(id);
			// if (!partitionFS.isEmpty())
			// {
			// ArrayList<ArrayList<ArrayList<Integer>>> partitionFS_effective =
			// new ArrayList<ArrayList<ArrayList<Integer>>>();
			// for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionF =
			// partitionFS
			// .iterator(); iter_partitionF.hasNext();)
			// {
			// partitionF = iter_partitionF.next();
			// if (partitionF.size() != 0
			// && is_in_center(partitionF.get(0), id))
			// {
			// partitionFS_effective.add(partitionF);
			// }
			// }
			// if (!partitionFS_effective.isEmpty())
			// {
			// LCD_valid_counter++;
			// if (node_influence_rank == 0)
			// {
			// LCD_node_influence_rank_0_10_percent_average++;
			// } else if (node_influence_rank == 1)
			// {
			// LCD_node_influence_rank_10_20_percent_average++;
			// } else if (node_influence_rank == 2)
			// {
			// LCD_node_influence_rank_20_30_percent_average++;
			// } else if (node_influence_rank == 3)
			// {
			// LCD_node_influence_rank_30_40_percent_average++;
			// } else if (node_influence_rank == 4)
			// {
			// LCD_node_influence_rank_40_50_percent_average++;
			// } else if (node_influence_rank == 5)
			// {
			// LCD_node_influence_rank_50_60_percent_average++;
			// } else if (node_influence_rank == 6)
			// {
			// LCD_node_influence_rank_60_70_percent_average++;
			// } else if (node_influence_rank == 7)
			// {
			// LCD_node_influence_rank_70_80_percent_average++;
			// } else if (node_influence_rank == 8)
			// {
			// LCD_node_influence_rank_80_90_percent_average++;
			// } else if (node_influence_rank == 9)
			// {
			// LCD_node_influence_rank_90_100_percent_average++;
			// }
			// double LCD_NMI_max = -1;
			// ArrayList<ArrayList<Integer>> partitionF_LCD_max = new
			// ArrayList<ArrayList<Integer>>();
			// for (Iterator<ArrayList<ArrayList<Integer>>>
			// iter_partitionF_effective = partitionFS_effective
			// .iterator(); iter_partitionF_effective.hasNext();)
			// {
			// partitionF = iter_partitionF_effective.next();
			// double LCD_NMI = NMI.NMIPartitionUnoverLap(partitionF,
			// partitionR, size);
			// if (LCD_NMI > LCD_NMI_max)
			// {
			// LCD_NMI_max = LCD_NMI;
			// partitionF_LCD_max = partitionF;
			// }
			// }
			// TreeSet<Integer> LCD_community = new TreeSet<Integer>();
			// LCD_community.addAll(partitionF_LCD_max.get(0));
			// LCD_communities.add(LCD_community);
			// LCD_NMI_average += LCD_NMI_max;
			// ArrayList<Double> LCD_RPF = RPF.getRecall_Precision_F(
			// partitionF_LCD_max, partitionR);
			// LCD_R_average += LCD_RPF.get(0);
			// LCD_P_average += LCD_RPF.get(1);
			// LCD_F_average += LCD_RPF.get(2);
			// }
			// }
			/**********************************************************************************/
		}

		DC_NMI_average = DC_NMI_average / DC_valid_counter;
		KCC_NMI_average = KCC_NMI_average / KCC_valid_counter;
		Clauset_NMI_average = Clauset_NMI_average / Clauset_valid_counter;
		LWP_NMI_average = LWP_NMI_average / LWP_valid_counter;
		Chen_NMI_average = Chen_NMI_average / Chen_valid_counter;
		LS_NMI_average = LS_NMI_average / LS_valid_counter;
		LCD_NMI_average = LCD_NMI_average / LCD_valid_counter;

		DC_R_average = DC_R_average / DC_valid_counter;
		KCC_R_average = KCC_R_average / KCC_valid_counter;
		Clauset_R_average = Clauset_R_average / Clauset_valid_counter;
		LWP_R_average = LWP_R_average / LWP_valid_counter;
		Chen_R_average = Chen_R_average / Chen_valid_counter;
		LS_R_average = LS_R_average / LS_valid_counter;
		LCD_R_average = LCD_R_average / LCD_valid_counter;

		DC_P_average = DC_P_average / DC_valid_counter;
		KCC_P_average = KCC_P_average / KCC_valid_counter;
		Clauset_P_average = Clauset_P_average / Clauset_valid_counter;
		LWP_P_average = LWP_P_average / LWP_valid_counter;
		Chen_P_average = Chen_P_average / Chen_valid_counter;
		LS_P_average = LS_P_average / LS_valid_counter;
		LCD_P_average = LCD_P_average / LCD_valid_counter;

		DC_F_average = DC_F_average / DC_valid_counter;
		KCC_F_average = KCC_F_average / KCC_valid_counter;
		Clauset_F_average = Clauset_F_average / Clauset_valid_counter;
		LWP_F_average = LWP_F_average / LWP_valid_counter;
		Chen_F_average = Chen_F_average / Chen_valid_counter;
		LS_F_average = LS_F_average / LS_valid_counter;
		LCD_F_average = LCD_F_average / LCD_valid_counter;

		int validSize = size;// mostImportantMemberOfCommunities.size();

		DC_E = (double) DC_valid_counter / validSize;
		KCC_E = (double) KCC_valid_counter / validSize;
		Clauset_E = (double) Clauset_valid_counter / validSize;
		LWP_E = (double) LWP_valid_counter / validSize;
		Chen_E = (double) Chen_valid_counter / validSize;
		LS_E = (double) LS_valid_counter / validSize;
		LCD_E = (double) LCD_valid_counter / validSize;

		DC_communities_size = DC_communities.size();
		KCC_communities_size = KCC_communities.size();
		Clauset_communities_size = Clauset_communities.size();
		LWP_communities_size = LWP_communities.size();
		Chen_communities_size = Chen_communities.size();
		LS_communities_size = LS_communities.size();
		LCD_communities_size = LCD_communities.size();

		DC_distinct_communities_size = get_community_size_distinct(DC_communities);
		KCC_distinct_communities_size = get_community_size_distinct(KCC_communities);
		Clauset_distinct_communities_size = get_community_size_distinct(Clauset_communities);
		LWP_distinct_communities_size = get_community_size_distinct(LWP_communities);
		Chen_distinct_communities_size = get_community_size_distinct(Chen_communities);
		LS_distinct_communities_size = get_community_size_distinct(LS_communities);
		LCD_distinct_communities_size = get_community_size_distinct(LCD_communities);

		// DC_rate = (double) DC_communities_size / size;
		// KCC_rate = (double) KCC_communities_size / size;
		// Clauset_rate = (double) Clauset_communities_size / size;
		// LWP_rate = (double) LWP_communities_size / size;
		// Chen_rate = (double) Chen_communities_size / size;
		// LS_rate = (double) LS_communities_size / size;
		// LCD_rate = (double) LCD_communities_size / size;

		DC_rate_distinct = (double) DC_distinct_communities_size
				/ DC_communities_size;
		KCC_rate_distinct = (double) KCC_distinct_communities_size
				/ KCC_communities_size;
		Clauset_rate_distinct = (double) Clauset_distinct_communities_size
				/ Clauset_communities_size;
		LWP_rate_distinct = (double) LWP_distinct_communities_size
				/ LWP_communities_size;
		Chen_rate_distinct = (double) Chen_distinct_communities_size
				/ Chen_communities_size;
		LS_rate_distinct = (double) LS_distinct_communities_size
				/ LS_communities_size;
		LCD_rate_distinct = (double) LCD_distinct_communities_size
				/ LCD_communities_size;

		DC_ed = DC_rate_distinct * DC_E;
		KCC_ed = KCC_rate_distinct * KCC_E;
		Clauset_ed = Clauset_rate_distinct * Clauset_E;
		LWP_ed = LWP_rate_distinct * LWP_E;
		Chen_ed = Chen_rate_distinct * Chen_E;
		LS_ed = LS_rate_distinct * LS_E;
		LCD_ed = LCD_rate_distinct * LCD_E;

		// System.out.println("DC_communities");
		// out_print_communities(DC_communities);
		// System.out.println(DC_distinct_communities_size);
		// System.out.println("KCC_communities");
		// out_print_communities(KCC_communities);
		// System.out.println(KCC_distinct_communities_size);
		// System.out.println("Clauset_communities");
		// out_print_communities(Clauset_communities);
		// System.out.println(Clauset_distinct_communities_size);
		// System.out.println("LWP_communities");
		// out_print_communities(LWP_communities);
		// System.out.println(LWP_distinct_communities_size);
		// System.out.println("Chen_communities");
		// out_print_communities(Chen_communities);
		// System.out.println(Chen_distinct_communities_size);
		// System.out.println("LS_communities");
		// out_print_communities(LS_communities);
		// System.out.println(LS_distinct_communities_size);
		// System.out.println("LCD_communities");
		// out_print_communities(LCD_communities);
		// System.out.println(LCD_distinct_communities_size);

		// System.out.println("DC: DA, NMI, R, P, F");
		// System.out.println(DC_E + "," + DC_NMI_average + "," + DC_R_average
		// + "," + DC_P_average + "," + DC_F_average);
		// System.out.println("KCC: DA, NMI, R, P, F");
		// System.out.println(KCC_E + "," + KCC_NMI_average + "," +
		// KCC_R_average
		// + "," + KCC_P_average + "," + KCC_F_average);
		// System.out.println("Clauset: DA, NMI, R, P, F");
		// System.out.println(Clauset_E + "," + Clauset_NMI_average + ","
		// + Clauset_R_average + "," + Clauset_P_average + ","
		// + Clauset_F_average);
		// System.out.println("LWP: DA, NMI, R, P, F");
		// System.out.println(LWP_E + "," + LWP_NMI_average + "," +
		// LWP_R_average
		// + "," + LWP_P_average + "," + LWP_F_average);
		// System.out.println("Chen: DA, NMI, R, P, F");
		// System.out.println(Chen_E + "," + Chen_NMI_average + ","
		// + Chen_R_average + "," + Chen_P_average + "," + Chen_F_average);
		// System.out.println("LS: DA, NMI, R, P, F");
		// System.out.println(LS_E + "," + LS_NMI_average + "," + LS_R_average
		// + "," + LS_P_average + "," + LS_F_average);
		// System.out.println("LCD: DA, NMI, R, P, F");
		// System.out.println(LCD_E + "," + LCD_NMI_average + "," +
		// LCD_R_average
		// + "," + LCD_P_average + "," + LCD_F_average);

		DC_node_influence_ranks_percent_average
				.add(DC_node_influence_rank_0_10_percent_average
						/ DC_valid_counter);
		DC_node_influence_ranks_percent_average
				.add(DC_node_influence_rank_10_20_percent_average
						/ DC_valid_counter);
		DC_node_influence_ranks_percent_average
				.add(DC_node_influence_rank_20_30_percent_average
						/ DC_valid_counter);
		DC_node_influence_ranks_percent_average
				.add(DC_node_influence_rank_30_40_percent_average
						/ DC_valid_counter);
		DC_node_influence_ranks_percent_average
				.add(DC_node_influence_rank_40_50_percent_average
						/ DC_valid_counter);
		DC_node_influence_ranks_percent_average
				.add(DC_node_influence_rank_50_60_percent_average
						/ DC_valid_counter);
		DC_node_influence_ranks_percent_average
				.add(DC_node_influence_rank_60_70_percent_average
						/ DC_valid_counter);
		DC_node_influence_ranks_percent_average
				.add(DC_node_influence_rank_70_80_percent_average
						/ DC_valid_counter);
		DC_node_influence_ranks_percent_average
				.add(DC_node_influence_rank_80_90_percent_average
						/ DC_valid_counter);
		DC_node_influence_ranks_percent_average
				.add(DC_node_influence_rank_90_100_percent_average
						/ DC_valid_counter);

		KCC_node_influence_ranks_percent_average
				.add(KCC_node_influence_rank_0_10_percent_average
						/ KCC_valid_counter);
		KCC_node_influence_ranks_percent_average
				.add(KCC_node_influence_rank_10_20_percent_average
						/ KCC_valid_counter);
		KCC_node_influence_ranks_percent_average
				.add(KCC_node_influence_rank_20_30_percent_average
						/ KCC_valid_counter);
		KCC_node_influence_ranks_percent_average
				.add(KCC_node_influence_rank_30_40_percent_average
						/ KCC_valid_counter);
		KCC_node_influence_ranks_percent_average
				.add(KCC_node_influence_rank_40_50_percent_average
						/ KCC_valid_counter);
		KCC_node_influence_ranks_percent_average
				.add(KCC_node_influence_rank_50_60_percent_average
						/ KCC_valid_counter);
		KCC_node_influence_ranks_percent_average
				.add(KCC_node_influence_rank_60_70_percent_average
						/ KCC_valid_counter);
		KCC_node_influence_ranks_percent_average
				.add(KCC_node_influence_rank_70_80_percent_average
						/ KCC_valid_counter);
		KCC_node_influence_ranks_percent_average
				.add(KCC_node_influence_rank_80_90_percent_average
						/ KCC_valid_counter);
		KCC_node_influence_ranks_percent_average
				.add(KCC_node_influence_rank_90_100_percent_average
						/ KCC_valid_counter);

		Clauset_node_influence_ranks_percent_average
				.add(Clauset_node_influence_rank_0_10_percent_average
						/ Clauset_valid_counter);
		Clauset_node_influence_ranks_percent_average
				.add(Clauset_node_influence_rank_10_20_percent_average
						/ Clauset_valid_counter);
		Clauset_node_influence_ranks_percent_average
				.add(Clauset_node_influence_rank_20_30_percent_average
						/ Clauset_valid_counter);
		Clauset_node_influence_ranks_percent_average
				.add(Clauset_node_influence_rank_30_40_percent_average
						/ Clauset_valid_counter);
		Clauset_node_influence_ranks_percent_average
				.add(Clauset_node_influence_rank_40_50_percent_average
						/ Clauset_valid_counter);
		Clauset_node_influence_ranks_percent_average
				.add(Clauset_node_influence_rank_50_60_percent_average
						/ Clauset_valid_counter);
		Clauset_node_influence_ranks_percent_average
				.add(Clauset_node_influence_rank_60_70_percent_average
						/ Clauset_valid_counter);
		Clauset_node_influence_ranks_percent_average
				.add(Clauset_node_influence_rank_70_80_percent_average
						/ Clauset_valid_counter);
		Clauset_node_influence_ranks_percent_average
				.add(Clauset_node_influence_rank_80_90_percent_average
						/ Clauset_valid_counter);
		Clauset_node_influence_ranks_percent_average
				.add(Clauset_node_influence_rank_90_100_percent_average
						/ Clauset_valid_counter);

		LWP_node_influence_ranks_percent_average
				.add(LWP_node_influence_rank_0_10_percent_average
						/ LWP_valid_counter);
		LWP_node_influence_ranks_percent_average
				.add(LWP_node_influence_rank_10_20_percent_average
						/ LWP_valid_counter);
		LWP_node_influence_ranks_percent_average
				.add(LWP_node_influence_rank_20_30_percent_average
						/ LWP_valid_counter);
		LWP_node_influence_ranks_percent_average
				.add(LWP_node_influence_rank_30_40_percent_average
						/ LWP_valid_counter);
		LWP_node_influence_ranks_percent_average
				.add(LWP_node_influence_rank_40_50_percent_average
						/ LWP_valid_counter);
		LWP_node_influence_ranks_percent_average
				.add(LWP_node_influence_rank_50_60_percent_average
						/ LWP_valid_counter);
		LWP_node_influence_ranks_percent_average
				.add(LWP_node_influence_rank_60_70_percent_average
						/ LWP_valid_counter);
		LWP_node_influence_ranks_percent_average
				.add(LWP_node_influence_rank_70_80_percent_average
						/ LWP_valid_counter);
		LWP_node_influence_ranks_percent_average
				.add(LWP_node_influence_rank_80_90_percent_average
						/ LWP_valid_counter);
		LWP_node_influence_ranks_percent_average
				.add(LWP_node_influence_rank_90_100_percent_average
						/ LWP_valid_counter);

		Chen_node_influence_ranks_percent_average
				.add(Chen_node_influence_rank_0_10_percent_average
						/ Chen_valid_counter);
		Chen_node_influence_ranks_percent_average
				.add(Chen_node_influence_rank_10_20_percent_average
						/ Chen_valid_counter);
		Chen_node_influence_ranks_percent_average
				.add(Chen_node_influence_rank_20_30_percent_average
						/ Chen_valid_counter);
		Chen_node_influence_ranks_percent_average
				.add(Chen_node_influence_rank_30_40_percent_average
						/ Chen_valid_counter);
		Chen_node_influence_ranks_percent_average
				.add(Chen_node_influence_rank_40_50_percent_average
						/ Chen_valid_counter);
		Chen_node_influence_ranks_percent_average
				.add(Chen_node_influence_rank_50_60_percent_average
						/ Chen_valid_counter);
		Chen_node_influence_ranks_percent_average
				.add(Chen_node_influence_rank_60_70_percent_average
						/ Chen_valid_counter);
		Chen_node_influence_ranks_percent_average
				.add(Chen_node_influence_rank_70_80_percent_average
						/ Chen_valid_counter);
		Chen_node_influence_ranks_percent_average
				.add(Chen_node_influence_rank_80_90_percent_average
						/ Chen_valid_counter);
		Chen_node_influence_ranks_percent_average
				.add(Chen_node_influence_rank_90_100_percent_average
						/ Chen_valid_counter);

		LS_node_influence_ranks_percent_average
				.add(LS_node_influence_rank_0_10_percent_average
						/ LS_valid_counter);
		LS_node_influence_ranks_percent_average
				.add(LS_node_influence_rank_10_20_percent_average
						/ LS_valid_counter);
		LS_node_influence_ranks_percent_average
				.add(LS_node_influence_rank_20_30_percent_average
						/ LS_valid_counter);
		LS_node_influence_ranks_percent_average
				.add(LS_node_influence_rank_30_40_percent_average
						/ LS_valid_counter);
		LS_node_influence_ranks_percent_average
				.add(LS_node_influence_rank_40_50_percent_average
						/ LS_valid_counter);
		LS_node_influence_ranks_percent_average
				.add(LS_node_influence_rank_50_60_percent_average
						/ LS_valid_counter);
		LS_node_influence_ranks_percent_average
				.add(LS_node_influence_rank_60_70_percent_average
						/ LS_valid_counter);
		LS_node_influence_ranks_percent_average
				.add(LS_node_influence_rank_70_80_percent_average
						/ LS_valid_counter);
		LS_node_influence_ranks_percent_average
				.add(LS_node_influence_rank_80_90_percent_average
						/ LS_valid_counter);
		LS_node_influence_ranks_percent_average
				.add(LS_node_influence_rank_90_100_percent_average
						/ LS_valid_counter);

		LCD_node_influence_ranks_percent_average
				.add(LCD_node_influence_rank_0_10_percent_average
						/ LCD_valid_counter);
		LCD_node_influence_ranks_percent_average
				.add(LCD_node_influence_rank_10_20_percent_average
						/ LCD_valid_counter);
		LCD_node_influence_ranks_percent_average
				.add(LCD_node_influence_rank_20_30_percent_average
						/ LCD_valid_counter);
		LCD_node_influence_ranks_percent_average
				.add(LCD_node_influence_rank_30_40_percent_average
						/ LCD_valid_counter);
		LCD_node_influence_ranks_percent_average
				.add(LCD_node_influence_rank_40_50_percent_average
						/ LCD_valid_counter);
		LCD_node_influence_ranks_percent_average
				.add(LCD_node_influence_rank_50_60_percent_average
						/ LCD_valid_counter);
		LCD_node_influence_ranks_percent_average
				.add(LCD_node_influence_rank_60_70_percent_average
						/ LCD_valid_counter);
		LCD_node_influence_ranks_percent_average
				.add(LCD_node_influence_rank_70_80_percent_average
						/ LCD_valid_counter);
		LCD_node_influence_ranks_percent_average
				.add(LCD_node_influence_rank_80_90_percent_average
						/ LCD_valid_counter);
		LCD_node_influence_ranks_percent_average
				.add(LCD_node_influence_rank_90_100_percent_average
						/ LCD_valid_counter);

		// get_average_node_influence_rank_percent(
		// DC_node_influence_ranks_percent_average, DC_valid_counter);
		// get_average_node_influence_rank_percent(
		// KCC_node_influence_ranks_percent_average, KCC_valid_counter);
		// get_average_node_influence_rank_percent(
		// Clauset_node_influence_ranks_percent_average,
		// Clauset_valid_counter);
		// get_average_node_influence_rank_percent(
		// LWP_node_influence_ranks_percent_average, LWP_valid_counter);
		// get_average_node_influence_rank_percent(
		// Chen_node_influence_ranks_percent_average, Chen_valid_counter);
		// get_average_node_influence_rank_percent(
		// LS_node_influence_ranks_percent_average, LS_valid_counter);
		// get_average_node_influence_rank_percent(
		// LCD_node_influence_ranks_percent_average, LCD_valid_counter);

		// System.out.println(DC_node_influence_ranks_percent_average + ","
		// + DC_valid_counter);
		// System.out.println(KCC_node_influence_ranks_percent_average + ","
		// + KCC_valid_counter);
		// System.out.println(Clauset_node_influence_ranks_percent_average + ","
		// + Clauset_valid_counter);
		// System.out.println(LWP_node_influence_ranks_percent_average + ","
		// + LWP_valid_counter);
		// System.out.println(Chen_node_influence_ranks_percent_average + ","
		// + Chen_valid_counter);
		// System.out.println(LS_node_influence_ranks_percent_average + ","
		// + LS_valid_counter);
		// System.out.println(LCD_node_influence_ranks_percent_average + ","
		// + LCD_valid_counter);

		// for (int i = 0; i < 10; i++)
		// {
		// System.out.println(KCC_node_influence_ranks_percent_average);
		// System.out.println(KCC_ValidCounter);
		// }

		ArrayList<Measures> values_OneDataSet = new ArrayList<Measures>();
		Measures values_OneDataSet_DC = new Measures();
		values_OneDataSet_DC.NMI = DC_NMI_average;
		values_OneDataSet_DC.E = DC_E;
		// values_OneDataSet_DC.rate = DC_rate;
		values_OneDataSet_DC.rate_distinct = DC_rate_distinct;
		values_OneDataSet_DC.ED = DC_ed;
		values_OneDataSet_DC.rpf = new ArrayList<Double>();
		values_OneDataSet_DC.rpf.add(DC_R_average);
		values_OneDataSet_DC.rpf.add(DC_P_average);
		values_OneDataSet_DC.rpf.add(DC_F_average);
		values_OneDataSet_DC.node_rank_prcent
				.addAll(DC_node_influence_ranks_percent_average);

		Measures values_OneDataSet_KCC = new Measures();
		values_OneDataSet_KCC.NMI = KCC_NMI_average;
		values_OneDataSet_KCC.E = KCC_E;
		// values_OneDataSet_KCC.rate = KCC_rate;
		values_OneDataSet_KCC.rate_distinct = KCC_rate_distinct;
		values_OneDataSet_KCC.ED = KCC_ed;
		values_OneDataSet_KCC.rpf = new ArrayList<Double>();
		values_OneDataSet_KCC.rpf.add(KCC_R_average);
		values_OneDataSet_KCC.rpf.add(KCC_P_average);
		values_OneDataSet_KCC.rpf.add(KCC_F_average);
		values_OneDataSet_KCC.node_rank_prcent
				.addAll(KCC_node_influence_ranks_percent_average);

		Measures values_OneDataSet_Clauset = new Measures();
		values_OneDataSet_Clauset.NMI = Clauset_NMI_average;
		values_OneDataSet_Clauset.E = Clauset_E;
		// values_OneDataSet_Clauset.rate = Clauset_rate;
		values_OneDataSet_Clauset.rate_distinct = Clauset_rate_distinct;
		values_OneDataSet_Clauset.ED = Clauset_ed;
		values_OneDataSet_Clauset.rpf = new ArrayList<Double>();
		values_OneDataSet_Clauset.rpf.add(Clauset_R_average);
		values_OneDataSet_Clauset.rpf.add(Clauset_P_average);
		values_OneDataSet_Clauset.rpf.add(Clauset_F_average);
		values_OneDataSet_Clauset.node_rank_prcent
				.addAll(Clauset_node_influence_ranks_percent_average);

		Measures values_OneDataSet_LWP = new Measures();
		values_OneDataSet_LWP.NMI = LWP_NMI_average;
		values_OneDataSet_LWP.E = LWP_E;
		// values_OneDataSet_LWP.rate = LWP_rate;
		values_OneDataSet_LWP.rate_distinct = LWP_rate_distinct;
		values_OneDataSet_LWP.ED = LWP_ed;
		values_OneDataSet_LWP.rpf = new ArrayList<Double>();
		values_OneDataSet_LWP.rpf.add(LWP_R_average);
		values_OneDataSet_LWP.rpf.add(LWP_P_average);
		values_OneDataSet_LWP.rpf.add(LWP_F_average);
		values_OneDataSet_LWP.node_rank_prcent
				.addAll(LWP_node_influence_ranks_percent_average);

		Measures values_OneDataSet_Chen = new Measures();
		values_OneDataSet_Chen.NMI = Chen_NMI_average;
		values_OneDataSet_Chen.E = Chen_E;
		// values_OneDataSet_Chen.rate = Chen_rate;
		values_OneDataSet_Chen.rate_distinct = Chen_rate_distinct;
		values_OneDataSet_Chen.ED = Chen_ed;
		values_OneDataSet_Chen.rpf = new ArrayList<Double>();
		values_OneDataSet_Chen.rpf.add(Chen_R_average);
		values_OneDataSet_Chen.rpf.add(Chen_P_average);
		values_OneDataSet_Chen.rpf.add(Chen_F_average);
		values_OneDataSet_Chen.node_rank_prcent
				.addAll(Chen_node_influence_ranks_percent_average);

		Measures values_OneDataSet_LS = new Measures();
		values_OneDataSet_LS.NMI = LS_NMI_average;
		values_OneDataSet_LS.E = LS_E;
		// values_OneDataSet_LS.rate = LS_rate;
		values_OneDataSet_LS.rate_distinct = LS_rate_distinct;
		values_OneDataSet_LS.ED = LS_ed;
		values_OneDataSet_LS.rpf = new ArrayList<Double>();
		values_OneDataSet_LS.rpf.add(LS_R_average);
		values_OneDataSet_LS.rpf.add(LS_P_average);
		values_OneDataSet_LS.rpf.add(LS_F_average);
		values_OneDataSet_LS.node_rank_prcent
				.addAll(LS_node_influence_ranks_percent_average);

		Measures values_OneDataSet_LCD = new Measures();
		values_OneDataSet_LCD.NMI = LCD_NMI_average;
		values_OneDataSet_LCD.E = LCD_E;
		// values_OneDataSet_LCD.rate = LCD_rate;
		values_OneDataSet_LCD.rate_distinct = LCD_rate_distinct;
		values_OneDataSet_LCD.ED = LCD_ed;
		values_OneDataSet_LCD.rpf = new ArrayList<Double>();
		values_OneDataSet_LCD.rpf.add(LCD_R_average);
		values_OneDataSet_LCD.rpf.add(LCD_P_average);
		values_OneDataSet_LCD.rpf.add(LCD_F_average);
		values_OneDataSet_LCD.node_rank_prcent
				.addAll(LCD_node_influence_ranks_percent_average);

		values_OneDataSet.add(values_OneDataSet_DC);
		values_OneDataSet.add(values_OneDataSet_KCC);
		values_OneDataSet.add(values_OneDataSet_Clauset);
		values_OneDataSet.add(values_OneDataSet_LWP);
		values_OneDataSet.add(values_OneDataSet_Chen);
		values_OneDataSet.add(values_OneDataSet_LS);
		values_OneDataSet.add(values_OneDataSet_LCD);

		System.out
				.println("values_On_DataSet:(NMI    Effective    Distinct    ED    R    P    F;    Prcent)");
		for (int k = 0; k < values_OneDataSet.size(); k++)
		{
			if (k == 0)
			{
				System.out.print("DC:      ");
			} else if (k == 1)
			{
				System.out.print("KCC:     ");
			} else if (k == 2)
			{
				System.out.print("Clauset: ");
			} else if (k == 3)
			{
				System.out.print("LWP:     ");
			} else if (k == 4)
			{
				System.out.print("Chen:    ");
			} else if (k == 5)
			{
				System.out.print("LS:      ");
			} else if (k == 6)
			{
				System.out.print("LCD:     ");
			}
			// measures.rate = ((int) (measures.rate * 100)) / (double) 100;
			// System.out.print(measures.rate + " ");
			Measures measures = values_OneDataSet.get(k);
			// measures.NMI = ((int) (measures.NMI * 100)) / (double) 100;
			System.out.print(measures.NMI + " ");
			// measures.E = ((int) (measures.E * 100)) / (double) 100;
			System.out.print(measures.E + " ");
			// measures.rate_distinct = ((int) (measures.rate_distinct * 100))
			// / (double) 100;
			System.out.print(measures.rate_distinct + " ");
			// measures.ED = ((int) (measures.ED * 100)) / (double) 100;
			System.out.print(measures.ED + " ");
			for (Iterator<Double> iter_rpf = measures.rpf.iterator(); iter_rpf
					.hasNext();)
			{
				Double rpf = iter_rpf.next();
				// rpf = ((int) (rpf * 100)) / (double) 100;
				System.out.print(rpf + " ");
			}
			System.out.print(";");
			for (Iterator<Double> iter_prcent = measures.node_rank_prcent
					.iterator(); iter_prcent.hasNext();)
			{
				Double prcent = iter_prcent.next();
				// prcent = ((int) (prcent * 100)) / (double) 100;
				System.out.print(prcent + " ");
			}
			System.out.println();
		}
		return values_OneDataSet;
	}

	public void LFR_Tests() throws IOException
	{

		String lfrFilePath = "D:/Fifth/tests on artificial(lfr) networks/";
		String parameterFilePath = new String();
		parameterFilePath = "k(1000)/";
		int count_file = 11;
		for (int i = 11; i <= count_file; i++)
		{
			String valueFilePath = new String();

			/* NMI */
			double average_DC_NMI = 0;
			double average_KCC_NMI = 0;
			double average_Clauset_NMI = 0;
			double average_LWP_NMI = 0;
			double average_Chen_NMI = 0;
			double average_LS_NMI = 0;
			double average_LCD_NMI = 0;
			/* E */
			double average_DC_E = 0;
			double average_KCC_E = 0;
			double average_Clauset_E = 0;
			double average_LWP_E = 0;
			double average_Chen_E = 0;
			double average_LS_E = 0;
			double average_LCD_E = 0;
			/* rate_distinct */
			double average_DC_rate_distinct = 0;
			double average_KCC_rate_distinct = 0;
			double average_Clauset_rate_distinct = 0;
			double average_LWP_rate_distinct = 0;
			double average_Chen_rate_distinct = 0;
			double average_LS_rate_distinct = 0;
			double average_LCD_rate_distinct = 0;
			/* ED */
			double average_DC_ed = 0;
			double average_KCC_ed = 0;
			double average_Clauset_ed = 0;
			double average_LWP_ed = 0;
			double average_Chen_ed = 0;
			double average_LS_ed = 0;
			double average_LCD_ed = 0;
			/* rpf */
			ArrayList<Double> average_DC_rpf = new ArrayList<Double>();
			double average_DC_r = 0;
			double average_DC_p = 0;
			double average_DC_f = 0;
			ArrayList<Double> average_KCC_rpf = new ArrayList<Double>();
			double average_KCC_r = 0;
			double average_KCC_p = 0;
			double average_KCC_f = 0;
			ArrayList<Double> average_Clauset_rpf = new ArrayList<Double>();
			double average_Clauset_r = 0;
			double average_Clauset_p = 0;
			double average_Clauset_f = 0;
			ArrayList<Double> average_LWP_rpf = new ArrayList<Double>();
			double average_LWP_r = 0;
			double average_LWP_p = 0;
			double average_LWP_f = 0;
			ArrayList<Double> average_Chen_rpf = new ArrayList<Double>();
			double average_Chen_r = 0;
			double average_Chen_p = 0;
			double average_Chen_f = 0;
			ArrayList<Double> average_LS_rpf = new ArrayList<Double>();
			double average_LS_r = 0;
			double average_LS_p = 0;
			double average_LS_f = 0;
			ArrayList<Double> average_LCD_rpf = new ArrayList<Double>();
			double average_LCD_r = 0;
			double average_LCD_p = 0;
			double average_LCD_f = 0;

			ArrayList<Double> DC_node_influence_ranks_percent_average = new ArrayList<Double>();
			double DC_node_influence_rank_0_10_percent_average = 0;
			double DC_node_influence_rank_10_20_percent_average = 0;
			double DC_node_influence_rank_20_30_percent_average = 0;
			double DC_node_influence_rank_30_40_percent_average = 0;
			double DC_node_influence_rank_40_50_percent_average = 0;
			double DC_node_influence_rank_50_60_percent_average = 0;
			double DC_node_influence_rank_60_70_percent_average = 0;
			double DC_node_influence_rank_70_80_percent_average = 0;
			double DC_node_influence_rank_80_90_percent_average = 0;
			double DC_node_influence_rank_90_100_percent_average = 0;

			ArrayList<Double> KCC_node_influence_ranks_percent_average = new ArrayList<Double>();
			double KCC_node_influence_rank_0_10_percent_average = 0;
			double KCC_node_influence_rank_10_20_percent_average = 0;
			double KCC_node_influence_rank_20_30_percent_average = 0;
			double KCC_node_influence_rank_30_40_percent_average = 0;
			double KCC_node_influence_rank_40_50_percent_average = 0;
			double KCC_node_influence_rank_50_60_percent_average = 0;
			double KCC_node_influence_rank_60_70_percent_average = 0;
			double KCC_node_influence_rank_70_80_percent_average = 0;
			double KCC_node_influence_rank_80_90_percent_average = 0;
			double KCC_node_influence_rank_90_100_percent_average = 0;

			ArrayList<Double> Clauset_node_influence_ranks_percent_average = new ArrayList<Double>();
			double Clauset_node_influence_rank_0_10_percent_average = 0;
			double Clauset_node_influence_rank_10_20_percent_average = 0;
			double Clauset_node_influence_rank_20_30_percent_average = 0;
			double Clauset_node_influence_rank_30_40_percent_average = 0;
			double Clauset_node_influence_rank_40_50_percent_average = 0;
			double Clauset_node_influence_rank_50_60_percent_average = 0;
			double Clauset_node_influence_rank_60_70_percent_average = 0;
			double Clauset_node_influence_rank_70_80_percent_average = 0;
			double Clauset_node_influence_rank_80_90_percent_average = 0;
			double Clauset_node_influence_rank_90_100_percent_average = 0;

			ArrayList<Double> LWP_node_influence_ranks_percent_average = new ArrayList<Double>();
			double LWP_node_influence_rank_0_10_percent_average = 0;
			double LWP_node_influence_rank_10_20_percent_average = 0;
			double LWP_node_influence_rank_20_30_percent_average = 0;
			double LWP_node_influence_rank_30_40_percent_average = 0;
			double LWP_node_influence_rank_40_50_percent_average = 0;
			double LWP_node_influence_rank_50_60_percent_average = 0;
			double LWP_node_influence_rank_60_70_percent_average = 0;
			double LWP_node_influence_rank_70_80_percent_average = 0;
			double LWP_node_influence_rank_80_90_percent_average = 0;
			double LWP_node_influence_rank_90_100_percent_average = 0;

			ArrayList<Double> Chen_node_influence_ranks_percent_average = new ArrayList<Double>();
			double Chen_node_influence_rank_0_10_percent_average = 0;
			double Chen_node_influence_rank_10_20_percent_average = 0;
			double Chen_node_influence_rank_20_30_percent_average = 0;
			double Chen_node_influence_rank_30_40_percent_average = 0;
			double Chen_node_influence_rank_40_50_percent_average = 0;
			double Chen_node_influence_rank_50_60_percent_average = 0;
			double Chen_node_influence_rank_60_70_percent_average = 0;
			double Chen_node_influence_rank_70_80_percent_average = 0;
			double Chen_node_influence_rank_80_90_percent_average = 0;
			double Chen_node_influence_rank_90_100_percent_average = 0;

			ArrayList<Double> LS_node_influence_ranks_percent_average = new ArrayList<Double>();
			double LS_node_influence_rank_0_10_percent_average = 0;
			double LS_node_influence_rank_10_20_percent_average = 0;
			double LS_node_influence_rank_20_30_percent_average = 0;
			double LS_node_influence_rank_30_40_percent_average = 0;
			double LS_node_influence_rank_40_50_percent_average = 0;
			double LS_node_influence_rank_50_60_percent_average = 0;
			double LS_node_influence_rank_60_70_percent_average = 0;
			double LS_node_influence_rank_70_80_percent_average = 0;
			double LS_node_influence_rank_80_90_percent_average = 0;
			double LS_node_influence_rank_90_100_percent_average = 0;

			ArrayList<Double> LCD_node_influence_ranks_percent_average = new ArrayList<Double>();
			double LCD_node_influence_rank_0_10_percent_average = 0;
			double LCD_node_influence_rank_10_20_percent_average = 0;
			double LCD_node_influence_rank_20_30_percent_average = 0;
			double LCD_node_influence_rank_30_40_percent_average = 0;
			double LCD_node_influence_rank_40_50_percent_average = 0;
			double LCD_node_influence_rank_50_60_percent_average = 0;
			double LCD_node_influence_rank_60_70_percent_average = 0;
			double LCD_node_influence_rank_70_80_percent_average = 0;
			double LCD_node_influence_rank_80_90_percent_average = 0;
			double LCD_node_influence_rank_90_100_percent_average = 0;

			int file_number = 5;
			for (int j = 1; j <= file_number; j++)
			{
				valueFilePath = String.valueOf(i) + "/" + String.valueOf(j);
				String targetPath = lfrFilePath + parameterFilePath
						+ valueFilePath;
				String filePath = targetPath + ".txt";
				String filePathOfRealCommunities = targetPath + ".clu";
				// System.out.println(filePath + "(clu)");
				// System.out.println(filePathOfRealCommunities);
				Methods m = new Methods(filePath, filePathOfRealCommunities);
				ArrayList<Measures> all_algorithm_measure_values = m
						.runMethods(targetPath);
				for (int k = 0; k < all_algorithm_measure_values.size(); k++)
				{
					if (k == 0)
					{
						Measures measures = all_algorithm_measure_values.get(k);
						average_DC_NMI += measures.NMI;
						average_DC_E += measures.E;
						average_DC_rate_distinct += measures.rate_distinct;
						average_DC_ed += measures.ED;
						for (int count = 0; count < measures.rpf.size(); count++)
						{
							if (count == 0)
							{
								average_DC_r += measures.rpf.get(count);
							} else if (count == 1)
							{
								average_DC_p += measures.rpf.get(count);
							} else if (count == 2)
							{
								average_DC_f += measures.rpf.get(count);
							}
						}
						for (int count = 0; count < measures.node_rank_prcent
								.size(); count++)
						{
							if (count == 0)
							{
								DC_node_influence_rank_0_10_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 1)
							{
								DC_node_influence_rank_10_20_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 2)
							{
								DC_node_influence_rank_20_30_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 3)
							{
								DC_node_influence_rank_30_40_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 4)
							{
								DC_node_influence_rank_40_50_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 5)
							{
								DC_node_influence_rank_50_60_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 6)
							{
								DC_node_influence_rank_60_70_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 7)
							{
								DC_node_influence_rank_70_80_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 8)
							{
								DC_node_influence_rank_80_90_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 9)
							{
								DC_node_influence_rank_90_100_percent_average += measures.node_rank_prcent
										.get(count);
							}
						}
					} else if (k == 1)
					{
						Measures measures = all_algorithm_measure_values.get(k);
						average_KCC_NMI += measures.NMI;
						average_KCC_E += measures.E;
						average_KCC_rate_distinct += measures.rate_distinct;
						average_KCC_ed += measures.ED;
						for (int count = 0; count < measures.rpf.size(); count++)
						{
							if (count == 0)
							{
								average_KCC_r += measures.rpf.get(count);
							} else if (count == 1)
							{
								average_KCC_p += measures.rpf.get(count);
							} else if (count == 2)
							{
								average_KCC_f += measures.rpf.get(count);
							}
						}
						for (int count = 0; count < measures.node_rank_prcent
								.size(); count++)
						{
							if (count == 0)
							{
								KCC_node_influence_rank_0_10_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 1)
							{
								KCC_node_influence_rank_10_20_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 2)
							{
								KCC_node_influence_rank_20_30_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 3)
							{
								KCC_node_influence_rank_30_40_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 4)
							{
								KCC_node_influence_rank_40_50_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 5)
							{
								KCC_node_influence_rank_50_60_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 6)
							{
								KCC_node_influence_rank_60_70_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 7)
							{
								KCC_node_influence_rank_70_80_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 8)
							{
								KCC_node_influence_rank_80_90_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 9)
							{
								KCC_node_influence_rank_90_100_percent_average += measures.node_rank_prcent
										.get(count);
							}
						}
					} else if (k == 2)
					{
						Measures measures = all_algorithm_measure_values.get(k);
						average_Clauset_NMI += measures.NMI;
						average_Clauset_E += measures.E;
						average_Clauset_rate_distinct += measures.rate_distinct;
						average_Clauset_ed += measures.ED;
						for (int count = 0; count < measures.rpf.size(); count++)
						{
							if (count == 0)
							{
								average_Clauset_r += measures.rpf.get(count);
							} else if (count == 1)
							{
								average_Clauset_p += measures.rpf.get(count);
							} else if (count == 2)
							{
								average_Clauset_f += measures.rpf.get(count);
							}
						}
						for (int count = 0; count < measures.node_rank_prcent
								.size(); count++)
						{
							if (count == 0)
							{
								Clauset_node_influence_rank_0_10_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 1)
							{
								Clauset_node_influence_rank_10_20_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 2)
							{
								Clauset_node_influence_rank_20_30_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 3)
							{
								Clauset_node_influence_rank_30_40_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 4)
							{
								Clauset_node_influence_rank_40_50_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 5)
							{
								Clauset_node_influence_rank_50_60_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 6)
							{
								Clauset_node_influence_rank_60_70_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 7)
							{
								Clauset_node_influence_rank_70_80_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 8)
							{
								Clauset_node_influence_rank_80_90_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 9)
							{
								Clauset_node_influence_rank_90_100_percent_average += measures.node_rank_prcent
										.get(count);
							}
						}
					} else if (k == 3)
					{
						Measures measures = all_algorithm_measure_values.get(k);
						average_LWP_NMI += measures.NMI;
						average_LWP_E += measures.E;
						average_LWP_rate_distinct += measures.rate_distinct;
						average_LWP_ed += measures.ED;
						for (int count = 0; count < measures.rpf.size(); count++)
						{
							if (count == 0)
							{
								average_LWP_r += measures.rpf.get(count);
							} else if (count == 1)
							{
								average_LWP_p += measures.rpf.get(count);
							} else if (count == 2)
							{
								average_LWP_f += measures.rpf.get(count);
							}
						}
						for (int count = 0; count < measures.node_rank_prcent
								.size(); count++)
						{
							if (count == 0)
							{
								LWP_node_influence_rank_0_10_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 1)
							{
								LWP_node_influence_rank_10_20_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 2)
							{
								LWP_node_influence_rank_20_30_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 3)
							{
								LWP_node_influence_rank_30_40_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 4)
							{
								LWP_node_influence_rank_40_50_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 5)
							{
								LWP_node_influence_rank_50_60_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 6)
							{
								LWP_node_influence_rank_60_70_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 7)
							{
								LWP_node_influence_rank_70_80_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 8)
							{
								LWP_node_influence_rank_80_90_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 9)
							{
								LWP_node_influence_rank_90_100_percent_average += measures.node_rank_prcent
										.get(count);
							}
						}
					} else if (k == 4)
					{
						Measures measures = all_algorithm_measure_values.get(k);
						average_Chen_NMI += measures.NMI;
						average_Chen_E += measures.E;
						average_Chen_rate_distinct += measures.rate_distinct;
						average_Chen_ed += measures.ED;
						for (int count = 0; count < measures.rpf.size(); count++)
						{
							if (count == 0)
							{
								average_Chen_r += measures.rpf.get(count);
							} else if (count == 1)
							{
								average_Chen_p += measures.rpf.get(count);
							} else if (count == 2)
							{
								average_Chen_f += measures.rpf.get(count);
							}
						}
						for (int count = 0; count < measures.node_rank_prcent
								.size(); count++)
						{
							if (count == 0)
							{
								Chen_node_influence_rank_0_10_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 1)
							{
								Chen_node_influence_rank_10_20_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 2)
							{
								Chen_node_influence_rank_20_30_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 3)
							{
								Chen_node_influence_rank_30_40_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 4)
							{
								Chen_node_influence_rank_40_50_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 5)
							{
								Chen_node_influence_rank_50_60_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 6)
							{
								Chen_node_influence_rank_60_70_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 7)
							{
								Chen_node_influence_rank_70_80_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 8)
							{
								Chen_node_influence_rank_80_90_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 9)
							{
								Chen_node_influence_rank_90_100_percent_average += measures.node_rank_prcent
										.get(count);
							}
						}
					} else if (k == 5)
					{
						Measures measures = all_algorithm_measure_values.get(k);
						average_LS_NMI += measures.NMI;
						average_LS_E += measures.E;
						average_LS_rate_distinct += measures.rate_distinct;
						average_LS_ed += measures.ED;
						for (int count = 0; count < measures.rpf.size(); count++)
						{
							if (count == 0)
							{
								average_LS_r += measures.rpf.get(count);
							} else if (count == 1)
							{
								average_LS_p += measures.rpf.get(count);
							} else if (count == 2)
							{
								average_LS_f += measures.rpf.get(count);
							}
						}
						for (int count = 0; count < measures.node_rank_prcent
								.size(); count++)
						{
							if (count == 0)
							{
								LS_node_influence_rank_0_10_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 1)
							{
								LS_node_influence_rank_10_20_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 2)
							{
								LS_node_influence_rank_20_30_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 3)
							{
								LS_node_influence_rank_30_40_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 4)
							{
								LS_node_influence_rank_40_50_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 5)
							{
								LS_node_influence_rank_50_60_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 6)
							{
								LS_node_influence_rank_60_70_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 7)
							{
								LS_node_influence_rank_70_80_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 8)
							{
								LS_node_influence_rank_80_90_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 9)
							{
								LS_node_influence_rank_90_100_percent_average += measures.node_rank_prcent
										.get(count);
							}
						}
					} else if (k == 6)
					{
						Measures measures = all_algorithm_measure_values.get(k);
						average_LCD_NMI += measures.NMI;
						average_LCD_E += measures.E;
						average_LCD_rate_distinct += measures.rate_distinct;
						average_LCD_ed += measures.ED;
						for (int count = 0; count < measures.rpf.size(); count++)
						{
							if (count == 0)
							{
								average_LCD_r += measures.rpf.get(count);
							} else if (count == 1)
							{
								average_LCD_p += measures.rpf.get(count);
							} else if (count == 2)
							{
								average_LCD_f += measures.rpf.get(count);
							}
						}
						for (int count = 0; count < measures.node_rank_prcent
								.size(); count++)
						{
							if (count == 0)
							{
								LCD_node_influence_rank_0_10_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 1)
							{
								LCD_node_influence_rank_10_20_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 2)
							{
								LCD_node_influence_rank_20_30_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 3)
							{
								LCD_node_influence_rank_30_40_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 4)
							{
								LCD_node_influence_rank_40_50_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 5)
							{
								LCD_node_influence_rank_50_60_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 6)
							{
								LCD_node_influence_rank_60_70_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 7)
							{
								LCD_node_influence_rank_70_80_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 8)
							{
								LCD_node_influence_rank_80_90_percent_average += measures.node_rank_prcent
										.get(count);
							} else if (count == 9)
							{
								LCD_node_influence_rank_90_100_percent_average += measures.node_rank_prcent
										.get(count);
							}
						}
					}
				}
			}
			/* NMI */
			average_DC_NMI /= file_number;
			average_KCC_NMI /= file_number;
			average_Clauset_NMI /= file_number;
			average_LWP_NMI /= file_number;
			average_Chen_NMI /= file_number;
			average_LS_NMI /= file_number;
			average_LCD_NMI /= file_number;
			/* E */
			average_DC_E /= file_number;
			average_KCC_E /= file_number;
			average_Clauset_E /= file_number;
			average_LWP_E /= file_number;
			average_Chen_E /= file_number;
			average_LS_E /= file_number;
			average_LCD_E /= file_number;
			/* rate_distinct */
			average_DC_rate_distinct /= file_number;
			average_KCC_rate_distinct /= file_number;
			average_Clauset_rate_distinct /= file_number;
			average_LWP_rate_distinct /= file_number;
			average_Chen_rate_distinct /= file_number;
			average_LS_rate_distinct /= file_number;
			average_LCD_rate_distinct /= file_number;
			/* ED */
			average_DC_ed /= file_number;
			average_KCC_ed /= file_number;
			average_Clauset_ed /= file_number;
			average_LWP_ed /= file_number;
			average_Chen_ed /= file_number;
			average_LS_ed /= file_number;
			average_LCD_ed /= file_number;
			/* rpf */
			average_DC_r /= file_number;
			average_DC_p /= file_number;
			average_DC_f /= file_number;
			average_DC_rpf.add(average_DC_r);
			average_DC_rpf.add(average_DC_f);
			average_DC_rpf.add(average_DC_p);

			average_KCC_r /= file_number;
			average_KCC_p /= file_number;
			average_KCC_f /= file_number;
			average_KCC_rpf.add(average_KCC_r);
			average_KCC_rpf.add(average_KCC_f);
			average_KCC_rpf.add(average_KCC_p);

			average_Clauset_r /= file_number;
			average_Clauset_p /= file_number;
			average_Clauset_f /= file_number;
			average_Clauset_rpf.add(average_Clauset_r);
			average_Clauset_rpf.add(average_Clauset_f);
			average_Clauset_rpf.add(average_Clauset_p);

			average_LWP_r /= file_number;
			average_LWP_p /= file_number;
			average_LWP_f /= file_number;
			average_LWP_rpf.add(average_LWP_r);
			average_LWP_rpf.add(average_LWP_f);
			average_LWP_rpf.add(average_LWP_p);

			average_Chen_r /= file_number;
			average_Chen_p /= file_number;
			average_Chen_f /= file_number;
			average_Chen_rpf.add(average_Chen_r);
			average_Chen_rpf.add(average_Chen_f);
			average_Chen_rpf.add(average_Chen_p);

			average_LS_r /= file_number;
			average_LS_p /= file_number;
			average_LS_f /= file_number;
			average_LS_rpf.add(average_LS_r);
			average_LS_rpf.add(average_LS_f);
			average_LS_rpf.add(average_LS_p);

			average_LCD_r /= file_number;
			average_LCD_p /= file_number;
			average_LCD_f /= file_number;
			average_LCD_rpf.add(average_LCD_r);
			average_LCD_rpf.add(average_LCD_f);
			average_LCD_rpf.add(average_LCD_p);

			DC_node_influence_ranks_percent_average
					.add(DC_node_influence_rank_0_10_percent_average
							/ file_number);
			DC_node_influence_ranks_percent_average
					.add(DC_node_influence_rank_10_20_percent_average
							/ file_number);
			DC_node_influence_ranks_percent_average
					.add(DC_node_influence_rank_20_30_percent_average
							/ file_number);
			DC_node_influence_ranks_percent_average
					.add(DC_node_influence_rank_30_40_percent_average
							/ file_number);
			DC_node_influence_ranks_percent_average
					.add(DC_node_influence_rank_40_50_percent_average
							/ file_number);
			DC_node_influence_ranks_percent_average
					.add(DC_node_influence_rank_50_60_percent_average
							/ file_number);
			DC_node_influence_ranks_percent_average
					.add(DC_node_influence_rank_60_70_percent_average
							/ file_number);
			DC_node_influence_ranks_percent_average
					.add(DC_node_influence_rank_70_80_percent_average
							/ file_number);
			DC_node_influence_ranks_percent_average
					.add(DC_node_influence_rank_80_90_percent_average
							/ file_number);
			DC_node_influence_ranks_percent_average
					.add(DC_node_influence_rank_90_100_percent_average
							/ file_number);

			KCC_node_influence_ranks_percent_average
					.add(KCC_node_influence_rank_0_10_percent_average
							/ file_number);
			KCC_node_influence_ranks_percent_average
					.add(KCC_node_influence_rank_10_20_percent_average
							/ file_number);
			KCC_node_influence_ranks_percent_average
					.add(KCC_node_influence_rank_20_30_percent_average
							/ file_number);
			KCC_node_influence_ranks_percent_average
					.add(KCC_node_influence_rank_30_40_percent_average
							/ file_number);
			KCC_node_influence_ranks_percent_average
					.add(KCC_node_influence_rank_40_50_percent_average
							/ file_number);
			KCC_node_influence_ranks_percent_average
					.add(KCC_node_influence_rank_50_60_percent_average
							/ file_number);
			KCC_node_influence_ranks_percent_average
					.add(KCC_node_influence_rank_60_70_percent_average
							/ file_number);
			KCC_node_influence_ranks_percent_average
					.add(KCC_node_influence_rank_70_80_percent_average
							/ file_number);
			KCC_node_influence_ranks_percent_average
					.add(KCC_node_influence_rank_80_90_percent_average
							/ file_number);
			KCC_node_influence_ranks_percent_average
					.add(KCC_node_influence_rank_90_100_percent_average
							/ file_number);

			Clauset_node_influence_ranks_percent_average
					.add(Clauset_node_influence_rank_0_10_percent_average
							/ file_number);
			Clauset_node_influence_ranks_percent_average
					.add(Clauset_node_influence_rank_10_20_percent_average
							/ file_number);
			Clauset_node_influence_ranks_percent_average
					.add(Clauset_node_influence_rank_20_30_percent_average
							/ file_number);
			Clauset_node_influence_ranks_percent_average
					.add(Clauset_node_influence_rank_30_40_percent_average
							/ file_number);
			Clauset_node_influence_ranks_percent_average
					.add(Clauset_node_influence_rank_40_50_percent_average
							/ file_number);
			Clauset_node_influence_ranks_percent_average
					.add(Clauset_node_influence_rank_50_60_percent_average
							/ file_number);
			Clauset_node_influence_ranks_percent_average
					.add(Clauset_node_influence_rank_60_70_percent_average
							/ file_number);
			Clauset_node_influence_ranks_percent_average
					.add(Clauset_node_influence_rank_70_80_percent_average
							/ file_number);
			Clauset_node_influence_ranks_percent_average
					.add(Clauset_node_influence_rank_80_90_percent_average
							/ file_number);
			Clauset_node_influence_ranks_percent_average
					.add(Clauset_node_influence_rank_90_100_percent_average
							/ file_number);

			LWP_node_influence_ranks_percent_average
					.add(LWP_node_influence_rank_0_10_percent_average
							/ file_number);
			LWP_node_influence_ranks_percent_average
					.add(LWP_node_influence_rank_10_20_percent_average
							/ file_number);
			LWP_node_influence_ranks_percent_average
					.add(LWP_node_influence_rank_20_30_percent_average
							/ file_number);
			LWP_node_influence_ranks_percent_average
					.add(LWP_node_influence_rank_30_40_percent_average
							/ file_number);
			LWP_node_influence_ranks_percent_average
					.add(LWP_node_influence_rank_40_50_percent_average
							/ file_number);
			LWP_node_influence_ranks_percent_average
					.add(LWP_node_influence_rank_50_60_percent_average
							/ file_number);
			LWP_node_influence_ranks_percent_average
					.add(LWP_node_influence_rank_60_70_percent_average
							/ file_number);
			LWP_node_influence_ranks_percent_average
					.add(LWP_node_influence_rank_70_80_percent_average
							/ file_number);
			LWP_node_influence_ranks_percent_average
					.add(LWP_node_influence_rank_80_90_percent_average
							/ file_number);
			LWP_node_influence_ranks_percent_average
					.add(LWP_node_influence_rank_90_100_percent_average
							/ file_number);

			Chen_node_influence_ranks_percent_average
					.add(Chen_node_influence_rank_0_10_percent_average
							/ file_number);
			Chen_node_influence_ranks_percent_average
					.add(Chen_node_influence_rank_10_20_percent_average
							/ file_number);
			Chen_node_influence_ranks_percent_average
					.add(Chen_node_influence_rank_20_30_percent_average
							/ file_number);
			Chen_node_influence_ranks_percent_average
					.add(Chen_node_influence_rank_30_40_percent_average
							/ file_number);
			Chen_node_influence_ranks_percent_average
					.add(Chen_node_influence_rank_40_50_percent_average
							/ file_number);
			Chen_node_influence_ranks_percent_average
					.add(Chen_node_influence_rank_50_60_percent_average
							/ file_number);
			Chen_node_influence_ranks_percent_average
					.add(Chen_node_influence_rank_60_70_percent_average
							/ file_number);
			Chen_node_influence_ranks_percent_average
					.add(Chen_node_influence_rank_70_80_percent_average
							/ file_number);
			Chen_node_influence_ranks_percent_average
					.add(Chen_node_influence_rank_80_90_percent_average
							/ file_number);
			Chen_node_influence_ranks_percent_average
					.add(Chen_node_influence_rank_90_100_percent_average
							/ file_number);

			LS_node_influence_ranks_percent_average
					.add(LS_node_influence_rank_0_10_percent_average
							/ file_number);
			LS_node_influence_ranks_percent_average
					.add(LS_node_influence_rank_10_20_percent_average
							/ file_number);
			LS_node_influence_ranks_percent_average
					.add(LS_node_influence_rank_20_30_percent_average
							/ file_number);
			LS_node_influence_ranks_percent_average
					.add(LS_node_influence_rank_30_40_percent_average
							/ file_number);
			LS_node_influence_ranks_percent_average
					.add(LS_node_influence_rank_40_50_percent_average
							/ file_number);
			LS_node_influence_ranks_percent_average
					.add(LS_node_influence_rank_50_60_percent_average
							/ file_number);
			LS_node_influence_ranks_percent_average
					.add(LS_node_influence_rank_60_70_percent_average
							/ file_number);
			LS_node_influence_ranks_percent_average
					.add(LS_node_influence_rank_70_80_percent_average
							/ file_number);
			LS_node_influence_ranks_percent_average
					.add(LS_node_influence_rank_80_90_percent_average
							/ file_number);
			LS_node_influence_ranks_percent_average
					.add(LS_node_influence_rank_90_100_percent_average
							/ file_number);

			LCD_node_influence_ranks_percent_average
					.add(LCD_node_influence_rank_0_10_percent_average
							/ file_number);
			LCD_node_influence_ranks_percent_average
					.add(LCD_node_influence_rank_10_20_percent_average
							/ file_number);
			LCD_node_influence_ranks_percent_average
					.add(LCD_node_influence_rank_20_30_percent_average
							/ file_number);
			LCD_node_influence_ranks_percent_average
					.add(LCD_node_influence_rank_30_40_percent_average
							/ file_number);
			LCD_node_influence_ranks_percent_average
					.add(LCD_node_influence_rank_40_50_percent_average
							/ file_number);
			LCD_node_influence_ranks_percent_average
					.add(LCD_node_influence_rank_50_60_percent_average
							/ file_number);
			LCD_node_influence_ranks_percent_average
					.add(LCD_node_influence_rank_60_70_percent_average
							/ file_number);
			LCD_node_influence_ranks_percent_average
					.add(LCD_node_influence_rank_70_80_percent_average
							/ file_number);
			LCD_node_influence_ranks_percent_average
					.add(LCD_node_influence_rank_80_90_percent_average
							/ file_number);
			LCD_node_influence_ranks_percent_average
					.add(LCD_node_influence_rank_90_100_percent_average
							/ file_number);

			ArrayList<Measures> average_values_OneDataSet = new ArrayList<Measures>();
			Measures values_OneDataSet_DC = new Measures();
			values_OneDataSet_DC.NMI = average_DC_NMI;
			values_OneDataSet_DC.E = average_DC_E;
			// values_OneDataSet_DC.rate = DC_rate;
			values_OneDataSet_DC.rate_distinct = average_DC_rate_distinct;
			values_OneDataSet_DC.ED = average_DC_ed;
			values_OneDataSet_DC.rpf = new ArrayList<Double>();
			values_OneDataSet_DC.rpf.add(average_DC_r);
			values_OneDataSet_DC.rpf.add(average_DC_p);
			values_OneDataSet_DC.rpf.add(average_DC_f);
			values_OneDataSet_DC.node_rank_prcent
					.addAll(DC_node_influence_ranks_percent_average);

			Measures values_OneDataSet_KCC = new Measures();
			values_OneDataSet_KCC.NMI = average_KCC_NMI;
			values_OneDataSet_KCC.E = average_KCC_E;
			// values_OneDataSet_KCC.rate = KCC_rate;
			values_OneDataSet_KCC.rate_distinct = average_KCC_rate_distinct;
			values_OneDataSet_KCC.ED = average_KCC_ed;
			values_OneDataSet_KCC.rpf = new ArrayList<Double>();
			values_OneDataSet_KCC.rpf.add(average_KCC_r);
			values_OneDataSet_KCC.rpf.add(average_KCC_p);
			values_OneDataSet_KCC.rpf.add(average_KCC_f);
			values_OneDataSet_KCC.node_rank_prcent
					.addAll(KCC_node_influence_ranks_percent_average);

			Measures values_OneDataSet_Clauset = new Measures();
			values_OneDataSet_Clauset.NMI = average_Clauset_NMI;
			values_OneDataSet_Clauset.E = average_Clauset_E;
			// values_OneDataSet_Clauset.rate = Clauset_rate;
			values_OneDataSet_Clauset.rate_distinct = average_Clauset_rate_distinct;
			values_OneDataSet_Clauset.ED = average_Clauset_ed;
			values_OneDataSet_Clauset.rpf = new ArrayList<Double>();
			values_OneDataSet_Clauset.rpf.add(average_Clauset_r);
			values_OneDataSet_Clauset.rpf.add(average_Clauset_p);
			values_OneDataSet_Clauset.rpf.add(average_Clauset_f);
			values_OneDataSet_Clauset.node_rank_prcent
					.addAll(Clauset_node_influence_ranks_percent_average);

			Measures values_OneDataSet_LWP = new Measures();
			values_OneDataSet_LWP.NMI = average_LWP_NMI;
			values_OneDataSet_LWP.E = average_LWP_E;
			// values_OneDataSet_LWP.rate = LWP_rate;
			values_OneDataSet_LWP.rate_distinct = average_LWP_rate_distinct;
			values_OneDataSet_LWP.ED = average_LWP_ed;
			values_OneDataSet_LWP.rpf = new ArrayList<Double>();
			values_OneDataSet_LWP.rpf.add(average_LWP_r);
			values_OneDataSet_LWP.rpf.add(average_LWP_p);
			values_OneDataSet_LWP.rpf.add(average_LWP_f);
			values_OneDataSet_LWP.node_rank_prcent
					.addAll(LWP_node_influence_ranks_percent_average);

			Measures values_OneDataSet_Chen = new Measures();
			values_OneDataSet_Chen.NMI = average_Chen_NMI;
			values_OneDataSet_Chen.E = average_Chen_E;
			// values_OneDataSet_Chen.rate = Chen_rate;
			values_OneDataSet_Chen.rate_distinct = average_Chen_rate_distinct;
			values_OneDataSet_Chen.ED = average_Chen_ed;
			values_OneDataSet_Chen.rpf = new ArrayList<Double>();
			values_OneDataSet_Chen.rpf.add(average_Chen_r);
			values_OneDataSet_Chen.rpf.add(average_Chen_p);
			values_OneDataSet_Chen.rpf.add(average_Chen_f);
			values_OneDataSet_Chen.node_rank_prcent
					.addAll(Chen_node_influence_ranks_percent_average);

			Measures values_OneDataSet_LS = new Measures();
			values_OneDataSet_LS.NMI = average_LS_NMI;
			values_OneDataSet_LS.E = average_LS_E;
			// values_OneDataSet_LS.rate = LS_rate;
			values_OneDataSet_LS.rate_distinct = average_LS_rate_distinct;
			values_OneDataSet_LS.ED = average_LS_ed;
			values_OneDataSet_LS.rpf = new ArrayList<Double>();
			values_OneDataSet_LS.rpf.add(average_LS_r);
			values_OneDataSet_LS.rpf.add(average_LS_p);
			values_OneDataSet_LS.rpf.add(average_LS_f);
			values_OneDataSet_LS.node_rank_prcent
					.addAll(LS_node_influence_ranks_percent_average);

			Measures values_OneDataSet_LCD = new Measures();
			values_OneDataSet_LCD.NMI = average_LCD_NMI;
			values_OneDataSet_LCD.E = average_LCD_E;
			// values_OneDataSet_LCD.rate = LCD_rate;
			values_OneDataSet_LCD.rate_distinct = average_LCD_rate_distinct;
			values_OneDataSet_LCD.ED = average_LCD_ed;
			values_OneDataSet_LCD.rpf = new ArrayList<Double>();
			values_OneDataSet_LCD.rpf.add(average_LCD_r);
			values_OneDataSet_LCD.rpf.add(average_LCD_p);
			values_OneDataSet_LCD.rpf.add(average_LCD_f);
			values_OneDataSet_LCD.node_rank_prcent
					.addAll(LCD_node_influence_ranks_percent_average);

			average_values_OneDataSet.add(values_OneDataSet_DC);
			average_values_OneDataSet.add(values_OneDataSet_KCC);
			average_values_OneDataSet.add(values_OneDataSet_Clauset);
			average_values_OneDataSet.add(values_OneDataSet_LWP);
			average_values_OneDataSet.add(values_OneDataSet_Chen);
			average_values_OneDataSet.add(values_OneDataSet_LS);
			average_values_OneDataSet.add(values_OneDataSet_LCD);

			System.out
					.println("average_values_On_DataSet:(NMI    Effective    Distinct    ED    R    P    F;    Prcent)");
			for (int k = 0; k < average_values_OneDataSet.size(); k++)
			{
				if (k == 0)
				{
					System.out.print("DC:      ");
				} else if (k == 1)
				{
					System.out.print("KCC:     ");
				} else if (k == 2)
				{
					System.out.print("Clauset: ");
				} else if (k == 3)
				{
					System.out.print("LWP:     ");
				} else if (k == 4)
				{
					System.out.print("Chen:    ");
				} else if (k == 5)
				{
					System.out.print("LS:      ");
				} else if (k == 6)
				{
					System.out.print("LCD:     ");
				}
				// measures.rate = ((int) (measures.rate * 100)) / (double) 100;
				// System.out.print(measures.rate + " ");
				Measures measures = average_values_OneDataSet.get(k);
				// measures.NMI = ((int) (measures.NMI * 100)) / (double) 100;
				System.out.print(measures.NMI + " ");
				// measures.E = ((int) (measures.E * 100)) / (double) 100;
				System.out.print(measures.E + " ");
				// measures.rate_distinct = ((int) (measures.rate_distinct *
				// 100))
				// / (double) 100;
				System.out.print(measures.rate_distinct + " ");
				// measures.ED = ((int) (measures.ED * 100)) / (double) 100;
				System.out.print(measures.ED + " ");
				for (Iterator<Double> iter_rpf = measures.rpf.iterator(); iter_rpf
						.hasNext();)
				{
					Double rpf = iter_rpf.next();
					// rpf = ((int) (rpf * 100)) / (double) 100;
					System.out.print(rpf + " ");
				}
				System.out.print(";");
				for (Iterator<Double> iter_prcent = measures.node_rank_prcent
						.iterator(); iter_prcent.hasNext();)
				{
					Double prcent = iter_prcent.next();
					// prcent = ((int) (prcent * 100)) / (double) 100;
					System.out.print(prcent + " ");
				}
				System.out.println();
			}
		}
	}

	public static void main(String[] args) throws IOException
	{
		/**************************************************************/
		String filePath = "D:/Fifth/tests on real word networks/krebs' book.txt";// changed
		// karate,krebs' book,football,dolphin
		/* test on real networks */
		String filePathOfRealCommunities = "D:/Fifth/tests on real word networks/krebs' book.clu";// changed
		String targetPath = "D:/Fifth/karate";
		Methods m = new Methods(filePath, filePathOfRealCommunities);
		m.runMethods(targetPath);
		/**************************************************************/
		// Methods m = new Methods();
		// m.LFR_Tests();
		/**************************************************************/
	}
}
