package com.comparision.vi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

public class VIAlgorithm
{
	public int node_size;
	public int link_size;
	public int numberOfCommunities;
	public ArrayList<ArrayList<Integer>> adjacencyTable;

	public VIAlgorithm(int n, int m,
			ArrayList<ArrayList<Integer>> adjacencyTable)
	{
		this.node_size = n;
		this.link_size = m;
		this.numberOfCommunities = 0;
		this.adjacencyTable = adjacencyTable;
		// System.out.println(n);
		// System.out.println(m);
		// for(Iterator<ArrayList<Integer>> iter =
		// this.adjacencyTable.iterator();iter.hasNext();)
		// {
		// System.out.println(iter.next());
		// }
	}

	/* (Tested) Get node neighbors */
	public TreeSet<Integer> get_node_neighbors(int node_id)
	{
		TreeSet<Integer> neighbors = new TreeSet<Integer>();
		neighbors.addAll(adjacencyTable.get(node_id));
		neighbors.remove((Integer) node_id);
		return neighbors;
	}

	/* (Tested) Get community neighbors */
	public TreeSet<Integer> get_community_neighbors(
			TreeSet<Integer> detected_community)
	{
		TreeSet<Integer> community_neighbors = new TreeSet<Integer>();
		for (Iterator<Integer> iter_member = detected_community.iterator(); iter_member
				.hasNext();)
		{
			Integer member = iter_member.next();
			TreeSet<Integer> neighbors = get_node_neighbors(member);
			for (Iterator<Integer> iter_neighbor = neighbors.iterator(); iter_neighbor
					.hasNext();)
			{
				Integer neighbor = iter_neighbor.next();
				if (!detected_community.contains(neighbor))
				{
					community_neighbors.add(neighbor);
				}
			}
		}
		return community_neighbors;
	}

	/* (Tested) Get k_in_G and k_out_G */
	public ArrayList<Integer> get_k_in_and_out_G(
			TreeSet<Integer> detected_community)
	{
		ArrayList<Integer> k_in_and_out_G = new ArrayList<Integer>();
		int k_in_G = 0;
		int k_out_G = 0;
		for (Iterator<Integer> iter_member = detected_community.iterator(); iter_member
				.hasNext();)
		{
			Integer member = iter_member.next();
			TreeSet<Integer> neighbors = get_node_neighbors(member);
			for (Iterator<Integer> iter_neighbor = neighbors.iterator(); iter_neighbor
					.hasNext();)
			{
				Integer neighbor = iter_neighbor.next();
				if (detected_community.contains(neighbor))
				{
					k_in_G++;
				} else
				{
					k_out_G++;
				}
			}
		}
		// System.out.println("k_in_G: " + k_in_G);
		// System.out.println("k_out_G: " + k_out_G);
		k_in_and_out_G.add(k_in_G);
		k_in_and_out_G.add(k_out_G);
		return k_in_and_out_G;
	}

	/* (Tested) Calculate healthy degree */
	public double calculate_healthy_degree(TreeSet<Integer> detected_community)
	{
		double healthy_degree = 0;
		ArrayList<Integer> k_in_and_out_G = get_k_in_and_out_G(detected_community);
		double k_in_G = k_in_and_out_G.get(0);
		double k_out_G = k_in_and_out_G.get(1);
		healthy_degree = k_in_G / (k_in_G + k_out_G);
		// System.out.println("healthy_degree: " + healthy_degree);
		return healthy_degree;
	}

	/* Calculate influence degree */
	public double calculate_influence_degree(int seed_id, int candidate_node_id)
	{
		double influence_degree = 0;
		int level_1_path_count = 0;
		int level_2_path_count = 0;
		int level_3_path_count = 0;
		/* level 1 */
		TreeSet<Integer> level_1_nodes_all = get_node_neighbors(seed_id);
		if (level_1_nodes_all.contains(candidate_node_id))
		{
			level_1_path_count++;
		}
		/* level 2 */
		ArrayList<Integer> level_2_nodes_all = new ArrayList<Integer>();
		for (Iterator<Integer> iter_level_1_node = level_1_nodes_all.iterator(); iter_level_1_node
				.hasNext();)
		{
			Integer level_1_node = iter_level_1_node.next();
			TreeSet<Integer> level_2_nodes = get_node_neighbors(level_1_node);
			level_2_nodes_all.addAll(level_2_nodes);
			if (level_2_nodes.contains(candidate_node_id))
			{
				level_2_path_count++;
			}
		}
		/* level 3 */
		for (Iterator<Integer> iter_level_2_node = level_2_nodes_all.iterator(); iter_level_2_node
				.hasNext();)
		{
			Integer level_2_node = iter_level_2_node.next();
			TreeSet<Integer> level_3_nodes = get_node_neighbors(level_2_node);
			if (level_3_nodes.contains(candidate_node_id))
			{
				level_3_path_count++;
			}
		}
		// System.out.println(level_1_path_count);
		// System.out.println(level_2_path_count);
		// System.out.println(level_3_path_count);
		influence_degree = level_1_path_count + level_2_path_count / 4.0
				+ level_3_path_count / 9.0;
		// System.out.println(influence_degree);
		return influence_degree;
	}

	/* Get detected community */
	public TreeSet<Integer> get_detected_community(int seed_id)
	{
		TreeSet<Integer> detected_community = new TreeSet<Integer>();
		/* initial community */
		detected_community.add(seed_id);
		double healthy_degree = calculate_healthy_degree(detected_community);
		while (true)
		{
			/* addition step */
			TreeSet<Integer> community_neighbors = get_community_neighbors(detected_community);
			ArrayList<HealthyDegreeNode> healthy_degree_nodes = new ArrayList<HealthyDegreeNode>();
			for (Iterator<Integer> iter_community_neighbor = community_neighbors
					.iterator(); iter_community_neighbor.hasNext();)
			{
				Integer community_neighbor = iter_community_neighbor.next();
				TreeSet<Integer> detected_community_temp = new TreeSet<Integer>();
				detected_community_temp.addAll(detected_community);
				detected_community_temp.add(community_neighbor);
				double healthy_degree_temp = calculate_healthy_degree(detected_community_temp);
				double healthy_degree_deta = healthy_degree_temp
						- healthy_degree;
				if (healthy_degree_deta > 0)
				{
					HealthyDegreeNode healthy_degree_node = new HealthyDegreeNode(
							community_neighbor, healthy_degree_deta);
					healthy_degree_nodes.add(healthy_degree_node);
				}
			}
			if (!healthy_degree_nodes.isEmpty())
			{

				int node_with_max_healthy_influence_degree = -1;
				double max_healthy_influence_degree = 0;
				for (Iterator<HealthyDegreeNode> iter_healthy_degree_node = healthy_degree_nodes
						.iterator(); iter_healthy_degree_node.hasNext();)
				{
					HealthyDegreeNode healthy_degree_node = iter_healthy_degree_node
							.next();
					/* change node's healthy_degree to healthy_influence_degree */
					double influence_degree = calculate_influence_degree(
							seed_id, healthy_degree_node.id);
					double healthy_influence_degree = healthy_degree_node.healthy_degree
							* Math.pow(2, influence_degree);
					// System.out.println(healthy_degree_node);
					if (healthy_influence_degree > max_healthy_influence_degree)
					{
						max_healthy_influence_degree = healthy_influence_degree;
						node_with_max_healthy_influence_degree = healthy_degree_node.id;
					}
				}
				// System.out.println(node_with_max_healthy_influence_degree +
				// ","
				// + max_healthy_influence_degree);
				detected_community.add(node_with_max_healthy_influence_degree);
				healthy_degree = calculate_healthy_degree(detected_community);
			} else
			{
				break;
			}
			// System.out.println(detected_community);
			/* deletion step */
			int node_with_min_healthy_degree = -1;
			double min_healthy_degree = 0;
			for (Iterator<Integer> iter_community_member = detected_community
					.iterator(); iter_community_member.hasNext();)
			{
				Integer community_member = iter_community_member.next();
				TreeSet<Integer> detected_community_temp = new TreeSet<Integer>();
				detected_community_temp.addAll(detected_community);
				detected_community_temp.remove(community_member);
				double healthy_degree_temp = calculate_healthy_degree(detected_community_temp);
				double healthy_degree_deta = healthy_degree
						- healthy_degree_temp;
				if (healthy_degree_deta < 0)
				{
					min_healthy_degree = healthy_degree_deta;
					node_with_min_healthy_degree = community_member;
				}
			}
			if (node_with_min_healthy_degree != -1)
			{
				detected_community.remove(node_with_min_healthy_degree);
				// System.out.println(node_with_min_healthy_degree+","+healthy_degree+","+min_healthy_degree);
				healthy_degree = calculate_healthy_degree(detected_community);
			}
		}
		// System.out.println(seed_id + "," + detected_community);
		// if (!detected_community.contains(seed_id))
		// {
		// System.out.println(false);
		// }
		return detected_community;
	}

	/* run vi algorithm */
	public ArrayList<ArrayList<Integer>> run_vi(int seed_id)
	{
		TreeSet<Integer> detected_community = new TreeSet<Integer>();
		detected_community = get_detected_community(seed_id);
		if (detected_community.contains((Integer) seed_id))
		{
			// System.out.println("detected_community: " + detected_community);
			ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> detected_community_list = new ArrayList<Integer>();
			{
				detected_community_list.addAll(detected_community);
			}
			ArrayList<Integer> rest_nodes = new ArrayList<Integer>();
			for (int i = 0; i < this.node_size; i++)
			{
				if (!detected_community.contains(i))
				{
					rest_nodes.add(i);
				}
			}
			partitionF.add(detected_community_list);
			partitionF.add(rest_nodes);
			// System.out.println(partitionF.get(0));
			// System.out.println(partitionF.get(1));
			// System.out.println();
			return partitionF;
		} else
		{
			ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> community_1 = new ArrayList<Integer>();
			ArrayList<Integer> community_2 = new ArrayList<Integer>();
			for (int i = 0; i < this.node_size; i++)
			{
				community_2.add(i);
			}
			partitionF.add(community_1);
			partitionF.add(community_2);
			// System.out.println(false);
			return partitionF;
		}
	}

	public static void main(String[] args) throws IOException
	{
		String file_path = "D:/Fifth/tests on real word networks/";
		String file_name = "dolphin";
		// karate;dolphin;football;krebsbook;dblp;amazon;youtube
		// String network = "_network.txt";
		// karate_network;dolphin_network;football_network;krebsbook_network
		// dblp_network;amazon_network;youtube_network
		String community = "_community.txt";
		// karate_community;dolphin_community;football_community;krebsbook_community
		// dblp_community;amazon_community;youtube_community
		String table = "_table.txt";
		// karate_table;dolphin_table;football_table;krebsbook_table
		// dblp_table;amazon_table;youtube_table
		// String file_path_network = file_path + file_name + network;
		String file_path_community = file_path + file_name + community;
		String file_path_table = file_path + file_name + table;

		DataReaderRealWorldNetwork data_reader = new DataReaderRealWorldNetwork(
				file_path_community, file_path_table);
		data_reader.read_realworld_network();

		VIAlgorithm vi = new VIAlgorithm(data_reader.n, data_reader.m,
				data_reader.adjacencyTable);
		// vi.get_detected_community(5);
		// for (int i = 0; i < vi.node_size; i++)
		// {
		// vi.run_vi(i);
		// }
	}
}
