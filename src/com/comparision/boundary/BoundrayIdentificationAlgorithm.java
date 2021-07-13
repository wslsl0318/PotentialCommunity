package com.comparision.boundary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.fileoperations.methods.FileReaders;
import com.pulicoperations.methods.AdjacencyTables;

public class BoundrayIdentificationAlgorithm
{
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public ArrayList<Integer> C;
	public ArrayList<Integer> B;
	public ArrayList<Integer> NC;
	public double ts;

	public BoundrayIdentificationAlgorithm(FileReaders fr)
	{
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		
		for(int i=0;i<size;i++)
		{
			this.adjacencyMatrix[i][i]=1;
		}
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
		this.C = new ArrayList<Integer>();
		this.B = new ArrayList<Integer>();
		this.NC = new ArrayList<Integer>();
		this.ts = 0;
	}

	public void initSetC_B_NC_U(int s)
	{
		this.C.add(s);

		this.B = new ArrayList<Integer>();

		this.NC.addAll(this.adjacencyTable.get(s));
	}

	/* get N(i) */
	public ArrayList<Integer> getN(ArrayList<Integer> i)
	{
		ArrayList<Integer> ni = new ArrayList<Integer>();

		for (Iterator<Integer> iter = i.iterator(); iter.hasNext();)
		{
			Integer memberInI = iter.next();

			for (Iterator<Integer> iter2 = this.adjacencyTable.get(memberInI)
					.iterator(); iter2.hasNext();)
			{
				Integer member = iter2.next();

				if (!ni.contains(member))
				{
					ni.add(member);
				}
			}
		}

		// System.out.println("members In Ni:");
		// for (Iterator<Integer> iter = ni.iterator(); iter.hasNext();)
		// {
		// Integer memberInNi = iter.next();
		//
		// System.out.print(memberInNi + " ");
		// }
		// System.out.println();

		return ni;
	}

	/* get w(x,k) */
	public int getwxk(int k, ArrayList<Integer> x)
	{
		int numberOfLinks = 0;

		for (Iterator<Integer> iter = x.iterator(); iter.hasNext();)
		{
			Integer memberInX = iter.next();

			if (this.adjacencyMatrix[k][memberInX] != 0)
			{
				numberOfLinks++;
			}
		}

		// System.out.println("numberOfLinks: " + numberOfLinks);

		return numberOfLinks;
	}

	/* get intersection of N(i) and N(j) */
	public ArrayList<Integer> getIntersection(ArrayList<Integer> ni,
			ArrayList<Integer> nj)
	{
		ArrayList<Integer> intersection = new ArrayList<Integer>();

		for (Iterator<Integer> iter = ni.iterator(); iter.hasNext();)
		{
			Integer memberInNi = iter.next();

			if (nj.contains(memberInNi) && !intersection.contains(memberInNi))
			{
				intersection.add(memberInNi);
			}
		}

		// System.out.println("members In intersection of N(i) and N(j):");
		// for (Iterator<Integer> iter = intersection.iterator();
		// iter.hasNext();)
		// {
		// Integer memberInIntersection = iter.next();
		//
		// System.out.print(memberInIntersection + " ");
		// }
		// System.out.println();

		return intersection;
	}

	/* get Sum1 */
	public double getSum1(ArrayList<Integer> intersection,
			ArrayList<Integer> i, ArrayList<Integer> j)
	{
		double sum1 = 0;

		for (Iterator<Integer> iter = intersection.iterator(); iter.hasNext();)
		{
			Integer k = iter.next();

			int wki = getwxk(k, i);
			int wkj = getwxk(k, j);
			// System.out.println();

			sum1 += wki * wkj;
		}

//		 System.out.println("sum1: " + sum1);

		return sum1;
	}

	/* get Sum2 */
	public double getSum2(ArrayList<Integer> nx, ArrayList<Integer> x)
	{
		double sum2 = 0;

		for (Iterator<Integer> iter = nx.iterator(); iter.hasNext();)
		{
			Integer memberInNx = iter.next();

			int numberOfLinks = getwxk(memberInNx, x);

			sum2 += numberOfLinks * numberOfLinks;
		}

		sum2 = Math.sqrt(sum2);

//		 System.out.println("sum2: " + sum2);

		return sum2;
	}

	/* get L(i,j) */
	public double getLij(ArrayList<Integer> i, ArrayList<Integer> j)
	{
		double lij = 0;
		double x1 = 0;
		double x2 = 0;
		double x3 = 0;

		int numberOfLinks = 0;

		for (Iterator<Integer> iter = i.iterator(); iter.hasNext();)
		{
			Integer memberInI = iter.next();

			for (Iterator<Integer> iter2 = j.iterator(); iter2.hasNext();)
			{
				Integer memberInJ = iter2.next();

				if (this.adjacencyMatrix[memberInI][memberInJ] != 0)
				{
					numberOfLinks++;
				}
			}
		}

		if (numberOfLinks == 0)
		{
			return lij;
		}

		ArrayList<Integer> ni = getN(i);
		ArrayList<Integer> nj = getN(j);
		ArrayList<Integer> intersection = getIntersection(ni, nj);

		x1 = getSum1(intersection, i, j);
		x2 = getSum2(ni, i);
		x3 = getSum2(nj, j);

		lij = x1 / (x2 * x3);

		// System.out.println("L(" + i + "," + j + "):" + lij);

		return lij;
	}

	/* get IS(C,M) */
	public double getIS(ArrayList<Integer> c, ArrayList<Integer> m)
	{
		double iscm = 0;

		for (Iterator<Integer> iter = c.iterator(); iter.hasNext();)
		{
			Integer memberInC = iter.next();

			for (Iterator<Integer> iter2 = m.iterator(); iter2.hasNext();)
			{
				Integer memberInM = iter2.next();

				ArrayList<Integer> u = new ArrayList<Integer>();
				u.add(memberInC);
				ArrayList<Integer> v = new ArrayList<Integer>();
				v.add(memberInM);

				iscm += getLij(u, v);
			}
		}

		// System.out.println("IS(C,M): " + iscm);

		return iscm;
	}

	/* get DS(C) */
	public double getDS(ArrayList<Integer> c)
	{
		double dsc = 0;

		for (Iterator<Integer> iter = c.iterator(); iter.hasNext();)
		{
			Integer memberInC_1 = iter.next();

			for (Iterator<Integer> iter2 = c.iterator(); iter2.hasNext();)
			{
				Integer memberInC_2 = iter2.next();

				ArrayList<Integer> u = new ArrayList<Integer>();
				u.add(memberInC_1);
				ArrayList<Integer> v = new ArrayList<Integer>();
				v.add(memberInC_2);

				dsc += getLij(u, v);
			}
		}

		// System.out.println("DS(C): " + dsc);

		return dsc;
	}

	/* get TS */
	public double getTS()
	{
		double ts = 0;

		for (int i = 0; i < this.size; i++)
		{
			for (int j = 0; j < this.size; j++)
			{
				ArrayList<Integer> u = new ArrayList<Integer>();
				u.add(i);
				ArrayList<Integer> v = new ArrayList<Integer>();
				v.add(j);

				ts += getLij(u, v);
			}
		}

		// System.out.println("TS: " + ts);

		return ts;
	}

	/* get date Q */
	public double getdateQ(ArrayList<Integer> c, ArrayList<Integer> m)
	{
		double dateQ = 0;

		double iscm = getIS(c, m);
		double dsc = getDS(c);
		double dsm = getDS(m);
		double ts = this.ts;
		
		System.out.println(iscm+","+dsc+","+dsm+","+ts);

		dateQ = (2 * iscm) / ts - (2 * dsc * dsm) / (ts * ts);

		return dateQ;
	}

	/* update N(C) */
	public void updateNC()
	{
		this.NC.clear();

		for (Iterator<Integer> iter = this.C.iterator(); iter.hasNext();)
		{
			Integer memberInC = iter.next();

			for (Iterator<Integer> iter2 = this.adjacencyTable.get(memberInC)
					.iterator(); iter2.hasNext();)
			{
				Integer member = iter2.next();

				if (!this.NC.contains(member))
				{
					this.NC.add(member);
				}
			}
		}

		// System.out.println("members In N(C) :");
		// for (Iterator<Integer> iter = this.NC.iterator(); iter.hasNext();)
		// {
		// Integer memberInNC = iter.next();
		//
		// System.out.print(memberInNC + " ");
		// }
		// System.out.println();
	}

	/* get Neighbors Of C */
	public ArrayList<Integer> getNeighborsOfC()
	{
		ArrayList<Integer> neighborsOfC = new ArrayList<Integer>();

		for (Iterator<Integer> iter = this.NC.iterator(); iter.hasNext();)
		{
			Integer memberInNC = iter.next();

			if (!this.C.contains(memberInNC)
					&& !neighborsOfC.contains(memberInNC))
			{
				neighborsOfC.add(memberInNC);
			}
		}

		// System.out.println("may members In B(C) :");
		// for (Iterator<Integer> iter = neighborsOfC.iterator();
		// iter.hasNext();)
		// {
		// Integer memberInB = iter.next();
		//
		// System.out.print(memberInB + " ");
		// }
		// System.out.println();

		return neighborsOfC;
	}

	/* get Nodes Ou tOf C */
	public ArrayList<Integer> getNodesOutOfC()
	{
		ArrayList<Integer> nodesOutOfC = new ArrayList<Integer>();

		for (int i = 0; i < size; i++)
		{
			if (!this.C.contains(i))
			{
				nodesOutOfC.add(i);
			}
		}

		// System.out.println("may out of C :");
		// for (Iterator<Integer> iter = nodesOutOfC.iterator();
		// iter.hasNext();)
		// {
		// Integer nodeOutOfC = iter.next();
		//
		// System.out.print(nodeOutOfC + " ");
		// }
		// System.out.println();

		return nodesOutOfC;
	}

	/* update B */
	public void updateB()
	{
		this.B.clear();

		ArrayList<Integer> neighborsOfC = getNeighborsOfC();
		ArrayList<Integer> nodesOutOfC = getNodesOutOfC();

		for (Iterator<Integer> iter = neighborsOfC.iterator(); iter.hasNext();)
		{
			Integer neighbor = iter.next();

			ArrayList<Integer> j = new ArrayList<Integer>();
			j.add(neighbor);

			double ljc = getLij(j, C);

			for (Iterator<Integer> iter2 = nodesOutOfC.iterator(); iter2
					.hasNext();)
			{
				Integer nodeOutOfC = iter2.next();

				ArrayList<Integer> k = new ArrayList<Integer>();
				k.add(nodeOutOfC);

				double ljk = getLij(j, k);

				if (ljk > ljc && nodeOutOfC != neighbor)
				{
					if (!this.B.contains(neighbor))
					{
						this.B.add(neighbor);
					}

					break;
				}
			}
		}

//		 System.out.println("member in B :");
//		 for (Iterator<Integer> iter = this.B.iterator(); iter.hasNext();)
//		 {
//		 Integer memberInB = iter.next();
//		
//		 System.out.print(memberInB + " ");
//		 }
//		 System.out.println();
	}

	/* get Max L(i,k) */
	public double getMaxLik(ArrayList<Integer> i)
	{
		double maxlik = 0;
		
//		int nodeOutOfCX =-1;

		ArrayList<Integer> nodesOutOfC = getNodesOutOfC();

		for (Iterator<Integer> iter = nodesOutOfC.iterator(); iter.hasNext();)
		{
			Integer nodeOutOfC = iter.next();

			ArrayList<Integer> k = new ArrayList<Integer>();
			k.add(nodeOutOfC);

			if (!i.equals(k))
			{
				double lik = getLij(i, k);

				if (lik > maxlik)
				{
					maxlik = lik;
					
//					nodeOutOfCX = nodeOutOfC;
				}
			}
		}

//		System.out.println(nodeOutOfCX+" "+maxlik);
		
		return maxlik;
	}

	/* agglomerate local community */
	public void phase_1()
	{
		ArrayList<Integer> neighborsOfC = getNeighborsOfC();

		for (Iterator<Integer> iter = neighborsOfC.iterator(); iter.hasNext();)
		{
			Integer neighborOfC = iter.next();

			System.out.println("neighborsOfC: " + neighborOfC + " "
					+ neighborsOfC);

			ArrayList<Integer> i = new ArrayList<Integer>();
			i.add(neighborOfC);

			double lic = getLij(i, this.C);

			double maxlik = getMaxLik(i);

			System.out.println(lic + " " + maxlik);

			if (lic > maxlik)
			{
				if (!this.C.contains(neighborOfC))
				{
					this.C.add(neighborOfC);
					
					System.out.println(neighborOfC);

					neighborsOfC.clear();

					neighborsOfC = getNeighborsOfC();
				}

				updateNC();

				updateB();

				iter = neighborsOfC.iterator();

			} else
			{
				if (!this.B.contains(neighborOfC))
				{
					this.B.add(neighborOfC);
				}
			}
		}
	}

	/* get P */
	public int getP(ArrayList<Integer> i, ArrayList<Integer> ni)
	{
		int p = -1;

		double maxlik = 0;

		for (Iterator<Integer> iter = ni.iterator(); iter.hasNext();)
		{
			Integer memberInNi = iter.next();

			ArrayList<Integer> k = new ArrayList<Integer>();
			k.add(memberInNi);

			double lik = getLij(i, k);

			if (lik > maxlik && !this.C.contains(memberInNi))
			{
				maxlik = lik;

				p = memberInNi;
			}
		}

		return p;
	}

	/* identify the boundary of the local community */
	public void phase_2()
	{
		for (Iterator<Integer> iter = this.B.iterator(); iter.hasNext();)
		{
			Integer memberInB = iter.next();

			ArrayList<Integer> i = new ArrayList<Integer>();
			i.add(memberInB);

			ArrayList<Integer> ni = getN(i);

			int p = getP(i, ni);

			ArrayList<Integer> m = new ArrayList<Integer>();

			if (p != -1)
			{
				m.add(p);
				m.add(memberInB);

				double dateQ = getdateQ(this.C, m);
				
				System.out.println(m+" "+p+" "+dateQ);

				if (dateQ > 0)
				{
					
//					if(this.C.contains(o))

					updateNC();

					updateB();
					
//					break;

					iter = this.B.iterator();
				}
			}
		}
	}

	public void runBIA(int s)
	{
		this.C.add(s);
		updateNC();
		updateB();
		this.ts = getTS();

		int oldSize = this.C.size();

		int newSize = this.C.size();

//		phase_1();
//		System.out.println("***********************************");
//
//		 phase_2();
//		 
//		 phase_1();

		 do
		 {
		 oldSize = newSize;
		
		 phase_1();
		
		 phase_2();
		
		 newSize = this.C.size();
		
		 } while (newSize > oldSize);

		System.out.println("members In C: ");
		for (Iterator<Integer> iter = this.C.iterator(); iter.hasNext();)
		{
			Integer memberInC = iter.next();

			System.out.print(memberInC + " ");
		}
		System.out.println();

		System.out.println("members In NC: ");
		for (Iterator<Integer> iter = this.NC.iterator(); iter.hasNext();)
		{
			Integer memberInNC = iter.next();

			System.out.print(memberInNC + " ");
		}
		System.out.println();

		System.out.println("members In B: ");
		for (Iterator<Integer> iter = this.B.iterator(); iter.hasNext();)
		{
			Integer memberInB = iter.next();

			System.out.print(memberInB + " ");
		}
		System.out.println();
	}

	public static void main(String[] args)
	{
		String filePath = "D:/新的测试数据/graph2.txt";// 新的测试数据,真实数据集/graph,graph2,graph3,karate,
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

		BoundrayIdentificationAlgorithm bia = new BoundrayIdentificationAlgorithm(
				fr);

		 ArrayList<Integer> i = new ArrayList<Integer>();
		 i.add(0);
//		 i.add(5);

		 ArrayList<Integer> j = new ArrayList<Integer>();
//		 j.add(2);
		 j.add(12);
		 
		 
		// j.add(4);

		// int k = 0;
		// bia.getwxk(k, bia.getN(i));

		// ArrayList<Integer> intersection = bia.getIntersection(bia.getN(i),
		// bia.getN(j));

		// bia.getSum1(intersection, i, j);

		// bia.getSum2(bia.getN(i), i);

		// ArrayList<Integer> c = new ArrayList<Integer>();
		// c.add(1);
		// c.add(2);

		// ArrayList<Integer> m = new ArrayList<Integer>();
		// m.add(3);
		// m.add(4);

		// bia.getIS(c, m);

		// bia.C.add(0);
		// bia.updateNC();
		// System.out.println(bia.getNeighborsOfC());
		// System.out.println(bia.getNodesOutOfC());
		// bia.C.add(2);
		// bia.updateNC();
		// bia.getNeighborsOfC();
		// bia.getNodesOutOfC();
		// bia.updateB();
		// bia.getNeighborsOfC();
//		 bia.C.add(5);
//		 bia.C.add(2);
//		 bia.C.add(0);
		bia.runBIA(22);
		// bia.getMaxLik(i, bia.getN(i));
		// System.out.println(bia.getN(2));
		// bia.calculateLiC(0);
//		 bia.C.add(4);
//		 bia.C.add(5);
//		 bia.updateNC();
//		 System.out.println(bia.getLij(i, j));
//		 System.out.println(bia.getMaxLik(i));
		 
	}
}
