package com.comparision.lcd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

public class LCDAlgorithm
{
	public int node_size;
	public int link_size;
	public int numberOfCommunities;
	public ArrayList<ArrayList<Integer>> adjacencyTable;

	public LCDAlgorithm(int n, int m,
			ArrayList<ArrayList<Integer>> adjacencyTable)
	{
		this.node_size = n;
		this.link_size = m;
		this.numberOfCommunities = 0;
		this.adjacencyTable = adjacencyTable;
	}

	/* (Tested) Get the neihhbors of a maximum clique */
	public ArrayList<Integer> getMCNeighborNodes(ArrayList<Integer> mc)
	{
		ArrayList<Integer> neighborNodes = new ArrayList<Integer>();
		for (Iterator<Integer> iter = mc.iterator(); iter.hasNext();)
		{
			Integer memberInMC = iter.next();
			for (Iterator<Integer> iter2 = this.adjacencyTable.get(memberInMC)
					.iterator(); iter2.hasNext();)
			{
				Integer neighbor = iter2.next();
				if (adjacencyTable.get(memberInMC).contains(neighbor)
						&& !mc.contains(neighbor)
						&& !neighborNodes.contains(neighbor))
				{
					neighborNodes.add(neighbor);
				}
			}
		}
		return neighborNodes;
	}

	/* (Tested) */
	public boolean isConnectWithAllNodesInMC(int mc_neighbor,
			ArrayList<Integer> mc)
	{
		for (Iterator<Integer> iter = mc.iterator(); iter.hasNext();)
		{
			Integer memberInMC = iter.next();
			if (!adjacencyTable.get(mc_neighbor).contains(memberInMC))
			{
				return false;
			}
		}
		return true;
	}

	/* (Tested) Get MCS from a MC */
	public ArrayList<ArrayList<Integer>> getMCSNew(
			ArrayList<Integer> mc_neighbors, ArrayList<Integer> mc)
	{
		ArrayList<ArrayList<Integer>> mcs_new = new ArrayList<ArrayList<Integer>>();
		for (Iterator<Integer> iter = mc_neighbors.iterator(); iter.hasNext();)
		{
			Integer mc_neighbor = iter.next();

			if (isConnectWithAllNodesInMC(mc_neighbor, mc))
			{
				ArrayList<Integer> mc_new = new ArrayList<Integer>();
				mc_new.addAll(mc);
				mc_new.add(mc_neighbor);
				mcs_new.add(mc_new);
			}
		}
		// System.out.println("mcs_new");
		// for (Iterator<ArrayList<Integer>> iter = mcs_new.iterator(); iter
		// .hasNext();)
		// {
		// ArrayList<Integer> mc_new = iter.next();
		//
		// for (Iterator<Integer> iter2 = mc_new.iterator(); iter2.hasNext();)
		// {
		// Integer member = iter2.next();
		//
		// System.out.print(member + " ");
		// }
		// System.out.println();
		// }
		return mcs_new;
	}

	/* (Tested) is A a sub-set of B */
	public boolean isAinB(ArrayList<Integer> mc, ArrayList<Integer> mc_new)
	{
		for (Iterator<Integer> iter = mc.iterator(); iter.hasNext();)
		{
			Integer memberInMC = iter.next();
			if (!mc_new.contains(memberInMC))
			{
				return false;
			}
		}
		return true;
	}

	/* (Tested) */
	public boolean isAequalsB(ArrayList<Integer> mc, ArrayList<Integer> mc_new)
	{
		if (mc.size() == mc_new.size())
		{
			for (Iterator<Integer> iter = mc.iterator(); iter.hasNext();)
			{
				Integer memberInMC = iter.next();

				if (!mc_new.contains(memberInMC))
				{
					return false;
				}
			}
		} else
		{
			return false;
		}
		return true;
	}

	/* (Tested) Get the old MCS which are sub-set of new MCS */
	public ArrayList<ArrayList<Integer>> getMCSOld(
			ArrayList<ArrayList<Integer>> mcs,
			ArrayList<ArrayList<Integer>> mcs_new)
	{
		ArrayList<ArrayList<Integer>> mcs_old = new ArrayList<ArrayList<Integer>>();
		for (Iterator<ArrayList<Integer>> iter = mcs_new.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> mc_new = iter.next();
			for (Iterator<ArrayList<Integer>> iter2 = mcs.iterator(); iter2
					.hasNext();)
			{
				ArrayList<Integer> mc = iter2.next();
				if (isAinB(mc, mc_new) && !mcs_old.contains(mc))
				{
					mcs_old.add(mc);
				}
			}
		}
		// System.out.println("mcs_old");
		// for (Iterator<ArrayList<Integer>> iter = mcs_old.iterator(); iter
		// .hasNext();)
		// {
		// ArrayList<Integer> mc_old = iter.next();
		//
		// for (Iterator<Integer> iter2 = mc_old.iterator(); iter2.hasNext();)
		// {
		// Integer member = iter2.next();
		//
		// System.out.print(member + " ");
		// }
		// System.out.println();
		// }
		return mcs_old;
	}

	/* (Tested) Get the maximu cliques of a node */
	public ArrayList<ArrayList<Integer>> getMCS(int id)
	{
		ArrayList<ArrayList<Integer>> mcs_final = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> mcs = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> mc_first = new ArrayList<Integer>();
		mc_first.add(id);
		mcs.add(mc_first);
		for (Iterator<ArrayList<Integer>> iter = mcs.iterator(); iter.hasNext();)
		{
			ArrayList<Integer> mc = iter.next();
			ArrayList<Integer> mc_neighbors = getMCNeighborNodes(mc);
			ArrayList<ArrayList<Integer>> mcs_new = getMCSNew(mc_neighbors, mc);
			if (mcs_new.size() == 0 && !mcs_final.contains(mc))
			{
				mcs_final.add(mc);
				mcs.remove(mc);
				iter = mcs.iterator();
			} else
			{
				ArrayList<ArrayList<Integer>> mcs_old = getMCSOld(mcs, mcs_new);
				if (mcs_old.size() == 0)
				{
					mcs.addAll(mcs_new);
					iter = mcs.iterator();
				} else
				{
					mcs.removeAll(mcs_old);
					mcs.addAll(mcs_new);
					iter = mcs.iterator();
				}
			}
		}
		// System.out.println("mcs_final");
		// for (Iterator<ArrayList<Integer>> iter = mcs_final.iterator(); iter
		// .hasNext();)
		// {
		// ArrayList<Integer> mc_final = iter.next();
		//
		// for (Iterator<Integer> iter2 = mc_final.iterator(); iter2.hasNext();)
		// {
		// Integer member = iter2.next();
		//
		// System.out.print(member + " ");
		// }
		// System.out.println();
		// }
		return mcs_final;
	}

	/* (Tested) update the neighbors U of a local community LC */
	public ArrayList<Integer> updateU(ArrayList<Integer> u,
			ArrayList<Integer> lc)
	{
		u.clear();
		for (Iterator<Integer> iter = lc.iterator(); iter.hasNext();)
		{
			Integer memberInLC = iter.next();
			for (Iterator<Integer> iter2 = this.adjacencyTable.get(memberInLC)
					.iterator(); iter2.hasNext();)
			{
				Integer neighbor = iter2.next();
				if (!lc.contains(neighbor) && !u.contains(neighbor))
				{
					u.add(neighbor);
				}
			}
		}
		return u;
	}

	/* (Tested) calculate M */
	public double calculateM(ArrayList<Integer> s)
	{
		double m = 0;
		int innerLinks = 0;
		int outerLinks = 0;
		for (Iterator<Integer> iter = s.iterator(); iter.hasNext();)
		{
			Integer memberInC = iter.next();
			ArrayList<Integer> line = this.adjacencyTable.get(memberInC);
			for (Iterator<Integer> iter2 = line.iterator(); iter2.hasNext();)
			{
				Integer member = iter2.next();
				if (member != memberInC)
				{
					if (s.contains(member))
					{
						innerLinks++;
					} else
					{
						outerLinks++;
					}
				}
			}
		}
		m = (double) 0.5 * innerLinks / (double) outerLinks;
		return m;
	}

	/* (Tested) Add a node into lc */
	public void add_a_node_into_lc(ArrayList<Integer> lc, ArrayList<Integer> u)
	{
		double m = calculateM(lc);
		int addedNodeID = -1;
		double m_temp = -1;
		for (Iterator<Integer> iter = u.iterator(); iter.hasNext();)
		{
			Integer memberInU = iter.next();
			ArrayList<Integer> lc_temp = new ArrayList<Integer>();
			lc_temp.addAll(lc);
			lc_temp.add(memberInU);
			m_temp = calculateM(lc_temp);
			if (m_temp > m)
			{
				addedNodeID = memberInU;
				m = m_temp;
			}
		}
		if (addedNodeID != -1)
		{
			lc.add(addedNodeID);
			u = updateU(u, lc);
		}
	}

	/* (Tested) Extend lc */
	public void extend_lc(ArrayList<Integer> lc, ArrayList<Integer> u)
	{
		while (true)
		{
			int lc_old_size = lc.size();
			add_a_node_into_lc(lc, u);
			int lc_new_size = lc.size();
			if (lc_new_size > lc_old_size)
			{
				continue;
			} else
			{
				break;
			}
		}
	}

	/* (Tested) */
	public ArrayList<ArrayList<ArrayList<Integer>>> run_lcd(int id)
	{
		ArrayList<ArrayList<Integer>> lcs = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> mcs_final = getMCS(id);
		for (Iterator<ArrayList<Integer>> iter = mcs_final.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> mc = iter.next();
			// System.out.println(mc);
			ArrayList<Integer> lc = new ArrayList<Integer>();
			lc.addAll(mc);
			ArrayList<Integer> u = new ArrayList<Integer>();
			u = updateU(u, lc);
			extend_lc(lc, u);
			double m = calculateM(lc); // m >1
			// if(m >1)
			// {
			lcs.add(lc);
			// }
		}

		for (int i = 0; i < lcs.size(); i++)
		{
			ArrayList<Integer> lci = lcs.get(i);
			for (int j = i + 1; j < lcs.size(); j++)
			{
				ArrayList<Integer> lcj = lcs.get(j);
				if (isAequalsB(lci, lcj))
				{
					lcs.remove(lci);
					i = 0;
					break;
				}
			}
		}

		for (Iterator<ArrayList<Integer>> iter = lcs.iterator(); iter.hasNext();)
		{
			ArrayList<Integer> lc = iter.next();
			TreeSet<Integer> lc_temp = new TreeSet<Integer>();
			lc_temp.addAll(lc);
			lc.clear();
			lc.addAll(lc_temp);
			lc_temp.clear();
		}

		// System.out.println("lcs");
		// for (Iterator<ArrayList<Integer>> iter = lcs.iterator();
		// iter.hasNext();)
		// {
		// System.out.println(iter.next());
		// }

		ArrayList<ArrayList<ArrayList<Integer>>> partitionFS = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<TreeSet<Integer>> detected_communities = new ArrayList<TreeSet<Integer>>();
		for (Iterator<ArrayList<Integer>> iter = lcs.iterator(); iter.hasNext();)
		{
			ArrayList<Integer> detected_community = iter.next();
			ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> rest_nodes = new ArrayList<Integer>();
			for (int i = 0; i < this.node_size; i++)
			{
				if (!detected_community.contains(i))
				{
					rest_nodes.add(i);
				}
			}
			partitionF.add(detected_community);
			partitionF.add(rest_nodes);
			partitionFS.add(partitionF);
		}
		return partitionFS;
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

		LCDAlgorithm lcd = new LCDAlgorithm(data_reader.n, data_reader.m,
				data_reader.adjacencyTable);
		for (int i = 0; i < data_reader.n; i++)
		{
			lcd.run_lcd(i);
		}
		// lcd.run_lcd(24);
	}
}
