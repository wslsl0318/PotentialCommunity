package com.comparision.is;

import java.util.TreeSet;

public class ClusterCore {
    int node;
    int influences;
    double attraction;
    TreeSet<Integer> list = new TreeSet<>();


    public double getAttraction() {
        return attraction;
    }

    public void setAttraction(double attraction) {
        this.attraction = attraction;
    }



    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public int getInfluences() {
        return influences;
    }

    public void setInfluences(int influences) {
        this.influences = influences;
    }

    public TreeSet<Integer> getList() {
        return list;
    }

    public void setList(TreeSet<Integer> list) {
        this.list = list;
    }
}
