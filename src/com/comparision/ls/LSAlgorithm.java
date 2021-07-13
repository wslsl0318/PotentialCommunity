package com.comparision.ls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

public class LSAlgorithm
{
	public int node_size;
	public int link_size;
	public int numberOfCommunities;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public ArrayList<Integer> s;
	public ArrayList<Integer> c;
	public ArrayList<Integer> n;
	public double m;
	public TreeSet<LSNode> h;

	public LSAlgorithm(int n, int m,
			ArrayList<ArrayList<Integer>> adjacencyTable)
	{
		this.node_size = n;
		this.link_size = m;
		this.numberOfCommunities = 0;
		this.adjacencyTable = adjacencyTable;
		this.s = new ArrayList<Integer>();
		this.c = new ArrayList<Integer>();
		this.n = new ArrayList<Integer>();
		this.h = new TreeSet<LSNode>(new LSNode());
	}

	/* (Tested) */
	public void initAlgorithm(int id)
	{
		this.s.clear();
		this.c.clear();
		this.n.clear();
		this.s.add(id);
		this.c.add(id);
		this.n.addAll(this.adjacencyTable.get(id));
		this.n.remove((Integer) id);
		this.m = 0;
	}

	/* (Tested) */
	public void updateN()
	{
		this.n.clear();
		for (Iterator<Integer> iter = this.c.iterator(); iter.hasNext();)
		{
			Integer memberInC = iter.next();
			for (Iterator<Integer> iter2 = this.adjacencyTable.get(memberInC)
					.iterator(); iter2.hasNext();)
			{
				Integer neighbor = iter2.next();
				if (!this.c.contains(neighbor) && !this.n.contains(neighbor))
				{
					this.n.add(neighbor);
				}
			}
		}
	}

	/* (Tested) */
	public void updateS()
	{
		this.s.clear();
		for (Iterator<Integer> iter = this.c.iterator(); iter.hasNext();)
		{
			Integer memberInC = iter.next();
			for (Iterator<Integer> iter2 = this.adjacencyTable.get(memberInC)
					.iterator(); iter2.hasNext();)
			{
				Integer neighbor = iter2.next();
				if (!this.c.contains(neighbor) && !this.s.contains(memberInC))
				{
					this.s.add(memberInC);
				}
			}
		}
	}

	/* (Tested) Get neighbors of v */
	public ArrayList<Integer> getTv(int v)
	{
		ArrayList<Integer> tv = new ArrayList<Integer>();
		tv.addAll(this.adjacencyTable.get(v));
		tv.remove((Integer) v);
		return tv;
	}

	/* (Tested) */
	public ArrayList<Integer> getTempSet(int v, ArrayList<Integer> tv)
	{
		ArrayList<Integer> ts = new ArrayList<Integer>();
		ArrayList<Integer> x = new ArrayList<Integer>();
		x.addAll(c);
		x.addAll(n);
		for (Iterator<Integer> iter = x.iterator(); iter.hasNext();)
		{
			Integer memberInX = iter.next();
			if (tv.contains(memberInX))
			{
				ts.add(memberInX);
			}
		}
		return ts;
	}

	/* (Tested) */
	public void updateH()
	{
		this.h.clear();
		for (Iterator<Integer> iter = this.n.iterator(); iter.hasNext();)
		{
			Integer memberInN = iter.next();
			ArrayList<Integer> tv = getTv(memberInN);
			ArrayList<Integer> ts = getTempSet(memberInN, tv);
			double lsValue = (double) (ts.size()) / (double) (tv.size());
			LSNode lsn = new LSNode(memberInN, lsValue);
			this.h.add(lsn);
		}
	}

	/* (Tested) */
	public double getM(ArrayList<Integer> s)
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
				if (!member.equals(memberInC))
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

	/* (Tested) */
	public ArrayList<ArrayList<Integer>> run_ls(int id)
	{
		initAlgorithm(id);
		updateH();
		do
		{
			if (h.isEmpty())
			{
				break;
			}
			TreeSet<Integer> c_old = new TreeSet<Integer>();
			c_old.addAll(this.c);
			TreeSet<Integer> c_new = new TreeSet<Integer>();
			// System.out.println("addition");
			LSNode v = this.h.first();
			ArrayList<Integer> additionC = new ArrayList<Integer>();
			additionC.addAll(this.c);
			additionC.add(v.id);
			double oldM_addition = this.m;
			double newM_addition = getM(additionC);
			double detaM_addition = newM_addition - oldM_addition;
			if (detaM_addition > 0)
			{
				this.c.add(v.id);
				updateN();
				// this.n.remove((Integer) v.id);// changed
				updateS();
				this.m = newM_addition;
			}
			// System.out.println("deletion");
			do
			{
				int c_size_old = this.c.size();
				int c_size_new = c_size_old;
				for (Iterator<Integer> iter = this.s.iterator(); iter.hasNext();)
				{
					Integer memberInS = iter.next();
					ArrayList<Integer> deletionC = new ArrayList<Integer>();
					deletionC.addAll(this.c);
					deletionC.remove(memberInS);
					double oldM_deletion = this.m;
					double newM_deletion = getM(deletionC);
					double detaM_deletion = newM_deletion - oldM_deletion;
					if (detaM_deletion > 0)
					{
						this.c.remove(memberInS);
						updateN();
						// this.n.add(memberInS);
						updateS();
						// this.s.remove(memberInS);
						this.m = newM_deletion;
						break;
					}
				}
				c_size_new = this.c.size();
				if (c_size_new < c_size_old)
				{
					continue;
				} else
				{
					break;
				}
			} while (true);
			updateH();
			c_new.addAll(this.c);
			if (!c_old.equals(c_new))
			{
				continue;
			} else
			{
				break;
			}
		} while (true);
		// do
		// {
		// int c_size_old = this.c.size();
		// int c_size_new = c_size_old;
		ArrayList<Integer> nodes_to_be_removed = new ArrayList<Integer>();
		for (Iterator<Integer> iter = this.s.iterator(); iter.hasNext();)
		{
			int kin = 0;
			int kout = 0;
			Integer memberInS = iter.next();
			for (Iterator<Integer> iter_neighbor = adjacencyTable
					.get(memberInS).iterator(); iter_neighbor.hasNext();)
			{
				Integer neighbor = iter_neighbor.next();
				if (!neighbor.equals(memberInS))
				{
					if (this.c.contains(neighbor))
					{
						kin++;
					} else
					{
						kout++;
					}
				}
			}
			if (kout >= kin)
			{
				// this.c.remove(memberInS);
				// updateN();
				// updateS();
				// break;
				nodes_to_be_removed.add(memberInS);
			}
		}
		this.c.removeAll(nodes_to_be_removed);
		// c_size_new = this.c.size();
		// if (c_size_new < c_size_old)
		// {
		// continue;
		// } else
		// {
		// break;
		// }
		// } while (true);
		this.m = getM(this.c);
		if (this.c.contains((Integer) id))// && this.m > 1)
		{
			TreeSet<Integer> detected_community = new TreeSet<Integer>();
			detected_community.addAll(c);
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

		LSAlgorithm ls = new LSAlgorithm(data_reader.n, data_reader.m,
				data_reader.adjacencyTable);
		for (int i = 0; i < data_reader.n; i++)
		{
			ls.run_ls(i);
		}
		// ls.run_ls(0);
	}
}
