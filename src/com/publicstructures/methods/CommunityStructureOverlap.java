package com.publicstructures.methods;

import java.util.ArrayList;
import java.util.Iterator;

public class CommunityStructureOverlap
{
	public int node_id;
	public ArrayList<Integer> community_ids;

	public CommunityStructureOverlap()
	{
		this.community_ids = new ArrayList<Integer>();
	}

	@Override
	public String toString()
	{
		return "CommunityStructureOverlap [node_id=" + node_id
				+ ", community_ids=" + community_ids + "]";
	}

	public static ArrayList<ArrayList<Integer>> get_communities_overlap(
			ArrayList<CommunityStructureOverlap> communities_structure_overlap)
	{
		ArrayList<ArrayList<Integer>> communities_overlap = new ArrayList<ArrayList<Integer>>();
		/* Get communities number */
		int count_community = 0;
		for (Iterator<CommunityStructureOverlap> iter_community_structure = communities_structure_overlap
				.iterator(); iter_community_structure.hasNext();)
		{
			CommunityStructureOverlap community_structure_overlap = iter_community_structure
					.next();
			ArrayList<Integer> communities_ids = community_structure_overlap.community_ids;
			int communities_ids_size = communities_ids.size();
			Integer last_community_id = communities_ids
					.get(communities_ids_size - 1);
			if (count_community < last_community_id)
			{
				count_community = last_community_id;
			}
		}
		// System.out.println(count_community);
		/* Build communities */
		for (int i = 0; i < count_community; i++)
		{
			ArrayList<Integer> community = new ArrayList<Integer>();
			communities_overlap.add(community);
		}
		// System.out.println(communities_overlap.size());
		/* Assign members */
		for (Iterator<CommunityStructureOverlap> iter_community_structure = communities_structure_overlap
				.iterator(); iter_community_structure.hasNext();)
		{
			CommunityStructureOverlap community_structure_overlap = iter_community_structure
					.next();
			int node_id = community_structure_overlap.node_id - 1;
			ArrayList<Integer> communities_ids = community_structure_overlap.community_ids;
			// System.out.println(node_id+","+communities_ids);
			for (int i = 0; i < communities_ids.size(); i++)
			{
				Integer community_id = communities_ids.get(i);
				communities_overlap.get(community_id - 1).add(node_id);
			}
		}
		return communities_overlap;
	}
}
