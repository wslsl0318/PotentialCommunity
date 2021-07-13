package com.fileoperations.methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.publicstructures.methods.CommunityStructure;
import com.publicstructures.methods.CommunityStructureOverlap;

public class FileReaders
{
	// To store file path
	public String sourcePath;
	// To store the size of social network
	public int size;
	// To store the adjacency matrix of social network
	public int[][] adjacencyMatrix;
	// To store the adjacency matrix of social network
	public TreeSet<Integer> nos;

	public FileReaders(String sourcePath)
	{
		this.sourcePath = sourcePath;

		this.size = 0;

		this.adjacencyMatrix = new int[0][0];

		this.nos = null;
	}

	public int getSize()
	{
		return size;
	}

	public int[][] getAdjacencyMatrix()
	{
		return adjacencyMatrix;
	}

	/* To map the data source file into a adjacency matrix */
	public boolean readFile(boolean hasValue, boolean hasDirectrion)
			throws IOException
	{
		FileInputStream fis = new FileInputStream(this.sourcePath);

		byte[] buffer = new byte[200];

		int length = 0;

		String currentData = "";

		while (-1 != (length = fis.read(buffer, 0, 200)))
		{
			String str = new String(buffer, 0, length);

			currentData += str;
		}

		fis.close();

		currentData = currentData.replaceAll("\r\n", " ");
		currentData = currentData.replaceAll("\t", " ");

		String[] strs;

		strs = currentData.split(" ");

		// To get the number of nodes
		this.nos = new TreeSet<Integer>();

		for (int i = 0; i < strs.length; i++)
		{
			nos.add(Integer.parseInt(strs[i]));
		}

		ArrayList<Integer> al = new ArrayList<Integer>(nos);

		this.size = al.size();

		this.adjacencyMatrix = new int[size][size];

		if (hasValue == false && hasDirectrion == false)
		{
			for (int i = 0; i < strs.length; i += 2)
			{
				adjacencyMatrix[al.indexOf(Integer.parseInt(strs[i]))][al
						.indexOf(Integer.parseInt(strs[i + 1]))] = 1;

				adjacencyMatrix[al.indexOf(Integer.parseInt(strs[i + 1]))][al
						.indexOf(Integer.parseInt(strs[i]))] = 1;
			}
		} else if (hasValue == true && hasDirectrion == false)
		{
			for (int i = 0; i < strs.length; i += 3)
			{
				adjacencyMatrix[al.indexOf(Integer.parseInt(strs[i]))][al
						.indexOf(Integer.parseInt(strs[i + 1]))] = Integer
						.parseInt(strs[i + 2]);

				adjacencyMatrix[al.indexOf(Integer.parseInt(strs[i + 1]))][al
						.indexOf(Integer.parseInt(strs[i]))] = Integer
						.parseInt(strs[i + 2]);
			}
		} else if (hasValue == false && hasDirectrion == true)
		{
			for (int i = 0; i < strs.length; i += 2)
			{
				adjacencyMatrix[al.indexOf(Integer.parseInt(strs[i]))][al
						.indexOf(Integer.parseInt(strs[i + 1]))] = 1;
			}
		} else if (hasValue == true && hasDirectrion == true)
		{
			for (int i = 0; i < strs.length; i += 3)
			{
				adjacencyMatrix[al.indexOf(Integer.parseInt(strs[i]))][al
						.indexOf(Integer.parseInt(strs[i + 1]))] = Integer
						.parseInt(strs[i + 2]);
			}
		}

		for (int i = 0; i < size; i++)
		{
			adjacencyMatrix[i][i] = 0;
		}

		if (this.nos.first() == 0)
		{
			return true;
		} else
		{
			return false;
		}
	}

	public static String[] changeTxtToClu(String txtfile) throws IOException
	{
		FileInputStream fis = new FileInputStream(txtfile);

		byte[] buffer = new byte[200];

		int length = 0;

		String currentData = "";

		while (-1 != (length = fis.read(buffer, 0, 200)))
		{
			String str = new String(buffer, 0, length);

			currentData += str;
		}

		fis.close();

		currentData = currentData.replaceAll("\r\n", " ");
		currentData = currentData.replaceAll("\t", " ");
		currentData = currentData.replaceAll("  ", " ");

		String[] strs;

		strs = currentData.split(" ");

		for (int i = 0; i < strs.length; i++)
		{
			System.out.print(strs[i] + " ");
		}

		return strs;
	}

	public static String[] readRealCommunities(String clusfile)
			throws IOException
	{
		FileInputStream fis = new FileInputStream(clusfile);

		byte[] buffer = new byte[200];

		int length = 0;

		String currentData = "";

		while (-1 != (length = fis.read(buffer, 0, 200)))
		{
			String str = new String(buffer, 0, length);

			currentData += str;
		}

		fis.close();

		currentData = currentData.replaceAll("\r\n", " ");
		

		String[] strs;

		strs = currentData.split(" ");

		return strs;
	}
	
	public static ArrayList<CommunityStructureOverlap> get_communities_structure_overlap(
			String fileName)
	{
		ArrayList<CommunityStructureOverlap> communities_structure_overlap = new ArrayList<CommunityStructureOverlap>();// changed
		File file = new File(fileName);
		BufferedReader reader = null;
		try
		{
			// System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null)
			{
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);
				line++;
				/*******************************************************/
				CommunityStructureOverlap community_structure_overlap = new CommunityStructureOverlap();
				String[] strs;
				strs = tempString.split("\t");
				int node_id = Integer.parseInt(strs[0]);
				community_structure_overlap.node_id = node_id;
				String[] strs_2;
				strs_2 = strs[1].split(" ");
				for (int i = 0; i < strs_2.length; i++)
				{
					int community_id = Integer.parseInt(strs_2[i]);
					community_structure_overlap.community_ids.add(community_id);
				}
				communities_structure_overlap.add(community_structure_overlap);
				/*******************************************************/
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
		return communities_structure_overlap;
	}

	public static void main(String[] args) throws IOException
	{
		String filePath = "C:/Users/naruto/Desktop/Fifth_LFR_Networks/1/1/1.clu";//"D:/新的测试数据/krebs' books.clu";// dolphins,karates
		// String filePath = "D:/新的测试数据/空手道.txt";
		// String filePath = "D:/新的测试数据/value2.txt";
		// String filePath = "D:/新的测试数据/novalue.txt";

		// FileReaders fr = new FileReaders(filePath);

//		String[] strs = FileReaders.readRealCommunities(filePath);
//
//		ArrayList<ArrayList<Integer>> compnnents = CommunityStructure
//				.getComponentsFromString(strs);
//		System.out.println("Real Components:");
//		for (Iterator<ArrayList<Integer>> componentx = compnnents.iterator(); componentx
//				.hasNext();)
//		{
//			ArrayList<Integer> component = componentx.next();
//			for (Iterator<Integer> members = component.iterator(); members
//					.hasNext();)
//			{
//				int member = members.next();
//				System.out.print(member + " ");
//			}
//			System.out.println();
//		}
		
//		changeTxtToClu(filePath);

		// System.out.println("is NO from 0");
		// System.out.println(fr.readFile(false, false));
		//
		// System.out.println("nodes number");
		// System.out.println(fr.getSize());
		//
		// System.out.println("adjacencyMatrix[i][j]");
		// System.out.println(fr.getAdjacencyMatrix());
		
		 double d=12.56432;
		d=((int)(d*100))/(double)100 ;
		System.out.println(d);
	}
}
