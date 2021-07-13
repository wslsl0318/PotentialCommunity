package com.comparision.chen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

public class ChenAlgorithm
{
	public int node_size;
	public int link_size;
	public int numberOfCommunities;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public TreeSet<Integer> D;// community
	public TreeSet<Integer> C;// nodes in community without outer links
	public TreeSet<Integer> B;// nodes in community with outer links
	public TreeSet<Integer> S;// nodes out of community with inner links

	public ChenAlgorithm(int n, int m,
			ArrayList<ArrayList<Integer>> adjacencyTable)
	{
		this.node_size = n;
		this.link_size = m;
		this.numberOfCommunities = 0;
		this.adjacencyTable = adjacencyTable;
		this.D = new TreeSet<Integer>();
		this.C = new TreeSet<Integer>();
		this.B = new TreeSet<Integer>();
		this.S = new TreeSet<Integer>();
		// System.out.println("n: " + n);
		// System.out.println("m: " + m);
		// System.out.println("adjacencyTable");
		// for (Iterator<ArrayList<Integer>> iter = adjacencyTable.iterator();
		// iter
		// .hasNext();)
		// {
		// System.out.println(iter.next());
		// }
	}

	/* (Tested) Get node neighbors */
	public ArrayList<Integer> get_node_neighbors(int node_id)
	{
		ArrayList<Integer> node_neighbors = new ArrayList<Integer>();
		ArrayList<Integer> nodes_in_line = this.adjacencyTable.get(node_id);
		node_neighbors.addAll(nodes_in_line);
		node_neighbors.remove(0);
		return node_neighbors;
	}

	/* (Tested) initial node sets D, C, B, S */
	public void initial_node_sets(int seed_id)
	{
		D.clear();
		B.clear();
		S.clear();
		D.add(seed_id);
		B.add(seed_id);
		ArrayList<Integer> node_neighbors = get_node_neighbors(seed_id);
		S.addAll(node_neighbors);
	}

	/* (Tested) calculate Lin */
	public double calculate_L_in(TreeSet<Integer> D)
	{
		double L_in = 0;
		int number_of_inner_links = 0;
		for (Iterator<Integer> iter = D.iterator(); iter.hasNext();)
		{
			Integer member_in_D = iter.next();
			ArrayList<Integer> node_neighbors = get_node_neighbors(member_in_D);
			for (Iterator<Integer> iter_neighbor = node_neighbors.iterator(); iter_neighbor
					.hasNext();)
			{
				if (D.contains(iter_neighbor.next()))
				{
					number_of_inner_links++;
				}
			}
		}
		L_in = (double) number_of_inner_links / D.size();
		return L_in;
	}

	/* (Tested) calculate Lex */
	public double calculate_L_ex(TreeSet<Integer> B, TreeSet<Integer> S)
	{
		double L_ex = 0;
		int number_of_outer_links = 0;
		for (Iterator<Integer> iter = B.iterator(); iter.hasNext();)
		{
			Integer member_in_B = iter.next();
			ArrayList<Integer> node_neighbors = get_node_neighbors(member_in_B);
			for (Iterator<Integer> iter_neighbor = node_neighbors.iterator(); iter_neighbor
					.hasNext();)
			{
				if (S.contains(iter_neighbor.next()))
				{
					number_of_outer_links++;
				}
			}
		}
		L_ex = (double) number_of_outer_links / B.size();
		return L_ex;
	}

	/* (Tested) calculate L */
	public double calculate_L(double L_in, double L_ex)
	{
		double L = 0;
		L = L_in / L_ex;
		return L;
	}

	/* (Tested) is exists a neighbor not in D */
	public boolean is_in_B(TreeSet<Integer> D, int node_id)
	{
		ArrayList<Integer> node_neighbors = get_node_neighbors(node_id);
		for (Iterator<Integer> iter = node_neighbors.iterator(); iter.hasNext();)
		{
			Integer neighbor = iter.next();
			if (!D.contains(neighbor))
			{
				return true;
			}
		}
		return false;
	}

	/* (Tested) update D */
	public TreeSet<Integer> update_D_add_node(TreeSet<Integer> D, int node_id)
	{
		TreeSet<Integer> D_temp = new TreeSet<Integer>();
		D_temp.addAll(D);
		D_temp.add(node_id);
		return D_temp;
	}

	/* (Tested) update D */
	public TreeSet<Integer> update_D_delete_node(TreeSet<Integer> D, int node_id)
	{
		TreeSet<Integer> D_temp = new TreeSet<Integer>();
		D_temp.addAll(D);
		D_temp.remove(node_id);
		return D_temp;
	}

	/* (Tested) update B */
	public TreeSet<Integer> update_B(TreeSet<Integer> D)
	{
		TreeSet<Integer> B_temp = new TreeSet<Integer>();
		for (Iterator<Integer> iter = D.iterator(); iter.hasNext();)
		{
			Integer member_in_D = iter.next();
			if (is_in_B(D, member_in_D))
			{
				B_temp.add(member_in_D);
			}
		}
		return B_temp;
	}

	/* (Tested) update S */
	public TreeSet<Integer> update_S(TreeSet<Integer> D)
	{
		TreeSet<Integer> S_temp = new TreeSet<Integer>();
		for (Iterator<Integer> iter = D.iterator(); iter.hasNext();)
		{
			Integer member_in_D = iter.next();
			ArrayList<Integer> node_neighbors = get_node_neighbors(member_in_D);
			for (Iterator<Integer> iter_neighbor = node_neighbors.iterator(); iter_neighbor
					.hasNext();)
			{
				Integer neighbor = iter_neighbor.next();
				if (!D.contains(neighbor))
				{
					S_temp.add(neighbor);
				}
			}
		}
		return S_temp;
	}

	/* (Tested) discovery phase */
	public void discovery_phase(int seed_id)
	{
		initial_node_sets(seed_id);
		do
		{
			double L_in = calculate_L_in(D);
			double L_ex = calculate_L_ex(B, S);
			double L = calculate_L(L_in, L_ex);
			/*
			 * candidate nodes: nodes cause L_temp>L satisfy condition 1 or 3
			 */
			TreeSet<CandidateNode> candidate_nodes = new TreeSet<CandidateNode>(
					new CandidateNode());
			for (Iterator<Integer> iter_member_in_S = S.iterator(); iter_member_in_S
					.hasNext();)
			{
				Integer member_in_S = iter_member_in_S.next();
				TreeSet<Integer> D_temp = update_D_add_node(D, member_in_S);
				TreeSet<Integer> B_temp = update_B(D_temp);
				TreeSet<Integer> S_temp = update_S(D_temp);
				double L_in_temp = calculate_L_in(D_temp);
				double L_ex_temp = calculate_L_ex(B_temp, S_temp);
				double L_temp = calculate_L(L_in_temp, L_ex_temp);
				if (L_temp > L
						&& ((L_in_temp > L_in && L_ex_temp < L_ex) || (L_in_temp > L_in && L_ex_temp > L_ex)))
				{
					CandidateNode candidate_node = new CandidateNode(
							member_in_S, L_temp);
					candidate_nodes.add(candidate_node);
				}
				// System.out.println("*******************");
				// System.out.println("D: " + D);
				// System.out.println("D_temp: " + D_temp);
				// System.out.println("B_temp: " + B_temp);
				// System.out.println("S_temp: " + S_temp);
				// System.out.println("L_in_temp: " + L_in_temp + "," +
				// "L_ex_temp: "
				// + L_ex_temp + "," + "L_temp: " + L_temp);
				// System.out.println("*******************");
			}
			// System.out.println("candidate_nodes");
			// for (Iterator<CandidateNode> iter = candidate_nodes.iterator();
			// iter
			// .hasNext();)
			// {
			// System.out.println(iter.next());
			// }
			if (!candidate_nodes.isEmpty())
			{
				CandidateNode first_node = candidate_nodes.first();
				D = update_D_add_node(D, first_node.id);
				B = update_B(D);
				S = update_S(D);
			} else
			{
				break;
			}
		} while (true);
		// System.out.println("discovery_phase");
		// System.out.println("D_new: " + D);
		// System.out.println("B_new: " + B);
		// System.out.println("S_new: " + S);
	}

	/* (Tested) examination phase */
	public void examination_phase()
	{
		// do
		// {
		TreeSet<Integer> node_to_be_removed = new TreeSet<Integer>();
		for (Iterator<Integer> iter = D.iterator(); iter.hasNext();)
		{
			Integer member_in_D = iter.next();
			// remove
			TreeSet<Integer> D_temp_remove = update_D_delete_node(D,
					member_in_D);
			TreeSet<Integer> B_temp_remove = update_B(D_temp_remove);
			TreeSet<Integer> S_temp_remove = update_S(D_temp_remove);
			double L_in_temp_remove = calculate_L_in(D_temp_remove);
			double L_ex_temp_remove = calculate_L_ex(B_temp_remove,
					S_temp_remove);
			// double L_temp_remove = calculate_L(L_in_temp_remove,
			// L_ex_temp_remove);
			// add again
			TreeSet<Integer> D_temp_add = update_D_add_node(D_temp_remove,
					member_in_D);
			TreeSet<Integer> B_temp_add = update_B(D_temp_add);
			TreeSet<Integer> S_temp_add = update_S(D_temp_add);
			double L_in_temp_add = calculate_L_in(D_temp_add);
			double L_ex_temp_add = calculate_L_ex(B_temp_add, S_temp_add);
			// double L_temp_add = calculate_L(L_in_temp_add,
			// L_ex_temp_add);
			if (!(L_in_temp_add > L_in_temp_remove && L_ex_temp_add < L_ex_temp_remove))
			{
				node_to_be_removed.add(member_in_D);
			}
		}
		// if (!node_to_be_removed.isEmpty())
		// {
		D.removeAll(node_to_be_removed);
		B = update_B(D);
		S = update_S(D);
		// } else
		// {
		// break;
		// }

		// } while (true);
		// System.out.println("examination_phase");
		// System.out.println("D_new: " + D);
		// System.out.println("B_new: " + B);
		// System.out.println("S_new: " + S);
	}

	/* (Tested) */
	public ArrayList<ArrayList<Integer>> run_chen(int seed_id)
	{
		discovery_phase(seed_id);
		examination_phase();
		if (D.contains(seed_id))
		{
			TreeSet<Integer> detected_community = new TreeSet<Integer>();
			detected_community.addAll(D);
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
			return partitionF;
		}
	}

	public static void main(String[] args) throws IOException
	{
		String file_path = "D:/real world networks/";
		String file_name = "karate";
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

		ChenAlgorithm chen = new ChenAlgorithm(data_reader.n, data_reader.m,
				data_reader.adjacencyTable);
		for (int i = 0; i < data_reader.n; i++)
		{
			chen.run_chen(i);
		}
		// chen.run_chen(100);
	}
}
