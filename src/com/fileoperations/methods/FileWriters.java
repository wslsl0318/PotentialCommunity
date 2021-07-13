package com.fileoperations.methods;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import com.publicstructures.methods.CommunityStructure;
import com.pulicoperations.methods.Partitions;

public class FileWriters
{
	// To store the size of social network
	public int size;
	// To store the adjacency matrix of social network
	public int[][] adjacencyMatrix;

	public FileWriters(FileReaders fr)
	{
		this.size = fr.getSize();

		this.adjacencyMatrix = fr.getAdjacencyMatrix();
	}

	public void writeToClu(String[] strs,String targetCluPath) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(targetCluPath);

		OutputStreamWriter osw = new OutputStreamWriter(fos);

		BufferedWriter bw = new BufferedWriter(osw);

		bw.write("*Vertices" + " " + strs.length/2 + "\r\n");
		
		for (int i=1;i<strs.length;i+=2)
		{
			String str = strs[i];

			bw.write(str + "\r\n");
		}

		bw.close();
	}
	
	public void writeNetFile(Boolean hasValue, String targetPath)
			throws IOException
	{
		FileOutputStream fos = new FileOutputStream(targetPath);

		OutputStreamWriter osw = new OutputStreamWriter(fos);

		BufferedWriter bw = new BufferedWriter(osw);

		bw.write("*Vertices" + " " + this.size + "\r\n");

		System.out.println(this.size);

		for (int i = 0; i < this.size; i++)
		{
			String str = String.valueOf(i + 1);

			bw.write(str + " " + str + "\r\n");
		}

		bw.write("*edges" + "\r\n");

		if (hasValue == false)
		{
			for (int i = 0; i < this.size; i++)
			{
				for (int j = 0; j < this.size; j++)
				{
					if (this.adjacencyMatrix[i][j] != 0)
					{
						String str1 = String.valueOf(i + 1);

						String str2 = String.valueOf(j + 1);

						bw.write(str1 + " " + str2 + "\r\n");
					}
				}
			}
		} else
		{
			for (int i = 0; i < this.size; i++)
			{
				for (int j = 0; j < this.size; j++)
				{
					if (this.adjacencyMatrix[i][j] != 0)
					{
						String str1 = String.valueOf(i + 1);

						String str2 = String.valueOf(j + 1);

						bw.write(str1 + " " + str2 + " "
								+ this.adjacencyMatrix[i][j] + "\r\n");
					}
				}
			}
		}
		bw.close();
	}

	public void writeCluFile(Partitions partitions, String targetPath)
			throws IOException
	{
		FileOutputStream fos = new FileOutputStream(targetPath);

		OutputStreamWriter osw = new OutputStreamWriter(fos);

		BufferedWriter bw = new BufferedWriter(osw);

		bw.write("*Vertices" + " " + this.size + "\r\n");

		for (Iterator<CommunityStructure> iter = partitions.tcs.iterator(); iter
				.hasNext();)
		{

			CommunityStructure member = iter.next();

			String str = String.valueOf(member.cno);

			bw.write(str + "\r\n");
		}

		bw.close();
	}

	public static void main(String[] args) throws IOException
	{
		// String sourcePath = "D:/新的测试数据/悲惨世界.txt";
		// String sourcePath = "D:/新的测试数据/空手道.txt";
		// String sourcePath = "D:/新的测试数据/value2.txt";
		// String targetPath = "D:/新的测试数据/test.txt";

		// FileReaders fr = new FileReaders(sourcePath);
		// fr.readFile(false, false);
		// FileWriters fw = new FileWriters(fr);
		// fw.writeNetFile(false,targetPath);
		// fw.writeCluFile(partitions,targetPath);
	}
}
