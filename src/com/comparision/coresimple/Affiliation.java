package com.comparision.coresimple;

public class Affiliation
{
	int id1;
	int id2;
	/* isAffiliation = true means id2.value > id1.value && id2.level>id1.level */
	boolean isAffiliation;

	public Affiliation()
	{

	}

	public Affiliation(int id1, int id2, boolean isAffiliation)
	{
		this.id1 = id1;
		this.id2 = id2;
		this.isAffiliation = isAffiliation;
	}

	@Override
	public String toString()
	{
		return "Affiliation [id1=" + id1 + ", id2=" + id2 + ", isAffiliation="
				+ isAffiliation + "]";
	}

}
