package DataReaderArtificialNetwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class DataReaderArtificialNetwork
{
	public String file_path_network;
	public String file_path_community;
	public int n;// node number
	public int m;// link number
	public int nc;// community number;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public ArrayList<ArrayList<Integer>> real_communities;

	public DataReaderArtificialNetwork()
	{
		this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
		this.real_communities = new ArrayList<ArrayList<Integer>>();
	}

	public DataReaderArtificialNetwork(String file_path_network,
			String file_path_community)
	{

		this.file_path_network = file_path_network;
		this.file_path_community = file_path_community;
		this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
		this.real_communities = new ArrayList<ArrayList<Integer>>();
	}

	/* (Tested) Get network size */
	public void get_network_size()
	{
		TreeSet<Integer> node_ids = new TreeSet<Integer>();
		File file = new File(file_path_network);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null)
			{
				line++;
				String[] strs;
				strs = tempString.split("\t");
				int node_id_0 = Integer.parseInt(strs[0]) - 1;
				int node_id_1 = Integer.parseInt(strs[1]) - 1;
				node_ids.add(node_id_1);
				node_ids.add(node_id_0);
			}
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException e1)
				{
				}
			}
		}
		n = node_ids.size();
	}

	/* (Tested) Build adjacency Table */
	public void build_adjacency_table() throws IOException
	{
		for (int i = 0; i < n; i++)
		{
			TreeSet<Integer> neighbors = new TreeSet<Integer>();
			File file = new File(file_path_network);
			BufferedReader reader = null;
			try
			{
				reader = new BufferedReader(new FileReader(file));
				String tempString = null;
				int line = 1;
				while ((tempString = reader.readLine()) != null)
				{
					line++;
					String[] strs;
					strs = tempString.split("\t");
					int node_id_0 = Integer.parseInt(strs[0]) - 1;
					int node_id_1 = Integer.parseInt(strs[1]) - 1;
					if (node_id_0 == i && node_id_1 != i)
					{
						neighbors.add(node_id_1);
					} else if (node_id_0 != i && node_id_1 == i)
					{
						neighbors.add(node_id_0);
					}
				}
				ArrayList<Integer> one_line = new ArrayList<Integer>();
				one_line.add(i);
				one_line.addAll(neighbors);
				adjacencyTable.add(one_line);
				m += one_line.size() - 1;
				reader.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			} finally
			{
				if (reader != null)
				{
					try
					{
						reader.close();
					} catch (IOException e1)
					{
					}
				}
			}
		}
		m = m / 2;
	}

	/* (Tested) Get communities size */
	public void get_communities_size()
	{
		TreeSet<Integer> community_ids = new TreeSet<Integer>();
		File file = new File(file_path_community);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null)
			{
				line++;
				String[] strs_1 = tempString.split("\t");
				String communities = strs_1[1];
				String[] strs_2 = communities.split(" ");
				for (int i = 0; i < strs_2.length; i++)
				{
					community_ids.add(Integer.parseInt(strs_2[i]));
				}
			}
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException e1)
				{
				}
			}
		}
		nc = community_ids.size();
	}

	/* (Tested) Build communities */
	public void build_communities()
	{
		for (int i = 0; i < nc; i++)
		{
			ArrayList<Integer> community = new ArrayList<Integer>();
			this.real_communities.add(community);
		}
		File file = new File(file_path_community);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null)
			{
				line++;
				String[] strs_1 = tempString.split("\t");
				String communities = strs_1[1];
				String[] strs_2 = communities.split(" ");
				for (int i = 0; i < strs_2.length; i++)
				{
					int community_id = Integer.parseInt(strs_2[i]) - 1;
					real_communities.get(community_id).add(
							Integer.parseInt(strs_1[0]) - 1);
				}
			}
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				} catch (IOException e1)
				{
				}
			}
		}
	}

	/* (Tested) Read artificial network and initial paramaters */
	public void read_artificial_network() throws IOException
	{
		get_network_size();
		build_adjacency_table();
		get_communities_size();
		build_communities();
		// System.out.println("node number: " + n);
		// System.out.println("link number: " + m);
		// System.out.println("community number: " + nc);
		// System.out.println("adjacencyTable");
		// for (Iterator<ArrayList<Integer>> iter = adjacencyTable.iterator();
		// iter
		// .hasNext();)
		// {
		// System.out.println(iter.next());
		// }
		// System.out.println("real_communities");
		// for (Iterator<ArrayList<Integer>> iter = real_communities.iterator();
		// iter
		// .hasNext();)
		// {
		// System.out.println(iter.next());
		// }
	}

	public static void main(String[] args) throws IOException
	{
		String file_path = "D:/Sixth/tests_on_artificial_networks/n/1/";
		String file_name = "1";
		// karate;dolphin;football;krebsbook;dblp;amazon;youtube
		String network = ".txt";
		// karate_network;dolphin_network;football_network;krebsbook_network
		// dblp_network;amazon_network;youtube_network
		String community = ".clu";
		// karate_community;dolphin_community;football_community;krebsbook_community
		// dblp_community;amazon_community;youtube_community
		String table = "_table.txt";
		// karate_table;dolphin_table;football_table;krebsbook_table
		// dblp_table;amazon_table;youtube_table

		String file_path_network = file_path + file_name + network;
		String file_path_community = file_path + file_name + community;
		String file_path_table = file_path + file_name + table;

		DataReaderArtificialNetwork reader = new DataReaderArtificialNetwork(
				file_path_network, file_path_community);
		reader.read_artificial_network();
	}
}
