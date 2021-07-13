package com.fileoperations.methods;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileChangers
{
	public static String[] changeOldCluToNewClu(String oldCluFilePath,
			String newCluFilePath) throws IOException
	{
		FileInputStream fis = new FileInputStream(oldCluFilePath);
		byte[] buffer = new byte[200];
		int length = 0;
		String currentData = "";
		while (-1 != (length = fis.read(buffer, 0, 200)))
		{
			String str = new String(buffer, 0, length);

			currentData += str;
		}
		// System.out.println(currentData);
		fis.close();
		currentData = currentData.replaceAll("\r\n", " ");
		String[] strs;
		strs = currentData.split(" ");
		String[] strs_result = new String[strs.length];
		for (int i = 0; i < strs.length; i++)
		{
			String[] strs_temp = strs[i].split("\t");
			for (int j = 0; j < strs_temp.length; j++)
			{
				if (j == 1)
				{
					strs_result[i] = strs_temp[j];
				}
			}
		}
		// for(int i=0;i<strs_result.length;i++)
		// {
		// System.out.println(strs_result[i]);
		// }
		FileOutputStream fos = new FileOutputStream(newCluFilePath);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write("*Vertices" + " " + strs_result.length + "\r\n");
		for (int i = 0; i < strs_result.length; i++)
		{
			bw.write(strs_result[i] + "\r\n");
		}
		bw.close();
		return strs;
	}

	public static String[] changeOldNetToNewTxt(String oldNetFilePath,
			String newNetFilePath) throws IOException
	{
		FileInputStream fis = new FileInputStream(oldNetFilePath);
		byte[] buffer = new byte[200];
		int length = 0;
		String currentData = "";
		while (-1 != (length = fis.read(buffer, 0, 200)))
		{
			String str = new String(buffer, 0, length);
			currentData += str;
		}
		// System.out.println(currentData);
		fis.close();
		currentData = currentData.replaceAll("\r\n", " ");
		String[] strs;
		strs = currentData.split(" ");
		String[] strs_result = new String[strs.length * 2];
		for (int i = 0; i < strs.length; i++)
		{
			String[] strs_temp = strs[i].split("\t");
			for (int j = 0; j < strs_temp.length; j++)
			{
				strs_result[i * 2 + j] = strs_temp[j];
			}
		}
		// for(int i=0;i<strs_result.length;i++)
		// {
		// System.out.println(strs_result[i]);
		// }
		FileOutputStream fos = new FileOutputStream(newNetFilePath);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);
		for (int i = 0; i < strs_result.length; i += 2)
		{
			bw.write(strs_result[i] + " " + strs_result[i + 1] + "\r\n");
		}
		bw.close();
		return strs;
	}

	public static void main(String[] args) throws IOException
	{
		// String oldCluFilePath =
		// "C:/Users/naruto/Desktop/Fifth_LFR_Networks/1/1/1.net";
		// String newCluFilePath = "C:/Users/naruto/Desktop/1.txt";
		// changeOldNetToNewTxt(oldCluFilePath,newCluFilePath);

		String str1 = "C:/Users/123/Desktop/Fifth_LFR_Networks/";
		String str2 = "C:/Users/123/Desktop/Fifth_LFR_Networks(changed)/";

		for (int i = 1; i <= 2; i++)
		{
			for (int j = 1; j <= 8; j++)
			{
				for (int k = 1; k <= 1; k++)
				{
					String oldCluFilePath = str1 + Integer.toString(i) + "/"
							+ Integer.toString(j) + "/" + Integer.toString(k)
							+ ".clu";
					String newCluFilePath = str2 + Integer.toString(i) + "/"
							+ Integer.toString(j) + "/" + Integer.toString(k)
							+ ".clu";
					changeOldCluToNewClu(oldCluFilePath, newCluFilePath);

					String oldNetFilePath = str1 + Integer.toString(i) + "/"
							+ Integer.toString(j) + "/" + Integer.toString(k)
							+ ".net";
					String newTxTFilePath = str2 + Integer.toString(i) + "/"
							+ Integer.toString(j) + "/" + Integer.toString(k)
							+ ".txt";
					changeOldNetToNewTxt(oldNetFilePath, newTxTFilePath);
				}
			}
		}
	}
}
