package com.comparision.TWD;

import java.util.TreeSet;

public class PBCommunity
{
    public TreeSet<Integer> pos_c;
    public TreeSet<Integer> bnd_c;

    public PBCommunity()
    {
        this.pos_c = new TreeSet<Integer>();
        this.bnd_c = new TreeSet<Integer>();
    }

    @Override
    public String toString()
    {
        return "PBCommunity [pos_c=" + pos_c + ", bnd_c=" + bnd_c + "]";
    }
}
