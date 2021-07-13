package com.comparision.linksimilarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.fileoperations.methods.FileReaders;
import com.pulicoperations.methods.AdjacencyTables;
import com.pulicoperations.methods.Partitions;

public class LinkSimilarity
{
	public int size;
	public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public ArrayList<Integer> s;
	public ArrayList<Integer> c;
	public ArrayList<Integer> n;
	public double m;
	public TreeSet<LSNode> h;

	public LinkSimilarity(FileReaders fr)
	{
		this.size = fr.size;
		this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = AdjacencyTables.getAdjacencyTable(size,
				fr.adjacencyMatrix);
		this.s = new ArrayList<Integer>();
		this.c = new ArrayList<Integer>();
		this.n = new ArrayList<Integer>();
		this.m = 0;
		this.h = new TreeSet<LSNode>(new LSNode());
	}

	public void initAlgorithm(int id)
	{
		this.s.add(id);
		this.c.add(id);
		this.n.addAll(this.adjacencyTable.get(id));
		this.n.remove((Integer) id);
		this.m = 0;
	}

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

				if (this.adjacencyMatrix[memberInC][neighbor] != 0
						&& !this.c.contains(neighbor)
						&& !this.n.contains(neighbor))
				{
					this.n.add(neighbor);
				}
			}
		}
		// System.out.println(n);
	}

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

				if (this.adjacencyMatrix[memberInC][neighbor] != 0
						&& this.n.contains(neighbor)
						&& !this.s.contains(memberInC))
				{
					this.s.add(memberInC);
				}
			}
		}
		// System.out.println(s);
	}

	public ArrayList<Integer> getTv(int v)
	{
		ArrayList<Integer> tv = new ArrayList<Integer>();

		tv.addAll(this.adjacencyTable.get(v));
		tv.remove((Integer) v);

		return tv;
	}

	public ArrayList<Integer> getTempSet(int v)
	{
		ArrayList<Integer> ts = new ArrayList<Integer>();

		ArrayList<Integer> tv = getTv(v);

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

	public void updateH()
	{
		this.h.clear();

		for (Iterator<Integer> iter = this.n.iterator(); iter.hasNext();)
		{
			Integer memberInN = iter.next();

			ArrayList<Integer> tv = getTv(memberInN);
			ArrayList<Integer> ts = getTempSet(memberInN);

			double lsValue = (double) (ts.size()) / (double) (tv.size());

			LSNode lsn = new LSNode(memberInN, lsValue);

			this.h.add(lsn);
		}
	}

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

	public ArrayList<ArrayList<Integer>> runLS(int id)
	{
		// System.out.println("init");
		initAlgorithm(id);
		updateH();

		// System.out.println(s);
		// System.out.println(c);
		// System.out.println(n);
		// System.out.println(m);
		// for (Iterator<LSNode> iter = this.h.iterator(); iter.hasNext();)
		// {
		// LSNode memberInH = iter.next();
		//
		// System.out.println(memberInH.id + "," + memberInH.lsValue);
		// }
		// System.out.println();

		do
		{
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
				updateS();
				this.m = newM_addition;
			}

			// System.out.println(s);
			// System.out.println(c);
			// System.out.println(n);
			// System.out.println(m);

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
						updateS();
						this.m = newM_deletion;
						iter = this.s.iterator();
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
			// System.out.println(s);
			// System.out.println(c);
			// System.out.println(n);
			// System.out.println(m);
			// for (Iterator<LSNode> iter = this.h.iterator(); iter.hasNext();)
			// {
			// LSNode memberInH = iter.next();
			//
			// System.out.println(memberInH.id + "," + memberInH.lsValue);
			// }
			// System.out.println();

			c_new.addAll(this.c);

			if (!c_old.equals(c_new))
			{
				continue;
			} else
			{
				break;
			}

		} while (true);

		// System.out.println(c);

		if (this.c.contains((Integer) id))
		{
			ArrayList<Integer> innerMembers = this.c;

			ArrayList<Integer> outerMembers = Partitions.getOuterMembers(
					this.size, innerMembers);

			ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();

			partitionF.add(innerMembers);
			partitionF.add(outerMembers);

			// System.out.println("partitionF:");
			// for (Iterator<ArrayList<Integer>> iter = partitionF.iterator();
			// iter
			// .hasNext();)
			// {
			// ArrayList<Integer> partition = iter.next();
			// for (Iterator<Integer> iter2 = partition.iterator(); iter2
			// .hasNext();)
			// {
			// Integer member = iter2.next();
			// System.out.print(member + " ");
			// }
			// System.out.println();
			// }

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

		LinkSimilarity ls = new LinkSimilarity(fr);

		// ls.initAlgorithm(0);
		// ls.c.add(1);
		// ls.c.add(4);
		// ls.c.add(5);
		// ls.updateN();
		// ls.updateS();
		// System.out.println(ls.c);
		// ls.updateH();

		ls.runLS(0);

		// TreeSet<Integer> x1 = new TreeSet<Integer>();
		// x1.add(0);
		// x1.add(1);
		// x1.add(2);
		// TreeSet<Integer> x2 = new TreeSet<Integer>();
		// x2.add(2);
		// x2.add(1);
		// x2.add(0);
		// System.out.println(x1.equals(x2));

	}
}
