package com.comparision.chen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.fileoperations.methods.FileReaders;
import com.pulicoperations.methods.AdjacencyTables;
import com.pulicoperations.methods.Partitions;

public class ConnectionDensity
{
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public ArrayList<Integer> D;
	public ArrayList<Integer> C;
	public ArrayList<Integer> B;
	public ArrayList<Integer> S;
	public int inD;
	public int outD;
	public double lin;
	public double lex;

	public ConnectionDensity(FileReaders fr)
	{
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
		this.D = new ArrayList<Integer>();
		this.C = new ArrayList<Integer>();
		this.B = new ArrayList<Integer>();
		this.S = new ArrayList<Integer>();
		this.inD = 0;
		this.outD = 0;
		this.lin = 0;
		this.lex = 0;
	}

	/* initSetD,B,S */
	public void initSetD_B_S(int id)
	{
		this.D.add(id);
		this.B.add(id);
		for (Iterator<Integer> iter = this.adjacencyTable.get(id).iterator(); iter
				.hasNext();)
		{
			this.S.add(iter.next());
		}
		this.S.remove((Integer) id);

		this.inD = 0;
		this.outD = this.S.size();

		this.lin = 0;
		this.lex = (double) this.S.size() / (double) this.B.size();
	}

	/* get indi based on set D */
	public int getindi(int i)
	{
		int indi = 0;

		for (Iterator<Integer> iter = this.D.iterator(); iter.hasNext();)
		{
			if (this.adjacencyMatrix[i][iter.next()] != 0)
			{
				indi++;
			}
		}

		return indi;
	}

	/* get outdi based on set the rest of the network */
	public int getoutdi(int i, int indi)
	{
		int outdi = 0;

		outdi = this.adjacencyTable.get(i).size() - 1 - indi;

		return outdi;
	}

	/* get b_new.size() */
	public ArrayList<Integer> getb_new(int i)
	{
		ArrayList<Integer> b_new = new ArrayList<Integer>();

		ArrayList<Integer> dtemp = new ArrayList<Integer>();

		dtemp.addAll(this.D);

		dtemp.add(i);

		for (Iterator<Integer> iter = dtemp.iterator(); iter.hasNext();)
		{
			Integer memberInDtemp = iter.next();

			for (Iterator<Integer> iter2 = this.adjacencyTable.get(
					memberInDtemp).iterator(); iter2.hasNext();)
			{
				Integer neighbor = iter2.next();

				if (this.adjacencyMatrix[memberInDtemp][neighbor] != 0
						&& !dtemp.contains(neighbor)
						&& !b_new.contains(memberInDtemp))
				{
					b_new.add(memberInDtemp);

					break;
				}
			}
		}

		return b_new;
	}

	public ArrayList<Double> getLi_new(int i, int indi, int outdi)
	{
		ArrayList<Double> values = new ArrayList<Double>();

		double li_new = 0;

		int b_newSize = getb_new(i).size();

		double fenzi = (double) (this.inD + 2 * indi)
				/ (double) (this.D.size() + 1);

		double fenmu = (double) (this.outD - indi + outdi) / (double) b_newSize;

		li_new = fenzi / fenmu;

		values.add(li_new);
		values.add(fenzi);
		values.add(fenmu);

		return values;
	}

	public NodeI getNodeIMaxli()
	{
		TreeSet<NodeI> nodesInS = new TreeSet<NodeI>(new NodeI());

		for (Iterator<Integer> iter = this.S.iterator(); iter.hasNext();)
		{
			Integer memberInS = iter.next();

			int id = memberInS;
			int indi = getindi(id);
			int outdi = getoutdi(id, indi);

			ArrayList<Double> values = new ArrayList<Double>();

			values = getLi_new(id, indi, outdi);

			double li_new = values.get(0);

			NodeI nodei = new NodeI(id, li_new, indi, outdi);

			double lin_new = values.get(1);
			double lex_new = values.get(2);

			if (lin_new >= this.lin)
			{
				nodei.flag = true;
				nodei.fenzi = lin_new;
				nodei.fenmu = lex_new;
			}

			// System.out.println(nodei.id + "," + nodei.li_new + "," +
			// nodei.flag
			// + "," + nodei.fenzi + "," + nodei.fenmu);

			nodesInS.add(nodei);
		}

		return nodesInS.first();
	}

	/* Discovery Phase */
	public void discoveryPhase(int id)
	{
		initSetD_B_S(id);
		double l = 0;
		double l_new = 0;

		do
		{
			l = l_new;

			NodeI i_max = new NodeI();

			i_max = getNodeIMaxli();

			// System.out.println(this.lin + "," + this.lex + "," + this.inD +
			// ","
			// + this.outD);
			// System.out.println(i_max.id + "," + i_max.li_new + "," +
			// i_max.flag
			// + "," + i_max.fenzi + "," + i_max.fenmu + "," + i_max.indi
			// + "," + i_max.outdi);

			if (i_max.li_new > l && i_max.flag == true)
			{
				l_new = i_max.li_new;

				this.lin = i_max.fenzi;

				this.lex = i_max.fenmu;

				this.inD += 2 * i_max.indi;

				this.outD += (i_max.outdi - i_max.indi);

				this.D.add(i_max.id);

				for (Iterator<Integer> iter = this.adjacencyTable.get(i_max.id)
						.iterator(); iter.hasNext();)
				{
					Integer member = iter.next();

					if (!this.D.contains(member) && !this.S.contains(member))
					{
						this.S.add(member);
					}
				}

				this.S.remove((Integer) i_max.id);

				this.B = getb_new(i_max.id);

				// System.out.println(i_max.id + "," + D + "," + S + "," + B);
				// System.out.println(this.lin + "," + this.lex + "," + this.inD
				// + "," + this.outD);

			} else
			{
				this.S.remove((Integer) i_max.id);

				continue;
			}

		} while (l_new > l);

		// System.out.println(this.D);
	}

	public ArrayList<Integer> getBi(ArrayList<Integer> tempDi)
	{
		ArrayList<Integer> b_i = new ArrayList<Integer>();

		for (Iterator<Integer> iter = tempDi.iterator(); iter.hasNext();)
		{
			Integer memberInTempDi = iter.next();

			for (Iterator<Integer> iter2 = this.adjacencyTable.get(
					memberInTempDi).iterator(); iter2.hasNext();)
			{
				Integer neighbor = iter2.next();

				if (this.adjacencyMatrix[memberInTempDi][neighbor] != 0
						&& !tempDi.contains(neighbor)
						&& !b_i.contains(memberInTempDi))
				{
					b_i.add(memberInTempDi);

					break;
				}
			}
		}

		return b_i;
	}

	/* Examination Phase */
	public void examinationPhase()
	{
		ArrayList<Integer> nodesToBeRemoved = new ArrayList<Integer>();

		double lin_now = this.lin;
		double lex_now = this.lex;
		double l_now = lin_now / lex_now;

		// System.out.println(lin_now + "," + lex_now + "," + l_now);

		for (Iterator<Integer> iter = this.D.iterator(); iter.hasNext();)
		{
			ArrayList<Integer> tempDi = new ArrayList<Integer>();

			tempDi.addAll(this.D);

			Integer memberInD = iter.next();

			tempDi.remove(memberInD);

			// System.out.println(tempDi);

			ArrayList<Integer> tempBi = new ArrayList<Integer>();

			tempBi = getBi(tempDi);

			// System.out.println(tempBi);

			int numberOfInnerLinks = 0;
			int numberOfOuterLinks = 0;

			for (Iterator<Integer> iter2 = tempDi.iterator(); iter2.hasNext();)
			{
				Integer memberInTempDi = iter2.next();

				for (Iterator<Integer> iter3 = this.adjacencyTable.get(
						memberInTempDi).iterator(); iter3.hasNext();)
				{
					Integer neighbor = iter3.next();

					if (this.adjacencyMatrix[memberInTempDi][neighbor] != 0
							&& tempDi.contains(neighbor))
					{
						numberOfInnerLinks++;
					}

					if (this.adjacencyMatrix[memberInTempDi][neighbor] != 0
							&& !tempDi.contains(neighbor))
					{
						numberOfOuterLinks++;
					}
				}
			}

			// System.out.println(numberOfInnerLinks + "," +
			// numberOfOuterLinks);

			double lin_i = (double) numberOfInnerLinks / (double) tempDi.size();
			double lex_i = (double) numberOfOuterLinks / (double) tempBi.size();
			double l_i = lin_i / lex_i;

			// System.out.println(lin_i + "," + lex_i);

			if (l_now > l_i && (lin_now >= lin_i && lex_now <= lex_i))
			{
			} else
			{
				nodesToBeRemoved.add(memberInD);
			}
		}

		// System.out.println(nodesToBeRemoved);

		for (Iterator<Integer> iter = nodesToBeRemoved.iterator(); iter
				.hasNext();)
		{
			Integer nodeToBeRemoved = iter.next();

			if (this.D.contains(nodeToBeRemoved))
			{
				this.D.remove(nodeToBeRemoved);
			}
		}

		// System.out.println(D);
	}

	public ArrayList<ArrayList<Integer>> runChen(int id)
	{
		discoveryPhase(id);
		examinationPhase();

		if (this.D.contains((Integer) id))
		{
			ArrayList<Integer> innerMembers = this.D;

			ArrayList<Integer> outerMembers = Partitions.getOuterMembers(
					this.size, innerMembers);

			ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();

			partitionF.add(innerMembers);
			partitionF.add(outerMembers);

			System.out.println("partitionF:");
			for (Iterator<ArrayList<Integer>> iter = partitionF.iterator(); iter
					.hasNext();)
			{
				ArrayList<Integer> partition = iter.next();
				for (Iterator<Integer> iter2 = partition.iterator(); iter2
						.hasNext();)
				{
					Integer member = iter2.next();
					System.out.print(member + " ");
				}
				System.out.println();
			}

			return partitionF;
		} else
		{
			ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();

			return partitionF;
		}
	}

	public static void main(String[] args)
	{
		String filePath = "D:/真实数据集/karate.txt";// 新的测试数据,真实数据集/graph,graph2,graph3,karate,
		// krebs' book.txt,football

		FileReaders fr = new FileReaders(filePath);

		try
		{
			fr.readFile(false, false);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ConnectionDensity cd = new ConnectionDensity(fr);

		cd.runChen(3);
	}
}
