package com.comparision.methods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.comparision.coresimple.CommunityBasedOnCores;
import com.fileoperations.methods.FileReaders;
import com.publicstructures.methods.CommunityStructure;

public class LFRTests
{
	public String filePath;
	public String filePathOfRealCommunities;
	public FileReaders fr;
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> realCommunities;

	public LFRTests()
	{
		this.realCommunities = new ArrayList<ArrayList<Integer>>();
	}

	public LFRTests(String filePath, String filePathOfRealCommunities)
			throws IOException
	{
		this.filePath = filePath;
		this.filePathOfRealCommunities = filePathOfRealCommunities;
		this.fr = new FileReaders(filePath);
		try
		{
			fr.readFile(false, false);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		String[] strs = FileReaders
				.readRealCommunities(filePathOfRealCommunities);

		this.realCommunities = CommunityStructure.getComponentsFromString(strs);
		// this.topLevelNodes = new ArrayList<Integer>();
		CommunityBasedOnCores cbc = new CommunityBasedOnCores(fr);
		// this.topLevelNodes = cbc.getTopLevelNodes();
		// this.allNodes = cbc.getAllNodes();
	}

	public void LFR_Tests() throws IOException
	{

		String lfrFilePath = "D:/Fifth/tests on artificial(lfr) networks/";
		String parameterFilePath = new String();
		parameterFilePath = "mu/";
		for (int i = 1; i <= 1; i++)
		{
			String valueFilePath = new String();
			// System.out.println(lfrFilePath + parameterFilePath
			// + String.valueOf(i));
			ArrayList<Measures> all_algorithm_measure_values_average = new ArrayList<Measures>();
			/* NMI */
			double average_DC_NMI = 0;
			double average_KCC_NMI = 0;
			double average_Clauset_NMI = 0;
			double average_LWP_NMI = 0;
			double average_Chen_NMI = 0;
			double average_LS_NMI = 0;
			double average_LCD_NMI = 0;
			/* E */
			double average_DC_E = 0;
			double average_KCC_E = 0;
			double average_Clauset_E = 0;
			double average_LWP_E = 0;
			double average_Chen_E = 0;
			double average_LS_E = 0;
			double average_LCD_E = 0;
			/* rpf */
			ArrayList<Double> average_DC_rpf = new ArrayList<Double>();
			double average_DC_r = 0;
			double average_DC_p = 0;
			double average_DC_f = 0;
			ArrayList<Double> average_KCC_rpf = new ArrayList<Double>();
			double average_KCC_r = 0;
			double average_KCC_p = 0;
			double average_KCC_f = 0;
			ArrayList<Double> average_Clauset_rpf = new ArrayList<Double>();
			double average_Clauset_r = 0;
			double average_Clauset_p = 0;
			double average_Clauset_f = 0;
			ArrayList<Double> average_LWP_rpf = new ArrayList<Double>();
			double average_LWP_r = 0;
			double average_LWP_p = 0;
			double average_LWP_f = 0;
			ArrayList<Double> average_Chen_rpf = new ArrayList<Double>();
			double average_Chen_r = 0;
			double average_Chen_p = 0;
			double average_Chen_f = 0;
			ArrayList<Double> average_LS_rpf = new ArrayList<Double>();
			double average_LS_r = 0;
			double average_LS_p = 0;
			double average_LS_f = 0;
			ArrayList<Double> average_LCD_rpf = new ArrayList<Double>();
			double average_LCD_r = 0;
			double average_LCD_p = 0;
			double average_LCD_f = 0;

			for (int j = 1; j <= 1; j++)
			{
				valueFilePath = String.valueOf(i) + "/" + String.valueOf(j);
				String targetPath = lfrFilePath + parameterFilePath
						+ valueFilePath;
				String filePath = targetPath + ".txt";
				String filePathOfRealCommunities = targetPath + ".clu";
				System.out.println(filePath + "(clu)");
				// System.out.println(filePathOfRealCommunities);
				Methods m = new Methods(filePath, filePathOfRealCommunities);
				ArrayList<Measures> all_algorithm_measure_values = m
						.runMethods(targetPath);
				System.out
						.println("values_On_DataSet:(NMI    E    R    P    F)");
				for (int k = 0; k < all_algorithm_measure_values.size(); k++)
				{
					if (k == 0)
					{
						System.out.print("DC:      ");
					} else if (k == 1)
					{
						System.out.print("KCC:     ");
					} else if (k == 2)
					{
						System.out.print("Clauset: ");
					} else if (k == 3)
					{
						System.out.print("LWP:     ");
					} else if (k == 4)
					{
						System.out.print("Chen:    ");
					} else if (k == 5)
					{
						System.out.print("LS:      ");
					} else if (k == 6)
					{
						System.out.print("LCD:     ");
					}
					Measures measures = all_algorithm_measure_values.get(k);
					System.out.print(measures.NMI + " ");
					System.out.print(measures.E + " ");
					for (Iterator<Double> iter_rpf = measures.rpf.iterator(); iter_rpf
							.hasNext();)
					{
						Double rpf = iter_rpf.next();
						// rpf = ((int) (rpf * 100)) / (double) 100;
						System.out.print(rpf + " ");
					}
					System.out.println();
				}
			}
		}
		// String targetPath = "D:/Fifth/karates";
		// String str1 = "C:/Users/123/Desktop/Fifth_LFR_Networks/";// 1/1/1.txt
		// String str2 = "C:/Users/123/Desktop/Fifth_LFR_Networks(changed)/";//
		// 1/1/1.clu
		// double[][][][] values_LFRSets = new double[4][8][7][5];
		// for (int i = 1; i <= 1; i++)// 4
		// {
		// double[][][] values_On_LFRSet = new double[8][7][5];
		// for (int j = 1; j <= 8; j++)// 8
		// {
		// double[][] values_On_Mu = new double[7][5];
		// for (int k = 1; k <= 1; k++)// 5
		// {
		// String newCluFilePath = str2 + Integer.toString(i) + "/"
		// + Integer.toString(j) + "/" + Integer.toString(k)
		// + ".clu";
		// String newTxTFilePath = str2 + Integer.toString(i) + "/"
		// + Integer.toString(j) + "/" + Integer.toString(k)
		// + ".txt";
		// System.out.println(newCluFilePath);
		// System.out.println(newTxTFilePath);
		//
		// Methods methods = new Methods(newTxTFilePath,
		// newCluFilePath);
		// ArrayList<ArrayList<Double>> values_OneDataSet = methods
		// .runMethods(targetPath);
		// for (int l = 0; l < values_OneDataSet.size(); l++)
		// {
		// ArrayList<Double> line = values_OneDataSet.get(l);
		// for (int m = 0; m < line.size(); m++)
		// {
		// Double member = line.get(m);
		// values_On_Mu[l][m] += member / 1;
		// }
		// }
		// }
		// // System.out.println("values_On_DataSet_average:");
		// // double[][] values_OneMu = new double[7][5];
		// for (int l = 0; l < 7; l++)
		// {
		// for (int m = 0; m < 5; m++)
		// {
		// // System.out.print(values_On_Mu[l][m] + " ");
		// values_On_LFRSet[j - 1][l][m] = ((int) (values_On_Mu[l][m] * 100))
		// / (double) 100;
		// }
		// // System.out.println();
		// }
		// }
		// System.out.println("values_On_LFRSet:" + i);
		// for (int j = 1; j <= 8; j++)
		// {
		// System.out.println("mu=" + j);
		// for (int l = 0; l < 7; l++)
		// {
		// for (int m = 0; m < 5; m++)
		// {
		// System.out.print(values_On_LFRSet[j - 1][l][m] + " ");
		// }
		// System.out.println();
		// }
		// System.out.println();
		// }
		// }
	}

	public static void main(String[] args) throws IOException
	{
		LFRTests lfr = new LFRTests();
		lfr.LFR_Tests();
	}
}
