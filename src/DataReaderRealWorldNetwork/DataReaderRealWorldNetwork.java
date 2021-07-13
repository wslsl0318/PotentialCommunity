package DataReaderRealWorldNetwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class DataReaderRealWorldNetwork
{
	public String file_path_community;
	public String file_path_table;
	public int n;// node number
	public int m;// link number
	public int nc;// community number;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public ArrayList<ArrayList<Integer>> real_communities;

	public DataReaderRealWorldNetwork()
	{
		this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
		this.real_communities = new ArrayList<ArrayList<Integer>>();
	}

	public DataReaderRealWorldNetwork(String file_path_community,
			String file_path_table)
	{
		this.file_path_community = file_path_community;
		this.file_path_table = file_path_table;
		this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
		this.real_communities = new ArrayList<ArrayList<Integer>>();
	}

	/* (Tested) Read adjacency Table and Get the link number of network */
	public void read_adjacency_table() throws IOException
	{
		File file = new File(file_path_table);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			n = 1;
			while ((tempString = reader.readLine()) != null)
			{
				n++;
				String[] strs;
				strs = tempString.split("\t");
				ArrayList<Integer> neighbors = new ArrayList<Integer>();
				for (int i = 0; i < strs.length; i++)
				{
					neighbors.add(Integer.parseInt(strs[i]));
				}
				this.adjacencyTable.add(neighbors);
				this.m += (neighbors.size() - 1);
			}
			this.n = n - 1;
			m = m / 2;
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

	/* (Tested) Read real communities */
	public void read_real_communities()
	{
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
				String[] strs;
				strs = tempString.split("\t");
				ArrayList<Integer> community = new ArrayList<Integer>();
				for (int i = 0; i < strs.length; i++)
				{
					community.add(Integer.parseInt(strs[i]));
				}
				this.real_communities.add(community);
				this.nc++;
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

	/* (Tested) Read real world network and initial paramaters */
	public void read_realworld_network() throws IOException
	{
		read_adjacency_table();
		read_real_communities();
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
		String file_path = "D:/real world networks/";
		String file_name = "amazon";
		// karate;dolphin;football;krebsbook;dblp;amazon;youtube
		String network = "_network.txt";
		// karate_network;dolphin_network;football_network;krebsbook_network
		// dblp_network;amazon_network;youtube_network
		String community = "_community.txt";
		// karate_community;dolphin_community;football_community;krebsbook_community
		// dblp_community;amazon_community;youtube_community
		String table = "_table.txt";
		// karate_table;dolphin_table;football_table;krebsbook_table
		// dblp_table;amazon_table;youtube_table

		String file_path_network = file_path + file_name + network;
		String file_path_community = file_path + file_name + community;
		String file_path_table = file_path + file_name + table;

		DataReaderRealWorldNetwork reader = new DataReaderRealWorldNetwork(
				file_path_community, file_path_table);
		reader.read_realworld_network();
		System.out.println(reader.n+","+reader.m+","+reader.nc);
	}
}

/* (Tested) Set the size of network */
// public void set_size_of_network(String file_path_network)
// {
// TreeSet<Integer> distinct_node_ids = new TreeSet<Integer>();
// File file = new File(file_path_network);
// BufferedReader reader = null;
// try
// {
// // System.out.println("以行为单位读取文件内容，一次读一整行：");
// reader = new BufferedReader(new FileReader(file));
// String tempString = null;
// int line = 1;
// while ((tempString = reader.readLine()) != null)
// {
// line++;
// String[] strs;
// strs = tempString.split("\t");
// for (int i = 0; i < strs.length; i++)
// {
// int node_id = Integer.parseInt(strs[i]);
// distinct_node_ids.add(node_id);
// }
// }
// this.n = distinct_node_ids.size();
// reader.close();
// } catch (IOException e)
// {
// e.printStackTrace();
// } finally
// {
// if (reader != null)
// {
// try
// {
// reader.close();
// } catch (IOException e1)
// {
// }
// }
// }
// }

/* (Tested) Build adjacency Table */
// public void build_adjacency_table() throws IOException
// {
// for (int i = 0; i < size; i++)
// {
// TreeSet<Integer> neighbors = new TreeSet<Integer>();
// File file = new File(file_path_network);
// BufferedReader reader = null;
// try
// {
// reader = new BufferedReader(new FileReader(file));
// String tempString = null;
// int line = 1;
// while ((tempString = reader.readLine()) != null)
// {
// line++;
// String[] strs;
// strs = tempString.split("\t");
// int node_id_0 = Integer.parseInt(strs[0]);
// int node_id_1 = Integer.parseInt(strs[1]);
// if (node_id_0 == i && node_id_1 != i)
// {
// neighbors.add(node_id_1);
// } else if (node_id_0 != i && node_id_1 == i)
// {
// neighbors.add(node_id_0);
// }
// }
// reader.close();
// } catch (IOException e)
// {
// e.printStackTrace();
// } finally
// {
// if (reader != null)
// {
// try
// {
// reader.close();
// } catch (IOException e1)
// {
// }
// }
// }
// this.adjacencyTable.add(neighbors);
// // System.out.println(i + "," + neighbors);
// }
//
// FileOutputStream fos = new FileOutputStream(
// "D:/real world networks/dblptable.txt");
// OutputStreamWriter osw = new OutputStreamWriter(fos);
// BufferedWriter bw = new BufferedWriter(osw);
// for (int i = 0; i < size; i++)
// {
// TreeSet<Integer> line = this.adjacencyTable.get(i);
// bw.write(i + "\t");
// for (Iterator<Integer> iter = line.iterator(); iter.hasNext();)
// {
// bw.write(iter.next() + "\t");
// }
// bw.write("\r\n");
// }
// bw.close();
// // System.out.println("adjacencyTable: ");
// // for (int i = 0; i < adjacencyTable.size(); i++)
// // {
// // System.out.println(i + "," + adjacencyTable.get(i));
// // }
// }

/* (Tested) Build adjacency matrix */
// public void build_adjacency_matrix(String file_path_network)
// {
// this.adjacencyMatrix = new int[size][size];
// File file = new File(file_path_network);
// BufferedReader reader = null;
// try
// {
// reader = new BufferedReader(new FileReader(file));
// String tempString = null;
// int line = 1;
// while ((tempString = reader.readLine()) != null)
// {
// line++;
// String[] strs;
// strs = tempString.split("\t");
// int node_id_0 = Integer.parseInt(strs[0]);
// int node_id_1 = Integer.parseInt(strs[1]);
// this.adjacencyMatrix[node_id_0][node_id_1] = 1;
// this.adjacencyMatrix[node_id_1][node_id_0] = 1;
// }
//
// reader.close();
// } catch (IOException e)
// {
// e.printStackTrace();
// } finally
// {
// if (reader != null)
// {
// try
// {
// reader.close();
// } catch (IOException e1)
// {
// }
// }
// }
// }
