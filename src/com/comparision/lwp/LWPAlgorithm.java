package com.comparision.lwp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

public class LWPAlgorithm
{
	public int node_size;
	public int link_size;
	public int numberOfCommunities;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public TreeSet<Integer> S;
	public ArrayList<Integer> N;
	public ArrayList<Integer> Q;
	public ArrayList<Integer> deleteQ;

	public LWPAlgorithm(int n, int m,
			ArrayList<ArrayList<Integer>> adjacencyTable)
	{
		this.node_size = n;
		this.link_size = m;
		this.numberOfCommunities = 0;
		this.adjacencyTable = adjacencyTable;
		this.S = new TreeSet<Integer>();
		this.N = new ArrayList<Integer>();
		this.Q = new ArrayList<Integer>();
		this.deleteQ = new ArrayList<Integer>();
	}

	/* (Tested) init Set S */
	public void initSetS(Integer v)
	{
		this.S.clear();
		this.S.add(v);
	}

	/* (Tested) init Set N */
	public void initSetN(Integer v)
	{
		this.N.clear();
		this.N.addAll(this.adjacencyTable.get(v));
		this.N.remove(0);
	}

	/* (Tested) calculate M */
	public double calculateM(TreeSet<Integer> s)
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

	/* (Tested) sort Set N */
	public void sortSetN()
	{
		TreeSet<LWPNode> tempSetN = new TreeSet<LWPNode>(new LWPNode());
		for (Iterator<Integer> iter = this.N.iterator(); iter.hasNext();)
		{
			Integer memberInSetN = iter.next();
			LWPNode lwpn = new LWPNode(memberInSetN, this.adjacencyTable.get(
					memberInSetN).size() - 1);
			tempSetN.add(lwpn);
		}
		this.N.clear();
		for (Iterator<LWPNode> iter = tempSetN.iterator(); iter.hasNext();)
		{
			LWPNode member = iter.next();
			this.N.add(member.id);
		}
	}

	/* (Tested) is changed Set S Connected */
	public boolean isConnected(TreeSet<Integer> changedSetS)
	{
		ArrayList<Integer> nodesToBeRemoved = new ArrayList<Integer>();
		nodesToBeRemoved.add(changedSetS.first());
		ArrayList<Integer> removedNodes = new ArrayList<Integer>();
		while (nodesToBeRemoved.size() != 0)
		{
			Integer nodeToBeRemoved = nodesToBeRemoved.get(0);
			removedNodes.add(nodeToBeRemoved);
			ArrayList<Integer> line = this.adjacencyTable.get(nodeToBeRemoved);
			for (Iterator<Integer> iter = line.iterator(); iter.hasNext();)
			{
				Integer member = iter.next();
				if (member != nodeToBeRemoved && changedSetS.contains(member)
						&& !nodesToBeRemoved.contains(member)
						&& !removedNodes.contains(member))
				{
					nodesToBeRemoved.add(member);
				}
			}
			nodesToBeRemoved.remove(nodeToBeRemoved);
		}
		if (removedNodes.size() < changedSetS.size())
		{
			return false;
		}
		return true;
	}

	/* (Tested) addition Step */
	public void additionStep()
	{
		sortSetN();
		this.Q.clear();
		// do
		// {
		// int S_size_old = this.S.size();
		// int S_size_new = S_size_old;
		double oldM = calculateM(this.S);
		for (Iterator<Integer> iter = this.N.iterator(); iter.hasNext();)
		{
			Integer memberInSetN = iter.next();
			TreeSet<Integer> tempSetS = new TreeSet<Integer>();
			tempSetS.addAll(this.S);
			tempSetS.add(memberInSetN);
			double newM = calculateM(tempSetS);
			double dateM = newM - oldM;
			if (dateM > 0)
			{
				this.S.add(memberInSetN);
				oldM = newM;
				// updateSetN();
				this.Q.add(memberInSetN);
				// break;
			}
		}
		// S_size_new = this.S.size();
		// if (S_size_new > S_size_old)
		// {
		// continue;
		// } else
		// {
		// break;
		// }
		// } while (true);
		// System.out.println(Q);
	}

	/* deletion Step */
	public void deletionStep()
	{
		do
		{
			deleteQ.clear();
			double oldM = calculateM(this.S);
			for (Iterator<Integer> iter = this.S.iterator(); iter.hasNext();)
			{
				Integer memberInSetS = iter.next();
				TreeSet<Integer> tempSetS = new TreeSet<Integer>();
				tempSetS.addAll(S);
				tempSetS.remove(memberInSetS);
				double newM = calculateM(tempSetS);
				double dateM = newM - oldM;
				if (dateM > 0 && isConnected(tempSetS))
				{
					this.S.remove(memberInSetS);
					oldM = newM;
					if (this.Q.contains(memberInSetS))
					{
						this.Q.remove(memberInSetS);
						this.deleteQ.add(memberInSetS);
					}
					break;
				}
			}
		} while (!deleteQ.isEmpty());
	}

	/* (Tested) update Set N */
	public void updateSetN()
	{
		this.N.clear();
		for (Iterator<Integer> iter = this.S.iterator(); iter.hasNext();)
		{
			Integer memberInSetS = iter.next();
			ArrayList<Integer> line = this.adjacencyTable.get(memberInSetS);
			for (Iterator<Integer> iter2 = line.iterator(); iter2.hasNext();)
			{
				Integer member = iter2.next();
				if (!this.S.contains(member) && !this.N.contains(member))
				{
					this.N.add(member);
				}
			}
		}
	}

	public ArrayList<ArrayList<Integer>> run_lwp(Integer v)
	{
		initSetN(v);
		initSetS(v);
		do
		{
			additionStep();
			deletionStep();
			updateSetN();
		} while (!this.Q.isEmpty());
		if (calculateM(this.S) > 1 && this.S.contains(v))
		{
			TreeSet<Integer> detected_community = new TreeSet<Integer>();
			detected_community.addAll(S);
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

		LWPAlgorithm lwp = new LWPAlgorithm(data_reader.n, data_reader.m,
				data_reader.adjacencyTable);
		for (int i = 0; i < data_reader.n; i++)
		{
			lwp.run_lwp(i);
		}
		// lwp.run_lwp(0);
	}
}
