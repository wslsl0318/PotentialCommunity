package com.comparision.lcd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.fileoperations.methods.FileReaders;
import com.pulicoperations.methods.AdjacencyTables;
import com.pulicoperations.methods.Partitions;

public class LCD
{
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;

	public LCD(FileReaders fr)
	{
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
	}

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

				if (this.adjacencyMatrix[memberInMC][neighbor] != 0
						&& !mc.contains(neighbor)
						&& !neighborNodes.contains(neighbor))
				{
					neighborNodes.add(neighbor);
				}
			}
		}

		return neighborNodes;
	}

	public boolean isConnectWithAllNodesInMC(int mc_neighbor,
			ArrayList<Integer> mc)
	{
		for (Iterator<Integer> iter = mc.iterator(); iter.hasNext();)
		{
			Integer memberInMC = iter.next();

			if (this.adjacencyMatrix[mc_neighbor][memberInMC] == 0)
			{
				return false;
			}
		}

		return true;
	}

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
			// System.out.println(mc_neighbors);

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

					// System.out.println("mcs");
					// for (Iterator<ArrayList<Integer>> iter2 = mcs.iterator();
					// iter2
					// .hasNext();)
					// {
					// ArrayList<Integer> mca = iter2.next();
					//
					// for (Iterator<Integer> iter3 = mca.iterator(); iter3
					// .hasNext();)
					// {
					// Integer member = iter3.next();
					//
					// System.out.print(member + " ");
					// }
					// System.out.println();
					// }

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

				if (this.adjacencyMatrix[memberInLC][neighbor] != 0
						&& !lc.contains(neighbor) && !u.contains(neighbor))
				{
					u.add(neighbor);
				}
			}
		}

		// System.out.println(u);

		return u;
	}

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

				if (s.contains(member) && member != memberInC)
				{
					innerLinks++;
				}
				if (!s.contains(member) && member != memberInC)
				{

					outerLinks++;
				}
			}
		}

		m = (double) 0.5 * innerLinks / (double) outerLinks;

		return m;
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> runLCD(int id)
	{
		ArrayList<ArrayList<Integer>> lcs = new ArrayList<ArrayList<Integer>>();

		ArrayList<ArrayList<Integer>> mcs_final = getMCS(id);

		for (Iterator<ArrayList<Integer>> iter = mcs_final.iterator(); iter
				.hasNext();)
		{
			ArrayList<Integer> mc = iter.next();

			ArrayList<Integer> lc = new ArrayList<Integer>();
			lc.addAll(mc);
			ArrayList<Integer> u = new ArrayList<Integer>();
			u = updateU(u, lc);

			double m = calculateM(lc);
			int addedNodeID = -1;
			for (Iterator<Integer> iter2 = u.iterator(); iter2.hasNext();)
			{
				Integer memberInU = iter2.next();
				ArrayList<Integer> lc_temp = new ArrayList<Integer>();
				lc_temp.addAll(lc);
				lc_temp.add(memberInU);
				double m_temp = calculateM(lc_temp);
				if (m_temp > m)
				{
					addedNodeID = memberInU;
					m = m_temp;
				}
			}

			if (addedNodeID == -1)
			{
				lcs.add(lc);
				mcs_final.remove(mc);
				iter = mcs_final.iterator();
			} else
			{
				mc.add(addedNodeID);
				iter = mcs_final.iterator();
			}
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

		// System.out.println("lcs");
		// for (Iterator<ArrayList<Integer>> iter = lcs.iterator();
		// iter.hasNext();)
		// {
		// ArrayList<Integer> lc = iter.next();
		//
		// for (Iterator<Integer> iter2 = lc.iterator(); iter2.hasNext();)
		// {
		// Integer member = iter2.next();
		//
		// System.out.print(member + " ");
		// }
		// System.out.println();
		// }

		ArrayList<ArrayList<ArrayList<Integer>>> partitionFS = new ArrayList<ArrayList<ArrayList<Integer>>>();

		for (Iterator<ArrayList<Integer>> iter = lcs.iterator(); iter.hasNext();)
		{
			ArrayList<Integer> innerMembers = iter.next();

			ArrayList<Integer> outerMembers = Partitions.getOuterMembers(
					this.size, innerMembers);

			ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();

			partitionF.add(innerMembers);
			partitionF.add(outerMembers);
			partitionFS.add(partitionF);
		}

		// System.out.println("partitionFS:");
		// for (Iterator<ArrayList<ArrayList<Integer>>> iter = partitionFS
		// .iterator(); iter.hasNext();)
		// {
		// ArrayList<ArrayList<Integer>> partitionF = iter.next();
		// System.out.println();
		// for (Iterator<ArrayList<Integer>> iter2 = partitionF.iterator();
		// iter2
		// .hasNext();)
		// {
		// ArrayList<Integer> partition = iter2.next();
		// for (Iterator<Integer> iter3 = partition.iterator(); iter3
		// .hasNext();)
		// {
		// Integer member = iter3.next();
		// System.out.print(member + " ");
		// }
		// System.out.println();
		// }
		// }

		return partitionFS;
	}

	public static void main(String[] args)
	{
		String filePath = "D:/真实数据集/karate.txt";// 新的测试数据,真实数据集/graph,graph2,graph3,karate,
		// krebs' book.txt,

		FileReaders fr = new FileReaders(filePath);

		try
		{
			fr.readFile(false, false);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LCD lcd = new LCD(fr);

		// ArrayList<Integer> mc1 = new ArrayList<Integer>();
		// mc1.add(0);
		// mc1.add(1);
		// ArrayList<Integer> mc2 = new ArrayList<Integer>();
		// mc2.add(0);
		// mc2.add(2);
		// ArrayList<Integer> mc3 = new ArrayList<Integer>();
		// mc3.add(0);
		// mc3.add(3);
		// mc3.add(4);
		// ArrayList<Integer> mc4 = new ArrayList<Integer>();
		// mc4.add(0);
		// mc4.add(1);
		// mc4.add(2);
		// ArrayList<ArrayList<Integer>> mcs = new
		// ArrayList<ArrayList<Integer>>();
		// ArrayList<ArrayList<Integer>> mcs_new = new
		// ArrayList<ArrayList<Integer>>();
		// mcs.add(mc1);
		// mcs.add(mc2);
		// mcs_new.add(mc3);
		// mcs_new.add(mc4);

		// ArrayList<Integer> mc_neighbors = lcd.getMCNeighborNodes(mc);
		// lcd.getMCSNew(mc_neighbors, mc);

		// ArrayList<Integer> u = new ArrayList<Integer>();
		// u.add(0);
		// u.add(1);
		// u.add(2);
		// ArrayList<Integer> lc = new ArrayList<Integer>();
		// lc.add(0);
		// lc.add(1);
		// lc.add(2);

		// lcd.updateU(u, lc);

		lcd.runLCD(0);
		// ArrayList<Double> lcd_RPF=new ArrayList<Double>();
		// lcd_RPF.add((double) 0);
		// lcd_RPF.add((double) 0);
		// lcd_RPF.add((double) 0);
		// ArrayList<Double> lcd_RPF1=new ArrayList<Double>();
		// lcd_RPF1.add((double) 1);
		// lcd_RPF1.add((double) 2);
		// lcd_RPF1.add((double) 3);
		// ArrayList<Double> lcd_RPF2=new ArrayList<Double>();
		// lcd_RPF2.add((double) 4);
		// lcd_RPF2.add((double) 5);
		// lcd_RPF2.add((double) 6);
		// for(int i=0;i<3;i++)
		// {
		// Double x = lcd_RPF.get(i).;
		// x+=lcd_RPF1.get(i);
		// }
		// for(int i=0;i<3;i++)
		// {
		// Double x = lcd_RPF.get(i);
		// x+=lcd_RPF2.get(i);
		// }
		// System.out.println(lcd_RPF);
	}
}
