package com.comparision.coresimple;

import java.util.ArrayList;

public class KLevelNodes
{
	public int k;//level
	public ArrayList<Integer> nodes;
	
	public KLevelNodes()
	{
		this.nodes=new ArrayList<Integer>();
	}
	
	public KLevelNodes(int k,ArrayList<Integer> nodes)
	{
		this.k=k;
		this.nodes=new ArrayList<Integer>();
		this.nodes.addAll(nodes);
	}
}
